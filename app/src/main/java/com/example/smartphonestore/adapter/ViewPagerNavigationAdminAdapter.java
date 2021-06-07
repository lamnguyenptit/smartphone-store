package com.example.smartphonestore.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartphonestore.fragment.HomeAdminFragment;
import com.example.smartphonestore.fragment.OrderAdminFragment;
import com.example.smartphonestore.fragment.SettingAdminFragment;
import com.example.smartphonestore.model.User;

public class ViewPagerNavigationAdminAdapter extends FragmentStatePagerAdapter {
    public ViewPagerNavigationAdminAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return new OrderAdminFragment();
            case 2: return new SettingAdminFragment();
            default: return new HomeAdminFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
