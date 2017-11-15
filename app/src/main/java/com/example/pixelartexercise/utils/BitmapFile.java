package com.example.pixelartexercise.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by prebe on 13/11/2017.
 */

public class BitmapFile {

    private Bitmap bitmap;

    public BitmapFile(Bitmap map) {
        this.bitmap = map;
    }

    public Bitmap getResizedBitmap(int newWidth, int newHeight){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return res;
    }
}
