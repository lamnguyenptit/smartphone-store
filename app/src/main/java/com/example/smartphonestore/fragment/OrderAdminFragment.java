package com.example.smartphonestore.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartphonestore.AdminActivity;
import com.example.smartphonestore.MainActivity;
import com.example.smartphonestore.R;
import com.example.smartphonestore.adapter.RecyclerViewOrderAdapter;
import com.example.smartphonestore.adapter.RecyclerViewOrderAdminAdapter;
import com.example.smartphonestore.model.Order;
import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteOrderHelper;

import java.util.List;

public class OrderAdminFragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private AdminActivity adminActivity;
    private User user;
    private RecyclerViewOrderAdminAdapter recyclerViewOrderAdminAdapter;
    private SQLiteOrderHelper sqLiteOrderHelper;
    private List<Order> orders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_admin, container, false);
        sqLiteOrderHelper = new SQLiteOrderHelper(view.getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        adminActivity = (AdminActivity) getActivity();
        user = adminActivity.getUser();

        orders = sqLiteOrderHelper.getAllOrder();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewOrderAdminAdapter = new RecyclerViewOrderAdminAdapter(view.getContext());
        recyclerViewOrderAdminAdapter.setOrders(orders);
        recyclerView.setAdapter(recyclerViewOrderAdminAdapter);

        return view;
    }
}