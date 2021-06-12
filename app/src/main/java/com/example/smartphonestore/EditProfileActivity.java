package com.example.smartphonestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteUserHelper;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etUsername, etPhone, etAddress;
    private Button btUpdate;
    private SQLiteUserHelper sqLiteUserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initView();
        Intent intent = getIntent();
        sqLiteUserHelper = new SQLiteUserHelper(getApplicationContext());
        User user = (User) intent.getSerializableExtra("user");

        etUsername.setText(user.getUsername());
        etPhone.setText(user.getPhone());
        etAddress.setText(user.getAddress());

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUsername(etUsername.getText().toString());
                user.setPhone(etPhone.getText().toString());
                user.setAddress(etAddress.getText().toString());
                if (sqLiteUserHelper.getUserByUsername(user.getUsername()) == null){
                    sqLiteUserHelper.updateUser(user);
                    Toast.makeText(EditProfileActivity.this, "Update success !", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(EditProfileActivity.this, "This username has already existed !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        etUsername = findViewById(R.id.etUsername);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btUpdate = findViewById(R.id.btUpdate);
    }
}