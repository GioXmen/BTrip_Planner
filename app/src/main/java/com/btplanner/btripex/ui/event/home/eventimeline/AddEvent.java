package com.btplanner.btripex.ui.event.home.eventimeline;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import com.btplanner.btripex.ui.event.home.eventimeline.utils.ImageAdapter;
import com.btplanner.btripex.ui.event.home.HomeFragment;
import com.btplanner.btripex.ui.utils.ClickListener;
import com.btplanner.btripex.ui.utils.DatePickerFragment;
import com.btplanner.btripex.ui.login.LoginActivity;
import com.btplanner.btripex.ui.utils.ImagePicker;
import com.btplanner.btripex.ui.utils.ItemClickSupport;
import com.btplanner.btripex.ui.utils.TimePickerFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

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
    public TextView eventReceiptsLabel;
    public Button addReceiptButton;
    public View receiptDashLine;
    public Spinner spinner;
    ImageView iv_attachment;
    ImagePicker imagePicker;
    private EventViewModel eventViewModel;
    //private Bitmap bitmap;
    private String expenseReceipt1 = null;
    private String expenseReceipt2 = null;
    private String expenseReceipt3 = null;
    private Event currentEvent;
    private String currentEventId = null;
    private Trip newTrip;
    private List<Bitmap> expenses = new ArrayList<Bitmap>();

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
        addReceiptButton = findViewById(R.id.add_image);

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
            getIntent().removeExtra("eventId");
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
                if (eventFormState.getDatePeriodError() != null) {
                    eventStartEditText.setError(getString(eventFormState.getDatePeriodError()));
                    eventEndEditText.setError(getString(eventFormState.getDatePeriodError()));
                }
                if (eventFormState.getStartDateTimeError() != null) {
                    eventStartEditText.setError(getString(eventFormState.getStartDateTimeError()));
                    eventTimeEditText.setError(getString(eventFormState.getStartDateTimeError()));
                }
                if (eventFormState.getDatePeriodError() == null) {
                    eventStartEditText.setError(null);
                    eventEndEditText.setError(null);
                }
                if (eventFormState.getStartDateTimeError() == null) {
                    eventStartEditText.setError(null);
                    eventTimeEditText.setError(null);
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
                eventViewModel.eventDataChanged(eventNameEditText.getText().toString(), spinner.getSelectedItem().toString(),
                        eventDescriptionEditText.getText().toString(), eventLocationEditText.getText().toString(),
                        eventStartEditText.getText().toString(), eventEndEditText.getText().toString(),
                        eventTimeEditText.getText().toString(), eventExpenseEditText.getText().toString());
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
                if (expenses.size() > 0) {
                    expenseReceipt1 = bitmapToBase64(expenses.get(0));
                }
                if (expenses.size() > 1) {
                    expenseReceipt2 = bitmapToBase64(expenses.get(1));
                }
                if (expenses.size() > 2) {
                    expenseReceipt3 = bitmapToBase64(expenses.get(2));
                }
                if (currentEvent != null) {
                    currentEventId = currentEvent.getEventId();
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    eventViewModel.addEvent(currentEventId, eventNameEditText.getText().toString(),
                            EventType.valueOf(spinner.getSelectedItem().toString().toUpperCase()), eventDescriptionEditText.getText().toString(),
                            eventLocationEditText.getText().toString(), eventStartEditText.getText().toString(), eventEndEditText.getText().toString(),
                            eventTimeEditText.getText().toString(), eventExpenseEditText.getText().toString(), expenseReceipt1, expenseReceipt2, expenseReceipt3,
                            eventViewModel, newTrip);
                }
                return false;
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expenses.size() > 0) {
                    expenseReceipt1 = bitmapToBase64(expenses.get(0));
                }
                if (expenses.size() > 1) {
                    expenseReceipt2 = bitmapToBase64(expenses.get(1));
                }
                if (expenses.size() > 2) {
                    expenseReceipt3 = bitmapToBase64(expenses.get(2));
                }
                if (currentEvent != null) {
                    currentEventId = currentEvent.getEventId();
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                eventViewModel.addEvent(currentEventId, eventNameEditText.getText().toString(),
                        EventType.valueOf(spinner.getSelectedItem().toString().toUpperCase()), eventDescriptionEditText.getText().toString(),
                        eventLocationEditText.getText().toString(), eventStartEditText.getText().toString(), eventEndEditText.getText().toString(),
                        eventTimeEditText.getText().toString(), eventExpenseEditText.getText().toString(), expenseReceipt1, expenseReceipt2, expenseReceipt3,
                        eventViewModel, newTrip);
            }
        });

        iv_attachment.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                imagePicker.imagepicker(1);
            }
        });

        addReceiptButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                imagePicker.imagepicker(1);
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
        expenses.add(file);
        generateDataList(expenses);


        //iv_attachment.setImageBitmap(file);
        //String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        //imagePicker.createImage(file, filename, path, false);

    }

    private void generateDataList(List<Bitmap> expenses) {
        RecyclerView recyclerView = findViewById(R.id.imageRecyclerView);
        eventReceiptsLabel = findViewById(R.id.event_receipts);
        receiptDashLine = findViewById(R.id.receipt_dash_line);

        if (expenses.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            addReceiptButton.setVisibility(View.GONE);
            eventReceiptsLabel.setVisibility(View.GONE);
            receiptDashLine.setVisibility(View.GONE);
            iv_attachment.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            addReceiptButton.setVisibility(View.VISIBLE);
            eventReceiptsLabel.setVisibility(View.VISIBLE);
            receiptDashLine.setVisibility(View.VISIBLE);
            iv_attachment.setVisibility(View.GONE);

            if(expenses.size() > 2){
                addReceiptButton.setVisibility(View.GONE);
            }

            ImageAdapter adapter = new ImageAdapter(this, expenses, new ClickListener() {
                @Override public void onPositionClicked(int position) {
                    expenses.remove(position);
                    generateDataList(expenses);
                }

                @Override public void onLongClicked(int position) {
                    // callback performed on LongClick
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ImageAdapter.CustomViewHolder childHolder = (ImageAdapter.CustomViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
                        assert childHolder != null;

                            final Intent intent = new Intent(Intent.ACTION_VIEW)
                                    .setDataAndTypeAndNormalize(getImageUri(getApplicationContext(), expenses.get(position)), "image/*");
                            intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                    }
                }
        );
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

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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
        if (currentEvent.getExpenseReceipt1() != null) {
            String base64 = currentEvent.getExpenseReceipt1();
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            expenses.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        if (currentEvent.getExpenseReceipt2() != null) {
            String base64 = currentEvent.getExpenseReceipt2();
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            expenses.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        if (currentEvent.getExpenseReceipt3() != null) {
            String base64 = currentEvent.getExpenseReceipt3();
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            expenses.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        if(expenses.size() > 0){
            generateDataList(expenses);
        }
    }

}


