package com.btplanner.btripex.ui.main;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.Trip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.CustomViewHolder> {

    private List<Trip> dataList;
    private Context context;

    TripListAdapter(Context context, List<Trip> dataList){
        this.context = context;
        this.dataList = dataList;
    }

     public static class CustomViewHolder extends RecyclerView.ViewHolder {

         final View mView;

         public TextView id;
         TextView txtTitle;
         public TextView startDate;
         TextView description;
         ImageView coverImage;

         CustomViewHolder(View itemView) {
             super(itemView);
             mView = itemView;

             id = mView.findViewById(R.id.id);
             txtTitle = mView.findViewById(R.id.title);
             startDate = mView.findViewById(R.id.startDate);
             description = mView.findViewById(R.id.description);
             coverImage = mView.findViewById(R.id.coverImage);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.id.setText(dataList.get(position).getTripId());

        if(dataList.get(position).getTitle() != null) {
            holder.txtTitle.setVisibility(View.VISIBLE);
            holder.txtTitle.setText(dataList.get(position).getTitle());
        } else{
            holder.txtTitle.setVisibility(View.GONE);
        }

        if(dataList.get(position).getTripDescription() != null) {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(dataList.get(position).getTripDescription());
        } else{
            holder.description.setVisibility(View.GONE);
        }

        if(dataList.get(position).getStartDate() != null) {
            holder.startDate.setVisibility(View.VISIBLE);
            String startDate = dataList.get(position).getStartDate();
            holder.startDate.setText("Start Date: " + startDate.substring(0, 10));
        } else{
            holder.startDate.setVisibility(View.GONE);
        }

        RequestOptions options = new RequestOptions()
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background);


        byte[] base64 = Base64.decode(dataList.get(position).getThumbnail(), Base64.DEFAULT);
        if (base64 != null) {
            Glide.with(context).load(base64).override(1920, 1080).apply(options).into(holder.coverImage);
            //Glide.with(context).load("https://source.unsplash.com/560x560/?trip").override(560, 560) .apply(options).into(holder.coverImage);
        } else Glide.with(context).load("https://source.unsplash.com/560x560/?trip").override(560, 560) .apply(options).into(holder.coverImage);
/*
        Glide.with(context).load("https://source.unsplash.com/150x150/?trip").override(150, 150) .apply(options).into(holder.coverImage);
*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
