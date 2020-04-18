package com.btplanner.btripex.ui.event.report.utils;

import com.btplanner.btripex.ui.event.report.ExpenseReportUserView;

import androidx.annotation.Nullable;

/**
 * Data entry/retrieval result : success (event details) or error message.
 */
public class ExpenseReportResult {
    @Nullable
    private ExpenseReportUserView success;
    @Nullable
    private Integer error;

    public ExpenseReportResult(@Nullable Integer error) {
        this.error = error;
    }

    public ExpenseReportResult(@Nullable ExpenseReportUserView success) {
        this.success = success;
    }

    @Nullable
    public ExpenseReportUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}

