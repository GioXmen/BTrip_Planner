package com.btplanner.btripex.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.btplanner.btripex.R;
import com.btplanner.btripex.data.model.LoggedInUser;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private LoggedInUser loggedInUser;
    private Context context;

    public CustomAdapter(Context context,LoggedInUser loggedInUser){
        this.context = context;
        this.loggedInUser = loggedInUser;
    }

     class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        EditText username;
        EditText password;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            username = mView.findViewById(R.id.username);
            password = mView.findViewById(R.id.password);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_login, parent, false);
        return new com.btplanner.btripex.ui.adapter.CustomAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.username.setText(loggedInUser.getUserName());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
/*        builder.build().load(loggedInUser.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);*/

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
