package com.example.nasapp.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Cover;
import com.example.nasapp.Model.Epic;
import com.example.nasapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoverListAdapter extends RecyclerView.Adapter<CoverListAdapter.ViewHolder> {

    private static final String TAG = CoverListAdapter.class.getSimpleName();
    InteractionListener listener;
    ArrayList<Cover> coverList;
    Context context;
    Disposable disposableTimer;


    public CoverListAdapter(Context context, ArrayList<Cover> coverList, InteractionListener listener) {
        this.coverList = coverList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Cover cover = coverList.get(position);
        String coverTitle = cover.getCoverTitle();
        holder.cover_title.setText(coverTitle);

        switch (coverTitle){
            case "APOD":
                /*Set the APOD cover image and cover text.*/
                Picasso.with(context).load(cover.getApod().getUrl()).fit().centerCrop().into(holder.cover_image);
                holder.image_title.setText(cover.getApod().getTitle());
                break;
            case "EARTH":
                /*Load images one after another every n sec.*/
                final List<Epic> epicList=cover.getEpicImageList();
                if(epicList!=null) {
                    Observable
                            .interval(2000, TimeUnit.MILLISECONDS)
                            .take(epicList.size())
                            .repeat()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Long>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    Log.d(TAG, "Timer Observable onSubscribe()");
                                    disposableTimer=d;
                                }

                                @Override
                                public void onNext(Long aLong) {
                                    Log.d(TAG, "Timer Observable onNext()");
                                    Log.d(TAG, "Load image ..." + aLong);
                                    final Epic epic = (Epic) epicList.get(aLong.intValue());
                                    /*Pre-load images.*/
                                    Picasso.with(context).load(epic.getImageUrl()).fetch(new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            /*Load images to image view.*/
                                            Picasso.with(context).load(epic.getImageUrl()).into(holder.cover_image);
                                            holder.image_title.setText(epic.getDate());
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, "Timer Observable onError()" + e.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                    Log.d(TAG, "Timer Observable onComplete()");
                                }
                            });
                }
                break;
            case "MARS":
                /*Set the MARS cover image and cover text.*/
                holder.cover_image.setImageURI(cover.getImageResource());
                holder.image_title.setText(cover.getImageTitle());
                break;
            case "SEARCH":
                /*Set the SEARCH cover image and cover text.*/
                holder.cover_image.setImageURI(cover.getImageResource());
                holder.image_title.setText(cover.getImageTitle());
                break;
        }

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


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.d(TAG,"Adapter Detached from Recycler View.");
        if(disposableTimer!=null&&!disposableTimer.isDisposed()) {
            Log.d(TAG,"Timer Observable Disposed.");
            disposableTimer.dispose();
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
