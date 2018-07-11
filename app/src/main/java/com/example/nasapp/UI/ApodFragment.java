package com.example.nasapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Apod;
import com.example.nasapp.R;
import com.squareup.picasso.Picasso;

public class ApodFragment extends Fragment {
    private static final String TAG = ApodFragment.class.getSimpleName();
    private static final String ARG = "cover";

    View view;
    ImageView apodImage_iv;
    TextView apodTitle_tv;
    TextView apodExplanation_tv;

    Apod apod;
    InteractionListener listener;

    public ApodFragment() {
        // Required empty public constructor
    }

    public static ApodFragment newInstance(Apod apod) {
        ApodFragment fragment = new ApodFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, apod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            apod = getArguments().getParcelable(ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.apod_fragment,container,false);
        apodImage_iv = view.findViewById(R.id.apod_image);
        apodTitle_tv = view.findViewById(R.id.apod_title);
        apodExplanation_tv = view.findViewById(R.id.apod_explanation);
        apodExplanation_tv.setMovementMethod(new ScrollingMovementMethod());

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
        Picasso.with(getActivity()).load(apod.getUrl()).fit().centerCrop().into(apodImage_iv);
        apodTitle_tv.setText(apod.getTitle());
        apodExplanation_tv.setText(apod.getExplanation());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
        listener=null;
    }
}
