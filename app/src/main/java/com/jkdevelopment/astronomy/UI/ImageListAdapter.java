package com.jkdevelopment.astronomy.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jkdevelopment.astronomy.InteractionListener;
import com.jkdevelopment.astronomy.Model.LibraryImage.ItemsItem;
import com.jkdevelopment.astronomy.Model.LibraryImage.LibraryImageCollection;
import com.jkdevelopment.astronomy.R;
import com.squareup.picasso.Picasso;

public class ImageListAdapter extends PagerAdapter {

    View itemView;
    ImageView imageView;
    TextView image_title_tv;
    TextView image_description_tv;

    InteractionListener listener;
    LibraryImageCollection libraryImageCollection;
    Context context;
    LayoutInflater layoutInflater;
    ItemsItem image;

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
        itemView = layoutInflater.inflate(R.layout.image_item, container, false);
        imageView = itemView.findViewById(R.id.image);
        image_title_tv =itemView.findViewById(R.id.title);
        image_description_tv =itemView.findViewById(R.id.description);

        /*Get an image.*/
        image= libraryImageCollection.getCollection().getItems().get(position);

        /*Display data.*/
        Picasso.with(context).load(image.getLinks().get(0).getHref()).fit().centerCrop().into(imageView);
        image_title_tv.setText(image.getData().get(0).getTitle());
        image_description_tv.setText(image.getData().get(0).getDescription508());

        /*Allow text view vertical scrolling.*/
        image_description_tv.setMovementMethod(new ScrollingMovementMethod());

        /*Set marquee text (automatic horizontal scroll).*/
        image_title_tv.setSelected(true);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
