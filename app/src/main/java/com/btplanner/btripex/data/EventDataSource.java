package com.btplanner.btripex.data;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDataSource {

    void addEvent(String eventId, String eventName, EventType eventType, String eventDescription, String eventLocation,
                  String startDate, String endDate, String eventTime, String eventExpense,
                  String expenseReceipt, Trip trip, EventRepository eventRepository) {

        Event newEvent = new Event(eventId, eventName, eventType, eventDescription, eventLocation, startDate, endDate,
                eventTime, eventExpense, expenseReceipt, trip);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Event> call = service.addEvent(newEvent);
        call.enqueue(new Callback<Event>() {
            @Override
            @SuppressWarnings("unchecked")
            public void onResponse(@NonNull Call<Event> call, @NonNull Response<Event> response) {
                Event newEvent = response.body();
                Result<Event> result = new Result.Success<>(newEvent);
                if (newEvent == null) {
                    result = new Result.Error(new IOException(String.valueOf(R.string.event_add_failed)));
                }
                eventRepository.addEvent(result);
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onFailure(@NonNull Call<Event> call, @NonNull Throwable t) {
                Result<Event> result = new Result.Error(new IOException(String.valueOf(R.string.event_call_failed), t));
                eventRepository.addEvent(result);
            }
        });
    }
}
