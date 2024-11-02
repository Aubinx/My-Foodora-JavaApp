package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import users.Courier;
import utils.Location;

class CourierTest {

    private Courier courier;

    @BeforeEach
    void setUp() {
        Location location = new Location(0.0, 0.0);
        courier = new Courier("username", "password", "John", "Doe", location, "1234567890");
    }

    @Test
    void testIncrementDeliveredOrders() {
        assertEquals(0, courier.getDeliveredOrders());
        courier.incrementDeliveredOrders();
        assertEquals(1, courier.getDeliveredOrders());
    }

    @Test
    void testAcceptDelivery() {
        courier.setOnDuty(false);
        boolean accepted = courier.acceptDelivery();
        assertTrue(accepted);
        assertEquals(1, courier.getDeliveredOrders());
        courier.setOnDuty(true);
        accepted = courier.acceptDelivery();
        assertFalse(accepted);
        assertEquals(1, courier.getDeliveredOrders());
    }

    @Test
    void testRefuseDelivery() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        courier.refuseDelivery();
        assertEquals("John Doe has refused the delivery.\n", outContent.toString());
        System.setOut(System.out);
    }
}
