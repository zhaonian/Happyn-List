package com.searchsystem.mycommunity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luan on 3/11/17.
 */

public class Event implements Serializable { // better use Parcelable. change it later

    private String name;
    private String location;
    private String startTime;
    private String endTime;
    private String category;
    private String description;

    public Event() {
    }

    public Event(String name, String location, String startTime, String endTime, String category, String description) {
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Event> getEventsFromJson(JSONObject eventsJson) {
        ArrayList<Event> eventArray = new ArrayList<>();

        try {
            JSONArray events = (JSONArray) eventsJson.get("events");
            for (int i = 0; i < events.length(); i++) {
                JSONObject eventInfo = (JSONObject) events.get(i);
                Event event = new Event();
                event.setName(eventInfo.getString("name"));
                event.setStartTime(eventInfo.getString("startTime"));
                event.setEndTime(eventInfo.getString("endTime"));
                event.setCategory(eventInfo.getString("category"));
                event.setDescription(eventInfo.getString("description"));

                String locationStr = "";
                if (eventInfo.has("location")) {
                    JSONArray location = (JSONArray) eventInfo.get("location");
                    for (int j = 0; j < location.length(); j++) {
                        JSONObject locationInfo = (JSONObject) location.get(j);
                        locationStr += locationInfo.getString("street") + ", "
                                + locationInfo.getString("city") + ", "
                                + locationInfo.getString("state") + " "
                                + locationInfo.getString("zip");
                    }
                }
                event.setLocation(locationStr);
                eventArray.add(event);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventArray;
    }

    public Event getFBeventByName(ArrayList<Event> EventArray, String name) {
        for (Event event : EventArray) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        String temp = "";
        temp += "Name: " + this.name +
                "Location: " + this.location +
                "Start: " + this.startTime +
                "End: " + this.endTime +
                "Category: " + this.category +
                "Description: " + this.description;
        return temp;
    }
}
