package com.interactionprog.agendabuilder.android.view;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.android.AgendaBuilderApplication;
import com.interactionprog.agendabuilder.model.Activity;

import org.w3c.dom.Text;

public class ActivityEditorDialog {

    View view;
    Activity editActivity;
    static EditText nameActivity;
    static EditText lengthActivity;
    static EditText descriptionActivity;
    TextView minView;
    static TextView warningPromptView;
    static Spinner dropdown;

    public ActivityEditorDialog(View activityEditorView, Activity act){

        this.view = view;
        this.editActivity = act;


        /*
        //inflating the layout
        LayoutInflater inflater = (LayoutInflater) AgendaBuilderApplication.getAppContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View activityEditorView = inflater.inflate(R.layout.editor_activity_dialog, null);
        //making margins for better layouting
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(0,0,20,0);
        activityEditorView.setLayoutParams(ll);

        //creating the dialog alert to add stuff
        final Dialog dialog = new Dialog(activityEditorView.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.editor_activity_dialog);*/


        //intializing the spinner to hold different numbers
        dropdown = (Spinner)activityEditorView.findViewById(R.id.spinner);
        String[] items = new String[]{"Presentation", "Group Work", "Discussion", "Break"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activityEditorView.getContext(),
                R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);

        //initializing the other UI components
        nameActivity = (EditText)activityEditorView.findViewById(R.id.editText);
        lengthActivity = (EditText)activityEditorView.findViewById(R.id.editText2);
        descriptionActivity = (EditText)activityEditorView.findViewById(R.id.editText3);
        minView = (TextView)activityEditorView.findViewById(R.id.textView);
        warningPromptView = (TextView)activityEditorView.findViewById(R.id.textView14);

        //changing text color
        nameActivity.setTextColor(Color.BLACK);
        lengthActivity.setTextColor(Color.BLACK);
        descriptionActivity.setTextColor(Color.BLACK);
        minView.setTextColor(Color.BLACK);
        warningPromptView.setTextColor(Color.BLACK);
        nameActivity.setHintTextColor(AgendaBuilderApplication.getAppContext().getResources().getColor(R.color.lightGrey));
        lengthActivity.setHintTextColor(AgendaBuilderApplication.getAppContext().getResources().getColor(R.color.lightGrey));
        descriptionActivity.setHintTextColor(AgendaBuilderApplication.getAppContext().getResources().getColor(R.color.lightGrey));

        //populate fields if there are fields to populate
        if(editActivity!=null){
            nameActivity.setText(editActivity.getName());
            lengthActivity.setText(editActivity.getLength());
            descriptionActivity.setText(editActivity.getDescription());
            dropdown.setSelection(editActivity.getType() - 1);
        }

    }

    public static void setWarningMessage(String warningMessage){

        warningPromptView.setText(warningMessage);

    }

    public static void setDetailsOfActivityToEdit(Activity act){
        nameActivity.setText(act.getName());
        lengthActivity.setText(String.valueOf(act.getLength()));
        descriptionActivity.setText(act.getDescription());
        dropdown.setSelection(act.getType()-1);
    }

}
