package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.errors.AppErrors;
import com.qa.opencart.exceptions.BrowserExceptions;
import com.qa.opencart.exceptions.FrameworkExceptions;
import com.qa.opencart.logger.Log;

import io.qameta.allure.Step;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> thLocal = new ThreadLocal<WebDriver>();

	@Step("Initialyzing Driver with ThreadLocal.........")
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		// System.out.pri ntln("Launching the browser-" + browserName);
		Log.info("Launching the browser-  " + browserName);
		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("chrome");
			}
			else {
			thLocal.set(new ChromeDriver(optionsManager.chromeOptions()));
			}
			break;
		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox");
			}
			else {
			thLocal.set(new FirefoxDriver(optionsManager.firefoxOptions()));
			}
			break;
		case "edge":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("msedge");
			}
			else {
			thLocal.set(new EdgeDriver(optionsManager.edgeOptions()));
			}
			break;

		default:
			// System.out.println("Browsername is incorrect, please pass corect one -- " +
			// browserName);
			Log.error("Browsername is incorrect, please pass corect one-- " + browserName);
			throw new BrowserExceptions("No BROWSER FOUND" + browserName);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}

	private void init_remoteDriver(String browserName) {
		Log.info("Running tests on Remote Grid on browser : " + browserName);
		try {
			switch (browserName.toLowerCase().trim()) {
			case "chrome":

				thLocal.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.chromeOptions()));
				break;
			case "firefox":
				thLocal.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.firefoxOptions()));
				break;
			case "msedge":
				thLocal.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.edgeOptions()));
				break;
			default:
				Log.info("plz pass thr right supported browser on GRID....");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {
		return thLocal.get();
	}

	@Step("property initialyzing.... ")
	public Properties initProp() {

		FileInputStream fis = null;
		prop = new Properties();
		String envName = System.getProperty("env");
		System.out.println("Running tests on env- " + envName);
		try {
			if (envName == null) {
				System.out.println("no env passed so running test on QA env");
				fis = new FileInputStream(new File("./src/test/resources/config/config.qa.properties"));
			} else {
				switch (envName.toLowerCase().trim()) {
				case "dev":
					fis = new FileInputStream(new File("./src/test/resources/config/config.dev.properties"));
					break;
				case "qa":
					fis = new FileInputStream(new File("./src/test/resources/config/config.qa.properties"));
					break;
				case "stage":
					fis = new FileInputStream(new File("./src/test/resources/config/config.stage.properties"));
					break;
				case "uat":
					fis = new FileInputStream(new File("./src/test/resources/config/config.uat.properties"));
					break;
				case "prod":
					fis = new FileInputStream(new File("./src/test/resources/config/config.prod.properties"));
					break;
				default:
					System.out.println("Env Name is not correct, please pass correct one- " + envName);
					throw new FrameworkExceptions(AppErrors.ENV_NOT_FOUND);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
	}

	@Step("Getting screenshot.....")
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp directory
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
	
}
