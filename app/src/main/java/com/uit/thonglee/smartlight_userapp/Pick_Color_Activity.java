package com.uit.thonglee.smartlight_userapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by thonglee on 04/12/2017.
 */

public class Pick_Color_Activity extends AppCompatActivity implements View.OnTouchListener{

    TextView textView_hexcolor;
    TextView textView_rgbcolor;
    View view_colorpick;
    ImageView imageView_img;
    Uri uri = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_color);
        inital_view();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                uri = null;
            } else {
                uri = intent.getParcelableExtra(MainActivity.IMAGE_KEY);
            }
        } else {
            uri = savedInstanceState.getParcelable(MainActivity.IMAGE_KEY);
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

            textView_rgbcolor.setText("R" + redValue + ",G" + greenValue + ",B" + blueValue);
//            textView_hexcolor.setBackgroundColor(Color.rgb(redValue, greenValue, blueValue));

            String hexColor = String.format("#%06X", (0xFFFFFF & pixel));

            textView_hexcolor.setText(hexColor);
            view_colorpick.setBackgroundColor(Color.parseColor(hexColor));
        }
        System.gc();
        return false;
    }

    public void inital_view(){
        textView_hexcolor = (TextView) findViewById(R.id.txtv_color_hex);
        textView_rgbcolor = (TextView) findViewById(R.id.txtv_color_rgb);
        view_colorpick = (View) findViewById(R.id.view_color);
        imageView_img = (ImageView) findViewById(R.id.img_pick);
        imageView_img.setOnTouchListener(this);
    }
}
