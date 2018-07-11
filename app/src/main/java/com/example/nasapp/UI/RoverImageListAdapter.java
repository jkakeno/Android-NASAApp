package com.example.nasapp.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.Model.RoverImage.PhotosItem;
import com.example.nasapp.R;

public class RoverImageListAdapter extends RecyclerView.Adapter<RoverImageListAdapter.ViewHolder> {

    InteractionListener listener;
    Rover rover;
    Context context;

    public RoverImageListAdapter(Context context, Rover rover, InteractionListener listener) {
        this.rover = rover;
        this.listener = listener;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rover_image_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotosItem marsImage = rover.getRoverImages().getPhotos().get(position);
        Log.d("MARS IMAGE URL: ", marsImage.getImgSrc());
        /*NOTE: Picasso couldn't load images for oportunity and spirit so use Glide here instead.*/
//        Picasso.with(context).load(marsImage.getImgSrc()).fit().centerCrop().into(holder.image);
        Glide.with(context).load(marsImage.getImgSrc()).fitCenter().centerCrop().into(holder.image);
        holder.date.setText(marsImage.getEarthDate());
    }

    @Override
    public int getItemCount() {
        if(rover.getRoverImages().getPhotos() !=null) {
            return rover.getRoverImages().getPhotos().size();
        }else{
            return 1;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.marsImage);
            date=itemView.findViewById(R.id.earthDate);
        }
    }
}
