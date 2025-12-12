package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productTitle = By.cssSelector(".name");

    public ProductPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
    }

    public String getProductTitle() {
        waitForLoad(); 
        return driver.findElement(productTitle).getText();
    }
}
