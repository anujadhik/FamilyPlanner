package com.metroinno.familyplanner.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.utils.Constants;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Tracker extends AppCompatActivity implements BeaconManager.MonitoringListener{

    ListView listView;
    Firebase mTrackerRef;

    BeaconManager beaconManager;
    Region region;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        listView = (ListView)findViewById(R.id.list_tracker);
        mTrackerRef= new Firebase(Constants.FIREBASE_URL_USER_TRACKER);


        ////stuff for beacon
     beaconManager = new BeaconManager(getApplicationContext());
      region = new Region("rid", null, 1808, null);
        beaconManager.setMonitoringListener(this);
            ////

        FirebaseListAdapter<String> adapter= new FirebaseListAdapter<String>(
                this,String.class,android.R.layout.simple_list_item_1,mTrackerRef
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView text= (TextView)v.findViewById(android.R.id.text1);
                text.setText(model);
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Should be invoked in #onStart.
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                // Beacons ranging.
                beaconManager.startMonitoring(region);
                Log.d("testbeacon", "Start Monitoring");
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.disconnect();
    }


    @Override
    public void onEnteredRegion(Region region, List<Beacon> list) {
        Log.d("testbeacon", "Reginon was entered");
        SharedPreferences pref = getSharedPreferences(Constants.MY_PREFS_NAME, 0);
        String userName = pref.getString("name", null);
        String date = DateFormat.getDateTimeInstance().format(new Date());
        Firebase newRef = mTrackerRef.push();
        id = newRef.getKey();

        newRef.setValue(userName+" entered home" +"\n" + date);


    }

    @Override
    public void onExitedRegion(Region region) {
        Log.d("testbeacon", "Reginon was left");
        SharedPreferences pref = getSharedPreferences(Constants.MY_PREFS_NAME, 0);
        String userName = pref.getString("name", null);
        String date = DateFormat.getDateTimeInstance().format(new Date());

        mTrackerRef.push().setValue(userName+" left home" +"\n" + date);

    }
}
