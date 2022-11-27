package com.example.androidfirstproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private DatabaseReference mDatabase;

    private List<ChatRoom> listMessage;
    Context context;
    private User currentUser;

    public MessageAdapter(List<ChatRoom> listMessage, Context context, User currentUser) {

        this.listMessage = listMessage;
        this.context = context;
        this.currentUser = currentUser;
    }


    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public Object getItem(int position) {
        return listMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view = _view;
        if (view == null) {
            view = View.inflate(_viewGroup.getContext(), R.layout.item_message, null);
            TextView nameUser2 = view.findViewById(R.id.nameUser2);
            TextView lastMessage = view.findViewById(R.id.lastMessage);
            TextView time = view.findViewById(R.id.time);
            MessageAdapter.ViewHolder holder = new MessageAdapter.ViewHolder(nameUser2,lastMessage,time);
            view.setTag(holder);
        }
        ChatRoom chatRoom = (ChatRoom)getItem(_i);

        MessageAdapter.ViewHolder holder = (MessageAdapter.ViewHolder) view.getTag();
        holder.nameUser2.setText(chatRoom.getNameUser2());

        mDatabase = FirebaseDatabase.getInstance().getReference("chatRoom");
        mDatabase.get().addOnCompleteListener(task -> {
            for (DataSnapshot data : task.getResult().getChildren()) {
                if (data.getKey().equals(chatRoom.getId())) {
                    mDatabase = FirebaseDatabase.getInstance().getReference("user");
                    mDatabase.get().addOnCompleteListener(task1 -> {
                        for (DataSnapshot data1 : task1.getResult().getChildren()) {
                            User user2 = data1.getValue(User.class);
                            if (chatRoom.getUserPhoneNumber().contains(user2.getPhoneNumber())) {
                                if (!user2.getPhoneNumber().equals(currentUser.getPhoneNumber())) {
                                    holder.nameUser2.setText(user2.getFullName());
                                }
                            }
                            break;
                        }
                    });
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("Message");
        mDatabase.get().addOnCompleteListener(task -> {
            for (DataSnapshot data : task.getResult().getChildren()) {
                if (data.getKey().equals(chatRoom.getLastMessageId())) {
                    Message message = data.getValue(Message.class);
                    holder.lastMessage.setText(message.getContent());
                    holder.time.setText(message.getTime());
                }
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private static class ViewHolder {
        TextView nameUser2,lastMessage, time ;

        public ViewHolder(TextView nameUser2, TextView lastMessage, TextView time) {
            this.nameUser2 = nameUser2;
            this.lastMessage = lastMessage;
            this.time = time;
        }
    }


}

