package users;

import orders.*;
import menu.*;
import utils.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurant extends User {
    private String name;
    private String id;
    private Location location;
    private Menu menu;
    private List<Meal> meals;
    private List<Order> orders;
    private double defaultDiscountFactor;
    private double specialDiscountFactor;

    public Restaurant(String name, Location location, String username, String password) {
        super(username, password);
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.location = location;
        this.menu = new Menu();
        this.meals = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.defaultDiscountFactor = 0.05; // Default discount factor
        this.specialDiscountFactor = 0.10; // Special offer discount factor
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Menu getMenu() {
        return menu;
    }

    public List<Meal> getMeals() {
        return meals;
    }
    
    public List<Order> getOrders() {
        return orders;
    }

    public void addDishToMenu(Dish dish, String category) {
        menu.addDish(dish, category);
    }

    public void removeDishFromMenu(Dish dish, String category) {
        menu.removeDish(dish, category);
    }

    public void createMeal(String name, String type) {
        meals.add(new Meal(name, type));
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    public void setDefaultDiscountFactor(double discountFactor) {
        this.defaultDiscountFactor = discountFactor;
        applyDiscountToMeals();
    }

    public double getDefaultDiscountFactor() {
        return defaultDiscountFactor;
    }

    public void setSpecialDiscountFactor(double discountFactor) {
        this.specialDiscountFactor = discountFactor;
        applySpecialDiscountToMeals();
    }

    public double getSpecialDiscountFactor() {
        return specialDiscountFactor;
    }

    public void applySpecialOfferToMeal(Meal meal) {
        meal.setMealOfTheWeek(true, specialDiscountFactor);
    }

    public void removeSpecialOfferFromMeal(Meal meal) {
        meal.setMealOfTheWeek(false, defaultDiscountFactor);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public int getTotalOrders() {
        return orders.size();
    }

    public boolean addDishToMeal(String dishName, String mealName) {
        Meal meal = getMealByName(mealName);
        Dish dish = menu.getDishByName(dishName);
        if (meal != null && dish != null) {
            if (!meal.addDish(dish)) {
                return false;
            }
            return true;
        } else {
            System.out.println("Meal or Dish not found");
            return false;
        }
    }

    public Meal getMealByName(String mealName) {
        for (Meal meal : meals) {
            if (meal.getName().equalsIgnoreCase(mealName)) {
                return meal;
            }
        }
        return null;
    }

    public void saveMeal(String mealName) {
        Meal meal = getMealByName(mealName);
        if (meal == null) {
            throw new IllegalArgumentException("Meal not found");
        }
        meals.add(meal); 
    }

    public void setMealOfTheWeek(String mealName) {
        Meal meal = getMealByName(mealName);
        if (meal == null) {
            throw new IllegalArgumentException("Meal not found");
        }
        meal.setMealOfTheWeek(true, specialDiscountFactor);
    }

    public void removeMealOfTheWeek(String mealName) {
        Meal meal = getMealByName(mealName);
        if (meal == null) {
            throw new IllegalArgumentException("Meal not found");
        }
        meal.setMealOfTheWeek(false, defaultDiscountFactor);
        applySpecialDiscountToMeals();
    }
    
    public void applyDiscountToMeals() {
        for (Meal meal : meals) {
            meal.setDiscountFactor(defaultDiscountFactor);
        }
    }
    
    public void applySpecialDiscountToMeals() {
        for (Meal meal : meals) {
            if (meal.isMealOfTheWeek()) {
                meal.setDiscountFactor(specialDiscountFactor);
            }
        }
    }

    @Override
    public String toString() {
        return "Restaurant:\n" + 
               "Name: " + name + "\n" + 
               "Username: "+ getUsername() + "\n"+ 
               "ID: " + id + "\n" + 
               "Location: " + location +"\n"+
               "Number of orders: "+ orders.size();
    
    }
}
