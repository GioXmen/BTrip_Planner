package com.btplanner.btripex.ui.event;

import com.btplanner.btripex.data.model.Event;
import java.util.List;

/**
 * Class exposing events to the UI.
 */
public class EventUserView {
    private List<Event> events;
    private Event event;

    public EventUserView(List<Event> events) {
        this.events = events;
    }

    EventUserView(Event event, boolean addOrRemove) {
        if (addOrRemove){
            this.event = event;
    //        this.events.add(event);
        } else this.events.remove(event);
    }

    public List<Event> getTrips(){
        return events;
    }

    public Event getAddedEvent(){
        return event;
    }
}

