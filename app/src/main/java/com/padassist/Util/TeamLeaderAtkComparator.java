package com.padassist.Util;

import com.padassist.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamLeaderAtkComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getLead().getTotalAtk() < rhs.getLead().getTotalAtk()) {
            return 1;
        } else if (lhs.getLead().getTotalAtk() == rhs.getLead().getTotalAtk()) {
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
