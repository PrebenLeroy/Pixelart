package com.example.pixelartexercise.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pixelartexercise.R;
import com.example.pixelartexercise.models.Grid;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private Board board;
    private ImageButton button, button2;
    private long actionbarHeight;
    private static JSONObject jsonObject;
    private int currentColor = Color.BLACK;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Grid grid;

    private Bitmap imageBit, imageBitmapNew;

    static{
        jsonObject = new JSONObject();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = (Board) findViewById(R.id.board);

        if(savedInstanceState != null){
            this.currentColor = (int) savedInstanceState.getInt("color");
        }

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        };

        for(int i = 0; i<board.getChildCount(); i++){
            PixelView pixelView = (PixelView) board.getChildAt(i);
            pixelView.setOnTouchListener(onTouchListener);
        }

        button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(false);
            }
        });

        button2 = (ImageButton) findViewById(R.id.imageButton2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getApplicationContext(), "Action canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public long getMenuBarHeight() {
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionbarHeight;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        //Hier eerst lokaliseren op welke textview men klikt

        for (int i = 0; i < board.getChildCount(); i++) {
            PixelView pixelView = (PixelView) board.getChildAt(i);
            if (x > pixelView.getLeft() && x < pixelView.getRight()) {
                if (y > (pixelView.getTop() + getStatusBarHeight() + getMenuBarHeight()) && y < (pixelView.getBottom() + getStatusBarHeight() + getMenuBarHeight())) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            pixelView.setBackgroundColor(currentColor);
                            pixelView.getTextView().setBackgroundColor(currentColor);
                            pixelView.setPixelColor(currentColor);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            pixelView.setBackgroundColor(currentColor);
                            pixelView.getTextView().setBackgroundColor(currentColor);
                            pixelView.setPixelColor(currentColor);
                            break;
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        return res;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBit = (Bitmap) extras.get("data");
            imageBit = Bitmap.createScaledBitmap(imageBit, 20, 20, false);
            imageBitmapNew = getResizedBitmap(imageBit, 25, 25);

            int width = imageBitmapNew.getWidth();
            int height = imageBitmapNew.getHeight();
            int[] store = new int[width * height];
            imageBitmapNew.getPixels(store, 0, width, 0, 0, width, height);

            for (int i = 0; i < board.getChildCount(); i++) {
                Log.i("color", String.format("%d",store[i]));
                PixelView pixelView = (PixelView) board.getChildAt(i);
                pixelView.setBackgroundColor(store[i]);
                pixelView.getTextView().setBackgroundColor(store[i]);
                pixelView.setPixelColor(store[i]);
            }
            //this.board.setBackgroundDrawable(new BitmapDrawable(imageBitmapNew));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("color", currentColor);
    }
}