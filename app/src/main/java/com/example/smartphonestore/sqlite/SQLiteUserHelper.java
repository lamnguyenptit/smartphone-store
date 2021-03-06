package com.example.smartphonestore.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.model.User;

public class SQLiteUserHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Store.db";
    public static final int DATABASE_VERSION = 9;

    public SQLiteUserHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "password TEXT," +
                "phone TEXT," +
                "address TEXT," +
                "is_admin BOOLEAN)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql0 = "DROP TABLE IF EXISTS cart";
//        db.execSQL(sql0);

    }

//    public long addUser(User user) {
//        ContentValues values = new ContentValues();
//        values.put("username", user.getUsername());
//        values.put("password", user.getPassword());
//        values.put("phone", user.getPhone());
//        values.put("address", user.getAddress());
//        values.put("is_admin", "true");
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        return sqLiteDatabase.insert("user",null, values);
//    }

    public void addUser(User user) {
        String sql = "INSERT INTO user(username, password, phone, address, is_admin) VALUES(?, ?, ?, ?, ?)";
        String[] args = {user.getUsername(), user.getPassword(), user.getPhone(), user.getAddress(), "false"};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }

    public User getUserById(int param) {
        SQLiteDatabase statement = getReadableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(param)};
        Cursor resultSet = statement.query("user", null, whereClause, whereArgs, null, null, null);
        if (resultSet.moveToNext()) {
            int id = resultSet.getInt(0);
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            String phone = resultSet.getString(3);
            String address = resultSet.getString(4);
            boolean isAdmin = resultSet.getString(5).equals("true");
            resultSet.close();
            return new User(id, username, password, phone, address, isAdmin);
        }
        return null;
    }

    public User getUserByUsername(String param) {
        SQLiteDatabase statement = getReadableDatabase();
        String whereClause = "username = ?";
        String[] whereArgs = {String.valueOf(param)};
        Cursor resultSet = statement.query("user", null, whereClause, whereArgs, null, null, null);
        if (resultSet.moveToNext()) {
            int id = resultSet.getInt(0);
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            String phone = resultSet.getString(3);
            String address = resultSet.getString(4);
            boolean isAdmin = resultSet.getString(5).equals("true");
            resultSet.close();
            return new User(id, username, password, phone, address, isAdmin);
        }
        return null;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        String whereClause = "username = ? AND password = ?";
        String[] whereArgs = {username, password};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("user", null, whereClause, whereArgs, null, null, null);
        if (resultSet.moveToNext()) {
            int id = resultSet.getInt(0);
            username = resultSet.getString(1);
            password = resultSet.getString(2);
            String phone = resultSet.getString(3);
            String address = resultSet.getString(4);
            boolean isAdmin = resultSet.getString(5).equals("true");
            resultSet.close();
            return new User(id, username, password, phone, address, isAdmin);
        }
        return null;
    }

    public int updateUser(User user){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("phone", user.getPhone());
        contentValues.put("address", user.getAddress());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(user.getId())};
        return statement.update("user", contentValues, whereClause, whereArgs);
    }

}
