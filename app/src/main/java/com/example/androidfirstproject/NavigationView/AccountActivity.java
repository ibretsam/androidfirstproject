package com.example.androidfirstproject.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        // Initialize And Assign Varible
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.contact);
        // Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.messege:
                        startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), PhoneBookActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.contact:
                        return true;
                }
                return false;
            }
        });
    }
}