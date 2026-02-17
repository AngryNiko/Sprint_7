package tests;

import data.TestData;
import io.restassured.response.Response;
import model.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private OrderSteps orderSteps = new OrderSteps();
    private List<String> color;
    private int track;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] data() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {null}
        };
    }

    @Test
    public void createOrderTest() {
        Order order = TestData.generateOrder(color);

        Response response = orderSteps.createOrder(order);

        track = response.then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue())
                .extract()
                .path("track");
    }

    @After
    public void cancelOrder() {
        if (track != 0) {
            orderSteps.cancelOrder(track);
        }
    }
}