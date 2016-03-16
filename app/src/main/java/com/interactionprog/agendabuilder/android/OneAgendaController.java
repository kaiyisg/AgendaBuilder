package com.interactionprog.agendabuilder.android;


import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.ActivityAdderDialog;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;

import java.util.List;

public class OneAgendaController {

    View view;
    AgendaModel agendaModel;
    int positionOfDayInAgendaModel;
    TableLayout tableLayout;
    FrameLayout agendaAdder;
    FrameLayout agendaDeleter;
    Button addActivityButton;
    Day day;
    TextView addActivity;
    TextView deleteActivity;

    Button addActivityInDialogButton; //button 9
    Button cancelActivityInDialogButton; //button 10
    ActivityAdderDialog activityAdderDialog;
    Spinner parkedActivitySelectorDropdown;

    public OneAgendaController(View v, AgendaModel model, int dayPos, TableLayout table,
                               FrameLayout adder,FrameLayout deleter, Day d){

        this.view = v;
        this.agendaModel = model;
        this.positionOfDayInAgendaModel = dayPos;
        this.tableLayout = table;
        this.agendaAdder = adder;
        this.agendaDeleter = deleter;
        this.day = d;

        addActivityButton = (Button)v.findViewById(R.id.button7);

        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activityAdderDialogOpener(v);

            }
        });

        //purpose of drag listener is to listen when rows are dragged onto the table
        //this means that the row has to reappear back at where it came from
        table.setOnDragListener(new TableDragListener(positionOfDayInAgendaModel));

        //purpose of drag listener is to listen when rows are dragged onto controllers
        agendaAdder.setOnDragListener(new AddDragListener(positionOfDayInAgendaModel));
        agendaDeleter.setOnDragListener(new RemoveDragListener());

        //purpose of this is to listen out to changes in start time
        final EditText startTimeHour = (EditText)view.findViewById(R.id.editText4);
        final EditText startTimeMin = (EditText)view.findViewById(R.id.editText5);

        startTimeHour.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                int hour = Integer.valueOf(startTimeHour.getText().toString());
                int min = Integer.valueOf(startTimeMin.getText().toString());
                int length = day.getTotalLength();
                if (AgendaModel.checkIfSensibleStartTime(hour,min,length)){
                    int start = hour*60 + min;
                    agendaModel.setStartTimingOfDay(day,start);
                }else{
                    Toast.makeText(view.getContext(), "Please emter a sensible hour",
                            Toast.LENGTH_SHORT).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        startTimeMin.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                int hour = Integer.valueOf(startTimeHour.getText().toString());
                int min = Integer.valueOf(startTimeMin.getText().toString());
                int length = day.getTotalLength();
                if (AgendaModel.checkIfSensibleStartTime(hour,min,length)){
                    int start = hour*60 + min;
                    agendaModel.setStartTimingOfDay(day,start);
                }else{
                    Toast.makeText(view.getContext(), "Please emter a sensible minute",
                            Toast.LENGTH_SHORT).show();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    class TableDragListener implements View.OnDragListener{

        int droppedPositionOfDayInList;

        TableDragListener(int droppedPositionDay){
            this.droppedPositionOfDayInList = droppedPositionDay;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            ClipDescription des = event.getClipDescription();
            if(des!=null) {
                String description = des.toString();
                String[] parts = description.split(" ");
                String initialpositionOfActivityInDayString = parts[2];
                int initialpositionOfActivityInDay = Integer.valueOf(initialpositionOfActivityInDayString);
                String initialpositionOfDayInListString = parts[3];
                int initialpositionOfDayInList = Integer.valueOf(initialpositionOfDayInListString);

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        List<Day> allDays = agendaModel.getDays();
                        Day initialDay = allDays.get(initialpositionOfDayInList);
                        Day currentDay = allDays.get(droppedPositionOfDayInList);
                        agendaModel.moveActivity(initialDay, initialpositionOfActivityInDay,
                                currentDay, initialpositionOfActivityInDay);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    default:
                        return false;
                }
            }
            return false;
        }
    }

    class AddDragListener implements View.OnDragListener{

        int droppedPositionOfDayInList;
        boolean set = false;
        int initialPosAct;
        int initialPosDay;

        AddDragListener(int droppedPositionDay){
            this.droppedPositionOfDayInList = droppedPositionDay;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            ClipDescription des = event.getClipDescription();
            if(des!=null ) {
                String description = des.toString();
                String[] parts = description.split(" ");
                String initialpositionOfActivityInDayString = parts[2];
                int initialpositionOfActivityInDay = Integer.valueOf(initialpositionOfActivityInDayString);
                String initialpositionOfDayInListString = parts[3];
                int initialpositionOfDayInList = Integer.valueOf(initialpositionOfDayInListString);

                initialPosAct = initialpositionOfActivityInDay;
                initialPosDay = initialpositionOfDayInList;
                set = true;

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        List<Day> allDays = agendaModel.getDays();
                        Day initialDay = allDays.get(initialpositionOfDayInList);
                        Day currentDay = allDays.get(droppedPositionOfDayInList);
                        agendaModel.moveActivity(initialDay, initialpositionOfActivityInDay,
                                currentDay, currentDay.getLastPositionIndex());
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    default:
                        return false;
                }
            }

            if(set){
                set = false;
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        List<Day> allDays = agendaModel.getDays();
                        Day initialDay = allDays.get(initialPosDay);
                        Day currentDay = allDays.get(droppedPositionOfDayInList);
                        agendaModel.moveActivity(initialDay, initialPosAct,
                                currentDay, currentDay.getLastPositionIndex());
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    default:
                        return false;
                }
            }

            return false;
        }
    }

    class RemoveDragListener implements View.OnDragListener{

        boolean set = false;
        int initialPosAct;
        int initialPosDay;

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            ClipDescription des = event.getClipDescription();
            if(des!=null) {
                String description = des.toString();
                String[] parts = description.split(" ");
                String initialpositionOfActivityInDayString = parts[2];
                int initialpositionOfActivityInDay = Integer.valueOf(initialpositionOfActivityInDayString);
                String initialpositionOfDayInListString = parts[3];
                int initialpositionOfDayInList = Integer.valueOf(initialpositionOfDayInListString);

                initialPosAct = initialpositionOfActivityInDay;
                initialPosDay = initialpositionOfDayInList;
                set = true;

                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        List<Day> allDays = agendaModel.getDays();
                        Day initialDay = allDays.get(initialpositionOfDayInList);
                        agendaModel.removeActivityFromDay(initialDay, initialpositionOfActivityInDay);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    default:
                        return false;
                }
            }

            if(set){
                set = false;
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        List<Day> allDays = agendaModel.getDays();
                        Day initialDay = allDays.get(initialPosDay);
                        agendaModel.removeActivityFromDay(initialDay, initialPosAct);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    default:
                        return false;
                }
            }
            return false;
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
