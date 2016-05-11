package com.metroinno.familyplanner.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.utils.Constants;

public class LoginActivity extends AppCompatActivity {

    private Firebase mRef;
    private String mUserEmail, mPassword;
    private EditText txtEmail,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        if(savedInstanceState == null){
            Firebase.setAndroidContext(this);
        }

        mRef= new Firebase(Constants.FIREBASE_URL);
        txtEmail =(EditText) findViewById(R.id.txt_email);
        txtPassword =(EditText) findViewById(R.id.txt_password);

        if(mRef.getAuth() != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }

    }
    public void startSignUpActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }


    public void onLogInClicked(View view) {
        LogIn();
    }

    private void LogIn() {
        mUserEmail=txtEmail.getText().toString();
        mPassword=txtPassword.getText().toString();

        mRef.authWithPassword(mUserEmail, mPassword, new MyAuthResultHandler());

    }

    private class MyAuthResultHandler implements Firebase.AuthResultHandler {
        @Override
        public void onAuthenticated(AuthData authData) {
            if (authData != null) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
           }
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {

        }
    }
}
