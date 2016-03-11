package com.interactionprog.agendabuilder.android.view;

import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Han Young on 11-Mar-16.
 */
public class ActivityAdderDialog {

    View view;
    AgendaModel agendaModel;
    Spinner dropdown;
    TextView displayText;

    public ActivityAdderDialog(View v, AgendaModel model){

        this.view = v;
        this.agendaModel = model;

        //initializing the spinner
        dropdown = (Spinner)view.findViewById(R.id.spinner3);
        displayText = (TextView)view.findViewById(R.id.textView21);

        //changing text color
        displayText.setTextColor(Color.BLACK);

        //getting the list of activities from the model
        List<Activity> allParkedActivities = agendaModel.getParkedActivites();

        //populate string array for view
        List<String> itemsList = new ArrayList<String>();
        for(Activity a: allParkedActivities){
            itemsList.add(a.getName());
        }
        String[] items = itemsList.toArray(new String[itemsList.size()]);

        //setting the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);

    }
}
