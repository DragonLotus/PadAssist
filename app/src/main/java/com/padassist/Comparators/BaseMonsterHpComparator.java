package com.padassist.Comparators;

import com.padassist.Data.BaseMonster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class BaseMonsterHpComparator implements Comparator<BaseMonster> {
    @Override
    public int compare(BaseMonster lhs, BaseMonster rhs) {
        if (lhs.getMonsterId() == 0) {
            return -1;
        } else if (rhs.getMonsterId() == 0) {
            return 1;
        } else {
            if (lhs.getHpMax() < rhs.getHpMax()) {
                return 1;
            } else if (lhs.getHpMax() == rhs.getHpMax()) {
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
}
