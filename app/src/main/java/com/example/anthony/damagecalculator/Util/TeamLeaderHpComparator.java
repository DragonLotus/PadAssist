package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamLeaderHpComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getLead().getTotalHp() < rhs.getLead().getTotalHp()) {
            return 1;
        } else if (lhs.getLead().getTotalHp() == rhs.getLead().getTotalHp()) {
            if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
