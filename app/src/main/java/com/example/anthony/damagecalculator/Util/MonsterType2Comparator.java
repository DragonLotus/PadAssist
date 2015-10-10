package com.example.anthony.damagecalculator.Util;


import android.util.Log;

import com.example.anthony.damagecalculator.Data.Monster;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class MonsterType2Comparator implements Comparator<Monster> {
    @Override
    public int compare(Monster lhs, Monster rhs) {
        Log.d("Type 2 Comparator", "I am enter comparator.");
        if (lhs.getMonsterId() == 0){
            return -1;
        }else if (rhs.getMonsterId() == 0){
            return 1;
        }else if (lhs.getType2() == -1 && rhs.getType2() == -1){
            Log.d("Type 2 Comparator", "lhs monster ID is: " + lhs.getMonsterId() + " rhs monster ID is: " + rhs.getMonsterId());
            if (lhs.getMonsterId() > rhs.getMonsterId()){
                Log.d("Type 2 Comparator", "Return 1");
                return 1;
            }else {
                Log.d("Type 2 Comparator", "Return -1");
                return -1;
            }
        }else if (lhs.getType2() == -1){
            return 1;
        }else if (rhs.getType2() == -1){
            return -1;
        }else {
            if (lhs.getType2() > rhs.getType2()) {
                return 1;
            } else if (lhs.getType2() == rhs.getType2()) {
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
