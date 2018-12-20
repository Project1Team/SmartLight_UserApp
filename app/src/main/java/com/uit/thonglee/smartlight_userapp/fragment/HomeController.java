package com.uit.thonglee.smartlight_userapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uit.thonglee.smartlight_userapp.R;
import com.suke.widget.SwitchButton;
import com.uit.thonglee.smartlight_userapp.activities.LoginActivity;
import com.uit.thonglee.smartlight_userapp.utils.UserConverter;


public class HomeController extends Fragment {

    public static final String MSG_OPCODE_SWITCH_OFF_11 = "010";
    public static final String MSG_OPCODE_SWITCH_OFF_12 = "020";
    public static final String MSG_OPCODE_SWITCH_OFF_13 = "030";
    public static final String MSG_OPCODE_SWITCH_OFF_14 = "040";

    public static final String MSG_OPCODE_SWITCH_ON_11 = "011";
    public static final String MSG_OPCODE_SWITCH_ON_12 = "021";
    public static final String MSG_OPCODE_SWITCH_ON_13 = "031";
    public static final String MSG_OPCODE_SWITCH_ON_14 = "041";

    public static final String MSG_OPCODE_SWITCH_OFF_21 = "110";
    public static final String MSG_OPCODE_SWITCH_OFF_22 = "120";
    public static final String MSG_OPCODE_SWITCH_OFF_23 = "130";
    public static final String MSG_OPCODE_SWITCH_OFF_24 = "140";

    public static final String MSG_OPCODE_SWITCH_ON_21 = "111";
    public static final String MSG_OPCODE_SWITCH_ON_22 = "121";
    public static final String MSG_OPCODE_SWITCH_ON_23 = "131";
    public static final String MSG_OPCODE_SWITCH_ON_24 = "141";

    public static String mac_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_control, container, false);

        TextView textView_temperature = view.findViewById(R.id.txtv_temperature);
        TextView textView_celsius = view.findViewById(R.id.txtv_celsius);
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

        mac_address = LoginActivity.user.getHomes().get(0).getMacAddr();

        if (Integer.parseInt(UserConverter.getTeamperatureValue(mac_address)) > 30 ) {
            textView_temperature.setText(UserConverter.getTeamperatureValue(mac_address));
            textView_temperature.setTextColor(getResources().getColor(R.color.hot_temp_color));
            textView_celsius.setTextColor(getResources().getColor(R.color.hot_temp_color));
        }
        else {
            textView_temperature.setText(UserConverter.getTeamperatureValue(mac_address));
        }
        if (UserConverter.getGasStatus(mac_address).equals("SOS")) {
            textView_gas.setText(UserConverter.getGasStatus(mac_address));
            textView_gas.setTextColor(Color.RED);
        }
        else {
            textView_gas.setText(UserConverter.getGasStatus(mac_address));
            textView_gas.setTextColor(getResources().getColor(R.color.safe_color));
        }

        if (UserConverter.getFireStatus(mac_address).equals("SOS")) {
            textView_fire.setText(UserConverter.getFireStatus(mac_address));
            textView_fire.setTextColor(Color.RED);
        }
        else {
            textView_fire.setText(UserConverter.getFireStatus(mac_address));
            textView_fire.setTextColor(getResources().getColor(R.color.safe_color));
        }

        textView_switch11.setText(UserConverter.getNameSwitch(mac_address, "1", 0));
        textView_switch12.setText(UserConverter.getNameSwitch(mac_address, "1", 1));
        textView_switch13.setText(UserConverter.getNameSwitch(mac_address, "1", 2));
        textView_switch14.setText(UserConverter.getNameSwitch(mac_address, "1", 3));

        textView_switch21.setText(UserConverter.getNameSwitch(mac_address, "2", 0));
        textView_switch22.setText(UserConverter.getNameSwitch(mac_address, "2", 1));
        textView_switch23.setText(UserConverter.getNameSwitch(mac_address, "2", 2));
        textView_switch24.setText(UserConverter.getNameSwitch(mac_address, "2", 3));

        switchButton_11.setChecked(UserConverter.getStatusSwitch(mac_address, "1", 0));
        switchButton_12.setChecked(UserConverter.getStatusSwitch(mac_address, "1", 1));
        switchButton_13.setChecked(UserConverter.getStatusSwitch(mac_address, "1", 2));
        switchButton_14.setChecked(UserConverter.getStatusSwitch(mac_address, "1", 3));

        switchButton_21.setChecked(UserConverter.getStatusSwitch(mac_address, "2", 0));
        switchButton_22.setChecked(UserConverter.getStatusSwitch(mac_address, "2", 1));
        switchButton_23.setChecked(UserConverter.getStatusSwitch(mac_address, "2", 2));
        switchButton_24.setChecked(UserConverter.getStatusSwitch(mac_address, "2", 3));

        switchButton_11.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked)
                {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_11);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_11);
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
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_12);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_12);
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
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_13);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_13);
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
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_14);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_14);
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
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_21);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_21);
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
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_22);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_22);
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
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_23);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_23);
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
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_ON_24);
                    }
                    catch (Exception e) {

                    }
                }
                else {
                    try {
                        LoginActivity.client.send("switch/" + mac_address + "/" + MSG_OPCODE_SWITCH_OFF_24);
                    }
                    catch (Exception e) {

                    }
                }
            }
        });
        return view;
    }
}
