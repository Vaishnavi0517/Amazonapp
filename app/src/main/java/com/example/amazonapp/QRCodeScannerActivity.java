package com.example.amazonapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCodeScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        new IntentIntegrator(this).setPrompt("Scan a QR Code").initiateScan();
        setContentView(R.layout.activity_qrcode_scanner);

    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null && result.getContents() != null) {

                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra("scanned_data", result.getContents());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No QR Code scanned", Toast.LENGTH_SHORT).show();
                finish();
            }
        }




}