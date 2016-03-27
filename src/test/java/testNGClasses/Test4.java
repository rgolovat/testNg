package testNGClasses;

import org.testng.annotations.Test;
import pages.MainPage;
import utils.MainClass;

public class Test4 extends MainClass {
	

	  
	  @Test(testName = "Check Price", description = "Verifies if results within the price query", groups = {"Smoke", "Regression"})  
	  public void price() {
		  getPage("http://whitelabeldemo.skyscanner.net/en-GB/flights");
		  MainPage.enterOriginCity("Lviv");
		  MainPage.enterDestinationCity("Kiev");
		  sleepFor(1000);
		  MainPage.enterSearchButton();
		  sleepFor(5000);
		  MainPage.movePriceSlider();
		  sleepFor(1000);
		  MainPage.checkPrice();
		  sleepFor(1000);
	  }
 
	   
}
