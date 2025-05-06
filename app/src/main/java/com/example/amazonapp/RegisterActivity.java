package com.example.amazonapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.amazonapp.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText regName, regEmail, regPass, regConfirmPass;
    AppCompatButton signUpButton;
    LinearLayout SignUpTextView;
    ProgressDialog progressDialog;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        regName=findViewById(R.id.regUsername);
        regEmail=findViewById(R.id.regEmail);
        regPass=findViewById(R.id.regPass);
        regConfirmPass=findViewById(R.id.regCofirmPass);

        signUpButton=findViewById(R.id.signUpButton);
        signUpButton.setBackgroundResource(R.drawable.intro_login);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        SignUpTextView=findViewById(R.id.signUpTextView);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name=regName.getEditableText().toString();
                String email=regEmail.getEditableText().toString();
                String password=regPass.getEditableText().toString();
                String confirmPassword=regConfirmPass.getEditableText().toString();

                if(TextUtils.isEmpty(name) ||  TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password)
                        || TextUtils.isEmpty(confirmPassword)){
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Enter valid data", Toast.LENGTH_SHORT).show();

                } else if (!email.matches(emailPattern)) {
                    progressDialog.dismiss();
                    regEmail.setError("Invalid Email");
                    Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();



                } else if (password.length()<=6){
                    progressDialog.dismiss();
                    regPass.setError("Invalid password");
                    Toast.makeText(RegisterActivity.this, "PLease enter more than 6 characters", Toast.LENGTH_SHORT).show();


                } else if (!password.equals(confirmPassword)) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();

                }else{
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                DatabaseReference reference=database.getReference().child("users")
                                        .child(auth.getCurrentUser().getUid());

                                String finalImageUri = "";
                                Users users = new Users(auth.getCurrentUser().getUid(),name, email, finalImageUri);

                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                if (task.isSuccessful()){
                                                    progressDialog.dismiss();
                                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));


                                                }else {
                                                    Toast.makeText(RegisterActivity.this, "Error in creating a new users", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        SignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}