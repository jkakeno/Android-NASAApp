package com.example.nasapp;

import android.graphics.Bitmap;

import com.example.nasapp.Model.Cover;
import com.example.nasapp.Model.Rover;
import com.google.android.gms.maps.model.LatLng;

public interface InteractionListener {

    void onCoverSelectInteraction(Cover cover);
    void onGetEarthImageryInteraction(LatLng currentLocation, String date);
    void onGetRoverImageryInteraction(Rover rover);
    void onMarsImageShareInteraction(Bitmap bitmap);
    void onSearchImageryInteraction(String keyword);
}
