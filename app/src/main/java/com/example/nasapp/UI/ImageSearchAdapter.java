package com.example.nasapp.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.R;

import java.util.ArrayList;

public class ImageSearchAdapter extends RecyclerView.Adapter<ImageSearchAdapter.ViewHolder> {

    InteractionListener listener;
    ArrayList<String> imageList;

    public ImageSearchAdapter(ArrayList<String> imageList, InteractionListener listener) {
        this.imageList = imageList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String image = imageList.get(position);

        /*TODO: Set image and image description.*/

    }

    @Override
    public int getItemCount() {
        if(imageList !=null) {
            return imageList.size();
        }else{
            return 1;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView image_description;

        public ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            image_description=itemView.findViewById(R.id.description);

        }
    }
}
