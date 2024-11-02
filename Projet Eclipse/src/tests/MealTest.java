package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import menu.Dish;
import menu.Meal;

class MealTest {

    private Meal meal;
    private Dish starter;
    private Dish mainDish;
    private Dish dessert;

    @BeforeEach
    void setUp() {
        meal = new Meal("Test Meal", "standard");
        starter = new Dish("Starter", "starter", "standard", 5.0);
        mainDish = new Dish("Main Dish", "main", "standard", 10.0);
        dessert = new Dish("Dessert", "dessert", "standard", 5.0);
    }

    @Test
    void testIsHalfMeal() {
        meal.addDish(starter);
        meal.addDish(mainDish);
        assertTrue(meal.isHalfMeal());
        assertFalse(meal.isFullMeal());
        meal.addDish(dessert);
        assertFalse(meal.isHalfMeal());
    }

    @Test
    void testIsFullMeal() {
        meal.addDish(starter);
        meal.addDish(mainDish);
        assertFalse(meal.isFullMeal());
        meal.addDish(dessert);
        assertTrue(meal.isFullMeal());
    }

    @Test
    void testIncrementOrderCount() {
        assertEquals(0, meal.getOrderCount());
        meal.incrementOrderCount();
        assertEquals(1, meal.getOrderCount());
        meal.incrementOrderCount();
        assertEquals(2, meal.getOrderCount());
    }

}
