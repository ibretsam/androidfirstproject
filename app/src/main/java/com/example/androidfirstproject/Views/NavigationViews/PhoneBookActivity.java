package com.example.androidfirstproject.Views.NavigationViews;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.example.androidfirstproject.adapter.PhoneBookAdapter;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBookActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FloatingActionButton fadd;
    private ArrayList<User> phoneBook;
    ListView lvPhoneBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_phone);
        lvPhoneBook = findViewById(R.id.listPhoneBook);
        mDatabase = FirebaseDatabase.getInstance().getReference("user");

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
                openNewUserDialog(Gravity.CENTER);
            }
        });
    }

    // thêm user
    private void openNewUserDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_add);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        String userId = mDatabase.push().getKey();


        EditText edtName = dialog.findViewById(R.id.edt_name_add);
        EditText edtPhone = dialog.findViewById(R.id.edt_phone_add);
        Button btnAddUser = dialog.findViewById(R.id.btnAdd);
        Button btnCanel = dialog.findViewById(R.id.btnCancel);
        final Handler heHandler = new Handler();
//        String regexPhone="^[0-9]$";
//        String phoneNumber=edtPhone.getText().toString();
        String anhMau = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png";
        String pathObject = String.valueOf(edtName);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(userId, String.valueOf(edtPhone.getText()), String.valueOf(edtName.getText()), anhMau);
                heHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 400);
            }
        });

        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        readUser();
    }

    private void createUser(String userId, String phoneNumber, String fullName, String profilePicture) {
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        User user = new User(phoneNumber,fullName,profilePicture);
        mDatabase.child(userId).setValue(user);
    }

    private void readUser() {
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child("-NGlDg2sUqEVDJPBTF0e");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<User> list = new ArrayList<>();
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    User user = task.getResult().getValue(User.class);
                    ArrayList<String> phoneBookUserID = user.getPhoneBook();
                }
                PhoneBookAdapter adapter = new PhoneBookAdapter(list, PhoneBookActivity.this);
                lvPhoneBook.setAdapter(adapter);
            }
        });

    }
}