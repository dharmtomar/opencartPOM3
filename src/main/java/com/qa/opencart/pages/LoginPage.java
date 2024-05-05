package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	// 1- private locators
	private By login_emailid = By.id("input-email");
	private By login_pass = By.id("input-password");
	private By forgot_password_link = By.linkText("Forgotten Password");
	private By login_button = By.xpath("//input[@value='Login']");
	private By regLink=By.linkText("Register");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}
	// 3- page methods
		@Step("checking login page title.....")
		public String getLoginPageTitle() {
			String title = eleUtil.waitForTitleIs(Constants.login_Page_Title_Fraction, 5);
			//System.out.println(title);
			Log.info("Login page title : "+title);
			return title;
		}

		@Step("checking login page url......")
		public String getLoginPageURL() {
			String URL = eleUtil.waitForURLContains(Constants.login_Page_URL_Fraction, 5);
			//System.out.println(URL);
			Log.info("Login page URL : "+URL);
			return URL;
		}

		@Step("checking presence of forgot password link...")
		public boolean isForgotPasswordExist() {
			return eleUtil.isElementExist(forgot_password_link);
		}

		@Step("opencart do login.........with username : {0} and password : {1}")
		public AccountPage doLogin(String username, String pwd) {
			//System.out.println("User cred are- " + username + " " + pwd);
			//Log.info("User cred are- " + username + " " + pwd);
			eleUtil.waitForElementVisible(login_emailid, 10).sendKeys(username);
			eleUtil.doSendKeys(login_pass, pwd);
			eleUtil.doClick(login_button);
			//return eleUtil.waitForTitleIs("My Account", 10);
			return new AccountPage(driver);
		}
		@Step("registration page navigation.......")
		public RegistrationPage navigateToRegisterPage() {
			eleUtil.doClick(regLink);
			return new RegistrationPage(driver);
		}

}
