package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.BaseMonster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/5/2015.
 */
public class BaseMonsterAlphabeticalComparator implements Comparator<BaseMonster> {
    @Override
    public int compare(BaseMonster lhs, BaseMonster rhs) {
        if (lhs.getMonsterId() == 0){
            return -1;
        }else if (rhs.getMonsterId() == 0){
            return 1;
        } else {
            int compare = String.CASE_INSENSITIVE_ORDER.compare(lhs.getName(), rhs.getName());
            return compare;
        }
    }
}
