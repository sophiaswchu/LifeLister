package model;

import org.json.JSONObject;
import persistence.Writable;

// Bird represents the relevant information for a birder: a name and sighting information
public class Bird implements Writable {
    private final String name;
    private Sighting lastSighting;

    // EFFECTS: creates a new Bird object
    public Bird(String name, Sighting sighting) {
        this.name = name;
        this.lastSighting = sighting;
    }

    // EFFECTS: returns the name of given Bird object
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the lastSighting of a given Bird object
    public Sighting getLastSighting() {
        return this.lastSighting;
    }

    // MODIFIES: this
    // EFFECTS: updates the lastSighting to be newSighting, logs a new Event to EventLog
    public void updateSighting(Sighting newSighting) {
        Event newE = new Event("Sighting of " + name + " changed from " + lastSighting.toString() + " to "
                + newSighting.toString() + ".");
        EventLog.getInstance().logEvent(newE);
        this.lastSighting = newSighting;
    }

    // EFFECTS: produces a String representation of given Bird
    public String toString() {
        return ("Name: " + this.name + "\n" + "Last Sighting: " + this.lastSighting.toString());
    }

    // EFFECTS: produces a String representation of given Bird on one line
    public String toLine() {
        return ("Bird Name: " + this.name + "      " + "Last Sighting: " + this.lastSighting.toString());
    }

    // EFFECTS: returns a new JSONObject of the Bird
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sighting", lastSighting.toString());
        return json;
    }
}
