package com.interactionprog.agendabuilder.android;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.ActivityEditorDialog;
import com.interactionprog.agendabuilder.android.view.BgActivitiesView;
import com.interactionprog.agendabuilder.model.AgendaModel;

public class MainActivity extends AppCompatActivity {

    AgendaModel agendaModel;
    Button addActivityButton;
    Button goToAgendaPlanningButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        AgendaBuilderApplication app = new AgendaBuilderApplication();
        agendaModel = app.getModel();

        BgActivitiesView bgActivitiesView = new BgActivitiesView(
                findViewById(R.id.bg_activities_id), agendaModel);

        addActivityButton = (Button)findViewById(R.id.button3);
        goToAgendaPlanningButton = (Button)findViewById(R.id.button4);



        addActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //inflating the layout
                LayoutInflater inflater = (LayoutInflater) AgendaBuilderApplication.getAppContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View activityEditorView = inflater.inflate(R.layout.editor_activity_dialog, null);

                Button saveActivity = (Button)activityEditorView.findViewById(R.id.button);
                Button cancelActivity = (Button)activityEditorView.findViewById(R.id.button2);

                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(activityEditorView);

                ActivityEditorDialog activityEditorDialog =
                        new ActivityEditorDialog(activityEditorView, null);
                //v.findViewById(R.id.editor_activity_dialog_id)

                //setting what happens when we save the activity
                saveActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //checking if all fields are set up properly, else cancel saving

                        //getting all the information from the fields

                        //save into the old activity if it exists

                        //save into a new activity if it does not previously exist

                        dialog.cancel();

                    }
                });

                //setting what happens when we cancel the activity
                cancelActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.cancel();

                    }
                });


                dialog.show();

            }
        });

        goToAgendaPlanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent;

            }
        });


    }
}
