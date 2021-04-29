package helpers;

import config.TestDataConfig;
import org.aeonbits.owner.ConfigFactory;

public class TestDataHelper {
    private static TestDataConfig dataConfig;

    private static TestDataConfig getTestDataConfig() {
        if (dataConfig == null) {
            return dataConfig = ConfigFactory.create(TestDataConfig.class, System.getProperties());
        } else {
            return dataConfig;
        }
    }

    public static String getUserLogin() {
        return getTestDataConfig().userLogin();
    }

    public static String getUserPassword() {
        return getTestDataConfig().userPassword();
    }
}
