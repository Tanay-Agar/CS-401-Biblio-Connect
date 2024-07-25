BiblioConnect: Integrated Library Management and Literary Social Network System

1. Using the BiblioConnect UI

1.1 Starting the Application
   - Run BiblioConnectApplication.
   - You'll see a login prompt.

1.2 Logging In
   - Enter your username and password.
   - If you don't have an account, contact a librarian to create one for you.

1.3 Main Menu
After logging in, you'll see the main menu with these options:
   1. Book Services
   2. Borrowing Services
   3. User Management
   4. Search
   5. Reports
   6. Social Media
   7. Logout

1.4 Library Services (Options 1-5)

1.4.1 Book Services
   - List all books: View the entire library catalog.
   - Add a book (Librarian only): Enter book details (title, author, ISBN, type, additional info).
   - Remove a book (Librarian only): Enter the book ID to remove it from the catalog.

1.4.2 Borrowing Services
   - Borrow a book: Enter the book ID you wish to borrow.
   - Return a book: Enter the ID of the book you're returning.
   - Reserve a book: Reserve a book that's currently unavailable.
   - View my reservations: See your current book reservations.
   - View borrowing history: Check your past and current borrowings.
   - View my overdue books: See any books you haven't returned on time.
   - View all overdue books (Librarian only): See all overdue books in the library.

1.4.3 User Management
   - Update my profile: Change your name, email, username, or password.
   - Register new user (Librarian only): Enter details for a new library user.
   - List all users (Librarian only): View all registered users.
   - Remove user (Librarian only): Remove a user from the system.

1.4.4 Search
   - Enter a query to search for books by title, author, or ISBN.

1.4.5 Reports (Librarian and Faculty only)
   - View database content report: See detailed reports on books, users, and borrowing records.

1.5 Social Media Features (Option 6)
Selecting "Social Media" presents these options:
   1. View/Edit Profile
   2. Create Post
   3. View Recent Posts
   4. Comment on Post
   5. Like Post
   6. Share Post
   7. Create Group
   8. Join/Leave Group
   9. View Group Discussions
   10. Group Chat
   11. Follow/Unfollow User
   12. View Followers/Following
   13. View Upcoming Events
   14. Create Event
   15. RSVP to Event
   16. Return to Main Menu

1.5.1 Profile Management
   - View/Edit Profile: Update your favorite books, reading habits, and literary preferences.

1.5.2 Posts
   - Create Post: Share thoughts or book reviews.
   - View Recent Posts: See the latest posts from other users.
   - Comment on Post: Add your thoughts to existing posts.
   - Like Post: Show appreciation for posts.
   - Share Post: Spread interesting posts to your followers.

1.5.3 Groups
   - Create Group: Start a new discussion group.
   - Join/Leave Group: Manage your group memberships.
   - View Group Discussions: See and participate in group conversations.
   - Group Chat: Real-time chat within a group.

1.5.4 Social Connections
   - Follow/Unfollow User: Manage your social connections.
   - View Followers/Following: See your social network.

1.5.5 Events
   - View Upcoming Events: See scheduled literary events.
   - Create Event: Organize a new literary event.
   - RSVP to Event: Sign up for an upcoming event.

1.6 Logging Out
   - From the main menu, select the Logout option to return to the login screen.

2. Implementation Description

2.1 Library Management System

2.1.1 Core Classes
   - User: Represents library users with attributes like userId, name, email, username, password, and role.
   - Book: Abstract class for books, with subclasses PhysicalBook, EBook, and AudioBook.
   - BorrowingRecord: Represents borrowing transactions.
   - UserRole: Enum defining user roles (STUDENT, FACULTY, LIBRARIAN) with borrowing limits and loan durations.

2.1.2 Services
   - LibraryManagementSystem: Main interface for library operations.
   - UserManagementService: Handles user-related operations (registration, authentication, profile management).
   - LibraryManagementService: Manages book and borrowing operations.

2.1.3 Data Access
   - DatabaseConnection: Manages connections and operations for the library database (project_db).

2.1.4 Utilities
   - BookFactory: Creates different types of books based on input parameters.
   - DataInitializer: Populates the database with initial sample data.

2.2 Literary Social Network

2.2.1 Core Classes
   - UserProfile: Represents a user's social profile with reading preferences.
   - Post: Represents user posts with comments and likes.
   - Comment: Represents comments on posts.
   - Event: Represents literary events.
   - Group: Represents discussion groups.

2.2.2 Services
   - SocialMediaService: Interface defining all social network operations.
   - SocialMediaServiceImpl: Implements social network functionalities.

2.2.3 Data Access
   - SocialDatabaseConnection: Manages connections and operations for the social network database (social_network_db).

2.2.4 Utilities
   - SocialMediaServiceFactory: Creates instances of SocialMediaService.

2.3 Integration
   - BiblioConnectApplication: Main entry point, initializes both library and social network components.
   - LibraryUI: Unified user interface for both library and social network features.

2.4 Database Structure
   - project_db: Stores library-related data (users, books, borrowing records).
   - social_network_db: Stores social network data (profiles, posts, comments, groups, events).

2.5 Key Features
   - Unified login for both library and social network access.
   - Role-based access control for library features.
   - Integrated UI allowing seamless transition between library and social features.
   - Separate database management for scalability and modularity.

This implementation allows for an integrated experience where users can manage their library activities and engage in a literary social network within a single application, while maintaining a clear separation of concerns in the backend architecture.
