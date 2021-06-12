package com.example.smartphonestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartphonestore.R;
import com.example.smartphonestore.model.Cart;
import com.example.smartphonestore.model.Item;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.sqlite.SQLiteCartHelper;
import com.example.smartphonestore.sqlite.SQLiteItemHelper;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;
import com.google.android.material.badge.BadgeDrawable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCartAdapter  extends RecyclerView.Adapter<RecyclerViewCartAdapter.CartViewHolder>{
    private Context context;
    private List<Item> items;
    private Smartphone smartphone;
    private Item item;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;
    private SQLiteItemHelper sqLiteItemHelper;
    private SQLiteCartHelper sqLiteCartHelper;
    private Cart cart;
    private double totalCost;
    private TextView tvTotalCost;
    private BadgeDrawable badge;

    public RecyclerViewCartAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(context);
        sqLiteItemHelper = new SQLiteItemHelper(context);
        sqLiteCartHelper = new SQLiteCartHelper(context);
    }

    public void setBadge(BadgeDrawable badge) {
        this.badge = badge;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setTvTotalCost(TextView tvTotalCost) {
        this.tvTotalCost = tvTotalCost;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        totalCost = 0;
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCartAdapter.CartViewHolder holder, int position) {
        item = items.get(position);
        int cartId = item.getCartId();
        cart = sqLiteCartHelper.getCartById(cartId);
        smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
        holder.tvName.setText(smartphone.getName());
        holder.tvPrice.setText(String.valueOf(smartphone.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        try {
            Picasso.get().load(smartphone.getImage()).fit().centerCrop().error(R.drawable.ic_error).into(holder.image);
        }catch (Exception e){
            e.printStackTrace();
        }

        for (Item item : items) {
            smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
            totalCost += smartphone.getPrice() * item.getQuantity();
        }
        cart.setTotalCost(totalCost);
        sqLiteCartHelper.updateCart(cart);

        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = items.get(position);
                int quantity = item.getQuantity();
                if (quantity > 1){
                    item.setQuantity(--quantity);
                    sqLiteItemHelper.updateItemQuantity(item);
                    holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                    smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
                    totalCost = totalCost - smartphone.getPrice();
                    tvTotalCost.setText("Total cost: $"+ totalCost);
                }
                else {
                    smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
                    sqLiteItemHelper.deleteItemById(item.getId());
                    items = sqLiteItemHelper.getItemByCartId(cartId);
                    totalCost = totalCost - smartphone.getPrice();
                    tvTotalCost.setText("Total cost: $" + totalCost);
                    notifyDataSetChanged();
                }

                if (badge.isVisible()){
                    quantity = 0;
                    List<Item> items;
                    items = sqLiteItemHelper.getItemByCartId(cart.getId());
                    for (Item item : items) {
                        quantity += item.getQuantity();
                    }
                    if (quantity > 0) {
                        badge.setVisible(true);
                        badge.setNumber(quantity);
                    }
                    else
                        badge.setVisible(false);
                }
            }
        });

        holder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = items.get(position);
                int quantity = item.getQuantity();
                item.setQuantity(++quantity);
                sqLiteItemHelper.updateItemQuantity(item);
                holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                smartphone = sqLiteSmartphoneHelper.getSmartphoneById(item.getSmartphoneId());
                totalCost += smartphone.getPrice();
                tvTotalCost.setText("Total cost: $" + totalCost);

                if (badge.isVisible()){
                    quantity = 0;
                    List<Item> items;
                    items = sqLiteItemHelper.getItemByCartId(cart.getId());
                    for (Item item : items) {
                        quantity += item.getQuantity();
                    }
                    if (quantity > 0) {
                        badge.setVisible(true);
                        badge.setNumber(quantity);
                    }
                    else
                        badge.setVisible(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvPrice, tvQuantity;
        private final ImageView image;
        private final ImageButton ivMinus, ivPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            image = itemView.findViewById(R.id.image);
            ivMinus = itemView.findViewById(R.id.ivMinus);
            ivPlus = itemView.findViewById(R.id.ivPlus);
        }
    }
}
