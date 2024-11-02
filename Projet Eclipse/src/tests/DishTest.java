package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import menu.Dish;

class DishTest {

    private Dish dish;

    @BeforeEach
    void setUp() {
        dish = new Dish("Test Dish", "main", "standard", 10.0);
    }

    @Test
    void testIncrementOrderCount() {
        assertEquals(0, dish.getOrderCount());
        dish.incrementOrderCount();
        assertEquals(1, dish.getOrderCount());
        dish.incrementOrderCount();
        assertEquals(2, dish.getOrderCount());
    }
}
