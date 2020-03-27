package com.btplanner.btripex.data.model;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    private String eventId;
    @SerializedName("name")
    private String eventName;
    @SerializedName("type")
    private String eventType;
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
    @SerializedName("expenseReceipt")
    private String expenseReceipt;
    @SerializedName("trip")
    private Trip trip;


    public Event(String eventId, String eventName, String eventType,
                 String eventDescription, String eventLocation, String startDate, String endDate,
                 String eventTime, String eventExpense, String expenseReceipt, Trip trip) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.eventExpense = eventExpense;
        this.expenseReceipt = expenseReceipt;
        this.trip = trip;
    }

    public Event(String eventName, String eventType, String eventDescription, String eventLocation,
                 String startDate, String endDate, String eventTime, String eventExpense,
                 String expenseReceipt, Trip trip) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.eventExpense = eventExpense;
        this.expenseReceipt = expenseReceipt;
        this.trip = trip;
    }

    public Event(String eventName, String eventType, String eventDescription, String eventLocation,
                 String startDate, String endDate, String eventTime, String eventExpense, String expenseReceipt) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventTime = eventTime;
        this.eventExpense = eventExpense;
        this.expenseReceipt = expenseReceipt;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
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

    public String getExpenseReceipt() {
        return expenseReceipt;
    }

    public void setExpenseReceipt(String expenseReceipt) {
        this.expenseReceipt = expenseReceipt;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
