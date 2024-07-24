package social_Network;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private String date;
    private List<String> attendees;

    public Event(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.attendees = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public List<String> getAttendees() {
        return attendees;
    }

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
                ", attendees=" + attendees.size() +
                '}';
    }
}