package steps;

import client.CourierClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;

public class CourierSteps {

    private CourierClient courierClient = new CourierClient();

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return courierClient.createCourier(courier);
    }

    @Step("Логин курьера")
    public Response loginCourier(CourierCredentials credentials) {
        return courierClient.login(credentials);
    }

    @Step("Получение id курьера")
    public int getCourierId(Courier courier) {
        return loginCourier(
                new CourierCredentials(
                        courier.getLogin(),
                        courier.getPassword()
                )
        ).then().extract().path("id");
    }

    @Step("Удаление курьера")
    public void deleteCourier(int id) {
        courierClient.delete(id);
    }
}