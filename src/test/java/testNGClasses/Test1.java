package testNGClasses;

import org.omg.CORBA.MARSHAL;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.w3c.dom.ls.LSInput;
import pages.MainPage;
import pages.ResultPage;
import utils.MainClass;
import utils.TestrailApi;

public class Test1 extends MainClass {
	@Test(testName = "Get Tickets", description = "Verifies if have results", groups = {"Smoke"})
	public void test1() {
		getPage("http://whitelabeldemo.skyscanner.net/en-GB/flights");
		MainPage.enterOriginCity("London Luton");
		MainPage.enterDestinationCity("Vigo");
		MainPage.selectDepart("2016", "April", "18");
		MainPage.selectNumOfAdults("1");
		MainPage.selectNumOfChildren("1");
		MainPage.selectNumOfInfants("1");
		MainPage.selectFlightClass("Economy");
		clickOn(MainPage.searchButton, "Search");
		List<WebElement> elements = getElements(ResultPage.airlineCards);
		for (WebElement el: elements) {
			AssertJUnit.assertTrue("Verifying if have results", el.getText() != null);
		}
	}

	public static void main(String args[]){
		Map<String, String> titles = new HashMap<String, String>();
		List<Map<String,String >> list = TestrailApi.getTests();
		for(Map<String,String> l : list){
			System.out.println( l.get("title"));

			titles.put(l.get("title"),l.get("id"));

		}
		System.out.println(titles);


	}
	
}
