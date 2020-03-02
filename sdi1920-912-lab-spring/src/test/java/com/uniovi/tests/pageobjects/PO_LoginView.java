package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView {

	/**
	 * Rellena el formulario de login con los parámetros especificados y envía el
	 * formulario
	 */
	public static void fillForm(WebDriver driver, String emailp, String passwordp) {
		WebElement email = driver.findElement(By.id("emailField"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement password = driver.findElement(By.id("passwordField"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);

		By boton = By.id("btnSubmitLogin");
		driver.findElement(boton).click();
	}

}
