package com.example.passwordmanager.general;

import com.example.passwordmanager.model.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.example.passwordmanager.tags.TestSuiteTags.HEALTH;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Java6Assertions.assertThat;


public class PasswordManagerHealthCheckTest {
    private static RequestSpecification request;

    @BeforeAll
    public static void setupResponseSpecification() {
        RestAssured.baseURI = "http://localhost:8080/";
        request = given();
    }

    @Test
    @Tag(HEALTH)
    @DisplayName("Should be able to hit the health endpoint")
    void healthCheck() {
        Response response = request
                .auth()
                .basic("admin", "admin")
                .get("actuator/health").then().statusCode(SC_OK).extract().response();

        Status status = response.getBody().as(Status.class);
        assertThat(status.getStatus()).isEqualTo("UP");
    }
}
