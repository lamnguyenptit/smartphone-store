package com.example.smartphonestore.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.smartphonestore.model.Order;
import com.example.smartphonestore.model.Smartphone;

import java.util.ArrayList;
import java.util.List;

import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_NAME;
import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_VERSION;

public class SQLiteOrderHelper extends SQLiteOpenHelper {
    public SQLiteOrderHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tbl_order(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "total_cost REAL," +
                "date TEXT," +
                "is_done BOOLEAN," +
                "user_id INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES user (id));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addOrder(Order order){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("total_cost", order.getTotalCost());
        contentValues.put("date", order.getDate());
        contentValues.put("is_done", "false");
        contentValues.put("user_id", order.getUserId());
        return statement.insert("tbl_order", null, contentValues);
    }

    public List<Order> getAllOrder() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase statement = getWritableDatabase();
        Cursor resultSet = statement.query("tbl_order", null, null, null, null, null, null);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            double total_cost = resultSet.getDouble(1);
            String date = resultSet.getString(2);
            boolean is_done = resultSet.getString(3).equals("true");
            int user_id = resultSet.getInt(4);
            orders.add(new Order(id, total_cost, date, is_done, user_id));
        }
        resultSet.close();
        return orders;
    }

    public List<Order> getAllOrderByUserId(int param) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        String whereClause = "user_id = ?";
        String[] whereArgs = {String.valueOf(param)};
        Cursor resultSet = statement.query("tbl_order", null, whereClause, whereArgs, null, null, null);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            double total_cost = resultSet.getDouble(1);
            String date = resultSet.getString(2);
            boolean is_done = resultSet.getString(3).equals("true");
            int user_id = resultSet.getInt(4);
            orders.add(new Order(id, total_cost, date, is_done, user_id));
        }
        resultSet.close();
        return orders;
    }

    public int updateOrder(Order order){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_done", String.valueOf(order.isDone()));
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(order.getId())};
        return statement.update("tbl_order", contentValues, whereClause, whereArgs);
    }

    public int deleteOrderById(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase statement = getWritableDatabase();
        return statement.delete("tbl_order", whereClause, whereArgs);
    }
}
