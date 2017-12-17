package com.example.hadasp.todolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by hadasp on 17/12/2017.
 */

@Entity(tableName = "Lists")
public class List {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
