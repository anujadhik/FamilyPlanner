package com.metroinno.familyplanner.controller;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.Chat;
import com.metroinno.familyplanner.model.User;


public class UserListAdapter extends FirebaseListAdapter<User> {
    public UserListAdapter(Activity activity, Class<User> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View view, User user) {
        TextView txtUser = (TextView) view.findViewById(R.id.txt_user_name);
        TextView txtMsg = (TextView) view.findViewById(R.id.txt_user_email);

        txtUser.setText(user.getName());
        txtMsg.setText(user.getEmail());
    }
}
