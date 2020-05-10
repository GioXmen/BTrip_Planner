package com.btplanner.btripex.ui.event.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Event;
import com.btplanner.btripex.data.model.EventType;
import com.btplanner.btripex.ui.utils.ClickListener;
import com.btplanner.btripex.ui.utils.VectorDrawableUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.CustomViewHolder> {

    private List<Event> dataList;
    private final ClickListener listener;
    private Context context;

    public ExpenseAdapter(Context context, List<Event> dataList, ClickListener listener){
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final View mView;

        public TextView eventId;
        public ImageView expenseType;
        TextView expenseName;
        TextView expenseLocation;
        TextView expenseTotal;

        public ImageButton deleteButton;
        private WeakReference<ClickListener> listenerRef;

        CustomViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            mView = itemView;

            listenerRef = new WeakReference<>(listener);
            eventId = mView.findViewById(R.id.expense_data);
            expenseName = mView.findViewById(R.id.expense_name);
            expenseLocation = mView.findViewById(R.id.expense_location);
            expenseTotal = mView.findViewById(R.id.expense_total);
            expenseType = mView.findViewById(R.id.expense_type);
            deleteButton = mView.findViewById(R.id.delete_expense);
            deleteButton.setClickable(true);
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ExpenseAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_event_total, parent, false);
        final ExpenseAdapter.CustomViewHolder holder = new ExpenseAdapter.CustomViewHolder(view, listener);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.listenerRef.get().onPositionClicked(holder.getPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.CustomViewHolder holder, int position) {

        Event eventModel = dataList.get(position);

        RequestOptions options = new RequestOptions()
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background);

        if (eventModel.getEventType() == EventType.HOTEL) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(this.context, R.drawable.ic_hotel_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.RESTAURANT) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_restaurant_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.FLIGHT) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_airport_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.TRANSPORTATION) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_transportation_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.MEETING) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_meeting_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.ACTIVITY) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_activity_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.ENTERTAINMENT) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_entertainment_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.SHOPPING) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_shopping_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.SIGHTSEEING) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_sightseeing_fill)).apply(options).into(holder.expenseType);
        } else if (eventModel.getEventType() == EventType.OTHER) {
            Glide.with(context).load(VectorDrawableUtils.getBitmap(context, R.drawable.ic_other_fill)).apply(options).into(holder.expenseType);
        }


        if(!eventModel.getEventId().isEmpty()){
            holder.eventId.setText(eventModel.getEventId());
        }
        if(!eventModel.getEventName().isEmpty()){
            String expenseName = "Name: " + eventModel.getEventName();
            holder.expenseName.setText(expenseName);
        }
        if(!eventModel.getEventLocation().isEmpty()){
            String expenseLocation = "Location: " + eventModel.getEventLocation();
            holder.expenseLocation.setText(expenseLocation);
        }
        if(!eventModel.getEventExpense().isEmpty()){
            String expense = "Total: " + eventModel.getEventExpense();
            holder.expenseTotal.setText(expense);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
