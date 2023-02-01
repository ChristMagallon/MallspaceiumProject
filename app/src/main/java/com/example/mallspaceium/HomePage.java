package com.example.mallspaceium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class HomePage extends AppCompatActivity {

    private TabLayout HomePageTabLayout;
    private ViewPager2 ViewPager2;
    private MyFragmentAdapterShopper adapterShopper;
    private MyFragmentAdapterShopOwner adapterShopOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        HomePageTabLayout = findViewById(R.id.HomePageTabLayout);
        ViewPager2 = findViewById(R.id.ViewPager2);

        String session_user_role = getIntent().getStringExtra("user_role");

        if(session_user_role.equals("Shopper")){
            adapterShopper = new MyFragmentAdapterShopper(this);
            ViewPager2.setAdapter(adapterShopper);
        }else if(session_user_role.equals("ShopOwner")){
            adapterShopOwner = new MyFragmentAdapterShopOwner(this);
            ViewPager2.setAdapter(adapterShopOwner);
        }

        HomePageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                HomePageTabLayout.getTabAt(position).select();;
            }
        });
    }
}