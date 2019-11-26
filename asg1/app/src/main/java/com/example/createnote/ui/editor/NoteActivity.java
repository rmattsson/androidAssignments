package com.example.createnote.ui.editor;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.createnote.R;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.networking.HttpRequest;
import com.example.createnote.networking.HttpRequestTask;
import com.example.createnote.networking.HttpResponse;
import com.example.createnote.networking.OnErrorListener;
import com.example.createnote.networking.OnResponseListener;
import com.example.createnote.sqlite.DatabaseException;
import com.example.createnote.ui.NotesApplication;
import com.example.createnote.ui.list.NoteListActivity;
import com.example.createnote.ui.util.DisplayUsersFragment;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private Note StartingNote;
    private Note newNote;
    private NoteActivityFragment fragment;
    private DisplayUsersFragment usersFragment;
    private static final int SHARE = Menu.FIRST;
    NotesApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        application = (NotesApplication) getApplication();



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





        //update/create the current note


            //delete the note from the database
            //handler.getNoteTable().delete(StartingNote);

            //put it back in the database!
            //handler.getNoteTable().update(newNote);

        HttpRequest request = new HttpRequest(application.getURL() + "/note/" + newNote.getUuid() , HttpRequest.Method.PUT);
        request.setRequestBody("application/json",newNote.format());
        HttpRequestTask task = new HttpRequestTask();
        task.setOnResponseListener(new OnResponseListener<HttpResponse>() {
            @Override
            public void onResponse(HttpResponse d) {
                //Toast.makeText(fragment.getContext(), d.toString(), Toast.LENGTH_LONG).show();
            }
        });
        task.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(Exception error) {
                Toast.makeText(fragment.getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        task.execute(request);




        setResult(Activity.RESULT_OK, getIntent());
        finish();

        return super.onOptionsItemSelected(item);
    }

}
