package com.uniovi.tests.pageobjects;

import java.util.List;

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

	/**
	 * Te lleva directamente al listado de usuarios utilizando el menú superior 
	 * 
	 */
	public static void goToUserListing(WebDriver driver) {

		// Pinchamos en la opción de menu de Usuarios: //li[contains(@id, //
		// 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		// Pinchamos en "Ver Usuarios".
		elementos.get(0).click();
	}
}
