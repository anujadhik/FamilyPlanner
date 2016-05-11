package com.metroinno.familyplanner.model;

import java.util.HashMap;

/**
 * Created by anuj on 3/30/16.
 */
public class TodoList {
    private String todoListName;
    private String listOwner;
    private String timestampCreated;

    public TodoList(){}

    public TodoList(String todoListName, String listOwner, String timestampCreated) {
        this.todoListName = todoListName;
        this.listOwner = listOwner;
        this.timestampCreated = timestampCreated;
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
