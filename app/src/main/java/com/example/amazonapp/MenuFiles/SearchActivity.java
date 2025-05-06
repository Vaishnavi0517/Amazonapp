package com.example.amazonapp.MenuFiles;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazonapp.HomeActivity;
import com.example.amazonapp.ProductDetails;
import com.example.amazonapp.R;
import com.example.amazonapp.model.AddProductModel;
import com.example.amazonapp.viewholder.ViewProductHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends BaseActivity {

    EditText inputText;
    AppCompatButton searchBtn;
    RecyclerView searchList;
    String searchInput;
    ImageView backHome;
    Toolbar viewToolbar;

    LinearLayout dynamicContent, bottomNavBar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_search);

        dynamicContent = findViewById(R.id.dynamicContent);
        bottomNavBar = findViewById(R.id.bottomNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_search,null);
        dynamicContent.addView(wizard);

        RadioGroup rg = findViewById(R.id.radioGroup1);
        RadioButton rb = findViewById(R.id.bottom_search);

        rb.setBackgroundColor(R.color.item_selected);
        rb.setTextColor(Color.parseColor("#3F5185"));

        viewToolbar = findViewById(R.id.viewtoolbar);
        viewToolbar.setBackgroundResource(R.drawable.bg_color);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setBackgroundResource(R.drawable.intro_login);

        inputText=findViewById(R.id.searchEditText);
        searchList=findViewById(R.id.searchlist);
        backHome=findViewById(R.id.backHome);

        searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput=inputText.getEditableText().toString();
                onStart();
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference prodListRef = FirebaseDatabase.getInstance().getReference().child("View All")
                .child("User View").child("Products");

        FirebaseRecyclerOptions<AddProductModel> options = new FirebaseRecyclerOptions.Builder<AddProductModel>()
                .setQuery(prodListRef.orderByChild("name").startAt(searchInput), AddProductModel.class).build();

        FirebaseRecyclerAdapter<AddProductModel, ViewProductHolder> adapter =
                new FirebaseRecyclerAdapter<AddProductModel, ViewProductHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewProductHolder holder, int position, @NonNull AddProductModel model) {

                        String name = model.getName().replaceAll("\n", " " );
                        String price = model.getPrice();
                        String imguri = model.getImg();

                        holder.addProductName.setText(name);
                        holder.addProductPrice.setText(price);
                        Picasso.get().load(imguri).into(holder.addproductImg);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= new Intent(SearchActivity.this, ProductDetails.class);
                                intent.putExtra("id",4);
                                intent.putExtra("uniqueId",name);
                                intent.putExtra("addProdName",name);
                                intent.putExtra("addProdPrice",price);
                                intent.putExtra("addprodDesc",model.getDescription());
                                intent.putExtra("addProdCategory",model.getCategory());
                                intent.putExtra("img",imguri);
                                startActivity(intent);

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public ViewProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_adapter,parent,false);
                        ViewProductHolder holder = new ViewProductHolder(view);
                        return  holder;

                    }


                };


        searchList.setAdapter(adapter);
        adapter.startListening();

    }

    }









