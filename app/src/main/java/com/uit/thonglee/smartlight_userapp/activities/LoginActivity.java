package com.uit.thonglee.smartlight_userapp.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.models.User;
import com.uit.thonglee.smartlight_userapp.utils.BlurBuilder;
import com.uit.thonglee.smartlight_userapp.utils.UserConverter;
import com.uit.thonglee.smartlight_userapp.view.CustomButton;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "Tag";
    public static int STATUS = 0;
    public static final int CONECTED = 1;
    public static final int DISCONECTED = 0;

    public static WebSocketClient client;
    public static User user;
    public  static String[] response;

    LinearLayout loginView;
    LinearLayout connectView;
    Toolbar toolbar;
    EditText editText_wss;
    EditText editText_wsp;
    CustomButton button_connect;
    EditText editText_username;
    EditText editText_password;
    CheckBox checkBox_remmember;
    Button button_login;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitialView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_connect:
//                DBObject userDBObject = (DBObject) JSON.parse("{ \"_id\" : { \"$oid\" : \"5ace1acc7f6beb0865a16383\"} , \"name\" : \"thao\" , \"password\" : \"111111\" , \"home\" : [ { \"name\" : \"home1\" , \"device\" : [ { \"name\" : \"light1\" , \"macAddr\" : \"11121212323\" , \"color\" : \"000000255255255\"} , { \"name\" : \"light2\" , \"macAddr\" : \"6546454535\" , \"color\" : \"000000255255255\"}]} , { \"name\" : \"home2\" , \"device\" : [ { \"name\" : \"light1\" , \"macAddr\" : \"121232323\" , \"color\" : \"000000255255255\"} , { \"name\" : \"light2\" , \"macAddr\" : \"3434354566\" , \"color\" : \"000000255255255\"}]} , { \"name\" : \"home3\" , \"device\" : [ { \"name\" : \"light1\" , \"macAddr\" : \"25621424\" , \"color\" : \"000000255255255\"} , { \"name\" : \"light2\" , \"macAddr\" : \"12312313\" , \"color\" : \"000000255255255\"}]}]}");
//                user = new User();
//                user = UserConverter.toUser(userDBObject);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                connectServer();
                break;
            case R.id.btn_login:
                if(STATUS == CONECTED){
                    try{
                        client.send("login/" + editText_username.getText().toString() +"@"+ editText_password.getText().toString());
                    }catch (Exception e){
                        Log.d(TAG, "Error: " + e.toString());
                    }
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void InitialView(){

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
        Bitmap blur_bitmap = BlurBuilder.blur(this, bm);
        this.getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), blur_bitmap));

        loginView = findViewById(R.id.loginview);
        connectView = findViewById(R.id.connect_view);
        toolbar = findViewById(R.id.toolbar_login);
        editText_wsp = findViewById(R.id.edtxt_wsp);
        editText_wss = findViewById(R.id.edtxt_wss);
        button_connect = findViewById(R.id.btn_connect);
        editText_username = findViewById(R.id.edt_username);
        editText_password = findViewById(R.id.edt_password);
        checkBox_remmember = findViewById(R.id.chb_rem);
        button_login = findViewById(R.id.btn_login);

        toolbar.setSubtitle("None");

        loginView.setVisibility(View.INVISIBLE);

        // set Text default fo ip, port (edit text)
        editText_wsp.setText(R.string.default_port);
        editText_wss.setText(R.string.default_ip);

        //set Click listener view
        button_login.setOnClickListener(this);
        button_connect.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
    public void connectServer(){
        if (STATUS == DISCONECTED){
            client = new WebSocketClient(URI.create("http://" + editText_wss.getText() + ":" + editText_wsp.getText())) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("TAG", "Connected");
                    STATUS = CONECTED;
                    connectView.post(new Runnable() {
                        @Override
                        public void run() {
                            toolbar.setSubtitle(R.string.toolbar_status_connected);
                            button_connect.setText(R.string.text_button_disconnect);
                            loginView.setVisibility(View.VISIBLE);
                        }
                    });
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    super.onMessage(bytes);
                }

                @Override
                public void onMessage(String message) {
                    Log.d(TAG, "server:" + message);
                    response = message.split("/");
                    switch (response[0]){
                        // login
                        case "login":
                            if(response[1].equals("1")){

                            }
                            else if(response[1].equals("2")){
                                connectView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "account have been used", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else {
                                connectView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "account wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            break;

                        // get User after login
                        case "getUser":
                            DBObject userDBObject = (DBObject) JSON.parse(response[1]);
                            user = new User();
                            user = UserConverter.toUser(userDBObject);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;

                        case "updateColor":
                            UserConverter.updateColor(response[1], response[2]);
                            break;

                        case "updateBrightness":
                            UserConverter.updateBrightness(response[1], response[2]);
                            break;

                        case "updateStatus":
                            UserConverter.updateStatus(response[1], response[2]);
                            break;

                        case "messageRes":
                            connectView.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, response[1], Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;

                        // get color for UI
                        case "getColor":
                            break;

                        // edit user profile
                        case "updateUser":
                            break;
                    }

                }
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d(TAG, "Disconnected");
                    STATUS = DISCONECTED;
                    connectView.post(new Runnable() {
                        @Override
                        public void run() {
                            toolbar.setSubtitle(R.string.toolbar_status_disconnect);
                            button_connect.setText(R.string.text_button_connect);
                            loginView.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                @Override
                public void onError(Exception ex) {
                    Log.e(TAG, "onError: "+ ex);
                    connectView.post(new Runnable() {
                        @Override
                        public void run() {
                            toolbar.setSubtitle(R.string.toolbar_status_Error);
                            loginView.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            };
            client.connect();
        }
        else{
            client.close();
        }
    }
}
