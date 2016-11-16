package com.padassist.Util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.padassist.Data.ActiveSkill;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.LeaderSkill;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmObjectSchema;
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
            schema.create("RealmString")
                    .addField("value", String.class);
            schema.get("Monster")
                    .addField("activeSkill2", String.class)
                    .addField("activeSkillLevel", int.class)
                    .addField("activeSkill2Level", int.class);
            schema.create("ActiveSkill")
                    .addField("name", String.class)
                    .addPrimaryKey("name")
                    .addField("description", String.class)
                    .addField("minimumCooldown", int.class)
                    .addField("maximumCooldown", int.class);
            oldVersion++;
        }

        if(oldVersion == 3){
            schema.get("BaseMonster")
                    .renameField("leaderSkill", "leaderSkillString")
                    .renameField("activeSkill", "activeSkillString")
                    .addRealmObjectField("leaderSkill", schema.get("LeaderSkill"))
                    .addRealmObjectField("activeSkill", schema.get("ActiveSkill"))
                    .addField("monsterIdString", String.class)
                    .addField("type1String", String.class)
                    .addField("type2String", String.class)
                    .addField("type3String", String.class)
                    .addIndex("monsterIdString")
                    .addIndex("type1String")
                    .addIndex("type2String")
                    .addIndex("type3String");
            schema.get("Monster")
                    .renameField("activeSkill2", "activeSkill2String")
                    .addRealmObjectField("activeSkill2", schema.get("ActiveSkill"))
                    .addField("baseMonsterIdString", String.class)
                    .addField("type1String", String.class)
                    .addField("type2String", String.class)
                    .addField("type3String", String.class)
                    .addIndex("baseMonsterIdString")
                    .addIndex("type1String")
                    .addIndex("type2String")
                    .addIndex("type3String")
                    .addField("name", String.class, FieldAttribute.INDEXED);
            oldVersion++;
        }

        if(oldVersion == 4){
            schema.get("BaseMonster")
                    .addIndex("leaderSkillString")
                    .addIndex("activeSkillString");
            schema.get("Monster")
                    .addIndex("activeSkill2String")
                    .addField("activeSkillString", String.class, FieldAttribute.INDEXED)
                    .addField("leaderSkillString", String.class, FieldAttribute.INDEXED);
            oldVersion++;
        }

        if(oldVersion == 5){
            schema.create("Enemy")
                    .addField("enemyId", long.class, FieldAttribute.PRIMARY_KEY)
                    .addField("enemyName", String.class, FieldAttribute.INDEXED)
                    .addField("monsterIdPicture", long.class)
                    .addField("targetDef", int.class)
                    .addField("beforeDefenseBreak", int.class)
                    .addField("damageThreshold", int.class)
                    .addField("damageImmunity", int.class)
                    .addField("reductionValue", int.class)
                    .addField("targetHp", long.class)
                    .addField("currentHp", long.class)
                    .addField("beforeGravityHP", long.class)
                    .addField("gravityPercent", double.class)
                    .addRealmListField("targetElement", schema.get("RealmElement"))
                    .addRealmListField("reduction", schema.get("RealmElement"))
                    .addRealmListField("absorb", schema.get("RealmElement"))
                    .addRealmListField("gravityList", schema.get("RealmInt"))
                    .addRealmListField("types", schema.get("RealmInt"))
                    .addField("hasAbsorb", boolean.class)
                    .addField("hasReduction", boolean.class)
                    .addField("hasDamageThreshold", boolean.class)
                    .addField("isDamaged", boolean.class)
                    .addField("hasDamageImmunity", boolean.class);
        }

    }
}
