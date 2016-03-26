package utils;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;

public class MainClass extends WebBrowser {

	private static SoftAssert sAssert = new SoftAssert();

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

	public static WebElement getElement(By by) {
		WebDriverWait wait = new WebDriverWait(Driver(), 10);
		WebElement element = null;
		try {
			element = wait.until(ExpectedConditions.visibilityOf(Driver().findElement(by)));
		} catch (NoSuchElementException e) {
			Logger().log(LogStatus.FATAL, "Cannot find elemnt on the page");
			e.printStackTrace();
		}
		return element;
	}

	public static void clickOn(By by, String webElement) {
		Logger().log(LogStatus.INFO, "Trying to click on " + webElement);
		WebElement element = getElement(by);
		if (element.isDisplayed() & element.getSize().getHeight() > 0 & element.getSize().getWidth() > 0) {
			element.click();
			Logger().log(LogStatus.PASS, "Clicked on " + webElement);
		} else {
			Logger().log(LogStatus.FAIL,
					"Cannot click on element: element is not displayed on page or it's dimensions are 0"
							+ Logger().addScreenCapture(Screenshot.take()));
			sAssert.assertTrue(1 == 2);
		}
	}

	public static void enterText(By by, String text) {
		Logger().log(LogStatus.INFO, "Trying to enter text: " + text);
		WebElement element = getElement(by);
		element.clear();
		element.sendKeys(text);
		Logger().log(LogStatus.PASS, "Entered text: " + text);
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
			Logger().log(LogStatus.FAIL, "Cannot switch to tab " + i);
			e.printStackTrace();
		}
		return tab;
	}

	private static List<String> getTabs() {
		List<String> windows = new ArrayList<String>(Driver().getWindowHandles());
		return windows;
	}

	public static void closeTab(int i) {
		switchToTab(i).close();
		Logger().log(LogStatus.PASS, "Tab closed");
	}

	public static String getElementAtt(By by, String attName) {
		return getElement(by).getAttribute(attName);
	}

	public static String getElementText(By by) {
		return getElement(by).getText();
	}

	public static void switchToDefaultFrame() {
		Driver().switchTo().defaultContent();
		Logger().log(LogStatus.PASS, "Switched to default frame");
	}

	public static List<WebElement> getElements(By by) {
		return Driver().findElements(by);
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

	public static boolean isElementDisplayed(By by) {
		WebDriverWait wait = new WebDriverWait(Driver(), 2);
		WebElement element = null;
		boolean b = false;
		try {
			element = wait.until(ExpectedConditions.visibilityOf(Driver().findElement(by)));
			if (element.isDisplayed()) {
				b = true;
			} else {
				b = false;
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return b;
	}

	public static void selectFromDropdownText(By dropDownIdent, String text) {
		Logger().log(LogStatus.INFO, "Trying to select " + text + " from dropdown");
		Select oSelection = new Select(getElement(dropDownIdent));
		oSelection.selectByVisibleText(text);
		Logger().log(LogStatus.PASS, "Selected " + text + " from dropdown");
	}

	public void selectFromDropdownValue(By dropDownIdent, String value) {
		Logger().log(LogStatus.INFO, "Trying to select " + value + " from dropdown");
		Select oSelection = new Select(getElement(dropDownIdent));
		oSelection.selectByValue(value);
		Logger().log(LogStatus.PASS, "Selected " + value + " from dropdown");
	}
	@AfterMethod
	public static void assertAll() {
		sAssert.assertAll();
	}

}