package users;

import utils.Location;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Courier extends User {
    private String name;
    private String surname;
    private String id;
    private Location position;
    private String phone;
    private boolean onDuty;
    private int deliveredOrders;
    private List<String> notifs;

    public Courier(String username, String password, String name, String surname, Location position, String phone) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.id = UUID.randomUUID().toString();
        this.position = position;
        this.phone = phone;
        this.onDuty = false;
        this.deliveredOrders = 0;
        this.notifs = new ArrayList<String>();
        
    }
    
    public List<String> getNotifs() {
    	return notifs; 
    }
    
    public void addNotifs(String s) {
    	notifs.add(s);
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
        return position;
    }

    public void setPosition(Location position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isOnDuty() {
        return onDuty;
    }

    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }

    public int getDeliveredOrders() {
        return deliveredOrders;
    }

    public void incrementDeliveredOrders() {
        this.deliveredOrders++;
    }

    public boolean acceptDelivery() {
        if (!onDuty) {
            System.out.println(name + " " + surname + " has accepted the delivery.");
            incrementDeliveredOrders();
            return true;
        } else {
            System.out.println(name + " " + surname + " is on duty and cannot accept the delivery.");
            return false;
        }
    }

    public void refuseDelivery() {
        System.out.println(name + " " + surname + " has refused the delivery.");
    }

    @Override
    public String toString() {
        return "Courier:\n" + 
               "Name: " + name + "\n" + 
               "Surname: " + surname + "\n" + 
               "Username: "+ getUsername() + "\n"+ 
               "ID: " + id + "\n" + 
               "Position: " + position + "\n" + 
               "Phone: " + phone + "\n" + 
               "On Duty: " + onDuty + "\n" + 
               "Number of deliveries: "+ getDeliveredOrders();
    }
}
