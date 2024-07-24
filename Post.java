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

    public Post(int id, String username, String content) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.likes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void addLike(String username) {
        if (!likes.contains(username)) {
            likes.add(username);
        }
    }

    public void removeLike(String username) {
        likes.remove(username);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", likes=" + likes.size() +
                '}';
    }
}