package tests;

import data.TestData;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    private CourierSteps courierSteps;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
        courier = TestData.generateCourier();
        courierSteps.createCourier(courier);
    }

    @Test
    public void courierCanLogin() {
        Response response = courierSteps.loginCourier(
                new CourierCredentials(
                        courier.getLogin(),
                        courier.getPassword()
                )
        );

        response.then()
                .statusCode(200)
                .body("id", notNullValue());

        courierId = response.then().extract().path("id");
    }

    @Test
    public void loginRequiresAllFields() {
        Response response = courierSteps.loginCourier(
                new CourierCredentials(courier.getLogin())
        );

        response.then()
                .statusCode(anyOf(is(400), is(504)));
    }

    @Test
    public void loginWithWrongPasswordReturnsError() {
        Response response = courierSteps.loginCourier(
                new CourierCredentials(courier.getLogin(), "wrongPassword")
        );

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithNonExistingUserReturnsError() {
        Response response = courierSteps.loginCourier(
                new CourierCredentials(TestData.generateLogin(), "1234")
        );

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        if (courierId == 0) {
            courierId = courierSteps.getCourierId(courier);
        }
        courierSteps.deleteCourier(courierId);
    }
}