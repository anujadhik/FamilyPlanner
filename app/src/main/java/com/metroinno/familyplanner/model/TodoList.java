package com.metroinno.familyplanner.model;

import java.util.HashMap;

/**
 * Created by anuj on 3/30/16.
 */
public class TodoList {
    private String todoListName;
    private String listOwner;
    private String timestampCreated;
    private String todoListDetails;

    public TodoList(){}

    public TodoList(String todoListName, String listOwner, String timestampCreated, String todoListDetails) {
        this.todoListName = todoListName;
        this.listOwner = listOwner;
        this.timestampCreated = timestampCreated;
        this.todoListDetails = todoListDetails;
    }

    public String getTodoListDetails() {
        return todoListDetails;
    }

    public String getTodoListName() {
        return todoListName;
    }

    public String getListOwner() {
        return listOwner;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }
}
