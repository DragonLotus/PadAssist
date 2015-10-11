package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/5/2015.
 */
public class TeamAlphabeticalComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        int compare = String.CASE_INSENSITIVE_ORDER.compare(lhs.getTeamName(), rhs.getTeamName());
        return compare;
    }
}
