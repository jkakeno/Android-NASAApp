package com.jkdevelopment.astronomy.Model;

import java.util.ArrayList;

public class ApodAndEpic {

    Apod apod;
    ArrayList<Epic> epicList;

    public ApodAndEpic(Apod apod, ArrayList<Epic> epicList) {
        this.apod=apod;
        this.epicList=epicList;
    }

    public Apod getApod() {
        return apod;
    }

    public void setApod(Apod apod) {
        this.apod = apod;
    }

    public ArrayList<Epic> getEpicList() {
        return epicList;
    }

    public void setEpicList(ArrayList<Epic> epicList) {
        this.epicList = epicList;
    }
}
