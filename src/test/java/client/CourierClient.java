package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;

public class CourierClient {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public Response createCourier(Courier courier) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    public Response login(CourierCredentials credentials) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(credentials)
                .post("/api/v1/courier/login");
    }

    public Response delete(int id) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .delete("/api/v1/courier/" + id);
    }
}