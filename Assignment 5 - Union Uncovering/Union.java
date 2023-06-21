import java.util.Scanner;
import java.util.ArrayList;

// The class the contains the main method
public class Union
{
    // The main method
    public static void main(String[] Args) 
    {
        // Create the scanner for reading in from the standard input
        Scanner sc = new Scanner(System.in);

        // Create the list of employees
        EmployeeList employeeList = new EmployeeList();

        // Loop until finished
        boolean done = false;
        while (!done) 
        {
            int employeeAdjustment = sc.nextInt();
            int jobAdjustment = sc.nextInt();
            int employeeID = sc.nextInt();
            int jobID = sc.nextInt();

            // Check if the information suggests termination of program
            if (employeeAdjustment == 0 && jobAdjustment == 0 &&
                employeeID == 0 && jobID == 0) 
            {
                // End the program
                done = true;
            }
            else 
            {
                // Update the list of employees using the given data
                employeeList.updateList(employeeAdjustment, jobAdjustment, 
                                    employeeID, jobID);
            }
        }

        // Determine and print the number of employees
        System.out.println("There are " + employeeList.getNumberOfEmployees() +
                           " employees");

        // Determine the number of job employee pairs
        int numberOfJobs = 0;
        for (var employee : employeeList.getCopyOfEmployees())
        {
            numberOfJobs += employee.getCopyOfJobs().size();
        }

        // Print the number of job employee pairs
        System.out.println("There are " + numberOfJobs + " jobs");

        // Determine the job completed the most by an employee
        int mostCompletedJob = 0;

        // Loop through all employees
        for (var employee : employeeList.getCopyOfEmployees())
        {
            // Loop through all jobs
            for (var job : employee.getCopyOfJobs()) 
            {
                // Determine how many times the current job was performed
                int currentCompletionCount = job.getTimesCompleted();

                // Update the most completed job if applicable
                if (mostCompletedJob < currentCompletionCount)
                {
                    mostCompletedJob = currentCompletionCount;
                }
            }
        }

        // Print the number of times a job completed the most by an 
        // employee
        System.out.println("Some job was performed " + mostCompletedJob +
                           " times");

        sc.close(); // The warning annoys me
    }
}

// A class that will hold all the job lists worked on by the employees.
class EmployeeList 
{
    // The list of job lists.
    // Each job list is the list of jobs worked on by an employee.
    private ArrayList<JobList> employees;

    // The current index modified by our list structure.
    // When an adjustment is performed the current index can update.
    // This is used for decoding, and should not be worried about outside
    // the constructor.
    private int current_index;
    
    // Constructor method for the employee list.
    // Creates an empty list of job lists.
    // Starts the current index of the list at 0.
    EmployeeList() 
    {
        employees = new ArrayList<>();
        current_index = 0;
    }

    // The method to determine how many employees are in the current
    // employee list.
    int getNumberOfEmployees() 
    {
        return employees.size();
    }

    // The method to adjust the list using an encoded update.
    // The parameters for the method is as follows,
    //    * The first parameter is the adjustment to the employee's index.
    //      The index of the employee list would be increased by the 
    //      given adjustment value.
    //      The index into the list will be wrapped around to allow for 
    //      modifying earlier employees.
    //      To allow for adding an employee the wrap around will be based
    //      on the size plus one of the current list of employees.
    //      If the current index lands outside the list of possible indices,
    //      then a new employee will be added to the list
    //    * The second parameter is the job adjustement, which performs
    //      the same function as the employee adjustment parameter, but
    //      on the job list instead.
    //      If a new employee is created the job adjustment is ignored
    //    * The third parameter is the employee ID.
    //      The employee ID is the ID of the newly created employee, if
    //      a new employee is created.
    //      If an employee is not created, then the parameter should be
    //      ignored.
    //    * The fourth parameter is the job ID.
    //      The job ID will not be used if a new job is not created.
    // The method will compute a new index in the employee list.
    // If the index created is outside the list, then a new employee should
    // be created.
    // In either case the employee with the computed index should perform
    // the given job.
    void updateList(int employeeAdjustment, 
                    int jobAdjustment,
                    int employeeID, 
                    int jobID) 
    {
        // Use modulo arithmetic to decode the index for the employee that
        // should perform the given job.
        current_index += employeeAdjustment;
        current_index %= (employees.size() + 1);

        // Check if the index would be at a new index
        if (current_index == employees.size())
        {
            // Add a new employee to the list using the employee ID parameter.
            employees.add(current_index, new JobList(employeeID));
        }

        // Update the employee's (at the current index) job list using the
        // given job information.
        employees.get(current_index).updateList(jobAdjustment, jobID);
    }
    
    // Method to return a copy of the list of employees.
    // A reference to the original list should not be returned.
    @SuppressWarnings("unchecked")
    ArrayList<JobList> getCopyOfEmployees()
    {
        return (ArrayList<JobList>)employees.clone();
    }
}

// Class the contains the list of jobs of a particular employee as an
// arary list.
class JobList
{
    // The array list of jobs.
    private ArrayList<Job> jobs;

    // The current index modified by our list structure.
    // When an adjustment is performed the current index can update.
    // This is used for decoding, and should not be worried about outside
    // the constructor.
    private int current_index;

    // The id of the employee
    public final int employee_id;

    // Construct for the Job list class.
    // Takes in the id of the newly created employee.
    // Should create the employees job list.
    // Should also start the current index to 0.
    JobList(int employeeID)
    {
        jobs = new ArrayList<>();
        current_index = 0;
        employee_id = employeeID;
    }

    // Method to return a copy of the jobs list as an array list of jobs.
    // You SHOULD NOT return a reference to the original private job list.
    // You should return a reference to the original array list.
    @SuppressWarnings("unchecked")
    ArrayList<Job> getCopyOfJobs()
    {
        return (ArrayList<Job>) jobs.clone();
    }

    // The method for updating an employees jobs based on an adjustment and
    // job ID.
    //    * The jobAdjustment parameter specifies how the current index should
    //      be adjusted to decode which job may be performed. You won't need
    //      to worry about the value for decoding.
    //    * The jobID paramater specifies the id of the job executed, and
    //      should only be used if a new job is created.
    // The job stored at the current_index should be performed.
    void updateList(int jobAdjustment, int jobID)
    {
        // Use modulo arithmetic to determine which index the
        // new job should go to
        current_index += jobAdjustment;
        current_index %= (jobs.size() + 1);

        // Check if the index would be at a new index
        if (current_index == jobs.size())
        {
            // Add a new job using the given job ID
            jobs.add(new Job(jobID));
        }

        // Have the job at the current_index peformed
        jobs.get(current_index).doJob();
    }
}

// The class for the job.
class Job
{
    // The final instance variable storing the ID for the given job
    public final int job_id;

    // The variable storing the number of times a job was completed
    private int times_completed;

    // Constructor to create a job with the given ID.
    // Set the ID to the given one.
    // Set the job to have not been completed
    Job(int jobID)
    {
        job_id = jobID;
        times_completed = 0;
    }

    // Method that handles the employee performing this job.
    // The times completed member should be updated.
    void doJob()
    {
        // Increment times completed
        ++times_completed;
    }

    // Accessor method to determine how many times a job was performed.
    int getTimesCompleted()
    {
        // Return times completed
        return times_completed;
    }
}

