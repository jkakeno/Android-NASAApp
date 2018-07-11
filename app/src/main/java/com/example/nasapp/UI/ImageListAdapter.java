package com.example.nasapp.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.LibraryImage.ItemsItem;
import com.example.nasapp.Model.LibraryImage.LibraryImageCollection;
import com.example.nasapp.R;
import com.squareup.picasso.Picasso;

public class ImageListAdapter extends PagerAdapter {

    InteractionListener listener;
    LibraryImageCollection libraryImageCollection;
    Context context;
    LayoutInflater layoutInflater;

    public ImageListAdapter(Context context, LibraryImageCollection libraryImageCollection, InteractionListener listener) {
        this.libraryImageCollection = libraryImageCollection;
        this.listener = listener;
        this.context=context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(libraryImageCollection.getCollection().getItems() !=null) {
            return libraryImageCollection.getCollection().getItems().size();
        }else{
            return 1;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.image_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.image);
        TextView image_title =itemView.findViewById(R.id.title);
        TextView image_description=itemView.findViewById(R.id.description);


        ItemsItem image= libraryImageCollection.getCollection().getItems().get(position);

        Picasso.with(context).load(image.getLinks().get(0).getHref()).fit().centerCrop().into(imageView);
        image_title.setText(image.getData().get(0).getTitle());
        image_description.setText(image.getData().get(0).getDescription508());


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
