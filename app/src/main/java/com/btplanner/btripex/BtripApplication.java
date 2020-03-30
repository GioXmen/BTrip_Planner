package com.btplanner.btripex;

import android.app.Application;
import android.content.Context;

import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.Trip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BtripApplication extends Application {
    private String username;
    private String password;

    Map<String, Event> eventsMap = new HashMap<String, Event>();
    Map<String, Trip> tripsMap = new HashMap<String, Trip>();

    public void setTripsMap(Map<String, Trip> tripsMap) {
        this.tripsMap = tripsMap;
    }

    public void setEventsMap(Map<String, Event> eventsMap) {
        this.eventsMap = eventsMap;
    }

    public Map<String, Event> getEventsMap() {
        return eventsMap;
    }

    public Map<String, Trip> getTripsMap() {
        return tripsMap;
    }

    public Trip getTrip(String id) {
        return tripsMap.get(id);
    }

    public Event getEvent(String id) {
        return eventsMap.get(id);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
