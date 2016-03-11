package com.interactionprog.agendabuilder.android;


import android.view.View;
import android.widget.Button;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.model.AgendaModel;

public class OneAgendaController {

    View view;
    AgendaModel agendaModel;
    Button addActivityButton;

    public OneAgendaController(View v, AgendaModel model){

        this.view = v;
        this.agendaModel = model;

        addActivityButton = (Button)v.findViewById(R.id.button7);


    }

}
