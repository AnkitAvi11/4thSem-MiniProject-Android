package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout emailAddress, userPassword;
    private Button signupButton;
    private ProgressBar spinner;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null) {
            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailAddress = findViewById(R.id.emailaddress);
        userPassword = findViewById(R.id.userPassword);
        signupButton = findViewById(R.id.signupButton);
        spinner = findViewById(R.id.myspinner);


        signupButton.setOnClickListener(v -> this.signupAction());

    }

    private void signupAction() {
        String email = emailAddress.getEditText().getText().toString();
        String password = userPassword.getEditText().getText().toString();
        spinner.setVisibility(View.VISIBLE);
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty", Toast.LENGTH_SHORT).show();
            spinner.setVisibility(View.INVISIBLE);
        }else{
            Log.d("ASS", "Email = " + email + " and password = " + password);
            this.registerUserWithEmailAndPassword(email, password);
        }

    }

    private void registerUserWithEmailAndPassword(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    Toast.makeText(SignupActivity.this, "Signup Succesfull", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SignupActivity.this, "Signup failed. Please check the fields again.", Toast.LENGTH_SHORT).show();
                }
                spinner.setVisibility(View.INVISIBLE);
            }
        });
    }

}