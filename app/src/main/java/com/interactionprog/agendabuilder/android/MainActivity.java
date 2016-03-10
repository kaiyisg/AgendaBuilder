package com.interactionprog.agendabuilder.android;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.view.ActivityEditorDialog;
import com.interactionprog.agendabuilder.android.view.AllActivitiesView;
import com.interactionprog.agendabuilder.android.view.BgActivitiesView;
import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    AgendaModel agendaModel;
    Button addActivityButton;
    Button goToAgendaPlanningButton;
    Button editSelectedActivityButton;
    Spinner parkedActivitySelectorDropdown;
    BgActivitiesView bgActivitiesView;
    AllActivitiesView allActivitiesView;
    TextView warningPromptView;
    ActivityEditorDialog activityEditorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //initializing buttons and getting model
        AgendaBuilderApplication app = new AgendaBuilderApplication();

        //initializing model with already present data
        //agendaModel = app.getModel();
        agendaModel = AgendaModel.getModelWithExampleData();

        //initialize buttons and widgets for controller
        addActivityButton = (Button)findViewById(R.id.button3);
        goToAgendaPlanningButton = (Button)findViewById(R.id.button4);
        editSelectedActivityButton = (Button)findViewById(R.id.button5);
        parkedActivitySelectorDropdown = (Spinner)findViewById(R.id.spinner2);
        warningPromptView = (TextView)findViewById(R.id.textView14);

        //initializing views
        bgActivitiesView = new BgActivitiesView(findViewById(R.id.bg_activities_id), agendaModel);
        allActivitiesView = new AllActivitiesView(findViewById(R.id.all_activities_id), agendaModel);


        //adding a new parked activity
        addActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                activityEditorDialogOpener(v, null);
            }
        });

        //button to go to the next page to plan the agenda
        goToAgendaPlanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent;

            }
        });

        //button to edit the currently selected parked activity
        editSelectedActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chosenString = parkedActivitySelectorDropdown.getSelectedItem().toString();

                //retrieving the chosen activity
                List<Activity> allParkedActivities = agendaModel.getParkedActivites();
                Activity activityChoice = null;
                for (Activity a: allParkedActivities){
                    if(a.getName().equals(chosenString)){
                        activityChoice = a;
                    }
                }

                activityEditorDialogOpener(v, activityChoice);

            }
        });

        //changing the details of the items in the parked activities list
        parkedActivitySelectorDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenString = parkedActivitySelectorDropdown.getSelectedItem().toString();
                AllActivitiesView.updateParkedActivityDetails(chosenString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AllActivitiesView.updateParkedActivityDetails("");
            }
        });

    }

    private void activityEditorDialogOpener(View v, final Activity activityToEdit){

        //inflating the layout
        LayoutInflater inflater = (LayoutInflater) AgendaBuilderApplication.getAppContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View activityEditorView = inflater.inflate(R.layout.editor_activity_dialog, null);

        //initializing buttons and widgets in dialog
        Button saveActivity = (Button)activityEditorView.findViewById(R.id.button);
        Button cancelActivity = (Button)activityEditorView.findViewById(R.id.button2);
        Button deleteActivity = (Button)activityEditorView.findViewById(R.id.button6);
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

        activityEditorDialog = new ActivityEditorDialog(activityEditorView, null);
        if(activityToEdit!=null){
            activityEditorDialog.setDetailsOfActivityToEdit(activityToEdit);
        }

        //setting what happens when we save the activity
        saveActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameData = nameActivity.getText().toString();
                String lengthData = lengthActivity.getText().toString();
                String descriptionData = descriptionActivity.getText().toString();

                //throwing warning prompts for any fields that are not entered properly
                if (nameData.matches("")) {
                    ActivityEditorDialog.setWarningMessage("Please enter a title to proceed!");
                } else if (lengthData.matches("")) {
                    ActivityEditorDialog.setWarningMessage("Please enter a time period to proceed!");
                } else if (descriptionData.matches("")) {
                    ActivityEditorDialog.setWarningMessage("Please enter a description to proceed!");
                } else {

                    //save into new activity if it previously exisits
                    if (activityToEdit != null) {
                        agendaModel.removeParkedActivity(activityToEdit);
                    }

                    //save into a new activity
                    agendaModel.addParkedActivity(new Activity(nameData,
                            descriptionData,
                            Integer.valueOf(lengthData),
                            (parkedActivitySelectorDropdown.getSelectedItemPosition() + 1)));

                    dialog.cancel();
                }
            }
        });

        //setting what happens when we cancel the activity
        cancelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        //setting what happens when we delete the activity
        deleteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityToEdit != null) {
                    agendaModel.removeParkedActivity(activityToEdit);
                }
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
