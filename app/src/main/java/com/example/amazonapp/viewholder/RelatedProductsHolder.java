package com.example.amazonapp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.Interfaces.ItemClickListener;
import com.example.amazonapp.R;

public class RelatedProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView relatedProdName, relatedProdPrice;
    public ImageView relatedProdImg;
    private ItemClickListener itemClickListener;

    public RelatedProductsHolder(@NonNull View itemView) {
        super(itemView);

        relatedProdName = itemView.findViewById(R.id.relatedProdName);
        relatedProdPrice = itemView.findViewById(R.id.relatedProdPrice);
        relatedProdImg = itemView.findViewById(R.id.relatedProdImg);
    }

    @Override
    public void onClick(View v) {


        itemClickListener.onClick(v, getAdapterPosition(),false );

    }
}
