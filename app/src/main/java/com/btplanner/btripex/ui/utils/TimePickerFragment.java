package com.btplanner.btripex.ui.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

    private int hour, minute;
    private TimePickerDialog.OnTimeSetListener mListener;

    public TimePickerFragment() {
    }

    public void setOnTimeSelectedListener(TimePickerDialog.OnTimeSetListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(requireContext(), mListener, hour, minute, true);
    }
}
