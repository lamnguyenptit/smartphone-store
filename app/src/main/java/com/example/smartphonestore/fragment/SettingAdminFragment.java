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

import com.example.smartphonestore.AdminActivity;
import com.example.smartphonestore.ChangePasswordActivity;
import com.example.smartphonestore.EditProfileActivity;
import com.example.smartphonestore.LoginActivity;
import com.example.smartphonestore.MainActivity;
import com.example.smartphonestore.R;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.model.User;


public class SettingAdminFragment extends Fragment {
    private TextView tvUsername;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private String[] list = {"Edit profile", "Change password", "Help and support", "Logout"};
    private View view;
    private User user;
    private AdminActivity adminActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_admin, container, false);
        tvUsername = view.findViewById(R.id.tvUsername);
        listView = view.findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        listView.setAdapter(arrayAdapter);

        adminActivity = (AdminActivity) getActivity();
        user = adminActivity.getUser();
        tvUsername.setText(user.getUsername());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(view.getContext(), EditProfileActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(view.getContext(), ChangePasswordActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        break;
                    case 3:
                        startActivity(new Intent(view.getContext(), LoginActivity.class));
                        break;
                }
            }
        });
        return view;
    }
}