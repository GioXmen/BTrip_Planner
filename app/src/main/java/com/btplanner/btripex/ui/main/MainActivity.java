package com.btplanner.btripex.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;
import com.btplanner.btripex.ui.event.EventActivity;
import com.btplanner.btripex.ui.utils.CustomAdapter;
import com.btplanner.btripex.ui.login.LoginActivity;
import com.btplanner.btripex.ui.main.addtrip.AddTrip;
import com.btplanner.btripex.ui.utils.ItemClickSupport;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static String username;
    public static String password;
    public static Map<String, Trip> tripsMap = new HashMap<String, Trip>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
    }

    @Override
    protected void onStart() {
        super.onStart();

        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Trip>> call = service.getAllTrips(username, password);
        call.enqueue(new Callback<List<Trip>>() {

            @Override
            public void onResponse(@NonNull Call<List<Trip>> call, @NonNull Response<List<Trip>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Trip>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), String.valueOf(R.string.trip_get_call_failed), Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AddTrip.class);
                progressBar.setVisibility(View.GONE);
                it.putExtra("username", username);
                it.putExtra("password", password);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    private void generateDataList(List<Trip> tripList) {
        List<String> ids = tripList.stream().map(Trip::getTripId).collect(Collectors.toList());
        int index = 0;
        for (Trip trip : tripList) {
            tripsMap.put(ids.get(index), trip);
            index += 1;
        }


        RecyclerView recyclerView = findViewById(R.id.customRecyclerView);
        TextView emptyView = findViewById(R.id.empty_view);

        if (tripList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            CustomAdapter adapter = new CustomAdapter(this, tripList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        CustomAdapter.CustomViewHolder childHolder = (CustomAdapter.CustomViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
                        assert childHolder != null;
                        Toast.makeText(getApplicationContext(), childHolder.txtTitle.getText(), Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(getApplicationContext(), EventActivity.class);
                        it.putExtra("id", childHolder.id.getText());
                        it.putExtra("title", childHolder.txtTitle.getText());
                        it.putExtra("username", username);
                        it.putExtra("password", password);
                        startActivity(it);
                    }
                }
        );
    }
}
