package com.interactionprog.agendabuilder.android;

import android.app.Application;
import android.content.Context;

import com.interactionprog.agendabuilder.model.AgendaModel;

public class AgendaBuilderApplication extends Application {

    private AgendaModel model = AgendaModel.getInstance();
    private static Context context;

    public void onCreate() {
        super.onCreate();
        AgendaBuilderApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AgendaBuilderApplication.context;
    }

    public AgendaModel getModel(){
        return model;
    }

    public void setModel(AgendaModel model){
        this.model = model;
    }
}
