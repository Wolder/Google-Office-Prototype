package com.example.genterprise.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.genterprise.CONSTANTS;
import com.example.genterprise.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private Button mSignup;
    private EditText mEmail, mPassword;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mSignup = findViewById(R.id.signin_btn);
        mEmail = findViewById(R.id.email_signup);
        mPassword = findViewById(R.id.password_signup);

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });
    }

    private void registerUser(final String email, final String password){
        Log.d(TAG, "registerUser: ");

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "onComplete: Sign up complete..." + task.isSuccessful());

                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            CONSTANTS.CURRENT_USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            CONSTANTS.CURRENT_USER_NAME = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "Sign up attempt failed...");
                        }
                    }
                });
    }
}