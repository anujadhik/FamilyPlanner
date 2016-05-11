package com.metroinno.familyplanner.model;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by anuj on 4/20/16.
 */
public class Chat {
    private String msg;
    private String author;
    private String timestampCreated;

    public Chat(){}

    public Chat(String msg, String author, String timestampCreated) {
        this.msg = msg;
        this.author = author;
        this.timestampCreated = timestampCreated;
    }

    public String getMsg() {
        return msg;
    }

    public String getAuthor() {
        return author;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }
}
