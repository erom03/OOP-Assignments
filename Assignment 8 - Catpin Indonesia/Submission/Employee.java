
import java.util.*;

public abstract class Employee implements Comparable<Employee> {
   // store the current usefulness of the employee
   protected int usefulness;

   // Store the loyalty of the employee (changes based on a few methods)
   protected int loyalty;

   // Store the time it takes an employee to finish an order
   protected int delay;

   // Store the time of the most recently finished order
   protected int order_finished_time;

   // Store whether an employee has been fired or not
   private boolean is_fired;

   // Constructor to initialize the values of the employee object/
   // The employee does not start fired.
   Employee(int usefulness, int loyalty, int delay, int startingTime) {
      this.usefulness = usefulness;
      this.loyalty = loyalty;
      this.delay = delay;
      order_finished_time = startingTime;
      is_fired = false;
   }

   // Method to fire an employee
   void fire() {
      is_fired = true;
   }

   // Method to return whether an employee is fired or not
   public boolean amIFired() {
      return is_fired;
   }

   // Perform the following updates to the employee
   //   * Decrease delay by 1 to a minimum of 1
   //   * Increase the usefulness by 1
   // The usefulness in the cafe will need to be updated.
   public void train(Restaurant cafe) {
      // Make sure we havent hit min delay
      if(delay > 1) {
         --delay;
      }

      // Change the usefulness by 1
      changeUsefulness(cafe, 1);
   }

   // If not paid the full amount (1), then
   //   * decrease loyalty by 1
   //   * increase delay by 1
   // Otherwise
   //   * increase loyalty by 1
   public void pay(int amt) {
      if(amt == 1) { // Check if paying full amount
         ++loyalty;
      } else { // Handle if not paid full amount
         --loyalty;
         ++delay;
      }
   }

   // Compare employees by their time they finished an order.
   // First Tie break by delay (smallest first)
   // Second Tie break by loyalty (largest first)
   // Third Tie break by usefulness (largest first)
   // If they are still tied, the order does not matter...
   public int compareTo(Employee o) {
      // TODO if there are issues with employee order check here first, might be this
      // TODO Forgot to compare by time finished
      if(delay != o.delay) {  // Make sure delays not equal
         if(delay < o.delay) {   // Return who has smaller delay
            return 1;
         } else {
            return -1;
         }
      } else if(loyalty != o.loyalty) {   // Make sure loyalty is not equal
         if(loyalty < o.loyalty) {  // Return who has more loyalty
            return -1;
         } else {
            return 1;
         }
      } else if(usefulness != o.usefulness) {   // Make sure usefulness is not equal
         if(usefulness < o.usefulness) {  // Return who has more usefulness
            return -1;
         } else {
            return 1;
         }
      } else {
         return 1;
      }
   }

   // Method to modify this employee's usefulness
   public void changeUsefulness(Restaurant cafe, int changeInUsefulness) {
      // TODO
   }

   // Method to set the time that the customer will finish their next order
   public void setNextOrderFinishedTime(int newTime) {
      order_finished_time = newTime;
   }
   
   // The worker will successfully work if they have a higher loyalty than 
   // the difficulty of the customer.
   // If they successfully perform, increase their usefulness by 1.
   // If their performance is a failure, decrease their usefulness by 1.
   // Returns true if the worker successfully performs.
   // Different employee types will have different functionality.
   public abstract boolean work(Customer customer, Restaurant cafe);
}
