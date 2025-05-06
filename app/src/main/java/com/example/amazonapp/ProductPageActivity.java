package com.example.amazonapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.MenuFiles.AddProductActivity;
import com.example.amazonapp.MenuFiles.BaseActivity;
import com.example.amazonapp.model.Review;
import com.example.amazonapp.viewholder.ReviewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductPageActivity extends AppCompatActivity {

    RecyclerView reviewsRecyclerView;
    ReviewAdapter reviewAdapter;
    List<Review> reviews;
    FirebaseDatabase db;
    String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviews);
        reviewsRecyclerView.setAdapter(reviewAdapter);

        db = FirebaseDatabase.getInstance();
        productId = getIntent().getStringExtra("productId");

        loadReviews();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadReviews() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("products");

        dbRef.child(productId)
                .child("reviews")
                .push()
                .get()
                .addOnSuccessListener(dataSnapshot -> {
                    reviews.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Review review = snapshot.getValue(Review.class);
                        if (review != null){
                            reviews.add(review);
                        }
                    }
                    reviewAdapter.notifyDataSetChanged();
                })

                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load reviews: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(ProductPageActivity.this, HomeActivity.class);
                    startActivity(intent);

                });
    }


}