package com.uit.thonglee.smartlight_userapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uit.thonglee.smartlight_userapp.fragment.HomeController;

import java.io.IOException;

import static com.uit.thonglee.smartlight_userapp.fragment.HomeController.mMediaPlayer;


public class ClearAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        if(mMediaPlayer.isPlaying())
        {
            mMediaPlayer.stop();
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(this, AlertFireActivity.class);
        startActivity(intent);
        HomeController.notificationManager.cancelAll();
        finish();
    }
}
