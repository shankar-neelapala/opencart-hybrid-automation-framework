package testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.ShoppingCartPage;
import testbase.BaseClass;

public class TC6RemoveProductFromCart extends BaseClass{
	
	 @Test(groups = "regression")
	 @Parameters({"product-id"})
	 public void removeProduct(String productId) {
		 logger.info("*****Started RemoveProductFromCartTC006*****");
		 HomePage hp = new HomePage(getDriver());
		 hp.clickOnMyAccount();
		 hp.clickOnLogin();
		 logger.info("Login into the account");
		 LoginPage lp = new LoginPage(getDriver());
		 lp.setEmail(prop.getProperty("email"));
		 lp.setPassword(prop.getProperty("password"));
		 lp.clickOnLoginButton();
		 logger.info("removing product from the cart");
		 ShoppingCartPage cart = new ShoppingCartPage(getDriver());
	     cart.clickOnCart();
	     cart.removeProductByName(productId);
	     Assert.assertFalse(cart.checkProductInCardById(productId));
	 }
}
