package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Location;
import fidelitycard.BasicFidelityCard;
import orders.Order;
import users.Customer;
import users.Restaurant;

class CustomerTest {

    private Customer customer;
    private Restaurant restaurant;
    private Order order;

    @BeforeEach
    void setUp() {
        Location address = new Location(1.0, 2.0);
        customer = new Customer("username", "password", "John", "Doe", address, "john.doe@example.com", "1234567890");
        restaurant = new Restaurant("Test Restaurant", new Location(1.0, 1.0), "restaurantUser", "restaurantPass");
        order = new Order("order1", restaurant);
    }

    @Test
    void testHasOrder() {
        assertFalse(customer.hasOrder("order1"));
        customer.placeOrder(order);
        assertTrue(customer.hasOrder("order1"));
    }

    @Test
    void testPlaceOrder() {
        assertEquals(0, customer.getOrderHistory().size());
        customer.placeOrder(order);
        assertEquals(1, customer.getOrderHistory().size());
        assertEquals(order, customer.getOrderHistory().get(0));
        assertEquals(1, restaurant.getOrders().size());
        assertEquals(order, restaurant.getOrders().get(0));
        double expectedFinalPrice = new BasicFidelityCard().applyDiscount(order.getTotalPrice());
        assertEquals(expectedFinalPrice, order.getFinalPrice(), 0.001);
    }
}
