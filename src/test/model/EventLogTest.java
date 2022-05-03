package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

// test class for EventLog
public class EventLogTest {
    EventLog log;

    @BeforeEach
    void runBefore() {
        log = EventLog.getInstance();
    }

    @Test
    void testGetInstance() {
        assertEquals(log,EventLog.getInstance());
    }

    @Test
    void testLogEvent() {
        Event event = new Event("event!");
        EventLog.getInstance().logEvent(event);
        for (Event e: EventLog.getInstance()) {
            assertEquals(event, e);
        }
    }

    @Test
    void testClear() {
        Event event = new Event("event!");
        EventLog.getInstance().logEvent(event);
        EventLog.getInstance().clear();
        for (Event e: EventLog.getInstance()) {
            assertEquals("Event log cleared.", e.getDescription());
        }
    }

    @Test
    void testIterator() {
        Iterator<Event> iterator = EventLog.getInstance().iterator();
        assertEquals(iterator, iterator);
    }
}
