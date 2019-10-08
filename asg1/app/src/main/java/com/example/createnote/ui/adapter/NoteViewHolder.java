package com.example.createnote.ui.adapter;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createnote.R;
import com.example.createnote.model.Category;
import com.example.createnote.model.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    //public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");


    private final TextView title;
    private final TextView body;
    private final TextView reminder;
    private final CardView card;
    private final View root;

    public NoteViewHolder(@NonNull View root) {
        super(root);

        title = root.findViewById(R.id.title_TextView);
        body = root.findViewById(R.id.body_TextView);
        reminder = root.findViewById(R.id.reminder_TextView);
        card = root.findViewById(R.id.cardView);
        this.root = root;
        card.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return touchMenu(view, motionEvent);
            }
        });

    }


    public void set(Note note) {

        title.setText(note.getTitle());
        body.setText(note.getBody());

        if(note.isHasReminder() == true)
            reminder.setText(note.getReminder().toString());
        else
            reminder.setText("");

        Category x = note.getCategory();
        changeBackground(x, card);

    }

    @SuppressLint("ResourceAsColor")
    private void changeBackground(Category x, CardView card) {
        Resources res = root.getResources();
        if (x == Category.RED)
            card.setBackgroundColor(res.getColor(R.color.RED, null));
        else if (x == Category.ORANGE)
            card.setBackgroundColor(res.getColor(R.color.ORANGE, null));
        else if (x == Category.YELLOW)
            card.setBackgroundColor(res.getColor(R.color.YELLOW, null));
        else if (x == Category.GREEN)
            card.setBackgroundColor(res.getColor(R.color.GREEN, null));
        else if (x == Category.TEAL)
            card.setBackgroundColor(res.getColor(R.color.TEAL, null));
        else if (x == Category.BLUE)
            card.setBackgroundColor(res.getColor(R.color.BLUE, null));
        else if (x == Category.INDIGO)
            card.setBackgroundColor(res.getColor(R.color.INDIGO, null));
        else if (x == Category.PURPLE)
            card.setBackgroundColor(res.getColor(R.color.PURPLE, null));

    }

    private boolean touchMenu(View view, MotionEvent motionEvent){
        ActionMode actionMode;
        actionMode = view.startActionMode(new ActionMode.Callback2() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.menu_floating, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        }, ActionMode.TYPE_FLOATING);
        return true;
    }
}
