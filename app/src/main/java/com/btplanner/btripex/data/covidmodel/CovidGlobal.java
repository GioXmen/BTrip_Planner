package com.btplanner.btripex.data.covidmodel;

import com.google.gson.annotations.SerializedName;

public class CovidGlobal {

    @SerializedName("NewConfirmed")
    private int NewConfirmed;

    @SerializedName("TotalConfirmed")
    private int TotalConfirmed;

    @SerializedName("NewDeaths")
    private int NewDeaths;

    @SerializedName("TotalDeaths")
    private int TotalDeaths;

    @SerializedName("NewRecovered")
    private int NewRecovered;

    @SerializedName("TotalRecovered")
    private int TotalRecovered;

    @SerializedName("NewConfirmed")
    public int getNewConfirmed() {
        return NewConfirmed;
    }

    public CovidGlobal() {
    }

    public void setNewConfirmed(int newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public int getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public int getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(int newDeaths) {
        NewDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public int getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(int newRecovered) {
        NewRecovered = newRecovered;
    }

    public int getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        TotalRecovered = totalRecovered;
    }
}
