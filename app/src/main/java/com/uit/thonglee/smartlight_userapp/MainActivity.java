
package com.uit.thonglee.smartlight_userapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int CAMERA_REQUEST = 0;
    public static final int GALLERY_PICTURE = 1;
    public static final String IMAGE_KEY = "image_key";
    public static  String[] PERMISSION = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    Button button_pick_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial_view();
    }
    public void initial_view(){
        button_pick_color = (Button) findViewById(R.id.btn_pick_color);
        button_pick_color.setOnClickListener(this);
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
        }
    }
    public void showDialog() {

        ArrayList<String> optionItems=new ArrayList<String>();
        optionItems.add(getString(R.string.camera_dialog));
        optionItems.add(getString(R.string.galery_dialog));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_dialog));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(  MainActivity.this, android.R.layout.simple_list_item_1 ,optionItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setTextColor(Color.BLACK);
                return view;
            }
        };

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
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
                    Intent intent_camera = new Intent(MainActivity.this, Pick_Color_Activity.class);
                    intent_camera.putExtra(IMAGE_KEY, selected_img_camera);
                    startActivity(intent_camera);
                    break;
                case GALLERY_PICTURE:
                    Uri selected_img_galery = data.getData();
                    Intent intent_galery = new Intent(MainActivity.this, Pick_Color_Activity.class);
                    intent_galery.putExtra(IMAGE_KEY, selected_img_galery);
                    startActivity(intent_galery);
                    break;
            }
        }

    }
}
