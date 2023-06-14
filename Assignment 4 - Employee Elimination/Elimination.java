import java.util.Scanner;

// The class to test the Cat and Customer classes
public class Elimination { 
    // The main method
    public static void main(String[] Args) {
        // The scanner for standard input
        Scanner sc = new Scanner(System.in);

        // Read in the contents from the first line
        // * Number of commands
        // * Maximum number of patrons
        // * Maximum number of cats
        int numCommands = sc.nextInt();
        int maxPatrons = sc.nextInt();
        int maxCats = sc.nextInt();
        
        // Store the current number of cats and patrons
        int curPatrons = 0;
        int curCats = 0;

        // Track all the cats and patrons in their own arrays
        Cat[] allCats = new Cat[maxCats];
        Customer[] allPatrons = new Customer[maxPatrons];

        // Loop through all the commands
        for (int command = 0; command < numCommands; command++) {
            // Read in the type of command
            String typeString = sc.next();

            // Handle the command based on the type
            switch (typeString) {
                case "Progress":
                    progress(allCats, allPatrons);
                    break;
                case "Cat":
                    addCat(curCats, allCats, sc);
                    curCats++;
                    break;
                case "Patron":
                    addPatron(curPatrons, allPatrons, sc);
                    curPatrons++;
                    break;
                default:
                    System.err.println("Unrecognized command");
            }
        }

        // Print all the remaining cats
        for (Cat curCat : allCats) {
            if (!curCat.isRemoved()) {
                System.out.println("Cat " + curCat + " was not chosen");
            }
        }

        // Print all remaining customers
        for (Customer curPatron : allPatrons) {
            if (!curPatron.isRemoved()) {
                System.out.println("Customer " + curPatron + 
                        " did not find a cat");
            }
        }
    }
    
    // Method to add a patron.
    public static void addPatron(int curPatrons, Customer[] allPatrons, 
                                          Scanner sc) {
            // Read in the cat's information
            String name = sc.next();
            int x = sc.nextInt();
            int y = sc.nextInt();

            // Create and initialize the cat
            allPatrons[curPatrons] = new Customer(name, x, y);
    }

    // Method to add a cat to the array of cats.
    public static void addCat(int curCats, Cat[] allCats, Scanner sc) {
            // Read in the cat's information
            String name = sc.next();
            int x = sc.nextInt();
            int y = sc.nextInt();

            // Create and initialize the cat
            allCats[curCats] = new Cat(name);
            allCats[curCats].setX(x);
            allCats[curCats].setY(y);
    }

    // Method to progress time by one unit
    public static void progress(Cat[] allCats, Customer[] allPatrons) {
        // Loop through all the customers
        for (Customer curPatron : allPatrons) {
            // Check that we have a Customer
            if (curPatron != null && !curPatron.isRemoved()) {
                // Get the Customer to find the cat they want
                curPatron.findFocus(allCats);
                
                // Try to move a Customer
                curPatron.move(allPatrons);

                // Check if we have obtained cat
                for (Cat curCat : allCats) {
                    if (curPatron.overlaps(curCat)) {
                        System.out.println(curPatron + " plays with " + curCat);
                        
                        // The customer leaves
                        curPatron.remove();

                        // The employee is done for the day
                        curCat.remove();
                    }
                }
            }
        }
    }
}

// The Cat class
class Cat {
    // A variable storing the name of the Cat.
    final String name;

    // Variables storing the location of the Cat.
    private int x, y;

    // A variable storing whether the Cat has been removed or not.
    private boolean removed;
    
    // Constructor method that takes in the name of the Cat.
    // The Cat's location should be set to the origin.
    // The Cat should not start removed.
    Cat(String name) {
        this.name = name;
        x = 0;
        y = 0;
        removed = false;
    }
    
    // Method to retrieve the x-coordinate of a Cat's position.
    // Returns the x-coordinate of the Cat.
    int getX() {
        return x;
    }
    
    // Method to retrieve the y-coordinate of a Cat's position.
    // Returns the y-coordinate of the Cat.
    int getY() {
        return y;
    }
    
    // Method to set the x-coordinate of a Cat's position.
    // Sets the x-coordinate of a Cat's position to the given value.
    void setX(int x) {
        this.x = x;
    }

    // Method to set the y-coordinate of a Cat's position.
    // Sets the y-coordinate of a Cat's position to the given value.
    void setY(int y) {
        this.y = y;
    }

    // Method to check if a Cat is removed.
    // Returns the removed boolean of the Cat.
    boolean isRemoved() {
        return removed;
    }

    // Method to remove a Cat.
    // Sets the remove boolean of the Cat to true.
    void remove() {
        removed = true;
    }

    // The toString method of the Cat class.
    // Returns the name of the Cat.
    public String toString() {
        return name;
    }
}

// The enum to store the direction
enum Direction {
    NONE, 
    UP,    // y increases
    DOWN, // y decrease
    LEFT, // x decrease
    RIGHT // x increase
}

// The Customer class
class Customer {
    // A final instance variable storing the name of the Customer.
    final String name;

    // Variables storing the location of the Customer.
    private int x, y;

    // A variable storing whether the Customer has been removed or not.
    private boolean removed;

    // A variable storing the direction of the Customer.
    private Direction curDirection;

    // Constructor that takes in the name, and the location (in terms of x 
    // and y) of the Customer.
    Customer(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        removed = false;    // Customer hasnt been removed
        curDirection = Direction.NONE;  // Customer still lacks direction
    }

    // Method that handles customers determining direction.
    // The Customer will focus on a Cat from the array of Cats parameter
    // that is one of the four cardinal directions (Up, Down, Left, Right) 
    // from the Customer.
    // The Cat the Customer focuses on should be the closest unremoved Cat.
    // If the Cat is in the Customer's location, then the customer will
    // have no direction.
    // Otherwise, the current direction of the Customer should be set to 
    // the direction the Cat of the customers focus is in.
    void findFocus(Cat[] Cats) {
        // Used to track which cat is currently the best
        // Set to the closest out of bounds distance possible
        int bestDist = 200000002;

        // Loop through each cat in array
        for(Cat currCat : Cats) {
            // Skip current cat if its already removed
            if(currCat.isRemoved())
                continue;

            // Check if theres a cat in the customers location
            if(currCat.getX() == x && currCat.getY() == y) {
                // Set direction to none and break out the loop as there'll be no better cat
                curDirection = Direction.NONE;
                break;
            }

            // Check if the cat is north/south of customer
            if(currCat.getX() == x) {
                // Check if cat is North and closer than current best distance
                if(currCat.getY() - y > 0 && currCat.getY() - y < bestDist) {
                    // Log new best distance
                    bestDist = currCat.getY() - y;
                    
                    // Change direction to North
                    // Professor why is it called north in the pdf but up in the enum
                    // oh well im not changing the rest of my comments
                    curDirection = Direction.UP;
                } else if(y - currCat.getY() < bestDist) {  // We know its south now check if its closer than the best distance
                    // Log new best distance
                    bestDist = y - currCat.getY();

                    // Change direction to SOUTH
                    curDirection = Direction.DOWN;
                }
            } else if(currCat.getY() == y) { // Now we check if the cat is east/west
                 // Check if cat is East and closer than current best distance
                if(currCat.getX() - x > 0 && currCat.getX() - x < bestDist) {
                    // Log new best distance
                    bestDist = currCat.getX() - x;
                    
                    // Change direction to East
                    curDirection = Direction.RIGHT;
                } else if(x - currCat.getX() < bestDist) {  // We know its West now check if its closer than the best distance
                    // Log new best distance
                    bestDist = x - currCat.getX();

                    // Change direction to West
                    curDirection = Direction.LEFT;
                }
            }
        }
    }

    // Method that handles Customer movement.
    // If the Customer has no direction of their destination contains 
    // another unremoved Customer, then the Customer will not move.
    // If the customer has a direction and the destination conatains no
    // unremoved customers, the Customers will move in their given 
    // direction.
    // Regardless of outcome the current Customer will have no resulting
    // direction after "movement".
    void move(Customer[] patrons) { /* TODO */ }

    // Method to check if a Customer could move into another patron.
    // Returns true if some unremoved Customer is at the destination 
    // location.
    // Returns false otherwise.
    boolean collides(Customer[] patrons) { /* TODO */ 
        return true; //placeholder
    }

    // Method to check if the Customer shares a space with a Cat.
    // Returns true if the Cat is not null, not removed, and has the same 
    // xy-position as the current customer.
    // Returns false otherwise.
    boolean overlaps(Cat employee) { /* TODO */ 
        return true; //placeholder
    }
    
    // Method to retrieve the x-coordinate of a Customer's position.
    // Returns the x-coordinate of the Customer.
    public int getX() {
        return x;
    }

    // Method to retrieve the y-coordinate of a Customer's position.
    // Returns the y-coordinate of the Customer.
    public int getY() {
        return y;
    }

    // Method to check if a Customer is removed.
    // Returns the removed boolean of the Customer.
    boolean isRemoved() {
        return removed;
    }

    // Method to remove a Customer.
    // Sets the removed boolean of the class to true.
    public void remove() {
        removed = true;
    }

    // The toString method of the Customer class.
    // Returns the name of the Customer.
    public String toString() {
        return name;
    }
}
