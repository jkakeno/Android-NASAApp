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

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.R;

import java.util.ArrayList;

public class RoverListFragment extends Fragment {
    private static final String TAG = RoverListFragment.class.getSimpleName();
    private static final String ARG = "rover_list";

    View view;
    RecyclerView recyclerView;
    RoverListAdapter adapter;
    GridLayoutManager layoutManager;

    ArrayList<Rover> roverList;
    InteractionListener listener;

    public RoverListFragment() {
        // Required empty public constructor
    }

    public static RoverListFragment newInstance(ArrayList<Rover> roverList) {
        RoverListFragment fragment = new RoverListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG, roverList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            roverList = getArguments().getParcelableArrayList(ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.rover_list_fragment,container,false);
        recyclerView = view.findViewById(R.id.rover_list);

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

        adapter = new RoverListAdapter(roverList, listener);
        layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
        listener=null;
    }
}
