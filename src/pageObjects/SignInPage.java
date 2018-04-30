/*
 * Page objects for the SignIn page at https://dscoutapp.com/sign_in
 * */
package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage {
	
	WebDriver driver;
	
	/* Get the emailID, password field locators */
	By userID = By.id("user_email");
	By userPassword = By.id("user_password");
	
	/* Get the sign-in button and error message */
	By signInButton = By.cssSelector("#sign-in > input");
	By errorMessage = By.xpath("//span[@class='blitz-message']");
	
	/* Possible messages to be checked to verify incorrect login behavior */
	String invalidMessage = "Invalid email or password.";
	String lockedOut = "Your account has been locked. Please check your email for instructions on how to unlock your account.";
	String invalidPassword = "Password should contain 10 characters including atleast 1 special character";
	
	public SignInPage(WebDriver driver) {
		// Initialize the driver
		this.driver = driver;
	}
	
	// Method for entering emailID, password and then click the signInButton
	public void enterUserDetails(String emailId, String pwd) {
		try{
		driver.findElement(userID).clear();
		driver.findElement(userID).sendKeys(emailId);
		driver.findElement(userPassword).clear();
		driver.findElement(userPassword).sendKeys(pwd);
		driver.findElement(signInButton).click();
		}
		catch(Exception e){ 	
			e.printStackTrace();
		}	
	}
	
	// Method to test invalid error message
	public Boolean invalidMessage() {
		return driver.findElement(errorMessage).getText().equals(invalidMessage);
	}
	
	// Method to test whether two incorrect login attempts give the locked out message
	public Boolean lockedOut() {
		return driver.findElement(errorMessage).getText().equals(lockedOut);
	}
	
	// Method to test invalid password 
	public Boolean invalidPassword() {
		return driver.findElement(errorMessage).getText().equals(invalidPassword);
	}
	
}
