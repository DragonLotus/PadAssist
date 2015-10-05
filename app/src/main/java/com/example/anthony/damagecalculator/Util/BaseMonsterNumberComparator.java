package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class BaseMonsterNumberComparator implements Comparator<BaseMonster> {
    @Override
    public int compare(BaseMonster lhs, BaseMonster rhs) {
        if (lhs.getMonsterId() > rhs.getMonsterId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
