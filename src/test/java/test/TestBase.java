package test;

import java.time.Duration;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;

import automation.VisualTesting;
import automation.WebAutomator;
import browsers.Browser;
import util.PropertiesHandler;

public class TestBase {
	
	protected WebAutomator automator;
	protected PropertiesHandler mp;
	VisualTesting vt;
	ExtentReports extent = new ExtentReports();
	
	

	public ExtentReports getExtent() {
		return extent;
	}


	@BeforeMethod
	@Parameters({"browser","isHeadless", "max_wait"})
	public void setUpDriver(@Optional("CHROME")String browser, Boolean isHeadless, @Optional("30") long max_wait) throws Exception 
	{
		//Initialize WebDriver
		Browser b = Browser.valueOf(browser);
		automator = new WebAutomator(b,isHeadless, true, max_wait);
		//Initialize property Manager
		mp = new PropertiesHandler();
		vt = new VisualTesting(this.automator.getDriver());
	}
	
	public void setUpDriverParaIncognito(String browser, @Optional("30") long max_wait) throws Exception 
	{
		Browser b = Browser.valueOf(browser);
		automator = new WebAutomator(b, true, true, max_wait);		
	}
	
	@AfterClass
	@Parameters({"close_browser_after_execution"})
	public void tearDownDriver(@Optional("true") boolean closeBrowser) throws Exception {
		if (closeBrowser) {
			automator.closeBrowser();
		}
	}
	
	@AfterMethod
	public void tearDownDriver() throws Exception {
		automator.closeAll();
	}

	protected WebAutomator getAutomator(){
		return this.automator;
	}

}
