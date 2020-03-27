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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String username;
    public static String password;

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Trip>> call = service.getAllTrips(username, password);
        call.enqueue(new Callback<List<Trip>>() {

            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AddTrip.class);
                progressBar.setVisibility(View.GONE);
                startActivity(it);
            }
        });
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


    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Trip> tripList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        emptyView = findViewById(R.id.empty_view);

        if (tripList == null || tripList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            adapter = new CustomAdapter(this,tripList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        //Make recyclerView clickable using ItemClickSupport util
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
                        startActivity(it);
                    }
                }
        );
    }
}
