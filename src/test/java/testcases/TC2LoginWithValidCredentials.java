package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import testbase.BaseClass;

public class TC2LoginWithValidCredentials extends BaseClass{

	@Test(groups = {"smoke"})
	public void login() {
		
		logger.info("*****Starting LoginWithValidCredentialsTC002*****");
		HomePage hp = new HomePage(getDriver());
		hp.clickOnMyAccount();
		logger.info("Clicking on login");
		hp.clickOnLogin();
		
		logger.info("Providing email and password");
		LoginPage lp = new LoginPage(getDriver());
		lp.setEmail(prop.getProperty("email"));
		lp.setPassword(prop.getProperty("password"));
		logger.info("Clicking on login button");
		lp.clickOnLoginButton();
		
		//validation
		MyAccountPage mp= new MyAccountPage(getDriver());
		Assert.assertEquals(mp.isMyAccountPageExists(), true);
		mp.clickOnLogout();
		logger.info("*****Finished LoginWithValidCredentialsTC002*****");
	}
}
