package com.btplanner.btripex.data.covidmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CovidSummary {

    @SerializedName("Global")
    private CovidGlobal Global;

    @SerializedName("Countries")
    private List<CovidCountries> Countries = new ArrayList<CovidCountries>();

    @SerializedName("Date")
    private Date Date;

    CovidSummary(){
        Countries = new ArrayList<CovidCountries>();
    }

    public List<CovidCountries> getCountries() {
        return Countries;
    }

    public void setCountries(List<CovidCountries> countries) {
        Countries = countries;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public CovidGlobal getGlobal() {
        return Global;
    }

    public void setGlobal(CovidGlobal global) {
        Global = global;
    }
}
