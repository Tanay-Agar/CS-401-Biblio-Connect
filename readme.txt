BiblioConnect Library Management System - Part 1
================================================


Background and Overview
-----------------------
BiblioConnect is a comprehensive library management system designed to streamline the operations of modern libraries. This application caters 
to the needs of various user roles including students, faculty members, and librarians, providing a user-friendly interface for managing books, 
user accounts, and borrowing activities.

Key Features
------------
BiblioConnect offers a rich set of features to enhance library operations:

Book Management: The system supports multiple book types including physical books, e-books, and audiobooks. Librarians can easily add new books to the catalog, 
                 remove outdated ones, and manage the library's inventory efficiently. Each book type has specific attributes, such as location for physical books, 
                 file size and download links for e-books, and narrator information for audiobooks.

User Management: BiblioConnect implements a role-based user system, distinguishing between students, faculty members, and librarians. Each role has 
                 specific privileges and borrowing limits. The system allows for user registration, profile updates, and account management, ensuring secure 
                 and personalized access to library services.

Borrowing Services: Users can borrow books, return them, and even reserve books that are currently unavailable. The system keeps track of due dates, 
                    manages overdue books, and maintains a comprehensive borrowing history for each user. Librarians have additional capabilities to view 
                    all overdue books across the system.

Search Functionality: A powerful search feature allows users to find books quickly by title, author, or ISBN. This makes it easy for library patrons 
                      to locate the resources they need.

Reporting: BiblioConnect includes reporting capabilities, allowing librarians and faculty members to generate database content reports. 
           These reports provide valuable insights into the library's collection and usage patterns.

Data Management: The system uses a robust database to store all information about books, users, and borrowing records. 
                 It includes functionality to initialize the database with sample data, making it easy to set up and test the system.



User Interface
--------------
BiblioConnect features a console-based user interface that guides users through various operations:

1. Login Menu:
   1. Login
   2. Exit

2. Main Menu:
   1. Book Services
   2. Borrowing Services
   3. User Management
   4. Search
   5. Reports
   6. Social Media
   7. Logout

3. Book Services Menu:
   1. List all books
   2. Add a book (Librarians only)
   3. Remove a book (Librarians only)
   4. Return to Main Menu

4. Borrowing Services Menu:
   1. Borrow a book
   2. Return a book
   3. Reserve a book
   4. View my reservations
   5. View borrowing history
   6. View my overdue books
   7. View all overdue books (Librarians only)
   8. Return to Main Menu

5. User Management Menu:
   1. Update my profile
   2. Register new user (Librarians only)
   3. List all users (Librarians only)
   4. Remove user (Librarians only)
   5. Return to Main Menu

6. Search Menu:
   1. Search by title
   2. Search by author
   3. Search by ISBN
   4. Return to Main Menu

7. Reports Menu (Librarians and Faculty only):
   1. View database content report
   2. Return to Main Menu

8. Social Media Menu:
   (Placeholder for future features)
   1. Return to Main Menu


Menu Navigation:
- Users navigate through menus by entering the number corresponding to their desired action.
- Invalid inputs are handled with appropriate error messages, prompting the user to try again.
- Certain menu options are only available to users with specific roles (e.g., Librarian).
- Users can always return to the previous menu or main menu from any sub-menu.

User Prompts:
- The system provides clear prompts for each action, guiding the user through the process.
- For actions like borrowing or returning books, users are prompted to enter relevant information (e.g., book ID).
- Confirmation messages are displayed after successful actions.
- Error messages are shown when an action cannot be completed, explaining the reason.

Role-Based Access:
- The menu options displayed to a user depend on their role (Student, Faculty, or Librarian).
- Librarians have access to all features, including administrative functions.
- Students and Faculty have access to basic borrowing and searching features.
- The system checks user permissions before executing role-specific actions.

Session Management:
- Users remain logged in and at the main menu until they choose to logout.
- Logging out returns the user to the login menu.
- The current user's information is maintained throughout their session for personalized interactions.




File Descriptions
-----------------

1. BiblioConnectApplication.java
   Main class that initializes and starts the application.

2. LibraryManagementSystem.java
   Interface defining the core structure of the library management system.

3. UserManagementService.java
   Interface outlining user management operations like registration and authentication.

4. LibraryManagementService.java
   Interface defining library operations such as book management and borrowing services.

5. LibraryManagementImpl.java
   Implements LibraryManagementSystem, coordinating UserManagementService and LibraryManagementService.

6. UserManagementServiceImpl.java
   Implements UserManagementService, handling user-related database operations.

7. LibraryManagementServiceImpl.java
   Implements LibraryManagementService, managing book and borrowing related operations.

8. User.java
   Represents a user in the system, storing personal information and role.

9. UserRole.java
   Enum defining user roles (STUDENT, FACULTY, LIBRARIAN) with specific borrowing privileges.

10. Book.java
    Abstract class representing a book, serving as a base for specific book types.

11. PhysicalBook.java
    Extends Book, representing a physical book with location information.

12. EBook.java
    Extends Book, representing an electronic book with format and download information.

13. AudioBook.java
    Extends Book, representing an audiobook with narrator and duration details.

14. BorrowingRecord.java
    Represents a borrowing transaction, tracking dates and reservation status.

15. DatabaseConnection.java
    Manages database connections and provides methods for database operations.

16. BookFactory.java
    Factory class for creating appropriate Book subclass instances.

17. DataInitializer.java
    Utility class for initializing the database with sample data.

18. LibraryUI.java
    Manages the user interface, handling user inputs and displaying information.




MySQL Database Setup
--------------------

To set up the MySQL database for BiblioConnect, follow these steps:

1. Install MySQL:
   If you haven't already, download and install MySQL from the official website:
   https://dev.mysql.com/downloads/mysql/

2. Create the Database:
   Open MySQL command line client or a tool like MySQL Workbench and run the following commands:

   CREATE DATABASE project_db;
   CREATE USER 'project_user'@'localhost' IDENTIFIED BY 'Luna123!';
   GRANT ALL PRIVILEGES ON project_db.* TO 'project_user'@'localhost';
   FLUSH PRIVILEGES;

3. Create Tables:
   Use the following SQL script to create the necessary tables:

   USE project_db;

   CREATE TABLE Users (
       userId VARCHAR(6) PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) UNIQUE NOT NULL,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL,
       role ENUM('STUDENT', 'FACULTY', 'LIBRARIAN') NOT NULL
   );

   CREATE TABLE Books (
       bookId VARCHAR(6) PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       author VARCHAR(100) NOT NULL,
       isbn VARCHAR(13) UNIQUE NOT NULL,
       bookType ENUM('Physical', 'EBook', 'AudioBook') NOT NULL,
       location VARCHAR(50),
       format VARCHAR(50),
       fileSize INT,
       downloadLink VARCHAR(255),
       narrator VARCHAR(100),
       duration INT,
       isAvailable BOOLEAN NOT NULL DEFAULT TRUE
   );

   CREATE TABLE BorrowingRecords (
       recordId INT AUTO_INCREMENT PRIMARY KEY,
       userId VARCHAR(6),
       bookId VARCHAR(6),
       borrowDate DATETIME NOT NULL,
       returnDate DATETIME,
       isReservation BOOLEAN NOT NULL DEFAULT FALSE,
       dueDate DATETIME NOT NULL,
       FOREIGN KEY (userId) REFERENCES Users(userId),
       FOREIGN KEY (bookId) REFERENCES Books(bookId)
   );



Table Schema Explanation:

1. Users Table:
   - userId: A unique 6-character identifier for each user.
   - name: The full name of the user.
   - email: The user's email address (unique).
   - username: The user's chosen username (unique).
   - password: The user's password (should be hashed in a real-world scenario).
   - role: The user's role in the system (STUDENT, FACULTY, or LIBRARIAN).

2. Books Table:
   - bookId: A unique 6-character identifier for each book.
   - title: The title of the book.
   - author: The author of the book.
   - isbn: The International Standard Book Number (unique).
   - bookType: The type of book (Physical, EBook, or AudioBook).
   - location: The physical location of the book (for Physical books).
   - format: The file format (for EBooks and AudioBooks).
   - fileSize: The size of the file in KB (for EBooks and AudioBooks).
   - downloadLink: The URL to download the book (for EBooks and AudioBooks).
   - narrator: The name of the narrator (for AudioBooks).
   - duration: The duration of the audiobook in minutes (for AudioBooks).
   - isAvailable: A boolean indicating if the book is currently available for borrowing.

3. BorrowingRecords Table:
   - recordId: An auto-incrementing unique identifier for each borrowing record.
   - userId: The ID of the user who borrowed the book (foreign key to Users table).
   - bookId: The ID of the borrowed book (foreign key to Books table).
   - borrowDate: The date and time when the book was borrowed.
   - returnDate: The date and time when the book was returned (NULL if not yet returned).
   - isReservation: A boolean indicating if this is a reservation (TRUE) or a current borrowing (FALSE).
   - dueDate: The date by which the book should be returned.

4. Configure Database Connection:
   Update the DatabaseConnection.java file with the following details:

   private static final String URL = "jdbc:mysql://localhost:3306/project_db";
   private static final String USER = "project_user";
   private static final String PASSWORD = "Luna123!";

5. Initialize Data:
   When you run the application for the first time, it will use the DataInitializer class to populate the database with sample data. 
   This includes creating some initial users, books, and borrowing records.

Note: Ensure that your MySQL server is running before starting the BiblioConnect application. If you encounter any connection issues, verify that the 
      server is accessible and that the user credentials are correct.

Security Note: The database credentials provided here are for demonstration purposes only. In a production environment, use strong, 
               unique passwords and consider using environment variables or a secure configuration file to store sensitive information.



Design Principles and Best Practices
------------------------------------

This project implements several key software design principles and best practices:

1. SOLID Principles:
   a. Single Responsibility Principle (SRP):
      - Each class has a single, well-defined responsibility (e.g., User class manages user data, LibraryUI handles user interaction).
      - Services are separated into distinct interfaces (UserManagementService, LibraryManagementService).

   b. Open/Closed Principle (OCP):
      - The Book class is abstract and can be extended without modifying existing code (PhysicalBook, EBook, AudioBook).
      - New user roles can be added to the UserRole enum without changing user management logic.

   c. Interface Segregation Principle (ISP):
      - Separate interfaces for user management and library management allow clients to depend only on the methods they use.

   d. Dependency Inversion Principle (DIP):
      - High-level modules (e.g., LibraryUI) depend on abstractions (interfaces) rather than concrete implementations.

2. Cohesion and Coupling:
   a. High Cohesion:
      - Classes have closely related responsibilities (e.g., UserManagementServiceImpl focuses solely on user management operations).
      - Methods within classes are strongly related to the class's purpose.

   b. Low Coupling:
      - Use of interfaces (e.g., LibraryManagementSystem) reduces dependencies between components.
      - Dependency injection is used to provide services to classes that need them.

3. Extensibility:
   a. The factory pattern (BookFactory) allows easy addition of new book types.
   b. The system architecture allows for easy addition of new features or services.
   c. Use of enums (UserRole) makes it simple to add new user roles with specific privileges.

4. Good OOP Practices:
   a. Encapsulation:
      - Private fields with public getters/setters in classes like User and Book.
      - Internal details of classes are hidden from external access.

   b. Inheritance:
      - Proper use of inheritance for different book types (PhysicalBook, EBook, AudioBook).

   c. Polymorphism:
      - Use of interfaces allows for different implementations of services.
      - Method overriding in Book subclasses (e.g., getDetails() method).

   d. Abstraction:
      - Abstract Book class provides a common interface for all book types.
      - Interfaces define contracts without specifying implementation details.

5. Defensive Design:
   a. Input validation:
      - User inputs are validated before processing (e.g., in LibraryUI).
      - Data integrity checks are performed before database operations.

   b. Exception handling:
      - Try-catch blocks are used to handle potential exceptions, especially in database operations.
      - Meaningful error messages are provided to users when operations fail.

   c. Null checks:
      - Objects are checked for null before use to prevent NullPointerExceptions.

   d. Data consistency:
      - Transactions are used in database operations to ensure data consistency.
      - The system checks for conflicts (e.g., borrowing an unavailable book) before performing operations.



These design principles and practices contribute to a robust, maintainable, and extensible system that can easily accommodate future enhancements and modifications.