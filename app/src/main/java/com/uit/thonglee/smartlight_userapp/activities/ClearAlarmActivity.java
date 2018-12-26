package com.uit.thonglee.smartlight_userapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uit.thonglee.smartlight_userapp.fragment.HomeController;
import static com.uit.thonglee.smartlight_userapp.fragment.HomeController.mMediaPlayer;


public class ClearAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        if(mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
        }
        Intent intent = new Intent(this, AlertActivity.class);
        startActivity(intent);
        HomeController.notificationManager.cancelAll();
        finish();
    }
}
