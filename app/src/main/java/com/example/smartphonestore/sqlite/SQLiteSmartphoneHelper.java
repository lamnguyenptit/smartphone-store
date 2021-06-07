package com.example.smartphonestore.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.smartphonestore.model.Smartphone;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_NAME;
import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_VERSION;

public class SQLiteSmartphoneHelper extends SQLiteOpenHelper {
    public SQLiteSmartphoneHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE smartphone(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "brand TEXT," +
                "chipset TEXT," +
                "ram TEXT," +
                "price REAL," +
                "image TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addSmartphone(Smartphone smartphone){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", smartphone.getName());
        contentValues.put("brand", smartphone.getBrand());
        contentValues.put("chipset", smartphone.getChipset());
        contentValues.put("ram", smartphone.getRam());
        contentValues.put("price", smartphone.getPrice());
        contentValues.put("image", smartphone.getImage());
        return statement.insert("smartphone", null, contentValues);
    }

    public int updateSmartphone(Smartphone smartphone){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", smartphone.getName());
        contentValues.put("brand", smartphone.getBrand());
        contentValues.put("chipset", smartphone.getChipset());
        contentValues.put("ram", smartphone.getRam());
        contentValues.put("price", smartphone.getPrice());
        contentValues.put("image", smartphone.getImage());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(smartphone.getId())};
        return statement.update("smartphone", contentValues, whereClause, whereArgs);
    }

    public int deleteSmartphone(int id){
        SQLiteDatabase statement = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        return statement.delete("smartphone", whereClause, whereArgs);
    }

    public List<Smartphone> getSmartphoneByItemName(String param) {
        List<Smartphone> smartphones = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        String whereClause = "name LIKE ?";
        String[] whereArgs = {"%" + param + "%"};
        Cursor resultSet = statement.query("smartphone", null, whereClause, whereArgs, null, null, null);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            String name = resultSet.getString(1);
            String brand = resultSet.getString(2);
            String chipset = resultSet.getString(3);
            String ram = resultSet.getString(4);
            double price = Double.parseDouble(String.valueOf(resultSet.getDouble(5)));
            String image = resultSet.getString(6);
            smartphones.add(new Smartphone(id, name, brand, chipset, ram, price, image));
        }
        resultSet.close();
        return smartphones;
    }

    public Smartphone getSmartphoneById(int id) {
        SQLiteDatabase statement = getReadableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor resultSet = statement.query("smartphone", null, whereClause, whereArgs, null, null, null);
        if (resultSet.moveToNext()){
            String name = resultSet.getString(1);
            String brand = resultSet.getString(2);
            String chipset = resultSet.getString(3);
            String ram = resultSet.getString(4);
            double price = Double.parseDouble(String.valueOf(resultSet.getDouble(5)));
            String image = resultSet.getString(6);
            resultSet.close();
            return new Smartphone(id, name, brand, chipset, ram, price, image);
        }
        return null;
    }

    public List<Smartphone> getAll() {
        List<Smartphone> smartphones = new ArrayList<>();
        SQLiteDatabase statement = getWritableDatabase();
        Cursor resultSet = statement.query("smartphone", null, null, null, null, null, null);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            String name = resultSet.getString(1);
            String brand = resultSet.getString(2);
            String chipset = resultSet.getString(3);
            String ram = resultSet.getString(4);
            double price = Double.parseDouble(String.valueOf(resultSet.getDouble(5)));
            String image = resultSet.getString(6);
            smartphones.add(new Smartphone(id, name, brand, chipset, ram, price, image));
        }
        resultSet.close();
        return smartphones;
    }
}
