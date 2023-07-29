
// The class for a cashier. They collect the money from the customer.
public class Cashier extends Employee {
   Cashier(int usefulness, int loyalty, int delay, int starting_time) {
      // Call the super constructor
      super(usefulness, loyalty, delay, starting_time);
   }

   // If the Casier fails to work successfully due to a high difficulty 
   // customer the employee will decrease the cost of the drink by 1 (to a 
   // minimum of 0) and will increase the reputation of the restaurant by 1
   // when the customer gets their order.
   // Please read the comment for the work method of the Employee for more
   // details.
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

         // Lower the price for poor service :(
         customer.giveDiscount();

         // Make sure to increase reputation once they get their order
         customer.changeReputationDelta(1);
      }

      return success;
   }
}
