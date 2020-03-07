package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_UserSearchView extends PO_View {

	/**
	 * Rellena el campo de búsqueda de usuarios y realiza la búsqueda
	 * 
	 */
	public static void fillField(WebDriver driver, String searchText) {
		WebElement search = driver.findElement(By.id("searchField"));
		search.click();
		search.clear();
		search.sendKeys(searchText);

		By boton = By.id("buscar");
		driver.findElement(boton).click();
	}
}
