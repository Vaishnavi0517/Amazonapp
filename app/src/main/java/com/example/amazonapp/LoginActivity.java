package com.example.amazonapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText phoneInput,otp;
    AppCompatButton sendotpbutton,loginbutton;
    String codeID;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    LinearLayout homeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
        {
            auth.signOut();
        }

        phoneInput = findViewById(R.id.phoneInput);
        sendotpbutton = findViewById(R.id.sendotpbutton);


        sendotpbutton = findViewById(R.id.sendotpbutton);
        sendotpbutton.setBackgroundResource(R.drawable.intro_login);

        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setBackgroundResource(R.drawable.intro_login);

        homeTextView=findViewById(R.id.homeTextView);


        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                codeID = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        };


        sendotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneInput.getText().toString().isEmpty())
                {
                    phoneInput.setError("Please Enter Your Phone Number");

                }
                else
                {
                    String number = "+91" + phoneInput.getText().toString();
                    sendOtp(number);

                    phoneInput.setVisibility(View.VISIBLE);


                    loginbutton.setVisibility(View.VISIBLE);

                }
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().isEmpty())
                {
                    otp.setError("Please Enter Your OTP");

                }
                else
                {
                    verifyOtp(phoneInput.getText().toString());
                }
            }
        });

        }

        void sendOtp(String number)
        {
            PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .setTimeout(60l,TimeUnit.SECONDS)
                    .build();
            PhoneAuthProvider.verifyPhoneNumber(options);

        }

        void verifyOtp(String phoneOtp)
        {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeID,phoneOtp);

            auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));



                    }else
                    {
                        Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, auth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    }

                }

            });

            homeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                }
            });

        }

    }

