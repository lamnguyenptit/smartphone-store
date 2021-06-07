package com.example.smartphonestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartphonestore.R;
import com.example.smartphonestore.model.Cart;
import com.example.smartphonestore.model.Item;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.model.User;
import com.example.smartphonestore.sqlite.SQLiteCartHelper;
import com.example.smartphonestore.sqlite.SQLiteItemHelper;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSmartphoneAdapter extends RecyclerView.Adapter<RecyclerViewSmartphoneAdapter.SmartphoneViewHoler> {
    private List<Smartphone> smartphones;
    private Context context;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;
    private SQLiteCartHelper sqLiteCartHelper;
    private SQLiteItemHelper sqLiteItemHelper;
    private User user;
    private Cart cart;
    private Item item;
    private int quantity;

    public RecyclerViewSmartphoneAdapter(Context context) {
        this.context = context;
        this.smartphones = new ArrayList<>();
        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(context);
        sqLiteCartHelper = new SQLiteCartHelper(context);
        sqLiteItemHelper = new SQLiteItemHelper(context);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSmartphones(List<Smartphone> smartphones) {
        this.smartphones = smartphones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SmartphoneViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SmartphoneViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout.smartphone_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSmartphoneAdapter.SmartphoneViewHoler holder, int position) {
        try {
            Picasso.get().load(smartphones.get(position).getImage()).fit().centerCrop().error(R.drawable.ic_error).into(holder.image);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.tvName.setText(smartphones.get(position).getName());
        holder.tvPrice.setText("$" + String.valueOf(smartphones.get(position).getPrice()));

        holder.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart = sqLiteCartHelper.getCartByUserId(user.getId());
                if (cart == null){
                    cart = new Cart(0, user.getId());
                    sqLiteCartHelper.addCart(cart);
                }
                item = sqLiteItemHelper.getItemByCartIdAndSmartphoneId(cart.getId(), smartphones.get(position).getId());
                if (item == null){
                    item = new Item(1, smartphones.get(position).getId(), cart.getId());
                    sqLiteItemHelper.addItem(item);
                }else {
                    quantity = item.getQuantity();
                    item.setQuantity(++quantity);
                    sqLiteItemHelper.updateItemQuantity(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (smartphones != null)
            return smartphones.size();
        return 0;
    }

    public class SmartphoneViewHoler extends RecyclerView.ViewHolder {
        private final ImageView image, ivCart;
        private final TextView tvName;
        private final TextView tvPrice;
        public SmartphoneViewHoler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivCart = itemView.findViewById(R.id.ivCart);
        }
    }
}
