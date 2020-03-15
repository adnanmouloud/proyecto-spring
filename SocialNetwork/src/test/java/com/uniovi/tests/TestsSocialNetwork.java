package com.uniovi.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_FriendshipView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PostView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_UserSearchView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestsSocialNetwork {

	// Paths Víctor (Comentar cuando se usen los otros)
//	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
//	static String Geckdriver024 = "C:\\Users\\powerservice\\Desktop\\POKEMON\\ATercero"
//			+ "\\SDI\\Materiales\\Práctica5\\OneDrive_2020-02-27\\PL-SDI-Sesio╠ün5-material\\geckodriver024win64.exe";

	// Paths Adnan (Comentar cuando se usen los otros)

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\Adnan\\Downloads\\3º Segundo Semestre\\SDI\\Practica\\Material\\P5\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
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
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "signup", "registerAsUserLbl");

		PO_RegisterView.fillForm(driver, "prueba1@uniovi.es", "Usuario", "Prueba", "1234", "1234");

		PO_View.checkElementWithId(driver, "authenticatedEmail");

		driver.manage().deleteAllCookies();
	}

	// PR02. Prueba de registro de Usuario con datos inválidos (campos vacíos)
	@Test
	public void PR02() {
		driver.manage().deleteAllCookies();
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
		driver.manage().deleteAllCookies();
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
		driver.manage().deleteAllCookies();
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
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "admin@email.com", "123456");

		// Esto solo sale en la página por defecto del administrador
		PO_View.checkElementWithId(driver, "listaUsuarios");

		driver.manage().deleteAllCookies();
	}

	// PR06. Prueba de inicio de sesión con datos válidos (usuario standard)
	@Test
	public void PR06() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		// Esto solo sale en la página por defecto de usuarios standard
		PO_View.checkElementWithId(driver, "authenticatedEmail");

		driver.manage().deleteAllCookies();
	}

	// PR07. Prueba de inicio de sesión con datos inválidos (usuario estándar, campo
	// email y contraseña vacíos)
	@Test
	public void PR07() {
		driver.manage().deleteAllCookies();
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
		driver.manage().deleteAllCookies();
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
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_View.clickOptionWithId(driver, "logout", "identifyYourselfLbl");

		driver.manage().deleteAllCookies();
	}

	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no
	// está autenticado
	@Test
	public void PR10() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.checkNoOptionWithId(driver, "logout");

		driver.manage().deleteAllCookies();
	}

	// PR11. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema.
	@Test
	public void PR11() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		PO_View.checkElementWithId(driver, "listaUsuarios");

		// Contamos el número de usuarios que hay en la primera página
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);

		PO_View.clickOptionWithId(driver, "siguiente", "listaUsuarios");

		// Contamos el número de usuarios que hay en la segunda página
		List<WebElement> elementos2 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		System.err.println(elementos2.size());

		// Como se crea un usuario en los tests anteriores, este sería el nuevo
		// resultado esperado
		assertTrue(elementos2.size() == 3);

		driver.manage().deleteAllCookies();
	}

	// PR12. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	// página
	// que corresponde con el listado usuarios existentes en el sistema.
	@Test
	public void PR12() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		PO_View.checkElementWithId(driver, "listaUsuarios");

		// Pinchamos en buscar
		PO_View.clickOptionWithId(driver, "buscar", "listaUsuarios");

		// Comprobar que están todos los usuarios del sistema

		// Contamos el número de usuarios que hay en la primera página
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);

		PO_View.clickOptionWithId(driver, "siguiente", "listaUsuarios");

		// Contamos el número de usuarios que hay en la segunda página
		List<WebElement> elementos2 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());

		// Como se crea un usuario en los tests anteriores, este sería el nuevo
		// resultado esperado
		assertTrue(elementos2.size() == 3);

		driver.manage().deleteAllCookies();
	}

	// PR13. Hacer una búsqueda escribiendo en el campo un texto que no exista y
	// comprobar que se muestra
	// la página que corresponde, con la lista de usuarios vacía.
	@Test
	public void PR13() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		PO_View.checkElementWithId(driver, "listaUsuarios");

		// metemos una cadena que no exista en el campo de búsqueda
		String textoABuscar = "yyyyyxq";
		PO_UserSearchView.fillField(driver, textoABuscar);

		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, textoABuscar, PO_View.getTimeout());

		driver.manage().deleteAllCookies();
	}

	// PR14. Hacer una búsqueda con un texto específico y comprobar que se muestra
	// la página que corresponde,
	// con la lista de usuarios en los que el texto especificados sea parte de su
	// nombre, apellidos o de su email.
	@Test
	public void PR14() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		PO_View.checkElementWithId(driver, "listaUsuarios");

		// metemos una cadena en el campo de búsqueda para buscar por nombre
		PO_UserSearchView.fillField(driver, "adnan");

		// Comprobar que no coincide ningún usuario del sistema con esta búsqueda
		List<WebElement> elementos2 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos2.size() == 1);

		// metemos una segunda cadena en el campo de búsqueda para buscar por appellido
		PO_UserSearchView.fillField(driver, "Gonzalo");

		// Comprobar que no coincide ningún usuario del sistema con esta búsqueda
		List<WebElement> elementos3 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos3.size() == 1);

		// metemos una tercera cadena en el campo de búsqueda para buscar por email
		PO_UserSearchView.fillField(driver, "gmail");

		// Comprobar que no coincide ningún usuario del sistema con esta búsqueda
		List<WebElement> elementos4 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos4.size() == 3);

		driver.manage().deleteAllCookies();
	}

	// PR15. Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario.
	// Comprobar que la solicitud de amistad aparece en el listado de invitaciones
	// (punto siguiente).
	@Test
	public void PR15() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		PO_View.checkElementWithId(driver, "listaUsuarios");

		PO_View.clickOptionWithIdNoCheck(driver, "addButton1");

		driver.manage().deleteAllCookies();
	}

	// PR16. Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario al
	// que ya le habíamos enviado la invitación previamente. No debería dejarnos
	// enviar la invitación, se podría
	// ocultar el botón de enviar invitación o notificar que ya había sido enviada
	// previamente.
	@Test
	public void PR16() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		// Enviamos solicitud al usuario 5
		PO_View.clickOptionWithIdNoCheck(driver, "addButton5");

		// Comprobamos que el botón para mandar la solicitud está deshabilitado
		assertFalse(driver.findElement(By.id("addButton5")).isEnabled());

		driver.manage().deleteAllCookies();
	}

	// PR17. Mostrar el listado de invitaciones de amistad recibidas. Comprobar con
	// un listado que
	// contenga varias invitaciones recibidas.
	@Test
	public void PR17() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "adnan@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		// Enviamos solicitud al usuario 4
		PO_View.clickOptionWithIdNoCheck(driver, "addButton4");

		PO_View.clickOptionWithId(driver, "logout", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "maria@outlook.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		// Enviamos otra solicitud al usuario 4
		PO_View.clickOptionWithIdNoCheck(driver, "addButton4");

		PO_View.clickOptionWithId(driver, "logout", "identifyYourselfLbl");

		// Nos loggeamos con el usuarios 4
		PO_LoginView.fillForm(driver, "fatema@outlook.es", "123456");

		PO_FriendshipView.goToInvitationsListing(driver);

		// Comprobamos que el número de peticiones son 2
		assertTrue(PO_View.countListingElementsOnView(driver) == 2);

		driver.manage().deleteAllCookies();
	}

	// PR18. Mostrar el listado de invitaciones de amistad recibidas. Comprobar con
	// un listado que
	// contenga varias invitaciones recibidas.
	@Test
	public void PR18() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "adnan@gmail.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		// Enviamos solicitud al usuario 4
		PO_View.clickOptionWithIdNoCheck(driver, "addButton4");

		PO_View.clickOptionWithId(driver, "logout", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "maria@outlook.es", "123456");

		PO_UserSearchView.goToUserListing(driver);

		// Enviamos otra solicitud al usuario 4
		PO_View.clickOptionWithIdNoCheck(driver, "addButton4");

		PO_View.clickOptionWithId(driver, "logout", "identifyYourselfLbl");

		// Nos loggeamos con el usuarios 4
		PO_LoginView.fillForm(driver, "fatema@outlook.es", "123456");

		PO_FriendshipView.goToInvitationsListing(driver);

		// Aceptamos la del usuario 1 (adnan)
		PO_View.clickOptionWithIdNoCheck(driver, "acceptButton1");

		// Comprobamos que esta no está, mientras que la otra sí
		PO_View.checkNoOptionWithId(driver, "invitation1");
		PO_View.checkElementWithId(driver, "invitation3");

		driver.manage().deleteAllCookies();
	}

	// PR19. Mostrar el listado de amigos de un usuario. Comprobar que el listado
	// contiene los amigos
	// que deben ser
	@Test
	public void PR19() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "admin@email.com", "123456");

		PO_FriendshipView.goToFriendsListing(driver);

		// Comprobamos que el número de amigos es 5 (máximo por página)
		assertTrue(PO_View.countListingElementsOnView(driver) == 5);

		PO_View.clickOptionWithIdNoCheck(driver, "siguiente");

		// Comprobamos que el número de amigos es 1 (máximo por página)
		assertTrue(PO_View.countListingElementsOnView(driver) == 1);

		driver.manage().deleteAllCookies();
	}

	// PR20. Visualizar al menos cuatro páginas en Español/Inglés/Español
	// (comprobando que algunas
	// de las etiquetas cambian al idioma correspondiente). Ejemplo, Página
	// principal/Opciones Principales de
	// Usuario/Listado de Usuarios.
	@Test
	public void PR20() {
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "kiko@outlook.es", "123456");

		// ******************** HOME ********************
		// Comprobación que sale el texto de bienvenida en Español e inglés
		PO_View.checkTagsLanguages(driver, "welcome.message", 1);

		// Comprobación que sale el texto de "Usuario Autenticado como:" en Español e
		// Inglés
		PO_View.checkTagsLanguages(driver, "user.authenticated", 1);

		// Comprobación que sale el texto de "Descripción" en Español e Inglés
		PO_View.checkTagsLanguages(driver, "details.description", 1);

		// ******************** USER/LIST ********************
		// Comprobación que sale el texto de "Nombre:" en Español e Inglés
		PO_UserSearchView.goToUserListing(driver);
		PO_View.checkTagsLanguages(driver, "name", 1);

		// Comprobación que sale el botón de "Buscar" en Español e Inglés
		PO_View.checkTagsLanguages(driver, "search", 1);

		// Comprobación que sale el botón de "Agregar amigo" en Español e Inglés
		PO_View.checkTagsLanguages(driver, "users.list.requestFriendship", 5);

		// ******************** FRIENDSHIP/LIST ********************
		// Comprobación que sale el texto de "Nombre:" en Español e Inglés
		PO_FriendshipView.goToInvitationsListing(driver);
		PO_View.checkTagsLanguages(driver, "name", 1);

		// ******************** FRIEND/LIST ********************
		// Comprobación que sale el texto de "Nombre:" en Español e Inglés
		PO_FriendshipView.goToFriendsListing(driver);
		PO_View.checkTagsLanguages(driver, "name", 1);

		// Comprobación que sale el texto de "Email" en Español e Inglés
		PO_View.checkTagsLanguages(driver, "email", 1);

		driver.manage().deleteAllCookies();
	}

	// PR21. Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios. Se deberá volver al
	// formulario de login.
	@Test
	public void PR21() {
		driver.manage().deleteAllCookies();

		driver.navigate().to(URL + "/user/list");

		PO_View.checkElementWithId(driver, "identifyYourselfLbl");

		driver.manage().deleteAllCookies();
	}

	// PR22. Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios. Se deberá volver al
	// formulario de login.
	@Test
	public void PR22() {
		driver.manage().deleteAllCookies();

		driver.navigate().to(URL + "/friendship/list");

		PO_View.checkElementWithId(driver, "identifyYourselfLbl");

		driver.manage().deleteAllCookies();
	}

	// PR23. Estando autenticado como usuario estándar intentar acceder a una opción
	// disponible solo
	// para usuarios administradores (Se puede añadir una opción cualquiera en el
	// menú). Se deberá indicar un
	// mensaje de acción prohibida.
	@Test
	public void PR23() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		// Accedemos mediante una URL, ya que la opción de menú no aparece si no eres
		// admin
		driver.navigate().to(URL + "/user/manage");

		PO_View.checkElementWithId(driver, "accessDenied");

		driver.manage().deleteAllCookies();
	}

	// PR23. Mostrar el listado de publicaciones de un usuario y
	// comprobar que se muestran todas las que
	// existen para dicho usuario.
	@Test
	public void PR26() {

		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "victorgon@gmail.es", "123456");

		PO_PostView.goToPostListing(driver);

		// Comprobamos que el número de publicaciones es 2
		assertTrue(PO_View.countListingElementsOnView(driver) == 2);

		driver.manage().deleteAllCookies();
	}

	// PR31. Estando autenticado como usuario estándar intentar acceder a una opción
	// disponible solo
	// para usuarios administradores (Se puede añadir una opción cualquiera en el
	// menú). Se deberá indicar un
	// mensaje de acción prohibida.
	@Test
	public void PR31() {
		driver.manage().deleteAllCookies();
		driver.navigate().to(URL);

		PO_View.clickOptionWithId(driver, "login", "identifyYourselfLbl");

		PO_LoginView.fillForm(driver, "admin@email.com", "123456");

		// Accedemos al menú de listado de usuarios que solo está accesible para el
		// admin
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/manage')]");
		elementos.get(0).click();

		PO_View.checkElementWithId(driver, "tableUsers");

		assertTrue(PO_View.countListingElementsOnView(driver) == 5);

		PO_View.clickOptionWithId(driver, "siguiente", "listaUsuarios");

		assertTrue(PO_View.countListingElementsOnView(driver) == 4);

		driver.manage().deleteAllCookies();
	}
}
