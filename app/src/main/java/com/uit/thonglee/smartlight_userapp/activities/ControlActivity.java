package com.uit.thonglee.smartlight_userapp.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.uit.thonglee.smartlight_userapp.utils.BlurBuilder;
import com.uit.thonglee.smartlight_userapp.view.CustomButton;
import com.uit.thonglee.smartlight_userapp.R;

import java.util.ArrayList;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener{

public static final String TAG = "Tag";
public static final int CAMERA_REQUEST = 0;
public static final int GALLERY_PICTURE = 1;
public static final String IMAGE_KEY = "image_key";
public static  String[] PERMISSION = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        Toolbar toolbar;

        CustomButton button_pick_color;
        CustomButton button_wheel_color;
        CustomButton button_red;
        CustomButton button_blue;
        CustomButton button_green;

@RequiresApi(api = Build.VERSION_CODES.ECLAIR)
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initial_view();

        }

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public void initial_view(){

        setContentView(R.layout.activity_control);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
        Bitmap blur_bitmap = BlurBuilder.blur(this, bm);
        this.getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), blur_bitmap));

        button_pick_color = (CustomButton) findViewById(R.id.btn_pick_color);
        button_wheel_color = (CustomButton) findViewById(R.id.btn_wheel_color);
        button_blue = (CustomButton) findViewById(R.id.btn_blue);
        button_green = (CustomButton) findViewById(R.id.btn_green);
        button_red = (CustomButton) findViewById(R.id.btn_red);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_control);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setSubtitle("None");

        button_pick_color.setOnClickListener(this);
        button_wheel_color.setOnClickListener(this);
        button_green.setOnClickListener(this);
        button_red.setOnClickListener(this);
        button_blue.setOnClickListener(this);
        }

public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (String permission : permissions) {
                        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        Log.e("permission", "" + permission);
                        return false;
                        }
                }
        }
        return true;
}
@Override
public void onClick(View view) {
        switch (view.getId()){
        case R.id.btn_pick_color:
        if(hasPermissions(this, PERMISSION))
        showDialog();
        break;
        case R.id.btn_wheel_color:
        Intent intent = new Intent(ControlActivity.this, Wheel_color_picker_Activity.class);
        startActivity(intent);
        break;
        case R.id.btn_red:
                try {
                    LoginActivity.client.send("changeColor/"+MainActivity.device.getMacAddr()+"/"+"255000000");
                }
                catch (Exception e){}
        break;
        case R.id.btn_blue:
            try{
                LoginActivity.client.send("changeColor/"+MainActivity.device.getMacAddr()+"/"+"000000255");
            }
            catch(Exception e){}
        break;
        case R.id.btn_green:
            try{
                LoginActivity.client.send("changeColor/"+MainActivity.device.getMacAddr()+"/"+"000255000");
            }
            catch (Exception e){}
        break;

        }
        }
public void showDialog() {

        ArrayList<String> optionItems=new ArrayList<String>();
        optionItems.add(getString(R.string.camera_dialog));
        optionItems.add(getString(R.string.galery_dialog));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_dialog));

final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(  ControlActivity.this, android.R.layout.simple_list_item_1 ,optionItems) {
@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        text1.setTextColor(Color.BLACK);
        return view;
        }
        };

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
@RequiresApi(api = Build.VERSION_CODES.ECLAIR)
@Override
public void onClick(DialogInterface dialog, int which) {
        switch (which) {
        case 0:
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        break;
        case 1:
        Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        break;
        }
        }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        }
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
        switch (requestCode){
        case CAMERA_REQUEST:
        Uri selected_img_camera = data.getData();
        Intent intent_camera = new Intent(ControlActivity.this, Pick_Color_Activity.class);
        intent_camera.putExtra(IMAGE_KEY, selected_img_camera);
        startActivity(intent_camera);
        break;
        case GALLERY_PICTURE:
        Uri selected_img_galery = data.getData();
        Intent intent_galery = new Intent(ControlActivity.this, Pick_Color_Activity.class);
        intent_galery.putExtra(IMAGE_KEY, selected_img_galery);
        startActivity(intent_galery);
        break;
        }
        }

        }
        }
