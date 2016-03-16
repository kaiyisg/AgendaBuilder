package com.interactionprog.agendabuilder.android.view;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.AllAgendasController;
import com.interactionprog.agendabuilder.android.OneActivityController;
import com.interactionprog.agendabuilder.android.OneAgendaController;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lee Han Young on 10-Mar-16.
 */
public class AllAgendasView implements Observer {

    HorizontalScrollView horizontalScrollView;
    View view;
    AgendaModel agendaModel;

    public AllAgendasView(View v, AgendaModel model){

        this.view = v;
        this.agendaModel = model;

        horizontalScrollView = (HorizontalScrollView)view.findViewById(R.id.horizontalScrollView);

        initializeAllDays(model.getDays());

        agendaModel.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {

        AgendaModel model = AgendaModel.getInstance();
        initializeAllDays(model.getDays());
        
    }

    private void initializeAllDays(List<Day> days){

        //clear all previous views before any calls made here
        horizontalScrollView.removeAllViews();

        //creating container to put into my scroll view
        LinearLayout topLinearLayout = new LinearLayout(view.getContext());
        topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        int dayPosition =0;
        for(Day d: days){
            topLinearLayout.addView(initializeOneDay(d,dayPosition));
            dayPosition+=1;
        }

        //adding the button that adds more views
        Button dayAdderButton = new Button(view.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dayAdderButton.setLayoutParams(params);
        dayAdderButton.setGravity(Gravity.CENTER_HORIZONTAL);
        dayAdderButton.setText("Add Day");
        AllAgendasController allAgendasController = new AllAgendasController(view, agendaModel, dayAdderButton);
        topLinearLayout.addView(dayAdderButton);

        //add the view to my scroll view
        horizontalScrollView.addView(topLinearLayout);

    }

    private View initializeOneDay(Day day, int positionOfDayInAgendaModel){

        //getting layout of one agenda template to fill up
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View oneAgendaView = inflater.inflate(R.layout.one_agenda_view, null);
        //making margins for better layouting
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(0, 0, 20, 0);
        oneAgendaView.setLayoutParams(ll);

        //initializing the start and end timings on top
        EditText startTimeHour = (EditText)oneAgendaView.findViewById(R.id.editText4);
        EditText startTimeMin = (EditText)oneAgendaView.findViewById(R.id.editText5);
        TextView endTime = (TextView)oneAgendaView.findViewById(R.id.textView17);
        TextView totalTime = (TextView)oneAgendaView.findViewById(R.id.textView19);
        startTimeHour.setText(day.getStartHour());
        startTimeMin.setText(day.getStartMin());
        endTime.setText(day.getEndTime());
        totalTime.setText(day.getTotalLengthTime());

        //initializing the colors ratio on top
        FrameLayout presentationBlueLayout = (FrameLayout)oneAgendaView.findViewById(R.id.presentation_blue_id);
        FrameLayout discussionGreenLayout = (FrameLayout)oneAgendaView.findViewById(R.id.discussion_green_id);
        FrameLayout groupworkRedLayout = (FrameLayout)oneAgendaView.findViewById(R.id.groupwork_red_id);
        FrameLayout breakYellowLayout = (FrameLayout)oneAgendaView.findViewById(R.id.break_yellow_id);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                day.getLengthByType(Activity.PRESENTATION));
        presentationBlueLayout.setLayoutParams(param);

        param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                day.getLengthByType(Activity.DISCUSSION));
        discussionGreenLayout.setLayoutParams(param);

        param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                day.getLengthByType(Activity.GROUP_WORK));
        groupworkRedLayout.setLayoutParams(param);

        param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                day.getLengthByType(Activity.BREAK));
        breakYellowLayout.setLayoutParams(param);


        //initializing the vertical scroll view so we can make use of each view later on to add stuff
        ScrollView verticalScrollView = (ScrollView)oneAgendaView.findViewById(R.id.scrollView2);
        FrameLayout agendaAdder = (FrameLayout)oneAgendaView.findViewById(R.id.agenda_adder_id);
        FrameLayout agendaDeleter = (FrameLayout)oneAgendaView.findViewById(R.id.agenda_deleter_id);

        //adding the activities view to each day
        int currentTime = day.getStart();
        TableLayout tl = (TableLayout)oneAgendaView.findViewById(R.id.table_layout_id);
        List<Activity> activities = day.getActivities();

        int positionOfActivityInAgenda = 0;

        for(Activity act:activities){

            //getting and setting up each row
            TableRow tr = new TableRow(oneAgendaView.getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            //setting the colors of each row
            Resources resource = oneAgendaView.getContext().getResources();
            if(act.getType()==Activity.BREAK){
                tr.setBackgroundColor(resource.getColor(R.color.break_yellow));
            }else if (act.getType()==Activity.DISCUSSION){
                tr.setBackgroundColor(resource.getColor(R.color.discussion_green));
            }else if (act.getType()==Activity.GROUP_WORK){
                tr.setBackgroundColor(resource.getColor(R.color.groupwork_red));
            }else{
                tr.setBackgroundColor(resource.getColor(R.color.presentation_blue));
            }

            //adding time of the activity start to the row
            TextView timeView = new TextView(oneAgendaView.getContext());
            timeView.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            timeView.setText(makeIntoTime(currentTime));
            tr.addView(timeView);

            //adding the name of each activity to the row
            TextView nameView = new TextView(oneAgendaView.getContext());
            TableRow.LayoutParams llp = new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            llp.setMargins(20, 0, 0, 10); // llp.setMargins(left, top, right, bottom);
            nameView.setLayoutParams(llp);
            nameView.setText(act.getName());
            tr.addView(nameView);

            //initializing the activity row controller that controls drag and drop
            OneActivityController oneActivityController = new
                    OneActivityController(oneAgendaView,
                    agendaModel,act,tr,positionOfActivityInAgenda, positionOfDayInAgendaModel);

            //adding the row to the table
            tl.addView(tr);

            //setting the next current time for the next activity
            currentTime+=act.getLength();
            //setting the next position for the next activity
            positionOfActivityInAgenda+=1;
        }

        //initializing the controller for the activity
        OneAgendaController oneAgendaController = new OneAgendaController(
                oneAgendaView, agendaModel, positionOfDayInAgendaModel, tl,
                agendaAdder,agendaDeleter, day);

        return oneAgendaView;
    }

    private String makeIntoTime(int time){
        String hour = String.valueOf(time/60);
        if(hour.length()==1){
            hour = "0" + hour;
        }
        String min = String.valueOf(time%60);
        if(min.length()==1){
            min = "0" + min;
        }
        return hour+":"+min;
    }

}
