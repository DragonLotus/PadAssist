package com.padassist.Util;

import com.padassist.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class MonsterPlusAtkComparator implements Comparator<Monster> {
    @Override
    public int compare(Monster lhs, Monster rhs) {
        if (lhs.getMonsterId() == 0){
            return -1;
        }else if (rhs.getMonsterId() == 0){
            return 1;
        }else if (lhs.getAtkPlus() < rhs.getAtkPlus()) {
            return 1;
        } else if (lhs.getAtkPlus() == rhs.getAtkPlus()) {
            if (lhs.getElement1Int() > rhs.getElement1Int()) {
                return 1;
            } else if (lhs.getElement1Int() == rhs.getElement1Int()) {
                if (lhs.getBaseMonsterId() > rhs.getBaseMonsterId()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
