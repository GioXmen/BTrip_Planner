package com.btplanner.btripex.data.covidmodel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CovidCountries {

    @SerializedName("Country")
    private String Country;

    @SerializedName("CountryCode")
    private String CountryCode;

    @SerializedName("Slug")
    private String Slug;

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

    @SerializedName("Date")
    private Date Date;

    @SerializedName("Country")
    public String getCountry() {
        return Country;
    }

    public CovidCountries() {
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public int getNewConfirmed() {
        return NewConfirmed;
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

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }
}
