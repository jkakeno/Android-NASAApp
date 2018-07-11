package com.example.nasapp.Retrofit;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    static String NASA_BASE_URL ="https://api.nasa.gov";
    static String EPIC_BASE_URL ="https://epic.gsfc.nasa.gov";
    static String IMAGE_LIBRARY_BASE_URL ="https://images-api.nasa.gov";


    public static ApiInterface getApodApiInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NASA_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getEpicApiInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EPIC_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getImageLibraryInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IMAGE_LIBRARY_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getRoverImageInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NASA_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getAssetsInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NASA_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getEarthImageryInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NASA_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }
}
