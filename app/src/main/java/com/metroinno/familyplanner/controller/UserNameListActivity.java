package com.metroinno.familyplanner.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.EventList;
import com.metroinno.familyplanner.model.User;
import com.metroinno.familyplanner.utils.Constants;

public class UserNameListActivity extends AppCompatActivity {

    Firebase mUserListRef;
    private UserListAdapter userListAdapter;
    private ListView userNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_list);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Members");

        mUserListRef=new Firebase(Constants.FIREBASE_URL_USERS);

        //Making listview
        userNameList = (ListView) findViewById(R.id.user_name_list);
        userListAdapter = new UserListAdapter(this, User.class,R.layout.user_row, mUserListRef);
        userNameList.setAdapter(userListAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userListAdapter.cleanup();
    }
}
