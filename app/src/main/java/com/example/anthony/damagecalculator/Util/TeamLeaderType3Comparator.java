package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamLeaderType3Comparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getLead().getType3() == -1 && rhs.getLead().getType3() == -1) {
            if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                return 1;
            } else {
                return -1;
            }
        } else if (lhs.getLead().getType3() == -1) {
            return 1;
        } else if (rhs.getLead().getType3() == -1) {
            return -1;
        } else {
            if (lhs.getLead().getType3() > rhs.getLead().getType3()) {
                return 1;
            } else if (lhs.getLead().getType3() == rhs.getLead().getType3()) {
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
