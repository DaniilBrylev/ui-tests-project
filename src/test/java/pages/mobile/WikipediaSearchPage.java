package pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WikipediaSearchPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By searchContainer = By.id("org.wikipedia:id/search_container");
    private final By searchInput = By.id("org.wikipedia:id/search_src_text");
    private final By firstResultTitle = By.id("org.wikipedia:id/page_list_item_title");
    private final By onboardingSkip = By.id("org.wikipedia:id/fragment_onboarding_skip_button");

    public WikipediaSearchPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void dismissOnboardingIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement skip = shortWait.until(ExpectedConditions.elementToBeClickable(onboardingSkip));
            skip.click();
        } catch (TimeoutException ignored) {
        }
    }

    public void openSearch() {
        dismissOnboardingIfPresent();
        wait.until(ExpectedConditions.elementToBeClickable(searchContainer)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
    }

    public void searchFor(String query) {
        openSearch();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(query);
    }

    public String getFirstResultTitle() {
        WebElement first = wait.until(ExpectedConditions.visibilityOfElementLocated(firstResultTitle));
        return first.getText();
    }

    public void openFirstResult() {
        WebElement first = wait.until(ExpectedConditions.elementToBeClickable(firstResultTitle));
        first.click();
    }

    public boolean isResultVisible(String text) {
        return !driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"org.wikipedia:id/page_list_item_title\").textContains(\"" + text + "\")"
        )).isEmpty();
    }
}
