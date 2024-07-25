package social_Network;

import java.time.LocalDateTime;

public class SocialInteraction {
    private int interactionId;
    private String username;
    private String bookId;
    private String content;
    private LocalDateTime timestamp;

    public SocialInteraction(String username, String bookId, String content, LocalDateTime timestamp) {
        this.username = username;
        this.bookId = bookId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getInteractionId() { return interactionId; }
    public void setInteractionId(int interactionId) { this.interactionId = interactionId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
