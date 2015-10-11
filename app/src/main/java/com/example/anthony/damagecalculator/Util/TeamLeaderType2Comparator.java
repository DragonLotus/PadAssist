package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamLeaderType2Comparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getLead().getType2() == -1 && rhs.getLead().getType2() == -1) {
            if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                return 1;
            } else {
                return -1;
            }
        } else if (lhs.getLead().getType2() == -1) {
            return 1;
        } else if (rhs.getLead().getType2() == -1) {
            return -1;
        } else {
            if (lhs.getLead().getType2() > rhs.getLead().getType2()) {
                return 1;
            } else if (lhs.getLead().getType2() == rhs.getLead().getType2()) {
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
