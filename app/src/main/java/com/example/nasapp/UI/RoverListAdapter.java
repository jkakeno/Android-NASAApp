package com.example.nasapp.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.R;

import java.util.ArrayList;

public class RoverListAdapter extends RecyclerView.Adapter<RoverListAdapter.ViewHolder>{
    InteractionListener listener;
    ArrayList<Rover> roverList;

    public RoverListAdapter(ArrayList<Rover> roverList, InteractionListener listener) {
        this.roverList = roverList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rover_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Rover rover = roverList.get(position);
        holder.rover_name_tv.setText(rover.getRoverName());
        holder.number_of_images_tv.setText(rover.getNumberOfImages());
        holder.rover_image.setImageURI(rover.getRoverImageUri());

        holder.rover_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRoverSelectInteraction(rover);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(roverList !=null) {
            return roverList.size();
        }else{
            return 1;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView rover_image;
        TextView rover_name_tv;
        TextView number_of_images_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            rover_image=itemView.findViewById(R.id.roverImage);
            rover_name_tv =itemView.findViewById(R.id.roverName);
            number_of_images_tv =itemView.findViewById(R.id.numberOfImages);

        }
    }
}
