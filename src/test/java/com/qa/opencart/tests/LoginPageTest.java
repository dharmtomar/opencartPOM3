package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.basepage.BaseTest;
import com.qa.opencart.errors.AppErrors;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginPageTest extends BaseTest{

	@Description("checking the login  page title---")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String title = login.getLoginPageTitle();
		Assert.assertEquals(title, Constants.login_Page_Title_Fraction, AppErrors.APP_TITLE_NOT_FOUND);
	}

	@Description("checking the login page URL---")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 2)
	public void loginPageURLTest() {
		String URL = login.getLoginPageURL();
		Assert.assertEquals(URL.contains(Constants.login_Page_URL_Fraction), true, AppErrors.APP_URL_NOT_FOUND);
	}

	@Description("checking user is able to login---")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 3)
	public void loginTest() {
		accPage = login.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountPageTitle(), Constants.acc_Page_Title_Fraction,
				AppErrors.APP_TITLE_NOT_FOUND);
	}
}
