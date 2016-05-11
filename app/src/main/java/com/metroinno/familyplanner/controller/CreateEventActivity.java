package com.metroinno.familyplanner.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.EventList;
import com.metroinno.familyplanner.utils.Constants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtEventTitle,txtDate, txtTime, txtPlace, txtInfo;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public int yr,month,day,hour,min;
    private String listId;
    private EventList mEventList;
    CoordinatorLayout co;

    private Firebase mRef,mListRef;

    String userName;
    SharedPreferences pref;

    private static final String TAG = "MyA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        co = (CoordinatorLayout) findViewById(R.id.eco_layout);
        txtEventTitle= (EditText)findViewById(R.id.txt_event_title);
        txtInfo= (EditText)findViewById(R.id.txt_info);
        txtPlace= (EditText)findViewById(R.id.txt_place);

        txtDate = (EditText) findViewById(R.id.txt_date);
        txtTime = (EditText) findViewById(R.id.txt_time);


        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);


        mRef= new Firebase(Constants.FIREBASE_URL_EVENTS);

        pref = getSharedPreferences(Constants.MY_PREFS_NAME, 0);

        userName = pref.getString("name", null);//"No name defined" is the default value.


        //Get push id and fetching data

        Intent intent = this.getIntent();
        listId = intent.getStringExtra("LIST_ID");
        if (listId != null) {

            mListRef = mRef.child(listId);

            mListRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    EventList eventList = dataSnapshot.getValue(EventList.class);
                    if (eventList == null) {
                        finish();
                        return;
                    }
                    mEventList = eventList;

                    txtEventTitle.setText(eventList.getEventListName());
                    txtDate.setText(eventList.getDate());
                    txtTime.setText(eventList.getTime());
                    txtPlace.setText(eventList.getPlace());
                    txtInfo.setText(eventList.getInfo());

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
            getMenuInflater().inflate(R.menu.menu_edit,menu);
        }else{

        getMenuInflater().inflate(R.menu.menu_event, menu);}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_event) {
            addEvent();
        }

        if (id == R.id.action_save_edit){
            saveEditedEvent();
        }

        if (id == R.id.action_delete){
            removeEvent();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveEditedEvent() {
        String eventName = txtEventTitle.getText().toString();
        String eventOwner = userName;//(String)mRef.getAuth().getProviderData().get("email");
        String eventDate= txtDate.getText().toString();
        String eventTime= txtTime.getText().toString();
        String eventPlace= txtPlace.getText().toString();
        String eventInfo= txtInfo.getText().toString();

        if(eventName != null){
            Firebase newEventRef = new Firebase(Constants.FIREBASE_URL_EVENTS).child(listId);

            EventList newEventList = new EventList(eventName,eventOwner,eventDate,eventTime,eventPlace,eventInfo);
            newEventRef.setValue(newEventList);

            Toast.makeText(CreateEventActivity.this,"Event saved",Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    private void removeEvent() {

        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            Firebase eventDeleteRef = new Firebase(Constants.FIREBASE_URL_EVENTS).child(listId);
                            eventDeleteRef.removeValue();
                            Toast.makeText(CreateEventActivity.this,"Event deleted",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Event will be deleted")
                .setPositiveButton("OK", dialogClickListener)
                .setNegativeButton("CANCEL", dialogClickListener)
                .show();


        /*Firebase eventDeleteRef = new Firebase(Constants.FIREBASE_URL_EVENTS).child(listId);
        eventDeleteRef.removeValue();
        Toast.makeText(CreateEventActivity.this,"Event deleted",Toast.LENGTH_SHORT).show();
        finish();*/
    }

    public void addEvent() {
        String eventName = txtEventTitle.getText().toString();
        String eventOwner = userName;//(String)mRef.getAuth().getProviderData().get("email");
        String eventDate= txtDate.getText().toString();
        String eventTime= txtTime.getText().toString();
        String eventPlace= txtPlace.getText().toString();
        String eventInfo= txtInfo.getText().toString();

        if(eventName != null){
            Firebase newEventRef = mRef.push();

            final String eventListId = newEventRef.getKey();

            EventList newEventList = new EventList(eventName,eventOwner,eventDate,eventTime,eventPlace,eventInfo);
            newEventRef.setValue(newEventList);

            Toast.makeText(CreateEventActivity.this,"Event Created",Toast.LENGTH_SHORT).show();
            finish();
        }

        Log.i(TAG,"off"+day);
    }

    @Override
    public void onClick(View v) {
        if (v == txtDate) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            day=dayOfMonth;

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        }


        if (v == txtTime) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }


    public void closeActivity(View view) {
        finish();
    }
}
