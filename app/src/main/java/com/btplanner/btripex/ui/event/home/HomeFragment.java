package com.btplanner.btripex.ui.event.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;
import com.btplanner.btripex.ui.event.EventActivity;
import com.btplanner.btripex.ui.event.home.eventimeline.AddEvent;
import com.btplanner.btripex.ui.event.home.eventimeline.TimeLineAdapter;
import com.btplanner.btripex.ui.event.home.eventimeline.TimeLineViewHolder;
import com.btplanner.btripex.ui.utils.ItemClickSupport;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

public class  HomeFragment extends Fragment {

    public static Map<String, Event> eventsMap = new HashMap<String, Event>();
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                /*textView.setText(s);*/
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
        super.onStart();

        ProgressBar progressBar = requireView().findViewById(R.id.progressbarHome);
        progressBar.setVisibility(View.VISIBLE);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Event>> call = service.getAllEvents(EventActivity.tripId);
        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), String.valueOf(R.string.event_get_call_failed), Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = requireView().findViewById(R.id.fab_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), AddEvent.class);
                progressBar.setVisibility(View.GONE);
                it.putExtra("id", EventActivity.tripId);
                it.putExtra("title", EventActivity.title);
                startActivity(it);
            }
        });
    }

    private void generateDataList(List<Event> eventList) {
        List<String> ids = eventList.stream().map(Event::getEventId).collect(Collectors.toList());
        int index = 0;
        for (Event event : eventList) {
            eventsMap.put(ids.get(index), event);
            index += 1;
        }

        mRecyclerView = requireView().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        initView(eventList);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        TimeLineViewHolder childHolder = (TimeLineViewHolder) recyclerView.findViewHolderForLayoutPosition(position);

                        Intent it = new Intent(getActivity(), AddEvent.class);
                        assert childHolder != null;
                        it.putExtra("eventId", childHolder.getId().getText());
                        it.putExtra("id", EventActivity.tripId);
                        it.putExtra("title", EventActivity.title);
                        startActivity(it);
                    }
                }
        );
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    private void initView(List<Event> eventList) {
        TimeLineAdapter mTimeLineAdapter = new TimeLineAdapter(eventList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }
}
