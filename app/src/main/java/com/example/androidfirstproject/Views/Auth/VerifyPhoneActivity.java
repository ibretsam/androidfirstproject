package com.example.androidfirstproject.Views.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidfirstproject.R;
import com.example.androidfirstproject.Views.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    EditText edtOTP;
    Button btnResend, btnVerify;
    String verificationID, phone;
    FirebaseAuth rAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        edtOTP = findViewById(R.id.edtOTP);
        btnResend = findViewById(R.id.btnResend);
        btnVerify = findViewById(R.id.btnVerify);

        rAuth = FirebaseAuth.getInstance();


        Bundle phoneNumberBundle = getIntent().getExtras();
        if (phoneNumberBundle != null) {
            phone = phoneNumberBundle.getString("phoneNumber");
        }

        sendVerificationCode(phone);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = edtOTP.getText().toString().trim();
                if (TextUtils.isEmpty(otp)) {
                    edtOTP.setError("Please enter your OTP.");
                    return;
                }

                verifyCode(otp);
            }
        });
    }

    // Hàm gửi OTP qua số điện thoại
    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(rAuth)
                        .setPhoneNumber(phoneNumber)       // Set số điện thoại
                        .setTimeout(60L, TimeUnit.SECONDS) // Set thời gian chờ
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                final String code = credential.getSmsCode();
                if (code != null) {
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(VerifyPhoneActivity.this, "Verification Failed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, token);
                verificationID = s;
                Toast.makeText(VerifyPhoneActivity.this, "OTP code has sent to your phone number", Toast.LENGTH_SHORT);
            }
        };

        private void verifyCode (String code){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
            signInByCredentials(credential);
        }

        private void signInByCredentials (PhoneAuthCredential credential){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(VerifyPhoneActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(VerifyPhoneActivity.this, HomeActivity.class));
                            }
                        }
                    });
        }
    }