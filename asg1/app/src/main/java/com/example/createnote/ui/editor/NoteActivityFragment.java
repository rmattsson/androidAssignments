package com.example.createnote.ui.editor;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.createnote.R;
import com.example.createnote.ui.util.CircleView;
import com.example.createnote.ui.util.DatePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.createnote.model.Note;
import com.example.createnote.model.Category;
import com.example.createnote.ui.util.TimePickerDialogFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteActivityFragment extends Fragment {

    private ConstraintLayout colour_layout;
    private LinearLayout reminder_layout;

    private EditText title;
    private EditText body;

    private Switch reminder_switch;

    private Button addReminder;
    private ImageButton undoBTN;

    private CircleView RED;
    private CircleView ORANGE;
    private CircleView YELLOW;
    private CircleView GREEN;
    private CircleView TEAL;
    private CircleView BLUE;
    private CircleView INDIGO;
    private CircleView PURPLE;

    private Category currentColour = Category.GREEN;
    private boolean hasReminder;
    private Date reminder = null;

    //this will prevent a new note being created when the editTexts changes while undoing
    private boolean undoing = false;


    public NoteActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Note n = getActivity().getIntent().getParcelableExtra("Note");
        final View root = inflater.inflate(R.layout.fragment_note, container, false);

        final ArrayList<Note> history = new ArrayList();





        //layouts
        colour_layout = root.findViewById(R.id.note_layout);
        reminder_layout = root.findViewById(R.id.reminder_layout);
        reminder_layout.setVisibility(View.GONE);

        //texts
        title = root.findViewById(R.id.title_editText);
        title.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        char[] keyStrokes = editable.toString().toCharArray();
                        if (!undoing && keyStrokes[keyStrokes.length - 1] == ' ' )
                            addNote(history);
                    }
                });

        body = root.findViewById(R.id.body_editText);
        body.addTextChangedListener(new TextWatcher() {



            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!undoing)
                    addNote(history);
            }
        });


        //reminder
        addReminder = root.findViewById(R.id.addReminder_button);
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseADate(root, history);
            }
        });

        //undo
        undoBTN = root.findViewById(R.id.undo_button);
        undoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo(history);
            }
        });

        //saving the beginning of the program so you can udo the first change
        addNote(history);

        //toggle menu witch
        reminder_switch = root.findViewById(R.id.reminder_switch);
        reminder_switch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b)
                    reminder_layout.setVisibility(View.GONE);
                else
                    reminder_layout.setVisibility(View.VISIBLE);
            }
        });

        hasReminder=false;


        //colors
        RED = root.findViewById(R.id.red_circleView);
        RED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.RED;
                changeBackground();

                addNote(history);
            }
        });

        ORANGE = root.findViewById(R.id.orange_circleView);
        ORANGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.ORANGE;
                changeBackground();

                addNote(history);
            }
        });

        YELLOW = root.findViewById(R.id.yellow_circleView);
        YELLOW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.YELLOW;
                changeBackground();

                addNote(history);
            }
        });

        GREEN = root.findViewById(R.id.green_circleView);
        GREEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.GREEN;
                changeBackground();

                addNote(history);
            }
        });

        TEAL = root.findViewById(R.id.teal_circleView);
        TEAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.TEAL;
                changeBackground();

                addNote(history);
            }
        });

        BLUE = root.findViewById(R.id.blue_circleView);
        BLUE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.BLUE;
                changeBackground();

                addNote(history);
            }
        });
        INDIGO = root.findViewById(R.id.indigo_circleView);
        INDIGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.INDIGO;
                changeBackground();

                addNote(history);
            }
        });

        PURPLE = root.findViewById(R.id.purple_circleView);
        PURPLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColour = Category.PURPLE;
                changeBackground();

                addNote(history);
            }
        });

        history.add(n);
        history.add(n);
        undo(history);
        return root;
    }


    //date picker that is called when you add a reminder
    private void chooseADate(final View root, final ArrayList<Note> history)
    {
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

                        reminder = calendar.getTime();
                        Toast.makeText(getContext(), reminder.toString(), Toast.LENGTH_LONG).show();

                        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.CANADA);
                        addReminder = root.findViewById(R.id.addReminder_button);
                        addReminder.setEnabled(false);
                        addReminder.setText("reminder: " + format.format(reminder));
                    }
                });

                timeDialogFragment.show(getFragmentManager(), "timePicker");


            }
        });

        dialogFragment.show(getFragmentManager(), "datePicker");
        hasReminder = true;

        addNote(history);
    }

    //called when you click a circle view to change the category,
    //changes background colour
    private void changeBackground() {
        if (currentColour == Category.RED)
            colour_layout.setBackgroundColor(RED.getColor());
        if (currentColour == Category.ORANGE)
            colour_layout.setBackgroundColor(ORANGE.getColor());
        if (currentColour == Category.YELLOW)
            colour_layout.setBackgroundColor(YELLOW.getColor());
        if (currentColour == Category.GREEN)
            colour_layout.setBackgroundColor(GREEN.getColor());
        if (currentColour == Category.TEAL)
            colour_layout.setBackgroundColor(TEAL.getColor());
        if (currentColour == Category.BLUE)
            colour_layout.setBackgroundColor(BLUE.getColor());
        if (currentColour == Category.INDIGO)
            colour_layout.setBackgroundColor(INDIGO.getColor());
        if (currentColour == Category.PURPLE)
            colour_layout.setBackgroundColor(PURPLE.getColor());

    }

    //undo button revert to before last edit
    private void undo(List<Note> hist) {
        if (hist.size() > 1) {
            undoing = true;
            hist.remove(hist.size() - 1);
            currentColour = hist.get(hist.size() - 1).getCategory();
            changeBackground();


            title.setText(hist.get(hist.size() - 1).getTitle());
            body.setText(hist.get(hist.size() - 1).getBody());
            if (hist.get(hist.size() - 1).isHasReminder())
            {
                addReminder.setEnabled(true);
                addReminder.setText("ADD A REMINDER");
            }
            undoing = false;
        }
    }

    //stores all changes in a list
    // (I know a stack makes more sense but i thought of that after implementing the list & i'm lazy)
    private void addNote(ArrayList<Note> history)
    {
        Note note = new Note();
        if (history.size() != 0) {
            note.setCreated(history.get(history.size() - 1).getCreated());
        }
        else
        {
            note.setCreated(new Date());
        }
        note.setBody(body.getText().toString());
        note.setTitle(title.getText().toString());
        note.setModified(new Date());
        note.setCategory(currentColour);
        note.setHasReminder(hasReminder);
        note.setReminder(reminder);
        history.add(note);
        hasReminder = false;
    }


}
