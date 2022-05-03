package persistence;

import org.json.JSONObject;

// interface for model classes that can be written to file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
