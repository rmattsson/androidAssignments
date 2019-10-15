package com.example.createnote.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createnote.R;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.ui.list.NoteListActivity;
import com.example.createnote.ui.list.NoteListActivityFragment;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<Note> data;
    private NoteListActivityFragment fragment;
    private NoteDatabaseHandler dbHandler;

    public NoteAdapter(List<Note> data, NoteListActivityFragment fragment, NoteDatabaseHandler dbHandler){
        this.data = data;
        this.fragment = fragment;
        this.dbHandler = dbHandler;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_note, parent, false);
        NoteViewHolder holder = new NoteViewHolder(root, fragment, dbHandler, this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.set(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
