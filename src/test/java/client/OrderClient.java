package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;

public class OrderClient {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public Response createOrder(Order order) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/v1/orders");
    }

    public Response getOrders() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .get("/api/v1/orders");
    }
}