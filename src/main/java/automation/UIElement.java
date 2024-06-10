package automation;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UIElement {

	private WebAutomator automator;
	private WebElement   element;
	
	
	public UIElement(WebAutomator automator, WebElement element) {
		this.automator = automator;

		this.element = element;
	}
	
	public WebElement getWebElement() {
		return this.element;
	}
	
	public void setText(String text) {
		this.element.sendKeys(text);
	}
	
	public void clear() {
		this.element.clear();
	}
	
	public void clearAndSetText(String text) {
		this.clear();
		this.setText(text);
	}
	
	public String getLink() {
		return this.element.getAttribute("href");
	}
	
	public void submit() {
		this.element.submit();
	}
		
	public void click() {
		this.element.click();
	}
	
	public boolean isSelected() {
		return this.element.isSelected();
	}
	
	public boolean isDisplayed() {
		return this.element.isDisplayed();
	}
	
	public boolean isEnabled() {
		return this.element.isEnabled();
	}
	
	public String getText() {
		return this.element.getText();
	} 
	
	public String getValue() {
		return this.element.getAttribute("value");
	}

}
