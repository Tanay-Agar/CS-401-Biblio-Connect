package social_Network;

public class UserProfile {
    private String username;
    private String favoriteBooks;
    private String readingHabits;
    private String literaryPreferences;

    public UserProfile(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }
    public String getFavoriteBooks() { return favoriteBooks; }
    public void setFavoriteBooks(String favoriteBooks) { this.favoriteBooks = favoriteBooks; }
    public String getReadingHabits() { return readingHabits; }
    public void setReadingHabits(String readingHabits) { this.readingHabits = readingHabits; }
    public String getLiteraryPreferences() { return literaryPreferences; }
    public void setLiteraryPreferences(String literaryPreferences) { this.literaryPreferences = literaryPreferences; }

    @Override
    public String toString() {
        return "UserProfile{" +
                "username='" + username + '\'' +
                ", favoriteBooks='" + favoriteBooks + '\'' +
                ", readingHabits='" + readingHabits + '\'' +
                ", literaryPreferences='" + literaryPreferences + '\'' +
                '}';
    }
}