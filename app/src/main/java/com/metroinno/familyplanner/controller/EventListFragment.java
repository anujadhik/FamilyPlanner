package com.metroinno.familyplanner.controller;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.metroinno.familyplanner.R;
import com.metroinno.familyplanner.model.EventList;
import com.metroinno.familyplanner.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment {


    private EventListAdapter eventListAdapter;
    private ListView eventListView;



    public EventListFragment() {
    }

    //Use this factory method to create a new instance of

    public static EventListFragment newInstance() {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
        initializeScreen(rootView);

        //Making listview
        eventListView = (ListView) rootView.findViewById(R.id.event_list_view);
        Firebase mEventRef= new Firebase(Constants.FIREBASE_URL_EVENTS);
        eventListAdapter = new EventListAdapter(getActivity(), EventList.class,R.layout.event_row, mEventRef);
        eventListView.setAdapter(eventListAdapter);

        //Clicking the list item
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventList selectedList = eventListAdapter.getItem(i);
                if(selectedList !=null){
                    Intent intent = new Intent(getActivity(),CreateEventActivity.class);

                    String listId= eventListAdapter.getRef(i).getKey();
                    intent.putExtra("LIST_ID",listId);
                    startActivity(intent);
                }

            }

        });


        return rootView;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventListAdapter.cleanup();
    }

    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
    }
}
