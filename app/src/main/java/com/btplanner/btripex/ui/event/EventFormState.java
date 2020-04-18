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
    private Integer datePeriodError;
    @Nullable
    private Integer startDateTimeError;
    private boolean isDataValid;

    EventFormState(@Nullable Integer eventNameError, @Nullable Integer eventTypeError,
                   @Nullable Integer datePeriodError, @Nullable Integer startDateTimeError) {
        this.eventNameError = eventNameError;
        this.eventTypeError = eventTypeError;
        this.datePeriodError = datePeriodError;
        this.startDateTimeError = startDateTimeError;
        this.isDataValid = false;
    }

    EventFormState(boolean isDataValid) {
        this.eventNameError = null;
        this.eventTypeError = null;
        this.datePeriodError = null;
        this.startDateTimeError = null;
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
    public Integer getDatePeriodError() {
        return datePeriodError;
    }

    @Nullable
    public Integer getStartDateTimeError() {
        return startDateTimeError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
