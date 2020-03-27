package com.btplanner.btripex.ui.event;

import androidx.annotation.Nullable;

/**
 * Data entry/retrieval result : success (event details) or error message.
 */
public class EventResult {
    @Nullable
    private EventUserView success;
    @Nullable
    private Integer error;

    public EventResult(@Nullable Integer error) {
        this.error = error;
    }

    public EventResult(@Nullable EventUserView success) {
        this.success = success;
    }

    @Nullable
    public EventUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
