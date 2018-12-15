package com.uit.thonglee.smartlight_userapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suke.widget.SwitchButton;
import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.activities.LoginActivity;
import com.uit.thonglee.smartlight_userapp.utils.UserConverter;


public class HomeController extends Fragment {

    public static final String MSG_OPCODE_SWITCH_OFF_1 = "010";
    public static final String MSG_OPCODE_SWITCH_OFF_2 = "020";
    public static final String MSG_OPCODE_SWITCH_OFF_3 = "030";
    public static final String MSG_OPCODE_SWITCH_OFF_4 = "040";

    public static final String MSG_OPCODE_SWITCH_ON_1 = "011";
    public static final String MSG_OPCODE_SWITCH_ON_2 = "021";
    public static final String MSG_OPCODE_SWITCH_ON_3 = "031";
    public static final String MSG_OPCODE_SWITCH_ON_4 = "041";

    public static final String MSG_OPCODE_SWITCH_OFF_5 = "110";
    public static final String MSG_OPCODE_SWITCH_OFF_6 = "120";
    public static final String MSG_OPCODE_SWITCH_OFF_7 = "130";
    public static final String MSG_OPCODE_SWITCH_OFF_8 = "140";

    public static final String MSG_OPCODE_SWITCH_ON_5 = "111";
    public static final String MSG_OPCODE_SWITCH_ON_6 = "121";
    public static final String MSG_OPCODE_SWITCH_ON_7 = "131";
    public static final String MSG_OPCODE_SWITCH_ON_8 = "141";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_control, container, false);

        TextView textView_temperature = view.findViewById(R.id.txtv_temperature);
        TextView textView_gas = view.findViewById(R.id.txtv_gas);
        TextView textView_fire = view.findViewById(R.id.txtv_fire);

        TextView textView_switch11 = view.findViewById(R.id.txtv_switch11);
        TextView textView_switch12 = view.findViewById(R.id.txtv_switch12);
        TextView textView_switch13 = view.findViewById(R.id.txtv_switch13);
        TextView textView_switch14 = view.findViewById(R.id.txtv_switch14);

        TextView textView_switch21 = view.findViewById(R.id.txtv_switch21);
        TextView textView_switch22 = view.findViewById(R.id.txtv_switch22);
        TextView textView_switch23 = view.findViewById(R.id.txtv_switch23);
        TextView textView_switch24 = view.findViewById(R.id.txtv_switch24);

        SwitchButton switchButton_11 = view.findViewById(R.id.switch_button_11);
        SwitchButton switchButton_12 = view.findViewById(R.id.switch_button_12);
        SwitchButton switchButton_13 = view.findViewById(R.id.switch_button_13);
        SwitchButton switchButton_14 = view.findViewById(R.id.switch_button_14);

        SwitchButton switchButton_21 = view.findViewById(R.id.switch_button_21);
        SwitchButton switchButton_22 = view.findViewById(R.id.switch_button_22);
        SwitchButton switchButton_23 = view.findViewById(R.id.switch_button_23);
        SwitchButton switchButton_24 = view.findViewById(R.id.switch_button_24);

        textView_temperature.setText(UserConverter.getTeamperatureValue(LoginActivity.user.getHomes().get(0).getMacAddr()));
        textView_gas.setText(UserConverter.getGasStatus(LoginActivity.user.getHomes().get(0).getMacAddr()));
        textView_fire.setText(UserConverter.getFireStatus(LoginActivity.user.getHomes().get(0).getMacAddr()));

        textView_switch11.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 0));
        textView_switch12.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 1));
        textView_switch13.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 2));
        textView_switch14.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 3));

        textView_switch21.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 0));
        textView_switch22.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 1));
        textView_switch23.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 2));
        textView_switch24.setText(UserConverter.getNameSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 3));

        switchButton_11.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 0));
        switchButton_12.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 1));
        switchButton_13.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 2));
        switchButton_14.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "1", 3));

        switchButton_21.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 0));
        switchButton_22.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 1));
        switchButton_23.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 2));
        switchButton_24.setChecked(UserConverter.getStatusSwitch(LoginActivity.user.getHomes().get(0).getMacAddr(), "2", 3));

        switchButton_11.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_1);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_1);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_12.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_2);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_2);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_13.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_3);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_3);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_14.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_4);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_4);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_21.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_5);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_5);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_22.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_6);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_6);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_23.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_7);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_7);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });

        switchButton_24.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_ON_8);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + "macAddress/" + MSG_OPCODE_SWITCH_OFF_8);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });
        return view;
    }
}
