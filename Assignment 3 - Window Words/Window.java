import java.util.Scanner;

public class Window {
    public static void main(String[] args) {
        // Open a scanner to read user input
        Scanner sc = new Scanner(System.in);

        // Get the number of lines to read from the user
        int lines = sc.nextInt();

        // Read the first line to avoid blanks later in the program
        sc.nextLine();

        // Create an array of strings based on how many lines there are
        String[] input = new String[lines];

        // Read each line of user input
        for(int i = 0; i < lines; i++) {
            input[i] = sc.nextLine();
        }

        // Loop through each line of input
        for(int i = 0; i < lines; i++) {
            // Loop backwards through the string
            for(int j = input[i].length() - 1; j >= 0; j--) {
                // Check if we've hit a symbol
                if(input[i].charAt(j) == '>') {
                    int tmp = j;

                    while(input[i].charAt(tmp) != '<')
                        tmp--;

                    System.out.print(input[i].substring(tmp, j + 1));

                    j = tmp;
                } else {
                    // Not a special character so we can just print it
                    System.out.print(input[i].charAt(j));
                }
            }

            // Print new line
            System.out.println();
        }

        // Close the scanner
        sc.close();
    }
}
