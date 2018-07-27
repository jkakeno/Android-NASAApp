package com.example.nasapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.Model.RoverList;
import com.example.nasapp.R;

public class MarsImageSearchFragment extends Fragment {
    private static final String TAG = MarsImageSearchFragment.class.getSimpleName();
    private static final String ARG = "rover_list";

    View view;
    GridView roverGrid;
    GridView cameraGrid;
    EditText sol_et;
    Button getMarsImage_bt;

    InteractionListener listener;
    Rover rover;
    RoverList roverList;
    InputMethodManager imm;


    public MarsImageSearchFragment() {
        // Required empty public constructor
    }

    public static MarsImageSearchFragment newInstance(RoverList roverList) {
        MarsImageSearchFragment fragment = new MarsImageSearchFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, roverList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        if (getArguments() != null) {
            roverList = getArguments().getParcelable(ARG);
        }
        rover=new Rover();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        view = inflater.inflate(R.layout.mars_image_search_fragment,container,false);

        final RoverGridAdapter roverGridAdapter = new RoverGridAdapter(getActivity(),roverList);
        roverGrid=view.findViewById(R.id.rover_grid);
        roverGrid.setAdapter(roverGridAdapter);

        final CameraGridAdapter cameraGridAdapter = new CameraGridAdapter(getActivity(),roverList);
        cameraGrid=view.findViewById(R.id.camera_grid);
        cameraGrid.setAdapter(cameraGridAdapter);

        sol_et = view.findViewById(R.id.sol_selected);

        getMarsImage_bt = view.findViewById(R.id.get_mars_images);
        getMarsImage_bt.setVisibility(View.GONE);

        roverGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                /*Pass the position to the adapter to persist the state of the selected item.*/
                roverGridAdapter.selectedRover =position;
                roverGridAdapter.notifyDataSetChanged();

                String roverName = roverList.getRoverList().get(position).getRoverName();
                rover.setRoverName(roverName);

                checkStartButtonVisibility();
            }
        });

        cameraGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                /*Pass the position to the adapter to persist the state of the selected item.*/
                cameraGridAdapter.selectedCamera =position;
                cameraGridAdapter.notifyDataSetChanged();

                String camera = roverList.getRoverList().get(0).getCameraList().get(position);
                rover.setCameraSetting(camera);

                checkStartButtonVisibility();
            }
        });

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        sol_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {

                    rover.setSolSetting(sol_et.getText().toString());
                    checkStartButtonVisibility();
                    imm.hideSoftInputFromWindow(sol_et.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void checkStartButtonVisibility() {
        Log.d(TAG,"Rover name: " +rover.getRoverName());
        Log.d(TAG,"Camera: " + rover.getCameraSetting());
        Log.d(TAG,"Martian Sol: " + rover.getSolSetting());

        if(rover.getRoverName()==null||rover.getCameraSetting()==null||rover.getSolSetting()==null||rover.getSolSetting().isEmpty()){
            getMarsImage_bt.setVisibility(View.GONE);
        }else{
            getMarsImage_bt.setVisibility(View.VISIBLE);
            getMarsImage_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGetRoverImageryInteraction(rover);
                }
            });
        }
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
