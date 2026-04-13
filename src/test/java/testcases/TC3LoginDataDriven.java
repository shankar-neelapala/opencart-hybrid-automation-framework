package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class TC3LoginDataDriven extends BaseClass{

	@Test(dataProvider = "login-data",dataProviderClass = DataProviders.class, groups = {"smoke"})
	public void loginDataDrivenTest(String email, String password, String result) {
		logger.info("*****Starting LoginDataDrivenTC003*****");
		HomePage hp = new HomePage(getDriver());
		hp.clickOnMyAccount();
		logger.info("Clicking on login");
		hp.clickOnLogin();
		
		logger.info("Providing email and password");
		LoginPage lp = new LoginPage(getDriver());		
		lp.setEmail(email);
		lp.setPassword(password);
		logger.info("Clicking on login button");
		lp.clickOnLoginButton();
		
		//validation
		logger.info("validating results");
		MyAccountPage mp= new MyAccountPage(getDriver());
		boolean status = mp.isMyAccountPageExists();
		//System.out.println(status);
		if (result.equalsIgnoreCase("valid")) {
		    Assert.assertTrue(status, "Login should succeed for valid credentials");
		} else if (result.equalsIgnoreCase("invalid")) {
		    Assert.assertFalse(status, "Login should fail for invalid credentials");
		}

		if (status) {
		    mp.clickOnLogout();
		    //System.out.println(email);
		}

		logger.info("*****Finished LoginDataDrivenTC003*****");
	}
}
