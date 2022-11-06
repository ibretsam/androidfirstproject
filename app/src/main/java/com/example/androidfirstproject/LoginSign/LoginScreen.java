package com.example.androidfirstproject.LoginSign;

import static android.content.Intent.EXTRA_USER;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.DB.SQLSever;
import com.example.androidfirstproject.DB.User;
import com.example.androidfirstproject.NavigationView.MessageActivity;
import com.example.androidfirstproject.R;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginuser);

        final TextInputLayout password = (TextInputLayout) findViewById(R.id.pass);
        final TextInputLayout username = (TextInputLayout) findViewById(R.id.username);
        final Button login = (Button) findViewById(R.id.login);
        final Button signup = (Button) findViewById(R.id.SignUp);
        final Button quenmk = (Button) findViewById(R.id.quenmk);


        final SQLSever sqlSever = new SQLSever(this);
        ArrayList<User> list = new ArrayList<>();
        //----------Tài Khoản Gốc-------------------
        User s = new User("giaosuhuy", "Giáo Sư Huy", "hqhlucifer666@gmail.com", "giaosuhuy", "CEO", 3);
        sqlSever.AddUser(s);
        //---------------------------------------------------------------------------

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSignUp();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                if(name.equals("") || pass.equals("")){
                    Toast.makeText( LoginScreen.this, "Vui Lòng Điền Đủ Thông tin!!!", Toast.LENGTH_SHORT).show();
                }else{
                    User s = sqlSever.getUser(name);
                    if(s != null){
                        if(s.getPassword().equals(pass)){
                            Toast.makeText( LoginScreen.this, "Đăng nhập thành công ^.^", Toast.LENGTH_SHORT).show();
                            name.isEmpty();
                            pass.isEmpty();
                            Login(s);
                        }else {
                            name.isEmpty();
                            pass.isEmpty();
                            Toast.makeText( LoginScreen.this, "Tài khoản hoặc mật khẩu không chính xác!!!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        name.isEmpty();
                        pass.isEmpty();
                        Toast.makeText( LoginScreen.this, "Tài khoản Không Tồn tại!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenForgotPassword();
            }
        });
    }

    public void OpenSignUp(){
        Intent intent = new Intent( LoginScreen.this, SignUpScreen.class);
        startActivity(intent);
    }

    public void Login(User s){
        Intent intent = new Intent( LoginScreen.this, MessageActivity.class);
        intent.putExtra(EXTRA_USER, s.getAccount());
        startActivity(intent);
        finish();
    }

    public void OpenForgotPassword(){
        Intent intent = new Intent( LoginScreen.this, ForgotPassword.class);
        startActivity(intent);
    }


}
