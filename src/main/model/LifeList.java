package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// LifeList represents a listing of all birds for a birder
public class LifeList implements Writable {
    private LinkedList<Bird> lifeList;

    // EFFECTS: creates a new LifeList object
    public LifeList() {
        this.lifeList = new LinkedList<>();
    }

    // EFFECTS: returns the lifeList
    public LinkedList<Bird> getLinkedList() {
        return lifeList;
    }

    // REQUIRES: a bird species name not in LifeList
    // MODIFIES: this
    // EFFECTS: adds a new Bird object to the list, logs event in EventLog
    public void addBird(Bird bird) {
        Event newE = new Event("New bird, " + bird.getName() + ", added to lifeList.");
        boolean eventIsNew = true;
        for (Event e: EventLog.getInstance()) {
            if (e.getDescription().equals(newE.getDescription())) {
                eventIsNew = false;
            }
        }
        if (eventIsNew) {
            EventLog.getInstance().logEvent(newE);
        }
        this.lifeList.add(bird);
    }

    // EFFECTS: prints the String representation of all birds in list to screen
    public String listToString() {
        String toPrint = "";
        for (Bird bird : this.lifeList) {
            toPrint = toPrint.concat(bird.toString() + "\n");
        }
        return toPrint;
    }

    // EFFECTS: returns true if a Bird with birdName is in this, false otherwise
    public boolean isBirdInLifeList(String birdName) {
        for (Bird bird : this.lifeList) {
            if (bird.getName().equals(birdName)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: called on non-empty LifeList
    // EFFECTS: returns the bird that was the last to be added
    public Bird lastBird() {
        return this.lifeList.getLast();
    }

    // EFFECTS: returns the bird object with name birdName, else returns null
    public Bird getBird(String birdName) {
        for (Bird bird : this.lifeList) {
            if (bird.getName().equals(birdName)) {
                return bird;
            }
        }
        return null;
    }

    // EFFECTS: returns true if bird is an element of the list, false otherwise
    public boolean contains(Bird bird) {
        return this.lifeList.contains(bird);
    }

    // EFFECTS: returns all birds in the specified time period
    //          0 in any of the parameters means wants only the birds from the greater specified period
    //          ie. 2001, 0, 0 means birds in 2001 while 2004 11 0 means birds in November 2004
    public LifeList birdsInTimePeriod(int year, int month, int day) {
        LifeList finalList = new LifeList();
        if (year == 0) {
            finalList = this;
        } else {
            LifeList listInYear = this.birdsInYear(year);
            if (month == 0) {
                finalList = listInYear;
            } else {
                LifeList listInMonth = listInYear.birdsInMonth(month);
                if (day == 0) {
                    finalList =  listInMonth;
                } else {
                    LifeList listInDay = listInMonth.birdsInDay(day);
                    finalList =  listInDay;
                }
            }
        }
        return finalList;
    }

    // EFFECTS: returns a LifeList of all birds with sightings on day
    public LifeList birdsInDay(int day) {
        LifeList listInDay = new LifeList();
        for (Bird bird : this.lifeList) {
            if (bird.getLastSighting().getDay() == day) {
                listInDay.addBird(bird);
            }
        }
        return listInDay;
    }

    // EFFECTS: returns a LifeList of all birds with sightings in month
    public LifeList birdsInMonth(int month) {
        LifeList listInMonth = new LifeList();
        for (Bird bird : this.lifeList) {
            if (bird.getLastSighting().getMonth() == month) {
                listInMonth.addBird(bird);
            }
        }
        return listInMonth;
    }

    // EFFECTS: returns a LifeList of all birds with sightings in year
    public LifeList birdsInYear(int year) {
        LifeList listInYear = new LifeList();
        for (Bird bird : this.lifeList) {
            if (bird.getLastSighting().getYear() == year) {
                listInYear.addBird(bird);
            }
        }
        return listInYear;
    }

    // EFFECTS: returns true if there are no elements in the lifeList
    public boolean isEmpty() {
        return this.lifeList.isEmpty();
    }

    // EFFECTS: returns a string of all the Bird names from this
    public String namesToString() {
        String toPrint = "";
        for (Bird bird : this.lifeList) {
            toPrint = toPrint.concat(bird.getName() + "\n");
        }
        return toPrint;
    }

    // EFFECTS: returns a LifeList of all birds with sightings in location
    public LifeList birdsInLocation(String location) {
        LifeList listInLocation = new LifeList();
        for (Bird bird : this.lifeList) {
            if (bird.getLastSighting().getLocation().equals(location)) {
                listInLocation.addBird(bird);
            }
        }
        return listInLocation;
    }

    // EFFECTS: returns a new JSONObject of lifeList
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("birds", birdsToJson());
        return json;
    }

    // EFFECTS: returns birds in this lifelist as a JSON array
    private JSONArray birdsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Bird bird: lifeList) {
            jsonArray.put(bird.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns an unmodifiable list of all birds
    public List<Bird> getBirds() {
        return Collections.unmodifiableList(lifeList);
    }
}
