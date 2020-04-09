package com.btplanner.btripex.ui.event;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.EventRepository;
import com.btplanner.btripex.data.Result;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.data.model.Trip;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventViewModel extends ViewModel {

    private MutableLiveData<EventFormState> eventFormState = new MutableLiveData<>();
    private MutableLiveData<EventResult> eventResult = new MutableLiveData<>();
    private EventRepository eventRepository;

    EventViewModel(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public LiveData<EventFormState> getEventFormState() {
        return eventFormState;
    }

    public LiveData<EventResult> getEventResult() {
        return eventResult;
    }

    public void addEvent(String eventId, String eventName, EventType eventType, String eventDescription, String eventLocation,
                         String startDate, String endDate, String eventTime, String eventExpense, String expenseReceipt1,
                         String expenseReceipt2, String expenseReceipt3, EventViewModel eventViewModel, Trip trip) {

        String startTimeAndDate = startDate;
        if (eventTime != null) {
            startTimeAndDate = startDate.substring(0, 10) + "T" + eventTime.substring(0, 5) + ":00.000+0000";
        }
        eventRepository.addEvent(eventId, eventName, eventType, eventDescription, eventLocation, startTimeAndDate, endDate,
                startTimeAndDate, eventExpense, expenseReceipt1, expenseReceipt2, expenseReceipt3, eventViewModel, trip);
    }

    public void addEvent(Result<Event> result) {
        if (result instanceof Result.Success) {
            Event data = ((Result.Success<Event>) result).getData();
            eventResult.setValue(new EventResult(new EventUserView(data, true)));
        } else {
            eventResult.setValue(new EventResult(R.string.event_add_failed));
        }
    }

    public void eventDataChanged(String eventName, String eventType, String eventDescription, String eventLocation,
                                 String startDate, String endDate, String eventTime, String eventExpense) {

        if (isEventFieldEmpty(eventName)) {
            eventFormState.setValue(new EventFormState(R.string.event_name_invalid, null, null, null));
        } else if (isEventFieldEmpty(eventType)) {
            eventFormState.setValue(new EventFormState(null, R.string.invalid_type, null, null));
        } else if (isDatePeriodInValid(startDate, endDate)) {
            eventFormState.setValue(new EventFormState(null, null, R.string.date_period_invalid,null));
        } else if (isStartDateTimeInValid(startDate, eventTime)) {
            eventFormState.setValue(new EventFormState(null, null, null,R.string.date_time_invalid));
        }
        else {
            eventFormState.setValue(new EventFormState(true));
        }
    }

    private boolean isEventFieldEmpty(String name) {
        if (name == null) {
            return true;
        } else {
            return name.trim().isEmpty();
        }
    }

    private static boolean isDatePeriodInValid(String start, String end) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(start, dateFormat);
            LocalDate endDate = LocalDate.parse(end, dateFormat);
            //LocalDate current = LocalDate.now();
            return (startDate.isAfter(endDate));
        }catch(DateTimeParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean isStartDateTimeInValid(String startDate, String startTime) {
        if (startDate == null || startTime == null) {
            return true;
        } else return startDate.trim().isEmpty() || startTime.trim().isEmpty();
    }

}
