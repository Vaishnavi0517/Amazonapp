package com.example.amazonapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.model.Category;
import com.example.amazonapp.model.Product;
import com.example.amazonapp.model.Products;
import com.example.amazonapp.viewholder.CategoryAdapter;
import com.example.amazonapp.viewholder.ProductAdapter;
import com.example.amazonapp.viewholder.ProductsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView categoriesRecyclerView, productsRecyclerView;
    CategoryAdapter categoryAdapter;
    ProductsAdapter productsAdapter;
    FirebaseDatabase database;

    private List<Category> categories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private ArrayList<String> Products;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home2);

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);

        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryAdapter = new CategoryAdapter(categories, this::loadProductsByCategory);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        productsAdapter = new ProductsAdapter(Products);
        productsRecyclerView.setAdapter(productsAdapter);

        database=FirebaseDatabase.getInstance();

        loadCategories();
    }

    private void loadCategories() {
        DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference("categories");

        categoriesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = snapshot.getValue(Category.class);
                    categories.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Log.e("FirebaseError", databaseError.getMessage());
            }
        });
    }

    private void loadProductsByCategory(String categoryId) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("products");

        productsRef.orderByChild("category").equalTo(categoryId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            products.add(product);
                        }
                        productsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle error
                        Log.e("FirebaseError", databaseError.getMessage());
                    }
                });
    }



}