package social_Network;

import biblioConnect_v3.User;
import java.sql.SQLException;
import java.util.*;
import java.time.LocalDateTime;

public class SocialMediaServiceImpl implements SocialMediaService {
    private Scanner scanner;
    private Map<String, UserProfile> userProfiles;
    private List<Post> posts;
    private Map<String, List<String>> groups;
    private Map<String, List<String>> followers;
    private List<Event> events;
    private int postIdCounter;
    private int eventIdCounter;

    public SocialMediaServiceImpl() {
        scanner = new Scanner(System.in);
        userProfiles = new HashMap<>();
        posts = new ArrayList<>();
        groups = new HashMap<>();
        followers = new HashMap<>();
        events = new ArrayList<>();
        postIdCounter = 1;
        eventIdCounter = 1;
        initializeGroups();
    }

    private void initializeGroups() {
        groups.put("Fiction Lovers", new ArrayList<>());
        groups.put("Non-Fiction Enthusiasts", new ArrayList<>());
        groups.put("Mystery Readers", new ArrayList<>());
        groups.put("Science Fiction Fans", new ArrayList<>());
    }

    @Override
    public void createUserProfile(String username, String favoriteBooks, String readingHabits, String literaryPreferences) {
        try {
            UserProfile profile = new UserProfile(username);
            profile.setFavoriteBooks(favoriteBooks);
            profile.setReadingHabits(readingHabits);
            profile.setLiteraryPreferences(literaryPreferences);
            SocialDatabaseConnection.createUserProfile(profile);
            userProfiles.put(username, profile);
            System.out.println("User profile created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating user profile: " + e.getMessage());
        }
    }

    @Override
    public void updateUserProfile(String username, String favoriteBooks, String readingHabits, String literaryPreferences) {
        try {
            UserProfile profile = SocialDatabaseConnection.getUserProfile(username);
            if (profile != null) {
                profile.setFavoriteBooks(favoriteBooks);
                profile.setReadingHabits(readingHabits);
                profile.setLiteraryPreferences(literaryPreferences);
                SocialDatabaseConnection.updateUserProfile(profile);
                userProfiles.put(username, profile);
                System.out.println("User profile updated successfully.");
            } else {
                System.out.println("User profile not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user profile: " + e.getMessage());
        }
    }

    @Override
    public UserProfile getUserProfile(String username) {
        try {
            return SocialDatabaseConnection.getUserProfile(username);
        } catch (SQLException e) {
            System.out.println("Error retrieving user profile: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void createPost(String username, String content) {
        try {
            Post post = new Post(0, username, content);
            SocialDatabaseConnection.createPost(post);
            System.out.println("Post created successfully! Post ID: " + post.getId());
        } catch (SQLException e) {
            System.out.println("Error creating post: " + e.getMessage());
        }
    }

    @Override
    public void commentOnPost(String postId, String username, String content) {
        try {
            Comment comment = new Comment(username, content);
            SocialDatabaseConnection.addComment(Integer.parseInt(postId), comment);
            System.out.println("Comment added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding comment: " + e.getMessage());
        }
    }

    @Override
    public void likePost(String postId, String username) {
        try {
            SocialDatabaseConnection.addLike(Integer.parseInt(postId), username);
            System.out.println("Post liked successfully!");
        } catch (SQLException e) {
            System.out.println("Error liking post: " + e.getMessage());
        }
    }

    @Override
    public void sharePost(String postId, String username) {
        try {
            Post originalPost = SocialDatabaseConnection.getPost(Integer.parseInt(postId));
            if (originalPost != null) {
                String sharedContent = "Shared post from " + originalPost.getUsername() + ": " + originalPost.getContent();
                createPost(username, sharedContent);
                System.out.println("Post shared successfully!");
            } else {
                System.out.println("Original post not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error sharing post: " + e.getMessage());
        }
    }

    @Override
    public void createGroup(String name, String description) {
        try {
            SocialDatabaseConnection.createGroup(name, description);
            groups.put(name, new ArrayList<>());
            System.out.println("Group created successfully: " + name);
        } catch (SQLException e) {
            System.out.println("Error creating group: " + e.getMessage());
        }
    }

    @Override
    public void joinGroup(String groupId, String username) {
        try {
            SocialDatabaseConnection.joinGroup(groupId, username);
            groups.computeIfAbsent(groupId, k -> new ArrayList<>()).add(username);
            System.out.println("You've joined the group: " + groupId);
        } catch (SQLException e) {
            System.out.println("Error joining group: " + e.getMessage());
        }
    }

    @Override
    public void leaveGroup(String groupId, String username) {
        try {
            SocialDatabaseConnection.leaveGroup(groupId, username);
            groups.getOrDefault(groupId, new ArrayList<>()).remove(username);
            System.out.println("You've left the group: " + groupId);
        } catch (SQLException e) {
            System.out.println("Error leaving group: " + e.getMessage());
        }
    }

    @Override
    public void postInGroup(String groupId, String username, String content) {
        try {
            SocialDatabaseConnection.postInGroup(groupId, username, content);
            System.out.println("Posted successfully in group: " + groupId);
        } catch (SQLException e) {
            System.out.println("Error posting in group: " + e.getMessage());
        }
    }

    @Override
    public List<String> getGroupDiscussions(String groupId) {
        try {
            return SocialDatabaseConnection.getGroupDiscussions(groupId);
        } catch (SQLException e) {
            System.out.println("Error retrieving group discussions: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void followUser(String followerUsername, String followedUsername) {
        try {
            // Check if both users exist in the user_profiles table
            UserProfile follower = SocialDatabaseConnection.getUserProfile(followerUsername);
            UserProfile followed = SocialDatabaseConnection.getUserProfile(followedUsername);
            
            if (follower == null) {
                System.out.println("Your profile doesn't exist. Creating a default profile.");
                createUserProfile(followerUsername, "", "", "");
            }
            if (followed == null) {
                System.out.println("The user you're trying to follow doesn't have a profile.");
                return;
            }
            
            SocialDatabaseConnection.followUser(followerUsername, followedUsername);
            System.out.println("You are now following " + followedUsername);
        } catch (SQLException e) {
            System.out.println("Error following user: " + e.getMessage());
        }
    }

    @Override
    public void unfollowUser(String followerUsername, String unfollowedUsername) {
        try {
            SocialDatabaseConnection.unfollowUser(followerUsername, unfollowedUsername);
            System.out.println("You have unfollowed " + unfollowedUsername);
        } catch (SQLException e) {
            System.out.println("Error unfollowing user: " + e.getMessage());
        }
    }

    @Override
    public List<String> getFollowers(String username) {
        try {
            return SocialDatabaseConnection.getFollowers(username);
        } catch (SQLException e) {
            System.out.println("Error retrieving followers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getFollowing(String username) {
        try {
            return SocialDatabaseConnection.getFollowing(username);
        } catch (SQLException e) {
            System.out.println("Error retrieving following: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void createEvent(String name, String date, String description, String location) {
        try {
            Event event = new Event(0, name, date, description, location);
            SocialDatabaseConnection.createEvent(event);
            System.out.println("Event created successfully! Event ID: " + event.getId());
        } catch (SQLException e) {
            System.out.println("Error creating event: " + e.getMessage());
        }
    }

    @Override
    public void rsvpToEvent(int eventId, String username) {
        try {
            SocialDatabaseConnection.rsvpToEvent(eventId, username);
            System.out.println("RSVP successful for event ID: " + eventId);
        } catch (SQLException e) {
            System.out.println("Error RSVPing to event: " + e.getMessage());
        }
    }

    @Override
    public List<Event> viewUpcomingEvents() {
        try {
            return SocialDatabaseConnection.getUpcomingEvents();
        } catch (SQLException e) {
            System.out.println("Error retrieving upcoming events: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void showSocialMediaMenu(User currentUser) {
        while (true) {
            System.out.println("\n===== Social Media Menu =====");
            System.out.println("1. View/Edit Profile");
            System.out.println("2. Create Post");
            System.out.println("3. View Recent Posts");
            System.out.println("4. Comment on Post");
            System.out.println("5. Like Post");
            System.out.println("6. Share Post");
            System.out.println("7. Create Group");
            System.out.println("8. Join/Leave Group");
            System.out.println("9. View Group Discussions");
            System.out.println("10. Group Chat");
            System.out.println("11. Follow/Unfollow User");
            System.out.println("12. View Followers/Following");
            System.out.println("13. View Upcoming Events");
            System.out.println("14. Create Event");
            System.out.println("15. RSVP to Event");
            System.out.println("16. Return to Main Menu");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    handleProfileOperations(currentUser);
                    break;
                case 2:
                    handleCreatePost(currentUser);
                    break;
                case 3:
                    handleViewRecentPosts();
                    break;
                case 4:
                    handleCommentOnPost(currentUser);
                    break;
                case 5:
                    handleLikePost(currentUser);
                    break;
                case 6:
                    handleSharePost(currentUser);
                    break;
                case 7:
                    handleCreateGroup(currentUser);
                    break;
                case 8:
                    handleGroupOperations(currentUser);
                    break;
                case 9:
                    handleViewGroupDiscussions();
                    break;
                case 10:
                    handleGroupChat(currentUser);
                    break;
                case 11:
                    handleFollowOperations(currentUser);
                    break;
                case 12:
                    handleViewFollowersFollowing(currentUser);
                    break;
                case 13:
                    handleViewEvents();
                    break;
                case 14:
                    handleCreateEvent(currentUser);
                    break;
                case 15:
                    handleRSVPToEvent(currentUser);
                    break;
                case 16:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @Override
    public void handleProfileOperations(User currentUser) {
        System.out.println("\n===== Profile Operations =====");
        UserProfile profile = getUserProfile(currentUser.getUsername());
        if (profile == null) {
            System.out.println("You don't have a profile yet. Let's create one!");
            String favoriteBooks = getStringInput("Enter your favorite books: ");
            String readingHabits = getStringInput("Describe your reading habits: ");
            String literaryPreferences = getStringInput("Describe your literary preferences: ");
            createUserProfile(currentUser.getUsername(), favoriteBooks, readingHabits, literaryPreferences);
        } else {
            System.out.println("Current Profile:");
            System.out.println(profile);
            System.out.println("\nDo you want to update your profile? (y/n)");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                String favoriteBooks = getStringInput("Enter your favorite books: ");
                String readingHabits = getStringInput("Describe your reading habits: ");
                String literaryPreferences = getStringInput("Describe your literary preferences: ");
                updateUserProfile(currentUser.getUsername(), favoriteBooks, readingHabits, literaryPreferences);
            }
        }
    }

    @Override
    public void handleCreatePost(User currentUser) {
        String content = getStringInput("Enter your post content: ");
        createPost(currentUser.getUsername(), content);
    }

    @Override
    public void handleViewRecentPosts() {
        System.out.println("\n===== Recent Posts =====");
        List<Post> recentPosts = getRecentPosts(10);
        if (recentPosts.isEmpty()) {
            System.out.println("No recent posts found.");
        } else {
            for (Post post : recentPosts) {
                System.out.println("ID: " + post.getId() + " | User: " + post.getUsername() + 
                                   " | Content: " + post.getContent() + 
                                   " | Time: " + post.getTimestamp());
                List<Comment> comments = getCommentsForPost(post.getId());
                if (!comments.isEmpty()) {
                    System.out.println("Comments:");
                    for (Comment comment : comments) {
                        System.out.println("  - " + comment.getUsername() + ": " + comment.getContent());
                    }
                }
                System.out.println("--------------------");
            }
        }
    }

    @Override
    public void handleCommentOnPost(User currentUser) {
        String postId = getStringInput("Enter the ID of the post you want to comment on: ");
        String content = getStringInput("Enter your comment: ");
        commentOnPost(postId, currentUser.getUsername(), content);
    }

    @Override
    public void handleLikePost(User currentUser) {
        String postId = getStringInput("Enter the ID of the post you want to like: ");
        likePost(postId, currentUser.getUsername());
    }

    @Override
    public void handleSharePost(User currentUser) {
        String postId = getStringInput("Enter the ID of the post you want to share: ");
        sharePost(postId, currentUser.getUsername());
    }

    @Override
    public void handleCreateGroup(User currentUser) {
        String name = getStringInput("Enter group name: ");
        String description = getStringInput("Enter group description: ");
        createGroup(name, description);
    }

    @Override
    public void handleGroupOperations(User currentUser) {
        System.out.println("\n===== Group Operations =====");
        System.out.println("Available groups:");
        List<String> allGroups = listAllGroups();
        allGroups.forEach(System.out::println);
        
        String groupName = getStringInput("Enter group name to join/leave (or press enter to cancel): ");
        if (!groupName.isEmpty()) {
            if (allGroups.contains(groupName)) {
                List<String> groupMembers = getGroupMembers(groupName);
                if (groupMembers.contains(currentUser.getUsername())) {
                    leaveGroup(groupName, currentUser.getUsername());
                } else {
                    joinGroup(groupName, currentUser.getUsername());
                }
            } else {
                System.out.println("Group not found: " + groupName);
            }
        }
    }

    @Override
    public void handleViewGroupDiscussions() {
        String groupName = getStringInput("Enter group name to view discussions: ");
        List<String> discussions = getGroupDiscussions(groupName);
        if (discussions.isEmpty()) {
            System.out.println("No discussions found for this group.");
        } else {
            System.out.println("\n===== Group Discussions =====");
            discussions.forEach(System.out::println);
        }
    }

    @Override
    public void handleGroupChat(User currentUser) {
        String groupName = getStringInput("Enter group name to chat in: ");
        while (true) {
            String message = getStringInput("Enter your message (or 'exit' to leave chat): ");
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            postInGroup(groupName, currentUser.getUsername(), message);
            
            // Display recent messages
            List<String> recentMessages = getGroupDiscussions(groupName);
            System.out.println("\nRecent messages:");
            for (int i = 0; i < Math.min(5, recentMessages.size()); i++) {
                System.out.println(recentMessages.get(i));
            }
        }
    }

    @Override
    public void handleFollowOperations(User currentUser) {
        String username = getStringInput("Enter username to follow/unfollow: ");
        List<String> followers = getFollowers(username);
        if (followers.contains(currentUser.getUsername())) {
            unfollowUser(currentUser.getUsername(), username);
        } else {
            followUser(currentUser.getUsername(), username);
        }
    }

    @Override
    public void handleViewFollowersFollowing(User currentUser) {
        System.out.println("\n===== Your Followers =====");
        List<String> followers = getFollowers(currentUser.getUsername());
        if (followers.isEmpty()) {
            System.out.println("You have no followers.");
        } else {
            followers.forEach(System.out::println);
        }

        System.out.println("\n===== Users You Follow =====");
        List<String> following = getFollowing(currentUser.getUsername());
        if (following.isEmpty()) {
            System.out.println("You are not following anyone.");
        } else {
            following.forEach(System.out::println);
        }
    }

    @Override
    public void handleViewEvents() {
        System.out.println("\n===== Upcoming Events =====");
        List<Event> events = viewUpcomingEvents();
        if (events.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            events.forEach(System.out::println);
        }
    }

    @Override
    public void handleCreateEvent(User currentUser) {
        String name = getStringInput("Enter event name: ");
        String date = getStringInput("Enter event date (YYYY-MM-DD): ");
        String description = getStringInput("Enter event description: ");
        String location = getStringInput("Enter event location: ");
        createEvent(name, date, description, location);
    }

    @Override
    public void handleRSVPToEvent(User currentUser) {
        int eventId = getIntInput("Enter the ID of the event you want to RSVP to: ");
        rsvpToEvent(eventId, currentUser.getUsername());
    }

    @Override
    public List<Post> getRecentPosts(int limit) {
        try {
            return SocialDatabaseConnection.getRecentPosts(limit);
        } catch (SQLException e) {
            System.out.println("Error retrieving recent posts: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> listAllGroups() {
        try {
            return SocialDatabaseConnection.getAllGroups();
        } catch (SQLException e) {
            System.out.println("Error retrieving groups: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<String> getGroupMembers(String groupName) {
        try {
            return SocialDatabaseConnection.getGroupMembers(groupName);
        } catch (SQLException e) {
            System.out.println("Error retrieving group members: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<Comment> getCommentsForPost(int postId) {
        try {
            return SocialDatabaseConnection.getCommentsForPost(postId);
        } catch (SQLException e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
