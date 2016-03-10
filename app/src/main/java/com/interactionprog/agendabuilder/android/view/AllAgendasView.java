package com.interactionprog.agendabuilder.android.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
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
    }

    @Override
    public void update(Observable observable, Object data) {
    }

    private void initializeAllDays(List<Day> days){

        //clear all previous views before any calls made here
        horizontalScrollView.removeAllViews();

        //creating container to put into my scroll view
        LinearLayout topLinearLayout = new LinearLayout(view.getContext());
        topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        for(Day d: days){
            topLinearLayout.addView(initializeOneDay(d));
        }

        //add the view to my scroll view
        horizontalScrollView.addView(topLinearLayout);

    }

    private View initializeOneDay(Day day){

        //getting layout of one agenda template to fill up
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View oneAgendaView = inflater.inflate(R.layout.one_agenda_view, null);
        //making margins for better layouting
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(0, 0, 20, 0);
        oneAgendaView.setLayoutParams(ll);

        EditText startTime = (EditText)oneAgendaView.findViewById(R.id.editText4);
        TextView endTime = (TextView)oneAgendaView.findViewById(R.id.textView17);
        TextView totalTime = (TextView)oneAgendaView.findViewById(R.id.textView19);

        startTime.setText(String.valueOf(day.getStart()));
        endTime.setText(String.valueOf(day.getEnd()));
        totalTime.setText(String.valueOf(day.getTotalLength()));

        return oneAgendaView;
    }

}
