package model;

import java.util.ArrayList;

// Sighting represents a date (day, month, year) and a location to help with recording Birds
public class Sighting {
    private final int day;
    private final int month;
    private final int year;
    private final String location;

    //REQUIRES: day between 00-31, month between 00-12, year between 1900-2021
    //EFFECTS: creates a new Sighting object
    public Sighting(int month, int day, int year, String location) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.location = location;
    }

    // EFFECTS: returns a new Sighting object that represents the value of given String
    public static Sighting valueOf(String sighting) {
        int month = Integer.parseInt(sighting.substring(0, 2));
        int day = Integer.parseInt(sighting.substring(3, 5));
        int year = Integer.parseInt(sighting.substring(6, 10));
        String loc = sighting.substring(12);
        return new Sighting(month, day, year, loc);
    }

    //EFFECTS: returns year
    public int getYear() {
        return this.year;
    }

    //EFFECTS: returns day
    public int getDay() {
        return this.day;
    }

    //EFFECTS: returns month
    public int getMonth() {
        return this.month;
    }

    //EFFECTS: returns location
    public String getLocation() {
        return this.location;
    }

    //EFFECTS: represents Sighting in String form
    public String toString() {
        String dayString = String.valueOf(this.day);
        if (dayString.length() == 1) {
            dayString = "0" + dayString;
        }
        String monthString = String.valueOf(this.month);
        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }
        return monthString + "/" + dayString + "/" + year + ", " + this.location;
    }

    // EFFECTS: returns true if the given ArrayList is a valid date of form [month, day, year], false otherwise
    public static boolean properDate(int month, int day, int year) {
        boolean proper;
        if (year > 2021 || year <= 0) {
            proper = false;
        } else if (month <= 0 || month > 12) {
            proper = false;
        } else if (day > 31 || day <= 0) {
            proper = false;
        } else if (day == 31 && (month % 2 == 0)) {
            proper = false;
        } else if (day == 30 && month == 2) {
            proper = false;
        } else if (day == 29 && month == 2 && (year % 4 != 0)) {
            proper = false;
        } else {
            proper = true;
        }
        return proper;
    }
}
