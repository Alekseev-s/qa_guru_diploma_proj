package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.DriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.codeborne.selenide.Selenide.getWebDriverLogs;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static java.lang.String.join;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class DriverHelper {
    private static DriverConfig driverConfig;

    private static DriverConfig getDriverConfig() {
        if (driverConfig == null) {
            return ConfigFactory.create(DriverConfig.class, System.getProperties());
        } else {
            return driverConfig;
        }
    }

    public static String getRemoteDriver() {
        return String.format(System.getProperty("web.remote.driver"),
                getDriverConfig().remoteDriverUser(),
                getDriverConfig().remoteDriverPassword());
    }

    public static boolean isRemoteDriver() {
        return System.getProperty("web.remote.driver") != null;
    }

    public static String getVideoStorage() {
        return System.getProperty("web.remote.video.storage");
    }

    public static boolean isVideo() {
        return getVideoStorage() != null;
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString().replace("selenoid", "");
    }

    public static String getConsoleLogs() {
        return join("\n", getWebDriverLogs(BROWSER));
    }

    public static String getBrowserSize() {
        return getDriverConfig().browserSize();
    }

    public static String getDefaultBrowser() {
        return getDriverConfig().defaultBrowser();
    }

    public static String getBaseUrl() {
        return getDriverConfig().baseUrl();
    }

    public static void configureDriver() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browser = System.getProperty("browser", getDefaultBrowser());
        Configuration.browserSize = getBrowserSize();
        Configuration.baseUrl = getBaseUrl();

        if (isRemoteDriver()) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.browserCapabilities = capabilities;
            Configuration.remote = getRemoteDriver();
        }
    }
}
