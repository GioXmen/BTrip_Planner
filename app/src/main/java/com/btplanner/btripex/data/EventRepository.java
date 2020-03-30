package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.RegisteredUser;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.ui.event.EventViewModel;
import com.btplanner.btripex.ui.main.TripViewModel;
import com.btplanner.btripex.ui.register.RegisterViewModel;

import java.util.Date;
import java.util.List;


/**
 * Class that requests event information from the remote data source and
 * maintains an in-memory cache of event information.
 */
public class EventRepository {

    private static volatile EventRepository instance;

    private EventDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private List<Event> events = null;
    private EventViewModel eventViewModel = null;

    // private constructor : singleton access
    private EventRepository(EventDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static EventRepository getInstance(EventDataSource dataSource) {
        if (instance == null) {
            instance = new EventRepository(dataSource);
        }
        return instance;
    }

    public boolean areEventsLoaded() {
        return events != null;
    }

    private void setEvents(List<Event> events) {
        this.events = events;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    private void addEvent(Event event) {
        this.events.add(event);
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    private void setEventViewModel (EventViewModel eventViewModel){
        this.eventViewModel = eventViewModel;
    }

    public void addEvent(String eventId, String eventName, EventType eventType, String eventDescription, String eventLocation, String startDate, String endDate,
                         String eventTime, String eventExpense, String expenseReceipt, EventViewModel eventViewModel, Trip trip) {
        setEventViewModel(eventViewModel);
        dataSource.addEvent(eventId, eventName, eventType, eventDescription, eventLocation, startDate, endDate,
                eventTime, eventExpense, expenseReceipt, trip,  instance);
    }

    public void addEvent( Result<Event> result) {
        if (result instanceof Result.Success) {
            if(events != null) {
                addEvent(((Result.Success<Event>) result).getData());
            }
        }
        eventViewModel.addEvent(result);
    }
}
