package com.btplanner.btripex.ui.event.eventimeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.ui.event.eventimeline.utils.DateTimeUtils;
import com.btplanner.btripex.ui.event.eventimeline.utils.VectorDrawableUtils;
import com.github.vipulasri.timelineview.TimelineView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder>{

    private List<Event> mFeedList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<Event> feedList) {
        mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.item_timeline, parent, false);


        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        Event timeLineModel = mFeedList.get(position);

        if(timeLineModel.getEventType() == EventType.HOTEL) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_hotel_fill));
            holder.mType.setText(R.string.hotel);
        } else if(timeLineModel.getEventType() == EventType.RESTAURANT) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_restaurant_fill));
            holder.mType.setText(R.string.restaurant);
        } else if(timeLineModel.getEventType() == EventType.FLIGHT) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_airport_fill));
            holder.mType.setText(R.string.flight);
        }

        if(timeLineModel.getStartDate() != null && !timeLineModel.getStartDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            String eventStartDate = timeLineModel.getStartDate().substring(0, 10);
            String date = "Start date: " + eventStartDate;
            if(timeLineModel.getEventTime() != null && timeLineModel.getEventTime().length() > 10) {
                String eventStartTime = timeLineModel.getEventTime().substring(11, 16);
                date = date + " Time: " + eventStartTime;
            }
            holder.mDate.setText(date);
            holder.id.setText(timeLineModel.getEventId());
        }
        else
            holder.mDate.setVisibility(View.GONE);

        if(timeLineModel.getEventLocation() != null && !timeLineModel.getEventLocation().isEmpty()) {
            holder.mLocation.setVisibility(View.VISIBLE);
            String location = "Location: " + timeLineModel.getEventLocation();
            holder.mLocation.setText(location);
        }
        else
            holder.mLocation.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}
