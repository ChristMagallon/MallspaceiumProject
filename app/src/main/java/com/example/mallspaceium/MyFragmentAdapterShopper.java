package com.example.mallspaceium;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mallspaceium.fragments.Location;
import com.example.mallspaceium.fragments.Notification;
import com.example.mallspaceium.fragments.ShopperHomepage;
import com.example.mallspaceium.fragments.ShopperProfile;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

public class MyFragmentAdapterShopper extends FragmentStateAdapter {

    public MyFragmentAdapterShopper(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch(position){
            case 0:
                return new ShopperHomepage();
            case 1:
                return new Location();
            case 2:
                return new Notification();
            case 3:
                return new ShopperProfile();
            default:
                return new ShopperHomepage();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
