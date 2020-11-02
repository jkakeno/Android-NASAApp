package com.jkdevelopment.astronomy.UI;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkdevelopment.astronomy.InteractionListener;
import com.jkdevelopment.astronomy.Model.LibraryImage.LibraryImageCollection;
import com.jkdevelopment.astronomy.R;

public class ImageListFragment extends Fragment {
    private static final String TAG = ImageListFragment.class.getSimpleName();
    private static final String ARG = "library_image_collection";

    View view;
    ViewPager viewPager;
    ImageListAdapter adapter;
    TextView no_image_tv;

    LibraryImageCollection libraryImageCollection;
    InteractionListener listener;


    public ImageListFragment() {
        // Required empty public constructor
    }

    public static ImageListFragment newInstance(LibraryImageCollection libraryImageCollection) {
        ImageListFragment fragment = new ImageListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, libraryImageCollection);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            libraryImageCollection = getArguments().getParcelable(ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.image_list_fragment,container,false);
        viewPager = view.findViewById(R.id.viewPager);
        no_image_tv = view.findViewById(R.id.no_image);

        if(libraryImageCollection==null||libraryImageCollection.getCollection().getItems().size()==0) {
            no_image_tv.setVisibility(View.VISIBLE);
        }else{
            no_image_tv.setVisibility(View.GONE);
            adapter = new ImageListAdapter(getActivity(), libraryImageCollection, listener);
            viewPager.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttach");
        listener = (InteractionListener) context;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
        listener=null;
    }
}
