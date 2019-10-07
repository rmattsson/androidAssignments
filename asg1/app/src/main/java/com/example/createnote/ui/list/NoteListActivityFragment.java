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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.createnote.R;
import com.example.createnote.model.Note;
import com.example.createnote.model.SampleData;
import com.example.createnote.ui.adapter.NoteAdapter;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteListActivityFragment extends Fragment {

    public NoteListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_note_list, container, false);

        Spinner noteSpinner = root.findViewById(R.id.note_Spinner);
        RecyclerView noteRecycler = root.findViewById(R.id.note_RecyclerView);


        List<Note> data = SampleData.getData();

        NoteAdapter adapter = new NoteAdapter(data);


        //ArrayAdapter<Note> adapter = new ArrayAdapter<>(getContext(), R.layout.list_item_note, R.id.title_TextView);


        //adapter.addAll(data);

        //noteSpinner.setAdapter(adapter);

        noteRecycler.setAdapter(adapter);


        sortNotes(noteRecycler);


        return root;
    }


    /*
    List<Todo> data = SampleData.GetData();
    data.sort(new comparator<todo>)() {
        @Override
                public int compare(Todo lhs, Todo rhs)
        {
            return (int) (rhs.getUrgency() - lhs.getUrgency());
        }
    }
*/

    private void sortNotes (RecyclerView recycler)
    {

        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }


}
