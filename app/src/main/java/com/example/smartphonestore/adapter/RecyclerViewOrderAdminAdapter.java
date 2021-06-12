package com.example.smartphonestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartphonestore.R;
import com.example.smartphonestore.model.Item;
import com.example.smartphonestore.model.Order;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteItemHelper;
import com.example.smartphonestore.sqlite.SQLiteOrderHelper;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;
import com.example.smartphonestore.sqlite.SQLiteUserHelper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewOrderAdminAdapter extends RecyclerView.Adapter<RecyclerViewOrderAdminAdapter.OrderViewHolder> {
    private Context context;
    private List<Order> orders;
    private List<Item> items;
    private List<Smartphone> smartphones;
    private SQLiteItemHelper sqLiteItemHelper;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;
    private SQLiteOrderHelper sqLiteOrderHelper;
    private Smartphone smartphone;
    private User user;
    SQLiteUserHelper sqLiteUserHelper;

    public RecyclerViewOrderAdminAdapter(Context context) {
        this.context = context;
        sqLiteItemHelper = new SQLiteItemHelper(context);
        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(context);
        sqLiteOrderHelper = new SQLiteOrderHelper(context);
        sqLiteUserHelper = new SQLiteUserHelper(context);
        items = new ArrayList<>();
        smartphones = new ArrayList<>();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewOrderAdminAdapter.OrderViewHolder holder, int position) {
        user = sqLiteUserHelper.getUserById(orders.get(position).getUserId());
        String strOrder = "ID: " + orders.get(position).getId() + "\n";
        strOrder += "Customer: " + user.getUsername() + "\nPhone: " + user.getPhone() + "\nAddress: " + user.getAddress() + "\n";
        items = sqLiteItemHelper.getItemByOrderId(orders.get(position).getId());
        for (Item item : items) {
            smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
            strOrder += "\t" + item.getQuantity() + " x "+ smartphone.getName() +": $"+ smartphone.getPrice() + "\n";
            smartphones.add(smartphone);
        }
        strOrder += "Total cost: $" + orders.get(position).getTotalCost();
        if (orders.get(position).isDone())
            strOrder += "\nStatus: Done";
        else strOrder += "\nStatus: Processing";
        holder.tvOrder.setText(strOrder);

        if (orders.get(position).isDone())
            holder.cb.setChecked(true);

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.cb.isChecked())
                    orders.get(position).setDone(true);
                    sqLiteOrderHelper.updateOrder(orders.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orders != null)
            return orders.size();
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvOrder;
        private final CheckBox cb;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrder = itemView.findViewById(R.id.tvOrder);
            cb = itemView.findViewById(R.id.cb);
        }
    }
}
