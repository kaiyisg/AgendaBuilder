package com.interactionprog.agendabuilder.android.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.model.Activity;

public class ActivityEditorDialog {

    View view;
    Activity editActivity;
    EditText nameActivity;
    EditText lengthActivity;
    EditText descriptionActivity;
    Button saveActivity;
    Button cancelActivity;

    public ActivityEditorDialog(View view, Activity act){

        this.view = view;
        this.editActivity = act;

        //intializing the spinner to hold different numbers
        Spinner dropdown = (Spinner)view.findViewById(R.id.spinner);
        String[] items = new String[]{"Presentation", "Group Work", "Discussion", "Break"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        //initializing the other UI components
        nameActivity = (EditText)view.findViewById(R.id.editText);
        lengthActivity = (EditText)view.findViewById(R.id.editText2);
        descriptionActivity = (EditText)view.findViewById(R.id.editText3);
        saveActivity = (Button)view.findViewById(R.id.button);
        cancelActivity = (Button)view.findViewById(R.id.button2);

        //populate fields if there are fields to populate
        if(editActivity!=null){
            nameActivity.setText(editActivity.getName());
            lengthActivity.setText(editActivity.getLength());
            descriptionActivity.setText(editActivity.getDescription());
            dropdown.setSelection(editActivity.getType() - 1);
        }

        //setting what happens when we save the activity
        saveActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checking if all fields are set up properly, else cancel saving

                //getting all the information from the fields

                //save into the old activity if it exists

                //save into a new activity if it does not previously exist

            }
        });

        //setting what happens when we cancel the activity
        cancelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
