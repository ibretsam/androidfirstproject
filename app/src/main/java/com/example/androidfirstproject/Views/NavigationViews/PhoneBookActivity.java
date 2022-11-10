package com.example.androidfirstproject.Views.NavigationViews;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PhoneBookActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FloatingActionButton fadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_phone);
        // Initialize And Assign Varible
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

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
                    case R.id.account:
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        fadd = findViewById(R.id.fadd);
        fadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference("user");
                String userId = mDatabase.push().getKey();
                createUser(userId,"0708391259", "Khanh Le", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png");
                createUser(userId,"0874872478", "Linh", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png");
                createUser(userId,"0987467283", "Trang", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readUser();
    }

    private void createUser(String userId, String phoneNumber, String fullName, String profilePicture) {
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        User user = new User(phoneNumber, fullName, profilePicture);
        mDatabase.child(userId).setValue(user);
    }

    private void readUser() {
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot data : task.getResult().getChildren()) {
                        User user = data.getValue(User.class);
                        Log.d(TAG, "User: " + user.getFullName());
                    }
                }
            }
        });


    }
}