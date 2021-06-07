package com.example.smartphonestore.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.smartphonestore.model.Cart;
import com.example.smartphonestore.model.Item;
import com.example.smartphonestore.model.Smartphone;

import java.util.ArrayList;
import java.util.List;

import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_NAME;
import static com.example.smartphonestore.sqlite.SQLiteUserHelper.DATABASE_VERSION;

public class SQLiteItemHelper extends SQLiteOpenHelper {
    public SQLiteItemHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE item(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quantity INTEGER," +
                "smartphone_id INTEGER," +
                "order_id INTEGER," +
                "cart_id INTEGER," +
                "FOREIGN KEY (smartphone_id) REFERENCES smartphone (id)," +
                "FOREIGN KEY (order_id) REFERENCES tbl_order (id)," +
                "FOREIGN KEY (cart_id) REFERENCES cart (id));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addItem(Item item){
        SQLiteDatabase statement = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", item.getQuantity());
        contentValues.put("smartphone_id", item.getSmartphoneId());
        contentValues.put("order_id", item.getOrderId());
        contentValues.put("cart_id", item.getCartId());
        return statement.insert("item", null, contentValues);
    }

    public Item getItemByCartIdAndSmartphoneId(int cartId, int smartphoneId){
        String sql = "SELECT item.* FROM cart, item, smartphone WHERE item.cart_id = cart.id AND item.smartphone_id = smartphone.id AND item.cart_id = ? AND item.smartphone_id = ?";
        String[] args = {String.valueOf(cartId), String.valueOf(smartphoneId)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.rawQuery(sql, args);
        if (resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            int quantity = resultSet.getInt(1);
            int smartphone_id = resultSet.getInt(2);
            int order_id = resultSet.getInt(3);
            int cart_id = resultSet.getInt(4);
            resultSet.close();
            return new Item(id, quantity, smartphone_id, order_id, cart_id);
        }
        return null;
    }

    public List<Item> getItemByCartId(int cartId){
        List<Item> items = new ArrayList<>();
        String sql = "SELECT item.* FROM cart, item WHERE item.cart_id = cart.id  AND cart_id = ?";
        String[] args = {String.valueOf(cartId)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.rawQuery(sql, args);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            int quantity = resultSet.getInt(1);
            int smartphone_id = resultSet.getInt(2);
            int order_id = resultSet.getInt(3);
            int cart_id = resultSet.getInt(4);
            items.add(new Item(id, quantity, smartphone_id, order_id, cart_id));
        }
        resultSet.close();
        return items;
    }

    public List<Item> getItemByOrderId(int orderId){
        List<Item> items = new ArrayList<>();
        String sql = "SELECT item.* FROM tbl_order, item WHERE item.order_id = tbl_order.id  AND order_id = ?";
        String[] args = {String.valueOf(orderId)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.rawQuery(sql, args);
        while (resultSet != null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            int quantity = resultSet.getInt(1);
            int smartphone_id = resultSet.getInt(2);
            int order_id = resultSet.getInt(3);
            int cart_id = resultSet.getInt(4);
            items.add(new Item(id, quantity, smartphone_id, order_id, cart_id));
        }
        resultSet.close();
        return items;
    }

    public Item getItemById(int id){
        String sql = "SELECT * FROM item WHERE id = ?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.rawQuery(sql, args);
        if (resultSet.moveToNext()){
            int quantity = resultSet.getInt(1);
            int smartphone_id = resultSet.getInt(2);
            int order_id = resultSet.getInt(3);
            int cart_id = resultSet.getInt(4);
            resultSet.close();
            return new Item(id, quantity, smartphone_id, order_id, cart_id);
        }
        return null;
    }

    public int updateItemQuantity(Item item){
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", item.getQuantity());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(item.getId())};
        SQLiteDatabase statement = getWritableDatabase();
        return statement.update("item", contentValues, whereClause, whereArgs);
    }

    public int deleteItemById(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase statement = getWritableDatabase();
        return statement.delete("item", whereClause, whereArgs);
    }

    public int updateItemCartIdAndOrderId(Item item){
        ContentValues contentValues = new ContentValues();
        contentValues.put("cart_id", item.getCartId());
        contentValues.put("order_id", item.getOrderId());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(item.getId())};
        SQLiteDatabase statement = getWritableDatabase();
        return statement.update("item", contentValues, whereClause, whereArgs);
    }

//    public Item getItemBySmartphoneId(int param){
//        String sql = "SELECT item.* FROM smartphone, item WHERE item.smartphone_id = smartphone.id AND smartphone_id = ?";
//        String[] args = {String.valueOf(param)};
//        SQLiteDatabase statement = getReadableDatabase();
//        Cursor resultSet = statement.rawQuery(sql, args);
//        if (resultSet.moveToNext()){
//            int id = resultSet.getInt(0);
//            int quantity = resultSet.getInt(1);
//            int smartphone_id = resultSet.getInt(2);
//            int order_id = resultSet.getInt(3);
//            int cart_id = resultSet.getInt(4);
//            resultSet.close();
//            return new Item(id, quantity, smartphone_id, order_id, cart_id);
//        }
//        return null;
//    }
}
