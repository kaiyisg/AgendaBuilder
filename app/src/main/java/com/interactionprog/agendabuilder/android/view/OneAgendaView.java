package com.interactionprog.agendabuilder.android.view;

import android.view.View;

import com.interactionprog.agendabuilder.model.AgendaModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Lee Han Young on 25-Feb-16.
 */
public class OneAgendaView implements Observer {

    View view;
    AgendaModel agendaModel;

    public OneAgendaView(View view, AgendaModel model){
        this.view = view;
        this.agendaModel = model;
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
