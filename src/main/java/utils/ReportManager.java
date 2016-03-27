package utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ReportManager {

	private static Map<Long, ExtentTest> testThread = new HashMap<Long, ExtentTest>();
	private static ExtentReports extent;
	
	private synchronized static ExtentReports getInstance() {
		if (extent == null) {
			extent = new ExtentReports("./src/test/java/resources/reports/Report.html", true);
		}
		return extent;
	}

	public synchronized static Map<Long, ExtentTest> startTest(String testName, String testDescription, String ... groups) {
		Long threadID = Thread.currentThread().getId();
		ExtentTest test = getInstance().startTest(testName, testDescription);
		test.assignCategory(groups);
		testThread.put(threadID, test);
		return testThread;
	}
	
	public synchronized static ExtentTest Logger() {
		ExtentTest logger = null;
		Long threadID = Thread.currentThread().getId();
		if (testThread.containsKey(threadID)) {
			logger = testThread.get(threadID);
		}
		return logger;
	}

	public synchronized static void closeTest() {
		getInstance().endTest(Logger());
	}

	public synchronized static void closeReporter() {
		getInstance().flush();
	}
	
	private String getTestName(Method m) {
		String testName = null;
			testName = m.getAnnotation(Test.class).testName();
		if (testName == null || testName == "") {
			testName = getClass().getSimpleName();
		}
		return testName;
	}
	
	private String getTestDescription(Method m) {
		String testDescription = null;
			testDescription = m.getAnnotation(Test.class).description();
		if (testDescription == null || testDescription == "") {
			testDescription = "";
		}
		return testDescription;
	}
	
	private static String getTestGroups(Method m) {
		String b = "";
		String[] testGroups = m.getAnnotation(Test.class).groups();
		try {
			for (int i = 0; i < testGroups.length; i++) {
				b = b + ": " + testGroups[i];
			}
		} catch (Exception e) {
		}
		return b;
	}

	@BeforeMethod
	public void startReporting(Method m) {
		startTest(getTestName(m), getTestDescription(m), getTestGroups(m), WebBrowser.getCurrentBrowserName());
	}

	@AfterMethod
	public void stopReporting() {
		closeTest();
	}

}