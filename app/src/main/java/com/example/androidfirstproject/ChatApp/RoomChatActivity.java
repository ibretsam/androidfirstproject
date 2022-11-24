package com.example.androidfirstproject.ChatApp;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class RoomChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView tvNameUser2;
    EditText input_message;
    ImageButton sendMessage;
    private String currentUserID;
    private String phoneUser2, idChatRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserID = user.getUid();

        ChatRoom chatRoom;
        chatRoom = (ChatRoom) getIntent().getSerializableExtra("chatRoom");
        phoneUser2 = chatRoom.getUser2Phone();
        checkPhoneUser2(phoneUser2);
        tvNameUser2 = findViewById(R.id.tvNameUser2);
        input_message = findViewById(R.id.input_message);
        sendMessage = findViewById(R.id.sendMessage);
        readIdChatRoom();
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String inputMessage = String.valueOf(input_message.getText());
              String currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
              createMessage(phoneUser2,inputMessage,currentTime,idChatRoom);
              input_message.setText("");
              Toast.makeText(RoomChatActivity.this, "Tin nhắn đã gửi", Toast.LENGTH_SHORT).show();

            }
        });


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


}