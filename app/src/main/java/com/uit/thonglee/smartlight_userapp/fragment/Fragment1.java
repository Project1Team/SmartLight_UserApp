package com.uit.thonglee.smartlight_userapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.activities.LoginActivity;


public class Fragment1 extends Fragment {
    public TextView textView_name;
    public Button button_logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        textView_name = view.findViewById(R.id.txtv_name);
        button_logout = view.findViewById(R.id.btn_logout);
        textView_name.setText(LoginActivity.user.getName());
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    LoginActivity.client.send("logout/"+LoginActivity.user.getId().toString());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                catch (Exception e){

                }
            }
        });

        return view;
    }
}