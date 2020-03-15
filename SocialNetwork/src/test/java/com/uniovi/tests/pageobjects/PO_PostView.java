package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_PostView extends PO_View{
	
	/**
	 * Te lleva directamente al listado de posts utilizando el menú superior 
	 * 
	 */
	public static void goToPostListing(WebDriver driver) {

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'posts-menu')]/a");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'post/list')]");
		elementos.get(0).click();
	}

}
