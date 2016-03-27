package testNGClasses;

import org.testng.annotations.Test;
import pages.MainPage;
import utils.MainClass;

public class Test2 extends MainClass {

	@Test(testName = "Go to Car hire Page", description = "Verify if redirected to right page", groups = "Smoke")
	public void test2() {
		getPage("http://whitelabeldemo.skyscanner.net/en-GB/flights");
		clickOn(MainPage.carHireLink, "Car Hire");
		assertTrue("Verifying if redirected to correct page",
				getCurrUrl().equals("http://whitelabeldemo.skyscanner.net/en-GB/carhire/"));
	}

}
