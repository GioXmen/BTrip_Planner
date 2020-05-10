package com.btplanner.btripex.ui.event.home.eventimeline;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_hotel_timeline_fill));
            String text = mContext.getResources().getString(R.string.hotel) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.RESTAURANT) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_restaurant_timeline_fill));
            String text = mContext.getResources().getString(R.string.restaurant) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.FLIGHT) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_airport_timeline_fill));
            String text = mContext.getResources().getString(R.string.flight) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.TRANSPORTATION) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_transportation_timeline_fill));
            String text = mContext.getResources().getString(R.string.transportation) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.MEETING) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_meeting_timeline_fill));
            String text = mContext.getResources().getString(R.string.meeting) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.ACTIVITY) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_activity_timeline_fill));
            String text = mContext.getResources().getString(R.string.activity) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.ENTERTAINMENT) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_entertainment_timeline_fill));
            String text = mContext.getResources().getString(R.string.entertainment) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.SHOPPING) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_shopping_timeline_fill));
            String text = mContext.getResources().getString(R.string.shopping) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.SIGHTSEEING) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_sightseeing_timeline_fill));
            String text = mContext.getResources().getString(R.string.sightseeing) + " - " + timeLineModel.getEventName();
            holder.mType.setText(text);
        } else if (timeLineModel.getEventType() == EventType.OTHER) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_other_timeline_fill));
            String text = mContext.getResources().getString(R.string.other) + " - " + timeLineModel.getEventName();
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

    @SuppressLint("SimpleDateFormat")
    private static String datePeriodDetection(String start) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date startDate = sdf.parse(start);
            Date currentDate = new Date();
            if (startDate.after(currentDate)) {
                return "after";
            } else if (startDate.before(currentDate)) {
                return "before";
            }
            return "equal";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "before";
    }

    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

}
