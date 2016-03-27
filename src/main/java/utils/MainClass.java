package utils;

import java.util.ArrayList;
import java.util.List;
import javax.naming.directory.NoSuchAttributeException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;

public class MainClass extends WebBrowser {

	private static SoftAssert sAssert = new SoftAssert();

	private static By getBy(String el, String args) {
		By element = null;
		if (el.startsWith("//")) {
			element = By.xpath(String.format(el, args));
		} else if (el.startsWith(".")) {
			element = By.cssSelector(el);
		} else {
			element = By.id(el);
		}
		return element;
	}


	public static void getPage(String address) {
		Logger().log(LogStatus.INFO, "Trying to redirect to " + address);
		Driver().get(address);
		String title = Driver().getTitle();
		if (title.contains("is not available") || title.contains("Problem loading page")) {
			Logger().log(LogStatus.FATAL, title + Logger().addScreenCapture(Screenshot.take()));
		} else {
			Logger().log(LogStatus.PASS, "Redirected to: " + address);
		}
	}

	public static String getCurrUrl() {
		String currAddress = Driver().getCurrentUrl();
		String title = Driver().getTitle();
		if (title.contains("is not available") || title.contains("Problem loading page")) {
			Logger().log(LogStatus.FATAL, title + Logger().addScreenCapture(Screenshot.take()));
		}
		return currAddress;
	}

	public static WebElement getElement(String el) {
		WebElement element = null;
		try {
			element = getElement(el, "");
		} catch (NoSuchElementException e) {

		} catch (ElementNotVisibleException e1) {

		} catch (InvalidElementStateException e2) {

		} catch (WebDriverException e3){
			
		}
		return element;
	}

	public static WebElement getElement(String el, String args) {
		WebDriverWait wait = new WebDriverWait(Driver(), 30);
		WebElement element = null;
		try {
			element = wait.until(ExpectedConditions.visibilityOf(Driver().findElement(getBy(el, args))));
		} catch (NoSuchElementException e) {
			Logger().log(LogStatus.FATAL,
					"Cannot find Element on the page" + Logger().addScreenCapture(Screenshot.take()));
			e.printStackTrace();
		} catch (ElementNotVisibleException e1) {
			Logger().log(LogStatus.FATAL,
					"Element is not visible on the page" + Logger().addScreenCapture(Screenshot.take()));
			e1.printStackTrace();
		} catch (InvalidElementStateException e2) {
			Logger().log(LogStatus.FATAL, "" + e2.getMessage() + Logger().addScreenCapture(Screenshot.take()));
			e2.printStackTrace();
		} catch (WebDriverException e3){
			Logger().log(LogStatus.FATAL, "" + e3.getMessage());
			e3.printStackTrace();
		}
		return element;
	}

	public static void clickOn(String el, String webElement) {
		WebDriverWait wait = new WebDriverWait(Driver(), 30);
		Logger().log(LogStatus.INFO, "Trying to click on " + webElement);
		try {
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(getElement(el, webElement)));
			element = getElement(el, webElement);
			element.click();
			Logger().log(LogStatus.PASS, "Clicked on " + webElement);
		} catch (NoSuchElementException e1) {
		} catch (ElementNotVisibleException e2) {
		} catch (InvalidElementStateException e2) {
		} catch (WebDriverException e3){			
		}
	}

	public void clickOn(String el) {
		clickOn(el, "");
	}

	public static void moveTo(String tr, String lb, int lprocent, String rb, int rprocent) {
		WebDriverWait wait = new WebDriverWait(Driver(), 100);
		Logger().log(LogStatus.INFO, "Trying to moved " + rb + " on track " + tr);
		
		if (By.cssSelector(tr) != null) {
			WebElement track = wait.until(ExpectedConditions.elementToBeClickable(getElement(tr)));
			int width = track.getSize().getWidth();
		
			if (lb != null) {
				WebElement lbutton = wait.until(ExpectedConditions.elementToBeClickable(getElement(lb)));
				if (lbutton.isDisplayed()) {
					new Actions(Driver())
								.dragAndDropBy(lbutton, width / 100 * (lprocent), 0)
								.build()
				                .perform();
					
					Logger().log(LogStatus.PASS, "Slider moved " + lb);
				} else {
					Logger().log(LogStatus.FAIL,
							"Cannot moved on element: element is not displayed on page or it's dimensions are 0"
									+ Logger().addScreenCapture(Screenshot.take()));
					sAssert.assertTrue(1 == 2);
				}		
			}
			if (rb != null) {
				WebElement rbutton = wait.until(ExpectedConditions.elementToBeClickable(getElement(rb)));
				if (rbutton.isDisplayed()) {
					new Actions(Driver())
								.dragAndDropBy(rbutton, width / 100 * (-rprocent), 0)
								.build()
				                .perform();
					
					Logger().log(LogStatus.PASS, "Slider moved " + rb);
				} else {
					Logger().log(LogStatus.FAIL,
							"Cannot moved on element: element is not displayed on page or it's dimensions are 0"
									+ Logger().addScreenCapture(Screenshot.take()));
					sAssert.assertTrue(1 == 2);
				}
			}
		}
	}
	
	public static void checkPrice(String resultPrice, String searchPrice) {
		WebDriverWait wait = new WebDriverWait(Driver(), 100);
		
		WebElement rbutton = wait.until(ExpectedConditions.elementToBeClickable(getElement(searchPrice)));
		int sPrice = Integer.parseInt((rbutton.getAttribute("aria-valuetext").replaceAll("[£,]", "")));
		List<WebElement> elements = Driver().findElements(By.cssSelector(resultPrice));
		System.out.println("Number of elements: " + elements.size());
		for(WebElement ele : elements){
			int rPrice = Integer.parseInt(ele.getText().replace("£", ""));
			if (rPrice <= sPrice) {
				System.out.println("result - Ok: price " + rPrice + " <= " + sPrice);
			} else {
				System.out.println("result - Error: price " + rPrice + " <= " + sPrice);
			}
		}
	}
	
	private static int timeToInt(String time) {
	    String[] hourMin = time.split(":");
	    int hour = Integer.parseInt(hourMin[0]);
	    int mins = Integer.parseInt(hourMin[1]);
	    int hoursInMins = hour * 60;
	    return hoursInMins + mins;
	}
	
	public static void checkDepart(String resultTime, String searchTime) {
		WebDriverWait wait = new WebDriverWait(Driver(), 100);
		
		WebElement rbutton = wait.until(ExpectedConditions.elementToBeClickable(getElement(searchTime)));
		String[] sTime = rbutton.getAttribute("aria-valuetext").split(" - ");
		int startTime = timeToInt(sTime[0]);
		int endTime = timeToInt(sTime[1]);
		
		List<WebElement> elements = Driver().findElements(By.cssSelector(resultTime));
		System.out.println("Number of elements: " + elements.size());
		for(WebElement ele : elements){
			//String[] rTime = ele.getAttribute("aria-valuetext").split(" - ");
			
			int rTime = timeToInt(ele.getText());
			
			if (rTime >= startTime && rTime <= endTime) {
				System.out.println("result - Ok: depart " + rTime + " after " + startTime + " before " + endTime);
			} else {
				System.out.println("result - Error: depart " + rTime + " after " + startTime + " before " + endTime);
			}
			
		}
	}	
	
	public static void enterText(String el, String args, String text) {
		Logger().log(LogStatus.INFO, "Trying to enter text: " + text);
		try {
			WebElement element = getElement(el, args);
			element.clear();
			element.sendKeys(text);
			Logger().log(LogStatus.PASS, "Entered text: " + text);
		} catch (NoSuchElementException e1) {
		} catch (ElementNotVisibleException e2) {
		} catch (InvalidElementStateException e2) {
		} catch (WebDriverException e3){			
		}

	}

	public static void enterText(String el, String text) {
		try {
			enterText(el, "", text);
		} catch (NoSuchElementException e) {

		} catch (ElementNotVisibleException e1) {

		} catch (InvalidElementStateException e2) {

		} catch (WebDriverException e3){			
		}

	}

	public static void switchToFrame(String frameId) {
		try {
			Driver().switchTo().frame(frameId);
		} catch (NoSuchFrameException e) {
			Logger().log(LogStatus.FATAL, "Cannot find frame: " + frameId);
			e.printStackTrace();
		}
	}

	public static void sleepFor(int msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver switchToTab(int i) {
		Logger().log(LogStatus.INFO, "Trying to switch to tab " + i);
		WebDriver tab = null;
		try {
			tab = Driver().switchTo().window(getTabs().get(i));
			Logger().log(LogStatus.PASS, "Switched to tab " + tab.getTitle());
		} catch (NoSuchWindowException e) {
			Logger().log(LogStatus.FAIL, "No such window or tab present: " + i);
			e.printStackTrace();
		}
		return tab;
	}

	private static List<String> getTabs() {
		List<String> windows = new ArrayList<String>(Driver().getWindowHandles());
		return windows;
	}

	public static void closeTab(int i) {
		try {
			switchToTab(i).close();
			Logger().log(LogStatus.PASS, "Tab closed");
		} catch (NoSuchWindowException e) {
		}

	}

	public static String getElementAtt(String el, String args, String attName) throws NoSuchAttributeException {
		return getElement(el).getAttribute(attName);

	}

	public static String getElementAtt(String el, String attName) throws NoSuchAttributeException {
		return getElementAtt(el, "", attName);
	}

	public static String getElementText(String el, String args) {
		String text = null;
		try {
			text = getElement(el).getText();
		} catch (NoSuchElementException e) {		
		} catch (ElementNotVisibleException e3){			
		} catch (WebDriverException e2){
		}
		return text;
	}

	public static String getElementText(String el) {
		return getElement(el, "").getText();
	}

	public static void switchToDefaultFrame() {
		try {
			Driver().switchTo().defaultContent();
			Logger().log(LogStatus.PASS, "Switched to default frame");
		} catch (NoSuchFrameException e) {
			Logger().log(LogStatus.FATAL, "No defalt frame was found");
		}

	}

	public static List<WebElement> getElements(String el, String args) {
		return Driver().findElements(getBy(el, args));
	}

	public static List<WebElement> getElements(String el) {
		return Driver().findElements(getBy(el, ""));
	}

	public static void assertEquals(String beforeMess, Object actual, Object expected) {
		Logger().log(LogStatus.INFO, beforeMess);
		if (actual.equals(expected)) {
			Logger().log(LogStatus.PASS, "Objects match");
		} else {
			Logger().log(LogStatus.FAIL, "Expected: '" + expected + "' Actual: '" + actual + "'"
					+ Logger().addScreenCapture(Screenshot.take()));
			sAssert.assertEquals(actual, expected);
		}
	}

	public static void assertTrue(String beforeMess, boolean actual) {
		Logger().log(LogStatus.INFO, beforeMess);
		if (actual) {
			Logger().log(LogStatus.PASS, "True");
		} else {
			Logger().log(LogStatus.FAIL, "Expected: true, but false" + Logger().addScreenCapture(Screenshot.take()));
			sAssert.assertTrue(actual);
		}
	}

	public static boolean isElementDisplayed(String el, String args) {
		WebDriverWait wait = new WebDriverWait(Driver(), 2);
		WebElement element = null;
		boolean b = false;
		try {
			element = wait.until(ExpectedConditions.visibilityOf(Driver().findElement(getBy(el, args))));
			if (element.isDisplayed()) {
				b = true;
			} else {
				b = false;
			}
		} catch (NoSuchElementException e) {
		}
		return b;
	}

	public static boolean isElementDisplayed(String el) {
		return isElementDisplayed(el, "");
	}

	public static void selectFromDropdownText(String dropDownIdent, String args, String text) {
		Logger().log(LogStatus.INFO, "Trying to select " + text + " from dropdown");
		try {
			Select oSelection = new Select(getElement(dropDownIdent, args));
			oSelection.selectByVisibleText(text);
			Logger().log(LogStatus.PASS, "Selected " + text + " from dropdown");
		} catch (NoSuchElementException e) {

		}
	}

	public static void selectFromDropdownText(String dropDownIdent, String text) {
		selectFromDropdownText(dropDownIdent, "", text);
	}

	public void selectFromDropdownValue(String dropDownIdent, String args, String value) {
		Logger().log(LogStatus.INFO, "Trying to select " + value + " from dropdown");
		try {
			Select oSelection = new Select(getElement(dropDownIdent));
			oSelection.selectByValue(value);
			Logger().log(LogStatus.PASS, "Selected " + value + " from dropdown");
		} catch (NoSuchElementException e) {
		} catch (ElementNotVisibleException e1) {
		}

	}

	public void selectFromDropdownValue(String dropDownIdent, String value) {
		selectFromDropdownValue(dropDownIdent, "", value);
	}

	@AfterMethod
	public static void assertAll() {
		sAssert.assertAll();
	}

}