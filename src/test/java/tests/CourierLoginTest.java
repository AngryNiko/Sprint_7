package tests;

import data.TestData;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.apache.http.HttpStatus.*;
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
        courierId = courierSteps.getCourierId(courier);
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
                .statusCode(SC_OK)
                .body("id", notNullValue());

        courierId = response.then().extract().path("id");
    }

    @Test
    public void loginWithoutLoginReturns400() {
        CourierCredentials credentials =
                new CourierCredentials(null, "password");

        Response response = courierSteps.loginCourier(credentials);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithoutPasswordReturns400() {
        CourierCredentials credentials =
                new CourierCredentials(courier.getLogin(), "");

        Response response = courierSteps.loginCourier(credentials);

        response.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithWrongPasswordReturnsError() {
        Response response = courierSteps.loginCourier(
                new CourierCredentials(courier.getLogin(), "wrongPassword")
        );

        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithNonExistingUserReturnsError() {
        Response response = courierSteps.loginCourier(
                new CourierCredentials(TestData.generateLogin(), "1234")
        );

        response.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
        }
    }
}