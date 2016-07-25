package com.padassist.Util;

import com.padassist.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/5/2015.
 */
public class MonsterAlphabeticalComparator implements Comparator<Monster> {
    @Override
    public int compare(Monster lhs, Monster rhs) {
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
