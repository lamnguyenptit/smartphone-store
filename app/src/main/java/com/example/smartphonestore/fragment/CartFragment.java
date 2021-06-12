package com.example.smartphonestore.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartphonestore.MainActivity;
import com.example.smartphonestore.R;
import com.example.smartphonestore.adapter.RecyclerViewCartAdapter;
import com.example.smartphonestore.model.Cart;
import com.example.smartphonestore.model.Item;
import com.example.smartphonestore.model.Order;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteCartHelper;
import com.example.smartphonestore.sqlite.SQLiteItemHelper;
import com.example.smartphonestore.sqlite.SQLiteOrderHelper;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;
import com.google.android.material.badge.BadgeDrawable;
import com.google.type.Color;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button btCheckOut;
    private TextView tvTotalCost;
    private View view;
    private RecyclerViewCartAdapter recyclerViewCartAdapter;
    private SQLiteItemHelper sqLiteItemHelper;
    private SQLiteCartHelper sqLiteCartHelper;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;
    private SQLiteOrderHelper sqLiteOrderHelper;
    private List<Item> items;
    private User user;
    private Cart cart;
    private Smartphone smartphone;
    private MainActivity mainActivity;
    private double totalCost;
    private int quantity;
    private Order order;
    private BadgeDrawable badge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView();

        mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();
        badge = mainActivity.getBadge();

        sqLiteItemHelper = new SQLiteItemHelper(view.getContext());
        sqLiteCartHelper = new SQLiteCartHelper(view.getContext());
        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(view.getContext());
        sqLiteOrderHelper = new SQLiteOrderHelper(view.getContext());

        cart = sqLiteCartHelper.getCartByUserId(user.getId());

        if (cart == null || cart.getTotalCost() == 0){
            tvTotalCost.setText("Cart is empty");
            btCheckOut.setEnabled(false);
            btCheckOut.setBackgroundColor(Color.RED_FIELD_NUMBER);
        } else {
            totalCost = 0;
            quantity = 0;
            items = sqLiteItemHelper.getItemByCartId(cart.getId());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerViewCartAdapter = new RecyclerViewCartAdapter(view.getContext());
            recyclerViewCartAdapter.setItems(items);
            recyclerViewCartAdapter.setBadge(badge);
            recyclerView.setAdapter(recyclerViewCartAdapter);

            for (Item item : items) {
                smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
                totalCost += smartphone.getPrice() * item.getQuantity();
                quantity += item.getQuantity();
            }
            cart.setTotalCost(totalCost);
            sqLiteCartHelper.updateCart(cart);
            tvTotalCost.setText("Total cost: $" + totalCost);
        }

        try {
            recyclerViewCartAdapter.setTvTotalCost(tvTotalCost);
        } catch (Exception e){
            System.out.println(e);
        }

        if (quantity > 0) {
            badge.setVisible(true);
            badge.setNumber(quantity);
        }
        else
            badge.setVisible(false);

        btCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.clear();
                recyclerViewCartAdapter.setItems(items);
                items = sqLiteItemHelper.getItemByCartId(cart.getId());
                cart = sqLiteCartHelper.getCartByUserId(user.getId());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                order = new Order(cart.getTotalCost(), sdf.format(date), user.getId());
                sqLiteOrderHelper.addOrder(order);
                List<Order> orders = new ArrayList<>();
                orders = sqLiteOrderHelper.getAllOrderByUserId(user.getId());
                order = orders.get(orders.size() - 1);
                sqLiteCartHelper.updateCart(new Cart(0));
                for (Item item : items) {
                    item.setCartId(0);
                    item.setOrderId(order.getId());
                    sqLiteItemHelper.updateItemCartIdAndOrderId(item);
                }
            }
        });

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        btCheckOut = view.findViewById(R.id.btCheckOut);
        tvTotalCost = view.findViewById(R.id.tvTotalCost);
    }
}