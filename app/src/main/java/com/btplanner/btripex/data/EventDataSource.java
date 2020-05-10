package com.btplanner.btripex.data;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.data.model.ExpenseReport;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDataSource {

    void addEvent(String eventId, String eventName, EventType eventType, String eventDescription, String eventLocation,
                  String startDate, String endDate, String eventTime, String eventExpense,
                  String expenseReceipt1, String expenseReceipt2, String expenseReceipt3, Trip trip, EventRepository eventRepository) {

        Event newEvent = new Event(eventId, eventName, eventType, eventDescription, eventLocation, startDate, endDate,
                eventTime, eventExpense, expenseReceipt1, expenseReceipt2, expenseReceipt3, trip);

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


    void removeEvent(String eventId, EventRepository eventRepository) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Void> call = service.removeEvent(eventId);
        call.enqueue(new Callback<Void>() {
            @Override
            @SuppressWarnings("unchecked")
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Result<Void> result = new Result.Success<>(response.code());
                if ((response.code()) != 200) {
                    result = new Result.Error(new IOException(String.valueOf(R.string.event_remove_failed)));
                }
                eventRepository.removeEvent(result);
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Result<Void> result = new Result.Error(new IOException(String.valueOf(R.string.event_call_failed), t));
                eventRepository.removeEvent(result);
            }
        });
    }

     void generatePDFReport(String tripId, List<String> excludeEventIds, EventRepository eventRepository) {

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ExpenseReport> call = service.generateReport(tripId, excludeEventIds);
        call.enqueue(new Callback<ExpenseReport>() {
            @Override
            @SuppressWarnings("unchecked")
            public void onResponse(@NonNull Call<ExpenseReport> call, @NonNull Response<ExpenseReport> response) {
                ExpenseReport pdf = response.body();
                Result<ExpenseReport> result = new Result.Success<>(pdf);
                if (pdf == null) {
                    result = new Result.Error(new IOException(String.valueOf(R.string.generate_report_failed)));
                }
                eventRepository.generatePDFReport(result);
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onFailure(@NonNull Call<ExpenseReport> call, @NonNull Throwable t) {
                Result<ExpenseReport> result = new Result.Error(new IOException(String.valueOf(R.string.report_call_failed), t));
                eventRepository.generatePDFReport(result);
            }
        });
    }
}
