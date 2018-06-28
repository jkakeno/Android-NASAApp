package com.example.nasapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EarthPictureFragment extends Fragment {

    private static final String TAG = EarthPictureFragment.class.getSimpleName();
    private static final String ARG = "earth_image_list";

    View view;
    ImageView earthImage_iv;

    ArrayList<String> earth_image_list;
    InteractionListener listener;

    public EarthPictureFragment() {
        // Required empty public constructor
    }

    public static EarthPictureFragment newInstance(ArrayList<String> earth_image_list) {
        EarthPictureFragment fragment = new EarthPictureFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG, earth_image_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            earth_image_list = getArguments().getStringArrayList(ARG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.location_pick_fragment, container, false);
        earthImage_iv = view.findViewById(R.id.earth_image);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        listener = (InteractionListener) context;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        for(String url: earth_image_list)
        /*TODO: Iterate thru the list of URL and set image every 1 sec.*/
        Picasso.with(getActivity()).load("image_url").into(earthImage_iv);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        listener = null;
    }

}
