package users;

import utils.Location;
import fidelitycard.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import orders.Order;

public class Customer extends User {
    private String name;
    private String surname;
    private String id;
    private Location address;
    private String email;
    private String phone;
    private List<Order> orderHistory;
    private FidelityCard fidelityCard;
    private boolean notifySpecialOffers;
    private List<String> notifications;

    public Customer(String username, String password, String name, String surname, Location address, String email, String phone) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.id = UUID.randomUUID().toString();
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.orderHistory = new ArrayList<>();
        this.fidelityCard = new BasicFidelityCard(); 
        this.notifySpecialOffers = false; 
        this.notifications = new ArrayList<String>();
    }
    
    public boolean hasOrder(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }
    
    public List<String> getNotifications(){
    	return notifications;
    }
    
    public void addNotifications(String s) {
    	notifications.add(s);
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
    

    public Location getLocation() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public FidelityCard getFidelityCard() {
        return fidelityCard;
    }

    public void setFidelityCard(FidelityCard fidelityCard) {
        this.fidelityCard = fidelityCard;
    }

    public boolean isNotifySpecialOffers() {
        return notifySpecialOffers;
    }
    
    public String notifs(boolean b) {
    	if (b) {
    		return "on";
    	}
    	return "off";
    }

    public void setNotifySpecialOffers(boolean notifySpecialOffers) {
        this.notifySpecialOffers = notifySpecialOffers;
    }

    public void placeOrder(Order order) {
        if (order.getRestaurant() == null) {
            throw new IllegalArgumentException("Order must be associated with a restaurant.");
        }
        double totalPrice = order.getTotalPrice();
        double finalPrice = fidelityCard.applyDiscount(totalPrice);
        order.setFinalPrice(finalPrice);
        orderHistory.add(order);
        order.getRestaurant().addOrder(order);
        if (fidelityCard instanceof PointFidelityCard) {
            ((PointFidelityCard) fidelityCard).addPoints(totalPrice);
        }
    }

    public Order getOrder(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
    
    public String aboutFC(FidelityCard fc) {
    	if (fc instanceof PointFidelityCard) {
    		return "You have "+((PointFidelityCard) fc).getPoints() +" points on your card.";
    	} return "";
    }
    
    @Override
    public String toString() {
        return "Customer:\n" + 
               "Name: " + name + "\n" + 
               "Surname: " + surname + "\n" + 
               "Username: "+ getUsername() + "\n"+ 
               "ID: " + id + "\n" + 
               "Address: " + address + "\n" + 
               "Email: " + email + "\n" + 
               "Phone: " + phone +"\n" +
               "Notif:" + notifs(notifySpecialOffers) +"\n"+
               "Number of orders: "+ orderHistory.size()+"\n"+
               "Fidelity card: "+ getFidelityCard() + "\n"+
               aboutFC(getFidelityCard()); 
        
        
    }
}
