package com.example.smartphonestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteUserHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etPhone, etAddress;
    private Button btRegister;
    private SQLiteUserHelper sqLiteUserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        sqLiteUserHelper = new SQLiteUserHelper(getApplicationContext());

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setPhone(etPhone.getText().toString());
                user.setAddress(etAddress.getText().toString());
                sqLiteUserHelper.addUser(user);
                Toast.makeText(RegisterActivity.this, "Register success !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btRegister = findViewById(R.id.btRegister);
    }
}