package com.example.androidfirstproject.Views.NavigationViews;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.example.androidfirstproject.Views.Auth.PhoneInputActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountActivity extends AppCompatActivity {
    private Button logoutBtn;
    private String currentUserID,nameUser1,CurrentPhoneUser1;
    private DatabaseReference mDatabase;
    TextView myPhone, myName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        // Initialize And Assign Varible
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        myPhone = findViewById(R.id.myPhone);
        myName = findViewById(R.id.myName);
        logoutBtn = findViewById(R.id.logoutBtn);

        getCurrentId();

        account();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccountActivity.this, PhoneInputActivity.class));
                finish();
            }
        });

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.account);
        // Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.messege:
                        startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), PhoneBookActivity.class));
                        overridePendingTransition(0, 0);
                    case R.id.account:
                        return true;

                }
                return false;
            }
        });


    }

    public void getCurrentId(){
        if ( currentUserID == null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            try {
                currentUserID = user.getUid();
                Log.d(TAG, "CurrentUserID: " + currentUserID);
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error: Cannot get UserID", Toast.LENGTH_SHORT);
                Log.d(TAG, "CurrentUserID Error: " + e.getMessage());
            }
        }
    }
    public void account(){
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child(currentUserID);
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    User user = task.getResult().getValue(User.class);
                    nameUser1 = user.getFullName();
                    CurrentPhoneUser1 = user.getPhoneNumber();
                    myName.setText(nameUser1);
                    myPhone.setText(CurrentPhoneUser1);
                    Log.d(">>>TAG",""+nameUser1+CurrentPhoneUser1);
                }
            }
        });
    }
}