package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;

import static client.Endpoints.*;

public class OrderClient {

    public Response createOrder(Order order) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER);
    }

    public Response getOrders() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .get(GET_ORDERS);
    }

    public Response cancelOrder(int track) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .queryParam("track", track)
                .put(CANCEL_ORDER);
    }
}