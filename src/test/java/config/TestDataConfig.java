package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/testData.properties"
})
public interface TestDataConfig extends Config {
    @Key("user.login")
    String userLogin();

    @Key("user.password")
    String userPassword();
}
