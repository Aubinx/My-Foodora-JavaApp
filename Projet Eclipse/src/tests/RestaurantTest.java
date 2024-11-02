package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Location;
import menu.Dish;
import menu.Meal;
import users.Restaurant;

class RestaurantTest {

    private Restaurant restaurant;
    private Meal meal;
    private Dish dish;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant("Test Restaurant", new Location(1.0, 1.0), "restaurantUser", "restaurantPass");
        meal = new Meal("Test Meal", "standard");
        dish = new Dish("Test Dish", "main", "standard", 10.0);
        restaurant.createMeal("Test Meal", "standard");
        restaurant.addDishToMenu(dish, "main");
        restaurant.addDishToMeal(dish.getName(), meal.getName());
    }

    @Test
    void testCreateMeal() {
        assertEquals(1, restaurant.getMeals().size());
        restaurant.createMeal("New Meal", "standard");
        assertEquals(2, restaurant.getMeals().size());
    }

    @Test
    void testRemoveMeal() {
        restaurant.removeMeal(meal);
        assertEquals(0, restaurant.getMeals().size());
    }

    @Test
    void testApplySpecialOfferToMeal() {
        restaurant.applySpecialOfferToMeal(meal);
        assertTrue(meal.isMealOfTheWeek());
        assertEquals(0.10, meal.getDiscountFactor(), 0.001);
    }

    @Test
    void testRemoveSpecialOfferFromMeal() {
        restaurant.applySpecialOfferToMeal(meal);
        restaurant.removeSpecialOfferFromMeal(meal);
        assertFalse(meal.isMealOfTheWeek());
        assertEquals(0.05, meal.getDiscountFactor(), 0.001);
    }

    @Test
    void testSaveMeal() {
        restaurant.saveMeal("Test Meal");
        assertEquals(1, restaurant.getMeals().size());
    }

    @Test
    void testRemoveMealOfTheWeek() {
        restaurant.setMealOfTheWeek("Test Meal");
        restaurant.removeMealOfTheWeek("Test Meal");
        assertFalse(meal.isMealOfTheWeek());
    }

    @Test
    void testApplyDiscountToMeals() {
        restaurant.setDefaultDiscountFactor(0.15);
        restaurant.applyDiscountToMeals();
        for (Meal m : restaurant.getMeals()) {
            assertEquals(0.15, m.getDiscountFactor(), 0.001);
        }
    }

    @Test
    void testApplySpecialDiscountToMeals() {
        restaurant.setSpecialDiscountFactor(0.20);
        restaurant.applySpecialOfferToMeal(meal);
        restaurant.applySpecialDiscountToMeals();
        for (Meal m : restaurant.getMeals()) {
            if (m.isMealOfTheWeek()) {
                assertEquals(0.20, m.getDiscountFactor(), 0.001);
            } else {
                assertEquals(0.05, m.getDiscountFactor(), 0.001);
            }
        }
    }
}
