package com.example.smartphonestore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.smartphonestore.AdminActivity;
import com.example.smartphonestore.R;
import com.example.smartphonestore.adapter.RecyclerViewSmartphoneAdminAdapter;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;

import java.text.ParseException;
import java.util.List;

public class HomeAdminFragment extends Fragment {
    private Button btAdd, btGetAll;
    private EditText etName, etBrand, etChipset, etRam, etPrice, etImage;
    private RecyclerView recyclerView;
    private RecyclerViewSmartphoneAdminAdapter recyclerViewSmartphoneAdminAdapter;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;
    private List<Smartphone> smartphones;
    private View view;

    public HomeAdminFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_admin, container, false);
        btAdd = view.findViewById(R.id.btAdd);
        btGetAll = view.findViewById(R.id.btGetAll);
        etName = view.findViewById(R.id.etName);
        etBrand = view.findViewById(R.id.etBrand);
        etChipset = view.findViewById(R.id.etChipset);
        etRam = view.findViewById(R.id.etRam);
        etPrice = view.findViewById(R.id.etPrice);
        etImage = view.findViewById(R.id.etImage);
        recyclerView = view.findViewById(R.id.recyclerView);

        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewSmartphoneAdminAdapter = new RecyclerViewSmartphoneAdminAdapter(view.getContext());
        smartphones = sqLiteSmartphoneHelper.getAll();
        recyclerViewSmartphoneAdminAdapter.setSmartphones(smartphones);
        recyclerView.setAdapter(recyclerViewSmartphoneAdminAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String brand = etBrand.getText().toString();
                String chipset = etChipset.getText().toString();
                String ram = etRam.getText().toString();
                double price = Double.parseDouble(etPrice.getText().toString());
                String image = etImage.getText().toString();
                Smartphone smartphone = new Smartphone(name, brand, chipset, ram, price, image);
                sqLiteSmartphoneHelper.addSmartphone(smartphone);
                smartphones = sqLiteSmartphoneHelper.getAll();
                recyclerViewSmartphoneAdminAdapter.setSmartphones(smartphones);

                etName.setText("");
                etBrand.setText("");
                etChipset.setText("");
                etRam.setText("");
                etPrice.setText("");
                etImage.setText("");
            }
        });

        btGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Smartphone> smartphones = null;
                smartphones = sqLiteSmartphoneHelper.getAll();
                recyclerViewSmartphoneAdminAdapter.setSmartphones(smartphones);
                recyclerView.setAdapter(recyclerViewSmartphoneAdminAdapter);
            }
        });

        return view;
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
                recyclerViewSmartphoneAdminAdapter.setSmartphones(smartphones);
                recyclerView.setAdapter(recyclerViewSmartphoneAdminAdapter);
                return true;
            }
        });
    }
}