package tests;

import allure.JiraIssue;
import allure.JiraIssues;
import allure.ProjectTeam;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.TestDataHelper.getUserLogin;
import static helpers.TestDataHelper.getUserPassword;
import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ProjectTeam
@JiraIssues({@JiraIssue("QC3-33")})
@Feature("Тесты главной страницы")
public class MainPageTests extends TestBase {
    @Test
    @DisplayName("Тест логина")
    void loginTest() {
        step("Открываем главную страницу", () -> {
            open("");
        });

        step("Переходим на страницу логина", () -> {
            $("#link-login").click();
        });

        step("Вводим логин и пароль, нажимаем 'Войти'", () -> {
            $("[name=user_email]").setValue(getUserLogin());
            $("[name=password]").setValue(getUserPassword());
            $("#link_login").click();
        });

        step("Проверяем, что на главной странице отображается 'Перейти в личный кабинет'", () -> {
            $("#user_info").shouldHave(Condition.text("Перейти в личный кабинет"));
        });
    }

    @Test
    @DisplayName("Смена города с главной страницы")
    void changeCityTest() {
        step("Открываем главную страницу", () -> {
            open("");
        });

        step("Открываем окно выбора города", () -> {
            $(".toolsbar").$(".tool-city-selector").click();
        });

        step("В поле поиска вводим 'Ворк'", () -> {
            $("#city-list-modal").$("input").setValue("Ворк");
        });

        step("Проверяем, что в результатах поиска есть 'Воркута' и кликаем на этот вариант", () -> {
            $("#city-list-modal").$(".city-list-modal-autocomplete-result").$(byText("Воркута г")).click();
        });

        step("Проверяем, что на главной странице теперь отображается выбранный город", () -> {
            $(".toolsbar").$(".tool-city-selector").shouldHave(Condition.text("Воркута"));
        });
    }

    @Test
    @DisplayName("Шапка страницы остается видна при скроллинге")
    void toolbarVisibilityTest() {
        step("Открываем главную страницу", () -> {
            open("");
        });

        step("Скроллим страницу вниз", () -> {
            $("#index-articles").scrollTo();
        });

        step("Проверяем, что шапка страницы видна", () -> {
            $("#header-toolbar-items").shouldBe(Condition.visible);
        });
    }

    @Test
    @DisplayName("Некий broken тест")
    void brokenTest() {
        $("#cCountCmpare").shouldHave(Condition.text("2"));
    }

    @Test
    @DisplayName("Некий failed тест")
    void failedTest() {
        assertThat(2 + 2, equalTo(5));
    }
}
