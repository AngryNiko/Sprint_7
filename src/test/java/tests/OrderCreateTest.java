package tests;

import data.TestData;
import io.restassured.response.Response;
import model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private OrderSteps orderSteps = new OrderSteps();
    private List<String> color;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
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
        Order order = TestData.generateOrder(List.of("BLACK"));

        Response response = orderSteps.createOrder(order);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}