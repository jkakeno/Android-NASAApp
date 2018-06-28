package com.example.nasapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Cover;
import com.example.nasapp.R;

public class ApodFragment extends Fragment {
    private static final String TAG = ApodFragment.class.getSimpleName();
    private static final String ARG = "cover";

    View view;
    ImageView apodImage;
    TextView apodTitle;
    TextView apodDescription;

    Cover cover;
    InteractionListener listener;

    public ApodFragment() {
        // Required empty public constructor
    }

    public static ApodFragment newInstance(Cover cover) {
        ApodFragment fragment = new ApodFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, cover);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            cover = getArguments().getParcelable(ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.apod_fragment,container,false);
        apodImage = view.findViewById(R.id.apod_image);
        apodTitle = view.findViewById(R.id.apod_title);
        apodDescription = view.findViewById(R.id.apod_description);

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
        apodImage.setImageURI(cover.getImageResource());
        apodTitle.setText(cover.getImageTitle());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
        listener=null;
    }
}
