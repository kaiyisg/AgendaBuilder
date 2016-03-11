package com.interactionprog.agendabuilder.android;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.ActivityAdderDialog;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;

import java.util.List;

public class OneAgendaController {

    View view;
    AgendaModel agendaModel;
    Day day;
    Button addActivityButton;

    Button addActivityInDialogButton; //button 9
    Button cancelActivityInDialogButton; //button 10
    ActivityAdderDialog activityAdderDialog;
    Spinner parkedActivitySelectorDropdown;

    public OneAgendaController(View v, AgendaModel model, Day d){

        this.view = v;
        this.agendaModel = model;
        this.day = d;

        addActivityButton = (Button)v.findViewById(R.id.button7);

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activityAdderDialogOpener(v);

            }
        });


    }

    private void activityAdderDialogOpener(View v){

        //inflating the layout
        LayoutInflater inflater = (LayoutInflater) AgendaBuilderApplication.getAppContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View activityAdderView = inflater.inflate(R.layout.activity_adder_dialog, null);

        //intializing buttons and widgets
        addActivityInDialogButton = (Button)activityAdderView.findViewById(R.id.button9);
        cancelActivityInDialogButton = (Button)activityAdderView.findViewById(R.id.button10);
        parkedActivitySelectorDropdown = (Spinner)activityAdderView.findViewById(R.id.spinner3);

        //creating dialog box
        final Dialog dialog = new Dialog(v.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(activityAdderView);

        activityAdderDialog = new ActivityAdderDialog(activityAdderView, agendaModel);

        addActivityInDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int parkedPosition = parkedActivitySelectorDropdown.getSelectedItemPosition();

                //finding the activity object from the string
                String parkedActivityString = (String)parkedActivitySelectorDropdown.getSelectedItem();
                List<Activity> allParkedActivities = agendaModel.getParkedActivites();
                Activity parkedActivity = null;
                for (Activity a: allParkedActivities){
                    if(a.getName().equals(parkedActivityString)){
                        parkedActivity = a;
                    }
                }

                //remove parkedActivity
                agendaModel.removeParkedActivity(parkedActivity);

                //add parked activity to the day
                agendaModel.addActivity(parkedActivity,day, day.getLastPositionIndex());

                dialog.cancel();
            }
        });

        cancelActivityInDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

    }

}
