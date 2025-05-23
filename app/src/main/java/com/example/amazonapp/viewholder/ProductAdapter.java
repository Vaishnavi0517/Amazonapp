package com.example.amazonapp.viewholder;

import android.app.admin.SecurityLog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.amazonapp.R;
import com.example.amazonapp.constant.Constant;
import com.example.amazonapp.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private List<Product> products = new ArrayList<Product>();

    private final Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void updateProducts(List<Product> products) {
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView productName, productPrice;
        ImageView productImage = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_product_adapter, parent, false);
            productName = convertView.findViewById(R.id.productName);
            productPrice = convertView.findViewById(R.id.productPrice);
            productImage = convertView.findViewById(R.id.productImage);
            convertView.setTag(new ViewHolder(productName, productPrice, productImage));
        } else {

            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            productName = viewHolder.productName;
            productPrice = viewHolder.productPrice;
            productImage = viewHolder.productImage;
        }

        final Product product = (Product) getItem(position);
        productName.setText(product.getpName());
        productPrice.setText(Constant.CURRENCY + String.valueOf(product.getpPrice().setScale(0, BigDecimal.ROUND_HALF_UP)));
        productImage.setImageResource(context.getResources().getIdentifier(product.getpImageName(),"drawable", context.getPackageName()));
        return convertView;

    }

    private static class ViewHolder{
        public  final TextView productName;
        public  final TextView productPrice;
        public final ImageView productImage;

        public ViewHolder(TextView productName, TextView productPrice, ImageView productImage){
            this.productName=productName;
            this.productPrice=productPrice;
            this.productImage=productImage;

        }

    }


}
