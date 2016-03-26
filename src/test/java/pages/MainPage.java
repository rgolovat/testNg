package pages;

import org.openqa.selenium.By;
import utils.MainClass;

public class MainPage extends MainClass {
	
	public static By originCity = By.xpath("//div[@class='js-originplace field-box flex']/div");
	public static By originCity1 = By.xpath("//input[@placeholder='Origin city or airport']");
	public static final String originCity2 = "//ul[@class='dropdown-items js-dropdown-items active']//span[text()='%s']";
	public static By destinationCity = By.xpath("//div[@class='js-destinationplace field-box flex']/div");
	public static By destinationCity1 = By.xpath("//input[@placeholder='Destination city or airport']");
	public static final String destinationCity2 = "//ul[@class='dropdown-items js-dropdown-items active']//span[text()='%s']";
	public static By depart = By.xpath("//button[@data-label='Depart']");
	public static final String DAY = "//table[@id='date-depart_table']//div[text()='%s']";
	public static final String monthYear = "//div[@id='date-depart_root']//span[text()='%s']";
    public static By NumOfAdults = By.xpath("//div[@title='Adults (12+ years)']");
	public static final String numOfAdults = "//div[@title='Adults (12+ years)']//a[text()='%s']";
	
	public static void enterOriginCity(String city) {
		clickOn(originCity, "Origin city");
		enterText(originCity1, city);
		clickOn(By.xpath(String.format(originCity2, city)), city);
	}
	
	public static void enterDestinationCity(String city){
		clickOn(destinationCity, "Origin city");
		enterText(destinationCity1, city);
		clickOn(By.xpath(String.format(destinationCity2, city)), city);
	}
	
	
	public static void selectDepart(String year, String month, String day){
		clickOn(MainPage.depart, "Depart");
		clickOn(By.xpath(String.format(monthYear, month + " " + year)), month + " " + year);
		clickOn(By.xpath(String.format(DAY, day)), day);		
	}
	
	public static void selectNumOfAdults(String num){
		clickOn(NumOfAdults, "Number of Adults");
		clickOn(By.xpath(String.format(numOfAdults, num)), num);
	} 
	

}
