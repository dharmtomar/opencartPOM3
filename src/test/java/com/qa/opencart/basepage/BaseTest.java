package com.qa.opencart.basepage;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.factory.OptionsManager;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.RegistrationPage;

import io.qameta.allure.Step;

public class BaseTest {
//---------------
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected LoginPage login;
	protected RegistrationPage RegisterPage;
	protected OptionsManager ops;
	protected SoftAssert softAssert;
	protected AccountPage accPage;
	@Step("launching the browser : {0}")
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browsername) {
		
		df=new DriverFactory();
		prop=df.initProp();
		
		if(browsername!=null) {
		prop.setProperty("browser", browsername);
		}
			
		driver=df.initDriver(prop);
		
		login=new LoginPage(driver);
		softAssert=new SoftAssert();
	}
	
	@Step("closing the browser.....")
	@AfterTest
	public void teardown() {
		driver.quit();
	}
}
