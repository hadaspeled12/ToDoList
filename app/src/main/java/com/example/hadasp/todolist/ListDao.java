package com.example.hadasp.todolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by hadasp on 17/12/2017.
 */

@Dao
public interface ListDao {
    @Query("SELECT * FROM Lists")
    java.util.List<List> getAllLists();

    @Query("SELECT * FROM Lists WHERE id = :id")
    List getListById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllLists(List... lists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List list);

    @Delete
    void deleteList(List list);

    @Update
    void update(List list);
}
