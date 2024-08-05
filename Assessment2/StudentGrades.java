import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class StudentGrades {
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file name of the Student marks sheet: ");
        String fileName = scanner.nextLine();
        
        readStudentData(fileName);
        
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Display all student marks");
            System.out.println("2. Display students with total marks below a threshold");
            System.out.println("3. Display top 5 and bottom 5 students by total marks");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    displayAllStudentMarks();
                    break;
                case 2:
                    System.out.print("Enter the threshold: ");
                    double threshold = scanner.nextDouble();
                    displayStudentsBelowThreshold(threshold);
                    break;
                case 3:
                    displayTopAndBottomStudents();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public static void readStudentData(String fileName) {
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            // Skip the header line
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("#")) continue; // Ignore comments
                String[] data = line.split(",");
                if (data.length >= 6) {
                    try {
                        String lastName = data[0].trim();
                        String firstName = data[1].trim();
                        int studentId = Integer.parseInt(data[2].trim());
                        double a1 = Double.parseDouble(data[3].trim());
                        double a2 = Double.parseDouble(data[4].trim());
                        double a3 = Double.parseDouble(data[5].trim());
                        students.add(new Student(firstName, lastName, studentId, a1, a2, a3));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing data: " + Arrays.toString(data));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            System.exit(1);
        }
    }

    public static void displayAllStudentMarks() {
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        students.sort(Comparator.comparingInt(Student::getStudentId));
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public static void displayStudentsBelowThreshold(double threshold) {
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        for (Student student : students) {
            if (student.getTotalMarks() < threshold) {
                System.out.println(student);
            }
        }
    }

    public static void displayTopAndBottomStudents() {
        students.sort(Comparator.comparingDouble(Student::getTotalMarks).reversed());
        
        System.out.println("Top 5 Students:");
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        for (int i = 0; i < Math.min(5, students.size()); i++) {
            System.out.println(students.get(i));
        }

        System.out.println("Bottom 5 Students:");
        System.out.printf("%-10s %-15s %-15s %-5s %-5s %-5s %-5s%n", "Student ID", "First Name", "Last Name", "A1", "A2", "A3", "Total");
        for (int i = Math.max(0, students.size() - 5); i < students.size(); i++) {
            System.out.println(students.get(i));
        }
    }
}

class Student {
    private String firstName;
    private String lastName;
    private int studentId;
    private double a1, a2, a3;

    public Student(String firstName, String lastName, int studentId, double a1, double a2, double a3) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
    }

    public int getStudentId() {
        return studentId;
    }

    public double getTotalMarks() {
        return a1 + a2 + a3;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-15s %-15s %-5.1f %-5.1f %-5.1f %-5.1f", studentId, firstName, lastName, a1, a2, a3, getTotalMarks());
    }
}
