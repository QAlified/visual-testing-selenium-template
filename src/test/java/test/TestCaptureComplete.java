package test;

import java.io.IOException;

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

public class TestCaptureComplete extends TestBase{
	
	
	@Test
	@Parameters({ "urlQAlified", "newBase"})
	public void captureComplete(String urlQAlified, String newBase)
			throws MissingPropertyException, NoSuchPropertyFileException, IOException, InterruptedException, NoValidBrowserException {

		//Reportes con Extent
		ExtentSparkReporter spark = new ExtentSparkReporter("sparkReports/ReporteCaptureComplete.html");
		spark.config().setTheme(Theme.DARK);
		extent.attachReporter(spark);
		ExtentTest test = extent.createTest("TestCaptureComplete");
		this.automator.setTitulo("CaptureComplete");

		test.log(Status.INFO, "Directing to page: " + urlQAlified);
		this.automator.goTo(urlQAlified);
		this.automator.maximizeWindows();
		Thread.sleep(6000);

		Contact contact = new Contact(automator, test, vt, newBase);
		contact.clickContact();
		Thread.sleep(8000);
		
		
		vt.Capture(newBase, test, "CrearContacto.png");
		test.log(Status.INFO, "Capturing page...");
		Thread.sleep(5000);

		contact.ingresarContact("Micaela", "mgonzalez@genexusconsulting.com", "Prueba de capturas de pantalla");
		
		test.log(Status.PASS, "Test Finished...");
		Thread.sleep(2000);
		extent.flush();
	}
	
	

}
