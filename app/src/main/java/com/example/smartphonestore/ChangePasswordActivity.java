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

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText etOldPassword, etNewPassword, etConfirmPassword;
    private TextView tvResult;
    private Button btUpdate;
    private SQLiteUserHelper sqLiteUserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
        Intent intent = getIntent();
        sqLiteUserHelper = new SQLiteUserHelper(getApplicationContext());
        User user = (User) intent.getSerializableExtra("user");

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getPassword() != etOldPassword.getText().toString())
                    tvResult.setText("Wrong old password");
                else if (etNewPassword != etConfirmPassword)
                    tvResult.setText("New password does not match");
                else {
                    user.setPassword(etNewPassword.getText().toString());
                    sqLiteUserHelper.updateUser(user);
                    Toast.makeText(ChangePasswordActivity.this, "Change password success !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void initView() {
        etOldPassword = findViewById(R.id.etOldPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btUpdate = findViewById(R.id.btUpdate);
        tvResult = findViewById(R.id.tvResult);
    }
}