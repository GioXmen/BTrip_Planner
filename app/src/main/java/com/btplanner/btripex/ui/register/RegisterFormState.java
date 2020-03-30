package com.btplanner.btripex.ui.register;

import androidx.annotation.Nullable;

/**
 * Data validation state of the register form.
 */
class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer passwordConfirmError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer passwordConfirmError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.passwordConfirmError = passwordConfirmError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.passwordConfirmError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getPasswordConfirmError() {
        return passwordConfirmError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
