
public class Server extends Employee {
   
   // Constructor for a server.
   Server(int usefulness, int loyalty, int delay, int starting_time) {
      // Call the super constructor
      super(usefulness, loyalty, delay, starting_time);
   }

   // If the Server fails to work successfully due to a high difficulty
   // cusomter the employee with double their delay to serve the customer
   public boolean work(Customer customer, Restaurant cafe) {
      boolean success;

      // Check if employee can do the work
      if(loyalty > customer.difficulty) {
         // Handle base case for all employee types
         success = true;
         changeUsefulness(cafe, 1);
      }
      else {
         // Handle base case for all employee types
         success = false;
         changeUsefulness(cafe, -1);

         // Double the delay
         customer.addToEndTime(delay);
      }

      return success;
   }
}
