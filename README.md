# Student Record Management System (Java)

A command-line application built in Java to manage student records with CRUD operations, input validation, and file-based data persistence.

---

## Features
- **Add Students**: Input ID, name, and marks (validated for format and range).  
- **View All Students**: Display all records in a tabular format.  
- **Update Students**: Modify name or marks for existing entries by ID.  
- **Delete Students**: Remove records by ID.  
- **Data Persistence**: Automatically saves/loads records to/from a file (e.g., `students.txt`).  
- **Input Validation**: Ensures valid IDs (positive integers), names (non-empty), and marks (0-100).  

---

## Screenshots

### 1. Empty System Startup
![No previous data found](Student-Record-SS-1.png)  
*Initial state when no data file exists.*

### 2. Adding New Students
![Add Student with Validation](Student-Record-SS-5.png)  
*Input validation for IDs and marks (rejects invalid entries like negative numbers or text).*

### 3. Viewing All Records
![View Student Records](Student-Record-SS-2.png)  
*Displays all students with IDs, names, and marks.*

### 4. Updating a Student
![Update Student Record](Student-Record-SS-4.png)  
*Error handling when updating a non-existent ID (left) vs. successful update (right).*

### 5. Deleting a Student
![Delete Student](Student-Record-SS-3.png)  
*Confirms deletion and updates the record count.*

### 6. System Shutdown
![Graceful Exit](Student-Record-SS-6.png)  
*Data saved automatically on exit.*

---

## Requirements
- **JDK 8+**   

---

## How to Run
1. **Clone the repository**:  
   ```bash
   git clone https://github.com/your-repo/student-record-system-java.git
