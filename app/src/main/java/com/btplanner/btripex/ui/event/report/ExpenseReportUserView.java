package com.btplanner.btripex.ui.event.report;

import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.ExpenseReport;

import java.util.List;

/**
 * Class exposing events to the UI.
 */
public class ExpenseReportUserView {
    private ExpenseReport generatedReport;

    public ExpenseReportUserView(ExpenseReport generatedReport) {
        this.generatedReport = generatedReport;
    }

    public ExpenseReport getGeneratedReport() {
        return generatedReport;
    }

    public void setGeneratedReport(ExpenseReport generatedReport) {
        this.generatedReport = generatedReport;
    }
}
