package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.*;

public class TestBase {
    @BeforeAll
    public static void setUp() {
        configureDriver();
    }

    @AfterEach
    public void addAttachments() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        if (isRemoteDriver()) {
            attachVideo(getSessionId());
        }
        closeWebDriver();
    }
}
