package social_Network;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private int id;
    private String username;
    private String content;
    private LocalDateTime timestamp;
    private List<String> likes;
    private List<Comment> comments;
    private List<String> shares;

    public Post(int id, String username, String content) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.shares = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; } // Add this method
    public String getUsername() { return username; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; } // Add this method
    public List<String> getLikes() { return likes; }
    public List<Comment> getComments() { return comments; }
    public List<String> getShares() { return shares; }

    public void addLike(String username) {
        if (!likes.contains(username)) {
            likes.add(username);
        }
    }

    public void removeLike(String username) {
        likes.remove(username);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addShare(String username) {
        shares.add(username);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", likes=" + likes.size() +
                ", comments=" + comments.size() +
                ", shares=" + shares.size() +
                '}';
    }
}
