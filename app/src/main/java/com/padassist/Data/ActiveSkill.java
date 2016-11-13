package com.padassist.Data;

import org.parceler.Parcel;

import io.realm.ActiveSkillRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DragonLotus on 9/18/2015.
 */
@Parcel(implementations = {ActiveSkillRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {ActiveSkill.class})
public class ActiveSkill extends RealmObject {
    @PrimaryKey
    private String name;

    private String description;

    private int minimumCooldown;

    private int maximumCooldown;

    public ActiveSkill() {
        name = "Blank";
        description = "Nothing is here";
        minimumCooldown = 0;
        maximumCooldown = 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaximumCooldown() {
        return maximumCooldown;
    }

    public void setMaximumCooldown(int maximumCooldown) {
        this.maximumCooldown = maximumCooldown;
    }

    public int getMinimumCooldown() {
        return minimumCooldown;
    }

    public void setMinimumCooldown(int minimumCooldown) {
        this.minimumCooldown = minimumCooldown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxLevel() {
        return maximumCooldown - minimumCooldown + 1;
    }
}
