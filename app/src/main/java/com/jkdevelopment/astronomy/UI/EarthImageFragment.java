package com.jkdevelopment.astronomy.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkdevelopment.astronomy.InteractionListener;
import com.jkdevelopment.astronomy.Model.Earth.Image;
import com.jkdevelopment.astronomy.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EarthImageFragment extends Fragment {

    private static final String TAG = EarthImageFragment.class.getSimpleName();
    private static final String ARG = "earth_image_list";

    View view;
    ImageView earthImage_iv;
    TextView dateTaken_tv;
    TextView cloudCoverage_tv;

    List<Image> earth_image_list;
    InteractionListener listener;
    Disposable disposableTimer;

    public EarthImageFragment() {
        // Required empty public constructor
    }

    public static EarthImageFragment newInstance(ArrayList<Image> earth_image_list) {
        EarthImageFragment fragment = new EarthImageFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG, earth_image_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            earth_image_list = getArguments().getParcelableArrayList(ARG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.earth_picture_fragment, container, false);
        earthImage_iv = view.findViewById(R.id.earth_image);
        dateTaken_tv = view.findViewById(R.id.date_taken);
        cloudCoverage_tv = view.findViewById(R.id.cloud_coverage);

        if(earth_image_list!=null) {
            /*Load images one after another every n sec.*/
            Observable
                    .interval(2000, TimeUnit.MILLISECONDS)
                    .take(earth_image_list.size())
                    .repeat()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG, "Timer Observable onSubscribe()");
                            disposableTimer = d;
                        }

                        @Override
                        public void onNext(Long aLong) {
                            Log.d(TAG, "Timer Observable onNext()");
                            Log.d(TAG, "Load image ..." + aLong);
                            final Image image = earth_image_list.get(aLong.intValue());
                            /*Pre-load images.*/
                            Picasso.with(getActivity()).load(image.getUrl()).fetch(new Callback() {
                                @Override
                                public void onSuccess() {
                                    /*Load images to image view.*/
                                    Picasso.with(getActivity()).load(image.getUrl()).into(earthImage_iv);
                                    dateTaken_tv.setText(image.getDate());
                                    double cloudScore = image.getCloudScore();
                                    String percentage = new DecimalFormat("##.#").format(cloudScore * 100);
                                    cloudCoverage_tv.setText(percentage + "%");
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        listener = null;
        if(disposableTimer!=null && !disposableTimer.isDisposed()){
            disposableTimer.dispose();
            Log.d(TAG,"Timer Observable Disposed.");
        }
    }

}
