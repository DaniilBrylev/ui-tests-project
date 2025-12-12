package tests.web;

import config.BaseWebTest;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.web.HomePage;
import pages.web.ProductPage;

public class WebUiTests extends BaseWebTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void openHomePage() {
        homePage = new HomePage(driver, wait);
        homePage.open();
    }

    @Test(description = "Проверка загрузки главной страницы")
    public void shouldLoadHomePage() {
        Assert.assertTrue(homePage.isLoaded(), "Главная страница должна загрузиться");
    }

    @Test(description = "Переход в категорию Phones и проверка первого товара")
    public void shouldOpenCategoryAndShowProducts() {
        homePage.goToCategory("Phones");
        String firstProduct = homePage.getFirstProductName();
        Assert.assertEquals(firstProduct, "Samsung galaxy s6", "Первый товар категории Phones должен совпадать");
    }

    @Test(description = "Открытие первого товара и проверка названия")
    public void shouldOpenFirstProductAndMatchTitle() {
        homePage.goToCategory("Laptops");
        String expectedName = homePage.getFirstProductName();
        ProductPage productPage = homePage.openFirstProduct();
        Assert.assertEquals(productPage.getProductTitle(), expectedName, "Название на странице товара должно совпадать");
    }

    @Test(description = "Проверка отправки формы Contact")
    public void shouldSendContactFormAndReceiveAlert() {
        Alert alert = homePage.submitContactForm(
                "ui-tests@example.com",
                "UI Tester",
                "Automated feedback message");

        String alertText = alert.getText();
        Assert.assertTrue(alertText.toLowerCase().contains("thanks"), "Ожидается благодарность в тексте алерта");
        alert.accept();
    }
}
