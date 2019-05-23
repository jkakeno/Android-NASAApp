package com.jkdevelopment.astronomy.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkdevelopment.astronomy.InteractionListener;
import com.jkdevelopment.astronomy.Model.Apod;
import com.jkdevelopment.astronomy.R;
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

        /*Display data.*/
        Picasso.with(getActivity()).load(apod.getUrl()).fit().centerCrop().into(apodImage_iv);
        apodTitle_tv.setText(apod.getTitle());
        apodExplanation_tv.setText(apod.getExplanation());

        /*Allow vertical scrolling.*/
        apodExplanation_tv.setMovementMethod(new ScrollingMovementMethod());

        /*Set marquee text (text horizontal scroll).*/
        apodTitle_tv.setSelected(true);

        /*Apply animation to views.*/
        Animation itemAnimationSlideInFromRight = AnimationUtils.loadAnimation(getActivity(), R.anim.item_animation_slide_in_from_righ);
        Animation itemAnimationSlideInFromLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.item_animation_slide_in_from_left);
        Animation itemAnimationSlideInFromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.item_animation_slide_in_from_bottom);
        apodImage_iv.startAnimation(itemAnimationSlideInFromRight);
        apodTitle_tv.startAnimation(itemAnimationSlideInFromLeft);
        apodExplanation_tv.startAnimation(itemAnimationSlideInFromBottom);

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
