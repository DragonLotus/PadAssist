package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class MonsterAtkComparator implements Comparator<Monster> {
    @Override
    public int compare(Monster lhs, Monster rhs) {
        if (lhs.getMonsterId() == 0) {
            return -1;
        } else if (rhs.getMonsterId() == 0) {
            return 1;
        } else {
            if (lhs.getTotalAtk() < rhs.getTotalAtk()) {
                return 1;
            } else if (lhs.getTotalAtk() == rhs.getTotalAtk()) {
                if (lhs.getBaseMonsterId() > rhs.getBaseMonsterId()) {
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
