package social_Network;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SocialDatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/social_network_db";
    private static final String USER = "project_user";
    private static final String PASSWORD = "Luna123!";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // User Profile methods
    public static void createUserProfile(UserProfile profile) throws SQLException {
        String sql = "INSERT INTO user_profiles (username, favorite_books, reading_habits, literary_preferences) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profile.getUsername());
            pstmt.setString(2, profile.getFavoriteBooks());
            pstmt.setString(3, profile.getReadingHabits());
            pstmt.setString(4, profile.getLiteraryPreferences());
            pstmt.executeUpdate();
        }
    }

    public static UserProfile getUserProfile(String username) throws SQLException {
        String sql = "SELECT * FROM user_profiles WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserProfile profile = new UserProfile(rs.getString("username"));
                    profile.setFavoriteBooks(rs.getString("favorite_books"));
                    profile.setReadingHabits(rs.getString("reading_habits"));
                    profile.setLiteraryPreferences(rs.getString("literary_preferences"));
                    return profile;
                }
            }
        }
        return null;
    }

    public static void updateUserProfile(UserProfile profile) throws SQLException {
        String sql = "UPDATE user_profiles SET favorite_books = ?, reading_habits = ?, literary_preferences = ? WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profile.getFavoriteBooks());
            pstmt.setString(2, profile.getReadingHabits());
            pstmt.setString(3, profile.getLiteraryPreferences());
            pstmt.setString(4, profile.getUsername());
            pstmt.executeUpdate();
        }
    }

    // Post methods
    public static void createPost(Post post) throws SQLException {
        String sql = "INSERT INTO posts (username, content, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, post.getUsername());
            pstmt.setString(2, post.getContent());
            pstmt.setTimestamp(3, Timestamp.valueOf(post.getTimestamp()));
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static List<Post> getRecentPosts(int limit) throws SQLException {
        String sql = "SELECT * FROM posts ORDER BY timestamp DESC LIMIT ?";
        List<Post> posts = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(rs.getInt("id"), rs.getString("username"), rs.getString("content"));
                    post.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    public static Post getPost(int postId) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Post post = new Post(rs.getInt("id"), rs.getString("username"), rs.getString("content"));
                    post.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    return post;
                }
            }
        }
        return null;
    }

    // Comment methods
    public static void addComment(int postId, Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (post_id, username, content, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.setString(2, comment.getUsername());
            pstmt.setString(3, comment.getContent());
            pstmt.setTimestamp(4, Timestamp.valueOf(comment.getTimestamp()));
            pstmt.executeUpdate();
        }
    }

    public static List<Comment> getCommentsForPost(int postId) throws SQLException {
        String sql = "SELECT * FROM comments WHERE post_id = ? ORDER BY timestamp";
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment(rs.getString("username"), rs.getString("content"));
                    comment.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    // Like methods
    public static void addLike(int postId, String username) throws SQLException {
        String sql = "INSERT INTO likes (post_id, username) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

    public static void removeLike(int postId, String username) throws SQLException {
        String sql = "DELETE FROM likes WHERE post_id = ? AND username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

    // Group methods
    public static void createGroup(String name, String description) throws SQLException {
        String sql = "INSERT INTO `groups` (name, description) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.executeUpdate();
        }
    }

    public static void joinGroup(String groupName, String username) throws SQLException {
        String sql = "INSERT INTO group_members (group_name, username) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

    public static void leaveGroup(String groupName, String username) throws SQLException {
        String sql = "DELETE FROM group_members WHERE group_name = ? AND username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }

    public static void postInGroup(String groupName, String username, String content) throws SQLException {
        String sql = "INSERT INTO group_posts (group_name, username, content, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setString(2, username);
            pstmt.setString(3, content);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating post failed, no rows affected.");
            }
        }
    }

    public static List<String> getGroupDiscussions(String groupName) throws SQLException {
        String sql = "SELECT username, content, timestamp FROM group_posts WHERE group_name = ? ORDER BY timestamp DESC";
        List<String> discussions = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String discussion = rs.getString("username") + " - " + 
                                        rs.getTimestamp("timestamp") + ": " + 
                                        rs.getString("content");
                    discussions.add(discussion);
                }
            }
        }
        return discussions;
    }

    public static List<String> getAllGroups() throws SQLException {
        String sql = "SELECT name FROM `groups`";
        List<String> groups = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                groups.add(rs.getString("name"));
            }
        }
        return groups;
    }

    public static List<String> getGroupMembers(String groupName) throws SQLException {
        String sql = "SELECT username FROM group_members WHERE group_name = ?";
        List<String> members = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    members.add(rs.getString("username"));
                }
            }
        }
        return members;
    }

    // Following methods
    public static void followUser(String follower, String followed) throws SQLException {
        String sql = "INSERT INTO followers (follower, followed) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, follower);
            pstmt.setString(2, followed);
            pstmt.executeUpdate();
        }
    }

    public static void unfollowUser(String follower, String followed) throws SQLException {
        String sql = "DELETE FROM followers WHERE follower = ? AND followed = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, follower);
            pstmt.setString(2, followed);
            pstmt.executeUpdate();
        }
    }

    public static List<String> getFollowers(String username) throws SQLException {
        String sql = "SELECT follower FROM followers WHERE followed = ?";
        List<String> followers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    followers.add(rs.getString("follower"));
                }
            }
        }
        return followers;
    }

    public static List<String> getFollowing(String username) throws SQLException {
        String sql = "SELECT followed FROM followers WHERE follower = ?";
        List<String> following = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    following.add(rs.getString("followed"));
                }
            }
        }
        return following;
    }

    // Event methods
    public static void createEvent(Event event) throws SQLException {
        String sql = "INSERT INTO events (name, date, description, location) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, event.getName());
            pstmt.setString(2, event.getDate());
            pstmt.setString(3, event.getDescription());
            pstmt.setString(4, event.getLocation());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    event.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static List<Event> getUpcomingEvents() throws SQLException {
        String sql = "SELECT * FROM events WHERE date >= CURDATE() ORDER BY date";
        List<Event> events = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event(rs.getInt("id"), rs.getString("name"), rs.getString("date"),
                            rs.getString("description"), rs.getString("location"));
                    events.add(event);
                }
            }
        }
        return events;
    }

    public static void rsvpToEvent(int eventId, String username) throws SQLException {
        String sql = "INSERT INTO event_attendees (event_id, username) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        }
    }
}
