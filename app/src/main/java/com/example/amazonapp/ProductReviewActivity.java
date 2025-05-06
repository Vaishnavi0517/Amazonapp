package com.example.amazonapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.amazonapp.MenuFiles.AddProductActivity;
import com.example.amazonapp.MenuFiles.BaseActivity;
import com.example.amazonapp.model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductReviewActivity extends AppCompatActivity {

     RatingBar ratingBar;
     EditText reviewEditText;
     Button submitReviewButton;
     FirebaseDatabase db;
     String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review);

        ratingBar = findViewById(R.id.ratingbar);
        reviewEditText = findViewById(R.id.reviewEditText);
        submitReviewButton = findViewById(R.id.submitReviewButton);

        db = FirebaseDatabase.getInstance();
        productId = getIntent().getStringExtra("productId");

        submitReviewButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String reviewText = reviewEditText.getText().toString().trim();

            if (rating == 0 || reviewText.isEmpty()) {
                Toast.makeText(this, "Please provide a rating and review", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            saveReview(new Review(userId, reviewText, rating));
        });
    }

    private void saveReview(Review review) {
        DatabaseReference dbRef =FirebaseDatabase.getInstance().getReference("products");
        dbRef.child(productId)
                .child("reviews")
                .push()
                .setValue(review)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Review submitted successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(ProductReviewActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to submit review:  " +e.getMessage() , Toast.LENGTH_SHORT).show();
                });
    }
}
