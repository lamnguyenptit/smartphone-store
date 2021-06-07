package com.example.smartphonestore.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartphonestore.fragment.CartFragment;
import com.example.smartphonestore.fragment.HomeFragment;

import com.example.smartphonestore.fragment.OrderFragment;
import com.example.smartphonestore.fragment.SettingFragment;

public class ViewPagerNavigationAdapter extends FragmentStatePagerAdapter {
    public ViewPagerNavigationAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return new CartFragment();
            case 2: return new OrderFragment();
            case 3: return new SettingFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
