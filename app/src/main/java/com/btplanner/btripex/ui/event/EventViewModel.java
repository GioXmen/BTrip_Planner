package com.btplanner.btripex.ui.event;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.EventRepository;
import com.btplanner.btripex.data.Result;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventViewModel extends ViewModel {

    private MutableLiveData<EventFormState> eventFormState = new MutableLiveData<>();
    private MutableLiveData<EventResult> eventResult = new MutableLiveData<>();
    private EventRepository eventRepository;

    public EventViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<EventFormState> getEventFormState() {
        return eventFormState;
    }

    public LiveData<EventResult> getEventResult() {
        return eventResult;
    }

    public void addEvent(String eventId, String eventName, EventType eventType, String eventDescription, String eventLocation, String startDate, String endDate,
                         String eventTime, String eventExpense, String expenseReceipt, EventViewModel eventViewModel, Trip trip) {

        String startTimeAndDate = startDate;
        if(eventTime != null) {
            startTimeAndDate = startDate.substring(0, 10) + "T" + eventTime.substring(0, 5) + ":00.000+0000";
        }
        eventRepository.addEvent(eventId, eventName, eventType, eventDescription, eventLocation, startTimeAndDate, endDate,
                startTimeAndDate, eventExpense, expenseReceipt, eventViewModel, trip);
    }

    public void addEvent(Result<Event> result) {
        if (result instanceof Result.Success) {
            Event data = ((Result.Success<Event>) result).getData();
            eventResult.setValue(new EventResult(new EventUserView(data, true)));
        } else {
            eventResult.setValue(new EventResult(R.string.event_add_failed));
        }
    }

    public void eventDataChanged(String eventName, String eventType, String eventDescription, String eventLocation, String startDate, String endDate,
                                 String eventTime, String eventExpense, String expenseReceipt) {

        if (!isNameValid(eventName)) {
            eventFormState.setValue(new EventFormState(R.string.invalid_title, null, null));
        } else if (!isTypeValid(eventType)) {
            eventFormState.setValue(new EventFormState(null, R.string.invalid_type, null));
        }
        // TODO: ADD DATE VALIDATION
        else {
            eventFormState.setValue(new EventFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isNameValid(String name) {
        if (name == null) {
            return false;
        } else {
            return !name.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isTypeValid(String type) {
        return type != null;
    }

}
