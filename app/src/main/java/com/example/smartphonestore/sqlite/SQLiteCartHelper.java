package com.example.smartphonestore.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.smartphonestore.model.Cart;
import com.example.smartphonestore.model.Smartphone;

import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_NAME;
import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_VERSION;

public class SQLiteCartHelper extends SQLiteOpenHelper {

    public SQLiteCartHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE cart(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total_cost REAL," +
                "user_id INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES user (id));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCart(Cart cart){
        String sql = "INSERT INTO cart(total_cost, user_id) VALUES(?, ?)";
        String[] args = {String.valueOf(cart.getTotalCost()), String.valueOf(cart.getUserId())};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }

    public Cart getCartByUserId(int param){
        String sql = "SELECT cart.* FROM cart, user WHERE cart.user_id = user.id AND user_id = ?";
        String[] args = {String.valueOf(param)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.rawQuery(sql, args);
        if (resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            double totalCost = resultSet.getDouble(1);
            int userId = resultSet.getInt(2);
            resultSet.close();
            return new Cart(id, totalCost, userId);
        }
        return null;
    }

    public Cart getCartById(int param){
        String sql = "SELECT * FROM cart WHERE id =  ?";
        String[] args = {String.valueOf(param)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.rawQuery(sql, args);
        if (resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            double totalCost = resultSet.getDouble(1);
            int userId = resultSet.getInt(2);
            resultSet.close();
            return new Cart(id, totalCost, userId);
        }
        return null;
    }

    public int updateCart(Cart cart){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("total_cost", cart.getTotalCost());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(cart.getId())};
        return statement.update("cart", contentValues, whereClause, whereArgs);
    }

    public int removeCartItem(Cart cart){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("total_cost", cart.getTotalCost());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(cart.getId())};
        return statement.update("cart", contentValues, whereClause, whereArgs);
    }
}
