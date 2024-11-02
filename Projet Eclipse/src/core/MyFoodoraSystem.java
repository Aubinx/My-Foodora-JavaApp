package core;

import orders.*;
import utils.*;
import menu.*;
import users.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class MyFoodoraSystem {
    private List<User> users;
    private List<Customer> customers;
    private List<Courier> couriers;
    private List<Restaurant> restaurants;
    private List<Manager> managers;
    private List<Order> orders;
    private double serviceFee;
    private double markupPercentage;
    private double deliveryCost;
    private String deliveryPolicy;

    public MyFoodoraSystem() {
        this.users = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.couriers = new ArrayList<>();
        this.restaurants = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.serviceFee = 0.0;
        this.markupPercentage = 0.0;
        this.deliveryCost = 0.0;
        this.deliveryPolicy = "fastest";
    }

    // Ajouter et supprimer des utilisateurs
    public void addUser(User user) {
        users.add(user);
        if (user instanceof Customer) {
            customers.add((Customer) user);
        } else if (user instanceof Courier) {
            couriers.add((Courier) user);
        } else if (user instanceof Restaurant) {
            restaurants.add((Restaurant) user);
        } else if (user instanceof Manager) {
            managers.add((Manager) user);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
        if (user instanceof Customer) {
            customers.remove((Customer) user);
        } else if (user instanceof Courier) {
            couriers.remove((Courier) user);
        } else if (user instanceof Restaurant) {
            restaurants.remove((Restaurant) user);
        } else if (user instanceof Manager) {
            managers.remove((Manager) user);
        }
    }

    // Getters pour les listes d'utilisateurs
    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // Définir les valeurs du service-fee, markup percentage, delivery cost
    public void setServiceFee(double fee) {
        this.serviceFee = fee;
    }

    public void setMarkupPercentage(double percentage) {
        this.markupPercentage = percentage;
    }

    public void setDeliveryCost(double cost) {
        this.deliveryCost = cost;
    }

    public double computeTotalIncome() {
        return orders.stream().mapToDouble(Order::getFinalPrice).sum();
    }
    
    public double lastMonthIncome() {
    	LocalDate currentDate = LocalDate.now();
        LocalDate oneMonthAgo = currentDate.minusMonths(1);
        return orders.stream()
            .filter(order -> order.getDate() != null && !order.getDate().isBefore(oneMonthAgo) && !order.getDate().isAfter(currentDate))
            .mapToDouble(Order::getFinalPrice)
            .sum();
    }

    public double computeTotalProfit() {
        return orders.stream().mapToDouble(order -> 
            order.getFinalPrice() * markupPercentage + serviceFee - deliveryCost
        ).sum();
    }

    public void setDeliveryPolicy(String policy) {
        this.deliveryPolicy = policy;
    }
    
    public void setTargetProfit (String policy) {
    	double targetInc = profitFromTo(LocalDate.now().minusMonths(1),LocalDate.now());
    	switch(policy) {
    		case "deliverycost":
    			this.deliveryCost = computeTargetProfit_DeliveryCost(targetInc);
    			System.out.println("Delivery cost is set to : "+this.deliveryCost);
    			break;
    		case "servicefee":
    			this.serviceFee = computeTargetProfit_ServiceFee(targetInc);
    			System.out.println("Service fee is set to : "+this.serviceFee);
    			break;
    		case "markup":
    			this.markupPercentage = computeTargetProfit_Markup(targetInc);
    			System.out.println("Markup percentage is set to : "+this.markupPercentage*100 +"%");
    			break;
    	}
    }
    
    public void setTargetProfit(String policy, double targetInc) {
    	switch(policy) {
    	case "deliverycost":
    		this.deliveryCost = computeTargetProfit_DeliveryCost(targetInc);
    		System.out.println("Delivery cost is set to : "+this.deliveryCost);
    		break;
    	case "servicefee":
    		this.serviceFee = computeTargetProfit_ServiceFee(targetInc);
    		System.out.println("Service fee is set to : "+this.serviceFee);
    		break;
    	case "markup":
    		this.markupPercentage = computeTargetProfit_Markup(targetInc);
    		System.out.println("Markup percentage is set to : "+this.markupPercentage);
    		break;
    	}
    }

    public Courier selectCourierForDelivery(Location restaurantLocation) {
        switch (deliveryPolicy) {
            case "fastest":
                return couriers.stream()
                        .filter(Courier::isOnDuty)
                        .min(Comparator.comparingDouble(courier -> courier.getLocation().distanceTo(restaurantLocation)))
                        .orElse(null);
            case "fair-occupation":
                return couriers.stream()
                        .filter(Courier::isOnDuty)
                        .min(Comparator.comparingInt(Courier::getDeliveredOrders))
                        .orElse(null);
            default:
                throw new IllegalArgumentException("Unknown delivery policy: " + deliveryPolicy);
        }
    }

    // Notifier les utilisateurs des offres spéciales
    public void notifySpecialOffers(String offer) {
        customers.stream()
            .filter(Customer::isNotifySpecialOffers)
            .forEach(customer -> {
                customer.addNotifications(offer);
            });
    }
    
    public void notifyCourier(Courier c, String s) {
    	c.addNotifs(s);
    }

    public double computeTargetProfit_DeliveryCost(double targetprofit) {
    	double income = lastMonthIncome();
        int numOrders = numOrdersLastMonth();
        double deliveryCost = (income * markupPercentage + serviceFee * numOrders - targetprofit) / numOrders;
        return deliveryCost;
    }
    
    public int numOrdersLastMonth() {
    	int n = 0;
    	LocalDate currentDate = LocalDate.now();
        LocalDate oneMonthAgo = currentDate.minusMonths(1);
    	for (Order order : orders) {
    		if (order.getDate() != null && !order.getDate().isBefore(oneMonthAgo) && !order.getDate().isAfter(currentDate)) {
    			n+=1;
    		}
    	}
    	return n;
    }

    public double computeTargetProfit_ServiceFee(double targetProfit) {
        double income = lastMonthIncome();
        int numOrders = numOrdersLastMonth()
;        double serviceFee = (targetProfit + deliveryCost * numOrders - income * markupPercentage) / numOrders;
        return serviceFee;
    }

    public double computeTargetProfit_Markup(double targetProfit) {
    	double income = lastMonthIncome();
        int numOrders = numOrdersLastMonth();
        double markupPercentage = (targetProfit + deliveryCost * numOrders - serviceFee * numOrders) / income;
        return markupPercentage;
    }

    public List<Meal> getMostOrderedHalfMeals() {
        return orders.stream()
                .flatMap(order -> order.getMeals().stream())
                .filter(Meal::isHalfMeal)
                .sorted(Comparator.comparingInt(Meal::getOrderCount).reversed())
                .collect(Collectors.toList());
    }

    public List<Meal> getLeastOrderedHalfMeals() {
        return orders.stream()
                .flatMap(order -> order.getMeals().stream())
                .filter(Meal::isHalfMeal)
                .sorted(Comparator.comparingInt(Meal::getOrderCount))
                .collect(Collectors.toList());
    }

    public List<Dish> getMostOrderedItems() {
        return orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .sorted(Comparator.comparingInt(Dish::getOrderCount).reversed())
                .collect(Collectors.toList());
    }

    public List<Dish> getLeastOrderedItems() {
        return orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .sorted(Comparator.comparingInt(Dish::getOrderCount))
                .collect(Collectors.toList());
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Restaurant getRestaurantByName(String name) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name)) {
                return restaurant;
            }
        }
        return null;
    }

    public Order getOrderByName(String name) {
        for (Order order : orders) {
            if (order.getOrderId().equals(name)) {
                return order;
            }
        }
        return null;
    }
    
    public double profitFromTo(LocalDate startDate, LocalDate endDate) {
        return orders.stream()
            .filter(order -> !order.getDate().isBefore(startDate) && !order.getDate().isAfter(endDate))
            .mapToDouble(order -> 
                order.getFinalPrice() * markupPercentage + serviceFee - deliveryCost
            ).sum();
    }
    
    public void addOrder (Order o) {
    	orders.add(o);
    }
    
    public Customer findCustomerByOrderId(String orderId) {
        for (Customer customer : customers) {
            if (customer.hasOrder(orderId)) {
                return customer;
            }
        }
        return null;
    }
}
