import java.util.*;

public class Compliance {
    public static void main(String[] args) {
        // Scanner to read user input
        Scanner sc = new Scanner(System.in);

        // Track employees and their usefulness
        TreeMap<Long, TreeSet<Employee>> usefulnessToEmployee = new TreeMap<>();

        // Track Names to which employee
        HashMap<String, Employee> nameToEmployee = new HashMap<>();

        // Read input from user
        while(sc.hasNextLine()) {
            // Track current line of input
            String input = sc.nextLine();

            // Check for hire command
            if(input.startsWith("Hire")) {
                // Get usefulness value
                Long usefulness = Long.parseLong(input.split(" ")[2]);
                
                // Get name
                String name = input.split(" ")[1];

                // Create new temp employee with needed info
                Employee employee = new Employee(name, usefulness);

                // Add info to Usefullness- Employee map
                // Check if key already exists
                if(usefulnessToEmployee.containsKey(usefulness)) {
                    // Add employee to existing set
                    usefulnessToEmployee.get(usefulness).add(employee);
                } else {
                    // Create set
                    TreeSet<Employee> temp = new TreeSet<>();

                    // Add employee to set
                    temp.add(employee);

                    // Add values to map
                    usefulnessToEmployee.put(usefulness, temp);
                }

                // Add info to Name - Employee map
                nameToEmployee.put(name, employee);

            } else if(input.startsWith("Adjust")) { // Check for Adjust command
                // Get name
                String name = input.split(" ")[1];
                
                // Get adjustment value
                Long adjustment = Long.parseLong(input.split(" ")[2]);
                
                // Get employee
                Employee employee = nameToEmployee.get(name);

                // Track old usefulness value
                Long oldUsefulness = employee.getUsefulness();
                
                // Remove employee from Usefulness set
                usefulnessToEmployee.get(oldUsefulness).remove(employee);

                // Check if set is now empty
                if(usefulnessToEmployee.get(oldUsefulness).isEmpty()) {
                    // Remove entry from map
                    usefulnessToEmployee.remove(oldUsefulness);
                }

                // Track new usefulness value and adjust the old one
                Long newUsefulness = employee.adjustUsefulness(adjustment);

                // Replace the employee in our name hashmap
                nameToEmployee.replace(name, employee);

                // Replace the employee in our usefulness map
                // Check if key already exists
                if(usefulnessToEmployee.containsKey(newUsefulness)) {
                    // Add employee to existing set
                    usefulnessToEmployee.get(newUsefulness).add(employee);
                } else {
                    // Create set
                    TreeSet<Employee> temp = new TreeSet<>();

                    // Add employee to set
                    temp.add(employee);

                    // Add values to map
                    usefulnessToEmployee.put(newUsefulness, temp);
                }

            } else if(input.startsWith("Month")) {  // Check for Month command
                // Check if we even have an employee
                if(nameToEmployee.isEmpty()) {
                    System.out.println("0");
                    continue;
                }

                // Get the top values from our Usefulness set
                Long topUsefulness = usefulnessToEmployee.lastKey();

                // Track the set of top Employees while removing them from the map
                TreeSet<Employee> top = usefulnessToEmployee.remove(topUsefulness);

                // Print how many employees are contained in the set (employees of the month)
                System.out.println(top.size());

                // iterate through the top employees
                for(var currEmployee : top) {
                    // Get the employees name
                    String name = currEmployee.getName();

                    // Print the name
                    System.out.println(name);

                    // Remove the entry from the name map
                    nameToEmployee.remove(name);
                }
            }
        }

        // Close the scanner for fun
        sc.close();
    }
}

class Employee implements Comparable<Employee> {
    // Store name and usefulness values
    private String name;
    private Long usefulness;

    // Used to create new employee
    public Employee(String name, Long usefulness) {
        this.name = name;
        this.usefulness = usefulness;
    }

    // Returns the value of usefulness
    public Long getUsefulness() {
        return usefulness;
    }

    // Returns employees name
    public String getName() {
        return name;
    }

    // Used to compare names
    public int compareTo(Employee employee) {
        // The negative is a cursed way to sort things correctly
        // dont judge
        return -(employee.name.compareTo(this.name));
    }

    // Allows for adjustment of usefulness
    public Long adjustUsefulness(Long adjustment) {
        usefulness += adjustment;

        return usefulness;
    }

}