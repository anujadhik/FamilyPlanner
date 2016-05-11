package com.metroinno.familyplanner.controller;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.Chat;

/**
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat>{
    public ChatListAdapter(Activity activity, Class<Chat> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        //this.mActivity=activity;
    }

    @Override
    protected void populateView(View view, Chat chat) {

        TextView txtUser = (TextView) view.findViewById(R.id.txt_user);
        TextView txtMsg = (TextView) view.findViewById(R.id.txt_msg);
        TextView txtTime = (TextView) view.findViewById(R.id.txt_time);

        txtUser.setText(chat.getAuthor());
        txtMsg.setText(chat.getMsg());
        txtTime.setText(chat.getTimestampCreated());
    }
}
