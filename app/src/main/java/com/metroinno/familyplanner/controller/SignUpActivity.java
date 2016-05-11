package com.metroinno.familyplanner.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.User;
import com.metroinno.familyplanner.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private Firebase mRef;
    private String mUserName, mUserEmail, mPassword;
    private EditText txtUsername,txtEmail,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        mRef= new Firebase(Constants.FIREBASE_URL);

        txtUsername =(EditText) findViewById(R.id.txt_username_create);
        txtEmail =(EditText) findViewById(R.id.txt_email_create);
        txtPassword =(EditText) findViewById(R.id.txt_password_create);

    }



    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }

    public void startHomeActivity() {
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }


    public void onSignUpClicked(View view) {
        mUserName = txtUsername.getText().toString();
        mUserEmail = txtEmail.getText().toString().toLowerCase();
        mPassword = txtPassword.getText().toString();

        mRef.createUser(mUserEmail, mPassword,  new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                String uid = (String) result.get("uid");
                 createUserInFirebaseHelper(uid);
                startLoginActivity();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                /* Error occurred, log the error and dismiss the progress dialog */
                 /* Display the appropriate error message */
                }
        });
    }

    private void createUserInFirebaseHelper(String uid) {

        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(uid);
        /**
         +    * See if there is already a user (for example, if they already logged in with an associated
         +         * Google account.
         +         */
        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                         /* If there is no user, make one */
                if (dataSnapshot.getValue() == null) {

                    User newUser = new User(mUserName, mUserEmail);
                    userLocation.setValue(newUser);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }

    public void onLogInTextClicked(View view) {
        startLoginActivity();
    }
}
