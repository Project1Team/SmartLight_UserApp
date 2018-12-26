package com.uit.thonglee.smartlight_userapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.fragment.HomeController;

public class AlertActivity extends AppCompatActivity {

    ImageButton button_reset;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        button_reset = findViewById(R.id.btn_resetButton);
        String type = getIntent().getStringExtra(HomeController.RESET_STATUS);
//        if(type.equals("fire")){
//            button_reset.setText("Fire");
//        }
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.client.send("resetFire/" + LoginActivity.user.getHomes().get(0).getMacAddr());
                finish();
            }
        });
    }
}
