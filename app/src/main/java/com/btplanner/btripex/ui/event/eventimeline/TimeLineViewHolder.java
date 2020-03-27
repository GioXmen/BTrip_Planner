package com.btplanner.btripex.ui.event.eventimeline;

import android.view.View;
import android.widget.TextView;

import com.btplanner.btripex.R;
import com.github.vipulasri.timelineview.TimelineView;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeLineViewHolder extends RecyclerView.ViewHolder{


    @BindView(R.id.text_timeline_date)
    TextView mDate;
    @BindView(R.id.text_timeline_type)
    TextView mType;
    @BindView(R.id.text_timeline_location)
    TextView mLocation;
    @BindView(R.id.timeline)
    TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        mTimelineView.initLine(viewType);
    }
}
