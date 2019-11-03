package com.example.createnote.ui.list;

import android.content.Intent;
import android.os.Bundle;

import com.example.createnote.R;
import com.example.createnote.model.Note;
import com.example.createnote.ui.editor.NoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionButton fab = findViewById(R.id.fab);

        //TODO
        fab.setOnClickListener(new FloatingActionButton.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent i = new Intent(view.getContext(), NoteActivity.class);
            i.putExtra("Note", new Note());
            view.getContext().startActivity(i);
        }
        });
    }

}
