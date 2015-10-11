package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamLeaderElement2Comparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getLead().getElement2Int() == -1 && rhs.getLead().getElement2Int() == -1) {
            if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                return 1;
            } else {
                return -1;
            }
        } else if (lhs.getLead().getElement2Int() == -1) {
            return 1;
        } else if (rhs.getLead().getElement2Int() == -1) {
            return -1;
        } else {
            if (lhs.getLead().getElement2Int() > rhs.getLead().getElement2Int()) {
                return 1;
            } else if (lhs.getLead().getElement2Int() == rhs.getLead().getElement2Int()) {
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
}
