package com.btplanner.btripex.ui.event;

import androidx.annotation.Nullable;

/**
 * Data removal result : success (true / false) or error message.
 */
public class RemoveEventResult {
    @Nullable
    private Boolean success;
    @Nullable
    private Integer error;

    RemoveEventResult(@Nullable Integer error) {
        this.error = error;
    }

    RemoveEventResult(@Nullable Boolean success) {
        this.success = success;
    }

    @Nullable
    public Boolean getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
