package com.example.createnote.ui.list;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.createnote.R;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.sqlite.DatabaseException;
import com.example.createnote.ui.adapter.NoteAdapter;
import com.example.createnote.ui.editor.NoteActivity;
import com.example.createnote.ui.editor.NoteActivityFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    protected void onActivityResult(int request, int result, @Nullable Intent i){

        Toast.makeText(this, "Restart apps to see changes", Toast.LENGTH_LONG).show();

        Note oldNote = i.getParcelableExtra("StartingNote");
        Note newNote = i.getParcelableExtra("newNote");

        NoteListActivityFragment fragment = (NoteListActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        //snackbar
        if (oldNote != null) {
            Snackbar snackbar = Snackbar
                    .make(fragment.root, "Would you like to undo your changes?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NoteDatabaseHandler dbHandler = new NoteDatabaseHandler(fragment.getContext());
                            try {
                                dbHandler.getNoteTable().delete(newNote);
                                dbHandler.getNoteTable().create(oldNote);
                            } catch (DatabaseException e) {
                                e.printStackTrace();
                            }

                            Snackbar sb2 = Snackbar.make(fragment.root, "Undo Successful", 4000);
                            sb2.show();
                        }
                    });
            snackbar.show();
        }
    }

}
