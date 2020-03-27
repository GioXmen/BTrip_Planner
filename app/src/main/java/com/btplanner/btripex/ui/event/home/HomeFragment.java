package com.btplanner.btripex.ui.event.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.Trip;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;
import com.btplanner.btripex.ui.event.EventActivity;
import com.btplanner.btripex.ui.event.eventimeline.TimeLineAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
/*        final TextView textView = root.findViewById(R.id.text_home);

        final TextView id = root.findViewById(R.id.trip_id);
        final TextView title = root.findViewById(R.id.trip_title);

        id.setText(EventActivity.id);
        title.setText(EventActivity.title);*/


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                /*textView.setText(s);*/
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ProgressBar progressBar = (ProgressBar) requireView().findViewById(R.id.progressbarHome);
        progressBar.setVisibility(View.VISIBLE);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Event>> call = service.getAllEvents(EventActivity.id);
        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void generateDataList(List<Event> eventList) {
        mRecyclerView = (RecyclerView) requireView().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        initView(eventList);
    }

    private LinearLayoutManager getLinearLayoutManager() {
        //mark
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

    }

    private void initView(List<Event> eventList) {
        TimeLineAdapter mTimeLineAdapter = new TimeLineAdapter(eventList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }
}
