package api;

import io.restassured.specification.RequestSpecification;

import static filter.LogFilter.filters;
import static helpers.DriverHelper.getBaseUrl;
import static io.restassured.RestAssured.given;

public class CustomSpec {
    private final RequestSpecification request = given()
            .baseUri(getBaseUrl())
            .filter(filters().withCustomTemplates());

    public static CustomSpec spec() {
        return new CustomSpec();
    }

    public RequestSpecification request() {
        return request;
    }
}
