package com.interactionprog.agendabuilder.android;


import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.ActivityAdderDialog;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;

import java.util.List;

public class OneAgendaController {

    View view;
    AgendaModel agendaModel;
    final Day day;
    TableLayout tableLayout;
    Button addActivityButton;

    Button addActivityInDialogButton; //button 9
    Button cancelActivityInDialogButton; //button 10
    ActivityAdderDialog activityAdderDialog;
    Spinner parkedActivitySelectorDropdown;

    public OneAgendaController(View v, AgendaModel model, Day d, TableLayout table){

        this.view = v;
        this.agendaModel = model;
        this.day = d;
        this.tableLayout = table;

        addActivityButton = (Button)v.findViewById(R.id.button7);

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activityAdderDialogOpener(v);

            }
        });

        table.setOnDragListener(new MyDragListener(day));


    }

    class MyDragListener implements View.OnDragListener{

        Day day;

        MyDragListener(Day d){
            this.day = d;

        }

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();

            //this method call is only triggered to add an activity to the bottom of the list
            //activity controller tied to table row will be triggered first in any other case

            switch(action){
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DROP:

                    String description = event.getClipDescription().toString();

                    /*
                    String positionString = "";
                    int stringLength = description.length();
                    boolean addingToPositionString = false;
                    for(int i=0;i<stringLength;i++){
                        char braces = ;
                        if(description.charAt(i)==[{]){

                        }
                    }*/

                    int positionOfActivityInAgenda = Integer.valueOf(description.charAt(18))-48;
                    //todo this is a dirty fix, does not account for things that are double digit indexes

                    // Dropped, reassign View to ViewGroup
                    /*
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);

                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/


                    agendaModel.removeActivityFromDay(day, positionOfActivityInAgenda);

                    //agendaModel.addActivity(parkedActivity,day, day.getLastPositionIndex());

                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    return false;
            }
        }
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
