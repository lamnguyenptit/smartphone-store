package com.example.smartphonestore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartphonestore.R;
import com.example.smartphonestore.UpdateActivity;
import com.example.smartphonestore.model.Smartphone;
import com.example.smartphonestore.sqlite.SQLiteSmartphoneHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSmartphoneAdminAdapter extends RecyclerView.Adapter<RecyclerViewSmartphoneAdminAdapter.SmartphoneViewHolder> {
    private List<Smartphone> smartphones;
    private Context context;
    private SQLiteSmartphoneHelper sqLiteSmartphoneHelper;

    public RecyclerViewSmartphoneAdminAdapter(Context context){
        this.context = context;
        this.smartphones = new ArrayList<>();
        sqLiteSmartphoneHelper = new SQLiteSmartphoneHelper(context);
    }

    public void setSmartphones(List<Smartphone> smartphones) {
        this.smartphones = smartphones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SmartphoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SmartphoneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.smartphone_card_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewSmartphoneAdminAdapter.SmartphoneViewHolder holder, int position) {
        try {
            Picasso.get().load(smartphones.get(position).getImage()).fit().centerCrop().error(R.drawable.ic_error).into(holder.image);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.tvResult.setText(smartphones.get(position).toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                Smartphone smartphone = smartphones.get(position);
                intent.putExtra("smartphone", smartphone);
                context.startActivity(intent);
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = smartphones.get(position).getId();
                    sqLiteSmartphoneHelper.deleteSmartphone(id);
                    smartphones = sqLiteSmartphoneHelper.getAll();
                    notifyDataSetChanged();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
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

    public class SmartphoneViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView tvResult;
        private final Button btDelete;

        public SmartphoneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResult = itemView.findViewById(R.id.tvResult);
            btDelete = itemView.findViewById(R.id.btDelete);
            image = itemView.findViewById(R.id.image);
        }
    }

//    public void updateData(List<Smartphone> newList){
//        smartphones.clear();
//        if (newList!=null)
//            smartphones.addAll(newList);
//        notifyDataSetChanged();
//    }
}
