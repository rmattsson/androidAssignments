package com.example.createnote.ui.editor;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.example.createnote.R;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.sqlite.DatabaseException;
import com.example.createnote.ui.list.NoteListActivity;
import com.example.createnote.ui.util.DisplayUsersFragment;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private Note StartingNote;
    private Note newNote;
    private NoteActivityFragment fragment;
    private DisplayUsersFragment usersFragment;
    private static final int SHARE = Menu.FIRST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ActionBar actionBar = getActionBar();

        //actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fragment = (NoteActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        StartingNote = fragment.getNote();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_note, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.share_button)
        {
                String data = fragment.getNote().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
        }


        newNote = fragment.getNote();

        //update the note
        newNote.setModified(new Date());


        //back button pressed
//        Intent myIntent = new Intent(getApplicationContext(), NoteListActivity.class);
        getIntent().putExtra("StartingNote", StartingNote);
        getIntent().putExtra("newNote", newNote);
//        startActivityForResult(myIntent, 0);
        NoteDatabaseHandler handler = new NoteDatabaseHandler(fragment.getContext());




        //update/create the current note
        try {

            //delete the note from the database
            //handler.getNoteTable().delete(StartingNote);

            //put it back in the database!
            handler.getNoteTable().update(newNote);


        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        setResult(Activity.RESULT_OK, getIntent());
        finish();

        return super.onOptionsItemSelected(item);
    }

}
