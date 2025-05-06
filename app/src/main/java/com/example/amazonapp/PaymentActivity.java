package com.example.amazonapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.razorpay.Checkout;
import org.json.JSONObject;

import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity implements PaymentActivity1 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        if (!isPaymentAllowed()) {
            Toast.makeText(this, "Payments are not allowed between 8 PM and 6 AM", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        double amount = 200.00;
        if (amount < 100) {
            Toast.makeText(this, "Minimum payment is ₹100", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        startPayment(amount);
    }

    private boolean isPaymentAllowed() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return !(hour >= 20 || hour < 6);
    }

    private void startPayment(double amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_your_key_here");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Your App Name");
            options.put("description", "Payment for Order");
            options.put("currency", "INR");
            options.put("amount", amount * 100); // Amount in paise

            checkout.open(this, options);
        } catch (Exception e) {
            Toast.makeText(this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        sendInvoice("user_email@example.com", razorpayPaymentID);
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_SHORT).show();
    }



    private void sendInvoice(String email, String paymentID) {

        String subject = "Payment Invoice";
        String body = "Thank you for your payment. Your Payment ID is " + paymentID;
        Toast.makeText(this, "Invoice sent to: " + email, Toast.LENGTH_SHORT).show();

    }
}