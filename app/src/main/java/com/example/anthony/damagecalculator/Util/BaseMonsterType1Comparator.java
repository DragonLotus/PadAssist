package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.BaseMonster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class BaseMonsterType1Comparator implements Comparator<BaseMonster> {
    @Override
    public int compare(BaseMonster lhs, BaseMonster rhs) {
        if (lhs.getType1() > rhs.getType1()) {
            return 1;
        } else if (lhs.getType1() == rhs.getType1()) {
            if (lhs.getMonsterId() > rhs.getMonsterId()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
