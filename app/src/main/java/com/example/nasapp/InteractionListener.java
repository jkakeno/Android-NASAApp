package com.example.nasapp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.nasapp.Model.Cover;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.Model.RoverImage.PhotosItem;
import com.google.android.gms.maps.model.LatLng;

public interface InteractionListener {

    void onCoverSelectInteraction(Cover cover);
    void onGetEarthImageryInteraction(LatLng currentLocation, String date);
    void onRoverSelectInteraction(Rover rover);
    void onGetRoverImageryInteraction(Rover rover);
    void onMarsImageSelectInteraction(PhotosItem marsImage, ImageView imageView);
    void onMarsImageShareInteraction(Bitmap bitmap);
    void onSearchImageryInteraction(String keyword);
}
