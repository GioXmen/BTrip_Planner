package com.btplanner.btripex.ui.main;

import androidx.annotation.Nullable;

/**
 * Data validation state of the add trip form.
 */
public class TripFormState {
    @Nullable
    private Integer tripNameError;
    @Nullable
    private Integer tripDestinationError;
    @Nullable
    private Integer dateError;
    @Nullable
    private Integer tripDescriptionError;
    private boolean isDataValid;

    public TripFormState(@Nullable Integer tripNameError, @Nullable Integer tripDestinationError,
                  @Nullable Integer dateError, @Nullable Integer tripDescriptionError) {
        this.tripNameError = tripNameError;
        this.tripDestinationError = tripDestinationError;
        this.dateError = dateError;
        this.tripDescriptionError = tripDescriptionError;
        this.isDataValid = false;
    }

    public TripFormState(boolean isDataValid) {
        this.tripNameError = null;
        this.tripDestinationError = null;
        this.dateError = null;
        this.tripDescriptionError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getTripNameError() {
        return tripNameError;
    }

    @Nullable
    public Integer getTripDestinationError() {
        return tripDestinationError;
    }

    @Nullable
    public Integer getDateError() {
        return dateError;
    }

    @Nullable
    public Integer getTripDescriptionError() {
        return tripDescriptionError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
