import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class StudentInfo {
    private String studentName;
    private int studentId;
    private double assignment1, assignment2, assignment3;

    public StudentInfo(String name, int id, double a1, double a2, double a3) {
        this.studentName = name;
        this.studentId = id;
        this.assignment1 = a1;
        this.assignment2 = a2;
        this.assignment3 = a3;
    }

    public double calculateTotal() {
        return this.assignment1 + this.assignment2 + this.assignment3;
    }

    @Override
    public String toString() {
        return String.format("%-50s %-15d %.1f %.1f %.1f %.1f", this.studentName, this.studentId, this.assignment1, this.assignment2, this.assignment3, calculateTotal());
    }
}

public class StudentMarkProcessor {
    private static List<StudentInfo> studentList = new ArrayList<StudentInfo>();

    public static void main(String[] args) {
        Scanner consoleInput = new Scanner(System.in);
        System.out.println("Enter the filename of the student marks data: ");
        String fileLocation = consoleInput.nextLine();
        readStudentData(fileLocation);
        displayStudentMarks();
    }

    public static void readStudentData(String filename) {
        try (Scanner fileReader = new Scanner(new File(filename))) {
            fileReader.nextLine(); // Skip headers
            fileReader.nextLine(); // Skip any additional headers or info

            while (fileReader.hasNextLine()) {
                String dataLine = fileReader.nextLine();
                String[] studentData = dataLine.split(",");
                if (studentData.length >= 6) {
                    String fullName = studentData[1].trim() + " " + studentData[0].trim();
                    int id = Integer.parseInt(studentData[2].trim());
                    double score1 = parseScore(studentData[3]);
                    double score2 = parseScore(studentData[4]);
                    double score3 = parseScore(studentData[5]);

                    studentList.add(new StudentInfo(fullName, id, score1, score2, score3));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find the file: " + filename);
            System.exit(1);
        }
    }

    private static double parseScore(String scoreStr) {
        if (scoreStr == null || scoreStr.trim().isEmpty()) {
            return 0.0; // Return 0 if the score is missing or empty
        }
        try {
            return Double.parseDouble(scoreStr.trim());
        } catch (NumberFormatException ex) {
            System.err.println("Error parsing score: " + scoreStr);
            return 0.0; // Return 0 if parsing fails
        }
    }

    public static void displayStudentMarks() {
        for (StudentInfo student : studentList) {
            System.out.println(student);
        }
    }
}
