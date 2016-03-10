package com.interactionprog.agendabuilder.android.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AllActivitiesView implements Observer {

    View view;
    static AgendaModel agendaModel;
    Spinner dropdown;
    static TextView lengthTextView;
    static TextView typeTextView;
    static TextView descriptionTextView;

    public AllActivitiesView(View view, AgendaModel model){
        this.view = view;
        this.agendaModel = model;

        //initializing spinner
        dropdown = (Spinner)view.findViewById(R.id.spinner2);
        lengthTextView = (TextView)view.findViewById(R.id.textView8);
        typeTextView = (TextView)view.findViewById(R.id.textView10);
        descriptionTextView = (TextView)view.findViewById(R.id.textView12);

        updateParkedActivityList();

        //updateParkedActivityDetails();
    }

    @Override
    public void update(Observable observable, Object data) {

        String thing = data.toString();

        //updating spinenr every time a new activity is added
        updateParkedActivityList();
        updateParkedActivityDetails(thing);
    }

    private void updateParkedActivityList(){

        //getting the list of activities from the model
        List<Activity> allParkedActivities = agendaModel.getParkedActivites();

        //populate string array for view
        List<String> itemsList = new ArrayList<String>();
        for(Activity a: allParkedActivities){
            itemsList.add(a.getName());
        }
        String[] items = itemsList.toArray(new String[itemsList.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);
    }

    public static void updateParkedActivityDetails(String selectedActivity){
        List<Activity> allParkedActivities = agendaModel.getParkedActivites();

        Activity activityChoice = null;

        for (Activity a: allParkedActivities){
            if(a.getName().equals(selectedActivity)){
                activityChoice = a;
            }
        }

        if(activityChoice!=null){
            lengthTextView.setText(String.valueOf(activityChoice.getLength()));
            typeTextView.setText(activityChoice.getTypeInString());
            descriptionTextView.setText(activityChoice.getDescription());

        }
    }

}
