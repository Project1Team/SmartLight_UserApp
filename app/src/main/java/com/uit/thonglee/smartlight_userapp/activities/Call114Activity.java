package com.uit.thonglee.smartlight_userapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uit.thonglee.smartlight_userapp.fragment.HomeController;
import static com.uit.thonglee.smartlight_userapp.fragment.HomeController.mMediaPlayer;

public class Call114Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:114"));
        if(mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
        }
        HomeController.notificationManager.cancelAll();
        startActivity(intent);
        finish();
    }
}
