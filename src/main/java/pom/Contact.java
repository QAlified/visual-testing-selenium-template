package pom;

import java.io.IOException;

import org.openqa.selenium.By;

import com.aventstack.extentreports.ExtentTest;

import automation.VisualTesting;
import automation.WebAutomator;
import exceptions.NoValidBrowserException;

public class Contact {
	

    private WebAutomator automator;
	private String newBase;
	private ExtentTest test;
	private VisualTesting vt;
    
	private By contact = By.xpath("//li[@class='nav-item page-scroll']/a[.='Contact']");
    private By nombre = By.id("fullname");
    private By email = By.id("email");
    private By mensaje = By.id("field[5]");
    private By enviar = By.id("_form_14_submit");
	private By title = By.xpath("//h3[@class='mx-auto']");

    
    
    public Contact(WebAutomator a, ExtentTest test, VisualTesting vt, String newBase ) throws NoValidBrowserException, IOException {
        this.automator = a;
        this.test = test;
		this.vt = vt;
		this.newBase = newBase;
    }
    
    public void gotoPage(String url){
        this.automator.goTo(url);
    }
    
    public void clickContact(){
        this.automator.find(contact).click();
    }

    public By getTitle(){
       return title;
    }
    
    public void ingresarNombre(String name){
        this.automator.find(nombre).setText(name);
    }

    public void ingresarEmail(String mail){
        this.automator.find(email).setText(mail);
    }

    public void ingresarComentario(String comment){
        this.automator.find(mensaje).setText(comment);
    }

    public void clickEnviar(){
        this.automator.find(enviar).click();
    }
    
    public void ingresarContact(String nombre, String email, String comentario){
    	this.ingresarNombre(nombre);
    	this.ingresarEmail(email);
    	this.ingresarComentario(comentario);
    }

    
}
