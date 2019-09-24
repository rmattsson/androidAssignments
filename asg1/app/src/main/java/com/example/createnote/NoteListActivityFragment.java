package com.example.createnote;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.createnote.model.Category;
import com.example.createnote.model.Note;

import java.util.ArrayList;
import java.util.Date;
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
        List<Note> data = new ArrayList<>();

        data.add(new Note(1, "Hello", "Greetings world", Category.RED, true, new Date(), new Date(), new Date()));
        data.add(new Note(2, "Hello", "Greetings world", Category.BLUE, true, new Date(), new Date(), new Date()));
        data.add(new Note(3, "Hello", "Greetings world", Category.INDIGO, true, new Date(), new Date(), new Date()));

        ArrayAdapter<Note> adapter = new ArrayAdapter<>(getContext(), R.layout.list_item_note, R.id.note_TextView);

        adapter.addAll(data);

        noteSpinner.setAdapter(adapter);

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
}