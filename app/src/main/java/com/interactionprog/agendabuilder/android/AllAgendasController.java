package com.interactionprog.agendabuilder.android;

import android.view.View;
import android.widget.Button;

import com.interactionprog.agendabuilder.model.AgendaModel;

/**
 * Created by Lee Han Young on 16-Mar-16.
 */
public class AllAgendasController {

    View view;
    AgendaModel agendaModel;
    Button dayAddingButton;

    public AllAgendasController(View v, AgendaModel model, Button dayAdder){
        this.view = v;
        this.agendaModel = model;
        this.dayAddingButton = dayAdder;

        //adding day with a default starting timing of 8:00am
        dayAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendaModel.addDay(8,0);
            }
        });
    }

}
