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

public class MarsPictureListFragment extends Fragment {
    private static final String TAG = MarsPictureListFragment.class.getSimpleName();
    private static final String ARG = "rover";

    View view;
    EditText solPicked_et;
    Spinner cameraPicked_sp;
    Button getMarsImage_bt;

    ArrayList<String> cameraList;
    InteractionListener listener;
    Rover rover;
    InputMethodManager imm;

    String selectedSol;
    String selectedCamera;

    public MarsPictureListFragment() {
        // Required empty public constructor
    }

    public static MarsPictureListFragment newInstance(Rover rover) {
        MarsPictureListFragment fragment = new MarsPictureListFragment();
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

        cameraList = new ArrayList<>();
        cameraList.add("Select a camera...");
        cameraList.add("Front Hazard Avoidance Camera");
        cameraList.add("Navigation Camera");
        cameraList.add("Panoramic Camera");
        cameraList.add("Thermal Emmision Spectrometer");
        cameraList.add("Entry, Decent, Landing Camera");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.mars_picture_list_fragment, container, false);
        solPicked_et = view.findViewById(R.id.sol_picked);
        cameraPicked_sp = view.findViewById(R.id.camera_picked);
        getMarsImage_bt = view.findViewById(R.id.get_mars_images);


        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        solPicked_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    selectedSol = (solPicked_et.getText().toString());
                    solPicked_et.setText(selectedSol);
                    checkStartButtonVisibility();
                    imm.hideSoftInputFromWindow(solPicked_et.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

       /*Create select period spinner*/
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_camera_item, cameraList){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView,parent);
                TextView period_tv = (TextView) view;
                if(position == 0){
                    period_tv.setTextColor(Color.LTGRAY);
                }else{
                    period_tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_period_item);
        cameraPicked_sp.setAdapter(spinnerArrayAdapter);
        cameraPicked_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCamera = (String) parent.getItemAtPosition(position);
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
        if(selectedSol!=null && selectedCamera!="Select a camera..."){
            getMarsImage_bt.setVisibility(View.VISIBLE);
            getMarsImage_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGetRoverImageryInteraction(true);
                }
            });

        }else {
            getMarsImage_bt.setVisibility(View.GONE);
        }
    }
}
