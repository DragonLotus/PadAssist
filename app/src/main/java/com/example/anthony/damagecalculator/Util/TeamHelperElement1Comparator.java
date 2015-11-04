package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/5/2015.
 */
public class TeamHelperElement1Comparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getHelper().getElement1Int() > rhs.getHelper().getElement1Int()) {
            return 1;
        } else if (lhs.getHelper().getElement1Int() == rhs.getHelper().getElement1Int()) {
            if (lhs.getHelper().getRarity() < rhs.getHelper().getRarity()) {
                return 1;
            } else if (lhs.getHelper().getRarity() == rhs.getHelper().getRarity()) {
                if (lhs.getHelper().getElement2Int() == -1 && rhs.getHelper().getElement2Int() == -1) {
                    if (lhs.getHelper().getBaseMonsterId() > rhs.getHelper().getBaseMonsterId()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (lhs.getHelper().getElement2Int() == -1) {
                    return 1;
                } else if (rhs.getHelper().getElement2Int() == -1) {
                    return -1;
                } else {
                    if (lhs.getHelper().getElement2Int() > rhs.getHelper().getElement2Int()) {
                        return 1;
                    } else if (lhs.getHelper().getElement2Int() == rhs.getHelper().getElement2Int()) {
                        if (lhs.getHelper().getBaseMonsterId() > rhs.getHelper().getBaseMonsterId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
