package com.interactionprog.agendabuilder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public final class AgendaModel extends Observable {

    private static AgendaModel instance = null;

    List<Day> days = new ArrayList<Day>();
    List<Activity> parkedActivites = new ArrayList<Activity>();

    //making this class a singleton class
    private AgendaModel(){
    }

    public static AgendaModel getInstance(){
        if (instance ==null){
            instance = new AgendaModel();
        }
        return instance;
    }

    /**
     * adds create and add a new day to model with starting time (hours and minutes)
     */
    public Day addDay(int startHour, int startMin) {
        Day d = new Day(startHour, startMin);
        days.add(d);
        return d;
    }

    /**
     * add an activity to model
     */
    public void addActivity(Activity act, Day day, int position) {
        day.addActivity(act, position);
        setChanged();
        notifyObservers("ActivityAddedToDay");
    }

    /**
     * add an activity to parked activities
     */
    public void addParkedActivity(Activity act) {
        parkedActivites.add(act);
        setChanged();
        notifyObservers("ActivityParked");
    }

    /**
     * remove an activity on provided position from parked activites
     */
    public Activity removeParkedActivity(int position) {
        Activity act =  parkedActivites.remove(position);
        setChanged();
        notifyObservers("ParkedActivityRemoved");
        return act;
    }

    /**
     * remove an activity on provided name from parked activites
     */
    public Activity removeParkedActivity(Activity act) {

        int position = 0;
        for(Activity a: parkedActivites){
            if(a.equals(act)){
                break;
            }
            position +=1;
        }
        parkedActivites.remove(position);
        setChanged();
        notifyObservers("ParkedActivityRemoved");
        return act;
    }

    /**
     * moves activity between the days, or day and parked activities.
     * to park activity you need to set the newday to null
     * to move a parked activity to let's say first day you set oldday to null
     * and newday to first day instance
     */
    public void moveActivity(Day oldday, int oldposition, Day newday, int newposition) {
        if(oldday != null && oldday == newday) {
            oldday.moveActivity(oldposition,newposition);
        } else if(oldday == null && newday != null) {
            Activity act = removeParkedActivity(oldposition);
            newday.addActivity(act,newposition);
        } else if(oldday != null && newday == null){
            Activity act = oldday.removeActivity(oldposition);
            addParkedActivity(act);
        } else if(oldday != null && newday != null) {
            Activity activity = oldday.removeActivity(oldposition);
            newday.addActivity(activity,newposition);
        }
        setChanged();
        notifyObservers();
    };

    /*
     * Getter for the list of activities
     */
    public List<Activity> getParkedActivites(){
        return parkedActivites;
    }

    /*
     * Getter for the list of days
     */
    public List<Day> getDays(){
        return days;
    }

    /**
     * you can use this method to create some test data and test your implementation
     */
    public static AgendaModel getModelWithExampleData() {
        AgendaModel model = new AgendaModel();

        Day d = model.addDay(8,0);
        model.addActivity(new Activity("Introduction","Intro to the meeting",10,0),d,0);
        model.addActivity(new Activity("Idea 1","Presenting idea 1",30,0),d,1);
        model.addActivity(new Activity("Working in groups","Working on business model for idea 1",35,1),d,2);
        model.addActivity(new Activity("Idea 1 discussion","Discussing the results of idea 1",15,2),d,3);
        model.addActivity(new Activity("Coffee break","Time for some coffee",20,3),d,4);

        model.addParkedActivity(new Activity("Siesta", "Taking a 2h break", 120, Activity.BREAK));
        model.addParkedActivity(new Activity("Scrum", "Intense project brainstorming", 90, Activity.DISCUSSION));
        model.addParkedActivity(new Activity("Client Presentation", "Final presentation to clients", 45, Activity.PRESENTATION));


        return model;
    }
}

