package tests;

import allure.JiraIssue;
import allure.JiraIssues;
import allure.ProjectTeam;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@ProjectTeam
@JiraIssues({@JiraIssue("QC3-33")})
@Feature("Тесты списка товаров")
public class ProductListTests extends TestBase {
    Random random = new Random();

    @Test
    @DisplayName("Сортировка товаров по цене (по убыванию и по возрастанию)")
    void sortingByPriceTest() {
        step("Открываем каталог шуруповертов", () -> {
            open("catalog/akkumulyatornye-dreli-shurupoverty/");
        });

        step("Задаем сортировку по цене по возрастанию", () -> {
            $(".select2-listing select").selectOption(0);
        });

        step("Проверяем, что товары отсортированы по возрастанию цены", () -> {
            ElementsCollection minMaxPriceList = $$("#product-list li");
            assertThat(minMaxPriceList.get(0).$(".new-item-list-price-im").getText(), lessThan(minMaxPriceList.get(5).$(".new-item-list-price-im").getText()));
            assertThat(minMaxPriceList.get(7).$(".new-item-list-price-im").getText(), lessThan(minMaxPriceList.get(10).$(".new-item-list-price-im").getText()));
            assertThat(minMaxPriceList.get(15).$(".new-item-list-price-im").getText(), lessThan(minMaxPriceList.get(20).$(".new-item-list-price-im").getText()));
        });

        step("Задаем сортировку по цене по убыванию", () -> {
            $(".select2-listing select").selectOption(1);
        });

        step("Проверяем, что товары отсортированы по убыванию цены", () -> {
            ElementsCollection maxMinPriceList = $$("#product-list li");
            assertThat(maxMinPriceList.get(0).$(".new-item-list-price-im").getText(), greaterThan(maxMinPriceList.get(5).$(".new-item-list-price-im").getText()));
            assertThat(maxMinPriceList.get(7).$(".new-item-list-price-im").getText(), greaterThan(maxMinPriceList.get(10).$(".new-item-list-price-im").getText()));
            assertThat(maxMinPriceList.get(15).$(".new-item-list-price-im").getText(), greaterThan(maxMinPriceList.get(20).$(".new-item-list-price-im").getText()));
        });
    }

    @Test
    @DisplayName("Фильтрация товаров по параметру \"Вес\"")
    void filterProductsTest() {
        step("Открываем каталог бензопил", () -> {
            open("catalog/benzopily/");
        });

        step("В фильтре задаем максимальный вес 5 кг", () -> {
            $("#filterForm").$(byText("Вес")).scrollIntoView(false).click();
            $("#filterForm").$("#p541_to").setValue("5.0");
            $("#filterSubm").click();
        });

        step("Открываем случайный товар из выборки", () -> {
            ElementsCollection items = $$("#product-list li");
            items.get(random.nextInt(items.size())).click();
            $("#js-jump-to-description").click();
        });

        step("Проверяем, что вес бензопилы меньше 5 кг", () -> {
            String weight = $("#js-prod-descr").$("[data-param='Вес нетто']").parent().parent().sibling(0).getText();
            assertThat(weight, lessThan("5.0 кг"));
        });
    }
}
