package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamHelperAtkComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getHelper().getTotalAtk() < rhs.getHelper().getTotalAtk()) {
            return 1;
        } else if (lhs.getHelper().getTotalAtk() == rhs.getHelper().getTotalAtk()) {
            if (lhs.getHelper().getBaseMonsterId() > rhs.getHelper().getBaseMonsterId()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
