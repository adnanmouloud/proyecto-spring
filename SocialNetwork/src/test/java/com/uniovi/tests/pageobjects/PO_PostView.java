package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.utils.SeleniumUtils;

public class PO_PostView extends PO_View {

	/**
	 * Te lleva directamente al listado de posts utilizando el menú superior
	 */
	public static void goToPostListing(WebDriver driver) {

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'posts-menu')]/a");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/list')]");
		elementos.get(0).click();
	}

	/**
	 * Te lleva directamente a la opciçon de crear un nuevo post utilizando el menú
	 * superior
	 */
	public static void goToNewPost(WebDriver driver) {

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'posts-menu')]/a");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/add')]");
		elementos.get(0).click();
	}

	/**
	 * Rellena el formulario de nuevo post con los parámetros especificados y envía
	 * el formulario
	 */
	public static void fillForm(WebDriver driver, String titlep, String textp) {
		WebElement email = driver.findElement(By.id("titleField"));
		email.click();
		email.clear();
		email.sendKeys(titlep);
		WebElement password = driver.findElement(By.id("textField"));
		password.click();
		password.clear();
		password.sendKeys(textp);

		By boton = By.id("btnSubmitNewPost");
		driver.findElement(boton).click();
	}

	/**
	 * Cuenta el número de posts en la vista
	 */
	public static int countPostNumber(WebDriver driver) {
		List<WebElement> elements = SeleniumUtils.EsperaCargaPagina(driver, "id", "userPost", PO_View.getTimeout());

		return elements.size();
	}
}
