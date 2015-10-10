package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class MonsterType3Comparator implements Comparator<Monster> {
    @Override
    public int compare(Monster lhs, Monster rhs) {
        if (lhs.getMonsterId() == 0){
            return -1;
        }else if (rhs.getMonsterId() == 0){
            return 1;
        }else if (lhs.getType3() == -1 && rhs.getType3() == -1){
            if (lhs.getBaseMonsterId() > rhs.getBaseMonsterId()){
                return 1;
            }else {
                return -1;
            }
        }else if (lhs.getType3() == -1){
            return 1;
        }else if (rhs.getType3() == -1){
            return -1;
        }else {
            if (lhs.getType3() > rhs.getType3()) {
                return 1;
            } else if (lhs.getType3() == rhs.getType3()) {
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
