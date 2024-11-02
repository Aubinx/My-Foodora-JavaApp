package menu;

public class Dish {
    private String name;
    private String dishCategory; 
    private String foodCategory; // standard, vegetarian, gluten-free
    private double price;
    private int orderCount;

    public Dish(String name, String dishCategory, String foodCategory, double price) {
        this.name = name;
        this.dishCategory = dishCategory;
        this.foodCategory = foodCategory;
        this.price = price;
        this.orderCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getDishCategory() {
		return dishCategory;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public double getPrice() {
        return price;
    }

    public void incrementOrderCount() {
        this.orderCount++;
    }

    public int getOrderCount() {
        return orderCount;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s) - %.2f", name, dishCategory, foodCategory, price);
    }
}
