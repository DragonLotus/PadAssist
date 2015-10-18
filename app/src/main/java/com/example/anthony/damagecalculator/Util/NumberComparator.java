package com.example.anthony.damagecalculator.Util;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class NumberComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer lhs, Integer rhs) {
        if(lhs > rhs){
            return 1;
        }
        else return -1;
    }
}
