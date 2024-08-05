import java.util.*; // Import utility package for Scanner and ArrayList
import java.io.File; // Import File class for file handling
import java.io.FileNotFoundException; // Import exception class for file not found

class StudentInfo {
    // Class to store individual student information
    private String studentName; // Student's full name
    private int studentId; // Student's ID number
    private double assignment1, assignment2, assignment3; // Scores for three assignments

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
        return this.assignment1 + this.assignment2 + this.assignment3; // Sum of all assignments
    }

    // Getter method for studentName
    public String getStudentName() {
        return studentName; // Return student's full name
    }

    // Getter method for total marks
    public double getTotalMarks() {
        return calculateTotal(); // Return student's total marks
    }

    // Override toString to format student information for display
    @Override
    public String toString() {
        // Format and return student details including total marks
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
        String fileLocation = consoleInput.nextLine(); // Get the filename from the user

        // Read and process student data from file
        readStudentData(fileLocation); // Call function to read data
        // Display processed student marks
        displayStudentMarks(); // Call function to display data

        // Add call to filter by threshold
        ThresholdProcessor.filterAndPrintByThreshold(consoleInput, studentList); // Call function to filter by threshold
        
        // Add call to sort and print top 5 and bottom 5 students
        MarksSorter.printTopBottomStudents(studentList); // Call function to print top and bottom students
    }

    // Method to read student data from file
    public static void readStudentData(String filename) {
        try (Scanner fileReader = new Scanner(new File(filename))) {
            fileReader.nextLine(); // Skip the header line
            fileReader.nextLine(); // Skip another line if additional info present

            // Process each line representing student data
            while (fileReader.hasNextLine()) {
                String dataLine = fileReader.nextLine(); // Read a line from the file
                String[] studentData = dataLine.split(","); // Split line into parts by comma
                if (studentData.length >= 6) { // Check if line has at least 6 parts
                    // Extract and clean up data elements
                    String fullName = studentData[1].trim() + " " + studentData[0].trim(); // Combine first and last name
                    int id = Integer.parseInt(studentData[2].trim()); // Convert ID to integer
                    double score1 = parseScore(studentData[3]); // Parse first score
                    double score2 = parseScore(studentData[4]); // Parse second score
                    double score3 = parseScore(studentData[5]); // Parse third score

                    // Add new student info to the list
                    studentList.add(new StudentInfo(fullName, id, score1, score2, score3)); // Create and add student object
                }
            }
        } catch (FileNotFoundException ex) {
            // Handle the case where the file is not found
            System.out.println("Could not find the file: " + filename); // Print error message
            System.exit(1); // Exit the program
        }
    }

    // Helper method to safely parse score data
    private static double parseScore(String scoreStr) {
        if (scoreStr == null || scoreStr.trim().isEmpty()) {
            return 0.0; // Return default score if data is missing or empty
        }
        try {
            return Double.parseDouble(scoreStr.trim()); // Try to convert score to double
        } catch (NumberFormatException ex) {
            System.err.println("Error parsing score: " + scoreStr); // Print error message
            return 0.0; // Return default if parsing fails
        }
    }

    // Method to display formatted student marks
    public static void displayStudentMarks() {
        // Print header for clarity
        System.out.println(String.format("%-20s %-20s %-10s %-10s %-10s %-10s %-10s", 
            "Student Last Name", "First Name", "Student ID", "A1", "A2", "A3", "Total Marks"));
        System.out.println(String.join("", Collections.nCopies(90, "-"))); // Print separator line

        // Print each student's formatted information
        for (StudentInfo student : studentList) {
            System.out.println(student); // Print student details
        }
    }
}

// New class to handle threshold filtering
class ThresholdProcessor {
    // Method to filter students by threshold and print the results
    public static void filterAndPrintByThreshold(Scanner consoleInput, List<StudentInfo> studentList) {
        System.out.print("Enter the threshold for total marks: "); // Ask for threshold
        double threshold = consoleInput.nextDouble(); // Read threshold from user

        System.out.println("Students with total marks less than " + threshold + ":");
        System.out.println(String.format("%-20s %-20s", "Student Name", "Total Marks")); // Print header
        boolean found = false; // Initialize found flag

        for (StudentInfo student : studentList) {
            double total = student.calculateTotal(); // Calculate total marks
            if (total < threshold) { // Check if total is less than threshold
                System.out.format("%-20s %-20.1f\n", student.getStudentName(), total); // Print student name and total marks
                found = true; // Set found flag to true
            }
        }

        if (!found) {
            System.out.println("No students found below the threshold."); // Print message if no students found
        }
    }
}

// New class to handle sorting and printing top 5 and bottom 5 students
class MarksSorter {
    // Method to sort and print top 5 and bottom 5 students
    public static void printTopBottomStudents(List<StudentInfo> studentList) {
        // Sort the list of students by total marks
        studentList.sort((s1, s2) -> Double.compare(s2.getTotalMarks(), s1.getTotalMarks())); // Sort in descending order

        // Print top 5 students
        System.out.println("\nTop 5 Students:");
        System.out.println(String.format("%-20s %-20s %-10s", "Student Name", "Total Marks", "Student ID")); // Print header
        for (int i = 0; i < Math.min(5, studentList.size()); i++) {
            StudentInfo student = studentList.get(i);
            System.out.format("%-20s %-20.1f %-10d\n", student.getStudentName(), student.getTotalMarks(), student.studentId); // Print student details
        }

        // Print bottom 5 students
        System.out.println("\nBottom 5 Students:");
        System.out.println(String.format("%-20s %-20s %-10s", "Student Name", "Total Marks", "Student ID")); // Print header
        for (int i = Math.max(0, studentList.size() - 5); i < studentList.size(); i++) {
            StudentInfo student = studentList.get(i);
            System.out.format("%-20s %-20.1f %-10d\n", student.getStudentName(), student.getTotalMarks(), student.studentId); // Print student details
        }
    }
}
