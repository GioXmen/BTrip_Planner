package com.btplanner.btripex.ui.event.eventimeline;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.ui.event.EventFormState;
import com.btplanner.btripex.ui.event.EventResult;
import com.btplanner.btripex.ui.event.EventUserView;
import com.btplanner.btripex.ui.event.EventViewModel;
import com.btplanner.btripex.ui.event.EventViewModelFactory;
import com.btplanner.btripex.ui.event.home.HomeFragment;
import com.btplanner.btripex.ui.utils.DatePickerFragment;
import com.btplanner.btripex.ui.login.LoginActivity;
import com.btplanner.btripex.ui.utils.ImagePicker;
import com.btplanner.btripex.ui.utils.TimePickerFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEvent extends AppCompatActivity implements ImagePicker.ImageAttachmentListener {

    public static String tripId;
    public static String tripTitle;
    public EditText eventStartEditText;
    public EditText eventEndEditText;
    public EditText eventTimeEditText;
    public EditText eventNameEditText;
    public EditText eventLocationEditText;
    public EditText eventDescriptionEditText;
    public EditText eventExpenseEditText;
    public Spinner spinner;
    ImageView iv_attachment;
    ImagePicker imagePicker;
    private EventViewModel eventViewModel;
    private Bitmap bitmap;
    private String image = null;
    private Event currentEvent;
    private String currentEventId = null;
    private Trip newTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventViewModel = ViewModelProviders.of(this, new EventViewModelFactory())
                .get(EventViewModel.class);

        eventNameEditText = findViewById(R.id.event_name);
        eventLocationEditText = findViewById(R.id.event_location);
        eventDescriptionEditText = findViewById(R.id.event_description);
        eventExpenseEditText = findViewById(R.id.event_expense);
        eventStartEditText = findViewById(R.id.start_date);
        eventEndEditText = findViewById(R.id.end_date);
        eventTimeEditText = findViewById(R.id.event_time);

        spinner = findViewById(R.id.event_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.event_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final Button addEventButton = findViewById(R.id.add_event);
        final ProgressBar loadingProgressBar = findViewById(R.id.progressbarAddEvent);
        loadingProgressBar.setVisibility(View.GONE);

        imagePicker = new ImagePicker(this);
        iv_attachment = findViewById(R.id.event_expense_imageView);

        if (getIntent().hasExtra("eventId")) {
            addEventButton.setEnabled(true);
            setFields();
        }

        if (getIntent().hasExtra("title")) {
            tripId = getIntent().getStringExtra("id");
            tripTitle = getIntent().getStringExtra("title");
        }

        newTrip = new Trip(tripId, tripTitle);
        eventViewModel.getEventFormState().observe(this, new Observer<EventFormState>() {
            @Override
            public void onChanged(@Nullable EventFormState eventFormState) {
                if (eventFormState == null) {
                    return;
                }
                addEventButton.setEnabled(eventFormState.isDataValid());
                if (eventFormState.getEventNameError() != null) {
                    eventNameEditText.setError(getString(eventFormState.getEventNameError()));
                }
                if (eventFormState.getEventTypeError() != null) {
                    ((TextView) spinner.getSelectedView()).setError(getString(eventFormState.getEventTypeError()));
                }
                if (eventFormState.getDateError() != null) {
                    eventStartEditText.setError(getString(eventFormState.getDateError()));
                    eventEndEditText.setError(getString(eventFormState.getDateError()));
                }
            }
        });

        eventViewModel.getEventResult().observe(this, new Observer<EventResult>() {
            @Override
            public void onChanged(@Nullable EventResult eventResult) {
                if (eventResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (eventResult.getError() != null) {
                    showAddEventFailed(eventResult.getError());
                }
                if (eventResult.getSuccess() != null) {
                    updateUiWithEvent(eventResult.getSuccess());
                    finish();
                }
                setResult(Activity.RESULT_OK);
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (bitmap != null) {
                    bitmapToBase64();
                }
                eventViewModel.eventDataChanged(eventNameEditText.getText().toString(), spinner.getSelectedItem().toString(),
                        eventDescriptionEditText.getText().toString(), eventLocationEditText.getText().toString(),
                        eventStartEditText.getText().toString(), eventEndEditText.getText().toString(),
                        eventTimeEditText.getText().toString(), eventExpenseEditText.getText().toString(), image);
            }
        };

        eventNameEditText.addTextChangedListener(afterTextChangedListener);
        eventDescriptionEditText.addTextChangedListener(afterTextChangedListener);
        eventLocationEditText.addTextChangedListener(afterTextChangedListener);
        eventStartEditText.addTextChangedListener(afterTextChangedListener);
        eventEndEditText.addTextChangedListener(afterTextChangedListener);
        eventTimeEditText.addTextChangedListener(afterTextChangedListener);
        eventExpenseEditText.addTextChangedListener(afterTextChangedListener);
        eventExpenseEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (bitmap != null) {
                    bitmapToBase64();
                }
                if (currentEvent != null) {
                    currentEventId = currentEvent.getEventId();
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    eventViewModel.addEvent(currentEventId, eventNameEditText.getText().toString(),
                            EventType.valueOf(spinner.getSelectedItem().toString().toUpperCase()), eventDescriptionEditText.getText().toString(),
                            eventLocationEditText.getText().toString(), eventStartEditText.getText().toString(), eventEndEditText.getText().toString(),
                            eventTimeEditText.getText().toString(), eventExpenseEditText.getText().toString(), image, eventViewModel, newTrip);
                }
                return false;
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    bitmapToBase64();
                }
                if (currentEvent != null) {
                    currentEventId = currentEvent.getEventId();
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                eventViewModel.addEvent(currentEventId, eventNameEditText.getText().toString(),
                        EventType.valueOf(spinner.getSelectedItem().toString().toUpperCase()), eventDescriptionEditText.getText().toString(),
                        eventLocationEditText.getText().toString(), eventStartEditText.getText().toString(), eventEndEditText.getText().toString(),
                        eventTimeEditText.getText().toString(), eventExpenseEditText.getText().toString(), image, eventViewModel, newTrip);
            }
        });

        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                imagePicker.imagepicker(1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imagePicker.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap = file;
        iv_attachment.setImageBitmap(file);

        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imagePicker.createImage(file, filename, path, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Intent it = new Intent(getApplicationContext(), LoginActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUiWithEvent(EventUserView model) {
        String addedEvent = getString(R.string.event_added) + " " + model.getAddedEvent().getEventName();
        Toast.makeText(getApplicationContext(), addedEvent, Toast.LENGTH_LONG).show();
        finish();
    }

    private void showAddEventFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void showDatePickerDialog(View v, String date) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDateSelectedListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);

                String formattedTime = sdf.format(cal.getTime());
                if (date.equals("startDate")) {
                    eventStartEditText.setText(formattedTime);
                } else eventEndEditText.setText(formattedTime);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public void showStartDatePickerDialog(View view) {
        showDatePickerDialog(view, "startDate");
    }

    public void showEndDatePickerDialog(View view) {
        showDatePickerDialog(view, "endDate");
    }

    public void showTimePickerDialog(View view) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setOnTimeSelectedListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String myFormat = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                Calendar cal = Calendar.getInstance();
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), hourOfDay, minute);

                String formattedTime = sdf.format(cal.getTime());
                eventTimeEditText.setText(formattedTime);
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void bitmapToBase64() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        image = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void setFields() {
        String eventId = getIntent().getStringExtra("eventId");
        currentEvent = HomeFragment.eventsMap.get(eventId);

        assert currentEvent != null;
        if (currentEvent.getEventName() != null) {
            eventNameEditText.setText(currentEvent.getEventName());
        }
        if (currentEvent.getEventLocation() != null) {
            eventLocationEditText.setText(currentEvent.getEventLocation());
        }
        if (currentEvent.getEventDescription() != null) {
            eventDescriptionEditText.setText(currentEvent.getEventDescription());
        }
        if (currentEvent.getEventExpense() != null) {
            eventExpenseEditText.setText(currentEvent.getEventExpense());
        }
        if (currentEvent.getStartDate() != null) {
            eventStartEditText.setText(currentEvent.getStartDate().substring(0, 10));
        }
        if (currentEvent.getEndDate() != null) {
            eventEndEditText.setText(currentEvent.getEndDate().substring(0, 10));
        }
        if (currentEvent.getEventTime() != null) {
            eventTimeEditText.setText(currentEvent.getEventTime().substring(11, 16));
        }
        if (currentEvent.getEventType() != null) {
            spinner.setSelection(currentEvent.getEventType().ordinal());
        }
        if (currentEvent.getExpenseReceipt() != null) {
            image = currentEvent.getExpenseReceipt();
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            iv_attachment.setImageBitmap(decodedByte);
        }
    }

}


