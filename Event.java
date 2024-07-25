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
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public List<String> getAttendees() { return attendees; }

    public void addAttendee(String username) {
        if (!attendees.contains(username)) {
            attendees.add(username);
        }
    }

    public void removeAttendee(String username) {
        attendees.remove(username);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", attendees=" + attendees.size() +
                '}';
    }
}
