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

	//public static By searchButton = By.xpath("//a[@class='field-box search-button js-search-button']");

	public static final String sliderTrack = ".js-slider-price";
	public static final String rButton = ".js-slider-price .noUi-handle-lower";
	
	public static final String trackDepart = ".js-slider-depart-time";	
	public static final String lButtonDepart = ".js-slider-depart-time .noUi-handle-lower";
	public static final String rButtonDepart = ".js-slider-depart-time .noUi-handle-upper";
	
	public static final String trackReturn = ".js-slider-return-time";	
	public static final String lButtonReturn = ".js-slider-return-time .noUi-handle-lower";
	public static final String rButtonReturn = ".js-slider-return-time .noUi-handle-upper";
	
	public static final String resultPrice = ".price.js-raise-booking";
	
	public static final String resultDepart = ".points-list.count1 .time";
	
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
	
	public static void enterSearchButton(){
		clickOn(MainPage.searchButton, "");
	}

	public static void movePriceSlider(){
		moveTo(MainPage.sliderTrack, null, 0, MainPage.rButton, 30);
	}
	
	public static void moveDepartSlider(){
		moveTo(MainPage.trackDepart, MainPage.lButtonDepart, 20, MainPage.rButtonDepart, 40);
	}	
	
	public static void moveReturnSlider(){
		moveTo(MainPage.trackReturn, MainPage.lButtonReturn, 30, MainPage.rButtonReturn, 50);
	}
	
	public static void checkPrice(){
		checkPrice(MainPage.resultPrice, MainPage.rButton);
	}
	
	public static void checkDepart(){
		checkDepart(MainPage.resultDepart, MainPage.lButtonDepart);
	}	

}
