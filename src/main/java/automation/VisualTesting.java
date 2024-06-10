package automation;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import config.Config;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.CoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

public class VisualTesting {
	
	private WebDriver driver;
	
	
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	private AShot ashot;
	private String baselineFolder = Config.SCREENSHOT_PRUEBA_PATH + "baselines" + File.separator;
	private String checkpointFolder = Config.SCREENSHOT_PRUEBA_PATH + "checkpoints" + File.separator;
	private String compFolder = Config.SCREENSHOT_PRUEBA_PATH + "comparisons" + File.separator;
	
	public VisualTesting(WebDriver driver) {
		this.driver = driver;
		this.ashot = new AShot().shootingStrategy(ShootingStrategies.scaling(Config.SCALING_FACTOR));
	}
	
	public VisualTesting(WebDriver driver, ShootingStrategy s, CoordsProvider cp) {
		this.driver = driver;
		this.ashot = new AShot()
				.shootingStrategy(s)
				.coordsProvider(cp);
	}
	

	public void setShootingStrategy(ShootingStrategy s) {
		this.ashot.shootingStrategy(s);
	}
	
	public void setCoordsProvider(CoordsProvider cp) {
		this.ashot.coordsProvider(cp);
	}
	
	//Toma la captura de pantalla del elemento dado
	public Screenshot takeElementScreenshot(WebElement e) {
		return this.ashot.takeScreenshot(this.driver, e);
	}
	
	//Toma la captura de pantalla de toda la página
	public Screenshot takeScreenshot() {
		return this.ashot.takeScreenshot(this.driver);
	}
	
	//Agregar elemento ignorado
	public void addIgnoredElement(By ignoredElem) {
			this.ashot.addIgnoredElement(ignoredElem);
	}

	//Agregar elementos ignorados
	public void addIgnoredElements(Set<By> ignoredElems) {
			for (By elem:ignoredElems) {
				this.ashot.addIgnoredElement(elem);
			}
	}
	
	//Guardar capturas de pantalla como linea base
	public void saveScreenshotAsBaseline(Screenshot s, String name) {
		try {
			ImageIO.write(s.getImage(), "PNG", new File(baselineFolder + name));
		} catch (IOException e) {
			System.out.println("Unable to set Baseline screenshot with name " + name);
		}
	}
	
	//Guardar capturas de pantalla como punto de control
	public void saveScreenshotAsCheckpoint(Screenshot s, String name) {
		try {
			ImageIO.write(s.getImage(), "PNG", new File(checkpointFolder + name));
		} catch (IOException e) {
			System.out.println("Unable to set screenshot with name " + name);
		}
	}
	
	//Realiza captura de pantalla
	//Si el newBase es true lo guarda como base, 
	//sino lo guarda como checkpoint y hace la comparación con la base.
	public void Capture(String newBase,ExtentTest test,String name) {
		Screenshot s = this.takeScreenshot();
		if (newBase.contentEquals("true") || (loadBaseline(name)==null) ) {
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "The base is created...");
		}
		else {
			saveScreenshotAsCheckpoint(s, name);
			assertFalse(checkScreenshots(loadBaseline(name),loadCheckpoint(name),name,test));
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
		}
	}
	
	//Realiza captura de pantalla de un elemento en especifico.
	//Si el newBase es true lo guarda como base, 
	//sino lo guarda como checkpoint y hace la comparación con la base.
	public void Capture(String newBase,ExtentTest test,String name,By elem) {
		Screenshot s;
		if (newBase.contentEquals("true") || (loadBaseline(name)==null) ) {
			s = takeScreenshot();
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "The base is created...");
		}
		else {
			addIgnoredElement(elem);
			s = takeScreenshot();
			assertFalse(checkScreenshots(loadBaseline(name),s,name,test));
			saveScreenshotAsCheckpoint(s, name);
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
		}
	}

	//Realiza captura de pantalla de una lista de elementos.
	//Si el newBase es true lo guarda como base, 
	//sino lo guarda como checkpoint y hace la comparación con la base.
	public void Capture(String newBase,ExtentTest test,String name,Set<By> elems) {
		Screenshot s;
		if (newBase.contentEquals("true") || (loadBaseline(name)==null) ) {
			s = takeScreenshot();
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "The base is created...");
		}
		else {
			addIgnoredElements(elems);
			s = takeScreenshot();
			assertFalse(checkScreenshots(loadBaseline(name),s,name,test));
			saveScreenshotAsCheckpoint(s, name);
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
		}
	}
	
	//Realiza capturas de pantalla ocultando un elemento en especifico de la web.
	public void CaptureHidden(String newBase,ExtentTest test,String name,By Elem) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String originalFrame = driver.getWindowHandle();
		WebElement ignore = this.driver.findElement(Elem);
	    jsExecutor.executeScript("arguments[0].style.visibility='hidden'", ignore);
		Screenshot s = this.takeScreenshot();
		
		if (newBase.contentEquals("true") || (loadBaseline(name)==null) ) {
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "The base is created...");
		}
		else {
			saveScreenshotAsCheckpoint(s, name);
			assertFalse(checkScreenshots(loadBaseline(name),loadCheckpoint(name),name,test));
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
		}
	}

	
	//Realiza capturas de pantalla ocultando una lista de elementos de la web.
	public void CaptureHidden(String newBase,ExtentTest test,String name,Set<By> ignored) throws InterruptedException {
		String originalFrame = driver.getWindowHandle();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		for (By elem:ignored) {
				WebElement ignore = this.driver.findElement(elem);
		        jsExecutor.executeScript("arguments[0].style.visibility='hidden'", ignore);
		}

		Screenshot s = this.takeScreenshot();
		if (newBase.contentEquals("true") || (loadBaseline(name)==null) ) {
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "The base is created...");
		}
		else {
			saveScreenshotAsCheckpoint(s, name);
			assertFalse(checkScreenshots(loadBaseline(name),loadCheckpoint(name),name,test));
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
		}
	}
	
	
	//Captura de pantalla. Si el newBase es true lo guarda como base, 
	//sino lo guarda como checkpoint y hace la comparación con la base.
	public void CaptureElem(String newBase,WebElement elem,ExtentTest test,String name) {
		Screenshot s = this.takeElementScreenshot(elem);
		if (newBase.contentEquals("true")) {
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "Base was overwritten!");
			
		}
		else {
			saveScreenshotAsCheckpoint(s,name);
			assertFalse(checkScreenshots(loadBaseline(name),s,name,test));
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
			
		}
	}
	
	
	//Realiza captura de pantalla ignorando un elemnto en especifico de la web. 
	//Si el newBase es true lo guarda como base, 
	//sino lo guarda como checkpoint y hace la comparación con la base.
	public void CaptureElem(String newBase,WebElement elem,ExtentTest test,String name,By ignored) { 
		Screenshot s;
		if (newBase.contentEquals("true")) {
			s = takeElementScreenshot(elem);
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "Base was overwritten!");
			
		}
		else {
			addIgnoredElement(ignored);
			s = takeElementScreenshot(elem);
			assertFalse(checkScreenshots(loadBaseline(name),s,name,test));
			saveScreenshotAsCheckpoint(s,name);
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
			
		}
	}
	
	//Realiza captura de pantalla ignorando una lista de elementos de la web. 
	//Si el newBase es true lo guarda como base, 
	//sino lo guarda como checkpoint y hace la comparación con la base.
	public void CaptureElem(String newBase,WebElement elem,ExtentTest test,String name,Set<By> ignored) { 
		Screenshot s;
		if (newBase.contentEquals("true")) {
			s = takeElementScreenshot(elem);
			saveScreenshotAsBaseline(s,name);
			test.log(Status.PASS, "Base was overwritten!");
			
		}
		else {
			addIgnoredElements(ignored);
			s = takeElementScreenshot(elem);
			assertFalse(checkScreenshots(loadBaseline(name),s,name,test));
			saveScreenshotAsCheckpoint(s,name);
			test.log(Status.PASS, "Checkpoints and comparisons are created!");
			
		}
	}
	//Ocultar elemento
	public void hiddenElement(By elem) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement ignore = this.driver.findElement(elem);
        jsExecutor.executeScript("arguments[0].style.visibility='hidden'", ignore);
	}
	
	
	//Compara la base con el checkpoint. 
	//Agrega  al principio las areas ignoradas (si es que hay) del checkpoint a la baseline 
	//para que no se tengan en cuenta en la comparación de las  capturas. Luego se fija si hay diferencias. 
	//Si hay diferencias lo avisa y devuelve "true". Si no hay diferencias devuelve "false". 
	//Luego el metodo que llamo a ese recibe ese "true " o "false", 
	//y si es "true" tira una assert y corta la prueba.
	public boolean checkScreenshots(Screenshot baseline, Screenshot checkpoint, String compName,ExtentTest test) {
		System.out.println("Ignored Elements :" + checkpoint.getIgnoredAreas().toString());
		baseline.setIgnoredAreas(checkpoint.getIgnoredAreas()); 
		baseline.setCoordsToCompare(checkpoint.getCoordsToCompare()); 
		
		ImageDiff diff = new ImageDiffer().makeDiff(baseline, checkpoint);
		if (diff.hasDiff()) {
			test.log(Status.INFO, "there are differences");
			
			try {
				ImageIO.write(diff.getMarkedImage(), "PNG", new File(compFolder + compName));
				test.log(Status.FAIL, 
	                    "The base image is different to the checkpoint image"
	            ).fail("Expected image:")
	            .fail(MediaEntityBuilder.createScreenCaptureFromPath(baselineFolder+compName).build())
	            .fail("Image obtained:")
	            .fail(MediaEntityBuilder.createScreenCaptureFromPath(checkpointFolder+compName).build())
	            .fail("differences:")
	            .fail(MediaEntityBuilder.createScreenCaptureFromPath(compFolder+compName).build());
				return true;
			} catch (IOException e) {
				System.out.println("Unable to write Diff image with name " + compName);
				return true;
			}
		} else {
			test.log(Status.INFO, "there aren't differences");
			
			return false;
		}
	}
	
	//Línea base de carga de captura de pantalla
	public Screenshot loadBaseline(String name) {
		try {
			return new Screenshot(ImageIO.read(new File(baselineFolder + name)));
		} catch (IOException e) {
			System.out.println("Unable to load baseline image with name " + name);
		}
		return null;
	}
	
	
	//Punto de control de carga de captura de pantalla
	public Screenshot loadCheckpoint(String name) {
		try {
			return new Screenshot(ImageIO.read(new File(checkpointFolder + name)));
		} catch (IOException e) {
			System.out.println("Unable to load checkpoint image with name " + name);
		}
		return null;
	}	

}
