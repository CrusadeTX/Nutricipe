package com.project.nutricipe.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomeWebPageModel {
	private WebDriver driver;
	@FindBy(id="logout-link")
	private WebElement logoutBtn;
	@FindBy(id="recipe-card-2")
	private WebElement lasagna;
	@FindBy(id="recipe-card-1")
	private WebElement tomatoSalad;
	@FindBy(id="product-card-2")
	private WebElement milk;
	@FindBy(id="product-card-1")
	private WebElement tomato;
	@FindBy(id="product-card-3")
	private WebElement cheese;
	
	public HomeWebPageModel(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void navigateToMain() {
		driver.get(
				"http://localhost:8080/home");	
		
	}
	public void clickOnElement(WebElement element) {
		element.click();
	}
	public WebElement getMoreButton(WebElement element) {
		return element.findElement(By.className("btn-primary"));
	}

	public WebElement getLogoutBtn() {
		return logoutBtn;
	}

	public WebElement getLasagna() {
		return lasagna;
	}

	public WebElement getTomatoSalad() {
		return tomatoSalad;
	}

	public WebElement getMilk() {
		return milk;
	}

	public WebElement getTomato() {
		return tomato;
	}

	public WebElement getCheese() {
		return cheese;
	}
	
	

}
