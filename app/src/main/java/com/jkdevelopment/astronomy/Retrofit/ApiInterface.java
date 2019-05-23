package com.jkdevelopment.astronomy.Retrofit;

import com.jkdevelopment.astronomy.Model.Apod;
import com.jkdevelopment.astronomy.Model.Earth.Assets;
import com.jkdevelopment.astronomy.Model.Earth.Image;
import com.jkdevelopment.astronomy.Model.Epic;
import com.jkdevelopment.astronomy.Model.LibraryImage.LibraryImageCollection;
import com.jkdevelopment.astronomy.Model.RoverImage.RoverImages;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    String API_KEY = "rT9qT3KTMkGOzKSoVtYMjFLkJ7L5sXGA3xymwEqh";

    /*Make the api call return an observable instead of a call back.*/
    @GET("/planetary/apod?api_key=" + API_KEY)
    Observable<Apod> getApod();

    @GET("/api/natural")
    Observable<ArrayList<Epic>> getEpicImageList();

    @GET("/search?media_type=image")
    Observable<LibraryImageCollection> getImageCollectionFromLibrary(@Query("q") String keyword);

    @GET
    Observable<RoverImages> getRoverImages(@Url String url, @Query("sol") String sol, @Query("camera") String camera);

    @GET
    Observable<RoverImages> getRoverAllImages(@Url String url, @Query("sol") String sol);

    @GET("/planetary/earth/assets?api_key=" + API_KEY)
    Observable<Assets> getAssets(@Query("lon") String longitude, @Query("lat") String latitude, @Query("begin")String begin_date);

    @GET("/planetary/earth/imagery/?cloud_score=True&api_key=" + API_KEY)
    Observable<Image> getEarthImage(@Query("lon") String longitude, @Query("lat") String latitude, @Query("date")String date);

}
