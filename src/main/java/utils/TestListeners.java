package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import utils.TestrailApi;

import java.lang.reflect.Method;

@Listeners(TestListeners.class)
public class TestListeners implements ITestListener {

    private Method m;
    private String name;

    public void onTestStart(ITestResult result) {
    }

    public void onTestSuccess(ITestResult result) {
        TestrailApi.postResults("1", 1);
        System.out.println("pass");
    }

    public void onTestFailure(ITestResult result) {

        TestrailApi.postResults("1", 5);
        System.out.println("fail");
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }


}