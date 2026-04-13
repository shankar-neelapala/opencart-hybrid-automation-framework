package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.AccountRegistrationPage;
import pageobjects.HomePage;
import pageobjects.MyAccountPage;
import testbase.BaseClass;

public class TC1AccountRegistration extends BaseClass{
	
	@Test(groups = {"regression"})
	public void newAccountRegistration() {
		logger.info("*****Starting RegistrationTC001*****");
		HomePage homePage = new HomePage(getDriver());
		logger.info("Clicking on my account");
		homePage.clickOnMyAccount();
		logger.info("Clicking on registration");
		homePage.clickOnRegister();
		
		AccountRegistrationPage registration = new AccountRegistrationPage(getDriver());
		logger.info("Sending data to the application");
		registration.setFirstname(getRandomString());
		registration.setLastname(getRandomString());
		registration.setEmail(getRandomString()+"@gmail.com");
		registration.setTelephone(getRandomNumeric());
		
		String pass = getRandomAlphaNumeric().toLowerCase();
		registration.setPassword(pass);
		registration.setConfirmPassword(pass);
		
		registration.selectPrivacyPolicy();
		logger.info("Clicking on continue button to create account");
		registration.clickOnContinue();
		
		logger.info("Validating expected result with actual result");
		String expectedMessage = "Your Account Has Been Created!";
		MyAccountPage mp = new MyAccountPage(getDriver());
		//Assert.assertEquals(registration.getConfirmMessage().toLowerCase(), expectedMessage.toLowerCase());
		if(expectedMessage.toLowerCase().equals(mp.confirmAccount().toLowerCase())) {
			logger.info("Account creation successful");
			Assert.assertTrue(true);
		}
		else {
			logger.error("Account creation unsuccessful");
			logger.debug("Debug logs");
			Assert.assertTrue(false);
		}
	}
}
