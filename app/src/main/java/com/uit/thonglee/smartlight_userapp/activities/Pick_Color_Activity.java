package com.uit.thonglee.smartlight_userapp.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.uit.thonglee.smartlight_userapp.utils.BlurBuilder;
import com.uit.thonglee.smartlight_userapp.R;

import java.io.IOException;

/**
 * Created by thonglee on 04/12/2017.
 */

public class Pick_Color_Activity extends AppCompatActivity implements View.OnTouchListener{

    public ImageView imageView_img;
    public Uri uri = null;
    public SeekBar seekBar_wheel;
    public String red = "255";
    public String green = "255";
    public String blue = "255";
    public String brightness;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_color);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
        Bitmap blur_bitmap = BlurBuilder.blur(this, bm);
        this.getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), blur_bitmap));

        brightness = MainActivity.device.getBrightness();
        inital_view();

        seekBar_wheel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightness = exchange(i);
                try{
                    LoginActivity.client.send("changeBrightness/" + MainActivity.device.getMacAddr() + "/" + brightness);
                }

                catch(Exception e){

                }
        }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                uri = null;
            } else {
                uri = intent.getParcelableExtra(ControlActivity.IMAGE_KEY);
            }
        } else {
            uri = savedInstanceState.getParcelable(ControlActivity.IMAGE_KEY);
        }
        String uriString = getRealPathFromURI(uri);
        imageView_img.setImageBitmap(decodeSampledBitmapFromResource(uriString, 100, 100));
    }
    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    public static Bitmap decodeSampledBitmapFromResource(String resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(resId, options);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Matrix inverse = new Matrix();
        imageView_img.getImageMatrix().invert(inverse);
        float[] touchPoint = new float[] {event.getX(), event.getY()};
        inverse.mapPoints(touchPoint);
        int x = Integer.valueOf((int)touchPoint[0]);
        int y = Integer.valueOf((int) touchPoint[1]);

        int bitmapHeight=((BitmapDrawable) imageView_img.getDrawable()).getBitmap().getHeight();

        if(y>0 && y<bitmapHeight) {

            int pixel = ((BitmapDrawable) imageView_img.getDrawable()).getBitmap().getPixel(x, y);

            //then do what you want with the pixel data, e.g
            int redValue = Color.red(pixel);
            int blueValue = Color.blue(pixel);
            int greenValue = Color.green(pixel);

            red = exchange(redValue);
            green = exchange(greenValue);
            blue = exchange(blueValue);

            LoginActivity.client.send("changeColor/"+MainActivity.device.getMacAddr()+"/"+red + green + blue );

            String hexColor = String.format("#%06X", (0xFFFFFF & pixel));
        }
        System.gc();
        return false;
    }
    public String exchange(int value){
        if(value >= 0 && value < 10)
            return "00" + String.valueOf(value);
        else if (value >= 10 && value < 100)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }
    public void inital_view(){
        imageView_img = (ImageView) findViewById(R.id.img_pick);
        seekBar_wheel = (SeekBar) findViewById(R.id.seek_bar_pick_color);
        seekBar_wheel.setProgress(Integer.parseInt(brightness));
        imageView_img.setOnTouchListener(this);
    }
}
