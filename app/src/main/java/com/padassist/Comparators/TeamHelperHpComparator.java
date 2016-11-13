package com.padassist.Comparators;

import com.padassist.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamHelperHpComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getHelper().getTotalHp() < rhs.getHelper().getTotalHp()) {
            return 1;
        } else if (lhs.getHelper().getTotalHp() == rhs.getHelper().getTotalHp()) {
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
