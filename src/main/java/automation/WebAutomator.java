package automation;

import java.time.Duration;


import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import browsers.Browser;
import config.Config;
import exceptions.NoValidBrowserException;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

public class WebAutomator {
	
	//WebDriver Wrapper
	private WebDriver driver;
	
	//WebDriver wait
	private WebDriverWait wait;
	
    private String titulo;
	
	
    @SuppressWarnings("deprecation")
	public WebAutomator(Browser browser,boolean isHeadless, boolean isIncognito, long max_wait) throws NoValidBrowserException {
		switch (browser) {
		case CHROME: {		
			System.setProperty("webdriver.chrome.driver", Config.DRIVER_PATH + "chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		case FIREFOX:{			
			DriverManagerType  firefox = DriverManagerType.FIREFOX;
			WebDriverManager.getInstance(firefox).setup();			
			driver = new FirefoxDriver();
			break;
		}
			
		case EDGE:{
			DriverManagerType edge = DriverManagerType.EDGE;
			WebDriverManager.getInstance(edge).setup();			
			driver = new EdgeDriver();
			break;
		}
			
		case IEXPLORER:{
			DriverManagerType iexplore = DriverManagerType.IEXPLORER;
			WebDriverManager.getInstance(iexplore).setup();			
			driver = new InternetExplorerDriver();
			break;
		}
			
			
		default:
			throw new NoValidBrowserException(browser.toString());
			
		}
		wait = new WebDriverWait(driver, Duration.ofSeconds(max_wait));
		
	}
	    
	
		
		
		//Getters y Setters
		public WebDriver getDriver() {
			return this.driver;
		}
		
		//Browser API
		public void maximizeWindows() {
			this.driver.manage().window().maximize();
		}
		
		
		//Method that closes the browser and ends the active session of the WebDriver
		public void closeAll(){
			if(driver!=null)
			{
				this.driver.quit();
			}
			
		}

		public void back() {
			this.driver.navigate().back();
		}
		
		public void forward() {
			this.driver.navigate().forward();
		}
		
		public void refresh() {
			this.driver.navigate().refresh();
		}
		
		public void goTo(String url) {
			this.driver.get(url);
		}
		
		public void closeBrowser() {
			this.driver.quit();
		}
		
		public void closeCurrentTab() {
			this.driver.close();
		}
		
		public String getCurrentUrl() {
			return this.driver.getCurrentUrl();
		}
		
		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		
		
		public void visibleElement(By elem) {
			
			 JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		     WebElement element = driver.findElement(elem);
		     jsExecutor.executeScript("arguments[0].style.visibility=''", element);
		}
		
		public void hiddenElement(By elem) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement ignore = this.driver.findElement(elem);
	        jsExecutor.executeScript("arguments[0].style.visibility='hidden'", ignore);
		}
		
		
		//Elements API
		public UIElement find(By by) {
			return this.waitUntilPresent(by);
		}

		private UIElement waitUntilPresent(By by) {
			return this.wait(ExpectedConditions.presenceOfElementLocated(by));
		}
		
		private UIElement wait(ExpectedCondition<WebElement> conditions) {
			return new UIElement(this,this.wait.until(conditions));
		}

}
