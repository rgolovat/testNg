package testNGClasses;

import org.testng.annotations.Test;
import pages.MainPage;
import utils.MainClass;

public class Test2 extends MainClass {

	@Test
	public void test2() {
		getPage("http://whitelabeldemo.skyscanner.net/en-GB/flights");
		clickOn(MainPage.carHireLink);
		assertEquals("Verifying if redirected to correct page", getCurrUrl(),
				"http://whitelabeldemo.skyscanner.net/en-GB/carhire/");
	}

}
