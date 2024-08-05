import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMarksProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the file name: ");
        String fileName = scanner.nextLine();

        ArrayList<Student> students = readAndComputeMarks(fileName);
        printStudentRecords(students);
    }

    public static ArrayList<Student> readAndComputeMarks(String fileName) {
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 5) {
                    System.out.println("Skipping malformed data line: " + line);
                    continue;
                }

                try {
                    String name = data[0];
                    String studentId = data[1];
                    int mark1 = Integer.parseInt(data[2]);
                    int mark2 = Integer.parseInt(data[3]);
                    int mark3 = Integer.parseInt(data[4]);

                    int totalMark = mark1 + mark2 + mark3;
                    studentList.add(new Student(name, studentId, mark1, mark2, mark3, totalMark));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid mark data line: " + line);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("The specified file was not found. Please check the file name and try again.");
        }
        return studentList;
    }

    public static void printStudentRecords(ArrayList<Student> students) {
        System.out.println("Name\tStudent ID\tMarks\t\tTotal");
        System.out.println("------------------------------------------");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

class Student {
    String name;
    String studentId;
    int mark1;
    int mark2;
    int mark3;
    int totalMark;

    public Student(String name, String studentId, int mark1, int mark2, int mark3, int totalMark) {
        this.name = name;
        this.studentId = studentId;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.totalMark = totalMark;
    }

    @Override
    public String toString() {
        return name + "\t" + studentId + "\t" + mark1 + ", " + mark2 + ", " + mark3 + "\t" + totalMark;
    }
}