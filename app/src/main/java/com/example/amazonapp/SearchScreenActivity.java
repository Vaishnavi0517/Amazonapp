package com.example.amazonapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.amazonapp.viewholder.ProductsAdapter;

public class SearchScreenActivity extends AppCompatActivity {

    private static final int VOICE_SEARCH_REQUEST_CODE = 1;
    private EditText searchEditText;
    private TextView errorTextView;
    private RecyclerView productsRecyclerView;
    private ProductsAdapter productsAdapter;

    private ArrayList<String> products = new ArrayList<>();
    private ArrayList<String> filteredProducts = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_screen);

        searchEditText = findViewById(R.id.searchEditText);
        Button voiceSearchButton = findViewById(R.id.voiceSearchButton);
        errorTextView = findViewById(R.id.errorTextView);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);


        initializeProductList();


        productsAdapter = new ProductsAdapter(filteredProducts);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsRecyclerView.setAdapter(productsAdapter);


        voiceSearchButton.setOnClickListener(view -> startVoiceSearch());
    }

    private void initializeProductList() {

        products.add("Apple");
        products.add("Banana");
        products.add("Camera");
        products.add("Desk");
        products.add("Egg");
        products.add("Fan");
        products.add("Guitar");
        products.add("Hat");
    }

    private void startVoiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the product name");

        try {
            startActivityForResult(intent, VOICE_SEARCH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Voice search not supported on your device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_SEARCH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String voiceInput = results.get(0);
                searchEditText.setText(voiceInput);
                filterProducts(voiceInput);
            }
        }
    }

    private void filterProducts(String voiceInput) {
        filteredProducts.clear();

        // Check if the product starts with A, B, C, D, E
        char firstLetter = Character.toUpperCase(voiceInput.charAt(0));
        if (firstLetter >= 'A' && firstLetter <= 'E') {
            for (String product : products) {
                if (product.toLowerCase().startsWith(voiceInput.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }

            if (filteredProducts.isEmpty()) {
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText("No products found starting with: " + voiceInput);
            } else {
                errorTextView.setVisibility(View.GONE);
            }
        } else {
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText("Products starting with letters other than A, B, C, D, E are not allowed.");
        }

        productsAdapter.notifyDataSetChanged();
    }

}