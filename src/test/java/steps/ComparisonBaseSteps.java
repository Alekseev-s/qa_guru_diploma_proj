package steps;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ComparisonBaseSteps extends TestBase {
    @Step("Открываем главную страницу")
    public void openMainPage() {
        open("");
    }

    @Step("Добавляем список товаров в сравнение(через cookie)")
    public void addProductsToCompare(String productsId) {
        getWebDriver().manage().addCookie(new Cookie("compare_products", productsId));
    }

    @Step("Проверяем, что число товаров в шапке равно {count}")
    public void checkCompareCount(String count) {
        $("#cCountCompare").shouldHave(Condition.text(count));
    }

    @Step("Переходим на страницу товара с id {productId}")
    public void openProductPage(String productId) {
        open("catalog-" + productId + "/");
    }

    @Step("Удаляем товар с id {productId} из списка сравнения")
    public void removeProductFromComparison(String productId) {
        $("[for=id" + productId + "]").sibling(0).$("span").click();
    }
}
