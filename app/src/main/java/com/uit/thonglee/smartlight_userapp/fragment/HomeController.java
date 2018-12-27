package com.uit.thonglee.smartlight_userapp.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uit.thonglee.smartlight_userapp.R;
import com.suke.widget.SwitchButton;
import com.uit.thonglee.smartlight_userapp.activities.AlertFireActivity;
import com.uit.thonglee.smartlight_userapp.activities.AlertGasActivity;
import com.uit.thonglee.smartlight_userapp.activities.Call114Activity;
import com.uit.thonglee.smartlight_userapp.activities.ClearAlarmActivity;
import com.uit.thonglee.smartlight_userapp.activities.LoginActivity;
import com.uit.thonglee.smartlight_userapp.models.Device;
import com.uit.thonglee.smartlight_userapp.models.Fire;
import com.uit.thonglee.smartlight_userapp.models.Gas;
import com.uit.thonglee.smartlight_userapp.models.Temperature;
import com.uit.thonglee.smartlight_userapp.utils.UserConverter;

import java.io.IOException;
import java.util.List;


public class HomeController extends Fragment {

    public static final String RESET_STATUS = "resetStatus";

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

    public TextView textView_temperature;
    public TextView textView_celsius;
    public TextView textView_gas;
    public TextView textView_fire;
    public ImageButton button_resetFire;
    public ImageButton button_resetGas;

    public static NotificationManagerCompat notificationManager;
    public NotificationCompat.Builder mBuilder;
    public NotificationCompat.Builder mBuilder1;
    public static MediaPlayer mMediaPlayer;
    public Vibrator vibrator;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_control, container, false);

        textView_temperature = view.findViewById(R.id.txtv_temperature);
        textView_celsius = view.findViewById(R.id.txtv_celsius);
        textView_gas = view.findViewById(R.id.txtv_gas);
        textView_fire = view.findViewById(R.id.txtv_fire);

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

        button_resetFire = view.findViewById(R.id.btn_resetFire);
        button_resetGas = view.findViewById(R.id.btn_resetGas);

        mac_address = LoginActivity.user.getHomes().get(0).getMacAddr();

        createNotification();

        if (Integer.parseInt(UserConverter.getTeamperatureValue(mac_address)) > 30 ) {
            textView_temperature.setText(UserConverter.getTeamperatureValue(mac_address));
            textView_temperature.setTextColor(getResources().getColor(R.color.hot_temp_color));
            textView_celsius.setTextColor(getResources().getColor(R.color.hot_temp_color));
        }
        else {
            textView_temperature.setText(UserConverter.getTeamperatureValue(mac_address));
            textView_temperature.setTextColor(getResources().getColor(R.color.defaut_temp));
            textView_celsius.setTextColor(getResources().getColor(R.color.defaut_temp));
        }
        if (UserConverter.getGasStatus(mac_address).equals("DETECTED")) {
            textView_gas.setText(UserConverter.getGasStatus(mac_address));
            textView_gas.setTextColor(Color.RED);
            notificationManager.notify(1, mBuilder1.build());
            if(mMediaPlayer.isPlaying() == false){
                mMediaPlayer.start();
            }
            vibrator.vibrate(1000);
            button_resetGas.setVisibility(View.VISIBLE);
        }
        else {
            textView_gas.setText(UserConverter.getGasStatus(mac_address));
            textView_gas.setTextColor(getResources().getColor(R.color.safe_color));
        }

        if (UserConverter.getFireStatus(mac_address).equals("DETECTED")) {
            textView_fire.setText(UserConverter.getFireStatus(mac_address));
            textView_fire.setTextColor(Color.RED);
            // notification
            notificationManager.notify(0, mBuilder.build());
            if(mMediaPlayer.isPlaying() == false){
                mMediaPlayer.start();
            }
            vibrator.vibrate(1000);
            button_resetFire.setVisibility(View.VISIBLE);
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

        button_resetFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.client.send("resetFire/" + LoginActivity.user.getHomes().get(0).getMacAddr());
            }
        });
        button_resetGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.client.send("resetGas/" + LoginActivity.user.getHomes().get(0).getMacAddr());
            }
        });
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

        new updateUI().execute();
        return view;
    }
    private class updateUI extends AsyncTask<Void, String, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String string_temp = (String) textView_temperature.getText();
            String colorTeamperate = String.valueOf(textView_temperature.getCurrentTextColor());
            String string_fire = (String) textView_fire.getText();
            String string_gas = (String) textView_gas.getText();
            String statusButtonResetFire = String.valueOf(button_resetFire.getVisibility());
            String statusButtonResetGas = String.valueOf(button_resetGas.getVisibility());
            String colorFire = String.valueOf(textView_fire.getCurrentTextColor());
            String colorGas = String.valueOf(textView_gas.getCurrentTextColor());
            while(true){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Device> devices = LoginActivity.user.getHomes().get(0).getDevices();
                for(Device device : devices){
                    if(device.getType().equals("temperature")){
                        Temperature temperature = (Temperature) device;
                        if(!temperature.getValue().equals(string_temp)) {
                            string_temp = temperature.getValue();
                            if(Integer.parseInt(string_temp) > 30){
                                colorTeamperate = String.valueOf(getResources().getColor(R.color.hot_temp_color));
                            }
                            else{
                                colorTeamperate = String.valueOf(getResources().getColor(R.color.defaut_temp));
                            }
                        }
                    }
                    else if(device.getType().equals("fire")){
                        Fire fire = (Fire) device;
                        if(!fire.getStatus().equals(string_fire)){
                            string_fire = fire.getStatus();
                            if(fire.getStatus().equals("DETECTED")){
                                statusButtonResetFire = String.valueOf(View.VISIBLE);
                                colorFire = String.valueOf(Color.RED);
                                // notification
                                notificationManager.notify(0, mBuilder.build());
                                vibrator.vibrate(1000);
                                if(!mMediaPlayer.isPlaying())
                                    mMediaPlayer.start();
                            }
                            else{
                                statusButtonResetFire = String.valueOf(View.INVISIBLE);
                                colorFire = String.valueOf(getResources().getColor(R.color.safe_color));
                                notificationManager.cancel(0);
                                if(mMediaPlayer.isPlaying()){
                                    mMediaPlayer.stop();
                                    try {
                                        mMediaPlayer.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    else if(device.getType().equals("gas")){
                        Gas gas = (Gas) device;
                        if(!gas.getStatus().equals(string_gas)){
                            string_gas = gas.getStatus();
                            if(gas.getStatus().equals("DETECTED")){
                                statusButtonResetGas = String.valueOf(View.VISIBLE);
                                colorGas = String.valueOf(Color.RED);
                                // notification
                                notificationManager.notify(1, mBuilder1.build());
                                vibrator.vibrate(1000);
                                if(!mMediaPlayer.isPlaying())
                                    mMediaPlayer.start();
                            }
                            else{
                                statusButtonResetGas = String.valueOf(View.INVISIBLE);
                                colorGas = String.valueOf(getResources().getColor(R.color.safe_color));
                                notificationManager.cancel(1);
                                if(mMediaPlayer.isPlaying()){
                                    mMediaPlayer.stop();
                                    try {
                                        mMediaPlayer.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                publishProgress(string_temp, colorTeamperate, string_fire, statusButtonResetFire, colorFire, string_gas, statusButtonResetGas, colorGas);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textView_temperature.setText(values[0]);
            textView_temperature.setTextColor(Integer.parseInt(values[1]));
            textView_celsius.setTextColor(Integer.parseInt(values[1]));

            textView_fire.setText(values[2]);
            button_resetFire.setVisibility(Integer.parseInt(values[3]));
            textView_fire.setTextColor(Integer.parseInt(values[4]));

            textView_gas.setText(values[5]);
            button_resetGas.setVisibility(Integer.parseInt(values[6]));
            textView_gas.setTextColor(Integer.parseInt(values[7]));
        }
    }
    private void createNotification(){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this.getContext(), AlertFireActivity.class);
        Intent intent1 = new Intent(this.getContext(), AlertGasActivity.class);
        intent.putExtra(RESET_STATUS, "fire");
        intent1.putExtra(RESET_STATUS, "gas");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent clearAlarmIntent = new Intent(this.getContext(), ClearAlarmActivity.class);
        Intent call114Intent = new Intent(this.getContext(), Call114Activity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this.getContext(), 0, intent, 0);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this.getContext(), 0, intent1, 0);
        PendingIntent clearAlarmPending = PendingIntent.getActivity(this.getContext(), 0, clearAlarmIntent, 0);
        PendingIntent call114Pending = PendingIntent.getActivity(this.getContext(), 0, call114Intent, 0);


        // Vibrator
        vibrator = (Vibrator) this.getActivity().getSystemService(this.getContext().VIBRATOR_SERVICE);
        // Alert notification
        Uri ringTone = Uri.parse("android.resource://com.uit.thonglee.smartlight_userapp/" + R.raw.alert_ringing);
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(this.getContext(), ringTone);
            final AudioManager audioManager = (AudioManager) this.getActivity().getSystemService(this.getContext().AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // create notification for fire
        mBuilder = new NotificationCompat.Builder(this.getContext(), "1")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.icon_flame)
                .setContentTitle("FIRE")
                .setContentText("Fire detected! Your house is ON FIRE!")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(0, "CALL 114", call114Pending)
                .addAction(0, "DISABLE SOUND", clearAlarmPending)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        // create notification for gas
        mBuilder1 = new NotificationCompat.Builder(this.getContext(), "1")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.icon_gas)
                .setContentTitle("GAS")
                .setContentText("Gas detected! Your house is ON GAS!")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(0, "CALL 114", call114Pending)
                .addAction(0, "DISABLE SOUND", clearAlarmPending)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent1)
                .setAutoCancel(true);

        notificationManager = NotificationManagerCompat.from(this.getContext());
    }

}
