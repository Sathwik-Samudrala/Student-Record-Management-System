import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

// CLI-based Student Record Management System with CRUD operations
public class StudentManagementSystem {
    private static final String DATA_FILE = "students.dat";  // File for storing data
    private static ArrayList<Student> students = new ArrayList<>();  // Student list
    private static final Scanner scan = new Scanner(System.in);  // Scanner for input

    // Main method - program entry point
    public static void main(String[] args) {
        loadStudents();  // Load existing data
        displayMainMenu();  // Show main menu
        boolean running = true;
        while (running) {
            System.out.print("\nENTER YOUR CHOICE (1-5): ");
            int choice = getValidChoice();  // Get validated user choice
            switch (choice) {
    case 1: 
        addStudent(); 
        saveStudents(); // Save after adding
        break;
    case 2: 
        viewStudents(); 
        break;
    case 3: 
        updateStudent(); 
        saveStudents(); // Save after updating
        break;
    case 4: 
        deleteStudent(); 
        saveStudents(); // Save after deleting
        break;
    case 5: 
        running = false; 
        System.out.println("SYSTEM SHUTDOWN. GOODBYE!"); 
        break;
    default: 
        System.out.println("INVALID CHOICE. PLEASE TRY AGAIN.");
}
        }
        scan.close();  // Close scanner
    }

    // Displays the main menu options
    private static void displayMainMenu() {
        System.out.println("\n====================================");
        System.out.println("    STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("====================================");
        System.out.println("1. ADD STUDENT");
        System.out.println("2. VIEW ALL STUDENTS");
        System.out.println("3. UPDATE STUDENT");
        System.out.println("4. DELETE STUDENT");
        System.out.println("5. EXIT");
        System.out.println("====================================");
    }

    // Loads student data from file
    @SuppressWarnings("unchecked")
    private static void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) ois.readObject();
            System.out.println("Previous student data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting with empty records.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data: " + e.getMessage());
            students = new ArrayList<>();
        }
    }

    // Saves student data to file
    private static void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    // Validates and returns user's menu choice (1-5)
    private static int getValidChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scan.nextLine());
                if (choice >= 1 && choice <= 5) return choice;
                System.out.print("PLEASE ENTER NUMBER BETWEEN 1-5: ");
            } catch (NumberFormatException e) {
                System.out.print("INVALID INPUT! PLEASE ENTER NUMBER (1-5): ");
            }
        }
    }

    // Adds a new student with validation
    private static void addStudent() {
        System.out.println("\nADD NEW STUDENT");
        int id = getValidId();  // Get validated ID
        if (isIdExists(id)) {
            System.out.println("STUDENT ID " + id + " ALREADY EXISTS!");
            return;
        }
        System.out.print("ENTER STUDENT NAME: ");
        String name = scan.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("NAME CANNOT BE EMPTY! ENTER AGAIN: ");
            name = scan.nextLine().trim();
        }
        int marks = getValidMarks();  // Get validated marks
        students.add(new Student(id, name, marks));
        System.out.println("STUDENT ADDED SUCCESSFULLY!");
    }

    // Displays all student records in formatted table
    private static void viewStudents() {
        System.out.println("\nSTUDENT RECORDS");
        if (students.isEmpty()) {
            System.out.println("NO RECORDS FOUND.");
            return;
        }
        System.out.printf("%-10s %-20s %-10s\n", "ID", "NAME", "MARKS");
        System.out.println("----------------------------------------");
        for (Student student : students) {
            System.out.printf("%-10d %-20s %-10d\n", student.getId(), student.getName(), student.getMarks());
        }
        System.out.println("\nTOTAL STUDENTS: " + students.size());
    }

    // Updates existing student record
    private static void updateStudent() {
        System.out.println("\nUPDATE STUDENT");
        if (students.isEmpty()) {
            System.out.println("NO STUDENTS TO UPDATE.");
            return;
        }
        int id = getValidId();  // Get ID to update
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("STUDENT WITH ID " + id + " NOT FOUND.");
            return;
        }
        System.out.print("ENTER NEW NAME [" + student.getName() + "]: ");
        String name = scan.nextLine().trim();
        if (!name.isEmpty()) student.setName(name);
        int marks = getValidMarks("ENTER NEW MARKS [" + student.getMarks() + "]: ", student.getMarks());
        student.setMarks(marks);
        System.out.println("STUDENT UPDATED SUCCESSFULLY!");
    }

    // Deletes student record by ID
    private static void deleteStudent() {
        System.out.println("\nDELETE STUDENT");
        if (students.isEmpty()) {
            System.out.println("NO STUDENTS TO DELETE.");
            return;
        }
        int id = getValidId();  // Get ID to delete
        boolean removed = students.removeIf(s -> s.getId() == id);
        System.out.println(removed ? "STUDENT WITH ID " + id + " DELETED SUCCESSFULLY!" : "STUDENT WITH ID " + id + " NOT FOUND.");
    }

    // Validates and returns student ID (positive integer)
    private static int getValidId() {
        while (true) {
            System.out.print("ENTER STUDENT ID: ");
            String input = scan.nextLine().trim();
            try {
                int id = Integer.parseInt(input);
                if (id > 0) return id;
                System.out.println("ID MUST BE GREATER THAN 0.");
            } catch (NumberFormatException e) {
                System.out.println("INVALID ID! MUST CONTAIN DIGITS ONLY.");
            }
        }
    }

    // Validates and returns student marks (0-100)
    private static int getValidMarks(String prompt, int currentValue) {
        while (true) {
            System.out.print(prompt);
            String input = scan.nextLine().trim();
            if (input.isEmpty()) return currentValue;
            try {
                int marks = Integer.parseInt(input);
                if (marks >= 0 && marks <= 100) return marks;
                System.out.println("MARKS MUST BE BETWEEN 0-100.");
            } catch (NumberFormatException e) {
                System.out.println("INVALID MARKS! MUST BE A NUMBER.");
            }
        }
    }

    // Overloaded method for default marks prompt
    private static int getValidMarks() {
        return getValidMarks("ENTER MARKS (0-100): ", -1);
    }

    // Checks if student ID exists
    private static boolean isIdExists(int id) {
        return findStudentById(id) != null;
    }

    // Finds student by ID
    private static Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) return student;
        }
        return null;
    }
}
