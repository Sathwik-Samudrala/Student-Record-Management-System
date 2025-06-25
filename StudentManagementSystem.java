import java.util.ArrayList; // For dynamic array functionality
import java.util.InputMismatchException; // For input validation
import java.util.Scanner; // For user input
import java.io.*; // For file operations

/**
 * CLI-based Student Record Management System with CRUD operations
 * and persistent data storage using serialization.
 */
public class StudentManagementSystem {
    // File name for storing student data
    private static final String DATA_FILE = "students.dat";
    
    // ArrayList to store student objects
    private static ArrayList<Student> students = new ArrayList<>();
    
    // Scanner object for user input
    private static final Scanner scan = new Scanner(System.in);

    /**
     * Main method - entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        loadStudents(); // Load existing data
        
        // Display main menu
        System.out.println("====================================");
        System.out.println("    STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("====================================");
        System.out.println("1. ADD STUDENT");
        System.out.println("2. VIEW ALL STUDENTS");
        System.out.println("3. UPDATE STUDENT");
        System.out.println("4. DELETE STUDENT");
        System.out.println("5. EXIT");
        System.out.println("====================================");

        boolean running = true;
        while (running) {
            System.out.print("\nENTER YOUR CHOICE (1-5): ");
            int choice = getValidChoice();
            
            switch (choice) {
                case 1: 
                    addStudent(); 
                    saveStudents(); // Save after adding
                    break;
                case 2: 
                    viewStudent(); 
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
        scan.close(); // Close scanner before exiting
    }

    /**
     * Loads student data from file using object serialization
     */
    @SuppressWarnings("unchecked")
    private static void loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) ois.readObject();
            System.out.println("Previous student data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting with empty records.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data. Starting with empty records.");
            students = new ArrayList<>(); // Initialize empty list
        }
    }

    /**
     * Saves student data to file using object serialization
     */
    private static void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    /**
     * Validates and returns user's menu choice
     * @return Valid integer choice between 1-5
     */
    private static int getValidChoice() {
        while (true) {
            try {
                int choice = scan.nextInt();
                scan.nextLine(); // Consume newline
                return choice;
            } catch (InputMismatchException e) {
                System.out.print("INVALID INPUT! PLEASE ENTER NUMBER (1-5): ");
                scan.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Adds a new student to the system with validation
     */
    private static void addStudent() {
        System.out.println("\nADD NEW STUDENT");
        
        int id = getValidId();
        if (isIdExists(id)) {
            System.out.println("STUDENT ID " + id + " ALREADY EXISTS!");
            return;
        }
        scan.nextLine(); // Consume newline
        
        System.out.print("ENTER STUDENT NAME: ");
        String name = scan.nextLine().trim();
        
        int marks = getValidMarks();
        
        students.add(new Student(id, name, marks));
        System.out.println("STUDENT ADDED SUCCESSFULLY!");
    }

    /**
     * Displays all student records in the system
     */
    private static void viewStudent() {
        System.out.println("\nSTUDENT RECORDS");
        
        if (students.isEmpty()) {
            System.out.println("NO RECORDS FOUND.");
            return;
        }
        
        System.out.println("TOTAL STUDENTS: " + students.size());
        for (Student student : students) {
            System.out.println(student);
        }
    }

    /**
     * Updates existing student record by ID
     */
    private static void updateStudent() {
        System.out.println("\nUPDATE STUDENT");
        
        if (students.isEmpty()) {
            System.out.println("NO STUDENTS TO UPDATE.");
            return;
        }
        
        int id = getValidId();
        scan.nextLine(); // Consume newline
        
        boolean found = false;
        for (Student student : students) {
            if (student.getId() == id) {
                System.out.print("ENTER NEW NAME: ");
                String name = scan.nextLine().trim();
                
                int marks = getValidMarks();
                
                student.setName(name);
                student.setMarks(marks);
                System.out.println("STUDENT UPDATED SUCCESSFULLY!");
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("STUDENT WITH ID " + id + " NOT FOUND.");
        }
    }

    /**
     * Deletes student record by ID
     */
    private static void deleteStudent() {
        System.out.println("\nDELETE STUDENT");
        
        if (students.isEmpty()) {
            System.out.println("NO STUDENTS TO DELETE.");
            return;
        }
        
        int id = getValidId();
        scan.nextLine(); // Consume newline
        
        boolean removed = students.removeIf(student -> student.getId() == id);
        
        if (removed) {
            System.out.println("STUDENT WITH ID " + id + " DELETED SUCCESSFULLY!");
        } else {
            System.out.println("STUDENT WITH ID " + id + " NOT FOUND.");
        }
    }

    /**
     * Validates and returns student ID input
     * @return Valid positive integer ID
     */
    private static int getValidId() {
        while (true) {
            try {
                System.out.print("ENTER STUDENT ID: ");
                int id = scan.nextInt();
                if (id <= 0) throw new IllegalArgumentException();
                return id;
            } catch (InputMismatchException e) {
                System.out.println("INVALID ID! MUST BE POSITIVE NUMBER.");
                scan.nextLine(); // Clear invalid input
            } catch (IllegalArgumentException e) {
                System.out.println("ID MUST BE GREATER THAN 0.");
            }
        }
    }

    /**
     * Validates and returns student marks input
     * @return Valid marks between 0-100
     */
    private static int getValidMarks() {
        while (true) {
            try {
                System.out.print("ENTER MARKS (0-100): ");
                int marks = scan.nextInt();
                if (marks < 0 || marks > 100) throw new IllegalArgumentException();
                return marks;
            } catch (InputMismatchException e) {
                System.out.println("INVALID MARKS! MUST BE NUMBER.");
                scan.nextLine(); // Clear invalid input
            } catch (IllegalArgumentException e) {
                System.out.println("MARKS MUST BE BETWEEN 0-100.");
            }
        }
    }

    /**
     * Checks if a student ID already exists in the system
     * @param id The ID to check
     * @return true if ID exists, false otherwise
     */
    private static boolean isIdExists(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return true;
            }
        }
        return false;
    }
}