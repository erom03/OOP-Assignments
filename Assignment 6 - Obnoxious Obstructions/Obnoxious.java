import java.util.*;

public class Obnoxious {
    // Write the solution to the problem in Main using the Customer class
    public static void main(String[] args) {
        // Create a scanner to read user input
        Scanner sc = new Scanner(System.in);

        // Map to keep track of cat names to customer names
        HashMap<String, String> cats = new HashMap<>();

        // Map to keep track of customers and name
        HashMap<String, Customer> customers = new HashMap<>();

        // Queue to keep track of the current line
        Queue<String> line = new LinkedList<>();

        // Keep track of current time starting at 1
        int currTime = 1;

        // Read all the input from the user
        while(sc.hasNextLine()) {
            // Read in next line from user
            String input = sc.nextLine();

            // Check for Enter command
            if(input.startsWith("Enter")) {
                // Get customers name
                String name = input.split(" ")[1];
                
                // Create new customer to keep track of
                Customer newCustomer = new Customer(name, currTime);

                // Queue the new customer
                line.add(name);

                // Register customer in customer map
                customers.put(name, newCustomer);
            }
            
            // Check for Distract command
            else if(input.startsWith("Distract")) {
                // Get customer and cat name
                String customerName = input.split(" ")[1];
                String catName = input.split(" ")[2];

                // Map cat with customer
                cats.put(catName, customerName);

                // Add customer distraction
                customers.get(customerName).addDistraction();
            }

            // Check for leave command
            else if(input.startsWith("Leave")) {
                // Get cat name
                String catName = input.split(" ")[1];

                // Get associated customer name
                String customerName = cats.get(catName);

                // Remove distraction from customer
                customers.get(customerName).removeDistraction();

                // Remove cat from map
                cats.remove(catName);
            }

            // Check for serve command
            else if(input.startsWith("Serve")) {
                // Get customer at front of the line
                String currName = line.peek();
                Customer currCustomer = customers.get(currName);
                
                // Check if customer is distracted or if the line is empty
                if(line.isEmpty() || currCustomer.isDistracted()) {
                    // Print that cats are in the way
                    System.out.println("Those cats keep getting in the way!");
                } 
                
                // Customer isnt distracted
                else {
                    // Print name and minutes to be served
                    System.out.println(currName + " took " + (currTime -  currCustomer.enterTime) 
                                       + " minutes to be served.");
                    
                    // Remove customer from queue
                    line.poll();

                    // Remove customer from map
                    customers.remove(currName);
                }
            }

            // Increment time
            ++currTime;
        }

        // Close scanner cause why not
        sc.close();
    }
}

// This class will store important information about the Customer
class Customer {
    // Stores the name of the customer
    final String name;
    // Stores the time the customer enters the line
    final int enterTime;
    // Stores the number of cats distracting this customer
    private int numDistractions;

    // Constructor to initialize name, enterTime, and numDistractions
    Customer(String name, int enterTime) {
        this.name = name;
        this.enterTime = enterTime;
        numDistractions = 0;
    }

    // Add a distraction to the customer
    public void addDistraction() {
        numDistractions++;
    }

    // Remove a distraction from the customer
    public void removeDistraction() {
        numDistractions--;
    }

    // Check if the customer is distracted by any cats
    public boolean isDistracted() {
        if(numDistractions == 0) 
            return false;

        return true;
    }
}
