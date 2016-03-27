package testNGClasses;

import org.testng.annotations.Test;
import pages.MainPage;
import utils.MainClass;

public class Test3 extends MainClass {
	
	@Test(testName = "Check Price and Depart", description = "Verifies if results in the price query and the depart query ", groups = {"Smoke", "Regression"})  
	public void f() {
		  getPage("http://whitelabeldemo.skyscanner.net/en-GB/flights");
		  MainPage.enterOriginCity("Lviv");
		  MainPage.enterDestinationCity("Kiev");
		  sleepFor(1000);
		  MainPage.enterSearchButton();
		  sleepFor(5000);
		  MainPage.movePriceSlider();
		  sleepFor(1000);
		  MainPage.moveDepartSlider();
		  sleepFor(1000);
		  MainPage.checkPrice();
		  MainPage.checkDepart();
		  sleepFor(1000);
	  }
	   
}
