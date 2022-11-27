package com.example.androidfirstproject.Views.NavigationViews;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfirstproject.Adapters.ChatRoomAdapter;
import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RoomChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,nDatabase;
    TextView tvNameUser2;
    EditText input_message;
    ImageButton sendMessage;
    private String currentUserID;
    private String phoneUser2, idChatRoom;
    private ArrayList<String> listMessId;
    private ArrayList<Message> listMessagesChatRoom ;
    RecyclerView lvListChatRoom;
    String messageID, currentUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);
        //recyclerView
        lvListChatRoom = findViewById(R.id.lvRoomChat);
        lvListChatRoom.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        lvListChatRoom.setLayoutManager(linearLayoutManager);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserID = user.getUid();
        listMessId = new ArrayList<String>();
        listMessagesChatRoom = new ArrayList<Message>();

        ChatRoom chatRoom;
        Intent intent = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            chatRoom = intent.getSerializableExtra("chatRoom", ChatRoom.class);
        } else {
            chatRoom = (ChatRoom) intent.getSerializableExtra("chatRoom");
        }
         idChatRoom = chatRoom.getId();
        Log.d(">>>>>>>TAG","chatroomid"+idChatRoom);
        mDatabase = FirebaseDatabase.getInstance().getReference("user").child(currentUserID);
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    User user = task.getResult().getValue(User.class);
                    currentUserPhone = user.getPhoneNumber();
                    Log.d(TAG, "current: " + currentUserPhone);
                    if (chatRoom != null) {
                        for (String phone : chatRoom.getUserPhoneNumber()) {
                            if (!phone.equals(currentUserPhone)) {
                                phoneUser2 = phone;
                                Log.d(">>TAG","phoneuser2roomchat"+phoneUser2);
                                break;
                            }
                        }
                        checkPhoneUser2(phoneUser2);
                    }
                }
            }
        });


        tvNameUser2 = findViewById(R.id.tvNameUser2);
        input_message = findViewById(R.id.input_message);
        sendMessage = findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String inputMessage = String.valueOf(input_message.getText());
              String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
              createMessage(phoneUser2,inputMessage,currentTime,idChatRoom);
              input_message.setText("");
                mDatabase = FirebaseDatabase.getInstance().getReference("Message");
                mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        for (DataSnapshot data : task.getResult().getChildren()) {
                            try {
                                Message message= data.getValue(Message.class);
                                message.setId(data.getKey());
                                if (idChatRoom.equals(message.getChatRoomId())) {
                                   messageID = message.getId();
                                   message.setPhoneUser2(phoneUser2);
                                    if (!listMessId.contains(messageID)) {
                                        listMessId.add(messageID);
                                    }
                                    DatabaseReference nDatabase = FirebaseDatabase.getInstance().getReference("chatRoom");
                                    nDatabase.child(idChatRoom + "/messageList").setValue(listMessId);

                                }
                            } catch (Exception e) {
                                Log.d(">TAG", ""+ e.getMessage());
                                Toast.makeText(getApplicationContext(), "Error send message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        listMessagesChatRoom.clear();
                        nDatabase = FirebaseDatabase.getInstance().getReference("chatRoom").child(idChatRoom);
                        String idLastMessage = listMessId.get(listMessId.size() - 1);
                        nDatabase.child("lastMessageId").setValue(idLastMessage);
                        reload();
                    }
                });


              Toast.makeText(RoomChatActivity.this, "Tin nhắn đã gửi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
//        listMessagesChatRoom.clear();
//        readMessage();
        listMessagesChatRoom.clear();
        reload();
    }

    private User checkPhoneUser2(String phoneUser2) {
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot data : task.getResult().getChildren()) {
                    try {
                        User user = data.getValue(User.class);
                        if (phoneUser2.equals(user.getPhoneNumber())) {
                            tvNameUser2.setText(user.getFullName());
                            break;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error: " + e.getMessage());
                    }
                }
            }
        });
        return null;
    }

    public void createMessage(String phoneUser2, String inputMessage, String currentTime, String idChatRoom ){
        mDatabase = FirebaseDatabase.getInstance().getReference("Message");
        String messageId = mDatabase.push().getKey();
        Message message = new Message(inputMessage,currentTime,idChatRoom,currentUserPhone,phoneUser2);
        mDatabase.child(messageId).setValue(message);


    }

//    public void readMessage() {
//        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom").child(idChatRoom);
//        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                } else {
//                    ChatRoom chatRoom = task.getResult().getValue(ChatRoom.class);
//                    listMessId = chatRoom.getMessageList();
//                }
//            }
//        });
//        mDatabase = FirebaseDatabase.getInstance().getReference("Message");
//        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                } else {
//                    for (String id : listMessId) {
//                        for (DataSnapshot data : task.getResult().getChildren()) {
//                            if (data.getKey().equals(id.trim())) {
//                                try {
//                                    Message message = data.getValue(Message.class);
//                                    message.setId(data.getKey());
//                                    listMessagesChatRoom.add(message);
//                                } catch (Exception e) {
//                                    Log.d(TAG, "Exception: " + e.getMessage());
//                                }
//
//                            }
//
//                            ChatRoomAdapter adapter = new ChatRoomAdapter(listMessagesChatRoom, RoomChatActivity.this,currentUserID);
//                            lvListChatRoom.setAdapter(adapter);
//
//                        }
//
//
//                    }
//
//
//
//                }
//            }
//        });
//
//    }
    public void reload(){
        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom").child(idChatRoom);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ChatRoom chatRoom = dataSnapshot.getValue(ChatRoom.class);
                listMessId = chatRoom.getMessageList();
//                String idLastMessage = listMessId.get(listMessId.size() - 1);
//                mDatabase.child("lastMessage").setValue(idLastMessage);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Message");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (String id : listMessId) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.getKey().equals(id.trim())) {
                            try {
                                Message message = data.getValue(Message.class);
                                message.setId(data.getKey());
                                listMessagesChatRoom.add(message);
                                Log.d(">>>>>>>>>>>>>>>>>TAG",""+message.getPhoneUser2());
                            } catch (Exception e) {
                                Log.d(TAG, "Exception: " + e.getMessage());
                            }
                        }
                    }
                    ChatRoomAdapter adapter = new ChatRoomAdapter(listMessagesChatRoom, RoomChatActivity.this,currentUserID);
                    lvListChatRoom.setAdapter(adapter);
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