package persistence;

import model.Bird;

import static org.junit.jupiter.api.Assertions.assertEquals;

// provides a base method for ReaderTest and WriterTest
public class JsonTest {
    protected void checkBird(String name, String sighting, Bird bird) {
        assertEquals(name, bird.getName());
        assertEquals(sighting, bird.getLastSighting().toString());
    }
}
