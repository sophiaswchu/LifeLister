package persistence;

import model.Bird;
import model.LifeList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// test class for Reader
public class ReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        Reader reader = new Reader("./data/noSuchFile.json");
        try {
            LifeList lifelist = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLifeList() {
        Reader reader = new Reader("./data/testReaderEmptyLifeList.json");
        try {
            LifeList lifelist = reader.read();
            assertTrue(lifelist.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLifeList() {
        Reader reader = new Reader("./data/testReaderGeneralLifeList.json");
        try {
            LifeList lifelist = reader.read();
            List<Bird> birds = lifelist.getBirds();
            assertEquals(2, birds.size());
            checkBird("Sophia", "01/01/2001, Vancouver", birds.get(0));
            checkBird("Chu", "02/02/2002, Richmond", birds.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
