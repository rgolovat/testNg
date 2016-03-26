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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;

import io.appium.java_client.android.AndroidDriver;

public class WebBrowser extends ReportManager {

	private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<AndroidDriver> threadLocalMDriver = new ThreadLocal<AndroidDriver>();

	private WebDriver driver;
	private AndroidDriver mDriver;
	private boolean check;

	@Parameters("browser")
	@BeforeTest
	public void initWebBrowser(@Optional(value = "Firefox") String browser, @Optional(value = "") String device,
			@Optional(value = "") String OSv) {

		if (browser.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
			System.out.println("Firefox has started");
			threadLocalDriver.set(driver);
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/java/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			System.out.println("Chrome has started");
			threadLocalDriver.set(driver);
		} else if (browser.equalsIgnoreCase("MBrowser")) {
			check = true;
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", device);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", OSv);
			capabilities.setCapability("browserName", browser);
			try {
				mDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public static WebDriver Driver() {
		return threadLocalDriver.get();
	}
	
	@BeforeSuite
	public void startAppiumServer() {
		if (check = true) {
			ServerArguments serverArguments = new ServerArguments();
			serverArguments.setArgument("--address", "127.0.0.1");
			serverArguments.setArgument("--port", 4723);
			AppiumServer appiumServer = new AppiumServer(serverArguments);
			appiumServer.startServer();
			System.out.println("Appium server started");
		} else {
			System.out.println("Appium server will not be used");
		}
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