package com.project.nutricipe.ui;

import java.util.concurrent.TimeUnit;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RegisterTest {
	WebDriver driver;
	RegisterWebPageModel model;
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	@BeforeClass
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}
	@Before
	public void setup() {
		driver = new ChromeDriver();
		
		model = new RegisterWebPageModel(driver);
	}
	@Test
	public void registerWithEmptyFields() {
		final String expectedURL ="http://localhost:8080/register";
		model.navigateToMain();
		model.clickOnElement(model.getRegisterBtn());
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedURL));
	}
	@Test
	public void registerWithTakenUsername() {
		String expectedMessage = "A user with the specified username exists!";
		String expectedURL ="http://localhost:8080/register";
		model.navigateToMain();
		model.sendKeysToElement("fridge", model.getUsernameField());
		model.sendKeysToElement("email@email.com", model.getEmailField());
		model.sendKeysToElement("12345", model.getPasswordField());
		model.sendKeysToElement("12345", model.getRepeatPasswordField());
		model.clickOnElement(model.getRegisterBtn());
		WebElement incorrectDataMessage = driver.findElement(By.id("taken-username-error"));
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedURL));
		collector.checkThat(incorrectDataMessage.getText(),IsEqual.equalToObject(expectedMessage));
	}
	@Test
	public void registerWithTakenEmail() {
		String expectedMessage = "A user with the specified email address exists!";
		String expectedURL ="http://localhost:8080/register";
		model.navigateToMain();
		model.sendKeysToElement("thisisnttaken", model.getUsernameField());
		model.sendKeysToElement("angel@abv.bg", model.getEmailField());
		model.sendKeysToElement("12345", model.getPasswordField());
		model.sendKeysToElement("12345", model.getRepeatPasswordField());
		model.clickOnElement(model.getRegisterBtn());
		WebElement incorrectDataMessage = driver.findElement(By.id("taken-email-error"));
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedURL));
		collector.checkThat(incorrectDataMessage.getText(),IsEqual.equalToObject(expectedMessage));
	}
	@Test
	public void pressOnBack() {
		String expectedURL = "http://localhost:8080/login";
		model.navigateToMain();
		model.clickOnElement(model.getBackBtn());
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		
	}
	@After
	public void after() {
		driver.close();
	}

}
