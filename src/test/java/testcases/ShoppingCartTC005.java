package testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobjects.SearchProductPage;
import pageobjects.ShoppingCartPage;
import testbase.BaseClass;

public class ShoppingCartTC005 extends BaseClass {

    @Test
    @Parameters({"search-product", "product-name"})
    public void searchAndAddToCart(String searchProduct, String productName) {
        logger.info("*****Starting ShoppingCartTC005*****");

        SearchProductPage spp = new SearchProductPage(getDriver());
        spp.searchProductByName(searchProduct);
        spp.selectProduct(productName);

        if (!spp.confirmProduct(productName)) {
            Assert.fail();
        }

        ShoppingCartPage cart = new ShoppingCartPage(getDriver());
        cart.addToCart();

        if (!cart.isAdded(productName)) {
            Assert.fail();
        }

        cart.clickOnCart();
        Assert.assertTrue(cart.checkProductInCardById(spp.productId));
    }

    
    @Test(dependsOnMethods = "searchAndAddToCart")
    @Parameters({"product-id"})
    public void removeProduct(String productId) {
        logger.info("*****Started RemoveProductFromCartTC006*****");

        ShoppingCartPage cart = new ShoppingCartPage(getDriver());
        cart.clickOnCart();
        cart.removeProductByName(productId);
        Assert.assertFalse(cart.checkProductInCardById(productId));
    }
}
