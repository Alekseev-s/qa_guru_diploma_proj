package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Random;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.TestDataHelper.getUserLogin;
import static helpers.TestDataHelper.getUserPassword;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class UITests extends TestBase {
    Random random = new Random();

    @Test
    void loginTest() {
        open("");
        $("#link-login").click();
        $("[name=user_email]").setValue(getUserLogin());
        $("[name=password]").setValue(getUserPassword());
        $("#link_login").click();
        $("#user_info").shouldHave(Condition.text("Перейти в личный кабинет"));
    }

    @Test
    void searchProductTest() {
        open("");
        $("#main-search-form").$("[name=q]").setValue("Газонокосилка");
        $("#main-search-form").$("[type=submit]").click();
        $("#h1").shouldHave(Condition.text("Газонокосилки"));
        ElementsCollection items = $("#product-list").$$("li");
        items.get(random.nextInt(items.size())).shouldHave(Condition.text("Газонокосилка"));
    }

    @Test
    void searchBrandTest() {
        open("");
        $("#main-search-form").$("[name=q]").setValue("BOSCH");
        $("#main-search-form").$("[type=submit]").click();
        $("#prod_desc").shouldHave(Condition.text("Bosch – один из ведущих немецких производителей"));
        $x("//div[contains(@class, 'brand-catalog')]").shouldHave(Condition.text("Каталог BOSCH"));
    }

    @Test
    void changeCityTest() {
        open("");
        $(".toolsbar").$(".tool-city-selector").click();
        $("#city-list-modal").$("input").setValue("Ворк");
        $(".city-list-modal-autocomplete-result li").shouldHave(Condition.text("Воркута"));
        $(".city-list-modal-autocomplete-result li").click();
        $(".toolsbar").$(".tool-city-selector").shouldHave(Condition.text("Воркута"));
    }

    @Test
    void sortingByPriceTest() {
        open("catalog/akkumulyatornye-dreli-shurupoverty/");
        $(".select2-listing select").selectOption(0);
        ElementsCollection minMaxPriceList = $$("#product-list li");
        assertThat(minMaxPriceList.get(0).$(".new-item-list-price-im").getText(), lessThan(minMaxPriceList.get(5).$(".new-item-list-price-im").getText()));
        assertThat(minMaxPriceList.get(7).$(".new-item-list-price-im").getText(), lessThan(minMaxPriceList.get(10).$(".new-item-list-price-im").getText()));
        assertThat(minMaxPriceList.get(15).$(".new-item-list-price-im").getText(), lessThan(minMaxPriceList.get(20).$(".new-item-list-price-im").getText()));
        $(".select2-listing select").selectOption(1);
        assertThat(minMaxPriceList.get(0).$(".new-item-list-price-im").getText(), greaterThan(minMaxPriceList.get(5).$(".new-item-list-price-im").getText()));
        assertThat(minMaxPriceList.get(7).$(".new-item-list-price-im").getText(), greaterThan(minMaxPriceList.get(10).$(".new-item-list-price-im").getText()));
        assertThat(minMaxPriceList.get(15).$(".new-item-list-price-im").getText(), greaterThan(minMaxPriceList.get(20).$(".new-item-list-price-im").getText()));
    }

    @Test
    void filterProductsTest() {
        open("catalog/benzopily/");
        $("#filterForm").$(byText("Вес")).scrollIntoView(false).click();
        $("#filterForm").$("#p541_to").setValue("5.0");
        $("#filterSubm").click();
        ElementsCollection items = $$("#product-list li");
        items.get(random.nextInt(items.size())).click();
        $("#js-jump-to-description").click();
        String weight = $("#js-prod-descr").$("[data-param='Вес нетто']").parent().parent().sibling(0).getText();
        assertThat(weight, lessThan("5.0 кг"));
    }

    @Test
    void comparisonLabelTest() {
        open("");
        getWebDriver().manage().addCookie(new Cookie("compare_products", "151802-604870-400858"));
        open("");
        $("#cCountCompare").shouldHave(Condition.text("3"));
    }

    @Test
    void removeComparisonTest() {
        open("");
        getWebDriver().manage().addCookie(new Cookie("compare_products", "151802-604870-400858"));
        open("catalog-604870/");
        $("[for=id604870]").sibling(0).$("span").click();
        $("#cCountCompare").shouldHave(Condition.text("2"));
    }

    @Test
    void brokenTest() {
        open("");
        $("#cCountCompare").shouldHave(Condition.text("2"));
    }

    @Test
    void failedTest() {
        open("");
        $("type=submit").shouldHave(Condition.text("Тест"));
    }
}
