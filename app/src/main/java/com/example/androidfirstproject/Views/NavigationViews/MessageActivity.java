package com.example.androidfirstproject.Views.NavigationViews;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.Adapters.ChatRoomAdapter;
import com.example.androidfirstproject.Adapters.MessageAdapter;
import com.example.androidfirstproject.ChatApp.RoomChatActivity;
import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private String timemess,contentmess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        lvListChat= findViewById(R.id.lvListChat);
        listChatRoom = new ArrayList<>();

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
        readData();
    }

    private void readData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ChatRoom chatRoom = dataSnapshot.getValue(ChatRoom.class);
                    listChatRoom.add(chatRoom);
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
                if(listChatRoom != null) {
                    Log.d(">>>>>>..TAG", "" + listChatRoom + timemess + contentmess);
                    MessageAdapter adapter = new MessageAdapter(listChatRoom, MessageActivity.this, timemess, contentmess);
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


}