package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import menu.Dish;
import menu.Meal;
import orders.Order;
import users.Restaurant;
import utils.Location;

class OrderTest {

    private Restaurant restaurant;
    private Order order;
    private Dish dish;
    private Meal meal;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant("Test Restaurant", new Location(1.0, 1.0), "restaurantUser", "restaurantPass");
        order = new Order("order1", restaurant);
        dish = new Dish("Test Dish", "main", "standard", 10.0);
        meal = new Meal("Test Meal", "standard");
        restaurant.addDishToMenu(dish, "main");
        restaurant.createMeal("Test Meal", "standard");
        restaurant.addDishToMeal("Test Dish", "Test Meal");
    }

    @Test
    void testAddItem() {
        assertEquals(0, order.getDishes().size());
        assertEquals(0, order.getMeals().size());
        order.addItem("Test Dish");
        assertEquals(1, order.getDishes().size());
        assertEquals(0, order.getMeals().size());
        order.addItem("Test Meal");
        assertEquals(1, order.getMeals().size());
    }

    @Test
    void testFinalizeOrder() {
        LocalDate date = LocalDate.now();
        order.finalizeOrder(date);
        assertEquals(date, order.getDate());
    }
}
