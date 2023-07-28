
import java.util.*;

public class Main {
    public static void main(String[] Args) throws Exception{
        Scanner sc = new Scanner(System.in);
        Restaurant cafe = new Restaurant(100, 50, 100);

        int cmds = sc.nextInt();

        for (int i = 0; i < cmds; i++) {
            String cmd = sc.next();
            String line = sc.nextLine().trim();
            String[] tokens;
            switch (cmd) {
                case "HIRE":
                    tokens = line.split(" ");
                    int startingUsefulness = Integer.parseInt(tokens[1]);
                    int startingLoyalty = Integer.parseInt(tokens[2]);
                    int delay = Integer.parseInt(tokens[3]);
                    
                    Employee newEmployee;
                    if (tokens[0].equals("BARISTA"))
                        newEmployee = new Barista(startingUsefulness, 
                                                  startingLoyalty, 
                                                  delay, 
                                                  cafe.getCurTime());
                    else if (tokens[0].equals("CASHIER"))
                        newEmployee = new Cashier(startingUsefulness, 
                                                  startingLoyalty, 
                                                  delay, 
                                                  cafe.getCurTime());
                    else if (tokens[0].equals("SERVER"))
                        newEmployee = new Server(startingUsefulness, 
                                                 startingLoyalty, 
                                                 delay, 
                                                 cafe.getCurTime());
                    else 
                        throw new Exception("INVALID EMPLOYEE TYPE");
                    // Hire this new employee
                    cafe.hireEmployee(newEmployee);
                    break;

                case "FIRE":
                    cafe.fireWorst();
                    break;

                case "TIME":
                    tokens = line.split(" ");
                    int changeInTime = Integer.parseInt(tokens[0]);
                    cafe.progessTime(changeInTime);
                    break;

                case "SERVE":
                    tokens = line.split(" ");
                    int difficulty = Integer.parseInt(tokens[1]);
                    int payment = Integer.parseInt(tokens[2]);
                    int desiredEndTime = Integer.parseInt(tokens[3]);
                    Customer newCustomer = new Customer(tokens[0], 
                                                        difficulty, 
                                                        payment, 
                                                        desiredEndTime);
                    cafe.makeOrder(newCustomer);
                    break;

                case "TRAIN":
                    cafe.trainWorst();
                    break;

                case "PAY":
                    cafe.payEmployees();
                    break;

                case "STATUS":
                    System.out.println(cafe.getMoney() + " " + cafe.getReputation());
                    break;

                default:
                    System.err.println("Unhandled command " + cmd);
            }
        }
        sc.close();
    }
}