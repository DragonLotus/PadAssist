package com.padassist;

import android.content.Context;

/**
 * Created by Thomas on 4/7/2016.
 */
public class Singleton {

    private Context appContext;

    private Singleton(){}

    public void init(Context context){
        if(appContext == null){
            appContext = context;
        }
    }

    private Context getContext(){
        return appContext;
    }

    public static Context get(){
        return getInstance().getContext();
    }

    private static Singleton instance;

    public static Singleton getInstance(){
        return instance == null ?
                (instance = new Singleton()):
                instance;
    }
}
