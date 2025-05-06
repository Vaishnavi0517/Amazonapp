package com.example.amazonapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.internal.measurement.zzcp;

public class ProfilePageToggle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page_toggle);

        Switch biometricSwitch = findViewById(R.id.biometricSwitch);
        zzcp sharedPreferences = null;
        biometricSwitch.setChecked(sharedPreferences.getBoolean("biometric_enabled", false));

        biometricSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Trigger OTP before enabling
                startActivity(new Intent(this, OtpVerificationActivity.class));
            } else {
                sharedPreferences.edit().putBoolean("biometric_enabled", false).apply();
                Toast.makeText(this, "Biometric Login Disabled", Toast.LENGTH_SHORT).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}