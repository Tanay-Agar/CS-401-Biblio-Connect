package biblioConnect_v3;

import social_Network.SocialMediaService;

public class BiblioConnectApplication {
    public static void main(String[] args) {
        System.out.println("Starting BiblioConnect Library Management System...");
        
        try (LibraryManagementSystem system = new LibraryManagementImpl()) {
            SocialMediaService socialMediaService = SocialMediaService.getInstance();
            
            try (LibraryUI ui = new LibraryUI(system, socialMediaService)) {
                ui.start();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while running BiblioConnect: " + e.getMessage());
            e.printStackTrace();
        }
    }
}