package com.interactionprog.agendabuilder.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.ActivityEditorDialog;
import com.interactionprog.agendabuilder.android.view.AllActivitiesView;
import com.interactionprog.agendabuilder.android.view.BgActivitiesView;
import com.interactionprog.agendabuilder.model.AgendaModel;

public class MainActivity extends AppCompatActivity {

    AgendaModel agendaModel;
    Button addActivityButton;
    Button goToAgendaPlanningButton;
    Button editSelectedActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //initializing buttons and getting model
        AgendaBuilderApplication app = new AgendaBuilderApplication();
        agendaModel = app.getModel();
        addActivityButton = (Button)findViewById(R.id.button3);
        goToAgendaPlanningButton = (Button)findViewById(R.id.button4);
        editSelectedActivityButton = (Button)findViewById(R.id.button5);

        //initializing views
        BgActivitiesView bgActivitiesView = new BgActivitiesView(
                findViewById(R.id.bg_activities_id), agendaModel);
        AllActivitiesView allActivitiesView = new AllActivitiesView(
                findViewById(R.id.all_activities_id), agendaModel);


        addActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //inflating the layout
                LayoutInflater inflater = (LayoutInflater) AgendaBuilderApplication.getAppContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View activityEditorView = inflater.inflate(R.layout.editor_activity_dialog, null);

                //initializing buttons and widgets in dialog
                Button saveActivity = (Button)activityEditorView.findViewById(R.id.button);
                Button cancelActivity = (Button)activityEditorView.findViewById(R.id.button2);
                final EditText nameActivity = (EditText)activityEditorView.findViewById(R.id.editText);
                final EditText lengthActivity = (EditText)activityEditorView.findViewById(R.id.editText2);
                final EditText descriptionActivity = (EditText)activityEditorView.findViewById(R.id.editText3);

                //creating dialog box
                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(activityEditorView);

                //changing dialog box to go towards the right
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.END;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                ActivityEditorDialog activityEditorDialog =
                        new ActivityEditorDialog(activityEditorView, null);

                //setting what happens when we save the activity
                saveActivity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nameData = nameActivity.getText().toString();
                        String lengthData = lengthActivity.getText().toString();
                        String descriptionData = nameActivity.getText().toString();

                        if (nameData.matches("")) {

                            Toast.makeText(AgendaBuilderApplication.getAppContext(),
                                    "Please enter a title to proceed!", Toast.LENGTH_SHORT).show();

                        } else if (lengthData.matches("")) {

                            Toast.makeText(AgendaBuilderApplication.getAppContext(),
                                    "Please enter a time period to proceed!", Toast.LENGTH_SHORT).show();

                        } else if (descriptionData.matches("")) {

                            Toast.makeText(AgendaBuilderApplication.getAppContext(),
                                    "Please enter a description to proceed!", Toast.LENGTH_SHORT).show();

                        } else {

                            //getting all the information from the fields

                            //save into the old activity if it exists

                            //save into a new activity if it does not previously exist


                        }

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

        editSelectedActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }


    private void alertDialogPromptBuilder(String alert, View v){
        new AlertDialog.Builder(v.getContext())
                .setTitle("Enter details")
                .setMessage(alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

}
