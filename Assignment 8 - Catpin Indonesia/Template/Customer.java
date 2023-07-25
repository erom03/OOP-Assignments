
// The class of the customer
public class Customer implements Comparable<Customer>{
   public final int difficulty;
   private int payment;
   public final int desired_end_time;
   public final String name;
   private int finish_time;
   private int reputation_delta;

   // Constructor
   Customer(String name, int difficulty, int payment, int desiredEndTime) {
      // TODO
   }

   // Method to change the reputation and pay the cafe
   public void finishServingCustomer(Restaurant cafe) {
      // TODO
   }

   // Method to sort customers by their finish time.
   // Tie break by name
   public int compareTo(Customer o) {
      // TODO
   }

   // Reduce the price of a drink by 1 (to a minimum of 0)
   public void giveDiscount() {
      // TODO
   }

   // Method to set the order finish time for the customer
   public void setFinishTime(int finishTime) {
      // TODO
   }

   // Method to return the computed finish time for this customer
   public int getFinishTime() {
      // TODO
   }

   // Method to increment the change in time for the employee by the given 
   // delta time
   public void addToEndTime(int delta) {
      // TODO
   }
   
   // Method to change the amount the customer will change the reputation of
   // the cafe once they get their order
   public void changeReputationDelta(int reputationDeltaDelta) {
      // TODO
   }

   public void changeReputationOfRestaurant(Restaurant cafe) {
      // TODO
   }
}
