package com.example.createnote.ui.list;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.createnote.networking.HttpRequest;
import com.example.createnote.networking.HttpRequestTask;
import com.example.createnote.networking.HttpResponse;
import com.example.createnote.networking.OnErrorListener;
import com.example.createnote.networking.OnResponseListener;
import com.example.createnote.sqlite.DatabaseException;
import com.example.createnote.ui.NotesApplication;
import com.example.createnote.ui.adapter.NoteAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A fragment containing a view for a list of notes
 */
public class NoteListActivityFragment extends Fragment {

    public View root;

    public NoteListActivityFragment() {
    }

    //Data is initiallized be should receive notes from a database later on.
    List<Note> data = new ArrayList<>();
    List<Note> oldData = new ArrayList<>();
    NotesApplication application;
    SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        application = (NotesApplication) getActivity().getApplication();

        root = inflater.inflate(R.layout.fragment_note_list, container, false);

        //get the spinner, recycler view and the database handler
        final Spinner noteSpinner = root.findViewById(R.id.note_Spinner);
        final RecyclerView noteRecycler = root.findViewById(R.id.note_RecyclerView);
        swipe = root.findViewById(R.id.Refresher);
        NoteDatabaseHandler dbHandler = new NoteDatabaseHandler(getContext());


        //populate data list with notes from the server
        data = refresh();


        //create the adapter
        final NoteAdapter adapter = new NoteAdapter(data, this, dbHandler, application);

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

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //data = refresh();
                sortNotes(noteRecycler, adapter, 0, data);

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
        //if refreshing, stop
        swipe.setRefreshing(false);

        //redraw the page
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter.notifyDataSetChanged();
    }


    public List refresh()
    {
        data = new ArrayList<>();
        HttpRequest request = new HttpRequest(application.getURL() + "/user/"+ application.getUserUuid() + "/notes/", HttpRequest.Method.GET);
        HttpRequestTask task = new HttpRequestTask();
        task.setOnResponseListener(new OnResponseListener<HttpResponse>() {
            @Override
            public void onResponse(HttpResponse d) {
                Note[] array = Note.parseArray(d.getResponseBody());
                for (Note n:array) {
                    data.add(n);
                }
            }
        });
        task.setOnErrorListener(new OnErrorListener() {
            @Override
            public void onError(Exception error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        task.execute(request);

        return data;
    }

}
