package com.interactionprog.agendabuilder.android.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.interactionprog.agendabuilder.R;
import com.interactionprog.agendabuilder.model.AgendaModel;

import java.util.Observable;
import java.util.Observer;

public class AllActivitiesView implements Observer {

    View view;
    AgendaModel agendaModel;

    public AllActivitiesView(View view, AgendaModel model){
        this.view = view;
        this.agendaModel = model;

        //initializing spinner
        Spinner dropdown = (Spinner)view.findViewById(R.id.spinner2);
        TextView lengthTextView = (TextView)view.findViewById(R.id.textView8);
        TextView typeTextView = (TextView)view.findViewById(R.id.textView10);
        TextView descriptionTextView = (TextView)view.findViewById(R.id.textView12);


        //initializing other fields

    }

    @Override
    public void update(Observable observable, Object data) {

        //updating spinenr every time a new activity is added
        /*
        String[] items = new String[]{"Presentation", "Group Work", "Discussion", "Break"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activityEditorView.getContext(),
                R.layout.spinner_item, items);
        dropdown.setAdapter(adapter);*/

    }

}
