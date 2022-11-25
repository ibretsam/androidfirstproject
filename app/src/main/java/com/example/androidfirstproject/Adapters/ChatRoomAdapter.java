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

import java.util.ArrayList;
import java.util.List;

public class ChatRoomAdapter extends BaseAdapter {

    private List<Message> listChat;
    private Context context;

    public ChatRoomAdapter(List<Message> listChat, Context context) {
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
            view = View.inflate(_viewGroup.getContext(), R.layout.chat_layout_right,null);
            TextView myMessage = view.findViewById(R.id.myMessage);
            TextView myTime = view.findViewById(R.id.myTime);
            ViewHolder holder = new ViewHolder(myMessage,myTime);
            view.setTag(holder);
        }
        Message message = (Message) getItem(_i);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.myMessage.setText(message.getContent());
        holder.myTime.setText(message.getTime());
        return view;


    }

    private static class ViewHolder{
        TextView myMessage, myTime;
        public ViewHolder (TextView myMessage, TextView myTime){
            this.myMessage = myMessage;
            this.myTime = myTime;
        }
    }

}
