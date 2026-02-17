package tests;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    private OrderSteps orderSteps;

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
    }

    @Test
    public void orderListReturnsOrders() {
        Response response = orderSteps.getOrders();

        response.then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}