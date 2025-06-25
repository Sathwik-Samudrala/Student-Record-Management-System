# 🎓 Java Student Record System

A robust command-line student management application built with Java featuring CRUD operations, data persistence, and comprehensive input validation.

## ✨ Features

### 📝 Core Operations
- **Add Students**: `ID, Name, Marks` with validation
- **View All**: Tabular display of all records
- **Update Entries**: Modify names/marks by ID
- **Delete Records**: Remove students by ID

### 🛡️ Validation System
- **ID Checks**: Positive integers, no duplicates
- **Mark Validation**: 0-100 range enforced
- **Name Rules**: Non-empty strings
- **Error Recovery**: Re-prompt on invalid input

### 💾 Data Persistence
- Automatic save/load to `students.dat`
- Handles file corruption gracefully
- Preserves data between sessions

## 🖥️ Demonstration

### Successful Operations
![Student System 1](Student-Record-SS-2.png)
*Examples showing:*
- Adding new students with valid data
- Viewing all records in clean layout
- Successful updates to existing entries

### Error Handling
![Student System 2](Student-Record-SS-5.png)
*Robust validation for:*
- Invalid marks (negative/over 100)
- Non-numeric IDs
- Duplicate student entries
- Empty names

## 🚀 Getting Started

### Prerequisites
- Java JDK 8+
- Command line terminal

