package menu;


import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<Dish> starters;
    private List<Dish> mainDishes;
    private List<Dish> desserts;

    public Menu() {
        this.starters = new ArrayList<>();
        this.mainDishes = new ArrayList<>();
        this.desserts = new ArrayList<>();
    }

    public void addDish(Dish dish, String category) {
        switch (category) {
            case "starter":
                starters.add(dish);
                break;
            case "main":
                mainDishes.add(dish);
                break;
            case "dessert":
                desserts.add(dish);
                break;
        }
    }
    
    public void removeDish(Dish dish, String category) {
        switch (category) {
            case "starter":
                starters.remove(dish);
                break;
            case "main":
                mainDishes.remove(dish);
                break;
            case "dessert":
                desserts.remove(dish);
                break;
        }
    }

    public Dish getDishByName(String name) {
        for (Dish dish : starters) {
            if (dish.getName().equalsIgnoreCase(name)) {
                return dish;
            }
        }
        for (Dish dish : mainDishes) {
            if (dish.getName().equalsIgnoreCase(name)) {
                return dish;
            }
        }
        for (Dish dish : desserts) {
            if (dish.getName().equalsIgnoreCase(name)) {
                return dish;
            }
        }
        return null;
    }	
    
    public List<Dish> getStarters() {
        return starters;
    }

    public List<Dish> getMainDishes() {
        return mainDishes;
    }

    public List<Dish> getDesserts() {
        return desserts;
    }
    
    public List<Dish> getAllDishes() {
        List<Dish> allDishes = new ArrayList<>();
        allDishes.addAll(starters);
        allDishes.addAll(mainDishes);
        allDishes.addAll(desserts);
        return allDishes;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Starters:\n");
        for (Dish dish : starters) {
            sb.append(dish).append("\n");
        }

        sb.append("\nMain Dishes:\n");
        for (Dish dish : mainDishes) {
            sb.append(dish).append("\n");
        }

        sb.append("\nDesserts:\n");
        for (Dish dish : desserts) {
            sb.append(dish).append("\n");
        }
        return sb.toString();
    }
}