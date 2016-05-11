package com.metroinno.familyplanner.controller;

import android.content.Intent;
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
import com.metroinno.familyplanner.model.TodoList;
import com.metroinno.familyplanner.utils.Constants;

public class TodoListFragment extends Fragment {

    private TodoListAdapter todoListAdapter;
    private ListView todoListView;



    public TodoListFragment() {
    }

    //Use this factory method to create a new instance of

    public static TodoListFragment newInstance() {
        TodoListFragment fragment = new TodoListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        initializeScreen(rootView);

        //Making listview
        todoListView = (ListView) rootView.findViewById(R.id.todolist_list_view);
        Firebase mTodoRef= new Firebase(Constants.FIREBASE_URL_TASKS);
        todoListAdapter = new TodoListAdapter(getActivity(), TodoList.class,R.layout.todolist_row, mTodoRef);
        todoListView.setAdapter(todoListAdapter);

        //Clicking the list item
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TodoList selectedList = todoListAdapter.getItem(i);
                if(selectedList !=null){
                    Intent intent = new Intent(getActivity(),CreateTodoActivity.class);

                    String listId= todoListAdapter.getRef(i).getKey();
                    intent.putExtra("TODO_LIST_ID",listId);
                    startActivity(intent);
                }

            }

        });


        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        todoListAdapter.cleanup();
    }


    /**
     * Link layout elements from XML
     */
    private void initializeScreen(View rootView) {
    }
}
