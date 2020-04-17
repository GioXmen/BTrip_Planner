package com.btplanner.btripex.ui.event.statistics;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.covidmodel.CovidSummary;
import com.btplanner.btripex.data.network.GetDataService;
import com.btplanner.btripex.data.network.RetrofitClientInstance;
import com.btplanner.btripex.ui.event.statistics.charts.HorizontalBarChartFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Demonstrates how to keep your charts straight forward, simple and beautiful with the MPAndroidChart library.
 *
 * @author Philipp Jahoda
 */
public class StatisticsFragment extends Fragment {

    private StatisticsViewModel statisticsViewModel;
    public static CovidSummary covidSummary;
    private PageAdapter a;
    private ViewPager pager;
    private ProgressBar progressBar;

    private TextView totalConfirmed;
    private TextView totalDeaths;
    private TextView totalRecovered;
    private TextView newConfirmed;
    private TextView newDeaths;
    private TextView newRecovered;
    private TextView lastUpdate;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        totalConfirmed = root.findViewById(R.id.total_confirmed_data);
        totalDeaths = root.findViewById(R.id.total_deaths_data);
        totalRecovered = root.findViewById(R.id.total_recovered_data);
        newConfirmed = root.findViewById(R.id.new_confirmed_data);
        newDeaths = root.findViewById(R.id.new_deaths_data);
        newRecovered = root.findViewById(R.id.new_recovered_data);
        lastUpdate = root.findViewById(R.id.last_updated_data);

        progressBar = root.findViewById(R.id.progressbarStatistics);
        a = new PageAdapter(getFragmentManager());
        pager = root.findViewById(R.id.pager);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
        if(covidSummary == null){
        retrieveCovidSummary();
        }
        else {
            setupDashboardData();
            setupChartAfterDataRetrieval();
        }
    }

    public void setupChartAfterDataRetrieval(){
        final TextView textView = requireView().findViewById(R.id.statistics_empty_view);
        textView.setVisibility(View.GONE);

        pager.setOffscreenPageLimit(3);
        pager.setAdapter(a);
    }

    public void retrieveCovidSummary(){
        progressBar.setVisibility(View.VISIBLE);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<CovidSummary> call = service.getCovidSummary();
        call.enqueue(new Callback<CovidSummary>() {

            @Override
            public void onResponse(@NonNull Call<CovidSummary> call, @NonNull Response<CovidSummary> response) {
                progressBar.setVisibility(View.INVISIBLE);
                covidSummary = response.body();
                setupDashboardData();
                setupChartAfterDataRetrieval();
            }

            @Override
            public void onFailure(@NonNull Call<CovidSummary> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), String.valueOf(R.string.statistics_get_call_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDashboardData(){
        totalConfirmed.setText(String.valueOf(covidSummary.getGlobal().getTotalConfirmed()));
        totalDeaths.setText(String.valueOf(covidSummary.getGlobal().getTotalDeaths()));
        totalRecovered.setText(String.valueOf(covidSummary.getGlobal().getTotalRecovered()));
        newConfirmed.setText(String.valueOf(covidSummary.getGlobal().getNewConfirmed()));
        newDeaths.setText(String.valueOf(covidSummary.getGlobal().getNewDeaths()));
        newRecovered.setText(String.valueOf(covidSummary.getGlobal().getNewRecovered()));

        String template = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template, Locale.UK);
        String dateFormatted = simpleDateFormat.format(covidSummary.getDate());
        StringBuilder lastUpdated = new StringBuilder(dateFormatted);
        //lastUpdated.setCharAt(11, ' ');
        lastUpdate.setText(lastUpdated);
    }

    private class PageAdapter extends FragmentStatePagerAdapter {

        PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment f = null;

            switch(pos) {
                case 0:
                    f = HorizontalBarChartFragment.newInstance();
                    break;
                case 1:
                    f = HorizontalBarChartFragment.newInstance();
                    break;
/*                case 2:
                    f = BarChartFrag.newInstance();
                    break;
                case 3:
                    f = ScatterChartFrag.newInstance();
                    break;
                case 4:
                    f = PieChartFrag.newInstance();
                    break;*/
            }

            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
