package com.example.hadasp.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class ListActivity extends AppCompatActivity implements ListAdapter.ListAdapterInteraction{

    private ListAdapter mListAdapter;
    private RecyclerView recyclerView;
    private TextView mBtnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        mBtnAddNote = findViewById(R.id.btnAddNote);
        mBtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddListBtnClick();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv_lists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mListAdapter = new ListAdapter(this, getList());
        recyclerView.setAdapter(mListAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = (int) viewHolder.itemView.getTag();
                List currentList = getList().get(position);
                Log.e("ListActivity", position + "=" + direction);
                onDeleteList(currentList);
                java.util.List<List> updatedList = getList();
                mListAdapter.updateList(updatedList);
            }

        }).attachToRecyclerView(recyclerView);


    }
    private void onAddListBtnClick() {

        AppDatabase.getInstance(this).listDao().insertList( createList("List"));

        java.util.List<List> updatedList = getList();
        mListAdapter.updateList(updatedList);
    }

    public java.util.List<List> getList() {
        return AppDatabase.getInstance(this).listDao().getAllLists();
    }

    private List createList(String title) {
        List list = new List();
        list.setTitle(title);
        return list;
    }

    public void onDeleteList(List list) {
        java.util.List<Note> notes =
                AppDatabase.getInstance(this).noteDao().getNotesByList(list.getId());
        for (int i =0; i< notes.size(); i++){
            AppDatabase.getInstance(this).noteDao().delete(notes.get(i));
        }
        AppDatabase.getInstance(this).listDao().deleteList(list);

    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    public void onUpdateList(List list) {
        AppDatabase.getInstance(this).listDao().update(list);
    }
}
