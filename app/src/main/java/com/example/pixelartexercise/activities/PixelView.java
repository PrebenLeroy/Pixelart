package com.example.pixelartexercise.activities;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.pixelartexercise.R;
import com.example.pixelartexercise.models.Pixel;

/**
 * Created by prebe on 13/11/2017.
 */

public class PixelView extends ConstraintLayout {

    private Pixel pixel;

    private TextView textView;

    public PixelView(Context context, Pixel pixel) {
        super(context);
        initPixelView(pixel);
    }

    public PixelView(@NonNull Context context, @NonNull AttributeSet attrs, Pixel pixel) {
        super(context, attrs);
        initPixelView(pixel);
    }

    public PixelView(@NonNull Context context, @NonNull AttributeSet attrs, @AttrRes int defStyleAttr, Pixel pixel) {
        super(context, attrs, defStyleAttr);
        initPixelView(pixel);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    private void initPixelView(Pixel pixel) {
        this.pixel = pixel;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.pixel, this, true);
        textView = (TextView) findViewById(R.id.pixel);
        textView.setBackgroundColor(pixel.getBackgroundColor());
        updatePixelView();
    }

    public void setPixelColor(int color){
        this.pixel.changeColor(color);
    }

    public int getPixelColor(){ return pixel.getBackgroundColor(); }

    public void updatePixelView() {
        textView.setText("");
    }
}
