import java.util.Scanner;

public class Arithmetic {
    public static void main(String[] args) {
        // Create a new scanner
        Scanner myScan = new Scanner(System.in);

        // Get first word
        String firstWord = myScan.next();

        // Get the first value
        Double val1 = myScan.nextDouble();

        // Discard the following two words
        myScan.next();
        myScan.next();

        // Get the second val which is a percentage
        String percentage = myScan.next();

        // Remove the percentage sign from the string
        percentage = percentage.substring(0, percentage.length() - 1);
        System.out.println(percentage);

        // Assign the value without the sign to val2
        Double val2 = Double.parseDouble(percentage);

        // Check if were calculating posters or gas
        if(firstWord.equals("Posters"))
            posters(val1, val2);
        else 
            System.out.println("Hi its gas");
        
        // Close the scanner
        myScan.close();
    }
    
    // Calculates how many posters are needed
    static void posters(Double posters, Double vanDist) {
        // Create a new scanner
        Scanner myScan = new Scanner(System.in);

        // Get denominator of our equation
        Double denom;

        // Calculate the denominator of our equation
        // If the van distance is past 50% through then we inverse the problem
        if(vanDist <= 50)
            denom = ((vanDist * 2) / 100) * ((vanDist * 2) / 100);
        else
            denom = 2 - (((100 - vanDist) * 2) / 100) * (((100 - vanDist) * 2) / 100);

        // Calculate the equations answer
        double eqAnswer = posters * 2 / denom;

        // Convert to our actual answer by subtracting the ceiling of our equation answer and posters left
        int answer = (int)(Math.ceil(eqAnswer) - posters);

        // Print the ceiling of the answer
        System.out.println("Print " + answer + " more poster(s)");

        // Close the scanner
        myScan.close();
    }

    static void gas(Double gasLeft, Double postersUsed) {

    }
}