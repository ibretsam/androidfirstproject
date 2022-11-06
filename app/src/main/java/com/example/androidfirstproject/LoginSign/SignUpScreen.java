package com.example.androidfirstproject.LoginSign;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.DB.SQLSever;
import com.example.androidfirstproject.DB.User;
import com.example.androidfirstproject.R;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SignUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registeruser);

        final SQLSever sqlSever = new SQLSever(this);
        final TextInputLayout fullname = (TextInputLayout) findViewById(R.id.fullname);
        final TextInputLayout account = (TextInputLayout) findViewById(R.id.Account);
        final TextInputLayout gmail = (TextInputLayout) findViewById(R.id.gmail);
        final TextInputLayout password1 = (TextInputLayout) findViewById(R.id.pass);
        final TextInputLayout password2 = (TextInputLayout) findViewById(R.id.pass2);

        Button login = (Button) findViewById(R.id.login);
        Button signup = (Button) findViewById(R.id.SignUp);


        final SQLSever sql_user = new SQLSever(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String acc= account.getEditText().getText().toString();
                final String ten= fullname.getEditText().getText().toString();
                final String Gmail = gmail.getEditText().getText().toString();
                final String mk1= password1.getEditText().getText().toString();
                final String mk2= password2.getEditText().getText().toString();
                if(ten.equals("") || Gmail.equals("")|| mk1.equals("") || mk2.equals("") || acc.equals("")){
                    Toast.makeText(SignUpScreen.this, "Vui Lòng Điền Đủ Thông tin!!!", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder b=new AlertDialog.Builder(SignUpScreen.this);
                    b.setTitle("Sign Up");
                    b.setMessage("Bạn có muốn Đăng Ký?");
                    b.setIcon(R.drawable.icons8_adduser);
                    b.setPositiveButton("Yes", new DialogInterface. OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(mk1.equals(mk2)){
                                ArrayList<User> users= sqlSever.getArrayUser();
                                if(users != null){
                                    boolean ketqua = true;
                                    for(User x : users){
                                        if(x.getAccount().equals(acc)){
                                            ketqua = false;
                                        }
                                    }
                                    if(ketqua == true){
                                        User s = new User(acc, ten, Gmail, mk1, "Độc Giả", 1);
                                        sqlSever.AddUser(s);
                                        if(s!=null){
                                            Toast.makeText(SignUpScreen.this, "Đăng Ký Thành Công ^.^", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(SignUpScreen.this, "Đăng Ký Thất Bại ^.^", Toast.LENGTH_SHORT).show();
                                            acc.isEmpty();
                                            ten.isEmpty();
                                            Gmail.isEmpty();
                                            mk1.isEmpty();
                                            mk2.isEmpty();
                                        }
                                    }else{
                                        Toast.makeText(SignUpScreen.this, "Account Đã Tồn tại!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else{
                                Toast.makeText(SignUpScreen.this, "Mật Khẩu Nhập Lại Không đúng!!!", Toast.LENGTH_SHORT).show();
                                mk1.isEmpty();
                                mk2.isEmpty();
                            }
                        }});
                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
                    b.create().show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLogin();
            }
        });
    }
    public void OpenLogin(){
        Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
        startActivity(intent);

    }
}


