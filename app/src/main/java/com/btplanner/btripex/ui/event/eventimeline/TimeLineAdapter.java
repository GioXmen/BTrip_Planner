package com.btplanner.btripex.ui.event.eventimeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
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
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_hotel_fill, android.R.color.holo_green_dark));
            holder.mType.setText(R.string.hotel);
        } else if(timeLineModel.getEventType() == EventType.RESTAURANT) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_restaurant_fill, R.color.material_grey_500));
            holder.mType.setText(R.string.restaurant);
        } else if(timeLineModel.getEventType() == EventType.FLIGHT) {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_airport_fill), ContextCompat.getColor(mContext, R.color.colorPrimary));
            holder.mType.setText(R.string.flight);

        }

        if(!timeLineModel.getStartDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            //holder.mDate.setText(DateTimeUtils.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
            Date tripStartDate = null;
            try {
                 tripStartDate = sdf.parse(timeLineModel.getStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String date = "Start date:" + tripStartDate;
            holder.mDate.setText(date);
        }
        else
            holder.mDate.setVisibility(View.GONE);

        if(!timeLineModel.getEventLocation().isEmpty()) {
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
