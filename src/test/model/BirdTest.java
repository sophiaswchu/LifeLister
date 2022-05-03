package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Bird and all its methods
class BirdTest {
    Bird bird1;
    Bird bird2;
    Sighting sighting1 = new Sighting(01, 01, 2001, "Vancouver");
    Sighting sighting2 = new Sighting(02, 02, 2002, "Richmond");
    Sighting sighting3 = new Sighting(03, 03, 2003, "Burnaby");
    Sighting sighting4 = new Sighting(04, 04, 2004, "Surrey");

    @BeforeEach
    void runBefore() {
        bird1 = new Bird("Sophia", sighting1);
        bird2 = new Bird("Chu", sighting2);
    }

    @Test
    void testGetName() {
        assertEquals( "Sophia", bird1.getName());
        assertEquals("Chu", bird2.getName());
    }

    @Test
    void testGetLastSighting() {
        assertEquals("01/01/2001, Vancouver", bird1.getLastSighting().toString());
        assertEquals("02/02/2002, Richmond", bird2.getLastSighting().toString());
    }

    @Test
    void testUpdateSighting() {
        bird1.updateSighting(sighting3);
        assertEquals(sighting3, bird1.getLastSighting());
        assertEquals( "Sophia", bird1.getName());
        bird1.updateSighting(sighting4);
        assertEquals(sighting4, bird1.getLastSighting());
        assertEquals("Sophia", bird1.getName());
        bird2.updateSighting(sighting1);
        assertEquals(sighting1, bird2.getLastSighting());
        assertEquals("Chu", bird2.getName());
    }

    @Test
    void testToString() {
        assertEquals("Name: Sophia\nLast Sighting: 01/01/2001, Vancouver", bird1.toString());
        assertEquals("Name: Chu\nLast Sighting: 02/02/2002, Richmond", bird2.toString());
    }

    @Test
    void testToLine() {
        assertEquals("Bird Name: Sophia      Last Sighting: 01/01/2001, Vancouver", bird1.toLine());
        assertEquals("Bird Name: Chu      Last Sighting: 02/02/2002, Richmond", bird2.toLine());
    }
}