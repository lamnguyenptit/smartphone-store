package com.example.smartphonestore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartphonestore.MainActivity;
import com.example.smartphonestore.R;
import com.example.smartphonestore.adapter.RecyclerViewSmartphoneAdapter;
import com.example.smartphonestore.adapter.RecyclerViewSmartphoneAdminAdapter;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;
import com.google.android.material.badge.BadgeDrawable;

import java.text.ParseException;
import java.util.List;

public class HomeFragment extends Fragment{
    private RecyclerView recyclerView;
    private View view;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;
    private RecyclerViewSmartphoneAdapter recyclerViewSmartphoneAdapter;
    private List<Smartphone> smartphones;
    private MainActivity mainActivity;
    private User user;
    private BadgeDrawable badge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUser();
        badge = mainActivity.getBadge();

        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(view.getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerViewSmartphoneAdapter = new RecyclerViewSmartphoneAdapter(view.getContext());
        recyclerViewSmartphoneAdapter.setSmartphones(getAll());
        recyclerViewSmartphoneAdapter.setUser(user);
        recyclerViewSmartphoneAdapter.setBadge(badge);
        recyclerView.setAdapter(recyclerViewSmartphoneAdapter);

        return view;
    }

    private List<Smartphone> getAll() {
        return sqLiteSmartphoneHelper.getAll();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search smartphone by name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                smartphones = sqLiteSmartphoneHelper.getSmartphoneByItemName(newText);
                recyclerViewSmartphoneAdapter.setSmartphones(smartphones);
                recyclerView.setAdapter(recyclerViewSmartphoneAdapter);
                return true;
            }
        });
    }
}