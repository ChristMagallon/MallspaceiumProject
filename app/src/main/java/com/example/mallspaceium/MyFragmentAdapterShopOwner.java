package com.example.mallspaceium;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mallspaceium.fragments.Location;
import com.example.mallspaceium.fragments.Notification;
import com.example.mallspaceium.fragments.ShopOwnerHomepage;
import com.example.mallspaceium.fragments.ShopOwnerProfile;
import com.example.mallspaceium.fragments.ShopperProfile;

public class MyFragmentAdapterShopOwner extends FragmentStateAdapter {

    public MyFragmentAdapterShopOwner(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch(position){
            case 0:
                return new ShopOwnerHomepage();
            case 1:
                return new Location();
            case 2:
                return new Notification();
            case 3:
                return new ShopOwnerProfile();
            default:
                return new ShopOwnerHomepage();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
