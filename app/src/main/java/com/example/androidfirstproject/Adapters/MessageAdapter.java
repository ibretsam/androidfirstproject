package com.example.androidfirstproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.R;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private List<ChatRoom> listMessage;
    Context context;
    String timemess,contentmess;

    public MessageAdapter(List<ChatRoom> listMessage, Context context,String timemess,String contentmess) {

        this.listMessage = listMessage;
        this.context = context;
        this.timemess = timemess;
        this.contentmess = contentmess;
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
        holder.lastMessage.setText(contentmess);
        holder.time.setText(timemess);
        holder.nameUser2.setText(chatRoom.getNameUser2());
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

