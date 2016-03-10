package com.interactionprog.agendabuilder.android;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.OneAgendaView;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;

import java.util.List;

public class AgendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //getting model and days
        AgendaModel agendaModel = AgendaModel.getInstance();
        List<Day> allDays = agendaModel.getDays();
        for(Day d:allDays){
            OneAgendaView oneAgendaView = new OneAgendaView
                    (findViewById(R.id.one_agenda_view_id), agendaModel, d);
        }
    }

}
