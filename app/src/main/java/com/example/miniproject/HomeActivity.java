package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private MaterialToolbar homeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
        homeToolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(homeToolbar);
        getSupportActionBar().setTitle("Communion");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemainmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_button :
                this.auth.signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
                break;
            default:
                return false;
        }
        return false;
    }
}