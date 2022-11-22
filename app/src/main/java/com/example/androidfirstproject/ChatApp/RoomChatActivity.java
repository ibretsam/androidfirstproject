package com.example.androidfirstproject.ChatApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfirstproject.Adapters.IAdapterClickEvent;
import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.example.androidfirstproject.Views.NavigationViews.OnCompleteCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RoomChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView tvNameUser2;
    EditText input_message;
    ImageButton sendMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);
//        Bundle ChatRoomBundle = getIntent().getExtras();
        tvNameUser2 = findViewById(R.id.tvNameUser2);
        input_message = findViewById(R.id.input_message);
        sendMessage = findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String inputMessage = String.valueOf(input_message.getText());
            }
        });
        ChatRoom chatRoom;
        chatRoom = (ChatRoom) getIntent().getSerializableExtra("chatRoom");
        String phoneUser2 = chatRoom.getUser2Phone();
        checkPhoneUser2(phoneUser2);

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




}