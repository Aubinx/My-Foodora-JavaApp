package core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import users.*;
import utils.Location;
import menu.*;
import fidelitycard.*;
import orders.*;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;


import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CommandLineInterface {
    private MyFoodoraSystem system;
    private User loggedInUser;

    public CommandLineInterface(MyFoodoraSystem system) {
        this.system = system;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break; 
            }
            String commandLine = scanner.nextLine();
            String[] commandParts = commandLine.split(" ");
            executeCommand(commandParts);
            if (commandParts[0].equalsIgnoreCase("exit")) {
                break;
            }
        }
    }

    private void executeCommand(String [] commandParts) {
    	String commandName = commandParts[0];
        try {    
        	switch (commandName) {
                case "login":
                    login(commandParts);
                    break;
                case "logout":
                    logout();
                    break;
                case "showProfile":
                	showProfile(commandParts);
                	break;
                case "registerRestaurant":
                    registerRestaurant(commandParts);
                    break;
                case "registerCustomer":
                    registerCustomer(commandParts);
                    break;
                case "registerCourier":
                    registerCourier(commandParts);
                    break;
                case "addDishRestaurantMenu":
                    addDishRestaurantMenu(commandParts);
                    break;
                case "createMeal":
                    createMeal(commandParts);
                    break;
                case "addDish2Meal":
                    addDish2Meal(commandParts);
                    break;
                case "showMeal":
                    showMeal(commandParts);
                    break;
                case "showMenu":
                	showMenu(commandParts);
                	break;
                case "saveMeal":
                    saveMeal(commandParts);
                    break;
                case "setSpecialOffer":
                    setSpecialOffer(commandParts);
                    break;
                case "removeFromSpecialOffer":
                    removeFromSpecialOffer(commandParts);
                    break;
                case "notifOn":
                	notifOn(commandParts);
                	break;
                case "notifOff":
                	notifOff(commandParts);
                	break;
                case "showNotifs":
                	showNotifs(commandParts);
                	break;
                case "createOrder":
                    createOrder(commandParts);
                    break;
                case "addItem2Order":
                    addItem2Order(commandParts);
                    break;
                case "showOrder":
                    showOrder(commandParts);
                    break;
                case "endOrder":
                    endOrder(commandParts);
                    break;
                case "histoOrders":
                	histoOrders(commandParts);
                	break;
                case "onDuty":
                    onDuty(commandParts);
                    break;
                case "offDuty":
                    offDuty(commandParts);
                    break;
                case "findDeliverer":
                    findDeliverer(commandParts);
                    break;
                case "setDeliveryPolicy":
                    setDeliveryPolicy(commandParts);
                    break;
                case "setServiceFee":
                    setServiceFee(commandParts);
                    break;    
                case "setMarkupPerc":
                    setMarkupPerc(commandParts);
                    break;
                case "setDeliveryCost":
                    setDeliveryCost(commandParts);
                    break;
                case "setProfitPolicy":
                	setProfitPolicy(commandParts);
                	break;
                case "associateCard":
                    associateCard(commandParts);
                    break;
                case "showCourierDeliveries":
                    showCourierDeliveries();
                    break;
                case "showRestaurantTop":
                    showRestaurantTop();
                    break;
                case "showCustomers":
                    showCustomers();
                    break;
                case "showOrders":
                    showOrders();
                    break;
                case "showMenuItem":
                    showMenuItem(commandParts);
                    break;
                case "showTotalProfit":
                    showTotalProfit(commandParts);
                    break;
                case "defaultFactorDiscount":
                	defaultFactorDiscount(commandParts);
                	break;
                case "specialFactorDiscount":
                    specialFactorDiscount(commandParts);
                    break;
                case "lastMonthIncome":
                	lastMonthIncome(commandParts);
                	break;
                case "totalIncome":
                	totalIncome(commandParts);
                	break;
                case "runtest":
                    runtest(commandParts);
                    break;
                case "setup":
                    setup(commandParts);
                    break;
                case "help":
                    help();
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
                    break;
            }
        }catch (Exception e) {
        	System.out.println("Error: "+ e.getMessage());
        }
    }
    

    private void login(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: login <username> <password>");
            return;
        }
        String username = args[1];
        String password = args[2];
        loggedInUser = system.login(username, password);
        if (loggedInUser != null) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }

    private void logout() {
        loggedInUser = null;
        System.out.println("Logout successful.");
    }
    
    private void showProfile(String[] args) {
        if (loggedInUser != null) {
            System.out.println(loggedInUser.toString());
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    private void registerRestaurant(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 5) {
                System.out.println("Usage: registerRestaurant <name> <address> <username> <password>");
                return;
            }
            String name = args[1];
            String[] addressParts = args[2].split(",");
            Location address = new Location(Double.parseDouble(addressParts[0]), Double.parseDouble(addressParts[1]));
            String username = args[3];
            String password = args[4];
            Restaurant restaurant = new Restaurant(name, address, username, password);
            system.addUser(restaurant);
            System.out.println("Restaurant registered successfully.");
        } else {
            System.out.println("Only a manager can register a restaurant.");
        }
    }

    private void registerCustomer(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 6) {
                System.out.println("Usage: registerCustomer <firstName> <lastName> <username> <address> <password>");
                return;
            }
            String firstName = args[1];
            String lastName = args[2];
            String username = args[3];
            String[] addressParts = args[4].split(",");
            Location address = new Location(Double.parseDouble(addressParts[0]), Double.parseDouble(addressParts[1]));
            String password = args[5];
            Customer customer = new Customer(username, password, firstName, lastName, address, username + "@example.com", "0000000000");
            system.addUser(customer);
            System.out.println("Customer registered successfully.");
        } else {
            System.out.println("Only a manager can register a customer.");
        }
    }

    private void registerCourier(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 6) {
                System.out.println("Usage: registerCourier <firstName> <lastName> <username> <position> <password>");
                return;
            }
            String firstName = args[1];
            String lastName = args[2];
            String username = args[3];
            String[] positionParts = args[4].split(",");
            Location position = new Location(Double.parseDouble(positionParts[0]), Double.parseDouble(positionParts[1]));
            String password = args[5];
            Courier courier = new Courier(username, password, firstName, lastName, position, "0000000000");
            courier.setOnDuty(true); // Default to on-duty
            system.addUser(courier);
            System.out.println("Courier registered successfully.");
        } else {
            System.out.println("Only a manager can register a courier.");
        }
    }

    private void addDishRestaurantMenu(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 5) {
                System.out.println("Usage: addDishRestaurantMenu <dishName> <dishCategory> <foodCategory> <unitPrice>");
                return;
            }
            String dishName = args[1];
            String dishCategory = args[2];
            String foodCategory = args[3];
            double unitPrice = Double.parseDouble(args[4]);
            Dish dish = new Dish(dishName, dishCategory, foodCategory, unitPrice);
            ((Restaurant) loggedInUser).addDishToMenu(dish, dishCategory);
            System.out.println("Dish added to menu successfully.");
        } else {
            System.out.println("Only a restaurant can add a dish to its menu.");
        }
    }

    private void createMeal(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 3) {
                System.out.println("Usage: createMeal <mealName> <foodCategory>");
                return;
            }
            String mealName = args[1];
            String foodCategory = args[2];
            if (foodCategory.toLowerCase().equals("standard") || foodCategory.toLowerCase().equals("vegetarian") || foodCategory.toLowerCase().equals("gluten-free")) {
            	((Restaurant) loggedInUser).createMeal(mealName, foodCategory);
            	System.out.println("Meal created successfully.");}
            else {
            	System.out.println("Not a known meal type");
            }
        } else {
            System.out.println("Only a restaurant can create a meal.");
        }
    }

    private void addDish2Meal(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 3) {
                System.out.println("Usage: addDish2Meal <dishName> <mealName>");
                return;
            }
            String dishName = args[1];
            String mealName = args[2];
            boolean success = ((Restaurant) loggedInUser).addDishToMeal(dishName, mealName);
            if (success) {
                System.out.println("Dish added to meal successfully.");
            }
        } else {
            System.out.println("Only a restaurant can add a dish to a meal.");
        }
    }

    private void showMeal(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 2) {
                System.out.println("Usage: showMeal <mealName>");
                return;
            }
            String mealName = args[1];
            Meal meal = ((Restaurant) loggedInUser).getMealByName(mealName);
            System.out.println(meal);
        } else {
            System.out.println("Only a restaurant can show a meal.");
        }
    }
    
    private void showMenu(String[] args) {
    	if (loggedInUser instanceof Restaurant) {
    		Restaurant r = (Restaurant) loggedInUser; 
    		System.out.println(r.getMenu());
    	} else {
    		System.out.println("Only a restaurant can display its menu.");
    	}
    }

    private void saveMeal(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 2) {
                System.out.println("Usage: saveMeal <mealName>");
                return;
            }
            String mealName = args[1];
            ((Restaurant) loggedInUser).saveMeal(mealName);
            System.out.println("Meal saved successfully.");
        } else {
            System.out.println("Only a restaurant can save a meal.");
        }
    }

    private void setSpecialOffer(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 2) {
                System.out.println("Usage: setSpecialOffer <mealName>");
                return;
            }
            String mealName = args[1];
            Restaurant r = (Restaurant) loggedInUser;
            ((Restaurant) loggedInUser).setMealOfTheWeek(mealName);
            system.notifySpecialOffers(LocalDate.now()+ " - The restaurant "+r.getName()+" offers you "+ r.getSpecialDiscountFactor()*100+"% on "+ mealName + " meal !");
            System.out.println("Special offer set for meal successfully.");
        } else {
            System.out.println("Only a restaurant can set a special offer.");
        }
    }

    private void removeFromSpecialOffer(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 2) {
                System.out.println("Usage: removeFromSpecialOffer <mealName>");
                return;
            }
            String mealName = args[1];
            ((Restaurant) loggedInUser).removeMealOfTheWeek(mealName);
            System.out.println("Removed meal from special offer successfully.");
        } else {
            System.out.println("Only a restaurant can remove a meal from special offer.");
        }
    }
    
    private void notifOn (String[] args) {
    	if (loggedInUser instanceof Customer) {
    		Customer customer = (Customer) loggedInUser;
    		customer.setNotifySpecialOffers(true);
    		System.out.println("Notifications enabled");
    	} else {
    		System.out.println("Only a customer can enable notifications.");
    	}
    }
    
    private void notifOff (String[] args) {
    	if (loggedInUser instanceof Customer) {
    		Customer customer = (Customer) loggedInUser;
    		customer.setNotifySpecialOffers(false);
    		System.out.println("Notifications disabled");
    	} else {
    		System.out.println("Only a customer can disable notifications.");
    	}
    }
    
    private void showNotifs(String[] args) {
    	if (loggedInUser instanceof Customer) {
    		Customer customer = (Customer) loggedInUser;
    		List<String> notifs = customer.getNotifications();
    		for (String n:notifs) {
    			System.out.println(n);
    		}
    	} else if (loggedInUser instanceof Courier) {
    		Courier c = (Courier) loggedInUser;
    		List<String> notifs = c.getNotifs();
    		for (String n:notifs) {
    			System.out.println(n);
    		}
    	} else {
    		System.out.println("Managers can't access to notifications.");
    		}
    }

    private void createOrder(String[] args) {
        if (loggedInUser instanceof Customer) {
            if (args.length != 3) {
                System.out.println("Usage: createOrder <restaurantName> <orderName>");
                return;
            }
            String restaurantName = args[1];
            String orderName = args[2];
            Restaurant restaurant = system.getRestaurantByName(restaurantName);
            if (restaurant == null) {
                System.out.println("Restaurant not found.");
                return;
            }
            Order order = new Order(orderName, restaurant);
            ((Customer) loggedInUser).placeOrder(order); 
            System.out.println("Order created successfully.");
        } else {
            System.out.println("Only a customer can create an order.");
        }
    }

    private void addItem2Order(String[] args) {
        if (loggedInUser instanceof Customer) {
            if (args.length != 3) {
                System.out.println("Usage: addItem2Order <orderName> <itemName>");
                return;
            }
            String orderName = args[1];
            String itemName = args[2];
            Order order = ((Customer) loggedInUser).getOrder(orderName);
            if (order != null) {
                order.addItem(itemName);
                System.out.println("Item added to order successfully.");
            } else {
                System.out.println("Order not found.");
            }
        } else {
            System.out.println("Only a customer can add an item to an order.");
        }
    }

    private void showOrder(String[] args) {
        if (loggedInUser instanceof Customer) {
            if (args.length != 2) {
                System.out.println("Usage: showOrder <orderId>");
                return;
            }
            String orderId = args[1];
            Order order = ((Customer) loggedInUser).getOrder(orderId);
            if (order != null) {
                System.out.println(order);
            } else {
                System.out.println("Order not found.");
            }
        } else {
            System.out.println("Only a customer can view an order.");
        }
    }

    private void endOrder(String[] args) {
        if (loggedInUser instanceof Customer) {
            if (args.length != 3) {
                System.out.println("Usage: endOrder <orderName> <date >");
                return;
            }
            String orderName = args[1];
            String dateString = args[2];
            Customer customer = (Customer) loggedInUser;
            Order order = customer.getOrder(orderName);
            try {	
            	LocalDate date = LocalDate.parse(dateString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            	if (order != null) {
            		order.setDate(date);
            		System.out.println(order);
            		double finalPriceWithDiscount = customer.getFidelityCard().applyDiscount(order.getTotalPrice());
            		System.out.println("You benefit from " + customer.getFidelityCard());
            		if (customer.getFidelityCard() instanceof PointFidelityCard) {
            	        PointFidelityCard pointCard = (PointFidelityCard) customer.getFidelityCard();
            	        pointCard.addPoints(finalPriceWithDiscount);
            	        System.out.println("You have now " + pointCard.getPoints() + " points on your fidelity card.");
            	    }
            		System.out.printf("Final Price after discount: %.2f\n", finalPriceWithDiscount);
            		order.setFinalPrice(finalPriceWithDiscount);
            		order.finalizeOrder(date);
            		system.addOrder(order);
            		Restaurant restaurant = order.getRestaurant();
            		Courier courier = system.selectCourierForDelivery(loggedInUser.getLocation());
                    if (courier != null) {
                        System.out.println("Courier " + courier.getUsername() + " allocated to deliver the order.");
                        courier.incrementDeliveredOrders();
                        system.notifyCourier(courier, "You have been assigned to handle order with orderId : "+ orderName+". The customer is "+customer.getName()+" "+customer.getSurname()+" who lives at "+customer.getLocation()+". You have to go to the restaurant "+ restaurant.getName()+" which is located at "+restaurant.getLocation()+".");
                    } else {
                        System.out.println("No available courier found.");
                    }
            		System.out.println("Order finalized successfully.");
            	} else {
            		System.out.println("Order not found.");
            	}
            } catch (DateTimeParseException e) {
            	System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
       } else {
            System.out.println("Only a customer can finalize an order.");
       }
        
    }
    
    private void histoOrders(String [] args) {
    	if (loggedInUser instanceof Customer) {
    		Customer c = (Customer) loggedInUser;
    		List<Order> orders = c.getOrderHistory();
    		orders.stream()
    			  .sorted(Comparator.comparing(Order::getDate))
    			  .forEach(order -> System.out.println(order));
    		
    	} if (loggedInUser instanceof Restaurant) {
    		Restaurant r = (Restaurant) loggedInUser;
    		List<Order> orders = r.getOrders();
    		orders.stream()
    			  .sorted(Comparator.comparing(Order::getDate))
    			  .forEach(order -> System.out.println(order));
    	} else {
    		System.out.println("Only a customer or a restaurant can view their order history.");
    	}
    }

    private void onDuty(String[] args) {
        if (loggedInUser instanceof Courier) {
            if (args.length != 2) {
                System.out.println("Usage: onDuty <username>");
                return;
            }
            String username = args[1];
            Courier courier = (Courier) system.getUserByUsername(username);
            if (courier != null) {
                courier.setOnDuty(true);
                System.out.println("Courier set to on-duty.");
            } else {
                System.out.println("Courier not found.");
            }
        } else {
            System.out.println("Only a courier can change their duty status.");
        }
    }

    private void offDuty(String[] args) {
        if (loggedInUser instanceof Courier) {
            if (args.length != 2) {
                System.out.println("Usage: offDuty <username>");
                return;
            }
            String username = args[1];
            Courier courier = (Courier) system.getUserByUsername(username);
            if (courier != null) {
                courier.setOnDuty(false);
                System.out.println("Courier set to off-duty.");
            } else {
                System.out.println("Courier not found.");
            }
        } else {
            System.out.println("Only a courier can change their duty status.");
        }
    }

    private void findDeliverer(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 2) {
                System.out.println("Usage: findDeliverer <orderName>");
                return;
            }
            String orderId = args[1];
            Restaurant restaurant = (Restaurant) loggedInUser;
            
            Order order = null;
            for (Order o : restaurant.getOrders()) {
                if (o.getOrderId().equals(orderId)) {
                    order = o;
                    break;
                }
            }
            
            if (order != null) {
                Courier courier = system.selectCourierForDelivery(loggedInUser.getLocation());
                if (courier != null) {
                    System.out.println("Courier " + courier.getUsername() + " allocated to deliver the order.");
                    courier.incrementDeliveredOrders();
                    Customer c = system.findCustomerByOrderId(orderId);
                    system.notifyCourier(courier, "You have been assigned to handle order with orderId : "+orderId+". The customer is "+c.getName()+" "+c.getSurname()+" who lives at "+c.getLocation()+". You have to go to the restaurant "+ restaurant.getName()+" which is located at "+restaurant.getLocation()+".");
                } else {
                    System.out.println("No available courier found.");
                }
            } else {
                System.out.println("Order not found.");
            }
        } else {
            System.out.println("Only a restaurant can allocate a deliverer.");
        }
    }

    private void setDeliveryPolicy(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 2) {
                System.out.println("Usage: setDeliveryPolicy <policyName>");
                return;
            }
            String policyName = args[1];
            system.setDeliveryPolicy(policyName);
            System.out.println("Delivery policy set to " + policyName + ".");
        } else {
            System.out.println("Only a manager can set the delivery policy.");
        }
    }
    
    private void setMarkupPerc(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 2) {
                System.out.println("Usage: setMarkupPerc <number between 0 and 1> ");
                return;
            }
            try {
            	double markupPerc = Double.parseDouble(args[1]);
            	if (markupPerc < 0 || markupPerc > 1) {
                    throw new NumberFormatException();
                }
            	system.setMarkupPercentage(markupPerc);
            	System.out.println("Markup percentage set to " + markupPerc*100 + "%.");
            } catch (NumberFormatException e) {
            	System.out.println("The markup percentage must be a valid number (between 0 and 1).");
            }
        }else {
        	System.out.println("Only a manager can change the markup percentage.");
        }
    }
    
    private void setServiceFee(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 2) {
                System.out.println("Usage: setServiceFee <number> ");
                return;
            }
            try {
            	double serviceFee = Double.parseDouble(args[1]);
            	if (serviceFee < 0) {
                    throw new NumberFormatException();
                }
            	system.setServiceFee(serviceFee);
            	System.out.println("Service fee set to " + serviceFee + " euros.");
            } catch (NumberFormatException e) {
            	System.out.println("The service fee must be a valid number (>0).");
            }
        }else {
        	System.out.println("Only a manager can change the service fee.");
        }
    }
    
    private void setDeliveryCost(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 2) {
                System.out.println("Usage: setDeliveryCost <number> ");
                return;
            }
            try {
            	double dc = Double.parseDouble(args[1]);
            	if (dc < 0) {
                    throw new NumberFormatException();
                }
            	system.setDeliveryCost(dc);
            	System.out.println("Delivery cost set to " + dc + " euros.");
            } catch (NumberFormatException e) {
            	System.out.println("The delivery cost must be a valid number (>0).");
            }
        }else {
        	System.out.println("Only a manager can change the delivery cost.");
        }
    }


    private void setProfitPolicy(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length == 2) {
            	String policyName = args[1].toLowerCase().replaceAll("\\s", "");
            	if (!policyName.equals("deliverycost") && !policyName.equals("servicefee") && !policyName.equals("markup")) {
            		System.out.println("Not a known type of policy.");
            	} else {
            		System.out.println("You didn't enter any target profit, hence the income from last month has been used.");
            		system.setTargetProfit(policyName);
            	}
            } else if (args.length == 3) {
            	try {
            		String policyName = args[1].toLowerCase().replaceAll("\\s", "");
            		double targetProfit = Double.parseDouble(args[2]);
            		if (!policyName.equals("deliverycost") && !policyName.equals("servicefee") && !policyName.equals("markup")) {
            			System.out.println("Not a known type of policy.");
            		} else {
            			system.setTargetProfit(policyName,targetProfit);
            		}
            	} catch(NumberFormatException e) {
                    System.out.println("The target income has to be a numeric value.");
            	}
            } else {
                System.out.println("Usage: setProfitPolicy <policyName> or setProfitPolicy <policyName> <targetIncome (number)>");
                return;
            }
        } else {
        	System.out.println("Only a manager can change the target profit policy.");
        }
    }

    private void associateCard(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length != 3) {
                System.out.println("Usage: associateCard <username> <cardType>");
                return;
            }
            String username = args[1];
            String cardType = args[2];
            User user = system.getUserByUsername(username);
            if (user instanceof Customer) {
                FidelityCard card;
                switch (cardType.toLowerCase()) {
                    case "basic":
                        card = new BasicFidelityCard();
                        break;
                    case "point":
                        card = new PointFidelityCard();
                        break;
                    case "lottery":
                        card = new LotteryFidelityCard();
                        break;
                    default:
                        System.out.println("Unknown card type.");
                        return;
                }
                ((Customer) user).setFidelityCard(card);
                System.out.println("Fidelity card associated successfully.");
            } else {
                System.out.println("User not found or not a customer.");
            }
        } else {
            System.out.println("Only a manager can associate a fidelity card.");
        }
    }

    private void showCourierDeliveries() {
        if (loggedInUser instanceof Manager) {
            List<Courier> couriers = system.getCouriers().stream()
                .sorted((c1, c2) -> Integer.compare(c2.getDeliveredOrders(), c1.getDeliveredOrders()))
                .collect(Collectors.toList());
            couriers.forEach(courier -> System.out.println(courier.getUsername() + " - Deliveries: " + courier.getDeliveredOrders()));
        } else {
            System.out.println("Only a manager can view courier deliveries.");
        }
    }

    private void showRestaurantTop() {
        if (loggedInUser instanceof Manager) {
            List<Restaurant> restaurants = system.getRestaurants().stream()
                .sorted((r1, r2) -> Integer.compare(r2.getTotalOrders(), r1.getTotalOrders()))
                .collect(Collectors.toList());
            restaurants.forEach(restaurant -> System.out.println(restaurant.getName() + " - Orders: " + restaurant.getTotalOrders()));
        } else {
            System.out.println("Only a manager can view top restaurants.");
        }
    }

    private void showCustomers() {
        if (loggedInUser instanceof Manager) {
            List<Customer> customers = system.getCustomers();
            customers.forEach(customer -> System.out.println(customer.getUsername() + " - Name: " + customer.getName()+" "+customer.getSurname()));
        } else {
            System.out.println("Only a manager can view customers.");
        }
    }
    
    private void showOrders() {
        if (loggedInUser instanceof Manager) {
            List<Order> orders = system.getOrders();
            orders.stream()
                  .sorted(Comparator.comparing(Order::getDate))
                  .forEach(order -> System.out.println(order));
        } else {
            System.out.println("Only a manager can view orders.");
        }
    }

    private void showMenuItem(String[] args) {
        if (loggedInUser instanceof User) {
            if (args.length != 2) {
                System.out.println("Usage: showMenuItem <restaurantName>");
                return;
            }
            String restaurantName = args[1];
            Restaurant restaurant = system.getRestaurantByName(restaurantName);
            if (restaurant != null) {
                System.out.println(restaurant.getMenu());
            } else {
                System.out.println("Restaurant not found.");
            }
        } else {
            System.out.println("Only a user can view menu items.");
        }
    }

    private void showTotalProfit(String[] args) {
        if (loggedInUser instanceof Manager) {
            if (args.length == 1) {
                double totalProfit = system.computeTotalProfit();
                System.out.println("Total Profit: " + totalProfit);
            } else if (args.length == 3) {
                String startDateString = args[1];
                String endDateString = args[2];
                try {
                    LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    double totalProfit = system.profitFromTo(startDate, endDate);
                    System.out.println("Total Profit from " + startDate + " to " + endDate + ": " + totalProfit);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                }
            } else {
                System.out.println("Usage: showTotalProfit or showTotalProfit <startDate> <endDate>");
            }
        } else {
            System.out.println("Only a manager can view total profit.");
        }
    }
    
    private void defaultFactorDiscount(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 2) {
                System.out.println("Usage: defaultFactorDiscount <defaultFactor (number)>");
                return;
            }
            try {
            	double defaultFactorDiscount = Double.parseDouble(args[1]);
            	if (defaultFactorDiscount < 0 || defaultFactorDiscount > 1) {
                    throw new NumberFormatException();
                }
            	Restaurant restaurant = (Restaurant) loggedInUser;
            	restaurant.setDefaultDiscountFactor(defaultFactorDiscount);
            	System.out.println("Default factor discount set to " + defaultFactorDiscount*100 + "%.");
            } catch (NumberFormatException e) {
            	System.out.println("The default factor discount must be a valid number (between 0 and 1).");
            }
        }else {
        	System.out.println("Only a restaurant can change the default factor discount.");
        }
    }
    
    private void specialFactorDiscount(String[] args) {
        if (loggedInUser instanceof Restaurant) {
            if (args.length != 2) {
                System.out.println("Usage: specialFactorDiscount <specialFactor (number)>");
                return;
            }
            try {
            	double specialFactorDiscount = Double.parseDouble(args[1]);
            	if (specialFactorDiscount < 0 || specialFactorDiscount > 1) {
                    throw new NumberFormatException();
                }
            	Restaurant restaurant = (Restaurant) loggedInUser;
            	restaurant.setSpecialDiscountFactor(specialFactorDiscount);
            	System.out.println("Special factor discount set to " + specialFactorDiscount*100 + "%.");
            } catch (NumberFormatException e) {
            	System.out.println("The special factor discount must be a valid number (between 0 and 1).");
            }
        }else {
        	System.out.println("Only a restaurant can change the special factor discount.");
        }
    }
    
    private void lastMonthIncome(String [] args) {
    	if (loggedInUser instanceof Manager) {
            System.out.println("Revenue for the last month : " + system.lastMonthIncome());
        } else {
            System.out.println("Only a manager can view the income.");
        }
    }
    
    private void totalIncome(String [] args) {
    	if (loggedInUser instanceof Manager) {
            System.out.println("Revenue since the creation of the app : " + system.computeTotalIncome());
        } else {
            System.out.println("Only a manager can view the income.");
        }
    }
  
    private void runtest(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: runTest <testScenario-file>");
            return;
        }
        String testScenarioFile = args[1];
        System.out.println("Running test scenario from file: " + testScenarioFile);

        try (BufferedReader br = new BufferedReader(new FileReader(testScenarioFile))) {
            String commandLine;
            while ((commandLine = br.readLine()) != null) {
                if (!commandLine.trim().isEmpty()) {
                    System.out.println("> " + commandLine);
                    String[] commandParts = commandLine.split(" ");
                    executeCommand(commandParts);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading test scenario file: " + e.getMessage());
        }
    }
    
    private void setup(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: setup <numRestaurants> <numCustomers> <numCouriers>");
            return;
        }
        
        PrintStream originalOut = System.out; 
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        
        try {
        int numRestaurants = Integer.parseInt(args[1]);
        int numCustomers = Integer.parseInt(args[2]);
        int numCouriers = Integer.parseInt(args[3]);
        double squareSide = 100.0; 
        
        system.addUser(new Manager("ceo", "123", "John", "Doe"));
        system.setDeliveryCost(3.0);
        system.setMarkupPercentage(0.2);
        system.setServiceFee(2.50);
        Random random = new Random();
        
        for (int i = 1; i <= numRestaurants; i++) {
            String name = "Restaurant" + i;
            String username = "rest" + i;
            String password = "restpass" + i;
            Location location = generateRandomLocation(squareSide);
            Restaurant restaurant = new Restaurant(name, location, username, password);
            system.addUser(restaurant);
            restaurant.addDishToMenu(new Dish("main" + "_1", "main", "standard", generateRandomPricemain()), "main");
            restaurant.addDishToMenu(new Dish("main" + "_2", "main", "vegetarian", generateRandomPricemain()), "main");
            restaurant.addDishToMenu(new Dish("main" + "_3", "main", "gluten-free", generateRandomPricemain()), "main");
            restaurant.addDishToMenu(new Dish("start" + "_1", "starter", "vegetarian", generateRandomPricestart()), "starter");
            restaurant.addDishToMenu(new Dish("start" + "_2", "starter", "standard", generateRandomPricestart()), "starter");
            restaurant.addDishToMenu(new Dish("start" + "_3", "starter", "gluten-free", generateRandomPricestart()), "starter");
            restaurant.addDishToMenu(new Dish("dess" + "_1", "dessert", "gluten-free", generateRandomPricedess()), "dessert");
            restaurant.addDishToMenu(new Dish("dess" + "_2", "dessert", "standard", generateRandomPricedess()), "dessert");
            restaurant.addDishToMenu(new Dish("dess" + "_3", "dessert", "standard", generateRandomPricedess()), "dessert");
            restaurant.createMeal("meal" + "_1", "standard");
            restaurant.addDishToMeal("main_1", "meal_1");
            restaurant.addDishToMeal("start_2", "meal_1");
            restaurant.addDishToMeal("dess_3", "meal_1");
            restaurant.createMeal("meal" + "_2", "vegetarian");
            restaurant.addDishToMeal("main_2", "meal_2");
            restaurant.addDishToMeal("start_1", "meal_2");
            restaurant.createMeal("meal" + "_3", "gluten-free");
            restaurant.addDishToMeal("main_3", "meal_3");
            restaurant.addDishToMeal("start_3", "meal_3");
            restaurant.addDishToMeal("dess_1", "meal_3");
        }
        
        for (int i = 1; i <= numCouriers; i++) {
            String name = "Courier" + i;
            String username = "courier" + i;
            String password = "courpass" + i;
            Location position = generateRandomLocation(squareSide);
            Courier courier = new Courier(username, password, name, "Lastname" + i, position, "000000000" + i);
            boolean onDuty = random.nextBoolean();
            courier.setOnDuty(onDuty);
            system.addUser(courier);
        }

        for (int i = 1; i <= numCustomers; i++) {
            String name = "Customer" + i;
            String username = "cust" + i;
            String password = "custpass" + i;
            Location address = generateRandomLocation(squareSide);
            Customer customer = new Customer(username, password, name, "Lastname" + i, address, username + "@example.com", "0000000000");
            boolean notifySpecialOffers = random.nextBoolean();
            customer.setNotifySpecialOffers(notifySpecialOffers);
            system.addUser(customer);
            this.loggedInUser = customer;
            int numOrders = generateRandomNumber(2, 5);
            for (int j = 1; j <= numOrders; j++) {
            	String orderId = "order" + i + "_" + j;
                Restaurant randomRestaurant = getRandomRestaurant();
                String[] createOrderArgs = {"createOrder", randomRestaurant.getName(), orderId};
                executeCommand(createOrderArgs);

                int numItems = generateRandomNumber(1, 3);  // Random number of items per order
                for (int k = 0; k < numItems; k++) {
                    String itemName = getRandomItemName(randomRestaurant);
                    String[] addItemArgs = {"addItem2Order", orderId, itemName};
                    executeCommand(addItemArgs);
                }

                LocalDate orderDate = generateRandomDate();
                String dateString = orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String[] endOrderArgs = {"endOrder", orderId, dateString};
                executeCommand(endOrderArgs);
            }
            this.loggedInUser = null;
            
        }
        
        System.out.println("Setup completed with " + numRestaurants + " restaurants, " + numCustomers + " customers, and " + numCouriers + " couriers.");
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setOut(originalOut); 
        }
        
    }
    

    private static Location generateRandomLocation(double side) {
        double x = ThreadLocalRandom.current().nextDouble(0, side);
        double y = ThreadLocalRandom.current().nextDouble(0, side);
        return new Location(x, y);
    }

    private static double generateRandomPricestart() {
        return ThreadLocalRandom.current().nextDouble(4.0, 8.0);
    }
    
    private static double generateRandomPricemain() {
        return ThreadLocalRandom.current().nextDouble(12.0, 25.0);
    }
    
    private static double generateRandomPricedess() {
        return ThreadLocalRandom.current().nextDouble(5.0, 11.0);
    }
    
    private Restaurant getRandomRestaurant() {
        List<Restaurant> restaurants = system.getRestaurants();
        return restaurants.get((int) (Math.random() * restaurants.size()));
    }
    
    private double generateRandomPrice(double min, double max) {
        return roundToTwoDecimalPlaces(min + (Math.random() * (max - min)));
    }
    
    private double roundToTwoDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    private int generateRandomNumber(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
    
    private LocalDate generateRandomDate() {
        LocalDate startDate = LocalDate.now().minusMonths(3);
        long days = LocalDate.now().toEpochDay() - startDate.toEpochDay();
        return startDate.plusDays((long) (Math.random() * days));
    }
    
    private String getRandomItemName(Restaurant restaurant) {
        List<Dish> dishes = restaurant.getMenu().getAllDishes();
        List<Meal> meals = restaurant.getMeals();
        Random random = new Random();
        if (random.nextBoolean() && !meals.isEmpty()) {
            return meals.get(random.nextInt(meals.size())).getName();
        } else {
            return dishes.get(random.nextInt(dishes.size())).getName();
        }
    }


    private void help() {
        System.out.println("Available commands:");
        System.out.println("login <username> <password>");
        System.out.println("logout");
        System.out.println("showProfile");
        System.out.println("registerRestaurant <name> <address> <username> <password>");
        System.out.println("registerCustomer <firstName> <lastName> <username> <address> <password>");
        System.out.println("registerCourier <firstName> <lastName> <username> <position> <password>");
        System.out.println("addDishRestaurantMenu <dishName> <dishCategory (starter, main, dessert)> <foodCategory (standard, vegetarian, gluten-free)> <unitPrice>");
        System.out.println("createMeal <mealName> <foodCategory (starter, main, dessert)>");
        System.out.println("addDish2Meal <dishName> <mealName>");
        System.out.println("showMeal <mealName>");
        System.out.println("showMenu");
        System.out.println("saveMeal <mealName>");
        System.out.println("setSpecialOffer <mealName>");
        System.out.println("removeFromSpecialOffer <mealName>");
        System.out.println("notifOn");
        System.out.println("notifOff"); 
        System.out.println("showNotifs");
        System.out.println("createOrder <restaurantName> <orderName>");
        System.out.println("addItem2Order <orderName> <itemName>");
        System.out.println("showOrder <orderName>");
        System.out.println("endOrder <orderName> <date (yyyy-mm-dd)>");
        System.out.println("histoOrders");
        System.out.println("onDuty <username>");
        System.out.println("offDuty <username>");
        System.out.println("findDeliverer <orderName>");
        System.out.println("setDeliveryPolicy <policyName (fastest, fair-occupation)>");
        System.out.println("setServiceFee <serviceFee (in euros)>");
        System.out.println("setMarkupPerc <number between 0 and 1>");
        System.out.println("setDeliveryCost <number in euros>");
        System.out.println("setProfitPolicy <policyName (markup, deliverycost, servicefee)>");
        System.out.println("setProfitPolicy <policyName (markup, deliverycost, servicefee)> <targetIncome (number)>");
        System.out.println("associateCard <username> <cardType (basic, point, lottery)>");
        System.out.println("showCourierDeliveries");
        System.out.println("showRestaurantTop");
        System.out.println("showCustomers");
        System.out.println("showOrders (for a manager to view all the orders, sorted by date)");
        System.out.println("showMenuItem <restaurantName>");
        System.out.println("showTotalProfit");
        System.out.println("showTotalProfit <startDate (yyyy-mm-dd)> <endDate (yyyy-mm-dd)>");
        System.out.println("defaultFactorDiscount <defaultFactor (number between 0 and 1)>");
        System.out.println("specialFactorDiscount <specialFactor (number between 0 and 1)>");
        System.out.println("lastMonthIncome (income over a month)");
        System.out.println("totalIncome");
        System.out.println("runtest <testScenario-file>");
        System.out.println("setup <numRestaurants> <numCustomers> <numCouriers>");
        System.out.println("help");
    }
}
