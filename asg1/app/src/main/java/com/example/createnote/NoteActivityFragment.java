package com.example.createnote;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.createnote.util.CircleView;
import com.example.createnote.util.DatePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteActivityFragment extends Fragment {

    private ConstraintLayout note_layout;
    private LinearLayout reminder_layout;

    private Switch reminder_switch;

    private Button addReminder;
    //private Button undoBTN;

    private CircleView red;
    private CircleView orange;
    private CircleView yellow;
    private CircleView green;
    private CircleView teal;
    private CircleView blue;
    private CircleView indigo;
    private CircleView purple;

    public NoteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_note, container, false);

        note_layout = root.findViewById(R.id.note_layout);
        reminder_layout = root.findViewById(R.id.reminder_layout);
        reminder_layout.setVisibility(View.GONE);

        //reminder
        addReminder = root.findViewById(R.id.addReminder_button);
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseADate();
            }
        });



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

        //colors
        red = root.findViewById(R.id.red_circleView);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(red.getColor(), note_layout);
            }
        });

        orange = root.findViewById(R.id.orange_circleView);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(orange.getColor(), note_layout);
            }
        });

        yellow = root.findViewById(R.id.yellow_circleView);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(yellow.getColor(), note_layout);
            }
        });

        green = root.findViewById(R.id.green_circleView);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(green.getColor(), note_layout);
            }
        });

        teal = root.findViewById(R.id.teal_circleView);
        teal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(teal.getColor(), note_layout);
            }
        });

        blue = root.findViewById(R.id.blue_circleView);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(blue.getColor(), note_layout);
            }
        });
        indigo = root.findViewById(R.id.indigo_circleView);
        indigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(indigo.getColor(), note_layout);
            }
        });

        purple = root.findViewById(R.id.purple_circleView);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBackground(purple.getColor(), note_layout);
            }
        });

        return root;
    }



    private void chooseADate()
    {
        Date now = new Date();
        DatePickerDialogFragment dialogFragment = DatePickerDialogFragment.create(now, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                Date selectedDate = calendar.getTime();
                Toast.makeText(getContext(), selectedDate.toString(), Toast.LENGTH_LONG).show();
            }
        });
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    private void changeBackground(int color, ConstraintLayout layout) {
        layout.setBackgroundColor(color);
    }

    private void undo() {

    }
}
