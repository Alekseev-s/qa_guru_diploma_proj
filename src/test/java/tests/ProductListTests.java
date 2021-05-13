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
            ElementsCollection productsPriceList = $$("#product-list .new-item-list-price-im");
            assertThat(productsPriceList.get(0).getText(), lessThan(productsPriceList.get(5).getText()));
            assertThat(productsPriceList.get(7).getText(), lessThan(productsPriceList.get(10).getText()));
            assertThat(productsPriceList.get(15).getText(), lessThan(productsPriceList.get(20).getText()));
        });

        step("Задаем сортировку по цене по убыванию", () -> {
            $(".select2-listing select").selectOption(1);
        });

        step("Проверяем, что товары отсортированы по убыванию цены", () -> {
            ElementsCollection productsPriceList = $$("#product-list .new-item-list-price-im");
            assertThat(productsPriceList.get(0).getText(), greaterThan(productsPriceList.get(5).getText()));
            assertThat(productsPriceList.get(7).getText(), greaterThan(productsPriceList.get(10).getText()));
            assertThat(productsPriceList.get(15).getText(), greaterThan(productsPriceList.get(20).getText()));
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
