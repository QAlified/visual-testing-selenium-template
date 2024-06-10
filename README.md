# TEMPLATE VISUAL TESTING

El proyecto consiste en un framework de automatización de pruebas basado en Selenium WebDriver que sirve como punto de partida en cualquier proyecto de automatización de visual testing.

Se realiza un gran cubrimiento de pruebas basadas en testing visual, el cual se considera de gran aporte para muchos sistemas.
Tambien se ofrecen reportes luego de cada ejecucion.

## Comenzando 🚀

Estas instrucciones te permitirán obtener una copia local del proyecto en funcionamiento para propósitos de desarrollo y pruebas.

### Pre-requisitos 📋

Requisitos necesarios para el correcto funcionamiento del template y cómo instalarlos.

* Java 
* IDE de preferencia como por ejemplo, [Eclipse IDE](https://www.eclipse.org/)
* [TestNG](https://testng.org/doc/download.html), disponible también desde el  _Marketplace_  de Eclipse.
* [Ashot](https://mvnrepository.com/artifact/ru.yandex.qatools.ashot/ashot), la dependencia se agrega en el pom.xml del proyecto.
* [ExtentReports](https://www.extentreports.com/docs/versions/5/java/index.html), la dependencia se agrega en el pom.xml del proyecto.

### Instalación 🔧

A continuación se describen los pasos para descargar e instalar el template en tu IDE de preferencia.

1. Descargar una copia o clonar el código del repositorio desde GitHub.
2. Importar el proyecto en el IDE, recordar hacerlo como proyecto de tipo “Maven project”.
3. Al finalizar la importación, el proyecta está listo para usarse.

# Testing Visual 📸

### ¿Que es el visual testing?

El visual testing es una técnica de automatización de pruebas que se enfoca en la apariencia visual y la interacción del usuario con una aplicación. Consiste en evaluar la apariencia visual de la aplicación y su comportamiento de acuerdo a la interfaz del usuario, tales como botones, iconos y otros elementos visuales, considerados importantes para el cliente.

### ¿Por que utilizarlo?

Ofrece la seguridad y fiabilidad para validar correctamente.
Pruebas personalizables. Se pueden examinar las zonas que mas interesan en concreto, la vista actual en la que se encuentra el usuario dentro de una web o la web completa sin necesidad de utilizar el scroll.
Se pueden omitir partes y elementos de la web.
Funciona con cualquier ordenador, dispositivo o SO.

Cada prueba realizada se apoya en capturas de pantalla, tomadas a lo largo de la prueba con el fin de obtener el estado del Sistema en ese momento y compararlo con el estado siguiente del sistema.
Se obtiene una linea base de capturas, las cuales mas adelante se van comparando con otras capturas, de ejecuciones posteriores, y queda por cada par (base,test) una imagen de comparacion en la cual se marcan todas las diferencias en pantalla.

### AShot
Como principal herramienta para poder realizar este tipo de testing, se utilizo a AShot. Esta utlidad permite facilitar las screenshots y las diversas configuraciones que ellas conllevan, asi como hace mas facil las comparaciones de imagenes correspondientes.


### Reportes 📉📂

Se realiza un seguimiento a cada test ejecutado, a traves de un reporte en HTML que informa el paso a paso de la recorrida del caso de prueba. Informa tanto si pasa como si falla, y en caso de obtenerse alguna captura con diferencias respecto a la linea base, la diferencia es mostrada inmediatamente en pantalla en el documento.
El informe no solo proporciona un resumen, sino que también ayuda a la persona a visualizar y resumir los resultados de la tarea. Un informe también juega un papel vital en la automatización de pruebas.

### ¿Que herramienta utlizamos?

Se utiliza ExtentReports.
ExtentReports es una herramienta de generación de informes para proyectos de automatización de pruebas en Java que permite generar informes interactivos y visuales en formatos como HTML, correo electrónico, entre otros, en base a los resultados de pruebas de software. Ofrece estadísticas, gráficos, capturas de pantalla y resúmenes de pruebas, lo que hace que la herramienta sea muy útil para el análisis de datos de pruebas.
Además, los informes son personalizables y se pueden adaptar a las necesidades de cada proyecto.


## Diseño de pruebas ⚙️

A continuación de presenta información y ejemplos que detallan cómo comenzar a crear las pruebas automatizadas usando las clases del template.

### Primeros pasos

Comenzamos creando nuestra primer clase de pruebas utilizando la opción  _File > New > Class_ . Esta clase de pruebas contendrá los distintos métodos de prueba que se vayan generando en el proyecto.

Recomendamos que las clases de prueba extiendan de la clase _ TestBase _  donde se resuelve la creación del driver y el manejador de propiedades de forma totalmente automática.

Utilizando el objeto  _WebAutomator_  heredado de la clase TestBase podremos acceder a las principales funcionalidades de navegación del template. En el siguiente ejemplo se muestra el código para acceder a una determinada dirección URL, navegar hacia atrás y adelante, refrescar la página y cerrar el navegador.

```java
package test;

import org.testng.annotations.Test;

public class TestCaptureElement extends TestBase {
	
	@Test
	public void captureElement() {


	}

}
```

### Interactuando con elementos de la web

Utilizando la clase  _WebAutomator_  y  _UIElement_  es posible simular interacciones con los distintos elementos de la interfaz gráfica Web. Para ello, primero se debe crear el objeto UIElement utilizando el método   _find_ de  _WebAutomator_ . 
Luego, para realizar captura del elemento seleccionado se utiliza la clase  _VisualTesting_

```java
@Test
public class TestCaptureElement extends TestBase {


	@Test
	public void captureElement(String url, String newBase) {

		url = "https://qalified.com/";
		automator.goTo(url);

		//Reportes con Extent
		ExtentSparkReporter spark = new ExtentSparkReporter("sparkReports/ReporteCaptureElement.html");
		extent.attachReporter(spark);
		ExtentTest test = extent.createTest("TestCaptureElement"); 

		//Realiza captura de pantalla de un elemento en especifico.
		By title = contact.getTitle();
		vt.Capture(newBase, test, "CapturaElemento.png", title); 

	}

}

```

Para conocer más en detalle sobre las clases  _WebAutomator_,  _UIElement_  y _VisualTesting_ y sus métodos, visitar el apartado **Especificación**

### Ejecutar nuestra prueba

Agregar la clase recién creada al archivo  _testng.xml_ 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Suite_Demo">
	<parameter name="browser" value="CHROME" />
	<parameter name="max_wait" value="30" />
	<parameter name="close_browser_after_execution" value="false" />
	<test thread-count="5" name="testSuite-sample">
		<classes>
			<class name="test.CaptureElement" />
		</classes>
	</test>
</suite>

```

Los parámetros  _browser_  y  _max_wait_  se_ utilizan para establecer el navegador y tiempos de espera durante las pruebas.
El parámetro  _close_browser_after_execution_  permite indicar si se debe cerrar el navegador tras la ejecución de la prueba.

### Especificación

**WebAutomator**
La clase  _WebAutomator_  encapsula y extiende toda la funcionalidad de la clase  _WebDriver_  de Selenium y brinda mecanismos más eficientes para la interacción con el navegador y la web. A su vez, resuelve automaticamente los tiempos de espera aplicando  _ExplicitWaits_  al momento de interactuar con los elementos HTML.

| Método        | Descripción           |
| ------------- |---------------|
| public WebDriver `getDriver()`      | Retorna el objeto  _WebDriver_ de Selenium |
| public void `maximizeWindows()`     | Maximiza la ventana del navegador      |
| public void `back()`                | Navega hacia atras en el navegador      |
| public void `forward()`             | Navega hacia adeante en el navegador |
| public void `refresh()`             | Refresca la página del navegador |
| public void `goTo(String url)`      | Navega hacia la url determinada |
| public void `closeBrowser()`        | Cerrar el navegador |
| public void `closeCurrentTab()`     | Cerrar la pestaña de navegación |
| public String `getCurrentUrl()`     | Retorna la URL actual de la pestaña activa |
| public UIElement `find(By by)`      | Retorna el objeto  _UIElement_  que coincide con el selector  _By_ recibido por parámetro |
| public UIElement `findChild(By parent, By child)` | Analiza el objeto _UIElement_ (parent) y retorna el primer objeto  _UIElement_ hijo que coincide con el selector _By_ (child) |
| public UIElement `waitUntilVisible(By by)` | Retorna el objeto  _UIElement_  cuando el mismo sea visible en pantalla |
| public UIElement `waitUntilClickable(By by)` | Retorna el objeto  _UIElement_  cuando el mismo sea cliqueable en pantalla |
| public void `deleteAllCookies()` | Elimina todas las cookies en la sesión del navegador |
| public Set<Cookie> `getAllCookies()` | Retorna todas las cookies del navegador |
| public Cookie `getCookie(String cookie)` | Retorna la cookie cuyo nombre coincida con el recibido por parámetro |
| public void `addCookie(Cookie cookie)` | Agrega una nueva cookie en el navegador |
| public void `takeScreenshot()` | Realiza una captura de pantalla y la almacena en formato .png |

**UIElement**
La clase  _UIElement_  sustituye a la clase  _WebElement_  de Selenium encapsula toda la funcionalidad sobre la interacción de los elementos de la UI web. 

| Método        | Descripción           |
| ------------- |---------------|
| public WebElement `getWebElement()`        | Retorna el objeto  _WebElement_ de Selenium |
| public void `setText(String text)`         | Ingresa el texto recibido por parámetros en el elemento      |
| public void `clear()`                      | Limpia el contenido del elemento                  |
| public void `clearAndSetText(String text)` | Limpia el contenido del elemento e ingresa el texto recibido por parámetro |
| public String `getLink()`                  | Retorna el valor del atributo "href" del elemento web |
| public void `submit()`                     | Confirma el envio de información |
| public void `click()`                      | Simula la acción de clic en el elemento |
| public boolean `isSelected()`              | Retorna verdadero si el elemento web está seleccionado  |
| public boolean `isDisplayed()`             | Retorna verdadero si el elemento web es visible en pantalla  |
| public boolean `isEnabled()`               | Retorna verdadero si el elemento web está habilitado    |
| public String `getText()`                  | Retorna el texto visible de un elemento web |
| public String `getValue()`                 | Retorna el valor de un elemento |
| public UIElement `findElementBy(By by)`    | Retorna un nuevo  _UIElement_  interno al elemento web y que coincida con el selector  _By_ recibido por parámetros |
| public List<UIElement> `findElementsBy(By by)` |  Retorna una lista de  _UIElement_  internos al elemento web y que coincidan con el selector  _By_ recibido por parámetros|
| public void `setTextWithActions(String text)` | Ingresa el texto recibido por parámetros en el elemento utilizando la clase _Actions_  de Selenium |
| public void `selectByValue(String value)` | Selecciona una opcion de la lista basado en el atributo _value_ de la misma  |
| public void `selectByIndex(Integer index)` | Selecciona una opcion de la lista basado en el indice numerico de la misma  |
| public void `selectByVisibleText(String text)` | Selecciona una opcion de la lista basado en el texto visible de la misma  |
| public void `moveToElement()`              | Mueve la UI web hasta hacer visible el elemento en pantalla  |

**VisualTesting**
La clase  _VisualTesting_ encapsula toda la funcionalidad sobre la interacción de las capturas de pantalla. 

| Método        | Descripción           |
| ------------- |---------------|
| public void `setShootingStrategy(ShootingStrategy s)`     | Establece la política de tomar capturas de pantalla. |
| public Screenshot `takeElementScreenshot(WebElement e)`  | Toma la captura de pantalla del elemento dado. |
| public void `Screenshot takeScreenshot()` 				| Toma la captura de pantalla de toda la página. |
| public void `addIgnoredElement(By ignoredElem) `          | Agrega selector de elemento ignorado. |
| public void `addIgnoredElements(Set<By> ignoredElems)`    |  Agrega selector de elementos ignorados.|
| public void `saveScreenshotAsBaseline(Screenshot s, String name)`   | Guardar capturas de pantalla como linea base. |
| public void `saveScreenshotAsCheckpoint(Screenshot s, String name) `| Guardar capturas de pantalla como punto de control |
| public void `Capture(String newBase,ExtentTest test,String name)`   | Realiza captura de pantalla, si el newBase es true lo guarda como base, sino lo guarda como checkpoint y hace la comparación con la base  |
| public void `Capture(String newBase,ExtentTest test,String name,By elem)`  | Realiza captura de pantalla de un elemento en especifico, si el newBase es true lo guarda como base, sino lo guarda como checkpoint y hace la comparación con la base. |
| public void `Capture(String newBase,ExtentTest test,String name,Set<By> elems)`  | Realiza captura de pantalla de una lista de elementos, si el newBase es true lo guarda como base, sino lo guarda como checkpoint y hace la comparación con la base. |
| public void `CaptureHidden(String newBase,ExtentTest test,String name,By Elem)`  | Realiza capturas de pantalla ocultando un elemento en especifico de la web. |
| public void `CaptureHidden(String newBase,ExtentTest test,String name,Set<By> ignored)`    | Realiza capturas de pantalla ocultando una lista de elementos de la web. |
| public void `CaptureElem(String newBase,WebElement elem,ExtentTest test,String name)` | Captura de pantalla. Si el newBase es true lo guarda como base, sino lo guarda como checkpoint y hace la comparación con la base. |
| public void `CaptureElem(String newBase,WebElement elem,ExtentTest test,String name,By ignored)` | Realiza captura de pantalla ignorando un elemnto en especifico de la web, si el newBase es true lo guarda como base, sino lo guarda como checkpoint y hace la comparación con la base. |
| public void `CaptureElem(String newBase,WebElement elem,ExtentTest test,String name,Set<By> ignored)` | Realiza captura de pantalla ignorando una lista de elementos de la web, si el newBase es true lo guarda como base, sino lo guarda como checkpoint y hace la comparación con la base. |
| public void `checkScreenshots(Screenshot baseline, Screenshot checkpoint, String compName,ExtentTest test)` | Compara la base con el checkpoint. |
| public Screenshot `loadBaseline(String name)`      | Línea base de carga de captura de pantalla. |
| public Screenshot `loadCheckpoint(String name)`    | Punto de control de carga de captura de pantalla. |

## Construido con 🛠️

* [Selenium WebDriver](https://www.selenium.dev/) - El framework de automatización.
* [TestNG](https://testng.org/doc/) - Framework de pruebas.
* [Maven](https://maven.apache.org/) - Herramienta de construcción de proyecto y gestión de dependencias.

## Autores ✒️

* [**QAlified**](https://qalified.com/)

## Contacto 📢

info@qalified.com

---
⌨️ con ❤️ por QAlified



