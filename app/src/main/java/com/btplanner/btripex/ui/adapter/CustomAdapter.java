package com.btplanner.btripex.ui.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.btplanner.btripex.data.model.Trip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Trip> dataList;
    private Context context;

    public CustomAdapter(Context context, List<Trip> dataList){
        this.context = context;
        this.dataList = dataList;
    }

     class CustomViewHolder extends RecyclerView.ViewHolder {

         public final View mView;

         TextView txtTitle;
         private ImageView coverImage;

         CustomViewHolder(View itemView) {
             super(itemView);
             mView = itemView;

             txtTitle = mView.findViewById(R.id.title);
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

        RequestOptions options = new RequestOptions()
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background);


        Glide.with(context).load(Base64.decode(dataList.get(position).getThumbnail(), Base64.DEFAULT)).override(1920, 1080).apply(options).into(holder.coverImage);

/*
        Glide.with(context).load("https://source.unsplash.com/150x150/?trip").override(150, 150) .apply(options).into(holder.coverImage);
*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
