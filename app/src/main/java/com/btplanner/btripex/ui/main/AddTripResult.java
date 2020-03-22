package com.btplanner.btripex.ui.main;

import androidx.annotation.Nullable;

/**
 * Data entry/retrieval result : success (trip details) or error message.
 */
public class AddTripResult {
    @Nullable
    private TripsUserView success;
    @Nullable
    private Integer error;

    public AddTripResult(@Nullable Integer error) {
        this.error = error;
    }

    public AddTripResult(@Nullable TripsUserView success) {
        this.success = success;
    }

    @Nullable
    public TripsUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
