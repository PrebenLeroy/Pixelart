package com.example.pixelartexercise.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridLayout;

import com.example.pixelartexercise.models.Grid;

/**
 * Created by prebe on 13/11/2017.
 */

public class Board extends GridLayout {

    private Grid grid;

    private PixelView pixelView;

    public Board(Context context) {
        super(context);
        initBoard(context);
    }

    public Board(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initBoard(context);
    }

    public Board(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        initBoard(context);
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void initBoard(Context context) {
        grid = Grid.get(20);

        this.setRowCount(grid.getGridSize());
        this.setColumnCount(grid.getGridSize());

        int width = calculateCardWidth();

        for(int i = 0; i<grid.getGridSize(); i++){
            for(int j = 0; j<grid.getGridSize(); j++){
                PixelView view = new PixelView(this.getContext(), grid.getPixel(i,j));
                view.setPixelColor(grid.getPixel(i,j).getBackgroundColor());
                addView(view, width, width);
                Log.i("Info", "Added view " + i + " " + j);
                Log.i("color", String.format("%d", view.getPixelColor()));
            }
        }
    }

    private int calculateCardWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int actionBarHeight = getActionBarHeight();
        return Math.min(metrics.widthPixels, metrics.heightPixels-actionBarHeight)/grid.getGridSize();
    }

    private int getActionBarHeight() {
        final TypedArray ta = getContext().getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int actionBarHeight = (int) ta.getDimension(0, 0);
        return actionBarHeight;
    }

    public void updateBoard() {
        for (int i = 0; i < getChildCount(); i++) {
            pixelView = (PixelView) getChildAt(i);
            pixelView.updatePixelView();
        }
    }

}
