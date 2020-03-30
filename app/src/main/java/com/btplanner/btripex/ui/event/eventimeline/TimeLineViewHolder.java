package com.btplanner.btripex.ui.event.eventimeline;

import android.view.View;
import android.widget.TextView;

import com.btplanner.btripex.R;
import com.github.vipulasri.timelineview.TimelineView;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeLineViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.event_id)
    TextView id;
    @BindView(R.id.text_timeline_date)
    TextView mDate;
    @BindView(R.id.text_timeline_type)
    TextView mType;
    @BindView(R.id.text_timeline_location)
    TextView mLocation;
    @BindView(R.id.timeline)
    TimelineView mTimelineView;

    TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        mTimelineView.initLine(viewType);
    }

    public TextView getmDate() {
        return mDate;
    }

    public void setmDate(TextView mDate) {
        this.mDate = mDate;
    }

    public TextView getmLocation() {
        return mLocation;
    }

    public void setmLocation(TextView mLocation) {
        this.mLocation = mLocation;
    }

    public TextView getmType() {
        return mType;
    }

    public void setmType(TextView mType) {
        this.mType = mType;
    }

    public TimelineView getmTimelineView() {
        return mTimelineView;
    }

    public void setmTimelineView(TimelineView mTimelineView) {
        this.mTimelineView = mTimelineView;
    }

    public TextView getId() {
        return id;
    }

    public void setId(TextView id) {
        this.id = id;
    }
}
