package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.MyFoodoraSystem;
import orders.Order;
import users.Customer;
import users.Courier;
import users.Manager;
import users.Restaurant;
import users.User;
import utils.Location;

class MyFoodoraSystemTest {

    private MyFoodoraSystem system;

    @BeforeEach
    void setUp() {
        system = new MyFoodoraSystem();
        
        // Adding dummy data for tests
        Manager manager = new Manager("manager", "password", "ManagerName", "ManagerSurname");
        system.addUser(manager);
        manager.setDeliveryCost(4.0, system);
        manager.setMarkupPercentage(0.2, system);
        manager.setServiceFee(3, system);
        Customer customer = new Customer("cust", "password", "John", "Doe", new Location(1.0, 2.0), "john@example.com", "1234567890");
        system.addUser(customer);

        Courier courier = new Courier("courier", "password", "CourierName", "CourierSurname", new Location(1.5, 2.0), "0987654321");
        system.addUser(courier);
        courier.setOnDuty(true);

        Restaurant restaurant = new Restaurant("restaurant", new Location(1.0, 2.0), "restaurant", "password");
        system.addUser(restaurant);

        Order order1 = new Order("order1", restaurant);
        order1.setDate(LocalDate.now().minusDays(10));
        order1.setFinalPrice(100);
        system.addOrder(order1);

        Order order2 = new Order("order2", restaurant);
        order2.setDate(LocalDate.now().minusDays(20));
        order2.setFinalPrice(200);
        system.addOrder(order2);

        Order order3 = new Order("order3", restaurant);
        order3.setDate(LocalDate.now().minusMonths(2));
        order3.setFinalPrice(300);
        system.addOrder(order3);
    }

    @Test
    void testComputeTotalIncome() {
        double totalIncome = system.computeTotalIncome();
        assertEquals(600, totalIncome);
    }

    @Test
    void testLastMonthIncome() {
        double lastMonthIncome = system.lastMonthIncome();
        assertEquals(300, lastMonthIncome); // Only order1 and order2 are within the last month
    }

    @Test
    void testComputeTotalProfit() {
        system.setMarkupPercentage(0.1);
        system.setServiceFee(2.0);
        system.setDeliveryCost(1.0);
        double totalProfit = system.computeTotalProfit();
        assertEquals(63, totalProfit); 
    }

    @Test
    void testSelectCourierForDelivery() {
        system.setDeliveryPolicy("fastest");
        Location restaurantLocation = new Location(1.0, 2.0);
        Courier courier = system.selectCourierForDelivery(restaurantLocation);
        assertNotNull(courier);
        assertEquals("courier", courier.getUsername());
    }

    @Test
    void testNotifySpecialOffers() {
        Customer customer = system.getCustomers().get(0);
        customer.setNotifySpecialOffers(true);
        system.notifySpecialOffers("Special offer!");
        assertTrue(customer.getNotifications().contains("Special offer!"));
    }

    @Test
    void testNotifyCourier() {
        Courier courier = system.getCouriers().get(0);
        system.notifyCourier(courier, "You have a new delivery");
        assertTrue(courier.getNotifs().contains("You have a new delivery"));
    }

    @Test
    void testComputeTargetProfit_DeliveryCost() {
        system.setMarkupPercentage(0.1);
        system.setServiceFee(2.0);
        double deliveryCost = system.computeTargetProfit_DeliveryCost(100);
        assertEquals((300 * 0.1 + 2 * 2 - 100) / 2, deliveryCost);
    }

    @Test
    void testNumOrdersLastMonth() {
        int numOrders = system.numOrdersLastMonth();
        assertEquals(2, numOrders);
    }

    @Test
    void testComputeTargetProfit_ServiceFee() {
        system.setMarkupPercentage(0.1);
        system.setDeliveryCost(1.0);
        double serviceFee = system.computeTargetProfit_ServiceFee(100);
        assertEquals((100 + 1 * 2 - 300 * 0.1) / 2, serviceFee);
    }

    @Test
    void testComputeTargetProfit_Markup() {
        system.setServiceFee(2.0);
        system.setDeliveryCost(1.0);
        double markup = system.computeTargetProfit_Markup(100);
        assertEquals(0.32666666666666666, markup);
    }

    @Test
    void testLogin() {
        User user = system.login("manager", "password");
        assertNotNull(user);
        assertEquals("manager", user.getUsername());

        User invalidUser = system.login("invalid", "password");
        assertNull(invalidUser);
    }

    @Test
    void testProfitFromTo() {
        LocalDate startDate = LocalDate.now().minusMonths(2);
        LocalDate endDate = LocalDate.now();
        double profit = system.profitFromTo(startDate, endDate);
        assertEquals(117.0, profit);
    }

}
