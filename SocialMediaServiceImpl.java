package social_Network;

import biblioConnect_v3.User;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SocialMediaServiceImpl implements SocialMediaService {
    private static SocialMediaServiceImpl instance;
    private Scanner scanner;
    private Map<String, SocialProfile> profiles;
    private List<Post> posts;
    private Map<String, List<String>> groups;
    private Map<String, List<String>> followers;
    private List<Event> events;
    private int postIdCounter;

    private SocialMediaServiceImpl() {
        scanner = new Scanner(System.in);
        profiles = new HashMap<>();
        posts = new ArrayList<>();
        groups = new HashMap<>();
        followers = new HashMap<>();
        events = new ArrayList<>();
        postIdCounter = 1;
        initializeGroups();
        initializeEvents();
    }

    public static SocialMediaService getInstance() {
        if (instance == null) {
            instance = new SocialMediaServiceImpl();
        }
        return instance;
    }

    private void initializeGroups() {
        groups.put("Fiction", new ArrayList<>());
        groups.put("Non-Fiction", new ArrayList<>());
        groups.put("Mystery", new ArrayList<>());
        groups.put("Science Fiction", new ArrayList<>());
    }

    private void initializeEvents() {
        events.add(new Event(1, "Book Club Meeting", "2023-08-01"));
        events.add(new Event(2, "Author Signing", "2023-08-15"));
        events.add(new Event(3, "Reading Workshop", "2023-08-30"));
    }

    @Override
    public void showSocialMediaMenu(User currentUser) {
        while (true) {
            System.out.println("\n===== Social Media Menu =====");
            System.out.println("1. View/Edit Profile");
            System.out.println("2. Post a Message");
            System.out.println("3. View Recent Posts");
            System.out.println("4. Join/Leave Group");
            System.out.println("5. View Group Discussions");
            System.out.println("6. Follow/Unfollow User");
            System.out.println("7. View Followers/Following");
            System.out.println("8. View Upcoming Events");
            System.out.println("9. Return to Main Menu");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    handleProfileOperations(currentUser);
                    break;
                case 2:
                    handlePostMessage(currentUser);
                    break;
                case 3:
                    handleViewRecentPosts();
                    break;
                case 4:
                    handleGroupOperations(currentUser);
                    break;
                case 5:
                    handleViewGroupDiscussions();
                    break;
                case 6:
                    handleFollowOperations(currentUser);
                    break;
                case 7:
                    handleViewFollowersFollowing(currentUser);
                    break;
                case 8:
                    handleViewEvents(currentUser);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleProfileOperations(User currentUser) {
        System.out.println("\n===== Profile Operations =====");
        if (!profiles.containsKey(currentUser.getUsername())) {
            System.out.println("You don't have a social profile yet. Let's create one!");
            createProfile(currentUser);
        } else {
            viewProfile(currentUser.getUsername());
            System.out.println("\nDo you want to edit your profile? (y/n)");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                editProfile(currentUser);
            }
        }
    }

    private void createProfile(User user) {
        String favoriteBook = getStringInput("Enter your favorite book: ");
        String bio = getStringInput("Enter a brief bio: ");
        profiles.put(user.getUsername(), new SocialProfile(user.getUsername(), favoriteBook, bio));
        System.out.println("Profile created successfully!");
    }

    @Override
    public void viewProfile(String username) {
        SocialProfile profile = profiles.get(username);
        if (profile != null) {
            System.out.println("\n===== " + username + "'s Profile =====");
            System.out.println("Username: " + profile.getUsername());
            System.out.println("Favorite Book: " + profile.getFavoriteBook());
            System.out.println("Bio: " + profile.getBio());
        } else {
            System.out.println("Profile not found for user: " + username);
        }
    }

    @Override
    public void editProfile(User user) {
        SocialProfile profile = profiles.get(user.getUsername());
        if (profile != null) {
            String favoriteBook = getStringInput("Enter your new favorite book (or press enter to keep current): ");
            String bio = getStringInput("Enter your new bio (or press enter to keep current): ");
            
            if (!favoriteBook.isEmpty()) {
                profile.setFavoriteBook(favoriteBook);
            }
            if (!bio.isEmpty()) {
                profile.setBio(bio);
            }
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("Profile not found. Creating a new one.");
            createProfile(user);
        }
    }

    @Override
    public void postMessage(User user, String message) {
        if (message.trim().isEmpty()) {
            System.out.println("Message cannot be empty.");
            return;
        }
        Post post = new Post(postIdCounter++, user.getUsername(), message);
        posts.add(post);
        System.out.println("Message posted successfully!");
    }

    private void handlePostMessage(User currentUser) {
        String message = getStringInput("Enter your book-related message: ");
        postMessage(currentUser, message);
    }

    @Override
    public List<String> viewRecentPosts() {
        List<String> recentPosts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = posts.size() - 1; i >= Math.max(0, posts.size() - 10); i--) {
            Post post = posts.get(i);
            recentPosts.add(String.format("[%s] %s: %s (Likes: %d)", 
                post.getTimestamp().format(formatter), post.getUsername(), post.getContent(), post.getLikes().size()));
        }
        return recentPosts;
    }

    private void handleViewRecentPosts() {
        List<String> recentPosts = viewRecentPosts();
        System.out.println("\n===== Recent Posts =====");
        for (String post : recentPosts) {
            System.out.println(post);
        }
        System.out.println("\nDo you want to like a post? (y/n)");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            int postId = getIntInput("Enter the post ID you want to like: ");
            likePost(null, postId); // We're not using the User parameter in this implementation
        }
    }

    @Override
    public void likePost(User user, int postId) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                post.getLikes().add(user.getUsername());
                System.out.println("Post liked successfully!");
                return;
            }
        }
        System.out.println("Post not found.");
    }

    @Override
    public void joinGroup(User user, String groupName) {
        if (groups.containsKey(groupName)) {
            groups.get(groupName).add(user.getUsername());
            System.out.println("You've joined the " + groupName + " group!");
        } else {
            System.out.println("Group not found.");
        }
    }

    @Override
    public void leaveGroup(User user, String groupName) {
        if (groups.containsKey(groupName)) {
            groups.get(groupName).remove(user.getUsername());
            System.out.println("You've left the " + groupName + " group.");
        } else {
            System.out.println("Group not found.");
        }
    }

    private void handleGroupOperations(User currentUser) {
        System.out.println("\n===== Group Operations =====");
        System.out.println("Available groups:");
        for (String groupName : groups.keySet()) {
            System.out.println("- " + groupName);
        }
        String groupName = getStringInput("Enter the group name to join/leave (or press enter to cancel): ");
        if (!groupName.isEmpty()) {
            if (groups.get(groupName).contains(currentUser.getUsername())) {
                System.out.println("You're already in this group. Do you want to leave? (y/n)");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    leaveGroup(currentUser, groupName);
                }
            } else {
                joinGroup(currentUser, groupName);
            }
        }
    }

    @Override
    public void viewGroupDiscussions(String groupName) {
        if (groups.containsKey(groupName)) {
            System.out.println("\n===== " + groupName + " Group Discussion =====");
            for (Post post : posts) {
                if (groups.get(groupName).contains(post.getUsername())) {
                    System.out.println(post.getUsername() + ": " + post.getContent());
                }
            }
        } else {
            System.out.println("Group not found.");
        }
    }

    private void handleViewGroupDiscussions() {
        String groupName = getStringInput("Enter the group name to view discussions: ");
        viewGroupDiscussions(groupName);
    }

    @Override
    public void postToGroupDiscussion(User user, String groupName, String message) {
        if (groups.containsKey(groupName) && groups.get(groupName).contains(user.getUsername())) {
            Post post = new Post(postIdCounter++, user.getUsername(), message);
            posts.add(post);
            System.out.println("Message posted to " + groupName + " group discussion!");
        } else {
            System.out.println("You're not a member of this group or the group doesn't exist.");
        }
    }

    @Override
    public void followUser(User follower, String followedUsername) {
        if (!profiles.containsKey(followedUsername)) {
            System.out.println("User not found.");
            return;
        }
        followers.computeIfAbsent(followedUsername, k -> new ArrayList<>()).add(follower.getUsername());
        System.out.println("You are now following " + followedUsername);
    }

    @Override
    public void unfollowUser(User follower, String unfollowedUsername) {
        if (followers.containsKey(unfollowedUsername)) {
            followers.get(unfollowedUsername).remove(follower.getUsername());
            System.out.println("You have unfollowed " + unfollowedUsername);
        } else {
            System.out.println("You were not following this user.");
        }
    }

    private void handleFollowOperations(User currentUser) {
        String username = getStringInput("Enter the username to follow/unfollow: ");
        if (followers.containsKey(username) && followers.get(username).contains(currentUser.getUsername())) {
            System.out.println("You're already following this user. Do you want to unfollow? (y/n)");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                unfollowUser(currentUser, username);
            }
        } else {
            followUser(currentUser, username);
        }
    }

    @Override
    public List<String> viewFollowers(String username) {
        return followers.getOrDefault(username, new ArrayList<>());
    }

    @Override
    public List<String> viewFollowing(String username) {
        List<String> following = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : followers.entrySet()) {
            if (entry.getValue().contains(username)) {
                following.add(entry.getKey());
            }
        }
        return following;
    }

    private void handleViewFollowersFollowing(User currentUser) {
        System.out.println("\n===== Followers =====");
        List<String> userFollowers = viewFollowers(currentUser.getUsername());
        for (String follower : userFollowers) {
            System.out.println(follower);
        }

        System.out.println("\n===== Following =====");
        List<String> following = viewFollowing(currentUser.getUsername());
        for (String followed : following) {
            System.out.println(followed);
        }
    }

    @Override
    public List<String> viewUpcomingEvents() {
        List<String> upcomingEvents = new ArrayList<>();
        for (Event event : events) {
            upcomingEvents.add(event.getName() + " on " + event.getDate());
        }
        return upcomingEvents;
    }

    @Override
    public void rsvpToEvent(User user, int eventId) {
        for (Event event : events) {
            if (event.getId() == eventId) {
                event.getAttendees().add(user.getUsername());
                System.out.println("You have RSVP'd to " + event.getName());
                return;
            }
        }
        System.out.println("Event not found.");
    }

    private void handleViewEvents(User currentUser) {
        System.out.println("\n===== Upcoming Events =====");
        List<String> upcomingEvents = viewUpcomingEvents();
        for (int i = 0; i < upcomingEvents.size(); i++) {
            System.out.println((i + 1) + ". " + upcomingEvents.get(i));
        }

        System.out.println("\nDo you want to RSVP to an event? (y/n)");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            int eventChoice = getIntInput("Enter the number of the event you want to RSVP to: ");
            if (eventChoice > 0 && eventChoice <= events.size()) {
                rsvpToEvent(currentUser, events.get(eventChoice - 1).getId());
            } else {
                System.out.println("Invalid event number.");
            }
        }
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}