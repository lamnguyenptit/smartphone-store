package com.example.smartphonestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartphonestore.adapter.RecyclerViewSmartphoneAdminAdapter;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;

public class UpdateActivity extends AppCompatActivity {
    private Button btUpdate;
    private EditText etId, etName, etBrand, etChipset, etRam, etPrice, etImage;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initView();

        Intent intent = getIntent();
        Smartphone smartphone = (Smartphone) intent.getSerializableExtra("smartphone");
        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(getApplicationContext());

        etId.setText(String.valueOf(smartphone.getId()));
        etName.setText(smartphone.getName());
        etBrand.setText(smartphone.getBrand());
        etChipset.setText(smartphone.getChipset());
        etRam.setText(smartphone.getRam());
        etPrice.setText(String.valueOf(smartphone.getPrice()));
        etImage.setText(smartphone.getImage());

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(etId.getText().toString());
                    String name = etName.getText().toString();
                    String brand = etBrand.getText().toString();
                    String chipset = etChipset.getText().toString();
                    String ram = etRam.getText().toString();
                    double price = Double.parseDouble(etPrice.getText().toString());
                    String image = etImage.getText().toString();
                    sqLiteSmartphoneHelper.updateSmartphone(new Smartphone(id, name, brand, chipset, ram, price, image));
                    Toast.makeText(UpdateActivity.this, "Update success !", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        btUpdate = findViewById(R.id.btUpdate);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etBrand = findViewById(R.id.etBrand);
        etChipset = findViewById(R.id.etChipset);
        etRam = findViewById(R.id.etRam);
        etPrice = findViewById(R.id.etPrice);
        etImage = findViewById(R.id.etImage);
    }
}