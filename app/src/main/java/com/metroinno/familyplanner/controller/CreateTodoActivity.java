package com.metroinno.familyplanner.controller;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.EventList;
import com.metroinno.familyplanner.model.TodoList;
import com.metroinno.familyplanner.utils.Constants;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CreateTodoActivity extends AppCompatActivity {

    EditText txtTodoTitle, txtTodoDetails;
    FloatingActionButton fab;
    private Firebase mRef, mListRef;
    private TodoList mTodoList;

    String userName;
    SharedPreferences pref;

    private String listId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        pref = getSharedPreferences(Constants.MY_PREFS_NAME, 0);

        userName = pref.getString("name", null);//"No name defined" is the default value.

        txtTodoTitle = (EditText) findViewById(R.id.txt_todo_title);
        txtTodoDetails=(EditText) findViewById(R.id.txt_todo_details);

        mRef = new Firebase(Constants.FIREBASE_URL_TASKS);

        //Get push id and fetching data

        Intent intent = this.getIntent();
        listId = intent.getStringExtra("TODO_LIST_ID");


        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTodoDetails.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(txtTodoDetails, InputMethodManager.SHOW_IMPLICIT);
            }
        });




        if (listId != null) {

            mListRef = mRef.child(listId);

            mListRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TodoList todoList = dataSnapshot.getValue(TodoList.class);
                    if (todoList == null) {
                        finish();
                        return;
                    }
                    mTodoList = todoList;

                    txtTodoTitle.setText(todoList.getTodoListName());
                    txtTodoDetails.setText(todoList.getTodoListDetails());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(listId !=null){
            getMenuInflater().inflate(R.menu.menu_list_edit,menu);
        }else{
        getMenuInflater().inflate(R.menu.menu_todolist, menu);}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_todolist) {
            addTodoList();
        }

        if(id == R.id.action_delete_list){
            removeList();
        }

        if (id == R.id.action_save_list_edit){
            saveEditedList();
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeList() {

        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            Firebase listDeleteRef = new Firebase(Constants.FIREBASE_URL_TASKS).child(listId);
                            listDeleteRef.removeValue();
                            Toast.makeText(CreateTodoActivity.this,"List deleted",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("List will be deleted")
                .setPositiveButton("Ok", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener)
                .show();



    }

    private void saveEditedList() {
        String todoListName = txtTodoTitle.getText().toString();
        String todoListOwner = userName ;//(String) mRef.getAuth().getProviderData().get("email");
        String todoDate = DateFormat.getDateTimeInstance().format(new Date());
        String todoListDetail = txtTodoDetails.getText().toString();

        if(todoListName != null){
            Firebase newListRef = new Firebase(Constants.FIREBASE_URL_TASKS).child(listId);

            TodoList newTodoList = new TodoList(todoListName, todoListOwner, todoDate, todoListDetail);
            newListRef.setValue(newTodoList);
            finish();
            Toast.makeText(CreateTodoActivity.this,"List saved",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void addTodoList() {
        String todoListName = txtTodoTitle.getText().toString();
        String todoListOwner = userName ;//(String) mRef.getAuth().getProviderData().get("email");
        String todoDate = DateFormat.getDateTimeInstance().format(new Date());
        String todoListDetail = txtTodoDetails.getText().toString();

        if (!todoListName.equals("")) {

            Firebase newTodoRef = mRef.push();

            final String todoListId = newTodoRef.getKey();

            TodoList newTodoList = new TodoList(todoListName, todoListOwner, todoDate, todoListDetail);
            newTodoRef.setValue(newTodoList);
            finish();
            Toast.makeText(CreateTodoActivity.this,"List created",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void onCloseClicked(View view) {
        finish();
    }
}
