package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;

import static client.Endpoints.*;

public class CourierClient {

    public Response createCourier(Courier courier) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .post(CREATE_COURIER);
    }

    public Response login(CourierCredentials credentials) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(credentials)
                .post(LOGIN_COURIER);
    }

    public Response delete(int id) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .delete(DELETE_COURIER + id);
    }
}