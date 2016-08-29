package com.padassist.Util;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by DragonLotus on 8/25/2016.
 */

public class Migration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Log.d("Migration", "oldVersion is: " + oldVersion + " newVersion is: " + newVersion);
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            schema.get("Team").addField("teamBadge", int.class);
            oldVersion++;
        }
        if (oldVersion == 1) {
            schema.get("LeaderSkill").addField("minimumMatch", int.class);
            oldVersion++;
        }
        if (oldVersion == 2) {
//            schema.create("RealmString")
//                    .addField("value", String.class);
//            schema.get("Monster")
//                    .addField("activeSkill2", String.class)
//                    .addField("activeSkillLevel", int.class)
//                    .addField("activeSkill2Level", int.class);

            oldVersion++;
        }
    }
}
