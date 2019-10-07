package com.example.createnote.ui.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createnote.R;
import com.example.createnote.model.Category;
import com.example.createnote.model.Note;

import java.text.Format;
import java.text.SimpleDateFormat;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    //public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");


    private final TextView title;
    private final TextView body;
    private final TextView reminder;
    private final ConstraintLayout CardLayout;
    private final View root;

    public NoteViewHolder(@NonNull View root) {
        super(root);

        title = root.findViewById(R.id.title_TextView);
        body = root.findViewById(R.id.body_TextView);
        reminder = root.findViewById(R.id.reminder_TextView);
        CardLayout = root.findViewById(R.id.card_Layout);
        this.root = root;

        //TextView noteTextView = root.findViewById(R.id.note_TextView);




        //RecyclerView noteRecycler = root.findViewById(R.id.note_RecyclerView);

    }


    public void set(Note note) {
    title.setText(note.getTitle());
    body.setText(note.getBody());
    if(note.isHasReminder() == true)
        reminder.setText(note.getReminder().toString());
    else
        reminder.setText("");
    //Resource res = root.getResources();

        root.setBackgroundColor(note.getCategory().getColorId());

    }
}
