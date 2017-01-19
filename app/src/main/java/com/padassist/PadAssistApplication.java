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
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(9)
                .migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
