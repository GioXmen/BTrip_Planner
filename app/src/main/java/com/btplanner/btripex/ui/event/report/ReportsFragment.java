package com.btplanner.btripex.ui.event.report;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.ui.event.EventActivity;
import com.btplanner.btripex.ui.event.EventViewModel;
import com.btplanner.btripex.ui.event.EventViewModelFactory;
import com.btplanner.btripex.ui.event.home.HomeFragment;
import com.btplanner.btripex.ui.event.report.utils.ExpenseReportResult;
import com.btplanner.btripex.ui.utils.ClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReportsFragment extends Fragment {

    private Button generateReport;
    private TextView emptyView;
    private ProgressBar progressBar;
    private EventViewModel eventViewModel;

    private List<Event> eventList = new ArrayList<>();
    private List<String> excludeEventIds = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReportsViewModel reportsViewModel = ViewModelProviders.of(this).get(ReportsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reports, container, false);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        eventViewModel = ViewModelProviders.of(this, new EventViewModelFactory())
                .get(EventViewModel.class);

        emptyView = requireView().findViewById(R.id.report_empty_view);
        generateReport = requireView().findViewById(R.id.generate_report);
        progressBar = requireView().findViewById(R.id.progressbarReports);
        progressBar.setVisibility(View.INVISIBLE);
        excludeEventIds = new ArrayList<>();
        eventList = new ArrayList<>();
        eventList.addAll(HomeFragment.staticEventsList);

        generateReport.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

        generateDataList(eventList);

        eventViewModel.getExpenseReportResult().observe(this, new Observer<ExpenseReportResult>() {
            @Override
            public void onChanged(@Nullable ExpenseReportResult expenseReportResult) {
                if (expenseReportResult == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (expenseReportResult.getError() != null) {
                    showGenerateReportFailed(expenseReportResult.getError());
                }
                if (expenseReportResult.getSuccess() != null) {
                    saveGeneratedReport(expenseReportResult.getSuccess());
                }
                requireActivity().setResult(Activity.RESULT_OK);
            }
        });



        generateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                eventViewModel.generatePDFReport(EventActivity.tripId, excludeEventIds, eventViewModel);
            }
        });
    }

    private void saveGeneratedReport(ExpenseReportUserView model) {
        String pdf = model.getGeneratedReport().getEncodedPDFReport();
        byte[] pdfAsBytes = Base64.decode(pdf, 0);
        String path = Environment.getExternalStorageDirectory() + File.separator + "BTripReports" + File.separator;
        File file = createPDF(pdfAsBytes, path);
        OpenPDFFile(file);
    }

    private File createPDF(byte[] pdfAsBytes, String path){
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path, EventActivity.title + "TripExpenseReport.pdf");

        if (file.exists ())
        {

            file.delete ();
            file = new File(path,EventActivity.title + "TripExpenseReport.pdf");
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(pdfAsBytes);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("file","replaced");

        }
        else
        {
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(pdfAsBytes);
                out.flush();
                out.close();
                Log.i("file","created");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private void OpenPDFFile(File pdfFile) {
        if (pdfFile.exists()) //Checking for the file is exist or not
        {
            Uri path = Uri.fromFile(pdfFile);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path, "application/pdf");
            objIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent intent = Intent.createChooser(objIntent, "Choose PDF viewer");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No PDF reader found or file path incorrect!" , Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showGenerateReportFailed(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void generateDataList(List<Event> eventList) {
        RecyclerView recyclerView = requireView().findViewById(R.id.expenseRecyclerView);
        generateReport = requireView().findViewById(R.id.generate_report);

        if (eventList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            generateReport.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            generateReport.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            ExpenseAdapter adapter = new ExpenseAdapter(getContext(), eventList, new ClickListener() {
                @Override public void onPositionClicked(int position) {
                    excludeEventIds.add(eventList.get(position).getEventId());
                    eventList.remove(position);
                    generateDataList(eventList);
                }

                @Override public void onLongClicked(int position) {
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

}
