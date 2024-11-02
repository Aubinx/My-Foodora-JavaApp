package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.MyFoodoraSystem;
import users.Customer;
import users.Manager;
import users.Restaurant;
import users.User;
import utils.Location;

import java.util.ArrayList;
import java.util.List;

class ManagerTest {

    private Manager manager;
    private MyFoodoraSystem system;
    private List<User> users;
    private Customer customer;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        manager = new Manager("managerUser", "managerPass", "Jane", "Doe");
        system = new MyFoodoraSystem();
        users = new ArrayList<>();
        Location address = new Location(1.0, 2.0);
        customer = new Customer("customerUser", "customerPass", "John", "Doe", address, "john.doe@example.com", "1234567890");
        restaurant = new Restaurant("Test Restaurant", new Location(1.0, 1.0), "restaurantUser", "restaurantPass");
        system.addUser(customer);
        system.addUser(restaurant);
    }

    @Test
    void testAddUser() {
        assertEquals(0, users.size());
        manager.addUser(customer, users);
        assertEquals(1, users.size());
        assertTrue(users.contains(customer));
    }

    @Test
    void testRemoveUser() {
        manager.addUser(customer, users);
        assertEquals(1, users.size());
        manager.removeUser(customer.getUserID(), users);
        assertEquals(0, users.size());
        assertFalse(users.contains(customer));
    }

    @Test
    void testComputeTotalIncome() {
        double income = manager.computeTotalIncome(system);
        assertEquals(0.0, income, 0.001); 
    }

    @Test
    void testComputeTotalProfit() {
        double profit = manager.computeTotalProfit(system);
        assertEquals(0.0, profit, 0.001); 
    }

    @Test
    void testComputeAverageIncomePerCustomer() {
        double averageIncome = manager.computeAverageIncomePerCustomer(system);
        assertEquals(0.0, averageIncome, 0.001);
    }
}
