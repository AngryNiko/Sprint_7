package steps;

import client.OrderClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

public class OrderSteps {

    private OrderClient orderClient = new OrderClient();

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return orderClient.createOrder(order);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return orderClient.getOrders();
    }

    @Step("Отмена заказа по track")
    public Response cancelOrder(int track) {
        return orderClient.cancelOrder(track);
    }
}