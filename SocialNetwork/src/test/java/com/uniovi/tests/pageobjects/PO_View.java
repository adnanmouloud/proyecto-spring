package com.uniovi.tests.pageobjects;



import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.utils.SeleniumUtils;

public class PO_View {

	protected static PO_Properties p = new PO_Properties("messages");
	protected static int timeout = 2;

	public static int getTimeout() {
		return timeout;
	}

	public static void setTimeout(int timeout) {
		PO_View.timeout = timeout;
	}

	public static PO_Properties getP() {
		return p;
	}

	public static void setP(PO_Properties p) {
		PO_View.p = p;
	}

	/**
	 * Espera por la visibilidad de un texto correspondiente a la propiedad key en
	 * el idioma locale en la vista actualmente cargandose en driver..
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param key: clave del archivo de propiedades.
	 * @param locale: Retorna el índice correspondient al idioma. 0 p.SPANISH y 1
	 *        p.ENGLISH.
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkKey(WebDriver driver, String key, int locale) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString(key, locale),
				getTimeout());
		return elementos;
	}

	/**
	 * Espera por la visibilidad de un elemento/s en la vista actualmente cargandose
	 * en driver..
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param type:
	 * @param text:
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkElement(WebDriver driver, String type, String text) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, type, text, getTimeout());
		return elementos;
	}

	/**
	 * Hace click al elemento cuya id coincida con la primera id pasada como
	 * parámetro y comprueba si, tras el tiempo especificado por el timeout, está
	 * presente el elemento con idDestino
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param id: Id del elemento a clickar
	 * @param idDestino: Id cuya presencia se probará
	 */
	public static void clickOptionWithId(WebDriver driver, String id, String idDestino) {
		// CLickamos en la opción de registro y esperamos a que se cargue el enlace de
		// Registro.
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", id, getTimeout());
		// Tiene que haber un sólo elemento.
		assertTrue(elementos.size() == 1);
		// Ahora lo clickamos
		elementos.get(0).click();
		// Esperamos a que sea visible un elemento concreto
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", idDestino, getTimeout());
		// Tiene que haber un sólo elemento.
		assertTrue(elementos.size() == 1);
	}
	
	/**
	 * Hace click al elemento cuya id coincida con la primera id pasada como
	 * parámetro
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param id: Id del elemento a clickar
	 */
	public static void clickOptionWithIdNoCheck(WebDriver driver, String id) {
		// CLickamos en la opción de registro y esperamos a que se cargue el enlace de
		// Registro.
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", id, getTimeout());
		// Tiene que haber un sólo elemento.
		assertTrue(elementos.size() == 1);
		// Ahora lo clickamos
		elementos.get(0).click();
	}

	/**
	 * Hace click al elemento cuya id coincida con la id pasada como parámetro
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param id: Id del elemento a clickar
	 */
	public static void checkElementWithId(WebDriver driver, String id) {
		checkElement(driver, "id", id);
	}

	/**
	 * Comprueba que no hay ningún elemento presente cuya id coincida con la pasada
	 * como parámetro
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param id: Id del elemento que no deberá estar presente
	 */
	public static void checkNoOptionWithId(WebDriver driver, String id) {
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(@id,'" + id + "')]"));
		assertTrue("Texto " + id + " aun presente !", list.size() == 0);
	}

	public static int countListingElementsOnView(WebDriver driver) {
		List<WebElement> elements = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		
		return elements.size();
	}
}
