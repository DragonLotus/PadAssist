package com.padassist.Util;

import com.padassist.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamHelperRcvComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getHelper().getTotalRcv() < rhs.getHelper().getTotalRcv()) {
            return 1;
        } else if (lhs.getHelper().getTotalRcv() == rhs.getHelper().getTotalRcv()) {
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
