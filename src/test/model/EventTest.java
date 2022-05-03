package model;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// test class for Event
public class EventTest {

    @Test
    void testConstructor() {
        Date date = Calendar.getInstance().getTime();
        String description = "event!";
        Event event = new Event("event!");
        assertEquals(date, event.getDate());
        assertEquals(description, event.getDescription());
    }

    @Test
    void testEquals() {
        Event event = new Event("event!");
        Event event2 = new Event("second");
        assertFalse(event.equals(null));
        Integer i = 7;
        assertFalse(event.equals(i));
        assertFalse(event.equals(event2));
        assertFalse(event.equals(new Event("event!")));
    }

    @Test
    void testHashCode() {
        Event event = new Event("event!");
        assertEquals(13*event.getDate().hashCode() + event.getDescription().hashCode(), event.hashCode());
    }

    @Test
    void testToString() {
        Event event = new Event("event!");
        assertEquals(event.getDate().toString() + "\n" + event.getDescription(), event.toString());
    }
}
