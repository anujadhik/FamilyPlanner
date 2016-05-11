package com.metroinno.familyplanner.model;

/**
 * Created by anuj on 3/30/16.
 */
public class TodoListItem {
    private String itemName;
    private String owner;
    private String doneBy;
    private boolean done;

    public TodoListItem(){}

    public TodoListItem(String itemName, String owner) {
        this.itemName = itemName;
        this.owner = owner;
        this.doneBy = null;
        this.done = false;
    }

    public String getItemName() {
        return itemName;
    }

    public String getOwner() {
        return owner;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public boolean isDone() {
        return done;
    }
}
