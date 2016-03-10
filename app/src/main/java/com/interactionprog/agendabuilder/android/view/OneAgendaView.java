package com.interactionprog.agendabuilder.android.view;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lee Han Young on 25-Feb-16.
 */
public class OneAgendaView implements Observer {

    View view;
    AgendaModel agendaModel;
    Day day;
    EditText startTime;
    TextView endTime;
    TextView totalTime;

    public OneAgendaView(View view, AgendaModel model, Day d){
        this.view = view;
        this.agendaModel = model;
        this.day = d;

        startTime = (EditText)view.findViewById(R.id.editText4);
        endTime = (TextView)view.findViewById(R.id.textView17);
        totalTime = (TextView)view.findViewById(R.id.textView19);

        populateSingleDay();

        agendaModel.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {

        populateSingleDay();

    }

    private void populateSingleDay(){
        startTime.setText(String.valueOf(day.getStart()));
        endTime.setText(String.valueOf(day.getEnd()));
        totalTime.setText(String.valueOf(day.getTotalLength()));

    }
}
