package com.metroinno.familyplanner.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.Chat;
import com.metroinno.familyplanner.utils.Constants;

import java.text.DateFormat;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    Firebase mRef;
    EditText txtMsg;
    ImageButton btnSend;
    private Query mChatRef;
    private ChatListAdapter chatListAdapter;
    private ListView chatList;

    private String mName;
    private EditText mMessageEdit;
    private static final String TAG = "MActivity";
    String userName;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtMsg = (EditText) findViewById(R.id.txt_message);
        btnSend = (ImageButton) findViewById(R.id.btn_send);
        mRef = new Firebase(Constants.FIREBASE_URL_CHAT);
        mChatRef = mRef.limitToLast(40);
        chatList=(ListView)findViewById(R.id.listView);




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pref = getSharedPreferences(Constants.MY_PREFS_NAME, 0);

                userName = pref.getString("name", null);//"No name defined" is the default value.


                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Chat chat = new Chat(txtMsg.getText().toString(), userName, currentDateTimeString);
                mRef.push().setValue(chat, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        //if (firebaseError != null) {
                            //Log.e(TAG, firebaseError.toString());
                        //}
                    }
                });
                txtMsg.setText("");
            }
        });

        chatListAdapter=new ChatListAdapter(this,Chat.class,R.layout.chat_row,mRef);
        chatList.setAdapter(chatListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatListAdapter.cleanup();
    }
}
