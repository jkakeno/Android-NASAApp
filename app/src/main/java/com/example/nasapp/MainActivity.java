package com.example.nasapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nasapp.Model.Cover;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.UI.ApodFragment;
import com.example.nasapp.UI.CoverListFragment;
import com.example.nasapp.UI.ImageSearchFragment;
import com.example.nasapp.UI.LocationPickFragment;
import com.example.nasapp.UI.MarsPictureListFragment;
import com.example.nasapp.UI.RoverListFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InteractionListener {

    /*API Key: rT9qT3KTMkGOzKSoVtYMjFLkJ7L5sXGA3xymwEqh*/
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String COVER_LIST_FRAGMENT = "cover_list_fragment";
    private static final String APOD_FRAGMENT = "apod_fragment";
    private static final String LOCATION_PICK_FRAGMENT = "location_pick_fragment";
    private static final String EARTH_PICTURE_FRAGMENT = "earth_picture_fragment";
    private static final String ROVER_LIST_FRAGMENT = "rover_list_fragment";
    private static final String ROVER_PICTURE_LIST_FRAGMENT = "rover_picture_list_fragment";
    private static final String IMAGE_SEARCH_FRAGMENT = "image_search_fragment";

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    FragmentManager fragmentManager;
    ArrayList<Cover> coverList;
    Cover apodCover;
    Cover earthCover;
    Cover marsCover;
    Cover searchCover;
    Cover selectedCover;
    LatLng currentLocation;
    ArrayList<String> earthImageList;
    ArrayList<Rover> roverList;
    ArrayList<String> imageList;
    Rover curiosity;
    Rover opportunity;
    Rover spirit;
    Rover selectedRover;
    LocationManager locationManager;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            Toast.makeText(MainActivity.this, "Latitude: " + new DecimalFormat("##.######").format(currentLocation.latitude) + '\n'
                    + "Longitude: " + new DecimalFormat("###.######").format(currentLocation.longitude), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    boolean gpsEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*TODO: Call NASA api to get cover images.*/
        /*TODO: Set cover object images.*/
        /*TODO: Get APOD image.*/
        /*TODO: Get Earth's images.*/

        /*Create cover list dummy.*/
        coverList = new ArrayList<>();
        apodCover = new Cover("APOD", "Celestial Image");
        apodCover.setImageResource(Uri.parse("android.resource://com.example.nasapp/" + R.drawable.cover_apod_dummry));

        earthCover = new Cover("EARTH", "2018-06-22");
        earthCover.setImageResource(Uri.parse("android.resource://com.example.nasapp/" + R.drawable.cover_earth_dummy));

        marsCover = new Cover("MARS", "Rover Photos");
        marsCover.setImageResource(Uri.parse("android.resource://com.example.nasapp/" + R.drawable.cover_mars));

        searchCover = new Cover("SEARCH", "NASA Image Library");
        searchCover.setImageResource(Uri.parse("android.resource://com.example.nasapp/" + R.drawable.cover_search));

        coverList.add(apodCover);
        coverList.add(earthCover);
        coverList.add(marsCover);
        coverList.add(searchCover);

        /*Create a dummy currentLocation*/
//        currentLocation = new LatLng(42.51763,-83.511803);

        /*Create earth image list.*/
        earthImageList = new ArrayList<>();

        /*Create rover list.*/
        curiosity = new Rover("curiosity", Uri.parse("android.resource://com.example.nasapp/" + R.drawable.rover_curiosity), "1000");
        opportunity = new Rover("opportunity", Uri.parse("android.resource://com.example.nasapp/" + R.drawable.rover_opportunity), "1500");
        spirit = new Rover("spirit", Uri.parse("android.resource://com.example.nasapp/" + R.drawable.rover_spirit), "2500");

        roverList = new ArrayList<>();
        roverList.add(curiosity);
        roverList.add(opportunity);
        roverList.add(spirit);

        /*Create search image image list*/
        imageList = new ArrayList<>();

        /*Display cover list fragment.*/
        fragmentManager = getSupportFragmentManager();
        CoverListFragment coverListFragment = CoverListFragment.newInstance(coverList);
        fragmentManager.beginTransaction().replace(R.id.root, coverListFragment, COVER_LIST_FRAGMENT).commit();

        // Get the location manager and request for location update
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, locationListener);
        }
    }

    @Override
    public void onCoverSelectInteraction(Cover cover) {
        selectedCover = cover;
        String coverTitle = cover.getCoverTitle();

        switch (coverTitle) {
            case "APOD":
                ApodFragment apodFragment = ApodFragment.newInstance(cover);
                fragmentManager.beginTransaction().add(R.id.root, apodFragment, APOD_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();
                break;
            case "EARTH":
                /*Check GSP setting*/
                gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if(!gpsEnabled){
                    enableGPS();
                }else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_ASK_PERMISSIONS);
                }else if(currentLocation==null){
                    /*NOTE: After requestLocationUpdates() is called takes about 5sec to get a location.*/
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
                }else{
                    /*TODO: Create Async task doInBackground -> getCurrentLocation() and postExecute -> launch locationPickFragment*/
                    LocationPickFragment locationPickFragment = LocationPickFragment.newInstance(currentLocation);
                    fragmentManager.beginTransaction().add(R.id.root, locationPickFragment, LOCATION_PICK_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            LocationPickFragment locationPickFragment = LocationPickFragment.newInstance(currentLocation);
//                            fragmentManager.beginTransaction().add(R.id.root, locationPickFragment, LOCATION_PICK_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();
//                        }
//                    },5000);
                }
                break;
            case "MARS":
                RoverListFragment roverListFragment = RoverListFragment.newInstance(roverList);
                fragmentManager.beginTransaction().add(R.id.root, roverListFragment, ROVER_LIST_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();
                break;
            case "SEARCH":
                ImageSearchFragment imageSearchFragment = ImageSearchFragment.newInstance(imageList);
                fragmentManager.beginTransaction().add(R.id.root, imageSearchFragment, IMAGE_SEARCH_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();
                break;
        }
    }

    public void enableGPS() {
            /*Create alert dialog to prompt the user to turn GPS (Location) ON.*/
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Please enable location on your phone to use this feature.");
            alertDialogBuilder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
    }

    @Override
    public void onRoverSelectInteraction(Rover rover) {
        this.selectedRover = rover;
        MarsPictureListFragment marsPictureListFragment = MarsPictureListFragment.newInstance(rover);
        fragmentManager.beginTransaction().add(R.id.root, marsPictureListFragment, ROVER_PICTURE_LIST_FRAGMENT).addToBackStack(ROVER_LIST_FRAGMENT).commit();
    }

    @Override
    public void onStartEarthImageryInteraction(LatLng currentLocation, String date, String period) {

        Toast.makeText(this, "Latitude: " + new DecimalFormat("##.######").format(currentLocation.latitude) + '\n'
                + "Longitude: " + new DecimalFormat("###.######").format(currentLocation.longitude) + '\n'
                + "Date: " + date + '\n'
                + "Period: " + period, Toast.LENGTH_LONG).show();
        /*TODO: Get earth images.*/
//        EarthPictureFragment earthPictureFragment = EarthPictureFragment.newInstance(earthImageList);
//        fragmentManager.beginTransaction().add(R.id.root, earthPictureFragment, EARTH_PICTURE_FRAGMENT).addToBackStack(LOCATION_PICK_FRAGMENT).commit();
    }

    @Override
    public void onGetRoverImageryInteraction(boolean click) {
        /*TODO: Get Mars's images taken by the selected rover.*/
    }

    @Override
    public void onSearchImageryInteraction(String keyword) {
        /*TODO: Get images from Nasa library.*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(this,"FINE LOCATION permission granted!",Toast.LENGTH_LONG).show();
                } else {
                    // Permission Denied
                    Toast.makeText(this,"FINE LOCATION permission denied!",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}


