
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
      // Initialize given vals
      money = startingMoney;
      cur_reputation = startingReputation;
      max_reputation = maxReputation;

      // Set time
      cur_time = 0;

      // Create data structs
      barista_work_queue = new PriorityQueue<>();
      cashier_work_queue = new PriorityQueue<>();
      server_work_queue = new PriorityQueue<>();
      waiting_for_order_queue = new PriorityQueue<>();
      employees_by_usefulness = new TreeMap<>();
   }

   // Remove the least useful employees that are not currently working on an
   // order.
   // Keep trying higher usefulness values until some idle employee is found.
   // If ALL employees currently working do nothing.
   // Give all employees fired 1 unit of money if possible as severance
   public void fireWorst() {
      int mapSize = employees_by_usefulness.size();

      // Track if weve fired someone yet
      boolean firedSomeone = false;

      // Used to track current key
      int currKey = 0;

      // Find at which usefulness value we have employees to fire and mark them
      // To be fired
      for(int i = 0; i < mapSize; i++) {
         if(i == 0) {
            currKey = employees_by_usefulness.firstKey();
         } else {
            currKey = employees_by_usefulness.higherKey(currKey);
         }

         // Get the arraylist of employees
         ArrayList<Employee> employeeArray = employees_by_usefulness.get(currKey);
         
         // iterate through array of employees at current usefulness
         for(var currEmp : employeeArray) {
            // Make sure current employee isn't busy
            if(currEmp.order_finished_time > cur_time) {
               continue;
            }

            // Mark employee as fired
            currEmp.fire();

            // Note that we have fired someone
            firedSomeone = true;
         }

         // Check if someone has been fired so we know if to
         // move on to next usefulness value or not
         if(firedSomeone) break;
      }
      
      // Check if we fired someone at all
      if(!firedSomeone) return;

      // Get list from where we removed employees
      ArrayList<Employee> newEmployeeArray = employees_by_usefulness.get(currKey);
      
      for(var currEmp : newEmployeeArray) {
         // Check if the current employee is fired
         if(currEmp.amIFired()) {
            // Give severence
            decrementMoney(1);
         }
      }

      // Remove fired employees
      newEmployeeArray.removeIf(emp -> emp.amIFired());  // Dr Meade cleared me to use this at office hours just saying

      // Check if arraylist is empty to remove key
      if(newEmployeeArray.isEmpty())
         employees_by_usefulness.remove(currKey);
   }

   // "Pay" all the employees their 1 unit. 
   // Prioritize the employees with the highest usefulness.
   // We cannot go into negtive money...
   // Some employees may be paid 0.
   public void payEmployees() {
      int mapSize = employees_by_usefulness.size();
      int currKey = 0;

      for(int i = 0; i < mapSize; i++) {
         // Used to track current key, first set at last key
         if(i == 0) {
            currKey = employees_by_usefulness.lastKey();
         } else {
            currKey = employees_by_usefulness.lowerKey(currKey);
         }

          // Get the arraylist of employees
         ArrayList<Employee> employeeArray = employees_by_usefulness.get(currKey);
         
         // Iterate through array of employees at current usefulness
         for(var currEmp : employeeArray) {
            if(decrementMoney(1)) {
               currEmp.pay(1);
            } else 
               currEmp.pay(0);
         }
      }
   }

   // Method to try to decrease the money of the cafe by the given amount.
   // We cannot decrease below 0 money.
   // Returns true if possible, and false otherwise.
   public boolean decrementMoney(int amt) {
      // Check if its possible
      if(money - amt >= 0) {
         money -= amt;  // Decrement the money
         return true;
      }
      
      // Return false if not
      return false;
   }

   // Increment the current time by the given amount.
   // Process the finished Customers (their finish time is at or before the 
   // resutling current time).
   public void progessTime(int amt) {
      // Increment time
      cur_time += amt;

      // Check through queue for finished customers
      while(!(waiting_for_order_queue.isEmpty())) {
         
         Customer currCustomer = waiting_for_order_queue.peek();
         
         if(currCustomer.getFinishTime() <= cur_time) {
            // Finish serving the customer
            currCustomer.finishServingCustomer(this);

            // Remove them from queue
            waiting_for_order_queue.poll();
         } else {
            break;
         }
      }
   }

   // Hire the given employee. Add the employee to the relevnt queue
   public void hireEmployee(Employee newEmployee) {
      // Add employee to map
      // Check if key at new employees usefulness exists
      if(employees_by_usefulness.containsKey(newEmployee.usefulness)) {
         // get the arraylist at that key
         ArrayList<Employee> newList = employees_by_usefulness.get(newEmployee.usefulness);
         // add the new employee to the list
         newList.add(newEmployee);
      } else {
         // Create the arraylist for the new key
         ArrayList<Employee> newList = new ArrayList<>();

         // Add the new employee
         newList.add(newEmployee);

         // Add the new key value to the map
         employees_by_usefulness.put(newEmployee.usefulness, newList);
      }

      // Check which queue the new employee should go into and add them there
      if(newEmployee instanceof Barista) {
         barista_work_queue.add((Barista)newEmployee);
      } else if(newEmployee instanceof Cashier) {
         cashier_work_queue.add((Cashier)newEmployee);
      } else if(newEmployee instanceof Server) {
         server_work_queue.add((Server)newEmployee);
      }
   }

   // Train the least useful employees that are not currently completing an 
   // order.
   // Return the number of employees that were trained.
   public int trainWorst() {
      int numTrained = 0;  // Track num trained

      // Get the worst employees
      ArrayList<Employee> theWorst = employees_by_usefulness.get(employees_by_usefulness.firstKey());

      for(Employee currEmp : theWorst) {
         // Check theyre available
         if(currEmp.order_finished_time > cur_time) continue;

         // Train the employee
         currEmp.train(this);

         // Increment number of employees trained
         ++numTrained;
      }

      return numTrained;
   }

   // Try to serve a customer using a Server, Cashier, and Barista.
   // If one of those three rolls are not currently available, turn the 
   // customer away.
   // When the employees serve the customer, all three employees will be
   // preoccupied until the customer has finished being served.
   public void makeOrder(Customer customer) {
      // Get the three employees that would help customer
      Employee barista = barista_work_queue.peek();
      Employee server = server_work_queue.peek();
      Employee cashier = cashier_work_queue.peek();

      // Check they're available
      if(barista == null || barista.order_finished_time > cur_time) return;
      if(server == null || server.order_finished_time > cur_time) return;
      if(cashier == null || cashier.order_finished_time > cur_time) return;

      // Find the current finish time using the employees delay
      int finishedTime = barista.delay + cashier.delay + cashier.delay;

      // Assign new time finished to the workers
      barista.order_finished_time = finishedTime;
      server.order_finished_time = finishedTime;
      cashier.order_finished_time = finishedTime;

      // Set the customer finish time
      customer.setFinishTime(finishedTime);

      // Add customer to queue
      waiting_for_order_queue.add(customer);

      // Have the employees work
      barista.work(customer, this);
      server.work(customer, this);
      cashier.work(customer, this);
   }

   // Method to decrement the reputation of the store by 1.
   public void decrementReputation() {
      --cur_reputation;
   }

   // Method to increase the reputation of the cafe by 1.
   public void incrementReputation() {
      ++cur_reputation;
   }

   // Method to get the current time of the simulation.
   public int getCurTime() {
      return cur_time;
   }

   // Method to get the current reputation of the cafe.
   public int getReputation() {
      return cur_reputation;
   }

   // Method to get the current money of the cafe.
   public int getMoney() {
      return money;
   }
}
