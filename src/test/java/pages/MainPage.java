package pages;

import utils.MainClass;

public class MainPage extends MainClass {
	
	public static final String originCity = "//div[@class='js-originplace field-box flex']/div";
	public static final String originCity1 = "//input[@placeholder='Origin city or airport']";
	public static final String originCity2 = "//ul[@class='dropdown-items js-dropdown-items active']//span[text()='%s']";
	public static final String destinationCity = "//div[@class='js-destinationplace field-box flex']/div";
	public static final String destinationCity1 = "//input[@placeholder='Destination city or airport']";
	public static final String destinationCity2 = "//ul[@class='dropdown-items js-dropdown-items active']//span[text()='%s']";
	public static final String depart = "//button[@data-label='Depart']";
	public static final String DAY = "//table[@id='date-depart_table']//div[text()='%s']";
	public static final String monthYear = "//div[@id='date-depart_root']//span[text()='%s']";
    public static final String NumOfAdults = "//div[@title='Adults (12+ years)']";
	public static final String numOfAdults = "//div[@title='Adults (12+ years)']//a[text()='%s']";
	public static final String NumOfChildren = "//div[@title='Children (under 12)']";
	public static final String numOfChildren = "//div[@title='Children (under 12)']//a[text()='%s']";
	public static final String NumOfInfants = "//div[@title='Infants (under 2)']";
	public static final String numOfInfants = "//div[@title='Infants (under 2)']//a[text()='%s']";
	public static final String flightClass = "//button[@aria-label='Cabin Class']";
	public static final String FlightClass = "//div[@class='field-box js-cabin-class-selector']//a[text()='%s']";
	public static final String carHireLink = "//span[text()='Car Hire']";
	public static final String searchButton = "//a[@aria-label='Search']";
	
	public static void enterOriginCity(String city) {
		clickOn(originCity, "Origin city");
		enterText(originCity1, city);
		clickOn(originCity2, city);
	}
	
	public static void enterDestinationCity(String city){
		clickOn(destinationCity, "Origin city");
		enterText(destinationCity1, city);
		clickOn(destinationCity2, city);
	}
	
	
	public static void selectDepart(String year, String month, String day){
		clickOn(MainPage.depart, "Depart");
		clickOn(monthYear, month + " " + year);
		clickOn(DAY, day);		
	}
	
	public static void selectNumOfAdults(String num){
		clickOn(NumOfAdults, "Number of Adults");
		clickOn(numOfAdults, num);
	}
	
	public static void selectNumOfChildren(String num){
		clickOn(NumOfChildren, "Number of Children");
		clickOn(numOfChildren, num);
	}
	
	public static void selectNumOfInfants(String num){
		clickOn(NumOfInfants, "Number of Children");
		clickOn(numOfInfants, num);
	}
	
	public static void selectFlightClass(String fClass){
		clickOn(flightClass, "Flight class");
		clickOn(FlightClass, fClass);
	}
	
	

}
