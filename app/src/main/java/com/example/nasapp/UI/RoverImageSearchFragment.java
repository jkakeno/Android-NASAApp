package com.example.nasapp.UI;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.R;

import java.util.ArrayList;

public class RoverImageSearchFragment extends Fragment {
    private static final String TAG = RoverImageSearchFragment.class.getSimpleName();
    private static final String ARG = "rover";

    View view;
    EditText solSettingSelect_et;
    Spinner cameraSettingSelect_sp;
    Button getMarsImage_bt;

    ArrayList<String> cameraList;
    InteractionListener listener;
    Rover rover;
    InputMethodManager imm;

    String solSetting;
    String cameraSetting;

    public RoverImageSearchFragment() {
        // Required empty public constructor
    }

    public static RoverImageSearchFragment newInstance(Rover rover) {
        RoverImageSearchFragment fragment = new RoverImageSearchFragment();
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
            cameraList = rover.getCameraList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.rover_image_search_fragment, container, false);
        solSettingSelect_et = view.findViewById(R.id.sol_setting_select);
        cameraSettingSelect_sp = view.findViewById(R.id.camera_setting_select);
        getMarsImage_bt = view.findViewById(R.id.get_mars_images);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        solSettingSelect_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    solSetting = (solSettingSelect_et.getText().toString());
                    rover.setSolSetting(solSetting);
                    solSettingSelect_et.setText(solSetting);
                    checkStartButtonVisibility();
                    imm.hideSoftInputFromWindow(solSettingSelect_et.getWindowToken(), 0);
                    solSettingSelect_et.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });

       /*Create select camera spinner*/
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_camera_item, cameraList){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView,parent);
                TextView camera_tv = (TextView) view;
                if(position == 0){
                    camera_tv.setTextColor(Color.DKGRAY);
                }else{
                    camera_tv.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_camera_item);
        cameraSettingSelect_sp.setAdapter(spinnerArrayAdapter);
        cameraSettingSelect_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraSetting = (String) parent.getItemAtPosition(position);
//                rover.setCameraSetting(cameraSetting);
                checkStartButtonVisibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


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

    private void checkStartButtonVisibility() {
        if(solSetting ==null|| solSetting.isEmpty()|| cameraSetting =="Select a camera..."){
            getMarsImage_bt.setVisibility(View.GONE);
        }else {
            getMarsImage_bt.setVisibility(View.VISIBLE);
            getMarsImage_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGetRoverImageryInteraction(rover);
                }
            });
        }
    }
}
