package com.example.smartphonestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.smartphonestore.fragment.CartFragment;
import com.example.smartphonestore.fragment.HomeFragment;
import com.example.smartphonestore.fragment.OrderFragment;
import com.example.smartphonestore.fragment.SettingFragment;
import com.example.smartphonestore.model.Cart;
import com.example.smartphonestore.model.Item;
import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteCartHelper;
import com.example.smartphonestore.sqlite.SQLiteItemHelper;
import com.example.smartphonestore.sqlite.SQLiteOrderHelper;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private User user;
    private BadgeDrawable badge;
    private List<Item> items;
    private SQLiteItemHelper sqLiteItemHelper;
    private SQLiteCartHelper sqLiteCartHelper;
    private Cart cart;
    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        quantity = 0;

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

//        viewPagerNavigationAdapter = new ViewPagerNavigationAdapter(getSupportFragmentManager(), ViewPagerNavigationAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPager.setAdapter(viewPagerNavigationAdapter);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        badge = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badge.setVisible(false);

        sqLiteItemHelper = new SQLiteItemHelper(this);
        sqLiteCartHelper = new SQLiteCartHelper(this);
        cart = sqLiteCartHelper.getCartByUserId(user.getId());
        if (cart != null){
            items = sqLiteItemHelper.getItemByCartId(cart.getId());
            for (Item item : items) {
                quantity += item.getQuantity();
            }
            if (quantity > 0) {
                badge.setVisible(true);
                badge.setNumber(quantity);
            }
            else
                badge.setVisible(false);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.home: selectedFragment = new HomeFragment();
                        break;
                    case R.id.cart: selectedFragment = new CartFragment();
                        break;
                    case R.id.order: selectedFragment = new OrderFragment();
                        break;
                    case R.id.setting: selectedFragment = new SettingFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
    }

    public User getUser() {
        return user;
    }

    public BadgeDrawable getBadge() {
        return badge;
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.navigation);
    }
}