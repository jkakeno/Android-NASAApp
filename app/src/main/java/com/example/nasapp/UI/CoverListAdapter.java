package com.example.nasapp.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Cover;
import com.example.nasapp.R;

import java.util.ArrayList;

public class CoverListAdapter extends RecyclerView.Adapter<CoverListAdapter.ViewHolder> {

    InteractionListener listener;
    ArrayList<Cover> coverList;

    public CoverListAdapter(ArrayList<Cover> coverList, InteractionListener listener) {
        this.coverList = coverList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Cover cover = coverList.get(position);
        holder.cover_title.setText(cover.getCoverTitle());
        holder.image_title.setText(cover.getImageTitle());
        holder.cover_image.setImageURI(cover.getImageResource());

        /*TODO: Set the APOD cover image use picasso to set from url.*/
        /*TODO: Set the EARTH cover image use picasso to set from url. Set a different image every 1sec.*/
        /*TODO: Set the MARS and SEARCH cover image from URI resources.*/

        holder.cover_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCoverSelectInteraction(cover);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(coverList !=null) {
            return coverList.size();
        }else{
            return 1;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cover_image;
        TextView cover_title;
        TextView image_title;

        public ViewHolder(View itemView) {
            super(itemView);
            cover_image=itemView.findViewById(R.id.cover_image);
            cover_title=itemView.findViewById(R.id.cover_title);
            image_title=itemView.findViewById(R.id.image_title);

        }
    }
}
