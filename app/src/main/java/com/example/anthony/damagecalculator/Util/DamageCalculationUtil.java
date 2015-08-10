package com.example.anthony.damagecalculator.Util;

import android.util.Log;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;

import java.util.ArrayList;

/**
 * Created by Thomas on 7/11/2015.
 */
public class DamageCalculationUtil {


    public static double monsterElement1Damage(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos) {
        double totalOrbDamage = 0;
        for (int i = 0; i < orbMatches.size(); i++) {
            if (orbMatches.get(i).getColor().equals(monster.getElement1())) {
                totalOrbDamage += orbMatch(monster.getTotalAtk(), orbMatches.get(i), orbAwakenings, 0);
            }
        }
        return comboMultiplier(totalOrbDamage, combos);
    }

    public static double monsterElement2Damage(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos) {
        double totalOrbDamage = 0;
        for(int i = 0; i < orbMatches.size(); i++) {
            if(orbMatches.get(i).getColor().equals(monster.getElement2())) {
                totalOrbDamage += orbMatch((monster.getTotalAtk() / 3), orbMatches.get(i), orbAwakenings, 0);
            }
        }
        return comboMultiplier(totalOrbDamage, combos);
    }

    public static double comboMultiplier(double damage, int combos){
        return Math.ceil(damage * ((combos - 1)*.25 + 1.0));
    }

    public static double orbMatch(int Attack, OrbMatch orbMatches, int OrbAwakenings, int TPAwakenings) {
        // Write in combos
        // Attack, Orb Awakenings, TPA from API, draw from Monster Database
        //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
        double damage = 0;
        if (orbMatches.getOrbsLinked() < 3) {
            throw new IllegalArgumentException();
        }
        if (orbMatches.getNumOrbPlus() == 0) {
            damage = Attack * (((orbMatches.getOrbsLinked() - 3) * .25) + 1);
        } else {
            damage = Attack * (((orbMatches.getOrbsLinked() - 3) * .25) + 1)
                    * ((orbMatches.getNumOrbPlus() * .06) + 1) * ((OrbAwakenings * .05) + 1);
        }
        if (orbMatches.getOrbsLinked() == 4) {
            damage = damage * Math.pow(1.5, TPAwakenings);
        }

        return Math.ceil(damage);
    }

    public static double orbMatch(int Attack, int OrbsLinked, int OrbPlus, int OrbAwakenings, int TPAwakenings) {
        // Write in combos
        // Attack, Orb Awakenings, TPA from API, draw from Monster Database
        //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
        if (OrbsLinked < 3) {
            throw new IllegalArgumentException();
        }
        double damage = Attack * (((OrbsLinked - 3) * .25) + 1)
                * ((OrbPlus * .06) + 1) * ((OrbAwakenings * .05) + 1);
        if (OrbsLinked == 4) {
            damage = damage * Math.pow(1.5, TPAwakenings);
        }

        return Math.ceil(damage);
    }

    public static double finalMultiplier(double leadMul, double extraMul, int rowAwakenings, int numberOfRows) {
        //Extra multiplier can be skills
        //Lead multiplier needs conditions
        double multiplier;
        if (numberOfRows == 0) {
            multiplier = leadMul * extraMul;
        } else {
            multiplier = leadMul * extraMul * ((rowAwakenings * 0.1) * numberOfRows + 1);
        }
        return multiplier;
    }

    public static double monsterStatCalc(int minimumStat, int maximumStat, int currentLevel, int maxLevel, double statScale) {
        if (currentLevel <= 1) {
            return minimumStat;
        }
        //return (int) Math.floor(minimumStat + (maximumStat - minimumStat) * (Math.pow((double) ((currentLevel - 1) / (maxLevel - 1)), statScale)));
        return Math.floor(minimumStat + (maximumStat - minimumStat) * (Math.pow((double) (currentLevel - 1) / (maxLevel - 1), statScale)));
    }
}
