import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StudentDataFormatter {
    
    
    
    public static void main(String[] args) {
        // here we are Creating a scanner object to read input from the console
        Scanner inputScanner = new Scanner(System.in);
        
        // and here we are Asking the user for the name of the file to process
        System.out.print("Please enter the filename (e.g., file.csv): ");
        String fileName = inputScanner.nextLine(); // here we are Reading the filename entered by the user
        
        // for the context, we are providing header information about the application
        System.out.println("\nThis application shows the detailed information and in clean format of student assessments of Fundamentals of Programming");
        System.out.println("UNIT: Fundamentals of Programming");
        
        // this line is to Adjust all column widths to 30% cause the files are not in same length and same size
        System.out.println(String.format("%-30s %-30s %-30s %-30s %-30s %-30s", "Student Last Name", "First Name", "Student ID", "A1", "A2", "A3"));
        System.out.println("----------------------------------------------------------------------------------------------------");

        // Now we Attempt to open the file and process it
        try {
            // first we Create a file instance with the given file name
            File file = new File(fileName);
            // then we Open a scanner to read from the file
            Scanner fileScanner = new Scanner(file);
            
            // here we Read each line from the file using a while loop
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim(); // Read the next line and trim whitespace
                
                 // before moving, we should check for comments or empty lines and skip them
                if (line.startsWith("#") || line.isEmpty()) {
                    continue; // Skip processing this line and move to the next one
                }
                
                 // this part will split the line into parts based on commas
                String[] details = line.split(",");
                
                // here we are Checking if the line has the correct amount of data
                if (details.length == 6) { // There should be six pieces of data
                    // Assigning each part of the line to variables for clarity
                    String lastName = details[0];
                    String firstName = details[1];
                    String studentId = details[2];
                    String a1 = details[3];
                    String a2 = details[4];
                    String a3 = details[5];
                
                
                

        


    }


}
