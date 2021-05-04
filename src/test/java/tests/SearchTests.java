package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Feature("Тесты поиска")
public class SearchTests extends TestBase {
    Random random = new Random();

    @Test
    @DisplayName("Поиск товара по категории")
    void searchProductTest() {
        step("Открываем главную страницу", () -> {
            open("");
        });

        step("Выполняем поиск по слову \"Газонокосилка\"", () -> {
            $("#main-search-form").$("[name=q]").setValue("Газонокосилка");
            $("#main-search-form").$("[type=submit]").click();
        });

        step("Проверяем, что заголовок результатов поиска - \"Газонокосилки\"", () -> {
            $("#h1").shouldHave(Condition.text("Газонокосилки"));
        });

        step("Проверяем, что случайно выбранный товар из результата поиска является газонокосилкой", () -> {
            ElementsCollection items = $("#product-list").$$("li");
            items.get(random.nextInt(items.size())).shouldHave(Condition.text("Газонокосилка"));
        });
    }

    @Test
    @DisplayName("Поиск бренда BOSCH")
    void searchBrandTest() {
        step("Открываем главную страницу", () -> {
            open("");
        });

        step("Выполняем поиск по слову \"BOSCH\"", () -> {
            $("#main-search-form").$("[name=q]").setValue("BOSCH");
            $("#main-search-form").$("[type=submit]").click();
        });

        step("Проверяем, что результатом поиска статья о компании", () -> {
            $("#prod_desc").shouldHave(Condition.text("Bosch – один из ведущих немецких производителей"));
        });

        step("Проверяем, что на странице есть каталог \"BOSCH\"", () -> {
            $x("//div[contains(@class, 'brand-catalog')]").shouldHave(Condition.text("Каталог BOSCH"));
        });
    }
}
