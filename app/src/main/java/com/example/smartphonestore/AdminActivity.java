package com.example.smartphonestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smartphonestore.adapter.RecyclerViewSmartphoneAdminAdapter;
import com.example.smartphonestore.adapter.ViewPagerNavigationAdminAdapter;
import com.example.smartphonestore.fragment.HomeAdminFragment;
import com.example.smartphonestore.fragment.OrderAdminFragment;
import com.example.smartphonestore.fragment.SettingAdminFragment;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private ViewPagerNavigationAdminAdapter viewPagerNavigationAdminAdapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        viewPagerNavigationAdminAdapter = new ViewPagerNavigationAdminAdapter(getSupportFragmentManager(), ViewPagerNavigationAdminAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerNavigationAdminAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_admin: viewPager.setCurrentItem(0);
                        break;
                    case R.id.order_admin: viewPager.setCurrentItem(1);
                        break;
                    case R.id.setting_admin: viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.order_admin).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.setting_admin).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.home_admin).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public User getUser() {
        return user;
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPager);
    }
}