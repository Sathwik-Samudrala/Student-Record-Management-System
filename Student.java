import java.io.Serializable; // Allows object serialization (saving/loading to file)

/**
 * Represents a student with an ID, name, and marks.
 * Implements Serializable to allow saving/loading student objects to/from files.
 */
public class Student implements Serializable {

    // Recommended when implementing Serializable to avoid version conflicts
    private static final long serialVersionUID = 1L;

    // Student ID
    private int id;

    // Student name
    private String name;

    // Student marks
    private int marks;

    /**
     * Constructor to initialize a Student object with id, name, and marks
     * @param id Unique student ID
     * @param name Student's name
     * @param marks Student's marks
     */
    public Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    // Getter for ID
    public int getId() { return id; }

    // Getter for Name
    public String getName() { return name; }

    // Getter for Marks
    public int getMarks() { return marks; }

    // Setter for Name
    public void setName(String name) { this.name = name; }

    // Setter for Marks
    public void setMarks(int marks) { this.marks = marks; }

    /**
     * Overrides the default toString method to print student details
     * @return A formatted string of student details
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks;
    }
}