package com.btplanner.btripex.data.model;

import com.google.gson.annotations.SerializedName;

public class ExpenseReport {
    @SerializedName("encodedPDFReport")
    private String encodedPDFReport;

    public ExpenseReport(String encodedPDFReport) {
        this.encodedPDFReport = encodedPDFReport;
    }

    public String getEncodedPDFReport() {
        return encodedPDFReport;
    }

    public void setEncodedPDFReport(String encodedPDFReport) {
        this.encodedPDFReport = encodedPDFReport;
    }
}
