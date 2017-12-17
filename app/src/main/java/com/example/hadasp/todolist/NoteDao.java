package com.example.hadasp.todolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by hadasp on 10/12/2017.
 */

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Notes")
    List<Note> getAll();

    @Query("SELECT * FROM Notes WHERE id = :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM Notes WHERE listId = :listId")
    List<Note> getNotesByList(int listId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Note... notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);
}
