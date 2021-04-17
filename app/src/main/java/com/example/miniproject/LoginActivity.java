package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputLayout usernameLayout, passwordLayout;
    private Button loginButton;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLayout = findViewById(R.id.username);
        passwordLayout = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.resetPassword);

        loginButton.setOnClickListener(v -> this.loginAction());

        textView.setOnClickListener(v -> this.resetPasswordFunction());

    }


    //  method to reset the password using the email address
    private void resetPasswordFunction() {

        EditText editText = new EditText(getBaseContext());
        editText.setHint("Email address here");
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(LoginActivity.this);

        passwordResetDialog.setTitle("Reset password");
        passwordResetDialog.setMessage("Enter your email address");
        passwordResetDialog.setView(editText);

        passwordResetDialog.setPositiveButton("Send Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  extract the email and send the email
                String emailAddress = editText.getText().toString();
                auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid email address" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  closes the dialog box
            }
        });

        passwordResetDialog.create().show();

    }

    //  method to log a user in the application
    private void loginAction() {
        String email = usernameLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }else{
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

}