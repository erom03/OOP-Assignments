
// The Baristas are the employees that make the orders
public class Barista extends Employee {
   // The constructor for a barista
   Barista(int usefulness, int loyalty, int delay, int starting_time) {
      // Call the super constructor
      super(usefulness, loyalty, delay, starting_time);
   }

   // If the Barista fails to work successfully due to a high difficulty
   // customer the employee will fail to make the correct order which will
   // decrease the reputation of the restaraunt by 1 when the customer gets
   // their order.
   // The customer saw what you did Felix!
   // Please read the comment about the work method in the employee class.
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

         // Decrease resturant reputation when the customer gets their order
         customer.changeReputationDelta(-1);
      }

      return success;
   }
}
