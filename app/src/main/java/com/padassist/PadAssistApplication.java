package com.padassist;

import android.app.Application;

import com.padassist.Util.Migration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by DragonLotus on 9/4/2016.
 */

public class PadAssistApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .schemaVersion(5)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
