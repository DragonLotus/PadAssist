package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/5/2015.
 */
public class TeamLeaderType1Comparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if(lhs.getLead().getType1() > rhs.getLead().getType1()){
            return 1;
        }else {
            return -1;
        }
    }
}
