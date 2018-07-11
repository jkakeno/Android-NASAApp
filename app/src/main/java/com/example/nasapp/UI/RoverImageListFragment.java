package com.example.nasapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.R;

public class RoverImageListFragment extends Fragment {
    private static final String TAG = RoverImageListFragment.class.getSimpleName();
    private static final String ARG = "rover";

    View view;
    TextView no_image_tv;
    RecyclerView recyclerView;
    RoverImageListAdapter adapter;
    GridLayoutManager layoutManager;

    Rover rover;
    InteractionListener listener;

    public RoverImageListFragment() {
        // Required empty public constructor
    }

    public static RoverImageListFragment newInstance(Rover rover) {
        RoverImageListFragment fragment = new RoverImageListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, rover);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            rover = getArguments().getParcelable(ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.rover_image_list_fragment, container, false);
        recyclerView = view.findViewById(R.id.rover_image_list);
        no_image_tv = view.findViewById(R.id.no_image);

        if(rover==null || rover.getRoverImages().getPhotos().size()==0) {
            no_image_tv.setVisibility(View.VISIBLE);
        }else {
            no_image_tv.setVisibility(View.GONE);
            adapter = new RoverImageListAdapter(getActivity(), rover, listener);
            layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
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
    }

}
