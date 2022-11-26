package com.example.androidfirstproject.ChatApp;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfirstproject.Adapters.ChatRoomAdapter;
import com.example.androidfirstproject.Adapters.ChatRoomAdapterUser2;
import com.example.androidfirstproject.Adapters.IAdapterClickEvent;
import com.example.androidfirstproject.Adapters.PhoneBookAdapter;
import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.example.androidfirstproject.Views.NavigationViews.OnCompleteCallback;
import com.example.androidfirstproject.Views.NavigationViews.PhoneBookActivity;
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
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class RoomChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView tvNameUser2;
    EditText input_message;
    ImageButton sendMessage;
    private String currentUserID;
    private String phoneUser2, idChatRoom, user2Phone;
    private ArrayList<String> listMessId;
    private ArrayList<Message> listMessagesChatRoom ;
    ListView lvListChatRoom;
    String messageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);
        lvListChatRoom = findViewById(R.id.lvRoomChat);

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

        if (chatRoom != null) {
            phoneUser2 = chatRoom.getUser2Phone();
            checkPhoneUser2(phoneUser2);
        }

        tvNameUser2 = findViewById(R.id.tvNameUser2);
        input_message = findViewById(R.id.input_message);
        sendMessage = findViewById(R.id.sendMessage);
        readIdChatRoom();
//        reload();
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String inputMessage = String.valueOf(input_message.getText());
              String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
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
                                Log.d(TAG, "ChatroomID: " + idChatRoom);
                                Log.d(TAG, "ChatroomID: " + message.getChatRoomId());
                                if (idChatRoom.equals(message.getChatRoomId())) {
                                   messageID = message.getId();
                                   message.setPhoneUser2(phoneUser2);
                                    Log.d(TAG, "Message ID: " + messageID);
                                    listMessId.add(messageID);
                                    Log.d(">>>>>>>>>>..TAG","list"+listMessId);
                                    DatabaseReference nDatabase = FirebaseDatabase.getInstance().getReference("chatRoom");
                                    nDatabase.child(idChatRoom + "/messageList").setValue(listMessId);
                                    break;
                                }
                            } catch (Exception e) {
                                Log.d(">TAG", ""+ e.getMessage());
                                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
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
    }

    private User checkPhoneUser2(String phoneUser2) {
        mDatabase = FirebaseDatabase.getInstance().getReference("user");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot data : task.getResult().getChildren()) {
                    try {
                        User user = data.getValue(User.class);
                        if (phoneUser2.trim().equals(user.getPhoneNumber())) {
                            tvNameUser2.setText(user.getFullName());
                            break;
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return null;
    }

    public void createMessage(String phoneUser2, String inputMessage, String currentTime, String idChatRoom ){
        mDatabase = FirebaseDatabase.getInstance().getReference("Message");
        String messageId = mDatabase.push().getKey();
        Message message = new Message(inputMessage,currentTime,idChatRoom,phoneUser2,"");
        mDatabase.child(messageId).setValue(message);


    }

    public void readIdChatRoom() {
        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom");
        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot data : task.getResult().getChildren()) {
                    try {
                        ChatRoom chatRoom = data.getValue(ChatRoom.class);
                        chatRoom.setId(data.getKey());
                        if (phoneUser2.trim().equals(chatRoom.getUser2Phone())) {
                            idChatRoom = chatRoom.getId();
                            Log.d(">>>>>>>>>>>>TAG","" + idChatRoom );
                            user2Phone = chatRoom.getUser2Phone();
//                            listMessId = chatRoom.getMessageList();
//                            Log.d(">>>>>>>>>>>>TAG","" + listMessId );
                            listMessId = chatRoom.getMessageList();
                            break;
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "onComplete: " + e.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    public void readMessage() {
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom").child(idChatRoom);
//        // Read from the database
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ChatRoom chatRoom = dataSnapshot.getValue(ChatRoom.class);
//                 listMessId = chatRoom.getMessageList();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

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
                               } catch (Exception e) {
                                   Log.d(TAG, "Exception: " + e.getMessage());
                               }

                           }
                       }
                   }
                for (Message message : listMessagesChatRoom) {
                    if(message.getPhoneUser2()== phoneUser2) {
                        ChatRoomAdapter adapter = new ChatRoomAdapter(listMessagesChatRoom, RoomChatActivity.this);
                        lvListChatRoom.setAdapter(adapter);
                    }
                    else{
                        ChatRoomAdapterUser2 adapter2 = new ChatRoomAdapterUser2(listMessagesChatRoom, RoomChatActivity.this);
                        lvListChatRoom.setAdapter(adapter2);
                    }
                }
                }
//            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
    public void reload(){
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
                    for (Message message : listMessagesChatRoom) {
                        if(message.getPhoneUser2()==phoneUser2) {
                            ChatRoomAdapter adapter = new ChatRoomAdapter(listMessagesChatRoom, RoomChatActivity.this);
                            lvListChatRoom.setAdapter(adapter);
                        }
                        else{
                            ChatRoomAdapterUser2 adapter2 = new ChatRoomAdapterUser2(listMessagesChatRoom, RoomChatActivity.this);
                            lvListChatRoom.setAdapter(adapter2);
                        }
                    }
                }

            }
//            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }



}