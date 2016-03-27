package testNGClasses;

import org.testng.annotations.Test;
import pages.MainPage;
import utils.MainClass;

public class Test5 extends MainClass {

	  @Test(testName = "Check Depart", description = "Verifies if results within the depart query", groups = {"Smoke", "Regression"})
	  public void depart() {
		  getPage("http://whitelabeldemo.skyscanner.net/en-GB/flights");
		  MainPage.enterOriginCity("Lviv");
		  MainPage.enterDestinationCity("Kiev");
		  sleepFor(1000);
		  MainPage.enterSearchButton();
		  sleepFor(5000);
		  MainPage.moveDepartSlider();
		  sleepFor(1000);
		  MainPage.checkDepart();
		  sleepFor(1000);
	  }  
	   
}
