package com.btplanner.btripex.data.model;

import com.google.gson.annotations.SerializedName;

public class Trip {

    @SerializedName("id")
    private String tripId;
    @SerializedName("name")
    private String title;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("destination")
    private String tripDestination;
    @SerializedName("description")
    private String tripDescription;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("user")
    private LoggedInUser user;

    public Trip(String tripId, String title, String thumbnail, String tripDestination, String tripDescription, String startDate, String endDate) {
        this.tripId = tripId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.tripDescription = tripDescription;
        this.tripDestination = tripDestination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Trip(String title, String thumbnail, String tripDestination, String tripDescription, String startDate, String endDate) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.tripDescription = tripDescription;
        this.tripDestination = tripDestination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Trip(String title, String thumbnail, String tripDestination, String tripDescription, String startDate, String endDate, LoggedInUser user) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.tripDescription = tripDescription;
        this.tripDestination = tripDestination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
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

    public LoggedInUser getUser() {
        return user;
    }

    public void setUser(LoggedInUser user) {
        this.user = user;
    }
}
