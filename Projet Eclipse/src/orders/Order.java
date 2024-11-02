package orders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import menu.Dish;
import menu.Meal;
import users.Restaurant;

public class Order {
    private String orderId;
    private Restaurant restaurant;
    private List<Dish> dishes;
    private List<Meal> meals;
    private double totalPrice;
    private double finalPrice;
    private LocalDate date;

    public Order(String orderId, Restaurant restaurant) {
        this.orderId = orderId;
        this.restaurant = restaurant;
        this.dishes = new ArrayList<>();
        this.meals = new ArrayList<>();
        this.totalPrice = 0.0;
        this.finalPrice = 0.0;
        this.date = LocalDate.now();
    }

    public void setTotalPrice(double price) {
    	this.totalPrice = price;
    }
    
    public void addDish(Dish dish) {
        dishes.add(dish);
        totalPrice += dish.getPrice();
        dish.incrementOrderCount();
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        totalPrice += meal.getPrice();
        meal.incrementOrderCount();
    }

    public String getOrderId() {
    	return orderId;
    }
    
    public List<Dish> getDishes() {
        return dishes;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void addItem(String itemName) {
        Dish dish = restaurant.getMenu().getDishByName(itemName);
        if (dish != null) {
            addDish(dish);
            return;
        }

        Meal meal = restaurant.getMealByName(itemName);
        if (meal != null) {
            addMeal(meal);
            return;
        }

        throw new IllegalArgumentException("Item not found in the menu of the restaurant");
    }

    public void finalizeOrder(LocalDate date) {
        System.out.println("Order finalized on: " + date);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Restaurant: ").append(restaurant.getName()).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Dishes:\n");
        for (Dish dish : dishes) {
            sb.append("  ").append(dish).append("\n");
        }
        sb.append("Meals:\n");
        for (Meal meal : meals) {
            sb.append("  ").append(meal).append("\n");
        }
        sb.append("Total Price: ").append(totalPrice).append("\n");
        return sb.toString();
    }
}
