package com.example.androidfirstproject.Adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private DatabaseReference mDatabase;

    private List<ChatRoom> listMessage;
    Context context;
    private User currentUser;
    private Boolean found = false;

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

        getNameUser2(chatRoom,holder);

        getLastMessage(chatRoom,holder);

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

    public void getNameUser2(ChatRoom chatRoom, MessageAdapter.ViewHolder holder){
        for (String phone : chatRoom.getUserPhoneNumber()) {
            if (!currentUser.getPhoneNumber().equals(phone)) {
                mDatabase = FirebaseDatabase.getInstance().getReference("user");
                mDatabase.get().addOnCompleteListener(task -> {
                    for (DataSnapshot data : task.getResult().getChildren()) {
                        User user = data.getValue(User.class);
                        if (user.getPhoneNumber().equals(phone)) {
                            holder.nameUser2.setText(user.getFullName());
                            break;
                        }
                    }
                });
            }
        }
    }

    public void getLastMessage(ChatRoom chatRoom, MessageAdapter.ViewHolder holder) {
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
    }

}

