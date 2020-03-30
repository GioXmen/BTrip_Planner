package com.btplanner.btripex.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.btplanner.btripex.R;
import com.btplanner.btripex.ui.login.LoginActivity;
import com.btplanner.btripex.ui.main.addtrip.AddTrip;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class EventActivity extends AppCompatActivity {
    public static String tripId;
    public static String title;
    public static String username;
    public static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        tripId = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

/*        if (savedInstanceState != null) {
            id = savedInstanceState.getString("id");
            title = savedInstanceState.getString("title");
            username = savedInstanceState.getString("username");
            password = savedInstanceState.getString("password");
        } else {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            username = getIntent().getStringExtra("username");
            password = getIntent().getStringExtra("password");
        }*/

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("id", id);
        outState.putString("title", title);
        outState.putString("username", username);
        outState.putString("password", password);
        super.onSaveInstanceState(outState);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_trip) {
            Intent it = new Intent(getApplicationContext(), AddTrip.class);
            it.putExtra("tripId", tripId);
            it.putExtra("title", title);
            it.putExtra("username", username);
            it.putExtra("password", password);
            startActivity(it);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent it = new Intent(getApplicationContext(), LoginActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
