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

import java.util.List;

public class OneActivityController {

    View view;
    AgendaModel agendaModel;
    Activity activity;
    TableRow tableRow;
    int positionOfActivityInDay;
    int positionOfDayInList;

    public OneActivityController(View v, AgendaModel model, Activity act, TableRow row, int positionOfAct,int positionOfDay){

        this.view = v;
        this.agendaModel = model;
        this.activity = act;
        this.tableRow = row;
        this.positionOfActivityInDay = positionOfAct;
        this.positionOfDayInList = positionOfDay;

        tableRow.setOnTouchListener(new MyTouchListener());

        //purpose of draglistener is to listen when rows are dragged onto each other
        tableRow.setOnDragListener(new MyDragListener(positionOfAct, positionOfDay));

    }

    public final class MyTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction()==MotionEvent.ACTION_DOWN){

                //getting position in the day and which day itself and storing it here
                String[] clipDescription = {String.valueOf(positionOfActivityInDay),
                String.valueOf(positionOfDayInList)};
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

    class MyDragListener implements View.OnDragListener{

        int positionOfActivityInDay;
        int positionOfDayInList;


        MyDragListener(int positionOfAct,int positionOfDay){
            this.positionOfActivityInDay = positionOfAct;
            this.positionOfDayInList = positionOfDay;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();
            ClipDescription des = event.getClipDescription();
            if(des!=null){
                String description = des.toString();
                String[] parts = description.split(" ");
                String initialpositionOfActivityInDayString = parts[2];
                int initialpositionOfActivityInDay = Integer.valueOf(initialpositionOfActivityInDayString);
                String initialpositionOfDayInListString = parts[3];
                int initialpositionOfDayInList = Integer.valueOf(initialpositionOfDayInListString);

                List<Day> allDays = agendaModel.getDays();
                Day initialDay = allDays.get(initialpositionOfDayInList);
                Day currentDay = allDays.get(positionOfDayInList);

                switch(action){
                    case DragEvent.ACTION_DRAG_STARTED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        //add to on top if replacing something with higher index
                        //add to below if replacing something with lower index
                        if(positionOfActivityInDay>initialpositionOfActivityInDay){
                            positionOfActivityInDay+=1;
                        }
                        agendaModel.moveActivity(initialDay, initialpositionOfActivityInDay,
                                currentDay, positionOfActivityInDay);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    default:
                        return false;
                }
            }
            return false;
        }
    }

}
