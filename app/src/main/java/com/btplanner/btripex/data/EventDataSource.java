package com.btplanner.btripex.data;

import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDataSource {

    public void addEvent(String eventId, String eventName, EventType eventType, String eventDescription, String eventLocation,
                         String startDate, String endDate, String eventTime, String eventExpense,
                         String expenseReceipt, Trip trip, EventRepository eventRepository) {

        Event newEvent = new Event(eventId, eventName, eventType, eventDescription, eventLocation, startDate, endDate,
                eventTime, eventExpense, expenseReceipt, trip);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Event> call = service.addEvent(newEvent);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event newEvent = response.body();
                Result<Event> result = new Result.Success<>(newEvent);
                if (newEvent == null){
                    result = new Result.Error(new IOException("Error adding event"));
                }
                eventRepository.addEvent(result);
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Result<Event> result = new Result.Error(new IOException("Error adding event", t));
                eventRepository.addEvent(result);
            }
        });
    }
}
