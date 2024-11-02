package menu;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Meal {
    private String name;
    private Dish starter;
    private Dish mainDish;
    private Dish dessert;
    private String foodCategory; // standard, vegetarian, gluten-free
    private double discountFactor;
    private boolean isMealOfTheWeek;
    private int orderCount;

    public Meal(String name, String foodCategory) {
        this.name = name;
        this.foodCategory = foodCategory;
        this.discountFactor = 0.05; // Default discount factor
        this.isMealOfTheWeek = false;
        this.orderCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public boolean addDish(Dish dish) {
        if (!dish.getFoodCategory().equalsIgnoreCase(foodCategory)) {
            System.out.println("Dish category does not match meal category.");
            return false;
        }
        
        switch (dish.getDishCategory().toLowerCase()) {
            case "starter":
                this.starter = dish;
                break;
            case "main":
                this.mainDish = dish;
                break;
            case "dessert":
                this.dessert = dish;
                break;
            default:
                System.out.println("Unknown dish category: " + dish.getDishCategory());
                return false;
        }
        return true;
    }

    public void setMealOfTheWeek(boolean isMealOfTheWeek, double specialDiscountFactor) {
        this.isMealOfTheWeek = isMealOfTheWeek;
        if (isMealOfTheWeek) {
            this.discountFactor = specialDiscountFactor;
        } else {
            this.discountFactor = 0.05; // Reset to default discount factor
        }
    }

    public double getPrice() {
        double totalPrice = 0.0;
        if (starter != null) {
            totalPrice += starter.getPrice();
        }
        if (mainDish != null) {
            totalPrice += mainDish.getPrice();
        }
        if (dessert != null) {
            totalPrice += dessert.getPrice();
        }
        double discountedPrice = totalPrice * (1 - discountFactor);
        BigDecimal bd = new BigDecimal(discountedPrice).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean isHalfMeal() {
        return (starter != null && mainDish != null && dessert == null) ||
               (starter == null && mainDish != null && dessert != null);
    }

    public boolean isFullMeal() {
        return starter != null && mainDish != null && dessert != null;
    }

    public void incrementOrderCount() {
        this.orderCount++;
    }

    public int getOrderCount() {
        return orderCount;
    }
    
    public void setDiscountFactor(double discountFactor) {
        this.discountFactor = discountFactor;
    }
    
    public boolean isMealOfTheWeek() {
        return isMealOfTheWeek;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (").append(foodCategory).append("):\n");
        if (starter != null) {
            sb.append("Starter: ").append(starter).append("\n");
        }
        if (mainDish != null) {
            sb.append("Main Dish: ").append(mainDish).append("\n");
        }
        if (dessert != null) {
            sb.append("Dessert: ").append(dessert).append("\n");
        }
        sb.append("Price: ").append(getPrice()).append("\n");
        if (isMealOfTheWeek) {
            sb.append("This is the meal of the week!\n");
        }
        return sb.toString();
    }

	public double getDiscountFactor() {
		return discountFactor;
	}
}
