package data;

import model.Courier;
import model.Order;

import java.util.List;
import java.util.UUID;

public class TestData {

    // ===== Курьер =====

    public static String generateLogin() {
        return "login_" + UUID.randomUUID();
    }

    public static String defaultPassword() {
        return "password123";
    }

    public static String defaultFirstName() {
        return "Ivan";
    }

    public static Courier generateCourier() {
        return new Courier(
                generateLogin(),
                defaultPassword(),
                defaultFirstName()
        );
    }

    // ===== Заказ =====

    public static Order generateOrder(List<String> colors) {
        return new Order(
                "Иван",
                "Иванов",
                "Москва, Красная площадь, 1",
                "4",
                "+79999999999",
                5,
                "2026-06-06",
                "Позвонить за час",
                colors
        );
    }
}