package pageobjects;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchProductPage extends BasePage{

	public SearchProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement txtSearchBox;
	
	@FindBy(xpath = "//div[@class='row']//h4//a")
	List<WebElement> products;
	
	@FindBy(xpath = "//*[@id='content']//div[@class='col-sm-4']//h1")
	WebElement confirmProduct;
	
	@FindBy(xpath = "//div[@class='col-sm-4']//ul[@class='list-unstyled'][1]//li[2]")
	WebElement txtProductCode;	
	public String productId;
	
	public void searchProductByName(String name) {
		txtSearchBox.sendKeys(name + Keys.ENTER);
	}
	
	public void selectProduct(String productName) {
		for(WebElement prod : products) {
			if(prod.getText().equalsIgnoreCase(productName)) {
				//System.out.println(prod.getText());
				prod.click();
				productId = getProductCode();
				break;
			}
		}
	}
	
	public boolean confirmProduct(String name) {
		return confirmProduct.getText().equalsIgnoreCase(name);
	}
	
	public String getProductCode() {
		String cd = txtProductCode.getText();
		return cd.substring(cd.indexOf(":")+1);
	}
}
