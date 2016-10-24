package com.padassist.Comparators;

import com.padassist.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamLeaderPlusComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getLead().getTotalPlus() < rhs.getLead().getTotalPlus()) {
            return 1;
        } else if (lhs.getLead().getTotalPlus() == rhs.getLead().getTotalPlus()) {
            if (lhs.getLead().getElement1Int() > rhs.getLead().getElement1Int()) {
                return 1;
            } else if (lhs.getLead().getElement1Int() == rhs.getLead().getElement1Int()) {
                if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }

    }
}
