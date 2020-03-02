package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RegisterView extends PO_View {

	/**
	 * Rellena el formulario de registro con los parámetros especificados y envía el
	 * formulario
	 */
	static public void fillForm(WebDriver driver, String emailp, String namep, String lastNamep, String passwordp,
			String passwordConfp) {
		WebElement email = driver.findElement(By.id("emailField"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement name = driver.findElement(By.id("nameField"));
		name.click();
		name.clear();
		name.sendKeys(namep);
		WebElement lastName = driver.findElement(By.id("lastNameField"));
		lastName.click();
		lastName.clear();
		lastName.sendKeys(lastNamep);
		WebElement password = driver.findElement(By.id("passwordField"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		WebElement passwordConfirm = driver.findElement(By.id("passwordConfirmField"));
		passwordConfirm.click();
		passwordConfirm.clear();
		passwordConfirm.sendKeys(passwordConfp);

		By boton = By.id("btnSubmitRegister");
		driver.findElement(boton).click();
	}
}
