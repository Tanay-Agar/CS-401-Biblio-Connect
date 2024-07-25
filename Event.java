package social_Network;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private String date;
    private String description;
    private String location;
    private List<String> attendees;

    public Event(int id, String name, String date, String description, String location) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.location = location;
        this.attendees = new ArrayList<>();
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; } // Add this method
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public List<String> getAttendees() { return attendees; }

    public void addAttendee(String userId) {
        if (!attendees.contains(userId)) {
            attendees.add(userId);
        }
    }

    public void removeAttendee(String userId) {
        attendees.remove(userId);
    }
}
