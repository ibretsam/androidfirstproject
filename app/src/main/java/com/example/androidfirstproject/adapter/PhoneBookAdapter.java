package com.example.androidfirstproject.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.androidfirstproject.Models.ChatRoom;
import com.example.androidfirstproject.Models.User;
import com.example.androidfirstproject.R;
import com.example.androidfirstproject.Views.NavigationViews.PhoneBookActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.m;

import java.time.LocalTime;
import java.util.List;

public class PhoneBookAdapter extends BaseAdapter {

    private List<User> listUser;
    private Context context;

    public PhoneBookAdapter(List<User> listUser, Context context) {
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
        View view = _view;

        ImageView imageUser = null;
        if (view == null) {
            view = View.inflate(_viewGroup.getContext(), R.layout.item_phonebook, null);
            ImageView edit = view.findViewById(R.id.editPhoneBook);
            ImageView delete = view.findViewById(R.id.deletePhoneBook);
            TextView nameUser = view.findViewById(R.id.nameUser);
//            imageUser = view.findViewById(R.id.imagviewPhone);

            PhoneBookAdapter.ViewHolder holder = new PhoneBookAdapter.ViewHolder(nameUser, edit, delete, imageUser);
            view.setTag(holder);


        }
        User user = (User) getItem(_i);
        PhoneBookAdapter.ViewHolder holder = (PhoneBookAdapter.ViewHolder) view.getTag();
        holder.nameUser.setText(user.getFullName());
//      Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageUser);

//        holder.Glide.with(view).load(user.getPicture()).into(imageUser);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpdateDialog(Gravity.CENTER, user.getFullName());
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query getKey = ref.child("user").orderByChild("fullName").equalTo(user.getFullName());
                //getkey orderby child
                getKey.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot keySnapshot : dataSnapshot.getChildren()) {
                            keySnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });

        return view;
    }

    private static class ViewHolder {
        TextView nameUser;
        ImageView edit, delete, imageUser;

        public ViewHolder(TextView nameUser, ImageView edit, ImageView delete, ImageView imageUser) {
            this.nameUser = nameUser;
            this.edit = edit;
            this.delete = delete;
            this.imageUser = imageUser;
        }
    }

    private void openUpdateDialog(int gravity, String user) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_update);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);


        EditText edtNameUpdate = dialog.findViewById(R.id.edt_name_update);
        EditText edtImageUpdate = dialog.findViewById(R.id.edt_image_update);
        Button btnUpdateUser = dialog.findViewById(R.id.btnUpdate);
        Button btnCanel = dialog.findViewById(R.id.btnCancel);
        final Handler heHandler = new Handler();

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query getKey = ref.child("user").orderByChild("fullName").equalTo(user);
                String name = edtNameUpdate.getText().toString();
                String image = edtImageUpdate.getText().toString();

                //getkey orderby child
                getKey.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot keySnapshot : dataSnapshot.getChildren()) {
                            keySnapshot.getRef().child("fullName").setValue(name);
                            keySnapshot.getRef().child("picture").setValue(image);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
                heHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 400);
            }
        });
        btnCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
