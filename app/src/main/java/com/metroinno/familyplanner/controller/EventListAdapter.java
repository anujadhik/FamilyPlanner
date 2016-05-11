package com.metroinno.familyplanner.controller;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.EventList;


public class EventListAdapter extends FirebaseListAdapter<EventList> {
    public EventListAdapter(Activity activity, Class<EventList> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View view, EventList event) {

        TextView txtEventName = (TextView) view.findViewById(R.id.tview_event_name);
        TextView txtEventOwner = (TextView) view.findViewById(R.id.tview_owner);
        TextView txtEventDate = (TextView) view.findViewById(R.id.tview_date);
        TextView txtEventTime= (TextView) view.findViewById(R.id.tview_time);
        TextView txtEventPlace= (TextView) view.findViewById(R.id.tview_place);


        txtEventName.setText(event.getEventListName());
        txtEventOwner.setText(event.getOwner());
        txtEventDate.setText(event.getDate());
        txtEventTime.setText(event.getTime());
        txtEventPlace.setText(event.getPlace());



    }
}

