package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

// Test class for LifeList
public class LifeListTest {
    LifeList lifeList1;
    LifeList lifeListLong;
    Bird bird1;
    Bird bird2;
    Bird bird3;
    Bird bird4;
    Sighting sighting1 = new Sighting(01, 01, 2001, "Vancouver");
    Sighting sighting2 = new Sighting(02, 02, 2002, "Richmond");
    Sighting sighting3 = new Sighting(03, 03, 2003, "Burnaby");
    Sighting sighting4 = new Sighting(04, 04, 2004, "Surrey");

    @BeforeEach
    void runBefore() {
        lifeList1 = new LifeList();
        bird1 = new Bird("Sophia", sighting1);
        bird2 = new Bird("Chu", sighting2);
        lifeListLong = new LifeList();
        bird3 = new Bird("CPSC", sighting3);
        bird4 = new Bird("210",sighting4);
        lifeListLong.addBird(bird1);
        lifeListLong.addBird(bird3);
        lifeListLong.addBird(bird4);
    }

    @Test
    void testAddBird() {
        lifeList1.addBird(bird1);
        assertTrue(lifeList1.contains(bird1));
        lifeListLong.addBird(bird2);
        assertTrue(lifeListLong.contains(bird2));
    }

    @Test
    void testContains() {
        assertFalse(lifeList1.contains(bird1));
        assertFalse(lifeListLong.contains(bird2));
        assertTrue(lifeListLong.contains(bird3));
    }

    @Test
    void testListToString() {
        assertEquals("", lifeList1.listToString());
        String result = (bird1.toString() + "\n" + bird3.toString() + "\n" + bird4.toString() + "\n");
        assertEquals(result, lifeListLong.listToString());
    }

    @Test
    void testIsBirdInLifeList() {
        assertFalse(lifeList1.isBirdInLifeList("Sophia"));
        assertFalse(lifeListLong.isBirdInLifeList("Chu"));
        assertTrue(lifeListLong.isBirdInLifeList("210"));
    }

    @Test
    void testLastBird() {
        lifeList1.addBird(bird2);
        assertEquals(bird2, lifeList1.lastBird());
        assertEquals(bird4, lifeListLong.lastBird());
    }

    @Test
    void testGetBird() {
        assertEquals(null, lifeList1.getBird("Sophia"));
        assertEquals(null, lifeListLong.getBird("Chu"));
        assertEquals(bird3, lifeListLong.getBird("CPSC"));
    }

    @Test
    void testIsEmpty() {
        assertTrue(lifeList1.isEmpty());
        assertFalse(lifeListLong.isEmpty());
    }

    @Test
    void testBirdsInTimePeriod() {
        lifeList1.addBird(new Bird("md", new Sighting(02, 03, 2002, "f")));
        lifeList1.addBird(new Bird("m", new Sighting(02, 14, 2014, "g")));
        assertEquals("md\nm\n", lifeList1.birdsInTimePeriod(0, 0, 0).namesToString());
        lifeList1.addBird(new Bird("mdy1", new Sighting(02, 03, 2001, "a")));
        lifeList1.addBird(new Bird("mdy2", new Sighting(02, 03, 2001, "b")));
        lifeList1.addBird(new Bird("my", new Sighting(02, 07, 2001, "c")));
        lifeList1.addBird(new Bird("y", new Sighting(04, 19, 2001, "a")));
        lifeList1.addBird(new Bird("rand", new Sighting(05, 30, 2017, "a")));
        assertEquals("", lifeList1.birdsInTimePeriod(2020, 0, 0).namesToString());
        assertEquals("mdy1\nmdy2\nmy\ny\n", lifeList1.birdsInTimePeriod(2001, 0, 0).namesToString());
        assertEquals("mdy1\nmdy2\nmy\n", lifeList1.birdsInTimePeriod(2001, 02, 0).namesToString());
        assertEquals("mdy1\nmdy2\n", lifeList1.birdsInTimePeriod(2001, 02, 03).namesToString());
    }

    @Test
    void testBirdsInDay() {
        lifeList1.addBird(new Bird("mdy1", new Sighting(02, 03, 2001, "a")));
        lifeList1.addBird(new Bird("mdy2", new Sighting(02, 03, 2001, "b")));
        lifeList1.addBird(new Bird("my", new Sighting(02, 07, 2001, "c")));
        lifeList1.addBird(new Bird("y", new Sighting(04, 19, 2001, "a")));
        lifeList1.addBird(new Bird("rand", new Sighting(05, 30, 2017, "a")));
        lifeList1.addBird(new Bird("md", new Sighting(02, 03, 2002, "f")));
        lifeList1.addBird(new Bird("m", new Sighting(02, 14, 2014, "g")));
        assertEquals("", lifeList1.birdsInDay(32).namesToString());
        assertEquals("y\n", lifeList1.birdsInDay(19).namesToString());
        assertEquals("mdy1\nmdy2\nmd\n", lifeList1.birdsInDay(3).namesToString());
    }

    @Test
    void testBirdsInMonth() {
        lifeList1.addBird(new Bird("mdy1", new Sighting(02, 03, 2001, "a")));
        lifeList1.addBird(new Bird("mdy2", new Sighting(02, 03, 2001, "b")));
        lifeList1.addBird(new Bird("my", new Sighting(02, 07, 2001, "c")));
        lifeList1.addBird(new Bird("y", new Sighting(04, 19, 2001, "a")));
        lifeList1.addBird(new Bird("rand", new Sighting(05, 30, 2017, "a")));
        lifeList1.addBird(new Bird("md", new Sighting(02, 03, 2002, "f")));
        lifeList1.addBird(new Bird("m", new Sighting(02, 14, 2014, "g")));
        assertEquals("", lifeList1.birdsInMonth(13).namesToString());
        assertEquals("y\n", lifeList1.birdsInMonth(04).namesToString());
        assertEquals("mdy1\nmdy2\nmy\nmd\nm\n", lifeList1.birdsInMonth(02).namesToString());
    }

    @Test
    void testBirdsInYear() {
        lifeList1.addBird(new Bird("mdy1", new Sighting(02, 03, 2001, "a")));
        lifeList1.addBird(new Bird("mdy2", new Sighting(02, 03, 2001, "b")));
        lifeList1.addBird(new Bird("my", new Sighting(02, 07, 2001, "c")));
        lifeList1.addBird(new Bird("y", new Sighting(04, 19, 2001, "a")));
        lifeList1.addBird(new Bird("rand", new Sighting(05, 30, 2017, "a")));
        lifeList1.addBird(new Bird("md", new Sighting(02, 03, 2002, "f")));
        lifeList1.addBird(new Bird("m", new Sighting(02, 14, 2014, "g")));
        assertEquals("", lifeList1.birdsInYear(2020).namesToString());
        assertEquals("rand\n", lifeList1.birdsInYear(2017).namesToString());
        assertEquals("mdy1\nmdy2\nmy\ny\n", lifeList1.birdsInYear(2001).namesToString());
    }

    @Test
    void testBirdsInLocation() {
        lifeList1.addBird(new Bird("mdy1", new Sighting(02, 03, 2001, "a")));
        lifeList1.addBird(new Bird("mdy2", new Sighting(02, 03, 2001, "b")));
        lifeList1.addBird(new Bird("my", new Sighting(02, 07, 2001, "c")));
        lifeList1.addBird(new Bird("y", new Sighting(04, 19, 2001, "a")));
        lifeList1.addBird(new Bird("rand", new Sighting(05, 30, 2017, "a")));
        lifeList1.addBird(new Bird("md", new Sighting(02, 03, 2002, "f")));
        lifeList1.addBird(new Bird("m", new Sighting(02, 14, 2014, "g")));
        assertEquals("", lifeList1.birdsInLocation("z").namesToString());
        assertEquals("m\n", lifeList1.birdsInLocation("g").namesToString());
        assertEquals("mdy1\ny\nrand\n", lifeList1.birdsInLocation("a").namesToString());
    }

    @Test
    void testNamesToString() {
        lifeList1.addBird(bird2);
        assertEquals(bird2.getName() + "\n", lifeList1.namesToString());
        assertEquals(bird1.getName() + "\n" + bird3.getName() + "\n" +
                bird4.getName() + "\n", lifeListLong.namesToString());
    }

    @Test
    void testGetLinkedList() {
        assertTrue(lifeList1.getLinkedList().isEmpty());
        LinkedList<Bird> linkedListLong = lifeListLong.getLinkedList();
        assertEquals(linkedListLong.get(0), bird1);
        assertEquals(linkedListLong.get(1), bird3);
        assertEquals(linkedListLong.get(2), bird4);
    }
}
