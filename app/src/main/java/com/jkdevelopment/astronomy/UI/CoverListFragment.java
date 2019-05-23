package com.jkdevelopment.astronomy.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.jkdevelopment.astronomy.InteractionListener;
import com.jkdevelopment.astronomy.Model.Cover;
import com.jkdevelopment.astronomy.R;

import java.util.ArrayList;

public class CoverListFragment extends Fragment {
    private static final String TAG = CoverListFragment.class.getSimpleName();
    private static final String ARG = "cover_list";

    View view;
    RecyclerView recyclerView;
    CoverListAdapter adapter;
    LinearLayoutManager layoutManager;
    TextView nasaIcon_tv;

    ArrayList<Cover> coverList;
    InteractionListener listener;

    public CoverListFragment() {
        // Required empty public constructor
    }

    public static CoverListFragment newInstance(ArrayList<Cover> coverList) {
        CoverListFragment fragment = new CoverListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG, coverList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            coverList = getArguments().getParcelableArrayList(ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.cover_list_fragment,container,false);
        recyclerView = view.findViewById(R.id.cover_list);
        nasaIcon_tv = view.findViewById(R.id.nasa_icon);

        adapter = new CoverListAdapter(getActivity(),coverList, listener);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        /*Apply animation to views and list.*/
        LayoutAnimationController layoutAnimationFromBottom = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_from_bottom);
        Animation itemAnimationFallDown = AnimationUtils.loadAnimation(getActivity(), R.anim.item_animation_fall_down);
        nasaIcon_tv.startAnimation(itemAnimationFallDown);
        recyclerView.setLayoutAnimation(layoutAnimationFromBottom);

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
        /*Call this to trigger onDetachedFromRecyclerView() in adapter, so that the observable can be disposed.*/
        recyclerView.getAdapter().onDetachedFromRecyclerView(recyclerView);
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
