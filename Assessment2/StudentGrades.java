import java.util.*;  
import java.io.File;  
import java.io.FileNotFoundException;  
public class StudentGrades {  // Defining the main class
    private static List<Student> students = new ArrayList<>();  // Creating a list to store student objects

    public static void main(String[] args) {  // Main method where the program starts execution
        Scanner scanner = new Scanner(System.in);  // Creating a scanner object to take input from the user
        System.out.println("Enter the file name of the Student marks sheet: ");  // Prompting the user to enter the file name
        String fileName = scanner.nextLine();  // Reading the file name input by the user
        
        readStudentData(fileName);  // Calling the method to read student data from the file
        
        int choice;  // Variable to store the user's menu choice
        do {  // Do-while loop for displaying the menu until the user chooses to exit
            System.out.println("Menu:");  // Displaying menu options
            System.out.println("1. Display all student marks");
            System.out.println("2. Display students with total marks below a threshold");
            System.out.println("3. Display top 5 and bottom 5 students by total marks");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");  // Prompting the user to enter their choice
            choice = scanner.nextInt();  // Reading the user's choice
            
            switch (choice) {  // Switch statement to handle different menu options
                case 1:
                    displayAllStudentMarks();  // Calling the method to display all student marks
                    break;
                case 2:
                    System.out.print("Enter the threshold: ");  // Prompting the user to enter the threshold
                    double threshold = scanner.nextDouble();  // Reading the threshold value
                    displayStudentsBelowThreshold(threshold);  // Calling the method to display students below the threshold
                    break;
                case 3:
                    displayTopAndBottomStudents();  // Calling the method to display top 5 and bottom 5 students
                    break;
                case 4:
                    System.out.println("Exiting...");  // Message displayed when exiting the program
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");  // Message displayed for invalid choices
            }
        } while (choice != 4);  // Continue displaying the menu until the user chooses to exit (choice 4)
    }

    public static void readStudentData(String fileName) {  // Method to read student data from the file
        try (Scanner fileScanner = new Scanner(new File(fileName))) {  // Creating a scanner object to read the file
            // Skip the header line
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();  // Skipping the first line (header)
            }

            while (fileScanner.hasNextLine()) {  // Looping through the lines in the file
                String line = fileScanner.nextLine();  // Reading a line from the file
                if (line.startsWith("#")) continue;  // Ignoring lines that start with a comment symbol (#)
                String[] data = line.split(",");  // Splitting the line into fields based on commas
                if (data.length >= 6) {  // Checking if the line has enough data fields
                    try {
                        String lastName = data[0].trim();  // Extracting and trimming the last name
                        String firstName = data[1].trim();  // Extracting and trimming the first name
                        int studentId = Integer.parseInt(data[2].trim());  // Extracting and parsing the student ID
                        double a1 = data[3].trim().isEmpty() ? 0 : Double.parseDouble(data[3].trim());  // Extracting and parsing the first assignment mark (default to 0 if empty)
                        double a2 = data[4].trim().isEmpty() ? 0 : Double.parseDouble(data[4].trim());  // Extracting and parsing the second assignment mark (default to 0 if empty)
                        double a3 = data[5].trim().isEmpty() ? 0 : Double.parseDouble(data[5].trim());  // Extracting and parsing the third assignment mark (default to 0 if empty)
                        students.add(new Student(firstName, lastName, studentId, a1, a2, a3));  // Adding the student object to the list
                    } catch (NumberFormatException e) {  // Handling exceptions for parsing errors
                        System.err.println("Error parsing data: " + Arrays.toString(data));  // Displaying an error message
                    }
                } else {
                    System.err.println("Incomplete data: " + Arrays.toString(data));  // Displaying an error message for incomplete data
                }
            }
        } catch (FileNotFoundException e) {  // Handling the case where the file is not found
            System.out.println("File not found: " + fileName);  // Displaying a file not found message
            System.exit(1);  // Exiting the program
        }
    }

    public static void displayAllStudentMarks() {  // Method to display all student marks
        // Printing the header row
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        students.sort(Comparator.comparingInt(Student::getStudentId));  // Sorting the students list by student ID
        for (Student student : students) {  // Looping through the students list
            System.out.println(student);  // Printing each student's data
        }
    }

    public static void displayStudentsBelowThreshold(double threshold) {  // Method to display students with total marks below a threshold
        // Printing the header row
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        for (Student student : students) {  // Looping through the students list
            if (student.getTotalMarks() < threshold) {  // Checking if the student's total marks are below the threshold
                System.out.println(student);  // Printing the student's data
            }
        }
    }

    public static void displayTopAndBottomStudents() {  // Method to display the top 5 and bottom 5 students by total marks
        students.sort(Comparator.comparingDouble(Student::getTotalMarks).reversed());  // Sorting the students list by total marks in descending order
        
        // Printing the top 5 students
        System.out.println("Top 5 Students:");
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        for (int i = 0; i < Math.min(5, students.size()); i++) {  // Looping through the top 5 students
            System.out.println(students.get(i));  // Printing each student's data
        }

        // Printing the bottom 5 students
        System.out.println("Bottom 5 Students:");
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        for (int i = Math.max(0, students.size() - 5); i < students.size(); i++) {  // Looping through the bottom 5 students
            System.out.println(students.get(i));  // Printing each student's data
        }
    }
}

class Student {  // Defining the Student class to store student details
    private String firstName;  // Student's first name
    private String lastName;  // Student's last name
    private int studentId;  // Student's ID
    private double a1, a2, a3;  // Student's marks for three assignments

    public Student(String firstName, String lastName, int studentId, double a1, double a2, double a3) {  // Constructor to initialize student details
        this.firstName = firstName;  // Initializing first name
        this.lastName = lastName;  // Initializing last name
        this.studentId = studentId;  // Initializing student ID
        this.a1 = a1;  // Initializing assignment 1 marks
        this.a2 = a2;  // Initializing assignment 2 marks
        this.a3 = a3;  // Initializing assignment 3 marks
    }

    public int getStudentId() {  // Getter method for student ID
        return studentId;  // Returning student ID
    }

    public double getTotalMarks() {  // Method to calculate total marks
        return a1 + a2 + a3;  // Returning the sum of marks for all assignments
    }

    @Override
    public String toString() { // Overriding the toString method to provide a custom string representation of the Student object
        return String.format("%-10d %-15s %-15s %-5.1f %-5.1f %-5.1f %-5.1f", studentId, firstName, lastName, a1, a2, a3, getTotalMarks());
    }
}
