package pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[@id='button-cart']")
    WebElement btnCart;

    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']//a[1]")
    WebElement txtConfirmMessage;

    @FindBy(xpath = "//a[@title='Shopping Cart']//i[@class='fa fa-shopping-cart']")
    WebElement linkCart;

    public void addToCart() {
        btnCart.click();
    }

    public boolean isAdded(String productName) {
        return txtConfirmMessage.getText().toLowerCase()
                .contains(productName.toLowerCase());
    }

    public void clickOnCart() {
        linkCart.click();
    }

    public boolean checkProductInCardById(String productId) {
        List<WebElement> products = driver.findElements(
                By.xpath("//div[@class='table-responsive']//tbody//td[3]"));
        if(products.size() == 0) {
        	return false;
        }
        System.out.println(products.size());
        for (WebElement product : products) {
            if (product.getText().trim().equalsIgnoreCase(productId.trim())) {
                return true;
            }
        }
        return false;
    }

    public void removeProductByName(String productId) {
        List<WebElement> rows = driver.findElements(
                By.xpath("//div[@class='table-responsive']//tbody//tr"));

        for (int r = 0; r < rows.size(); r++) {

            String productIdXpath = "//div[@class='table-responsive']//tbody//tr[" + (r + 1) + "]//td[3]";
            String deleteBtnXpath = "//div[@class='table-responsive']//tbody//tr[" + (r + 1) + "]//button[contains(@class,'btn-danger')]";

            String cartId = driver.findElement(By.xpath(productIdXpath)).getText().trim();

            if (cartId.equalsIgnoreCase(productId.trim())) {
            	driver.findElement(By.xpath(deleteBtnXpath)).click();
            	new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(productIdXpath)));
                return;
            }
   
        }
    }
    
}
