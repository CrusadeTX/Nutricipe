package com.project.nutricipe.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInWebPageModel {
	private WebDriver driver;
	@FindBy(id="login")
	private WebElement loginBtn;
	@FindBy(id="register")
	private WebElement registerBtn;
	@FindBy(id="log-in-username")
	private WebElement usernameField;
	@FindBy(id="log-in-password")
	private WebElement passwordField;


	public LogInWebPageModel(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void navigateToMain() {
		driver.get(
				"http://localhost:8080/login");
	}
	public void sendKeysToElement(String keysToSend, WebElement element) {
		element.sendKeys(keysToSend);
	}
	public WebElement getLoginBtn() {
		return this.loginBtn;
	}
	public WebElement getRegisterBtn() {
		return this.registerBtn;
	}
	public WebElement getUsernameField() {
		return this.usernameField;
	}
	public WebElement getPasswordField() {
		return this.passwordField;
	}
	public void clickOnElement(WebElement element) {
		element.click();
	}

}
