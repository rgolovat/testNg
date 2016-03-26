package testNGClasses;

import org.testng.annotations.Test;
import pages.MainPage;
import utils.MainClass;

public class NewTest extends MainClass {
  @Test
  public void f() {
	  getPage("http://whitelabeldemo.skyscanner.net/en-GB/flights");
	  MainPage.enterOriginCity("London Luton");
	  MainPage.enterDestinationCity("Vigo");
	  sleepFor(10000);
  }
}
