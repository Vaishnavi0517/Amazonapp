package com.example.amazonapp;

import static com.example.amazonapp.R.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import com.example.amazonapp.MenuFiles.BaseActivity;
import com.example.amazonapp.MenuFiles.CartActivity;
import com.example.amazonapp.MenuFiles.SearchActivity;
import com.example.amazonapp.constant.Constant;
import com.example.amazonapp.model.Category;
import com.example.amazonapp.model.Product;
import com.example.amazonapp.model.Products;
import com.example.amazonapp.viewholder.CategoryAdapter;
import com.example.amazonapp.viewholder.ProductAdapter;
import com.example.amazonapp.viewholder.ProductsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class HomeActivity extends BaseActivity {

    Toolbar toolbar;

    CardView mobile1, mobile2, mobile3, mobile4;

    TextView oddmobilename, evenmobileprice, evenmobilename, viewAll;

    LinearLayout dynamicContent;
    LinearLayout bottomNavBar;

   public static ImageView home_cart;

   Intent intentcart;
   String getcartupdate;

   FirebaseStorage storage;
   StorageReference storageReference;

    private List<Category> categories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_home);


        dynamicContent = findViewById(id.dynamicContent);
        bottomNavBar = findViewById(id.bottomNavBar);
        View wizard = getLayoutInflater().inflate(layout.activity_home,null);
        dynamicContent.addView(wizard);

        RadioGroup rg = findViewById(id.radioGroup1);
        RadioButton rb = findViewById(id.bottom_home);

        rb.setBackgroundColor(color.item_selected);
        rb.setTextColor(Color.parseColor("#3F5185"));

        Toolbar toolbar = findViewById(id.toolbar);

        toolbar.setBackgroundResource(drawable.bg_color);

        mobile1 = findViewById(id.mobile1);
        mobile2 = findViewById(id.mobile2);
        mobile3 = findViewById(id.mobile3);
        mobile4 = findViewById(id.mobile4);

        oddmobilename = findViewById(id.oddmobilename);
        evenmobilename = findViewById(id.evenmobilename);
        evenmobileprice = findViewById(id.evenmobileprice);

        viewAll = findViewById(id.viewAllProducts);
        home_cart = findViewById(id.home_cart);

        intentcart = getIntent();
        if (intentcart.getStringExtra("cartadd")!=null && intentcart.getStringExtra("cartadd").equals("yes")){
            home_cart.setImageResource(drawable.cart_notify);
        } else if (intentcart.getStringExtra("cartadd")!=null && intentcart.getStringExtra("cartadd").equals("no")) {
            home_cart.setImageResource(drawable.cart);

        }else {
            home_cart.setImageResource(drawable.cart);
        }

        storage = FirebaseStorage.getInstance();


        mobile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProductDetails.class);
                i.putExtra("name",oddmobilename.getText().toString());
                i.putExtra("category","SmartPhones");
                i.putExtra("price",evenmobileprice.getText().toString());
                i.putExtra("uniqueId",oddmobilename.getText().toString());
                i.putExtra("id",1);
                startActivity(i);

            }
        });


        mobile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProductDetails.class);
                i.putExtra("name",evenmobilename.getText().toString());
                i.putExtra("category","SmartPhones");
                i.putExtra("price",evenmobileprice.getText().toString());
                i.putExtra("uniqueId",evenmobilename.getText().toString());
                i.putExtra("id",2);
                startActivity(i);

            }
        });

        mobile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProductDetails.class);
                i.putExtra("name",oddmobilename.getText().toString());
                i.putExtra("category","SmartPhones");
                i.putExtra("price",evenmobileprice.getText().toString());
                i.putExtra("uniqueId",oddmobilename.getText().toString());
                i.putExtra("id",3);
                startActivity(i);


            }
        });

        mobile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProductDetails.class);
                i.putExtra("name",evenmobilename.getText().toString());
                i.putExtra("category","SmartPhones");
                i.putExtra("price",evenmobileprice.getText().toString());
                i.putExtra("uniqueId",evenmobilename.getText().toString());
                i.putExtra("id",4);
                startActivity(i);

            }
        });

        ListView lvProducts = findViewById(id.productslist);

        ProductAdapter productAdapter = new ProductAdapter(this);
        productAdapter.updateProducts(Constant.PRODUCT_LIST);

        lvProducts.setAdapter(productAdapter);

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product product = Constant.PRODUCT_LIST.get(position);
                Intent intent = new Intent(HomeActivity.this, ProductDetails.class);
                intent.putExtra("id",3);
                intent.putExtra("uniqueId",product.getpName());
                intent.putExtra("name",product.getpName());
                intent.putExtra("description",product.getpDescription());
                intent.putExtra("category","smartwatches");
                intent.putExtra("price",Constant.CURRENCY+ String.valueOf(product.getpPrice()
                        .setScale(0, BigDecimal.ROUND_HALF_UP)));
                intent.putExtra("imageName",product.getpImageName());
                startActivity(intent);

            }


        });

        home_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });


    }




    private void addingToProductList(){
        String saveCurrentDate, saveCurrentTime;

        Calendar  calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        DatabaseReference productListRef = FirebaseDatabase.getInstance().getReference().child("View All");
        String name = "Fireboltt";

        final HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",name);
        productMap.put("name",name);
        productMap.put("price","₹1,049");
        productMap.put("category","SmartWatches");
        productMap.put("description","Legend Bluetooth \nCalling Smartwatch with\n Dual Button Technology");
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);

        productListRef.child("User View").child("Products")
                .child(name).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.i("Task","Successful");
                        }
                    }
                });

    }
}