package com.example.hadasp.todolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by hadasp on 10/12/2017.
 */

@Entity(tableName = "Notes" , foreignKeys = @ForeignKey(entity = List.class,
        parentColumns = "id",
        childColumns = "listId"))
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private boolean checked;

    private String body;

    public int listId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

}
