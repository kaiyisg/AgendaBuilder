package com.interactionprog.agendabuilder.android;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.BgActivitiesView;
import com.interactionprog.agendabuilder.model.AgendaModel;

public class MainActivity extends AppCompatActivity {

    AgendaModel agendaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        AgendaBuilderApplication app = new AgendaBuilderApplication();
        agendaModel = app.getModel();

        BgActivitiesView bgActivitiesView = new BgActivitiesView(
                findViewById(R.id.bg_activities_id), agendaModel);
    }
}
