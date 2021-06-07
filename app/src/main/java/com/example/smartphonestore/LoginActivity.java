package com.example.smartphonestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteUserHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private Button btLogin;
    private TextView tvRegister;
    SQLiteUserHelper sqLiteUserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        sqLiteUserHelper = new SQLiteUserHelper(getApplicationContext());
        etUsername.setText("lam");
        etPassword.setText("123456");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void initView() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void checkLogin(String username, String password) {
        User user = sqLiteUserHelper.getStudentByUsernameAndPassword(username, password);
        if (user != null){
            Intent intent;
            if (user.isAdmin()){
                Toast.makeText(LoginActivity.this, "Login success !", Toast.LENGTH_SHORT).show();
                intent = new Intent(LoginActivity.this, AdminActivity.class);
            }
            else {
                Toast.makeText(LoginActivity.this, "Login success !", Toast.LENGTH_SHORT).show();
                intent = new Intent(LoginActivity.this, MainActivity.class);
            }
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Wrong username or password !", Toast.LENGTH_SHORT).show();
        }
    }
}