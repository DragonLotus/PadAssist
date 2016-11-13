package com.padassist.Comparators;

import com.padassist.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class MonsterType1Comparator implements Comparator<Monster> {
    @Override
    public int compare(Monster lhs, Monster rhs) {
        if (lhs.getType1() > rhs.getType1()) {
            return 1;
        } else if (lhs.getType1() == rhs.getType1()) {
            if (lhs.getElement1Int() > rhs.getElement1Int()) {
                return 1;
            } else if (lhs.getElement1Int() == rhs.getElement1Int()) {
                if (lhs.getRarity() < rhs.getRarity()) {
                    return 1;
                } else if (lhs.getRarity() == rhs.getRarity()) {
                    if (lhs.getElement2Int() == -1 && rhs.getElement2Int() == -1) {
                        if (lhs.getBaseMonsterId() > rhs.getBaseMonsterId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else if (lhs.getElement2Int() == -1) {
                        return 1;
                    } else if (rhs.getElement2Int() == -1) {
                        return -1;
                    } else {
                        if (lhs.getElement2Int() > rhs.getElement2Int()) {
                            return 1;
                        } else if (lhs.getElement2Int() == rhs.getElement2Int()) {
                            if (lhs.getBaseMonsterId() > rhs.getBaseMonsterId()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
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
