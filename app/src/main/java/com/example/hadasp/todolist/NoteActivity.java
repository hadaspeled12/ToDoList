package com.example.hadasp.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class NoteActivity extends AppCompatActivity implements NoteAdapter.NotesAdapterInteraction {

    private NoteAdapter mNotesAdapter;
    private RecyclerView recyclerView;
    private TextView mBtnAddNote;
    private String mNameOfList;
    private int mIdOfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        mBtnAddNote = findViewById(R.id.btnAddNote);
        mBtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNoteBtnClick();
            }
        });

        mNameOfList = getIntent().getStringExtra(ListAdapter.name);
        mIdOfList = getIntent().getIntExtra(ListAdapter.id, 0);

        recyclerView = (RecyclerView) findViewById(R.id.rv_notes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mNotesAdapter = new NoteAdapter(this, getNoteList());
        recyclerView.setAdapter(mNotesAdapter);

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
                Note currentNote = getNoteList().get(position);
                Log.e("NoteActivity", position + "=" + direction);
                onDeleteNote(currentNote);
                List<Note> updatedList = getNoteList();
                mNotesAdapter.updateList(updatedList);
            }

        }).attachToRecyclerView(recyclerView);


    }

    private void onAddNoteBtnClick() {

        AppDatabase.getInstance(this).noteDao().insert( createNote( ""));

        List<Note> updatedList = getNoteList();
        mNotesAdapter.updateList(updatedList);
    }

    public List<Note> getNoteList() {
        return AppDatabase.getInstance(this).noteDao().getNotesByList(mIdOfList);
    }

    private Note createNote(String body) {
        Note note = new Note();
        note.setBody(body);
        note.setChecked(false);
        note.setListId(mIdOfList);
        return note;
    }


    public void onDeleteNote(Note note) {
        AppDatabase.getInstance(this).noteDao().delete(note);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    public void onUpdateNote(Note note) {
        AppDatabase.getInstance(this).noteDao().update(note);
    }
}
