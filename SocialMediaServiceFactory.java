package social_Network;

public class SocialMediaServiceFactory {
    private static SocialMediaService instance;

    public static SocialMediaService getInstance() {
        if (instance == null) {
            instance = new SocialMediaServiceImpl();
        }
        return instance;
    }
}