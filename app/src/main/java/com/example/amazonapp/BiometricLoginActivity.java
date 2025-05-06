package com.example.amazonapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.amazonapp.viewholder.BiometricHelper;

public class BiometricLoginActivity extends AppCompatActivity {

    @SuppressLint("NewApi")
    BiometricHelper biometricHelper = new BiometricHelper(this, new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            // Login success
            Toast.makeText(BiometricLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BiometricLoginActivity.this, firstpage.class));
        }

        @Override
        public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Toast.makeText(BiometricLoginActivity.this, "Authentication Error: " + errString, Toast.LENGTH_SHORT).show();
        }
    });



}