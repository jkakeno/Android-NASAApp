package com.example.nasapp.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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


public class MarsImageListAdapter extends RecyclerView.Adapter<MarsImageListAdapter.ViewHolder> {

    InteractionListener listener;
    Rover rover;
    Context context;
    Fragment fragment;

    public MarsImageListAdapter(Context context, Rover rover, InteractionListener listener, Fragment fragment) {
        this.rover = rover;
        this.listener = listener;
        this.context=context;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rover_image_item, parent,false);
                        /*Display cover list fragment.*/
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final PhotosItem marsImage = rover.getRoverImages().getPhotos().get(position);

        /*Postpone enter transition of this fragment (GridFragment) for when back is pressed from zoom fragment.*/
        fragment.startPostponedEnterTransition();

        /*NOTE: Picasso couldn't load images for opportunity and spirit so use Glide here instead.*/
        Glide.with(context).load(marsImage.getImgSrc()).fitCenter().centerCrop().into(holder.image);
        holder.date.setText(marsImage.getEarthDate());

        /*Set the shared element transition name. The shared element of this transition is the holder.image view. The transition name must be unique for every item.*/
        holder.image.setTransitionName(String.valueOf(marsImage));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarsImageFragment marsImageFragment = MarsImageFragment.newInstance(marsImage);
                /*Use the fragment manager of the fragment to which this adapter is attached to make the transition to the zoom fragment.*/
                /* So pass it with a unique name to addSharedElement().
                * Note: setReorderingAllowed(true) is needed with addSharedElement.*/
                fragment.getFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true) // Optimize for shared element transition
                        .addSharedElement(view, view.getTransitionName())
                        .replace(R.id.container, marsImageFragment, "mars_image_fragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(rover.getRoverImages().getPhotos() !=null) {
            return rover.getRoverImages().getPhotos().size();
        }else{
            return 1;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.marsImage);
            date=itemView.findViewById(R.id.earthDate);
        }
    }
}
