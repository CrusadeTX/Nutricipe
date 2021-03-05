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

public class LogInTest {
	WebDriver driver;
	LogInWebPageModel model;
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	@BeforeClass
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}
	@Before
	public void setup() {
		driver = new ChromeDriver();
		
		model = new LogInWebPageModel(driver);
	}
	@Test
	public void loginWithCorrectCredentials() {
		final String expectedURL ="http://localhost:8080/home";
		final String expectedUsernameOnHomePage="fridge";
		model.navigateToMain();
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		model.sendKeysToElement("fridge", model.getUsernameField());
		model.sendKeysToElement("12345", model.getPasswordField());
		model.clickOnElement(model.getLoginBtn());
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedURL));
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement usernameSpan = driver.findElement(By.id("current-user-username"));
		collector.checkThat(usernameSpan.getText(),IsEqual.equalToObject(expectedUsernameOnHomePage));
		
	}
	@Test
	public void loginWithWrongCredentials() {
		String expectedMessage = "Username or Password are incorrect";
		String expectedURL = "http://localhost:8080/login?error";
		model.navigateToMain();
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		model.sendKeysToElement("fridge", model.getUsernameField());
		model.sendKeysToElement("123456", model.getPasswordField());
		model.clickOnElement(model.getLoginBtn());
		WebElement incorrectDataMessage = driver.findElement(By.id("incorrect-data-error"));
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedURL));
		collector.checkThat(incorrectDataMessage.getText(),IsEqual.equalToObject(expectedMessage));
	}
	@Test
	public void loadRegisterPage() {
		String expectedURL = "http://localhost:8080/register";
		model.navigateToMain();
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		model.clickOnElement(model.getRegisterBtn());
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedURL));
		
		
	}
	@After
	public void after() {
		driver.close();
	}
}
