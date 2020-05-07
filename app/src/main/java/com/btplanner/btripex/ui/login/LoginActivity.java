package com.btplanner.btripex.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btplanner.btripex.BtripApplication;
import com.btplanner.btripex.R;
import com.btplanner.btripex.ui.main.MainActivity;
import com.btplanner.btripex.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ImageView devLogo;
    private int devOptionsClickCount;
    private static BtripApplication btripApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText devUrlEditText = findViewById(R.id.devUrl);
        final ImageView devUrlIcon = findViewById(R.id.devUrlIcon);
        usernameEditText.setTypeface(custom_font);
        passwordEditText.setTypeface(custom_font);
        devUrlEditText.setTypeface(custom_font);
        passwordEditText.setError(null);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
        registerButton.setEnabled(true);
        final ProgressBar loadingProgressBar = findViewById(R.id.login_bar);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        devLogo = findViewById(R.id.logo);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null && passwordEditText.getText().length() > 0) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
                if (loginFormState.getDevUrlError() != null && devUrlEditText.getText().length() > 0) {
                    devUrlEditText.setError(getString(loginFormState.getDevUrlError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());

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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), devUrlEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        devUrlEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), loginViewModel);
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!devUrlEditText.getText().toString().matches("")){
                    ((BtripApplication) getApplication()).setServerUrl(devUrlEditText.getText().toString());
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), loginViewModel);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!devUrlEditText.getText().toString().matches("")){
                    ((BtripApplication) getApplication()).setServerUrl(devUrlEditText.getText().toString());
                }
                Intent it = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(it);
            }
        });

        devLogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                devOptionsClickCount++;
                if(devOptionsClickCount > 3) {
                    devUrlEditText.setVisibility(View.VISIBLE);
                    devUrlIcon.setVisibility(View.VISIBLE);
                    ((RelativeLayout.LayoutParams) loginButton.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.devUrl);
                    ((RelativeLayout.LayoutParams) registerButton.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.devUrl);
                    v.invalidate();
                    v.requestLayout();
                }
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + " " + model.getUsername();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        it.putExtra("username", model.getUsername());
        it.putExtra("password", model.getPassword());
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(it);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
