package com.example.amazonapp.viewholder;


import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import android.content.Context;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class BiometricHelper {

    private final Context context;
    private final BiometricPrompt.AuthenticationCallback callback;
    private final Executor executor;

    public BiometricHelper(Context context, BiometricPrompt.AuthenticationCallback callback) {
        this.context = context;
        this.callback = callback;
        this.executor = ContextCompat.getMainExecutor(context);
    }

    public boolean isBiometricSupported() {
        BiometricManager biometricManager = (BiometricManager.from(context));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS;
        }
        return true;
    }

    public void showBiometricPrompt(String title, String subtitle) {
        BiometricPrompt biometricPrompt = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPrompt((FragmentActivity) context, executor, callback);
        }
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setNegativeButtonText("Cancel")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}

