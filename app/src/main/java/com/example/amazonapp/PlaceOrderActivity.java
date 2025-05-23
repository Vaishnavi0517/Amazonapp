package com.example.amazonapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.jar.JarException;

public class PlaceOrderActivity extends AppCompatActivity implements PaymentResultListener {

    EditText shipName, shipPhone, shipAddress, shipCity;
    AppCompatButton confirmOrder;
    FirebaseAuth auth;
    Intent intent;
    String totalAmount;
    TextView cartpricetotal;
    Toolbar cartToolbar;
    int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);

        shipName = findViewById(R.id.shipName);
        shipPhone = findViewById(R.id.shipPhone);
        shipAddress = findViewById(R.id.shipAddress);
        shipCity = findViewById(R.id.shipCity);
        confirmOrder = findViewById(R.id.confirmOrder);
        cartpricetotal = findViewById(R.id.cartpricetotal);
        cartToolbar = findViewById(R.id.cart_toolbar);

        auth = FirebaseAuth.getInstance();

        cartToolbar.setBackgroundResource(R.drawable.bg_color);
        confirmOrder.setBackgroundResource(R.drawable.bg_color);

        intent = getIntent();
        totalAmount = intent.getStringExtra("totalAmount");

        cartpricetotal.setText(totalAmount);

        String sAmount = "100";

        amount = Math.round(Float.parseFloat(sAmount)*100);

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }

    private void check(){
        if (TextUtils.isEmpty(shipName.getText().toString())){
            shipName.setError("Enter name");
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(shipPhone.getText().toString())){
            shipPhone.setError("Enter phone no.");
            Toast.makeText(this, "Please enter your phone no.", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(shipAddress.getText().toString())) {
            shipAddress.setError("Enter address");
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(shipCity.getText().toString())) {
            shipCity.setError("Enter city");
            Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show();
        }else {
            paymentFunc();
        }
    }

    private void paymentFunc(){
        Checkout checkout = new Checkout();

        checkout.setKeyID("");

        checkout.setImage(R.drawable.razorpay_logo);

        JSONObject object = new JSONObject();

        try {
            object.put("name","Android User");

            object.put("description","Test Payment");

            object.put("currency","INR");

            object.put("amount",amount);

            object.put("prefill.contact","7898653421");

            object.put("prefill.email","androiduser@rzp.com");

            checkout.open(PlaceOrderActivity.this,object);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void confirmOrderFunc(){

        String saveCurrentDate, saveCurrentTime;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders").child(auth.getCurrentUser().getUid()).child("History")
                .child(saveCurrentDate.replaceAll("/", "-")+" "+saveCurrentTime);

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", shipName.getText().toString());
        ordersMap.put("phone",shipPhone.getText().toString());
        ordersMap.put("address",shipAddress.getText().toString());
        ordersMap.put("city",shipCity.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);

    }

    @Override
    public void onPaymentSuccess(String s) {

        confirmOrderFunc();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}