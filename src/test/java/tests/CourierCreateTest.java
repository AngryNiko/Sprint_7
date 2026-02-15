package tests;

import data.TestData;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

public class CourierCreateTest {

    private CourierSteps courierSteps;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
        courier = TestData.generateCourier();
    }

    @Test
    public void courierCanBeCreated() {
        Response response = courierSteps.createCourier(courier);

        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierSteps.getCourierId(courier);
    }

    @Test
    public void cannotCreateDuplicateCourier() {
        courierSteps.createCourier(courier);

        Response response = courierSteps.createCourier(courier);

        response.then()
                .statusCode(409)
                .body("message", containsString("Этот логин уже используется"));

        courierId = courierSteps.getCourierId(courier);
    }

    @Test
    public void cannotCreateCourierWithoutLogin() {
        Courier invalidCourier = new Courier(
                null,
                TestData.defaultPassword(),
                TestData.defaultFirstName()
        );

        Response response = courierSteps.createCourier(invalidCourier);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierSteps.deleteCourier(courierId);
        }
    }
}