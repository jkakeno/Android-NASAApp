package com.jkdevelopment.astronomy.UI;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jkdevelopment.astronomy.InteractionListener;
import com.jkdevelopment.astronomy.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LocationPickFragment extends Fragment {
    private static final String TAG = LocationPickFragment.class.getSimpleName();
    private static final String ARG = "currentLocation";

    View view;
    TextView latitude_tv;
    TextView longitude_tv;
    EditText datePicked_et;
    Button getEarthImage_bt;

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePicker;
    LatLng currentLocation;
    InteractionListener listener;
    GoogleMap googleMap;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view = inflater.inflate(R.layout.location_pick_fragment, container, false);
        datePicked_et = view.findViewById(R.id.date_picked);
        latitude_tv = view.findViewById(R.id.latitude);
        longitude_tv = view.findViewById(R.id.longitude);
        getEarthImage_bt = view.findViewById(R.id.get_earth_images);

        getEarthImage_bt.setVisibility(View.GONE);

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
                String myFormat = "yyyy-MM-dd";
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

        if(selectedDate==null||selectedDate.isEmpty()){
            getEarthImage_bt.setVisibility(View.GONE);
        }else {
            getEarthImage_bt.setVisibility(View.VISIBLE);
            getEarthImage_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onGetEarthImageryInteraction(currentLocation,selectedDate);
                }
            });
        }
    }
}
