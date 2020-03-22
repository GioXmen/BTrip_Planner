package com.btplanner.btripex.ui.main.addtrip;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.ui.utils.DatePickerFragment;
import com.btplanner.btripex.ui.login.LoginActivity;
import com.btplanner.btripex.ui.main.AddTripResult;
import com.btplanner.btripex.ui.main.MainActivity;
import com.btplanner.btripex.ui.main.TripFormState;
import com.btplanner.btripex.ui.main.TripViewModel;
import com.btplanner.btripex.ui.main.TripViewModelFactory;
import com.btplanner.btripex.ui.main.TripsUserView;
import com.btplanner.btripex.ui.utils.ImagePicker;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTrip extends AppCompatActivity implements ImagePicker.ImageAttachmentListener{

    private TripViewModel tripViewModel;
    public EditText tripStartEditText;
    public EditText tripEndEditText;

    ImageView iv_attachment;
    //For Image Attachment

    private Bitmap bitmap;
    private String file_name;
    private String image = null;

    ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tripViewModel = ViewModelProviders.of(this, new TripViewModelFactory())
                .get(TripViewModel.class);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        final EditText tripNameEditText = findViewById(R.id.trip_name);
        final EditText tripDestinationEditText = findViewById(R.id.destination);
        final EditText tripDescriptionEditText = findViewById(R.id.description);
        tripStartEditText = findViewById(R.id.start_date);
        tripEndEditText = findViewById(R.id.end_date);

        tripNameEditText.setTypeface(custom_font);
        tripDestinationEditText.setTypeface(custom_font);
        tripStartEditText.setTypeface(custom_font);
        tripEndEditText.setTypeface(custom_font);
        tripDescriptionEditText.setTypeface(custom_font);

        final Button addTripButton = findViewById(R.id.add_trip);
        final ProgressBar loadingProgressBar = findViewById(R.id.progressbarAddTrip);
        loadingProgressBar.setVisibility(View.GONE);

        LoggedInUser user = new LoggedInUser(MainActivity.username, MainActivity.password);
        imagePicker =new ImagePicker(this);
        iv_attachment=(ImageView)findViewById(R.id.imageView);

        tripViewModel.getTripFormState().observe(this, new Observer<TripFormState>() {
            @Override
            public void onChanged(@Nullable TripFormState tripFormState) {
                if (tripFormState == null) {
                    return;
                }
                addTripButton.setEnabled(tripFormState.isDataValid());
                if (tripFormState.getTripNameError() != null) {
                    tripNameEditText.setError(getString(tripFormState.getTripNameError()));
                }
                if (tripFormState.getTripDescriptionError() != null) {
                    tripDescriptionEditText.setError(getString(tripFormState.getTripDescriptionError()));
                }
                if (tripFormState.getTripDestinationError() != null) {
                    tripDestinationEditText.setError(getString(tripFormState.getTripDestinationError()));
                }
                if (tripFormState.getDateError() != null) {
                    tripStartEditText.setError(getString(tripFormState.getDateError()));
                    tripEndEditText.setError(getString(tripFormState.getDateError()));
                }
            }
        });

        tripViewModel.getAddTripResult().observe(this, new Observer<AddTripResult>() {
            @Override
            public void onChanged(@Nullable AddTripResult addTripResult) {
                if (addTripResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (addTripResult.getError() != null) {
                    showAddTripFailed(addTripResult.getError());
                }
                if (addTripResult.getSuccess() != null) {
                    updateUiWithTrip(addTripResult.getSuccess());

                    //Complete and destroy login activity once successful
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
                if(bitmap != null){bitmapToBase64();}
                tripViewModel.addTripDataChanged(tripNameEditText.getText().toString(), image, tripDestinationEditText.getText().toString(),
                       tripDescriptionEditText.getText().toString(),  tripStartEditText.getText().toString(),  tripEndEditText.getText().toString());
            }
        };

        tripNameEditText.addTextChangedListener(afterTextChangedListener);
        tripDestinationEditText.addTextChangedListener(afterTextChangedListener);
        tripStartEditText.addTextChangedListener(afterTextChangedListener);
        tripEndEditText.addTextChangedListener(afterTextChangedListener);
        tripDescriptionEditText.addTextChangedListener(afterTextChangedListener);
        tripDescriptionEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(bitmap != null){bitmapToBase64();}
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                        tripViewModel.addTrip(tripNameEditText.getText().toString(), image,  tripDestinationEditText.getText().toString(),
                                tripDescriptionEditText.getText().toString(), tripStartEditText.getText().toString(),  tripEndEditText.getText().toString(),
                                tripViewModel, user);
                }
                return false;
            }
        });

        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmap != null){bitmapToBase64();}
                loadingProgressBar.setVisibility(View.VISIBLE);
                    tripViewModel.addTrip(tripNameEditText.getText().toString(), image,
                            tripDestinationEditText.getText().toString(), tripDescriptionEditText.getText().toString(),
                            tripStartEditText.getText().toString(),  tripEndEditText.getText().toString(), tripViewModel, user);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imagePicker.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap=file;
        this.file_name=filename;
        iv_attachment.setImageBitmap(file);

        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imagePicker.createImage(file,filename,path,false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent it = new Intent(getApplicationContext(), LoginActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUiWithTrip(TripsUserView model) {
        String addedTrip = getString(R.string.trip_added) + " " + model.getAddedTrip().getTitle();
        Toast.makeText(getApplicationContext(), addedTrip, Toast.LENGTH_LONG).show();

        // TODO : initiate successful logged in experience
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
    }

    private void showAddTripFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void showDatePickerDialog(View v, String date) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDateSelectedListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Do something with the date chosen by the user
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);

                String formattedTime = sdf.format(cal.getTime());
                if(date.equals("startDate")) {
                    tripStartEditText.setText(formattedTime);
                } else tripEndEditText.setText(formattedTime);
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

    private void bitmapToBase64(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        image = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}

