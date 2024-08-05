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
        


    }


}
