package com.example.createnote.ui.list;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.createnote.R;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.model.SampleData;
import com.example.createnote.sqlite.DatabaseException;
import com.example.createnote.ui.adapter.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A fragment containing a view for a list of notes
 */
public class NoteListActivityFragment extends Fragment {

    public NoteListActivityFragment() {
    }

    //Data is initiallized be should receive notes from a database later on.
    List<Note> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_note_list, container, false);

        //get the spinner, recycler view and the database handler
        final Spinner noteSpinner = root.findViewById(R.id.note_Spinner);
        final RecyclerView noteRecycler = root.findViewById(R.id.note_RecyclerView);

        NoteDatabaseHandler dbHandler = new NoteDatabaseHandler(getContext());

        try {
            //populate data list with notes from the database
            data = dbHandler.getNoteTable().readAll();
        } catch (DatabaseException e) {

            e.printStackTrace();
        }

        //create the adapter
        final NoteAdapter adapter = new NoteAdapter(data, this, dbHandler);

        //sort notes by title as default
        sortNotes(noteRecycler, adapter, 0, data);

        noteRecycler.setAdapter(adapter);

        //set a listener for when the selected item changes, inner class will call sortNotes()
        noteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortNotes(noteRecycler, adapter, l, data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return root;
    }

    //this method sorts notes based on which spinner item is selected, the choice is sent as a long
    private void sortNotes (RecyclerView recycler,NoteAdapter adapter, long l, List<Note> data)
    {

        if (l==0) {

            //sort by title
            data.sort(new Comparator<Note>() {
                @Override
                public int compare(Note lhs, Note rhs) {
                    return (lhs.getTitle().compareTo(rhs.getTitle()));
                }
            });
        }
        else if (l==1) {

            //sort by Creation Date
            data.sort(new Comparator<Note>() {
                @Override
                public int compare(Note lhs, Note rhs) {
                    return (lhs.getCreated().compareTo(rhs.getCreated()));
                }
            });
        }
        else if (l==2) {

            //sort by Last Modified
            data.sort(new Comparator<Note>() {
                @Override
                public int compare(Note lhs, Note rhs) {
                    return (lhs.getModified().compareTo(rhs.getModified()));
                }
            });
        }
        else if (l==3) {

            //sort by Reminder
            data.sort(new Comparator<Note>() {
                @Override
                public int compare(Note lhs, Note rhs) {


                    if (!lhs.isHasReminder())
                    {
                        return 1;

                    }
                    if (!rhs.isHasReminder())
                    {
                        return -1;
                    }

                        return (lhs.getReminder().compareTo(rhs.getReminder()));

                }
            });
        }
        else if (l==4) {

            //sort by Category
            data.sort(new Comparator<Note>() {
                @Override
                public int compare(Note lhs, Note rhs) {
                    return (lhs.getCategory().compareTo(rhs.getCategory()));
                }
            });
        }

        //redraw the page
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter.notifyDataSetChanged();
    }


}
