package com.padassist.Util;

import com.padassist.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class MonsterNumberComparator implements Comparator<Monster> {
    @Override
    public int compare(Monster lhs, Monster rhs) {
        if (lhs.getBaseMonsterId() > rhs.getBaseMonsterId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
