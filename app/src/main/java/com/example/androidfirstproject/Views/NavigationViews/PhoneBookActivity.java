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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.Adapters.IAdapterClickEvent;
import com.example.androidfirstproject.ChatApp.RoomChatActivity;
import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.example.androidfirstproject.Adapters.PhoneBookAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PhoneBookActivity extends AppCompatActivity implements IAdapterClickEvent {
    private DatabaseReference mDatabase;
    private FloatingActionButton fadd;
    private ArrayList<User> phoneBook;
    private ArrayList<String> phoneBookUserID;
    private User phoneUser;
    ListView lvPhoneBook;
    private String currentUserID, CurrentPhoneUser2;
    private FirebaseAuth rAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_phone);
        lvPhoneBook = findViewById(R.id.listPhoneBook);
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        phoneBook = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserID = user.getUid();
        } else {
            Toast.makeText(getApplicationContext(), "Error: Cannot get UserID", Toast.LENGTH_SHORT);
        }


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
                String phoneNumber = "+84" + String.valueOf(edtPhone.getText()).trim();
                checkPhone(phoneNumber, phoneUser -> {
                    if (phoneUser != null) {
                        addContact(phoneUser);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error: User not found", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error: User not found");
                    }
                });


//                createUser(userId, String.valueOf(edtPhone.getText()), String.valueOf(edtName.getText()), anhMau);
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
        phoneBook.clear();
        readUser();
    }

    private User checkPhone(String phoneNumber, final OnCompleteCallback callback) {
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot data : task.getResult().getChildren()) {
                    try {
                        User user = data.getValue(User.class);
                        user.setId(data.getKey());
                        if (phoneNumber.trim().equals(user.getPhoneNumber())) {
                            phoneUser = user;
                            break;
                        }
                        else {
                            phoneUser = null;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                callback.onComplete(phoneUser);
            }
        });
        return phoneUser;
    }

    private void addContact(User user) {
        phoneBookUserID.add(user.getId());
        mDatabase.child(currentUserID + "/phoneBook").setValue(phoneBookUserID);
        phoneBook.clear();
        readUser();
        mDatabase.child(currentUserID).child("phoneBook").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.d(TAG, "onComplete: " + task.getResult());
            }
        });
    }



    public void readUser() {
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child(currentUserID);
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    User user = task.getResult().getValue(User.class);
                    phoneBookUserID = user.getPhoneBook();
                    CurrentPhoneUser2 = user.getPhoneNumber();
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    if (phoneBookUserID.isEmpty()) {
                        phoneBook.clear();
                        PhoneBookAdapter adapter = new PhoneBookAdapter(phoneBook, PhoneBookActivity.this, phoneBookUserID);
                        lvPhoneBook.setAdapter(adapter);
                    } else {
                        for (String id : phoneBookUserID) {
                            for (DataSnapshot data : task.getResult().getChildren()) {
                                if (data.getKey().equals(id.trim())) {
                                    try {
                                        User user = data.getValue(User.class);
                                        user.setId(data.getKey());
                                        phoneBook.add(user);
                                    } catch (Exception e) {
                                        Log.d(TAG, "Exception: " + e.getMessage());
                                    }

                                }
                            }
                        }
                        PhoneBookAdapter adapter = new PhoneBookAdapter(phoneBook, PhoneBookActivity.this, phoneBookUserID);
                        lvPhoneBook.setAdapter(adapter);
                        lvPhoneBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                User user= (User) parent.getItemAtPosition(position);
                                String phoneUser2 = user.getPhoneNumber();
                                createChatRoom(phoneUser2);
                            }
                        });
                    }
                }
            }
        });

    }


    @Override
    public void onDeleteClick(ArrayList<String> phoneBookUserId) {
        mDatabase.child(currentUserID + "/phoneBook").setValue(phoneBookUserID);
        phoneBook.clear();
        readUser();
    }
    public void createChatRoom(String phoneUser2){
        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom");
        ChatRoom chatRoom = new ChatRoom(CurrentPhoneUser2,phoneUser2,new ArrayList<Message>(),new String());
        mDatabase.child(currentUserID).setValue(chatRoom);
        Intent intent =  new Intent(PhoneBookActivity.this, RoomChatActivity.class);
        intent.putExtra("chatRoom",chatRoom);
        startActivity(intent);

    }

}

