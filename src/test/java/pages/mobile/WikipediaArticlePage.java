package pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WikipediaArticlePage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By articleTitle = By.id("org.wikipedia:id/view_page_title_text");

    public WikipediaArticlePage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(articleTitle));
    }

    public String getArticleTitle() {
        waitForLoad();
        return driver.findElement(articleTitle).getText();
    }

    public void scrollToText(String text) {
        String command = String.format(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().textContains(\"%s\"))",
                text
        );
        driver.findElement(AppiumBy.androidUIAutomator(command));
    }

    public boolean isTextVisible(String text) {
        return !driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + text + "\")"
        )).isEmpty();
    }
}
