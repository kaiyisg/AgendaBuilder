package com.interactionprog.agendabuilder.android;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.AllAgendasView;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;

import java.util.List;

public class AgendaActivity extends AppCompatActivity {

    Button addDayButton;
    AllAgendasView allAgendasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //getting model and days
        final AgendaModel agendaModel = AgendaModel.getInstance();
        List<Activity> activities = agendaModel.getParkedActivites();
        List<Day> allDays = agendaModel.getDays();

        //initializing views
        allAgendasView = new AllAgendasView(findViewById(R.id.all_agendas_view_id), agendaModel);
    }

}
