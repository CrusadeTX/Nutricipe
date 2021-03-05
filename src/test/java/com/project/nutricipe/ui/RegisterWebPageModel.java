package com.project.nutricipe.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterWebPageModel {
	private WebDriver driver;
	@FindBy(id="btn-register")
	private WebElement registerBtn;
	@FindBy(id="btn-back")
	private WebElement backBtn;
	@FindBy(id="register-user")
	private WebElement usernameField;
	@FindBy(id="register-email")
	private WebElement emailField;
	@FindBy(id="register-pass")
	private WebElement passwordField;
	@FindBy(id="confirm-register-pass")
	private WebElement repeatPasswordField;
	public RegisterWebPageModel(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void navigateToMain() {
		driver.get(
				"http://localhost:8080/register");
	}
	public void sendKeysToElement(String keysToSend, WebElement element) {
		element.sendKeys(keysToSend);
	}
	public void clickOnElement(WebElement element) {
		element.click();
	}
	public WebElement getRegisterBtn() {
		return registerBtn;
	}
	public WebElement getBackBtn() {
		return backBtn;
	}
	public WebElement getUsernameField() {
		return usernameField;
	}
	public WebElement getEmailField() {
		return emailField;
	}
	public WebElement getPasswordField() {
		return passwordField;
	}
	public WebElement getRepeatPasswordField() {
		return repeatPasswordField;
	}

}
