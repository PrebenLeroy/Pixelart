package com.example.pixelartexercise.models;

import android.graphics.Color;

/**
 * Created by prebe on 13/11/2017.
 */

public class Pixel {
    private int backgroundColor = 0;

    public Pixel(){}

    public Pixel(int backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public void changeColor(int color){
        this.backgroundColor = color;
    }

    public int getBackgroundColor(){
        return this.backgroundColor;
    }
}
