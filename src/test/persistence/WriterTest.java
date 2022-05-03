package persistence;

import model.Bird;
import model.LifeList;
import model.Sighting;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// test class for Writer
public class WriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            LifeList lifelist = new LifeList();
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            LifeList lifelist = new LifeList();
            Writer writer = new Writer("./data/testWriterEmptyLifeList.json");
            writer.open();
            writer.write(lifelist);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmptyLifeList.json");
            lifelist = reader.read();
            assertTrue(lifelist.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            LifeList lifelist = new LifeList();
            Sighting sighting1 = new Sighting(01, 01, 2001, "Vancouver");
            Sighting sighting2 = new Sighting(02, 02, 2002, "Richmond");
            lifelist.addBird(new Bird("Sophia", sighting1));
            lifelist.addBird(new Bird("Chu", sighting2));
            Writer writer = new Writer("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(lifelist);
            writer.close();

            Reader reader = new Reader("./data/testWriterGeneralWorkroom.json");
            lifelist = reader.read();
            List<Bird> birds = lifelist.getBirds();
            assertEquals(2, birds.size());
            checkBird("Sophia", "01/01/2001, Vancouver", birds.get(0));
            checkBird("Chu", "02/02/2002, Richmond", birds.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
