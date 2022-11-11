package com.example.androidfirstproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;

import java.util.List;

public class PhoneBookAdapter extends BaseAdapter {

    private List<User> listUser;
    private Context context;
    public PhoneBookAdapter(List<User> listUser, Context context){
        this.context = context;
        this.listUser = listUser;
    }


    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {
        View view =_view;
        if(view == null){
            view = View.inflate(_viewGroup.getContext(), R.layout.item_phonebook,null);
            TextView nameUser = view.findViewById(R.id.nameUser);
           PhoneBookAdapter.ViewHolder holder = new PhoneBookAdapter.ViewHolder(nameUser);
            view.setTag(holder);
        }
        User user= (User) getItem(_i);
        PhoneBookAdapter.ViewHolder holder = (PhoneBookAdapter.ViewHolder) view.getTag();
        holder.nameUser.setText(user.getFullName());
        return view;


    }

    private static class ViewHolder{
        TextView nameUser;
        public ViewHolder (TextView nameUser){
            this.nameUser= nameUser;
        }
    }

}
