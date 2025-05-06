package com.example.amazonapp.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.Interfaces.ItemClickListener;
import com.example.amazonapp.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView orderName, orderDate, orderAddress, orderCity, orderPrice, orderPhone;
    private ItemClickListener itemClickListener;
    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        orderName = itemView.findViewById(R.id.orderName);
        orderPhone = itemView.findViewById(R.id.orderPhone);
        orderAddress = itemView.findViewById(R.id.orderAddress);
        orderCity = itemView.findViewById(R.id.orderCity);
        orderDate = itemView.findViewById(R.id.orderDate);
        orderPrice = itemView.findViewById(R.id.orderPrice);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
