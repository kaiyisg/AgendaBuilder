package com.interactionprog.agendabuilder.android.view;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.model.AgendaModel;

import java.util.Observable;
import java.util.Observer;

public class BgActivitiesView implements Observer {

    View view;
    AgendaModel agendaModel;
    Button addActivityButton;
    Button goToAgendaPlanningButton;
    TextView numberOfActivitiesParked;

    public BgActivitiesView(View view, AgendaModel model){
        this.view = view;
        this.agendaModel = model;

        //setting up views
        numberOfActivitiesParked = (TextView)view.findViewById(R.id.textView4);
        addActivityButton = (Button)view.findViewById(R.id.button3);
        goToAgendaPlanningButton = (Button)view.findViewById(R.id.button4);
        updateParkedActivitiesNumber();

        agendaModel.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        updateParkedActivitiesNumber();
    }

    private void updateParkedActivitiesNumber(){
        numberOfActivitiesParked.setText(String.valueOf(agendaModel.getParkedActivites().size()));
    }
}
