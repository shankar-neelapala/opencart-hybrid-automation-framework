package testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageobjects.SearchProductPage;
import testbase.BaseClass;

public class TC4SearchProduct extends BaseClass{

	@Test(groups = "regression")
	@Parameters({"search-product", "product-name"})
	public void searchProduct(String searchProduct, String productName) {
		logger.info("*****Starting SearchProductTC004*****");
		SearchProductPage spp = new SearchProductPage(getDriver());
		logger.info("Searching for "+searchProduct+" started");
		spp.searchProductByName(searchProduct);
		logger.info("Finding "+productName+" product");
		spp.selectProduct(productName);
		Assert.assertEquals(spp.confirmProduct(productName), true);
		logger.info("*****Finished SearchProductTC004*****");
	}
}
