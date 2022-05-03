package persistence;

import model.Bird;
import model.LifeList;
import model.Sighting;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// reads data from a Json file
public class Reader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads lifeList from file and returns it
    // throws IOException if an error occurs reading data from file
    public LifeList read() throws IOException {
        String data = readFile(source);
        JSONObject jsonObject = new JSONObject(data);
        return parseLifeList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private LifeList parseLifeList(JSONObject jsonObject) {
        LifeList lifelist = new LifeList();
        addBirds(lifelist, jsonObject);
        return lifelist;
    }

    // MODIFIES: lifelist
    // EFFECTS: parses birds from JSON object and adds them to workroom
    private void addBirds(LifeList lifelist, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("birds");
        for (Object json : jsonArray) {
            JSONObject nextBird = (JSONObject) json;
            addBird(lifelist, nextBird);
        }
    }

    // MODIFIES: lifelist
    // EFFECTS: parses bird from JSON object and adds it to workroom
    private void addBird(LifeList lifelist, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Sighting sighting = Sighting.valueOf(jsonObject.getString("sighting"));
        Bird bird = new Bird(name, sighting);
        lifelist.addBird(bird);
    }
}
