import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class StudentInfo {
    // Class to store individual student information
    private String studentName;
    private int studentId;
    private double assignment1, assignment2, assignment3;

    // Constructor to initialize student details
    public StudentInfo(String name, int id, double a1, double a2, double a3) {
        this.studentName = name;
        this.studentId = id;
        this.assignment1 = a1;
        this.assignment2 = a2;
        this.assignment3 = a3;
    }

    // Method to calculate and return total marks
    public double calculateTotal() {
        return this.assignment1 + this.assignment2 + this.assignment3;
    }

    // Override toString to format student information for display
    @Override
    public String toString() {
        return String.format("%-20s %-20s %-10d %-10.1f %-10.1f %-10.1f %-10.1f", 
            this.studentName, "", this.studentId, this.assignment1, this.assignment2, this.assignment3, calculateTotal());
    }
}

public class StudentMarkProcessor {
    // List to hold all student information
    private static List<StudentInfo> studentList = new ArrayList<StudentInfo>();

    public static void main(String[] args) {
        // Scanner for reading input from the console
        Scanner consoleInput = new Scanner(System.in);
        System.out.println("Enter the filename of the student marks data: ");
        String fileLocation = consoleInput.nextLine();

        // Read and process student data from file
        readStudentData(fileLocation);
        // Display processed student marks
        displayStudentMarks();
    }

    // Method to read student data from file
    public static void readStudentData(String filename) {
        try (Scanner fileReader = new Scanner(new File(filename))) {
            fileReader.nextLine(); // Skip the header line
            fileReader.nextLine(); // Skip another line if additional info present

            // Process each line representing student data
            while (fileReader.hasNextLine()) {
                String dataLine = fileReader.nextLine();
                String[] studentData = dataLine.split(",");
                if (studentData.length >= 6) {
                    // Extract and clean up data elements
                    String fullName = studentData[1].trim() + " " + studentData[0].trim();
                    int id = Integer.parseInt(studentData[2].trim());
                    double score1 = parseScore(studentData[3]);
                    double score2 = parseScore(studentData[4]);
                    double score3 = parseScore(studentData[5]);

                    // Add new student info to the list
                    studentList.add(new StudentInfo(fullName, id, score1, score2, score3));
                }
            }
        } catch (FileNotFoundException ex) {
            // Handle the case where the file is not found
            System.out.println("Could not find the file: " + filename);
            System.exit(1);
        }
    }

    // Helper method to safely parse score data
    private static double parseScore(String scoreStr) {
        if (scoreStr == null || scoreStr.trim().isEmpty()) {
            return 0.0; // Return default score if data is missing or empty
        }
        try {
            return Double.parseDouble(scoreStr.trim());
        } catch (NumberFormatException ex) {
            System.err.println("Error parsing score: " + scoreStr);
            return 0.0; // Return default if parsing fails
        }
    }

    // Method to display formatted student marks
    public static void displayStudentMarks() {
        // Print header for clarity
        System.out.println(String.format("%-20s %-20s %-10s %-10s %-10s %-10s %-10s", 
            "Student Last Name", "First Name", "Student ID", "A1", "A2", "A3", "Total Marks"));
        System.out.println(String.join("", Collections.nCopies(90, "-")));

        // Print each student's formatted information
        for (StudentInfo student : studentList) {
            System.out.println(student);
        }
    }
}
