package com.example.nasapp.Model.RoverImage;

public class Annotation {

    String text;
    int color;
    int textLocationX;
    int textLocationY;


    public Annotation(String text, int color, int textLocationX, int textLocationY) {
        this.text = text;
        this.color = color;
        this.textLocationX = textLocationX;
        this.textLocationY = textLocationY;
    }

    public String getText() {
        return text;
    }

    public int getTextLocationX() {
        return textLocationX;
    }

    public int getTextLocationY() {
        return textLocationY;
    }

    public int getColor() {
        return color;
    }
}
