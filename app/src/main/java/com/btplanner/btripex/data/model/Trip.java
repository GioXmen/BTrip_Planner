package com.btplanner.btripex.data.model;

import com.google.gson.annotations.SerializedName;

public class Trip {

    @SerializedName("trip_id")
    private String tripId;
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnail")
    private String thumbnail;

    public Trip(String tripId, String title, String thumbnail) {
        this.tripId = tripId;
        this.title = title;
        this.thumbnail = thumbnail;
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
}
