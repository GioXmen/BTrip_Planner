package com.btplanner.btripex.ui.event;

import androidx.annotation.Nullable;

/**
 * Data validation state of the add event form.
 */
public class EventFormState {
    @Nullable
    private Integer eventNameError;
    @Nullable
    private Integer eventTypeError;
    @Nullable
    private Integer dateError;
    private boolean isDataValid;

    EventFormState(@Nullable Integer eventNameError, @Nullable Integer eventTypeError,
                   @Nullable Integer dateError) {
        this.eventNameError = eventNameError;
        this.eventTypeError = eventTypeError;
        this.dateError = dateError;
        this.isDataValid = false;
    }

    EventFormState(boolean isDataValid) {
        this.eventNameError = null;
        this.eventTypeError = null;
        this.dateError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getEventNameError() {
        return eventNameError;
    }

    @Nullable
    public Integer getEventTypeError() {
        return eventTypeError;
    }

    @Nullable
    public Integer getDateError() {
        return dateError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
