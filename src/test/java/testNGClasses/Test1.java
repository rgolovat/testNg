package testNGClasses;

import org.testng.annotations.Test;

import bsh.BshClassManager.Listener;

import org.testng.AssertJUnit;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import pages.MainPage;
import pages.ResultPage;
import utils.ListenersQw;
import utils.MainClass;

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
	
}
