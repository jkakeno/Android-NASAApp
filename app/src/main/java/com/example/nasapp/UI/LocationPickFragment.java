package com.example.nasapp.UI;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nasapp.InteractionListener;
import com.example.nasapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class LocationPickFragment extends Fragment {
    private static final String TAG = LocationPickFragment.class.getSimpleName();
    private static final String ARG = "currentLocation";

    View view;
    TextView latitude_tv;
    TextView longitude_tv;
    EditText datePicked_et;
    Spinner periodPicked_sp;
    Button getEarthImage_bt;

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePicker;
    ArrayList<String> periodList;
    LatLng currentLocation;
    InteractionListener listener;
    GoogleMap googleMap;

    String selectedPeriod;
    String selectedDate;

    public LocationPickFragment() {
        // Required empty public constructor
    }

    public static LocationPickFragment newInstance(LatLng location) {
        LocationPickFragment fragment = new LocationPickFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        if (getArguments() != null) {
            currentLocation = getArguments().getParcelable(ARG);
        }

        periodList = new ArrayList<>();
        periodList.add("Select a period...");
        periodList.add("1 day");
        periodList.add("1 week");
        periodList.add("1 month");
        periodList.add("1 year");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.location_pick_fragment, container, false);
        datePicked_et = view.findViewById(R.id.date_picked);
        periodPicked_sp = view.findViewById(R.id.period_picked);
        latitude_tv = view.findViewById(R.id.latitude);
        longitude_tv = view.findViewById(R.id.longitude);
        getEarthImage_bt = view.findViewById(R.id.get_earth_images);

        /*Zoom in the map and center the map on current user currentLocation.*/
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                LocationPickFragment.this.googleMap = googleMap;
                updateMarkerOnMap();
                /*Add capability to add a marker on map touch.*/
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        currentLocation = latLng;
                        updateMarkerOnMap();
                    }
                });
            }
        });

        /*Create date picker*/
        calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                String date = simpleDateFormat.format(calendar.getTime());
                datePicked_et.setText(date);
                selectedDate= String.valueOf(datePicked_et.getText());
                checkGetEarthImageButtonVisibility();
            }
        };
        datePicked_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*Create select period spinner*/
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_period_item, periodList){
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
        periodPicked_sp.setAdapter(spinnerArrayAdapter);
        periodPicked_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPeriod = (String) parent.getItemAtPosition(position);
                checkGetEarthImageButtonVisibility();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return view;
    }

    private void updateMarkerOnMap() {
        googleMap.clear();
        CameraUpdate center = CameraUpdateFactory.newLatLng(currentLocation);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.addMarker(new MarkerOptions().position(currentLocation));

        latitude_tv.setText(new DecimalFormat("##.######").format(currentLocation.latitude));
        longitude_tv.setText(new DecimalFormat("###.######").format(currentLocation.longitude));
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

    private void checkGetEarthImageButtonVisibility() {
        if(selectedDate!=null && selectedPeriod!="Select a period..."){
            getEarthImage_bt.setVisibility(View.VISIBLE);
            getEarthImage_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onStartEarthImageryInteraction(currentLocation,selectedDate,selectedPeriod);
                }
            });

        }else {
            getEarthImage_bt.setVisibility(View.GONE);
        }
    }
}
