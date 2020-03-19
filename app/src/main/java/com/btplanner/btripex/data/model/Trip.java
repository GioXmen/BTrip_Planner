package com.btplanner.btripex.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
    private Date startDate;
    @SerializedName("endDate")
    private Date endDate;

    public Trip(String tripId, String title, String thumbnail, String tripDestination, String tripDescription, Date startDate, Date endDate) {
        this.tripId = tripId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.tripDescription = tripDescription;
        this.tripDestination = tripDestination;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }
}
