package com.example.smartphonestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.smartphonestore.adapter.ViewPagerNavigationAdapter;
import com.example.smartphonestore.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private ViewPagerNavigationAdapter viewPagerNavigationAdapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        viewPagerNavigationAdapter = new ViewPagerNavigationAdapter(getSupportFragmentManager(), ViewPagerNavigationAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerNavigationAdapter);
        viewPager.setOffscreenPageLimit(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home: viewPager.setCurrentItem(0);
                        break;
                    case R.id.cart: viewPager.setCurrentItem(1);
                        break;
                    case R.id.order: viewPager.setCurrentItem(2);
                        break;
                    case R.id.setting: viewPager.setCurrentItem(3);
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
                        bottomNavigationView.getMenu().findItem(R.id.cart).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.order).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.setting).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
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