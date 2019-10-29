package com.example.createnote.ui.adapter;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.example.createnote.ui.editor.NoteActivity;
import com.example.createnote.ui.editor.NoteActivityFragment;
import com.example.createnote.ui.list.NoteListActivityFragment;
import com.example.createnote.ui.util.DatePickerDialogFragment;
import com.example.createnote.ui.util.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.security.AccessController.getContext;

//////////////////////////////////////////////////////////////////////
//  This Class gets very confusing but i'll walk you through it!!   //
//////////////////////////////////////////////////////////////////////
public class NoteViewHolder extends RecyclerView.ViewHolder {

    //public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    //fields
    private long noteId;
    private final TextView title;
    private final TextView body;
    private final TextView reminder;
    private final CardView card;
    private final View root;
    private NoteListActivityFragment fragment;
    private NoteDatabaseHandler dbHandler;
    private NoteAdapter adapter;
    private Note n;

    //constructor
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

        //calls on touch when a cardview is touched
        card.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return touchMenu(view, motionEvent);
            }
        });

    }

    //sets each not in the database to a card view
    public void set(Note note) {

        title.setText(note.getTitle());
        body.setText(note.getBody());
        noteId = note.getId();

        if(note.isHasReminder() == true)
            reminder.setText(note.getReminder().toString());
        else
            reminder.setText("");

        //get category for colour
        Category x = note.getCategory();

        //changes background colour
        changeBackground(x, card);
        n = note;


    }

    //this sets the background colour of the note
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

    //brings up a menu when a note is touched
    private boolean touchMenu(View view, MotionEvent motionEvent){

        //make an action mode that inflates menu_floating.xml
        ActionMode actionMode;


        //inner class for the floating menu click events!!!!
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


            //this method is called when an action item in the floating method was clicked
            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                //text is used for testing purposes
                //it keeps track of which action item was pressed
                String text ="";

                switch (menuItem.getItemId())
                {
                    case R.id.edit_MenuItem:

                        Intent i = new Intent(view.getContext(), NoteActivity.class);
                        i.putExtra("Note", n);
                        view.getContext().startActivity(i);

                        break;
                    case R.id.reminder_MenuItem:

                        //reminder was pressed
                        text = "Reminder";

                        //current date
                        final Date now = new Date();

                        //datepickerdialog to allow the user to pick a new date for the reminder
                        DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.create(now, new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final Calendar calendar = Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);

                                //datepickerdialog to allow the user to pick a new time for the reminder
                                TimePickerDialogFragment timeDialogFragment = TimePickerDialogFragment.create(now, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                        calendar.set(calendar.MINUTE, minute);
                                        calendar.set(calendar.HOUR, hour);

                                        Date newReminder = calendar.getTime();
                                        Toast.makeText(fragment.getContext(), "restart app to see changes", Toast.LENGTH_LONG).show();

                                        //SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.CANADA);

                                        ///*

                                        try {
                                            //get the note that was touched
                                            Note selectedNote = dbHandler.getNoteTable().read(noteId);

                                            //delete the note from the database
                                            dbHandler.getNoteTable().delete(selectedNote);

                                            //update the note
                                            selectedNote.setHasReminder(true);
                                            selectedNote.setReminder(newReminder);
                                            selectedNote.setModified(now);

                                            //put it back in the database!
                                            dbHandler.getNoteTable().create(selectedNote);

                                            //tell the adapter to redraw (doesn't work)
                                            adapter.notifyDataSetChanged();

                                        } catch (DatabaseException e) {
                                            e.printStackTrace();
                                        }

                                        //*/
                                    }
                                });

                                //open time dialog
                                timeDialogFragment.show(fragment.getFragmentManager(), "timePicker");


                            }
                        });

                        //open date dialog
                        dialogFragment.show(fragment.getFragmentManager(), "datePicker");

                        break;
                    case R.id.trash_MenuItem:

                        //trash was pressed
                        text = "Trash, restart app to see changes";

                        try {
                            //get the note that was touched
                            Note selectedNote = dbHandler.getNoteTable().read(noteId);

                            //delete the note
                            dbHandler.getNoteTable().delete(selectedNote);

                            //tell the adapter to redraw (doesn't work)
                            adapter.notifyDataSetChanged();

                        } catch (DatabaseException e) {
                            e.printStackTrace();
                        }

                        break;
                    case R.id.close_MenuItem:

                        //close was pressed
                        text = "Close";

                        //close the floating menu
                        actionMode.finish();
                        break;
                }
                //for debugging, displays the action item that was pressed
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
