package com.example.amazonapp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.Interfaces.ItemClickListener;
import com.example.amazonapp.R;

public class ViewProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView addProductName, addProductPrice;
    private ItemClickListener itemClickListener;
    public ImageView addproductImg;

    public ViewProductHolder(@NonNull View itemView) {
        super(itemView);

        addProductName = itemView.findViewById(R.id.productName);
        addProductPrice = itemView.findViewById(R.id.productPrice);
        addproductImg = itemView.findViewById(R.id.productImage);
    }

    @Override
    public void onClick(View v) {


        itemClickListener.onClick(v, getAbsoluteAdapterPosition(),false);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
