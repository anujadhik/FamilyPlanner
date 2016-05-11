package com.metroinno.familyplanner.controller;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.EventList;
import com.metroinno.familyplanner.model.TodoList;

public class TodoListAdapter extends FirebaseListAdapter<TodoList> {
    public TodoListAdapter(Activity activity, Class<TodoList> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity=activity;
    }

    @Override
    protected void populateView(View view, TodoList todoList) {
        TextView txtListName = (TextView) view.findViewById(R.id.tv_todo_title);
        TextView txtListOwner = (TextView) view.findViewById(R.id.tv_owner);
        TextView txtListDate = (TextView) view.findViewById(R.id.tv_date);

        txtListName.setText(todoList.getTodoListName());
        txtListOwner.setText(todoList.getListOwner());
        txtListDate.setText(todoList.getTimestampCreated());
    }
}