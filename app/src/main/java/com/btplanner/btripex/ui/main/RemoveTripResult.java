package com.btplanner.btripex.ui.main;

import androidx.annotation.Nullable;

/**
 * Data removal result : success (true / false) or error message.
 */
public class RemoveTripResult {
    @Nullable
    private Boolean success;
    @Nullable
    private Integer error;

    RemoveTripResult(@Nullable Integer error) {
        this.error = error;
    }

    RemoveTripResult(@Nullable Boolean success) {
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
