
package com.uit.thonglee.smartlight_userapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.models.Device;
import com.uit.thonglee.smartlight_userapp.models.Room;
import com.uit.thonglee.smartlight_userapp.utils.Utils;
import com.uit.thonglee.smartlight_userapp.view.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity{
    public static Device device;
    public Toolbar toolbar;

    public TextView textView_name;
    public Button button_logout;

    @Override
    protected void onResume() {
        super.onResume();
        initUI();

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if(LoginActivity.STATUS==LoginActivity.CONNECTED)
            toolbar.setSubtitle("Connected");
        else
            toolbar.setSubtitle("Error");
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return LoginActivity.user.getRooms().size()+1;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view;
                if (position != 0 && position != 1){
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.unavailable_room, null, false);
                }
                else if (position != 1) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile, null, false);
                    textView_name = view.findViewById(R.id.txtv_name);
                    button_logout = view.findViewById(R.id.btn_logout);
                    textView_name.setText(LoginActivity.user.getName());
                    button_logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try{
                                LoginActivity.client.send("logout/"+LoginActivity.user.getId().toString());
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            catch (Exception e){

                            }
                        }
                    });
                } else
                {
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.home_control, null, false);
                }

                container.addView(view);
                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_action_name),
                        Color.parseColor(colors[0]))
                        .title("Profile")
                        .build()
        );
        int i = 1;
        for(Room home : LoginActivity.user.getRooms()){
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_action_room),
                            Color.parseColor(colors[i++]))
                            .title(home.getName().toString())
                            .build()
            );
        }


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
        public int index;
        public List<Device> devices;

        public RecycleAdapter(int index) {
            this.index = index;
            devices = LoginActivity.user.getRooms().get(index).getDevices();
            Log.d("IninialRecycle","--------------------------------------");
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_device, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("Range")
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            String color = devices.get(position).getColor();
            holder.textView_nameDevice.setText(devices.get(position).getName());
            holder.textView_macAddr.setText(devices.get(position).getMacAddr());
            Log.d("SetColor", color);
            holder.linearLayout_item.setBackgroundColor(Color.rgb(Utils.getRed(color), Utils.getGreen(color), Utils.getBlue(color)));
            if (devices.get(position).getStatus()==1)
                holder.toggleButton_status.setToggleOn();
            else
                holder.toggleButton_status.setToggleOff();

            holder.toggleButton_status.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
                @Override
                public void onToggle(boolean on) {
                    if(on) {
                        try {
                            holder.linearLayout_item.setEnabled(true);
                            LoginActivity.client.send("turnOn/" + devices.get(position).getMacAddr());
                        } catch (Exception e) {}
                    }
                    else {
                        try {
                            holder.linearLayout_item.setEnabled(false);
                            LoginActivity.client.send("turnOff/" + devices.get(position).getMacAddr());
                        } catch (Exception e) {}
                    }
                }
            });
            holder.linearLayout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    device = devices.get(position);
                    Intent intent = new Intent(MainActivity.this, ControlActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout linearLayout_item_bg;
            public LinearLayout linearLayout_item;
            public TextView textView_nameDevice;
            public TextView textView_macAddr;
            public ToggleButton toggleButton_status;

            public ViewHolder(final View itemView) {
                super(itemView);
                linearLayout_item_bg = itemView.findViewById(R.id.item_device_bg);
                linearLayout_item = itemView.findViewById(R.id.item_device);
                textView_nameDevice = itemView.findViewById(R.id.txtv_name_device);
                textView_macAddr = itemView.findViewById(R.id.txtv_macAddr);
                toggleButton_status = itemView.findViewById(R.id.togbtn_status);
            }
        }
    }
}
