
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
      this.name = name;
      this.difficulty = difficulty;
      this.payment = payment;
      desired_end_time = desiredEndTime;
   }

   // Method to change the reputation and pay the cafe
   public void finishServingCustomer(Restaurant cafe) {
      // Check if we got the drink on time
      int timeDelta = desired_end_time - finish_time;
      
      if(timeDelta >= 0) { // Got the drink on time
         changeReputationDelta(1);
      } else { // Drink was late
         changeReputationDelta(-1);
      }

      // Change reputation
      changeReputationOfRestaurant(cafe);

      // Pay the cafe
      cafe.decrementMoney(-payment);
   }

   // Method to sort customers by their finish time.
   // Tie break by name
   public int compareTo(Customer o) {
      // Check for tie
      if(finish_time == o.finish_time)
         return(name.compareTo(o.name));  // Handle the tie

      if(finish_time > o.finish_time)  // Check which is greater
         return 1;   // if the first is greater
      else
         return -1;  // if the second is greater
   }

   // Reduce the price of a drink by 1 (to a minimum of 0)
   public void giveDiscount() {
      // Check if above minimum
      if(payment > 0)
         --payment;
   }

   // Method to set the order finish time for the customer
   public void setFinishTime(int finishTime) {
      finish_time = finishTime;
   }

   // Method to return the computed finish time for this customer
   public int getFinishTime() {
      return finish_time;
   }

   // Method to increment the change in time for the employee by the given 
   // delta time
   public void addToEndTime(int delta) {
      finish_time += delta;
   }
   
   // Method to change the amount the customer will change the reputation of
   // the cafe once they get their order
   public void changeReputationDelta(int reputationDeltaDelta) {
      reputation_delta += reputationDeltaDelta;
   }

   public void changeReputationOfRestaurant(Restaurant cafe) {
      // Get how much we should change the reputation by
      int absDelta = Math.abs(reputation_delta);

      // Change the reputation by 1 as many times as we should
      // While accounting for if it should be positive or negative
      for(int i = 0; i < absDelta; i++) {
         if(reputation_delta > 0)
            cafe.incrementReputation();
         else
            cafe.decrementReputation();
      }
   }
}
