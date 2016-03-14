package com.interactionprog.agendabuilder.android;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.interactionprog.agendabuilder.model.Activity;
import com.interactionprog.agendabuilder.model.AgendaModel;
import com.interactionprog.agendabuilder.model.Day;

public class OneActivityController {

    View view;
    AgendaModel agendaModel;
    Activity activity;
    TableRow tableRow;
    int positionOfActivityInAgenda;

    public OneActivityController(View v, AgendaModel model, Activity act, TableRow row, int position){

        this.view = v;
        this.agendaModel = model;
        this.activity = act;
        this.tableRow = row;
        this.positionOfActivityInAgenda = position;

        tableRow.setOnTouchListener(new MyTouchListener());

    }

    public final class MyTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction()==MotionEvent.ACTION_DOWN){

                //getting position in the day and storing it here
                String[] clipDescription = {String.valueOf(positionOfActivityInAgenda)};
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                ClipData data = new ClipData((CharSequence)v.getTag(),
                        clipDescription,
                        item);

                View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data,dragShadowBuilder,v,0);
                v.setVisibility(View.INVISIBLE);
                return true;
            }
            return false;
        }
    }

}
