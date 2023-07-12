import java.util.*;

public class Compliance {
    public static void main(String[] args) {
        // Scanner to read user input
        Scanner sc = new Scanner(System.in);

        // Track employees and their usefulness
        TreeMap<Employee, Long> employeeToUsefulness = new TreeMap<>();

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

                // Add info to Employee - Usefullness map
                employeeToUsefulness.put(employee, usefulness);

                // Add info to Name - Employee map
                nameToEmployee.put(name, employee);

            } else if(input.startsWith("Adjust")) { // Check for Adjust command
                // Get name
                String name = input.split(" ")[1];
                
                // Get adjustment value
                Long adjustment = Long.parseLong(input.split(" ")[2]);
                
                // Get employee
                Employee employee = nameToEmployee.get(name);
                
                // Calculate new employee usefulness
                Long newUsefulness = employee.getUsefulness() + adjustment;

                // Replace the old employee with the new employee in the TreeMap
                employeeToUsefulness.replace(employee, newUsefulness);

            } else if(input.startsWith("Month")) {  // Check for Month command
                // Check if we even have an employee
                if(nameToEmployee.isEmpty()) {
                    System.out.println("0");
                    continue;
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

    public Long getUsefulness() {
        return usefulness;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Employee employee) {
        return (employee.usefulness.compareTo(this.usefulness));
    }

    public void adjustUsefulness(Long adjustment) {
        usefulness += adjustment;
    }

}