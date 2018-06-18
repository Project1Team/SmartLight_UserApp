package com.uit.thonglee.smartlight_userapp.activities;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toolbar;

import com.uit.thonglee.smartlight_userapp.utils.BlurBuilder;
import com.uit.thonglee.smartlight_userapp.R;

/**
 * Created by thonglee on 29/12/2017.
 */

public class Wheel_color_picker_Activity extends AppCompatActivity implements View.OnTouchListener{

    ImageView imageView_wheel_color;
    SeekBar seekBar_wheel;
    android.support.v7.widget.Toolbar toolbar;

    public String red;
    public String green;
    public String blue;
    public String color;
    public String brightness;

    @Override
    protected void onResume() {
        super.onResume();
        color = MainActivity.device.getColor();
        brightness = MainActivity.device.getBrightness();
        seekBar_wheel.setProgress(Integer.parseInt(brightness));
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_color_picker);

        toolbar = findViewById(R.id.toolbar_wheel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if(LoginActivity.STATUS==LoginActivity.CONECTED)
            toolbar.setSubtitle("Connected");
        else
            toolbar.setSubtitle("Error");

        color = MainActivity.device.getColor();
        imageView_wheel_color = (ImageView) findViewById(R.id.img_wheel_color);
        seekBar_wheel = (SeekBar) findViewById(R.id.seek_bar_wheel_color);
        seekBar_wheel.setProgress(Integer.parseInt(MainActivity.device.getBrightness()));
        seekBar_wheel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("seekbar",String.valueOf(i));
                brightness = exchange(i);
                try {
                    LoginActivity.client.send("changeBrightness/"+MainActivity.device.getMacAddr()+"/" + brightness);
                }
                catch (Exception e){}
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        imageView_wheel_color.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Matrix inverse = new Matrix();
        imageView_wheel_color.getImageMatrix().invert(inverse);
        float[] touchPoint = new float[] {motionEvent.getX(), motionEvent.getY()};
        inverse.mapPoints(touchPoint);
        int x = Integer.valueOf((int)touchPoint[0]);
        int y = Integer.valueOf((int) touchPoint[1]);

        int bitmapHeight=((BitmapDrawable) imageView_wheel_color.getDrawable()).getBitmap().getHeight();

        if(y>0 && y<bitmapHeight) {

            int pixel = ((BitmapDrawable) imageView_wheel_color.getDrawable()).getBitmap().getPixel(x, y);

            //then do what you want with the pixel data, e.g
            int redValue = Color.red(pixel);
            int blueValue = Color.blue(pixel);
            int greenValue = Color.green(pixel);

            red = exchange(redValue);
            green = exchange(greenValue);
            blue = exchange(blueValue);

            color = red + green + blue;
            try{
                LoginActivity.client.send("changeColor/"+MainActivity.device.getMacAddr()+"/"+red + green + blue);
            }
            catch (Exception e){}
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
}
