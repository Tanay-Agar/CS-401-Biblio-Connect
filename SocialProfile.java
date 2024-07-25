package social_Network;

public class SocialProfile {
    private String username;
    private String favoriteBook;
    private String bio;

    public SocialProfile(String username, String favoriteBook, String bio) {
        this.username = username;
        this.favoriteBook = favoriteBook;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public String getFavoriteBook() {
        return favoriteBook;
    }

    public void setFavoriteBook(String favoriteBook) {
        this.favoriteBook = favoriteBook;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "SocialProfile{" +
                "username='" + username + '\'' +
                ", favoriteBook='" + favoriteBook + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
