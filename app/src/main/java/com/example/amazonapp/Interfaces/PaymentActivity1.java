package com.example.amazonapp.Interfaces;

public interface PaymentActivity1 {
    void onPaymentSuccess(String razorpayPaymentID);

    void onPaymentError(int code, String response);
}
