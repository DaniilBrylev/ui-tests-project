package pages.web;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    private static final String BASE_URL = "https://www.demoblaze.com/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By navBrand = By.cssSelector("a.navbar-brand");
    private final By productTitles = By.cssSelector("#tbodyid .card-title a");

    private final By contactLink = By.cssSelector("a[data-target='#exampleModal']");
    private final By contactEmail = By.id("recipient-email");
    private final By contactName = By.id("recipient-name");
    private final By contactMessage = By.id("message-text");
    private final By contactSendButton = By.cssSelector("#exampleModal .btn-primary");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get(BASE_URL);
        waitForLoad();
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(navBrand));
    }

    public boolean isLoaded() {
        return driver.findElement(navBrand).isDisplayed();
    }

    public void goToCategory(String categoryName) {
        By categoryLocator = By.xpath("//a[@id='itemc' and normalize-space()='" + categoryName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(productTitles, 0));
    }

    public String getFirstProductName() {
        List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(productTitles)
        );
        return products.get(0).getText();
    }

 
    public ProductPage openFirstProduct() {
        WebElement firstProduct =
                wait.until(ExpectedConditions.elementToBeClickable(productTitles));

        firstProduct.click();

        ProductPage productPage = new ProductPage(driver, wait);
        productPage.waitForLoad();
        return productPage;
    }

    public Alert submitContactForm(String email, String name, String message) {
        wait.until(ExpectedConditions.elementToBeClickable(contactLink)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(contactEmail)).clear();
        driver.findElement(contactEmail).sendKeys(email);

        driver.findElement(contactName).clear();
        driver.findElement(contactName).sendKeys(name);

        driver.findElement(contactMessage).clear();
        driver.findElement(contactMessage).sendKeys(message);

        driver.findElement(contactSendButton).click();

        return wait.until(ExpectedConditions.alertIsPresent());
    }
}
