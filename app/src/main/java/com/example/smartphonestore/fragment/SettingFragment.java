package com.example.smartphonestore.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartphonestore.LoginActivity;
import com.example.smartphonestore.MainActivity;
import com.example.smartphonestore.R;
import com.example.smartphonestore.model.User;

public class SettingFragment extends Fragment {
    private TextView tvUsername;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private String[] list = {"Setting", "Change password", "Help and support", "Logout"};
    private View view;
    private User user;
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        tvUsername = view.findViewById(R.id.tvUsername);
        listView = view.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        listView.setAdapter(arrayAdapter);

        mainActivity = (MainActivity) getActivity();
        tvUsername.setText(mainActivity.getUser().getUsername());


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 3:
                        startActivity(new Intent(view.getContext(), LoginActivity.class));
                        break;
                }
            }
        });
        return view;
    }
}