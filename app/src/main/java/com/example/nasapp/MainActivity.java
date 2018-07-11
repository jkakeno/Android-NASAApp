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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.nasapp.Model.Apod;
import com.example.nasapp.Model.ApodAndEpic;
import com.example.nasapp.Model.Cover;
import com.example.nasapp.Model.Earth.Assets;
import com.example.nasapp.Model.Earth.Image;
import com.example.nasapp.Model.Earth.ResultsItem;
import com.example.nasapp.Model.Epic;
import com.example.nasapp.Model.LibraryImage.LibraryImageCollection;
import com.example.nasapp.Model.Rover;
import com.example.nasapp.Model.RoverImage.RoverImages;
import com.example.nasapp.Model.RoverList;
import com.example.nasapp.Retrofit.ApiInterface;
import com.example.nasapp.Retrofit.ApiUtils;
import com.example.nasapp.UI.ApodFragment;
import com.example.nasapp.UI.CoverListFragment;
import com.example.nasapp.UI.DelayedProgressDialog;
import com.example.nasapp.UI.EarthImageFragment;
import com.example.nasapp.UI.ImageListFragment;
import com.example.nasapp.UI.ImageSearchFragment;
import com.example.nasapp.UI.LocationPickFragment;
import com.example.nasapp.UI.RoverImageListFragment;
import com.example.nasapp.UI.RoverImageSearchFragment;
import com.example.nasapp.UI.RoverListFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements InteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String COVER_LIST_FRAGMENT = "cover_list_fragment";
    private static final String APOD_FRAGMENT = "apod_fragment";
    private static final String LOCATION_PICK_FRAGMENT = "location_pick_fragment";
    private static final String EARTH_IMAGE_FRAGMENT = "earth_picture_fragment";
    private static final String ROVER_LIST_FRAGMENT = "rover_list_fragment";
    private static final String ROVER_IMAGE_SEARCH_FRAGMENT = "rover_image_search_fragment";
    private static final String ROVER_IMAGE_LIST_FRAGMENT = "rover_image_list_fragment";
    private static final String IMAGE_SEARCH_FRAGMENT = "image_search_fragment";
    private static final String IMAGE_LIST_FRAGMENT = "image_list_fragment";
    private static final String PROGRESS_DIALOG = "progress_dialog";

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    FragmentManager fragmentManager;
    Cover apodCover;
    Cover earthCover;
    Cover marsCover;
    Cover searchCover;
    Rover roverSelected;
    Apod apod;
    LibraryImageCollection imageCollection;

    ArrayList<Cover> coverList= new ArrayList<>();
    ArrayList<Rover> roverList = new RoverList().getRoverList();
    ArrayList<Epic> epicList= new ArrayList<>();
    ArrayList<Image> earthImages=new ArrayList<>();

    LatLng currentLocation;
    LocationManager locationManager;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG,"wantLocation: " + wantLocation);
            if(wantLocation) {
                progressDialog.cancel();
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                Log.d(TAG,"Latitude: " + new DecimalFormat("##.######").format(currentLocation.latitude) + '\n'
                        + "Longitude: " + new DecimalFormat("###.######").format(currentLocation.longitude));

                LocationPickFragment locationPickFragment = LocationPickFragment.newInstance(currentLocation);
                fragmentManager.beginTransaction().add(R.id.root, locationPickFragment, LOCATION_PICK_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();

                wantLocation=false;
            }
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

    ApiInterface apodAPIInterface;
    ApiInterface epicAPIInterface;
    ApiInterface imageLibraryAPIInterface;
    ApiInterface roverImagesAPIInterface;
    ApiInterface assetsAPIInterface;
    ApiInterface earthImageryAPIInterface;

    Disposable disposableApodEpic;
    Disposable disposableImageCollection;
    Disposable disposableRoverImages;
    Disposable disposableAssets;
    Disposable disposableResults;
    Disposable disposableEarthImage;

    boolean gpsEnabled;
    boolean wantLocation;

    DelayedProgressDialog progressDialog = new DelayedProgressDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");

        /*NOTE: If there are issues with the lists field updating dynamically create them in onCreate().*/

        /*Display cover list fragment.*/
        fragmentManager = getSupportFragmentManager();

        /*Invoke api methods.*/
        apodAPIInterface = ApiUtils.getApodApiInterface();
        epicAPIInterface = ApiUtils.getEpicApiInterface();
        imageLibraryAPIInterface = ApiUtils.getImageLibraryInterface();
        roverImagesAPIInterface = ApiUtils.getRoverImageInterface();
        assetsAPIInterface = ApiUtils.getAssetsInterface();
        earthImageryAPIInterface = ApiUtils.getEarthImageryInterface();

        /*Get an observable apod object when calling apod api.*/
        Observable<Apod> apodObservable = apodAPIInterface.getApod().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        /*Get an observable epic object when calling epic api.*/
        Observable<ArrayList<Epic>> epicObservable = epicAPIInterface.getEpicImageList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        /*Combine the 2 observables apod and epic.*/
        Observable<ApodAndEpic> combinedObservable = Observable.zip(apodObservable, epicObservable, new BiFunction<Apod, ArrayList<Epic>, ApodAndEpic>() {
                    @Override
                    public ApodAndEpic apply(Apod apod, ArrayList<Epic> epics) throws Exception {
                        return new ApodAndEpic(apod,epics);
                    }
                });

        /*Create an anonymous observer to observe the combine observables.*/
        combinedObservable.subscribe(new Observer<ApodAndEpic>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "ApodEpic Observable onSubscribe()");
                /*Store the disposableApodEpic to dispose of this observer later.*/
                disposableApodEpic = d;
                progressDialog.show(fragmentManager,PROGRESS_DIALOG);
            }

            @Override
            public void onNext(ApodAndEpic apodAndEpic) {
                Log.d(TAG, "ApodEpic Observable onNext()");
                /*Store apod for later use.*/
                apod = apodAndEpic.getApod();
                /*Set apod cover and add it to the cover list.*/
                apodCover = new Cover("APOD");
                apodCover.setApod(apod);
                coverList.add(apodCover);
                /*Set epic cover and add it to the cover list.*/
                earthCover = new Cover("EARTH");
                epicList = apodAndEpic.getEpicList();
                earthCover.setEpicImageList(epicList);
                coverList.add(earthCover);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "ApodEpic Observable onError()");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "ApodEpic Observable onCompleted()");
                progressDialog.cancel();
                /*Once the anonymous observer has completed receiving omitted items add static covers to the cover list and start cover list fragment.*/
                coverList.add(marsCover);
                coverList.add(searchCover);

                CoverListFragment coverListFragment = CoverListFragment.newInstance(coverList);
                fragmentManager.beginTransaction().replace(R.id.root, coverListFragment, COVER_LIST_FRAGMENT).commit();
            }
        });

        /*Set up the covers with static content.*/
        marsCover = new Cover("MARS");
        marsCover.setImageTitle("Rover Photos");
        marsCover.setImageResource(Uri.parse("android.resource://com.example.nasapp/" + R.drawable.cover_mars));

        searchCover = new Cover("SEARCH");
        searchCover.setImageTitle("NASA Image Library");
        searchCover.setImageResource(Uri.parse("android.resource://com.example.nasapp/" + R.drawable.cover_library));


        // Get the location manager and request for location update
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
        if(disposableApodEpic!=null&&!disposableApodEpic.isDisposed()) {
            disposableApodEpic.dispose();
            Log.d(TAG,"ApodEpic Observable Disposed.");
        }
        if(disposableImageCollection!=null&&!disposableImageCollection.isDisposed()) {
            disposableImageCollection.dispose();
            Log.d(TAG,"LibraryImageCollection Observable Disposed.");
        }
        if(disposableRoverImages!=null&&!disposableRoverImages.isDisposed()) {
            disposableRoverImages.dispose();
            Log.d(TAG,"RoverImages Observable Disposed.");
        }
        if(disposableAssets!=null&&!disposableAssets.isDisposed()) {
            disposableAssets.dispose();
            Log.d(TAG,"Assets Observable Disposed.");
        }
        if(disposableResults!=null&&!disposableResults.isDisposed()) {
            disposableResults.dispose();
            Log.d(TAG,"Results Observable Disposed.");
        }
        if(disposableEarthImage!=null&&!disposableEarthImage.isDisposed()) {
            disposableEarthImage.dispose();
            Log.d(TAG,"Image Observable Disposed.");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public void onCoverSelectInteraction(Cover cover) {
        String coverTitle = cover.getCoverTitle();

        switch (coverTitle) {
            case "APOD":
                ApodFragment apodFragment = ApodFragment.newInstance(apod);
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
                    wantLocation=true;
                    /*NOTE: After requestLocationUpdates() is called takes about 5sec to get a location with a location listener.*/
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
                    progressDialog.show(fragmentManager,PROGRESS_DIALOG);
                }else{
                    LocationPickFragment locationPickFragment = LocationPickFragment.newInstance(currentLocation);
                    fragmentManager.beginTransaction().add(R.id.root, locationPickFragment, LOCATION_PICK_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();
                }
                break;
            case "MARS":
                RoverListFragment roverListFragment = RoverListFragment.newInstance(roverList);
                fragmentManager.beginTransaction().add(R.id.root, roverListFragment, ROVER_LIST_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT).commit();
                break;
            case "SEARCH":
                ImageSearchFragment imageSearchFragment = new ImageSearchFragment();
                ImageListFragment imageListFragment = new ImageListFragment();
                Fragment coverListFragment = fragmentManager.findFragmentByTag(COVER_LIST_FRAGMENT);
                int coverListFragmentId = coverListFragment.getId();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(coverListFragmentId,imageSearchFragment,IMAGE_SEARCH_FRAGMENT).addToBackStack(COVER_LIST_FRAGMENT);
                fragmentTransaction.add(R.id.image_list_container,imageListFragment,IMAGE_LIST_FRAGMENT);
                fragmentTransaction.commit();

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
    public void onGetEarthImageryInteraction(LatLng currentLocation, String begindate) {

        earthImages.clear();

        Log.d(TAG,"Latitude: " + new DecimalFormat("##.######").format(currentLocation.latitude) + '\n'
                + "Longitude: " + new DecimalFormat("###.######").format(currentLocation.longitude) + '\n'
                + "Date: " + begindate);

        final String lat = new DecimalFormat("##.######").format(currentLocation.latitude);
        final String lon = new DecimalFormat("###.######").format(currentLocation.longitude);

        /*Get an Observable Asset.*/
        Observable<Assets> assetsObservable = assetsAPIInterface.getAssets(lon,lat,begindate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        assetsObservable.subscribe(new Observer<Assets>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Assets Observable onSubscribed()");
                disposableAssets=d;
                progressDialog.show(fragmentManager,PROGRESS_DIALOG);
            }

            @Override
            public void onNext(Assets assets) {
                Log.d(TAG, "Assets Observable onNext()");

                /*Get the Result list from Assets.*/
                List<ResultsItem> resultsItemList = assets.getResults();
                if (resultsItemList != null) {
                    /*Make the list of results an iterable observable. Similar function as for loop.*/
                    /*Limit the number of emitted items to 10 to prevent errors (HTTP 429 Too Many Requests, Timeout).*/
                    /*Cascade observables to get the image for each result date.*/
                    Observable
                            .fromIterable(resultsItemList)
                            .take(15)
                            .concatMap(new Function<ResultsItem, ObservableSource<ResultsItem>>() {
                                @Override
                                public ObservableSource<ResultsItem> apply(final ResultsItem resultsItem) throws Exception {
                                    /*Get the image date from result and format so its acceptible to the api.*/
                                    String date = resultsItem.getDate();
                                    String[] dateParts = date.split("T");
                                    final String imageDate = dateParts[0];
                                    /*Call earthImageryAPIInterface for each result to get the image.*/
                                    return earthImageryAPIInterface.getEarthImage(lon, lat, imageDate).map(new Function<Image, ResultsItem>() {
                                        @Override
                                        public ResultsItem apply(Image image) throws Exception {
                                            /*The concatmap function expects a result as a return so add the image to a result.
                                            * NOTE: that an image field had to be added to the result class for this.
                                            * Then return a result.*/
                                            Log.d(TAG,"Get an image and set result...");
                                            resultsItem.setImage(image);
                                            return resultsItem;
                                        }
                                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                                }
                            })
                            .subscribeWith(new Observer<ResultsItem>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    Log.d(TAG, "Results Observable onSubscribed()");
                                    disposableResults = d;
                                }

                                @Override
                                public void onNext(ResultsItem resultsItem) {
                                    Log.d(TAG, "Results Observable onNext()");
                                    /*Add a image from result to the earth image list.*/
                                    earthImages.add(resultsItem.getImage());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG,"Result error: " + e.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                    Log.d(TAG, "Results Observable onComplete()");
                                    Log.d(TAG,"Result items completed -> Number of Images: " + earthImages.size());

                                    progressDialog.cancel();

                                    Fragment locationPickFragment = fragmentManager.findFragmentByTag(LOCATION_PICK_FRAGMENT);
                                    int locationPickFragmentId = locationPickFragment.getId();
                                    EarthImageFragment earthImageFragment = EarthImageFragment.newInstance(earthImages);
                                    fragmentManager.beginTransaction().replace(locationPickFragmentId, earthImageFragment, EARTH_IMAGE_FRAGMENT).addToBackStack(LOCATION_PICK_FRAGMENT).commit();
                                    /*NOTE: replace allows for onPause to be called when fragment is replaced. onPause is where the progress bar is dismissed.*/
                                }
                            });
                }else{
                    Toast.makeText(MainActivity.this, "There no images available for the selected date or location. Please select another date or location.", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Assets Observable onError()"+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Assets Observable onComplete()");
            }
        });
    }

    @Override
    public void onRoverSelectInteraction(Rover rover) {
        RoverImageSearchFragment roverImageSearchFragment = RoverImageSearchFragment.newInstance(rover);
        RoverImageListFragment roverImageListFragment =new RoverImageListFragment();
        Fragment roverListFragment = fragmentManager.findFragmentByTag(ROVER_LIST_FRAGMENT);
        int roverListFragmentId = roverListFragment.getId();

        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.add(roverListFragmentId, roverImageSearchFragment, ROVER_IMAGE_SEARCH_FRAGMENT).addToBackStack(ROVER_LIST_FRAGMENT);
        fragmentTransaction.add(R.id.mars_image_list_container,roverImageListFragment,ROVER_IMAGE_LIST_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    public void onGetRoverImageryInteraction(Rover rover) {
        this.roverSelected = rover;
        String name = rover.getRoverName();
        String url = null;
        switch (name){
            case "curiosity":
                url="/mars-photos/api/v1/rovers/"+"curiosity"+"/photos?api_key=rT9qT3KTMkGOzKSoVtYMjFLkJ7L5sXGA3xymwEqh";
                break;
            case "opportunity":
                url="/mars-photos/api/v1/rovers/"+"opportunity"+"/photos?api_key=rT9qT3KTMkGOzKSoVtYMjFLkJ7L5sXGA3xymwEqh";
                break;
            case "spirit":
                url="/mars-photos/api/v1/rovers/"+"spirit"+"/photos?api_key=rT9qT3KTMkGOzKSoVtYMjFLkJ7L5sXGA3xymwEqh";
                break;
        }
        String sol = rover.getSolSetting();
        String camera = rover.getCameraSetting();
        Observable<RoverImages> roverImagesObservable = roverImagesAPIInterface.getRoverImages(url,sol,camera).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        roverImagesObservable.subscribe(new Observer<RoverImages>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "RoverImages Observable onSubscribed()");
                disposableRoverImages=d;
                progressDialog.show(fragmentManager,PROGRESS_DIALOG);
            }

            @Override
            public void onNext(RoverImages roverImages) {
                Log.d(TAG, "RoverImages Observable onNext()");
                roverSelected.setRoverImages(roverImages);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "RoverImages Observable onError()");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "RoverImages Observable onComplete()");
                progressDialog.cancel();
                RoverImageListFragment roverImageListFragment = RoverImageListFragment.newInstance(roverSelected);
                fragmentManager.beginTransaction().replace(R.id.mars_image_list_container, roverImageListFragment, ROVER_IMAGE_LIST_FRAGMENT).commit();
            }
        });
    }

    @Override
    public void onSearchImageryInteraction(String keyword) {

        /*Get an observable library image collection object when calling image library api.*/
        Observable<LibraryImageCollection> libraryImageCollectionObservable = imageLibraryAPIInterface.getImageCollectionFromLibrary(keyword).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        libraryImageCollectionObservable.subscribe(new Observer<LibraryImageCollection>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "LibraryImageCollection Observable onSubscribed()");
                disposableImageCollection=d;
                progressDialog.show(fragmentManager,PROGRESS_DIALOG);
            }

            @Override
            public void onNext(LibraryImageCollection libraryImageCollection) {
                Log.d(TAG, "LibraryImageCollection Observable onNext()");
                imageCollection=libraryImageCollection;
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "LibraryImageCollection Observable onError()");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "LibraryImageCollection Observable onComplete()");
                progressDialog.cancel();
                ImageListFragment imageListFragment = ImageListFragment.newInstance(imageCollection);
                fragmentManager.beginTransaction().replace(R.id.image_list_container, imageListFragment, IMAGE_LIST_FRAGMENT).commit();
            }
        });
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


