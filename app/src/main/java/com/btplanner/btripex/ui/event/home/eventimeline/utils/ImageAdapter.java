package com.btplanner.btripex.ui.event.home.eventimeline.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.btplanner.btripex.R;
import com.btplanner.btripex.ui.utils.ClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.CustomViewHolder> {

    private List<Bitmap> dataList;
    private final ClickListener listener;
    private Context context;

    public ImageAdapter(Context context, List<Bitmap> dataList, ClickListener listener){
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

     public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         final View mView;

         public ImageView expenseImage;
         public TextView imageData;
         public ImageButton deleteButton;
         private WeakReference<ClickListener> listenerRef;

         CustomViewHolder(View itemView, ClickListener listener) {
             super(itemView);
             mView = itemView;

             listenerRef = new WeakReference<>(listener);
             expenseImage = mView.findViewById(R.id.expenseImage);
             imageData = mView.findViewById(R.id.image_data);
             deleteButton = mView.findViewById(R.id.delete);
             deleteButton.setClickable(true);
        }

         @Override
         public void onClick(View v) {
             listenerRef.get().onPositionClicked(getAdapterPosition());
         }
     }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.image_row, parent, false);
        final CustomViewHolder holder = new CustomViewHolder(view, listener);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.listenerRef.get().onPositionClicked(holder.getPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background);

        if (dataList.get(position) != null) {
            Glide.with(context).load(dataList.get(position)).override(1920, 1080).apply(options).into(holder.expenseImage);
        } else Glide.with(context).load("https://source.unsplash.com/560x560/?trip").override(560, 560) .apply(options).into(holder.expenseImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
