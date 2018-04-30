/*
 *                 SignIn test script
 * 
 * Test the following sign in scenarios
 * 1. Test to check successful sign in for the registered user
 * 2. Test to check unregistered users with incorrect email and password
 * 3. Test to check if the account is locked out after entering the incorrect password twice
 * 4. Test to check if the Sign-In fails with empty user name and valid password
 * 5. Test to check if the Sign-In fails with valid user name and empty password
 * 6. Test to check if the Sign-In fails with the invalid password
 * 
 * Expected outcomes:
 * All tests pass successfully
 */

package tests;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.SignInPage;

public class SignIn {
	WebDriver driver;
	SignInPage signInPage;
    
	// Setting up the Chrome driver
    @BeforeTest
    public void setUp() {
    	System.setProperty("webdriver.chrome.driver", "path to chromedriver");
		driver = new ChromeDriver();
		driver.get("https://dscoutapp.com/sign_in");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    // Test to check successful sign in for the registered user
	@Test(dataProvider = "Authentication")
		public void testWithValidDataSets(String  userName, String password){
			signInPage = new SignInPage(driver);
			signInPage.enterUserDetails(userName, password);
			//Check for the URL or title of the page
			Assert.assertTrue(driver.getCurrentUrl().equals("https://dscoutapp.com/sign_in"));  		
	}	
	// Test to check unregistered users with incorrect email and password
	@Test(dataProvider = "Authentication")
	public void testWithInvalidDataSets(String  userName, String password){
		signInPage = new SignInPage(driver);	
		signInPage.enterUserDetails(userName, password);
		Assert.assertTrue(signInPage.invalidMessage());
			}
			
	// Test to check if the account is locked out after entering the incorrect password twice
	@Test(dataProvider = "Authentication")
	public void testLockedOut(String  userName, String password){
				signInPage.enterUserDetails(userName, password);
				signInPage.enterUserDetails(userName, password);
				Assert.assertTrue(signInPage.lockedOut());
			}
	
	//Test to check if the Sign-In fails with empty user name and valid password
	@Test(dataProvider = "Authentication")
	public void testWithEmptyEmail(String  userName, String password){
				signInPage.enterUserDetails(userName, password);
				Assert.assertTrue(signInPage.invalidMessage());
				
			}
			
	//Test to check if the Sign-In fails with valid user name and empty password
	@Test(dataProvider = "Authentication")
	public void testWithEmptyPassword(String  userName, String password){
				signInPage.enterUserDetails(userName, password);
				Assert.assertTrue(signInPage.invalidPassword());
			}
			
	//Test to check if the Sign-In fails with the invalid password
	@Test(dataProvider = "Authentication")
	public void testWithInvalidPasswords(String  userName, String password){
				signInPage.enterUserDetails(userName, password);
				Assert.assertTrue(signInPage.invalidPassword());	
			}

	// Data sets for valid and invalid test cases 	
	@DataProvider(name = "Authentication")
	public static Object[][] validCredentials(Method m) {
		 
		 if(m.getName().equalsIgnoreCase("testWithValidDataSets")) {
	        return new Object[][] { 
	        	{ "automation@tester.com", "dontbreakthebuild"}
	        	};}
		 	
		 else if(m.getName().equalsIgnoreCase("testWithInvalidDataSets")) {
			 return new Object[][] { 
			 	{ "testinvalid@test.com", "Test@123" },
			 	{ "automation@tester.com", "abcd$"},
			 	{ "automation@tester.com", "qwertyqwerty"},
			 	{ "asdsfsd", "dontbreakthebuild"}
			    };}
		 
		 else if(m.getName().equalsIgnoreCase("testLockedOut")) {
			 return new Object[][] { 
				 { "test4@test.com", "asdf"}
			    };}
		 else if(m.getName().equalsIgnoreCase("testWithEmptyEmail")) {
			 return new Object[][] {    	
				 { "", "dontbreakthebuild"}
			    };}
		 else if(m.getName().equalsIgnoreCase("testWithEmptyPassword")) {
			 return new Object[][] {    	
				 { "automation@tester.com", ""}
			    };}
		 else  {
			 return new Object[][] {   	
				 { "testinvalidpassword@test.com", "test"}
			 	};}
	  } 
	 
	 @AfterClass
	 public void tearDown(){
		 //Quit the driver
		 driver.quit();
	 }
}
