package tests.mobile;

import config.BaseMobileTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.mobile.WikipediaArticlePage;
import pages.mobile.WikipediaSearchPage;

public class WikipediaMobileTests extends BaseMobileTest {

    private WikipediaSearchPage searchPage;
    private WikipediaArticlePage articlePage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        searchPage = new WikipediaSearchPage(driver, wait);
        articlePage = new WikipediaArticlePage(driver, wait);
    }

    @Test(description = "Search article by keyword and verify first result")
    public void shouldFindArticleByKeyword() {
        searchPage.searchFor("Appium");
        String firstResult = searchPage.getFirstResultTitle();
        Assert.assertTrue(firstResult.toLowerCase().contains("appium"), "First result should contain the query text");
    }

    @Test(description = "Open article and verify title")
    public void shouldOpenArticleAndVerifyTitle() {
        searchPage.searchFor("Appium");
        String firstResult = searchPage.getFirstResultTitle();
        searchPage.openFirstResult();
        Assert.assertEquals(articlePage.getArticleTitle(), firstResult, "Article title should match the search result");
    }

    @Test(description = "Scroll article down and verify content is visible")
    public void shouldScrollArticleAndSeeContent() {
        searchPage.searchFor("Appium");
        searchPage.openFirstResult();
        articlePage.waitForLoad();
        articlePage.scrollToText("References");
        Assert.assertTrue(articlePage.isTextVisible("References"), "References section should be visible after scroll");
    }
}
