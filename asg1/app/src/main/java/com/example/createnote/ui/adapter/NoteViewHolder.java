package com.example.createnote.ui.adapter;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.createnote.R;
import com.example.createnote.model.Category;
import com.example.createnote.model.Note;
import com.example.createnote.model.NoteDatabaseHandler;
import com.example.createnote.sqlite.DatabaseException;
import com.example.createnote.ui.list.NoteListActivityFragment;
import com.example.createnote.ui.util.DatePickerDialogFragment;
import com.example.createnote.ui.util.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    //public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    private long noteId;

    private final TextView title;
    private final TextView body;
    private final TextView reminder;
    private final CardView card;
    private final View root;
    private NoteListActivityFragment fragment;
    private NoteDatabaseHandler dbHandler;
    private NoteAdapter adapter;

    public NoteViewHolder(@NonNull View root, NoteListActivityFragment fragment , NoteDatabaseHandler dbHandler, NoteAdapter adapter) {
        super(root);

        title = root.findViewById(R.id.title_TextView);
        body = root.findViewById(R.id.body_TextView);
        reminder = root.findViewById(R.id.reminder_TextView);
        card = root.findViewById(R.id.cardView);
        this.root = root;
        this.fragment = fragment;
        this.dbHandler = dbHandler;
        this.adapter = adapter;
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
        noteId = note.getId();

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
                String text ="";
                switch (menuItem.getItemId())
                {
                    case R.id.reminder_MenuItem:
                        text = "Reminder";

                        final Date now = new Date();
                        DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.create(now, new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final Calendar calendar = Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);


                                TimePickerDialogFragment timeDialogFragment = TimePickerDialogFragment.create(now, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                        calendar.set(calendar.MINUTE, minute);
                                        calendar.set(calendar.HOUR, hour);

                                        Date newReminder = calendar.getTime();
                                        Toast.makeText(fragment.getContext(), newReminder.toString(), Toast.LENGTH_LONG).show();

                                        //SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.CANADA);

                                        ///*

                                        try {

                                            Note selectedNote = dbHandler.getNoteTable().read(noteId);
                                            selectedNote.setHasReminder(true);
                                            selectedNote.setReminder(newReminder);
                                            adapter.notifyDataSetChanged();

                                        } catch (DatabaseException e) {
                                            e.printStackTrace();
                                        }


                                        //*/



                                    }
                                });

                                timeDialogFragment.show(fragment.getFragmentManager(), "timePicker");


                            }
                        });

                        dialogFragment.show(fragment.getFragmentManager(), "datePicker");

                        //TODO
                        //update note

                        break;
                    case R.id.trash_MenuItem:


                        try {

                            Note selectedNote = dbHandler.getNoteTable().read(noteId);
                            dbHandler.getNoteTable().delete(selectedNote);
                            adapter.notifyDataSetChanged();

                        } catch (DatabaseException e) {
                            e.printStackTrace();
                        }

                        text = "Trash";
                        break;
                    case R.id.close_MenuItem:
                        text = "Close";
                        actionMode.finish();
                        break;
                }
                Toast.makeText(root.getContext(), text, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        }, ActionMode.TYPE_FLOATING);
        return true;
    }
}
