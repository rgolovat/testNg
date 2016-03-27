package testNGClasses;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.ResultPage;
import utils.MainClass;

public class Test1 extends MainClass {
	
	@Test(testName = "Reserve tickets", description = "Verifies if results are returned", groups = {"Smoke", "Regression"})
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
			assertTrue("Verifying if have results", el.getText() != null);
		}
	}
}
