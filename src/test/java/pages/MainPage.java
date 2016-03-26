package pages;

import org.openqa.selenium.By;

import utils.MainClass;

public class MainPage extends MainClass {
	
	public static By originCity = By.xpath("//div[@class='js-originplace field-box flex']/div");
	public static By destinationCity = By.xpath("//div[@class='js-destinationplace field-box flex']/div");
	
	public static void enterOriginCity(String city) {
		clickOn(MainPage.originCity, "Origin city");
		enterText(By.xpath("//input[@placeholder='Origin city or airport']"), city);
		clickOn(By.xpath("//ul[@class='dropdown-items js-dropdown-items active']//span[text()='" + city + "']"), city);
	}
	
	public static void enterDestinationCity(String city){
		clickOn(MainPage.destinationCity, "Origin city");
		enterText(By.xpath("//input[@placeholder='Destination city or airport']"), city);
		clickOn(By.xpath("//ul[@class='dropdown-items js-dropdown-items active']//span[text()='" + city + "']"), city);
	}

}
