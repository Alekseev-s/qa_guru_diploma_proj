package tests;

import allure.ProjectTeam;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.ComparisonBaseSteps;

@Feature("Тесты сравнения товаров")
public class ComparisonTests extends TestBase {
    ComparisonBaseSteps steps = new ComparisonBaseSteps();

    @Test
    @ProjectTeam
    @DisplayName("В шапке станицы отображается текущее кол-во добавленных для сравнения товаров")
    void comparisonLabelTest() {
        steps.openMainPage();
        steps.addProductsToCompare("151802-604870-400858");
        steps.openMainPage();
        steps.checkCompareCount("3");
    }

    @Test
    @ProjectTeam
    @DisplayName("Удаление из списка сравнения c карточки товара")
    void removeComparisonTest() {
        steps.openMainPage();
        steps.addProductsToCompare("151802-604870-400858");
        steps.openProductPage("604870");
        steps.removeProductFromComparison("604870");
        steps.checkCompareCount("2");
    }
}
