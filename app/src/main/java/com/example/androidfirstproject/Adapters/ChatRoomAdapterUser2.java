package com.example.androidfirstproject.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.Message;
import com.example.androidfirstproject.R;

import java.util.List;

public class ChatRoomAdapterUser2  extends BaseAdapter {

    private List<Message> listChat;
    private Context context;
    public ChatRoomAdapterUser2(List<Message> listChat, Context context){
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
            view = View.inflate(_viewGroup.getContext(), R.layout.chat_layout_left,null);
            TextView user2Message = view.findViewById(R.id.user2Message);
            TextView user2Time = view.findViewById(R.id.user2Time);
            ChatRoomAdapterUser2.ViewHolder holder = new ChatRoomAdapterUser2.ViewHolder(user2Message,user2Time);
            view.setTag(holder);
        }
        Message message = (Message) getItem(_i);
        ChatRoomAdapterUser2.ViewHolder holder = (ChatRoomAdapterUser2.ViewHolder) view.getTag();
        holder.user2Message.setText(message.getContent());
        holder.user2Time.setText(message.getTime());
        return view;


    }

    private static class ViewHolder{
        TextView user2Message, user2Time;
        public ViewHolder (TextView user2Message, TextView user2Time){
            this.user2Message = user2Message;
            this.user2Time = user2Time;
        }
    }

}
