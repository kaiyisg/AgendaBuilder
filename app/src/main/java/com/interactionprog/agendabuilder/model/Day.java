package com.interactionprog.agendabuilder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Day extends Observable {

    /**
     * the start of the agenda in min, counted from midnight
     */
    int start;

    List<Activity> activities = new ArrayList<Activity>();

    public List<Activity> getActivities(){
        return activities;
    }

    public Day(int hour, int min) {
        start = hour*60 + min;
    }

    public int getStart() {
        return start;
    }

    public String getStartHour(){
        String hour = String.valueOf(getStart()/60);
        if(hour.length()==1){
            hour = "0" + hour;
        }
        return hour;
    }

    public String getStartMin(){
        String min = String.valueOf(getStart()%60);
        if(min.length()==1){
            min = "0" + min;
        }
        return min;
    }

    public void setStart(int start) {
        this.start = start;
        setChanged();
        notifyObservers("StartChanged");
    }

    /**
     * returns the total length of the acitivities in a day in minutes
     */
    public int getTotalLength() {
        int result = 0;
        for(Activity act:activities) {
            result += act.getLength();
        }
        return result;
    }

    public String getTotalLengthTime(){
        String hour = String.valueOf(getTotalLength()/60);
        if(hour.length()==1){
            hour = "0" + hour;
        }
        String min = String.valueOf(getTotalLength()%60);
        if(min.length()==1){
            min = "0" + min;
        }
        return hour+":"+min;
    }

    public int getEnd() {
        return getStart() + getTotalLength();
    }

    public String getEndTime(){
        String hour = String.valueOf(getEnd()/60);
        if(hour.length()==1){
            hour = "0" + hour;
        }
        String min = String.valueOf(getEnd()%60);
        if(min.length()==1){
            min = "0" + min;
        }
        return hour+":"+min;
    }

    /**
     * returns the length (in minutes) of activities of certain type
     */
    public int getLengthByType(int type) {
        int result = 0;
        for(Activity act:activities) {
            if(act.getType() == type) {
                result += act.getLength();
            }
        }
        return result;
    }

    /**
     * adds an activity to specific position
     * this method will be called when needed from the model
     * don't call it directly
     */
    int addActivity(Activity act,int position){
        if(position > activities.size()) {
            position = activities.size();
        }
        activities.add(position, act);
        setChanged();
        notifyObservers("ActivityAdded");
        return position;
    }

    public int getLastPositionIndex(){
        return activities.size();
    }

    /**
     * removes an activity from specific position
     * this method will be called when needed from the model
     * don't call it directly
     */
    Activity removeActivity(int position) {
        Activity act = activities.remove(position);
        setChanged();
        notifyObservers("ActivityRemoved");
        return act;
    }

    /**
     * moves activity inside one day
     * this method will be called when needed from the model
     * don't call it directly
     */
    void moveActivity(int oldPosition, int newPosition) {
        if(newPosition>oldPosition){
            newPosition--;
        }
        Activity act = activities.remove(oldPosition);
        activities.add(newPosition,act);
        setChanged();
        notifyObservers("ActivityMoved");
    }


}
