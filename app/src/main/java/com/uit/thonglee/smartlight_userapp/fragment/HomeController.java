package com.uit.thonglee.smartlight_userapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suke.widget.SwitchButton;
import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.activities.LoginActivity;


public class HomeController extends Fragment {
    public static final String MSG_OPCODE_SWITCH_ON_1 = "0301";
    public static final String MSG_OPCODE_SWITCH_ON_2 = "0302";
    public static final String MSG_OPCODE_SWITCH_ON_3 = "0303";
    public static final String MSG_OPCODE_SWITCH_ON_4 = "0304";
    public static final String MSG_OPCODE_SWITCH_ON_5 = "0305";
    public static final String MSG_OPCODE_SWITCH_ON_6 = "0306";
    public static final String MSG_OPCODE_SWITCH_ON_7 = "0307";
    public static final String MSG_OPCODE_SWITCH_ON_8 = "0308";

    public static final String MSG_OPCODE_SWITCH_OFF_1 = "1311";
    public static final String MSG_OPCODE_SWITCH_OFF_2 = "1312";
    public static final String MSG_OPCODE_SWITCH_OFF_3 = "1313";
    public static final String MSG_OPCODE_SWITCH_OFF_4 = "1314";
    public static final String MSG_OPCODE_SWITCH_OFF_5 = "1315";
    public static final String MSG_OPCODE_SWITCH_OFF_6 = "1316";
    public static final String MSG_OPCODE_SWITCH_OFF_7 = "1317";
    public static final String MSG_OPCODE_SWITCH_OFF_8 = "1318";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_control, container, false);
        com.suke.widget.SwitchButton switchButton_1 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_1);
        com.suke.widget.SwitchButton switchButton_2 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_2);
        com.suke.widget.SwitchButton switchButton_3 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_3);
        com.suke.widget.SwitchButton switchButton_4 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_4);
        com.suke.widget.SwitchButton switchButton_5 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_5);
        com.suke.widget.SwitchButton switchButton_6 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_6);
        com.suke.widget.SwitchButton switchButton_7 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_7);
        com.suke.widget.SwitchButton switchButton_8 = (com.suke.widget.SwitchButton)
                view.findViewById(R.id.switch_button_8);


        switchButton_1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_1);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_1);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_2);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_2);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_3);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_3);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_4.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_4);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_4);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_5.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_5);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_5);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_6.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_6);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_6);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_7.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_7);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_7);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_8.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switchOn/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_8);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switchOff/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_8);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });
        return view;
    }
}
