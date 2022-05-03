package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Sighting
public class SightingTest {
    Sighting sighting1;
    Sighting sighting2;

    @BeforeEach
    void runBefore() {
        sighting1 = new Sighting(01, 02, 2003, "Vancouver");
        sighting2 = new Sighting(04, 05, 2006, "Richmond");
    }

    @Test
    void testGetYear() {
        assertEquals(2003, sighting1.getYear());
        assertEquals(2006, sighting2.getYear());
    }

    @Test
    void testGetDay() {
        assertEquals(02, sighting1.getDay());
        assertEquals(05, sighting2.getDay());
    }

    @Test
    void testGetMonth() {
        assertEquals(01, sighting1.getMonth());
        assertEquals(04, sighting2.getMonth());
    }

    @Test
    void testGetLocation() {
        assertEquals("Vancouver", sighting1.getLocation());
        assertEquals("Richmond", sighting2.getLocation());
    }

    @Test
    void testToString() {
        Sighting sighting3 = new Sighting(12, 07, 2020, "Surrey");
        Sighting sighting4 = new Sighting(03, 31, 2021, "Burnaby");
        Sighting sighting5 = new Sighting(11, 26, 2019, "Langley");
        assertEquals("01/02/2003, Vancouver", sighting1.toString());
        assertEquals("04/05/2006, Richmond", sighting2.toString());
        assertEquals("12/07/2020, Surrey", sighting3.toString());
        assertEquals("03/31/2021, Burnaby", sighting4.toString());
        assertEquals("11/26/2019, Langley", sighting5.toString());
    }

    @Test
    void testProperDateFutureYear() {
        assertFalse(Sighting.properDate(01, 01, 2022));
        assertFalse(Sighting.properDate(01, 01, 3000));
    }

    @Test
    void testProperDateNegativeYear() {
        assertFalse(Sighting.properDate(01, 01, 0));
        assertFalse(Sighting.properDate(01, 01, -100));
    }

    @Test
    void testProperDateMonthTooLarge() {
        assertFalse(Sighting.properDate(13, 01, 2000));
        assertFalse(Sighting.properDate(30, 01, 2010));
    }

    @Test
    void testProperDateNegativeMonth() {
        assertFalse(Sighting.properDate(0, 01, 2000));
        assertFalse(Sighting.properDate(-1, 01, 2010));
    }

    @Test
    void testProperDateDayTooLarge() {
        assertFalse(Sighting.properDate(06, 32, 2000));
        assertFalse(Sighting.properDate(01, -1, 2010));
    }

    @Test
    void testProperDateNegativeDay() {
        assertFalse(Sighting.properDate(06, 0, 2000));
        assertFalse(Sighting.properDate(01, -1, 2010));
    }

    @Test
    void testProperDateThirtyFirst() {
        assertFalse(Sighting.properDate(04, 31, 2000));
        assertTrue(Sighting.properDate(07, 31, 2010));
        assertTrue(Sighting.properDate(07, 30, 2008));
        assertTrue(Sighting.properDate(04, 30, 2009));
    }

    @Test
    void testProperDateThirtieth() {
        assertFalse(Sighting.properDate(02, 30, 2000));
        assertTrue(Sighting.properDate(06, 30, 2010));
    }

    @Test
    void testProperDateLeapDay() {
        assertFalse(Sighting.properDate(02, 29, 2010));
        assertTrue(Sighting.properDate(02, 29, 2000));
        assertTrue(Sighting.properDate(02, 28, 2000));
        assertTrue(Sighting.properDate(02, 28, 2010));
        assertTrue(Sighting.properDate(03, 29, 2010));
        assertTrue(Sighting.properDate(03, 29, 2000));
        assertTrue(Sighting.properDate(03, 28, 2000));
        assertTrue(Sighting.properDate(03, 28, 2010));
    }

    @Test
    void testValueOf() {
        String str = sighting1.toString();
        Sighting new1 = Sighting.valueOf(str);
        assertEquals(2, new1.getDay());
        assertEquals(1, new1.getMonth());
        assertEquals(2003, new1.getYear());
        assertEquals("Vancouver", new1.getLocation());
    }
}
