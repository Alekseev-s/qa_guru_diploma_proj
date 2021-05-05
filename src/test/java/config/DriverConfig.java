package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/driver.properties"
})
public interface DriverConfig extends Config {
    @Key("web.remote.driver.user")
    String remoteDriverUser();

    @Key("web.remote.driver.password")
    String remoteDriverPassword();

    @Key("web.browser.size")
    String browserSize();

    @Key("web.browser")
    String defaultBrowser();

    @Key("web.browser.version")
    String defaultBrowserVersion();

    @Key("web.base.url")
    String baseUrl();
}
