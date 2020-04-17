package com.btplanner.btripex.ui.event.statistics.charts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.covidmodel.CovidCountries;
import com.btplanner.btripex.data.covidmodel.CovidSummary;
import com.btplanner.btripex.ui.event.statistics.StatisticsFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseChartFragment extends Fragment{

    public static CovidSummary covidSummary;
    protected Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public BaseChartFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected BarChart generateBarChartData(BarChart chart){
        Typeface custom_font = Typeface.createFromAsset(requireActivity().getAssets(), "fonts/Lato-Light.ttf");
        covidSummary = StatisticsFragment.covidSummary;

        int count = 10;
        float barWidth = 9f;
        float spaceForBar = 10f;

        ArrayList<BarEntry> values = new ArrayList<>();
        List<CovidCountries> covidCountries = covidSummary.getCountries();
        covidCountries.sort(Comparator.comparing(CovidCountries::getTotalConfirmed).reversed());

        int[] colorsArray = new int[]{R.color.material_green_900, R.color.material_green_600, R.color.material_green_300, R.color.material_yellow_200,
                R.color.material_yellow_300, R.color.material_yellow_600, R.color.material_yellow_800, R.color.material_red_500,
                R.color.material_red_700, R.color.material_red_900};

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#1B5E20"));
        colors.add(Color.parseColor("#43A047"));
        colors.add(Color.parseColor("#FFF176"));
        colors.add(Color.parseColor("#FFF59D"));
        colors.add(Color.parseColor("#FFF176"));
        colors.add(Color.parseColor("#FDD835"));
        colors.add(Color.parseColor("#F9A825"));
        colors.add(Color.parseColor("#F44336"));
        colors.add(Color.parseColor("#D32F2F"));
        colors.add(Color.parseColor("#B71C1C"));


        List<LegendEntry> legendEntries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int val = covidCountries.get((count -1) - i).getTotalConfirmed();
            values.add(new BarEntry(i * spaceForBar, val, getResources().getDrawable(R.drawable.star)));

            LegendEntry legendEntry = new LegendEntry(covidCountries.get((count -1) - i).getCountry(),
                    Legend.LegendForm.SQUARE, 10f, 2f, null, colors.get(i));
            legendEntries.add(legendEntry);
        }

        BarDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Covid Cases Countries");

            set1.setDrawIcons(false);
            set1.setColors(colorsArray, getContext());

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(custom_font);
            data.setBarWidth(barWidth);
            chart.setData(data);
        }

        Legend customLegend  = chart.getLegend();
        customLegend.setCustom(legendEntries);
        chart.getLegend().setWordWrapEnabled(true);
        chart.getLegend().setYEntrySpace(10f);
        chart.getLegend().setXEntrySpace(10f);
        chart.getXAxis().setDrawLabels(false);
        return chart;
    }
}
