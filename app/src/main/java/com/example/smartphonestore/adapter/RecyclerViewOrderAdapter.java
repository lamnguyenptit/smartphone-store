package com.example.smartphonestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartphonestore.R;
import com.example.smartphonestore.model.Item;
import com.example.smartphonestore.model.Order;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.sqlite.SQLiteItemHelper;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewOrderAdapter extends RecyclerView.Adapter<RecyclerViewOrderAdapter.OrderViewHolder>{
    private Context context;
    private List<Order> orders;
    private List<Item> items;
    private List<Smartphone> smartphones;
    private SQLiteItemHelper sqLiteItemHelper;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;
    private Smartphone smartphone;

    public RecyclerViewOrderAdapter(Context context) {
        this.context = context;
        sqLiteItemHelper = new SQLiteItemHelper(context);
        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(context);
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
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewOrderAdapter.OrderViewHolder holder, int position) {
        String strOrder = "ID: " + orders.get(position).getId() + "\n";
        items = sqLiteItemHelper.getItemByOrderId(orders.get(position).getId());
        for (Item item : items) {
            smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
            strOrder += + item.getQuantity() + " x "+ smartphone.getName() +": $"+ smartphone.getPrice() + "\n";
            smartphones.add(smartphone);
        }
        strOrder += "Total cost: $" + orders.get(position).getTotalCost();
        if (orders.get(position).isDone())
            strOrder += "\nStatus: Done";
        else strOrder += "\nStatus: Processing";
        holder.tvOrder.setText(strOrder);
    }

    @Override
    public int getItemCount() {
        if (orders != null)
            return orders.size();
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvOrder;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrder = itemView.findViewById(R.id.tvOrder);
        }
    }
}
