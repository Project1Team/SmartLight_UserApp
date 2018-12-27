package com.uit.thonglee.smartlight_userapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.fragment.HomeController;

public class AlertGasActivity extends AppCompatActivity {
    ImageButton button_reset;
    TextView textView_alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_gas);

        button_reset = findViewById(R.id.btn_resetButton_gas);
        textView_alert = findViewById(R.id.txtv_alert_gas);
        String type = getIntent().getStringExtra(HomeController.RESET_STATUS);
        if(type.equals("gas")){
            textView_alert.setText(getString(R.string.alert_gas));
        }
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.client.send("resetGas/" + LoginActivity.user.getHomes().get(0).getMacAddr());
                finish();
            }
        });
    }
}
