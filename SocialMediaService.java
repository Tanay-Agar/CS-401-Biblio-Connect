package social_Network;

import biblioConnect_v3.User;
import java.util.List;

public interface SocialMediaService {
    void createUserProfile(String username, String favoriteBooks, String readingHabits, String literaryPreferences);
    void updateUserProfile(String username, String favoriteBooks, String readingHabits, String literaryPreferences);
    UserProfile getUserProfile(String username);
    void createPost(String username, String content);
    void commentOnPost(String postId, String username, String content);
    void likePost(String postId, String username);
    void sharePost(String postId, String username);
    void createGroup(String name, String description);
    void joinGroup(String groupId, String username);
    void leaveGroup(String groupId, String username);
    void postInGroup(String groupId, String username, String content);
    List<String> getGroupDiscussions(String groupId);
    void followUser(String followerUsername, String followedUsername);
    void unfollowUser(String followerUsername, String unfollowedUsername);
    List<String> getFollowers(String username);
    List<String> getFollowing(String username);
    void createEvent(String name, String date, String description, String location);
    void rsvpToEvent(int eventId, String username);
    List<Event> viewUpcomingEvents();
    void showSocialMediaMenu(User currentUser);
    void handleProfileOperations(User currentUser);
    void handleCreatePost(User currentUser);
    void handleViewRecentPosts();
    void handleCommentOnPost(User currentUser);
    void handleLikePost(User currentUser);
    void handleSharePost(User currentUser);
    void handleCreateGroup(User currentUser);
    void handleGroupOperations(User currentUser);
    void handleViewGroupDiscussions();
    void handleGroupChat(User currentUser);
    void handleFollowOperations(User currentUser);
    void handleViewFollowersFollowing(User currentUser);
    void handleViewEvents();
    void handleCreateEvent(User currentUser);
    void handleRSVPToEvent(User currentUser);
    List<Post> getRecentPosts(int limit);
    List<String> listAllGroups();
}
