package com.padassist.Util;

import com.padassist.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamHelperPlusHpComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getHelper().getHpPlus() < rhs.getHelper().getHpPlus()) {
            return 1;
        } else if (lhs.getHelper().getHpPlus() == rhs.getHelper().getHpPlus()) {
            if (lhs.getHelper().getElement1Int() > rhs.getHelper().getElement1Int()) {
                return 1;
            } else if (lhs.getHelper().getElement1Int() == rhs.getHelper().getElement1Int()) {
                if (lhs.getHelper().getBaseMonsterId() > rhs.getHelper().getBaseMonsterId()) {
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
