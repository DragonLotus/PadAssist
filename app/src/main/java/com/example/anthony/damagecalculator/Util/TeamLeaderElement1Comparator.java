package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/5/2015.
 */
public class TeamLeaderElement1Comparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if(lhs.getLead().getElement1Int() > rhs.getLead().getElement1Int()){
            return 1;
        }else {
            return -1;
        }
    }
}
