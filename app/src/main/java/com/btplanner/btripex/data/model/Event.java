package com.btplanner.btripex.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Event class that stores, retrieves and serializes JSON event data retrieved from Event Repository.
 */
public class Event implements Parcelable {

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @SerializedName("id")
    private String eventId;
    @SerializedName("name")
    private String eventName;
    @SerializedName("type")
    private EventType eventType;
    @SerializedName("description")
    private String eventDescription;
    @SerializedName("location")
    private String eventLocation;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("eventTime")
    private String eventTime;
    @SerializedName("expense")
    private String eventExpense;
    @SerializedName("expenseReceipt1")
    private String expenseReceipt1;
    @SerializedName("expenseReceipt2")
    private String expenseReceipt2;
    @SerializedName("expenseReceipt3")
    private String expenseReceipt3;
    @SerializedName("trip")
    private Trip trip;

    public Event(String eventId, String eventName, EventType eventType,
                 String eventDescription, String eventLocation, String startDate, String endDate,
                 String eventTime, String eventExpense, String expenseReceipt1, String expenseReceipt2,
                 String expenseReceipt3, Trip trip) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.eventExpense = eventExpense;
        this.expenseReceipt1 = expenseReceipt1;
        this.expenseReceipt2 = expenseReceipt2;
        this.expenseReceipt3 = expenseReceipt3;
        this.trip = trip;
    }

    public Event(String eventName, EventType eventType, String eventDescription, String eventLocation,
                 String startDate, String endDate, String eventTime, String eventExpense,
                 String expenseReceipt1, String expenseReceipt2, String expenseReceipt3, Trip trip) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.eventExpense = eventExpense;
        this.expenseReceipt1 = expenseReceipt1;
        this.expenseReceipt2 = expenseReceipt2;
        this.expenseReceipt3 = expenseReceipt3;
        this.trip = trip;
    }

    public Event(String eventName, EventType eventType, String eventDescription, String eventLocation,
                 String startDate, String endDate, String eventTime, String eventExpense, String expenseReceipt1,
                 String expenseReceipt2, String expenseReceipt3) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.eventExpense = eventExpense;
        this.expenseReceipt1 = expenseReceipt1;
        this.expenseReceipt2 = expenseReceipt2;
        this.expenseReceipt3 = expenseReceipt3;
    }

    protected Event(Parcel in) {
        this.eventId = in.readString();
        this.eventName = in.readString();
        this.eventDescription = in.readString();
        this.eventLocation = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.eventTime = in.readString();
        this.eventExpense = in.readString();
        this.expenseReceipt1 = in.readString();
        this.expenseReceipt2 = in.readString();
        this.expenseReceipt3 = in.readString();
        int tmpMStatus = in.readInt();
        this.eventType = tmpMStatus == -1 ? null : EventType.values()[tmpMStatus];
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventExpense() {
        return eventExpense;
    }

    public void setEventExpense(String eventExpense) {
        this.eventExpense = eventExpense;
    }

    public String getExpenseReceipt1() {
        return expenseReceipt1;
    }

    public void setExpenseReceipt1(String expenseReceipt1) {
        this.expenseReceipt1 = expenseReceipt1;
    }

    public String getExpenseReceipt2() {
        return expenseReceipt2;
    }

    public void setExpenseReceipt2(String expenseReceipt2) {
        this.expenseReceipt2 = expenseReceipt2;
    }
    public String getExpenseReceipt3() {
        return expenseReceipt3;
    }

    public void setExpenseReceipt3(String expenseReceipt3) {
        this.expenseReceipt3 = expenseReceipt3;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventId);
        dest.writeString(this.eventName);
        dest.writeString(this.eventDescription);
        dest.writeString(this.eventLocation);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.eventTime);
        dest.writeString(this.eventExpense);
        dest.writeString(this.expenseReceipt1);
        dest.writeString(this.expenseReceipt2);
        dest.writeString(this.expenseReceipt3);
        dest.writeInt(this.eventType == null ? -1 : this.eventType.ordinal());
    }
}
