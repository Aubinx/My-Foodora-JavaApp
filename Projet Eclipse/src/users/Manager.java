package users;

import utils.*;
import core.*;
import java.util.List;
import java.util.UUID;

public class Manager extends User {
    private String name;
    private String surname;
    private String id;

    public Manager(String username, String password, String name, String surname) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.id = UUID.randomUUID().toString();
    }

    public Location getLocation() {
        return null;
    }
    
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getId() {
        return id;
    }

    public void addUser(User user, List<User> users) {
        users.add(user);
    }

    public void removeUser(String userID, List<User> users) {
        users.removeIf(u -> u.getUserID().equals(userID));
    }


    public void setServiceFee(double fee, MyFoodoraSystem system) {
        system.setServiceFee(fee);
    }

    public void setMarkupPercentage(double percentage, MyFoodoraSystem system) {
        system.setMarkupPercentage(percentage);
    }

    public void setDeliveryCost(double cost, MyFoodoraSystem system) {
        system.setDeliveryCost(cost);
    }

    public double computeTotalIncome(MyFoodoraSystem system) {
        return system.computeTotalIncome();
    }

    public double computeTotalProfit(MyFoodoraSystem system) {
        return system.computeTotalProfit();
    }

    public double computeAverageIncomePerCustomer(MyFoodoraSystem system) {
        int numCustomers = system.getCustomers().size();
        if (numCustomers == 0) {
            return 0;
        }
        return computeTotalIncome(system) / numCustomers;
    }

    public User getMostActiveCourier(MyFoodoraSystem system) {
        return system.getCouriers().stream()
            .max((c1, c2) -> Integer.compare(c1.getDeliveredOrders(), c2.getDeliveredOrders()))
            .orElse(null);
    }

    public User getLeastActiveCourier(MyFoodoraSystem system) {
        return system.getCouriers().stream()
            .min((c1, c2) -> Integer.compare(c1.getDeliveredOrders(), c2.getDeliveredOrders()))
            .orElse(null);
    }

    public Restaurant getMostSellingRestaurant(MyFoodoraSystem system) {
        return system.getRestaurants().stream()
            .max((r1, r2) -> Integer.compare(r1.getTotalOrders(), r2.getTotalOrders()))
            .orElse(null);
    }

    public Restaurant getLeastSellingRestaurant(MyFoodoraSystem system) {
        return system.getRestaurants().stream()
            .min((r1, r2) -> Integer.compare(r1.getTotalOrders(), r2.getTotalOrders()))
            .orElse(null);
    }

    public void setDeliveryPolicy(String policy, MyFoodoraSystem system) {
        system.setDeliveryPolicy(policy);
    }

    @Override
    public String toString() {
        return "Manager:\n" + 
               "Name: " + name + "\n" + 
               "Surname: " + surname + "\n" + 
               "Username: "+ getUsername() + "\n"+ 
               "ID: " + id;
    }
}





