package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_FriendshipView extends PO_View {

	/**
	 * Te lleva directamente al listado de peticiones de amistad utilizando el menú superior 
	 * 
	 */
	public static void goToInvitationsListing(WebDriver driver) {

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'invitations-menu')]/a");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friendship/list')]");
		elementos.get(0).click();
	}
	
	/**
	 * Te lleva directamente al listado de amigos utilizando el menú superior 
	 * 
	 */
	public static void goToFriendsListing(WebDriver driver) {

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'friends-menu')]/a");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friend/list')]");
		elementos.get(0).click();
	}
}
