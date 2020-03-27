package com.btplanner.btripex.ui.utils;

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

import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Trip> dataList;
    private Context context;

    public CustomAdapter(Context context, List<Trip> dataList){
        this.context = context;
        this.dataList = dataList;
    }

     public class CustomViewHolder extends RecyclerView.ViewHolder {

         public final View mView;

         public TextView id;
         public TextView txtTitle;
         public TextView startDate;
         public TextView description;
         public ImageView coverImage;

         public CustomViewHolder(View itemView) {
             super(itemView);
             mView = itemView;

             id = mView.findViewById(R.id.id);
             txtTitle = mView.findViewById(R.id.title);
             startDate = mView.findViewById(R.id.startDate);
             description = mView.findViewById(R.id.description);
             coverImage = mView.findViewById(R.id.coverImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.description.setText(dataList.get(position).getTripDescription());
        holder.id.setText(dataList.get(position).getTripId());
        String startDate = dataList.get(position).getStartDate();

        holder.startDate.setText("Start Date: " + startDate.substring(0, 10));

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
