package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;

import io.appium.java_client.android.AndroidDriver;

public class WebBrowser extends ReportManager {

	private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<AndroidDriver> threadLocalMDriver = new ThreadLocal<AndroidDriver>();
    private static ThreadLocal<String> threadLocalBrowserName = new ThreadLocal<String>();
    public String browserName;
	private WebDriver driver;
	private AndroidDriver mDriver;

	@Parameters({ "browser", "device", "OSv" })
	@BeforeTest
	public void initWebBrowser(@Optional(value = "Firefox") String browser, @Optional(value = "") String device,
			@Optional(value = "") String OSv) {
		browserName = browser;
		if (browser.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
			System.out.println("Firefox has started");
			threadLocalDriver.set(driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/java/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			System.out.println("Chrome has started");
			threadLocalDriver.set(driver);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("MBrowser")) {
			ServerArguments serverArguments = new ServerArguments();
			serverArguments.setArgument("--address", "127.0.0.1");
			serverArguments.setArgument("--port", 4723);
			AppiumServer appiumServer = new AppiumServer(serverArguments);
			appiumServer.startServer();
			System.out.println("Appium server started");

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", device);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", OSv);
			capabilities.setCapability("browserName", "Chrome");
			try {
				mDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			browserName = "Mobile " + browserName;
			threadLocalDriver.set(mDriver);
			mDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		threadLocalBrowserName.set(browserName);
	}

	public static WebDriver Driver() {
		return threadLocalDriver.get();
	}
	
	public static String getCurrentBrowserName(){
		return threadLocalBrowserName.get();
	}
	

	@AfterTest(alwaysRun = true)
	public void closeWebBrowser() {
		if (driver != null) {
			driver.quit();
			threadLocalDriver.remove();
			System.out.println("Browser closed");
		}
	}

	@AfterSuite(alwaysRun = true)
	public void flush() {
		closeReporter();
	}
}