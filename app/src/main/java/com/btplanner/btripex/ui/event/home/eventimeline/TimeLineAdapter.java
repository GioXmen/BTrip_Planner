package com.btplanner.btripex.ui.event.home.eventimeline;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.ui.utils.VectorDrawableUtils;
import com.github.vipulasri.timelineview.TimelineView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<Event> mFeedList;
    private Context mContext;
    private String previousMarker = "start";

    public TimeLineAdapter(List<Event> feedList) {
        mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        view = mLayoutInflater.inflate(R.layout.item_timeline, parent, false);

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

        Event timeLineModel = mFeedList.get(position);

        if (timeLineModel.getEventType() == EventType.HOTEL) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_hotel_fill));
            String text = mContext.getResources().getString(R.string.hotel) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.RESTAURANT) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_restaurant_fill));
            String text = mContext.getResources().getString(R.string.restaurant) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.FLIGHT) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_airport_fill));
            String text = mContext.getResources().getString(R.string.flight) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        }

        if (timeLineModel.getStartDate() != null && !timeLineModel.getStartDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            String eventStartDate = timeLineModel.getStartDate().substring(0, 10);
            String date = "Start date: " + eventStartDate;
            if (timeLineModel.getEventTime() != null && timeLineModel.getEventTime().length() > 10) {
                String eventStartTime = timeLineModel.getEventTime().substring(11, 16);
                date = date + " Time: " + eventStartTime;
            }
            holder.mDate.setText(date);
            holder.id.setText(timeLineModel.getEventId());
        } else
            holder.mDate.setVisibility(View.GONE);

        if (timeLineModel.getEventLocation() != null && !timeLineModel.getEventLocation().isEmpty()) {
            holder.mLocation.setVisibility(View.VISIBLE);
            String location = "Location: " + timeLineModel.getEventLocation();
            holder.mLocation.setText(location);
        } else
            holder.mLocation.setVisibility(View.GONE);


        String temp = previousMarker;
        if (datePeriodDetection(timeLineModel.getEventTime().substring(0, 16)).equals("after")) {
            holder.mTimelineView.setEndLineColor(Color.GREEN, holder.getItemViewType());
            holder.mTimelineView.setStartLineColor(Color.GREEN, holder.getItemViewType());
            previousMarker = "after";
        } else if (datePeriodDetection(timeLineModel.getEventTime().substring(0, 16)).equals("before")) {
            holder.mTimelineView.setEndLineColor(Color.RED, holder.getItemViewType());
            holder.mTimelineView.setStartLineColor(Color.RED, holder.getItemViewType());
            previousMarker = "before";
        }
        if(!temp.equals(previousMarker)){
            holder.mTimelineView.setStartLineColor(Color.rgb(255,165,0), holder.getItemViewType());
        }
    }

    private static String datePeriodDetection(String start) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(start, dateFormat);
        LocalDateTime currentDate = LocalDateTime.now();
        if(startDate.isAfter(currentDate)){
            return "after";
        } else if (startDate.isBefore(currentDate)){
            return "before";
        }
        return "equal";
    }

    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

}
