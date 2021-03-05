package com.project.nutricipe.ui;

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
import org.openqa.selenium.support.FindBy;

public class HomeTest {
	WebDriver driver;
	LogInWebPageModel loginModel;
	HomeWebPageModel homeModel;
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	@BeforeClass
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}
	@Before
	public void setup() {
		driver = new ChromeDriver();
		loginModel = new LogInWebPageModel(driver);
		homeModel = new HomeWebPageModel(driver);
		loginModel.navigateToMain();
		loginModel.sendKeysToElement("fridge", loginModel.getUsernameField());
		loginModel.sendKeysToElement("12345", loginModel.getPasswordField());
		loginModel.clickOnElement(loginModel.getLoginBtn());
	}
	@Test
	public void viewLasagnaPage() {
		String expectedUrl = "http://localhost:8080/recipe.html";
		String expectedHeadingText="Lasagna";
		WebElement moreBtn = homeModel.getMoreButton(homeModel.getLasagna());
		homeModel.clickOnElement(moreBtn);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedUrl));
		WebElement heading = driver.findElement(By.id("heading"));
		collector.checkThat(heading.getText(),IsEqual.equalToObject(expectedHeadingText));
	}
	@Test
	public void viewTomatoSaladPage() {
		String expectedUrl = "http://localhost:8080/recipe.html";
		String expectedHeadingText="Tomato Salad";
		WebElement moreBtn = homeModel.getMoreButton(homeModel.getTomatoSalad());
		homeModel.clickOnElement(moreBtn);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedUrl));
		WebElement heading = driver.findElement(By.id("heading"));
		collector.checkThat(heading.getText(),IsEqual.equalToObject(expectedHeadingText));
	}
	@Test
	public void viewTomatoPage() {
		String expectedUrl = "http://localhost:8080/product.html";
		String expectedHeadingText="Tomato";
		WebElement moreBtn = homeModel.getMoreButton(homeModel.getTomato());
		homeModel.clickOnElement(moreBtn);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedUrl));
		WebElement heading = driver.findElement(By.id("heading"));
		collector.checkThat(heading.getText(),IsEqual.equalToObject(expectedHeadingText));
	}
	@Test
	public void viewMilkPage() {
		String expectedUrl = "http://localhost:8080/product.html";
		String expectedHeadingText="Milk";
		WebElement moreBtn = homeModel.getMoreButton(homeModel.getMilk());
		homeModel.clickOnElement(moreBtn);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedUrl));
		WebElement heading = driver.findElement(By.id("heading"));
		collector.checkThat(heading.getText(),IsEqual.equalToObject(expectedHeadingText));
	}
	@Test
	public void viewCheesePage() {
		String expectedUrl = "http://localhost:8080/product.html";
		String expectedHeadingText="Cheese";
		WebElement moreBtn = homeModel.getMoreButton(homeModel.getCheese());
		homeModel.clickOnElement(moreBtn);
		collector.checkThat(driver.getCurrentUrl(),IsEqual.equalToObject(expectedUrl));
		WebElement heading = driver.findElement(By.id("heading"));
		collector.checkThat(heading.getText(),IsEqual.equalToObject(expectedHeadingText));
	}
	@After
	public void after() {
		homeModel.navigateToMain();
		homeModel.clickOnElement(homeModel.getLogoutBtn());
		driver.close();
	}
}


