package utils;

import com.google.gson.Gson;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.http.Status;
import com.jayway.restassured.response.ResponseBody;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

public class TestrailApi {

    private static final String url = TestConfiguration.getProperty("testrail.url");

    public static Map<String,List<Map<String,List<Map<String,Integer>>>>> getProjects() {

        Map<String,List<Map<String,List<Map<String,Integer>>>>> response = given()
                .auth()
                .preemptive()
                .basic(TestConfiguration.getProperty("testrail.login"), TestConfiguration.getProperty("testrail.password"))
                .contentType("application/json")
                .expect()
                .statusCode(200)
                .when()
                .get(url + "?/api/v2/get_plan/1")
                .then()
                .contentType(ContentType.JSON)
                .extract().path("$");
//        System.out.println(response);
        return response;
    }

    public static List<Map<String,String>> getTests() {

        List<Map<String,String>> response = given()
                .auth()
                .preemptive()
                .basic(TestConfiguration.getProperty("testrail.login"), TestConfiguration.getProperty("testrail.password"))
                .contentType("application/json")
                .expect()
                .statusCode(200).log().all()
                .when()
                .get(url + "?/api/v2/get_tests/" + getProjects().get("entries").get(0).get("runs").get(0).get("id"))
                .then()
                .contentType(ContentType.JSON)
                .extract().path("$");
        System.out.println(response);
        return response;
    }


    public static void postResults(String id, Integer status_id) {

        Gson gson = new Gson();
        StatusId statusId = new StatusId();
        statusId.status_id = status_id;
        statusId.comment = "This test worked fine!";



            ResponseBody response = given()
                    .auth()
                    .preemptive()
                    .basic(TestConfiguration.getProperty("testrail.login"), TestConfiguration.getProperty("testrail.password"))
                    .contentType("application/json")
                    .with()
                    .body(gson.toJson(statusId)).log().all()
                    .post(url + "?/api/v2/add_result/" + id).body();
            response.toString();
            printResponse(response);
        }


    public static void printResponse(ResponseBody response) {
        BufferedReader br = new BufferedReader(new InputStreamReader(response.asInputStream()));
        String out;
        try {
            while ((out = br.readLine()) != null) {
                System.out.println(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String ,String> getMap() {
        Map<String, String> titles = new HashMap<String, String>();
        List<Map<String,String >> list = TestrailApi.getTests();
        for(Map<String,String> l : list){
            System.out.println( l.get("title"));

            titles.put(l.get("title"),l.get("id"));

        }
        return titles;
    }

    public static String getIdbyName(String name){
        return getMap().get(name);
    }

}
