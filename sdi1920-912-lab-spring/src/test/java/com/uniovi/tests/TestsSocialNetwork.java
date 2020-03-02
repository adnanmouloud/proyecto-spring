package com.uniovi.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsSocialNetwork {

	// Paths Víctor (Comentar cuando se usen los otros)
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\powerservice\\Desktop\\POKEMON\\ATercero"
			+ "\\SDI\\Práctica5\\OneDrive_2020-02-27\\PL-SDI-Sesio╠ün5-material\\geckodriver024win64.exe";

	// Paths Adnan (Comentar cuando se usen los otros)

//	static String PathFirefox65 = "";
//	static String Geckdriver024 = "";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	// TODO: No me está funcionando el before, cuando funcione, borrar
	// "driver.navigate()" de todos los tests
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR01. Prueba de registro de Usuario con datos válidos
	@Test
	public void PR01() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "signup", "registerAsUserLbl");

		PO_RegisterView.fillForm(driver, "prueba@uniovi.es", "Usuario", "Prueba", "1234", "1234");

		PO_View.checkElementWithId(driver, "authenticatedEmail");

		driver.manage().deleteAllCookies();
	}

	// PR02. Prueba de registro de Usuario con datos inválidos (campos vacíos)
	@Test
	public void PR02() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "signup", "registerAsUserLbl");

		// Email vacío
		PO_RegisterView.fillForm(driver, " ", "Usuario", "Prueba", "1234", "1234");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());

		// Nombre vacío
		PO_RegisterView.fillForm(driver, "prueba@uniovi.es", " ", "Prueba", "1234", "1234");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());

		// Apellidos vacío
		PO_RegisterView.fillForm(driver, "prueba@uniovi.es", "Usuario", " ", "1234", "1234");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());

		driver.manage().deleteAllCookies();
	}

	// PR03. Prueba de registro de Usuario con datos inválidos (repetición de
	// contraseña incorrecta)
	@Test
	public void PR03() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "signup", "registerAsUserLbl");

		PO_RegisterView.fillForm(driver, "prueba@uniovi.es", "Usuario", "Prueba", "1234", "12345");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());

		driver.manage().deleteAllCookies();
	}

	// PR04. Prueba de registro de Usuario con datos inválidos (email existente)
	@Test
	public void PR04() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "signup", "registerAsUserLbl");

		PO_RegisterView.fillForm(driver, "victorgon@gmail.es", "Usuario", "Prueba", "1234", "1234");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());

		driver.manage().deleteAllCookies();
	}

	// PR05. Prueba de inicio de sesión con datos válidos (administrador)
	@Test
	public void PR05() {
		// TODO: Hacer que la /home de admin sea distinta a la de un Standard User
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "admin@email.com", "123456");

		PO_View.checkElementWithId(driver, "authenticatedEmail");

		driver.manage().deleteAllCookies();
	}

	// PR06. Prueba de inicio de sesión con datos válidos (usuario standard)
	@Test
	public void PR06() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_View.checkElementWithId(driver, "authenticatedEmail");

		driver.manage().deleteAllCookies();
	}

	// PR07. Prueba de inicio de sesión con datos inválidos (usuario estándar, campo
	// email y contraseña vacíos)
	@Test
	public void PR07() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, " ", " ");

		PO_View.checkElementWithId(driver, "identifyYourselfLbl");

		driver.manage().deleteAllCookies();
	}

	// PR08. Prueba de inicio de sesión con datos válidos (usuario estándar, email
	// existente, pero contraseña
	// incorrecta)
	@Test
	public void PR08() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "adnan@gmail.es", "12");

		PO_View.checkElementWithId(driver, "identifyYourselfLbl");

		driver.manage().deleteAllCookies();
	}

	// PR09. Prueba hacer click en la opción de salir de sesión y comprobar que se
	// redirige a la página de inicio de
	// sesión (Login)
	@Test
	public void PR09() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_View.clickOptionWithId(driver, "logout", "identifyYourselfLbl");

		driver.manage().deleteAllCookies();
	}

	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado
	@Test
	public void PR10() {
		driver.navigate().to(URL);

		PO_View.checkNoOptionWithId(driver, "logout");

		driver.manage().deleteAllCookies();
	}
}
