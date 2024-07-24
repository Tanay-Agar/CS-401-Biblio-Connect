package social_Network;

import biblioConnect_v3.User;
import java.util.List;

public interface SocialMediaService {
    void showSocialMediaMenu(User currentUser);
    void viewProfile(String username);
    void editProfile(User user);
    void postMessage(User user, String message);
    List<String> viewRecentPosts();
    void likePost(User user, int postId);
    void joinGroup(User user, String groupName);
    void leaveGroup(User user, String groupName);
    void viewGroupDiscussions(String groupName);
    void postToGroupDiscussion(User user, String groupName, String message);
    void followUser(User follower, String followedUsername);
    void unfollowUser(User follower, String unfollowedUsername);
    List<String> viewFollowers(String username);
    List<String> viewFollowing(String username);
    List<String> viewUpcomingEvents();
    void rsvpToEvent(User user, int eventId);
    
    static SocialMediaService getInstance() {
        return SocialMediaServiceImpl.getInstance();
    }
}