package com.example.androidfirstproject.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.R;

import java.util.List;

public class ChatRoomAdapter extends BaseAdapter {

    private List<ChatRoom> listChat;
    private Context context;
    public ChatRoomAdapter(List<ChatRoom> listChat, Context context){
        this.context = context;
        this.listChat = listChat;
    }


    @Override
    public int getCount() {
        return listChat.size();
    }

    @Override
    public Object getItem(int position) {
        return listChat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view =_view;
        if(view == null){
            view = View.inflate(_viewGroup.getContext(), R.layout.item_message,null);
            TextView nameUser2 = view.findViewById(R.id.nameUser2);
            TextView lastMessage = view.findViewById(R.id.lastMessages);
            TextView time = view.findViewById(R.id.time);
            ViewHolder holder = new ViewHolder(nameUser2,lastMessage,time);
            view.setTag(holder);
        }
        ChatRoom chatroom = (ChatRoom) getItem(_i);
        ViewHolder holder = (ViewHolder) view.getTag();
//        holder.nameUser2.setText(chatroom.getUser2().getFullName());
//        if(chatroom.getLastMessage().getIsLastMessage() == 1){
//        holder.lastMessage.setText(chatroom.getLastMessage().getContent());}
//        holder.time.setText(chatroom.getMessage().getTime());
        return view;


    }

    private static class ViewHolder{
        TextView nameUser2, lastMessage, time;
        public ViewHolder (TextView nameUser2, TextView lastMessage, TextView time){
            this.nameUser2 = nameUser2;
            this.lastMessage = lastMessage;
            this.time= time;
        }
    }

}
