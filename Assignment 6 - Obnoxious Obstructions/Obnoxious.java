public class Obnoxious {
    // Write the solution to the problem in Main using the Customer class
    public static void main(String[] args) {
        
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
