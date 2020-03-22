package com.btplanner.btripex.ui.adapter;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.btplanner.btripex.ui.main.addtrip.AddTrip;

import java.util.Calendar;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {

    private int year, month, day;
    DatePickerDialog.OnDateSetListener mListener;

    public DatePickerFragment() {
        // Default constructor. Required
    }

    public void setOnDateSelectedListener(DatePickerDialog.OnDateSetListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(Objects.requireNonNull(getContext()), mListener, year, month, day);
    }
}
