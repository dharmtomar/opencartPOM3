package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class AccountPage {
	WebDriver driver;
	ElementUtil eleUtil;

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}
	
	private By logoutButton=By.linkText("Logout");
	private By myAccountLink=By.linkText("My Account");
	private By headers=By.cssSelector("div#content h2");
	private By search=By.name("search");
	private By searchButton=By.cssSelector("div#search button");
	
	@Step("Getting account page title.....")
	public String getAccountPageTitle() {
		String acctitle = eleUtil.waitForTitleIs(Constants.acc_Page_Title_Fraction, 5);
		System.out.println(acctitle);
		return acctitle;
	}
@Step("Getting account page url.....")
	public String getAccountPageURL() {
		String URL = eleUtil.waitForURLContains(Constants.acc_Page_URL_Fraction, 5);
		System.out.println(URL);
		return URL;
	}
@Step("checing logout button exist or not....")
	public boolean LogoutButtonExist() {
		return eleUtil.waitForElementVisible(logoutButton, 5).isDisplayed();
	}
@Step("cheking myaccount link existance...")
	public boolean MyAccountLinkExist() {
		return eleUtil.waitForElementVisible(myAccountLink, 5).isDisplayed();
	}
@Step("Getting headers....")
	public List<String> getHeaders() {
		List<WebElement> headerList=eleUtil.getElements(headers);
		List<String> headList= new ArrayList<String>();
		for(WebElement e:headerList) {
			String header=e.getText();
			headList.add(header);
		}
		return headList;
	}
//@Step("Searching for an item : {0}")
//	public SearchResultPage doSearchItem(String productname) {
//		System.out.println("searching for the product- "+productname);
//		eleUtil.doSendKeys(search, productname);
//		eleUtil.doClick(searchButton);
//		return new SearchResultPage(driver);
//	}
	
}
