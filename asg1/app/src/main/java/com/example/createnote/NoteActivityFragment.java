package com.example.createnote;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.createnote.util.DatePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class NoteActivityFragment extends Fragment {


    private Button addReminder;

    public NoteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_note, container, false);

        addReminder = root.findViewById(R.id.addReminder_button);

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseADate();
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
}
