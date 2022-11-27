package com.example.androidfirstproject.Views.NavigationViews;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.Adapters.MessageAdapter;
import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,nDatabase;
    private ChatRoom chatRoom = null;
    private ListView lvListChat;
    private ArrayList<ChatRoom> listChatRoom;
    private String timemess,contentmess, currentUserID;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        lvListChat= findViewById(R.id.lvListChat);
        listChatRoom = new ArrayList<>();
        currentUserID = getCurrentUserID();
        getCurrentPhoneNumber(new OnCompleteCallback() {
            @Override
            public void onComplete(User phoneUser) {
                readDataChatRoom();
            }
        });

        // Initialize And Assign Varible
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.messege);
        // Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.messege:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), PhoneBookActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
//        readDataChatRoom();

    }

    private void readDataChatRoom() {
        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listChatRoom.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ChatRoom chatRoom = data.getValue(ChatRoom.class);
                    if (chatRoom.getUserPhoneNumber().contains(currentUser.getPhoneNumber())) {
                        if (chatRoom.getMessageList().size() > 1) {
                            listChatRoom.add(chatRoom);
                        }
                    }

                    readDataMessage(chatRoom);
                }
                if(listChatRoom != null) {
                    Log.d(TAG, "chatRoomInfo: " + listChatRoom + timemess + contentmess);
                    MessageAdapter adapter = new MessageAdapter(listChatRoom, MessageActivity.this, currentUser);
                    lvListChat.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void readDataMessage(ChatRoom chatRoom) {
        nDatabase = FirebaseDatabase.getInstance().getReference("Message");
        nDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals(chatRoom.getLastMessageId())) {
                        try {
                            Message message = data.getValue(Message.class);
                            Log.d(">>Tag", "" + chatRoom.getLastMessageId() + message);
                            timemess = message.getTime();
                            contentmess = message.getContent();
                            Log.d(TAG, "chatRoomInfoOnDataChange: " + timemess + " " + contentmess);
                        } catch (Exception e) {
                            Log.d(TAG, "Exception: " + e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private String getCurrentUserID() {
        if ( currentUserID == null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            try {
                currentUserID = user.getUid();
                Log.d(TAG, "CurrentUserID: " + currentUserID);
                return currentUserID;
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error: Cannot get UserID", Toast.LENGTH_SHORT);
                Log.d(TAG, "CurrentUserID Error: " + e.getMessage());
            }
        }
        return currentUserID;
    }

    public void getCurrentPhoneNumber(OnCompleteCallback callback){
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child(currentUserID);
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    currentUser = task.getResult().getValue(User.class);
                    callback.onComplete(currentUser);
                }
            }
        });
    }




}