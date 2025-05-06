package com.example.amazonapp.MenuFiles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amazonapp.HomeActivity;
import com.example.amazonapp.R;

public class BaseActivity extends AppCompatActivity {


    RadioGroup radioGroup1;
    RadioButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base);

        radioGroup1 = findViewById(R.id.radioGroup1);
        home = findViewById(R.id.bottom_home);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent in;


                if (checkedId == R.id.bottom_home){
                    in = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(in);
                    overridePendingTransition(0,0);

                }

                else if (checkedId == R.id.bottom_addproduct) {
                    in = new Intent(getBaseContext(), AddProductActivity.class);
                    startActivity(in);
                    overridePendingTransition(0,0);

                }

                else if (checkedId == R.id.bottom_search) {
                    in = new Intent(getBaseContext(), SearchActivity.class);
                    startActivity(in);
                    overridePendingTransition(0,0);

                }

                else if (checkedId == R.id.bottom_mycart) {
                    in = new Intent(getBaseContext(), CartActivity.class);
                    startActivity(in);
                    overridePendingTransition(0,0);

                }

                else if (checkedId == R.id.bottom_profile) {
                    in = new Intent(getBaseContext(), ProfileActivity.class);
                    startActivity(in);
                    overridePendingTransition(0,0);

                }
                else {

                }

            }
        });

    }
}