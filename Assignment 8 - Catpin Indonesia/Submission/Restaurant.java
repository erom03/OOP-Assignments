
import java.util.*;

public class Restaurant {
   public final int max_reputation;
   private int cur_reputation;
   private int money;

   // The time at which the simulation is in
   private int cur_time;

   private PriorityQueue<Barista> barista_work_queue; // Makes order
   private PriorityQueue<Cashier> cashier_work_queue; // Rings people up
   private PriorityQueue<Server> server_work_queue;  // Takes orders

   // Store the employees by their usefulness value
   public TreeMap<Integer, ArrayList<Employee>> employees_by_usefulness;

   private PriorityQueue<Customer> waiting_for_order_queue;


   // Initialize the given values, start the current time at 0, and create
   // the data structures
   Restaurant(int startingMoney, int startingReputation, int maxReputation) {
      // TODO
   }

   // Remove the least useful employees that are not currently working on an
   // order.
   // Keep trying hire usefulness values until some idle employee is found.
   // If ALL employees currently working do nothing.
   // Give all employees fired 1 unit of money if possible as severance
   public void fireWorst() {
      // TODO
   }

   // "Pay" all the employees their 1 unit. 
   // Prioritize the employees with the highest usefulness.
   // We cannot go into negtive money...
   // Some employees may be paid 0.
   public void payEmployees() {
      // TODO
   }

   // Method to try to decrease the money of the cafe by thegiven amount.
   // We cannot decrease below 0 money.
   // Returns true if possible, and false otherwise.
   public boolean decrementMoney(int amt) {
      // TODO
   }

   // Increment the current time by the given amount.
   // Process the finished Customers (their finish time is at or before the 
   // resutling current time).
   public void progessTime(int amt) {
      // TODO
   }

   // Hire the given employee. Add the employee to the relevnt queue
   public void hireEmployee(Employee newEmployee) {
      // TODO
   }

   // Train the least useful employees that are not currently completing an 
   // order.
   // Return the number of employees that were trained.
   public int trainWorst() {
      // TODO
   }

   // Try to serve a customer using a Server, Cashier, and Barista.
   // If one of those three rolls are not currently available, turn the 
   // customer away.
   // When the employees serve the customer, all three employees will be
   // preoccupied until the customer has finished being served.
   public void makeOrder(Customer customer) {
      // TODO
   }

   // Method to decrement the reputation of the store by 1.
   public void decrementReputation() {
      // TODO
   }

   // Method to increase the reputation of the cafe by 1.
   public void incrementReputation() {
      // TODO
   }

   // Method to get the current time of the simulation.
   public int getCurTime() {
      // TODO
   }

   // Method to get the current reputation of the cafe.
   public int getReputation() {
      // TODO
   }

   // Method to get the current money of the cafe.
   public int getMoney() {
      // TODO
   }
}
