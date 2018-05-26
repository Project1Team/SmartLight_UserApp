
package com.uit.thonglee.smartlight_userapp.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uit.thonglee.smartlight_userapp.R;
import com.uit.thonglee.smartlight_userapp.models.Device;
import com.uit.thonglee.smartlight_userapp.models.Home;
import com.uit.thonglee.smartlight_userapp.utils.BlurBuilder;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity{
    public static Device device;
    public Toolbar toolbar;
    public TextView textView_name;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if(LoginActivity.STATUS==LoginActivity.CONECTED)
            toolbar.setSubtitle("Connected");
        else
            toolbar.setSubtitle("Error");
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return LoginActivity.user.getHome().size()+1;
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
                if(position != 0){
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.items_device_list, null, false);

                    final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(
                                    getBaseContext(), LinearLayoutManager.VERTICAL, false
                            )
                    );
                    recyclerView.setAdapter(new RecycleAdapter(position-1));

                }
                else {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile, null, false);
                    textView_name = view.findViewById(R.id.txtv_name);
                    textView_name.setText(LoginActivity.user.getName());
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
        for(Home home : LoginActivity.user.getHome()){
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
            devices = LoginActivity.user.getHome().get(index).getDevices();
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_device, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.textView_nameDevice.setText(devices.get(position).getName());
            holder.textView_macAddr.setText(devices.get(position).getMacAddr());
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

            public LinearLayout linearLayout_item;
            public TextView textView_nameDevice;
            public TextView textView_macAddr;

            public ViewHolder(final View itemView) {
                super(itemView);
                linearLayout_item = itemView.findViewById(R.id.item_device);
                textView_nameDevice = itemView.findViewById(R.id.txtv_name_device);
                textView_macAddr = itemView.findViewById(R.id.txtv_macAddr);
            }
        }
    }
}