package test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import exceptions.MissingPropertyException;
import exceptions.NoSuchPropertyFileException;
import exceptions.NoValidBrowserException;
import pom.Contact;

public class TestCaptureHiddenElement extends TestBase{
	
	private By title = By.xpath("//h3[@class='mx-auto']");
	
	
	@Test
	@Parameters({ "urlQAlified", "newBase"})
	public void captureHiddenElement(String urlQAlified, String newBase)
			throws MissingPropertyException, NoSuchPropertyFileException, IOException, InterruptedException, NoValidBrowserException {

		//Reportes con Extent
		ExtentSparkReporter spark = new ExtentSparkReporter("sparkReports/ReporteCaptureHiddenElement.html");
		spark.config().setTheme(Theme.DARK);
		extent.attachReporter(spark);
		ExtentTest test = extent.createTest("TestCaptureHiddenElement");
		this.automator.setTitulo("CaptureHiddenElement");
		
		test.log(Status.INFO, "Directing to page: " + urlQAlified);
		this.automator.goTo(urlQAlified);
		this.automator.maximizeWindows();
		Thread.sleep(7000);
		

		Contact contact = new Contact(automator, test, vt, newBase);
		contact.clickContact();
		Thread.sleep(8000);
		
		vt.Capture(newBase, test, "CrearContact.png");
		test.log(Status.INFO, "Capturing page...");
		Thread.sleep(7000);
		
		//ocultar elemento titulo -> ¿Algún proyecto en mente?
		vt.hiddenElement(title);
		Thread.sleep(7000);
		
		vt.Capture(newBase, test, "ContactPage.png");
		test.log(Status.INFO, "Capturing page...");
		Thread.sleep(7000);

		contact.ingresarContact("Micaela", "mgonzalez@genexusconsulting.com", "Prueba de capturas de pantalla");
		
		test.log(Status.PASS, "Test Finished...");
		Thread.sleep(2000);
		extent.flush();
	}

}
