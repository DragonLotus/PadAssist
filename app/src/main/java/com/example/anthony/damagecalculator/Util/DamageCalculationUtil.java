package com.example.anthony.damagecalculator.Util;

import android.util.Log;

import com.example.anthony.damagecalculator.Data.Color;
import com.example.anthony.damagecalculator.Data.Enemy;
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
                totalOrbDamage += orbMatch(monster.getTotalAtk(), orbMatches.get(i), orbAwakenings, monster.getTPA());
            }
        }
        return comboMultiplier(totalOrbDamage, combos);
    }

    public static double monsterElement2Damage(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos) {
        double totalOrbDamage = 0;
        if(monster.getElement1().equals(monster.getElement2())){
            for(int i = 0; i < orbMatches.size(); i++) {
                if(orbMatches.get(i).getColor().equals(monster.getElement2())) {
                    totalOrbDamage += orbMatch((int)Math.ceil(monster.getTotalAtk() * .1), orbMatches.get(i), orbAwakenings, monster.getTPA());
                }
            }
            return comboMultiplier(totalOrbDamage, combos);
        }
        for(int i = 0; i < orbMatches.size(); i++) {
            if(orbMatches.get(i).getColor().equals(monster.getElement2())) {
                totalOrbDamage += orbMatch((int)Math.ceil(monster.getTotalAtk() / 3), orbMatches.get(i), orbAwakenings, monster.getTPA());
            }
        }
        return comboMultiplier(totalOrbDamage, combos);
    }

    public static double comboMultiplier(double damage, int combos){
        return Math.ceil(damage * ((combos - 1) * .25 + 1.0));
    }

    public static double monsterElement1DamageEnemy(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy){
        double damage = monsterElement1DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy);
        if(damage == 0){
            return 0;
        }
        damage = monsterDamageEnemyDefense(damage, enemy);
        return damage;
    }

    public static double monsterElement2DamageEnemy(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy){
        double damage = monsterElement2DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy);
        if(damage == 0){
            return 0;
        }
        damage = monsterDamageEnemyDefense(damage, enemy);
        return damage;
    }

    public static double monsterElement1DamageEnemyElement(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy){
        double damage = monsterElement1Damage(monster, orbMatches, orbAwakenings, combos);
        if(monster.getElement1().equals(Color.RED)){
            if(enemy.getTargetColor().equals(Color.BLUE)){
                damage = damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.GREEN)){
                damage = damage*2;
            }
        }
        else if(monster.getElement1().equals(Color.BLUE)){
            if(enemy.getTargetColor().equals(Color.GREEN)){
                damage = damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.RED)){
                damage = damage*2;
            }
        }
        else if(monster.getElement1().equals(Color.GREEN)){
            if(enemy.getTargetColor().equals(Color.RED)){
                damage = damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.BLUE)){
                damage = damage*2;
            }
        }
        else if(monster.getElement1().equals(Color.LIGHT)){
            if(enemy.getTargetColor().equals(Color.DARK)){
                damage = damage*2;
            }
        }
        else if(monster.getElement1().equals(Color.DARK)){
            if(enemy.getTargetColor().equals(Color.LIGHT)){
                damage = damage*2;
            }
        }
        return damage;
    }

    public static double monsterElement2DamageEnemyElement(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy){
        double damage = monsterElement2Damage(monster, orbMatches, orbAwakenings, combos);
        if (damage == 0){
            return 0;
        }
        if(monster.getElement2().equals(Color.RED)){
            if(enemy.getTargetColor().equals(Color.BLUE)){
                damage = damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.GREEN)){
                damage = damage*2;
            }
        }
        else if(monster.getElement2().equals(Color.BLUE)){
            if(enemy.getTargetColor().equals(Color.GREEN)){
                damage = damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.RED)){
                damage = damage*2;
            }
        }
        else if(monster.getElement2().equals(Color.GREEN)){
            if(enemy.getTargetColor().equals(Color.RED)){
                damage = damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.BLUE)){
                damage = damage*2;
            }
        }
        else if(monster.getElement2().equals(Color.LIGHT)){
            if(enemy.getTargetColor().equals(Color.DARK)){
                damage = damage*2;
            }
        }
        else if(monster.getElement2().equals(Color.DARK)){
            if(enemy.getTargetColor().equals(Color.LIGHT)){
                damage = damage*2;
            }
        }
        return damage;
    }

    public static double monsterDamageEnemyDefense(double damage, Enemy enemy){
        if(damage-enemy.getTargetDef()<0){
            return 1;
        }
        return damage - enemy.getTargetDef();
    }

    public static double monsterElement1DamageReduction(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy){
        double damage = monsterElement1DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy);
        if (damage == 0){
            return 0;
        }
        if(enemy.getReduction().isEmpty()){
            return monsterDamageEnemyDefense(damage, enemy);
        }
        else if(enemy.getReduction().contains(monster.getElement1())){
            return monsterDamageEnemyDefense(damage/2, enemy);
        }
        else return monsterDamageEnemyDefense(damage, enemy);
    }

    public static double monsterElement2DamageReduction(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy){
        double damage = monsterElement2DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy);
        if (damage == 0){
            return 0;
        }
        if(enemy.getReduction().isEmpty()){
            return monsterDamageEnemyDefense(damage, enemy);
        }
        else if(enemy.getReduction().contains(monster.getElement2())){
            return monsterDamageEnemyDefense(damage/2, enemy);
        }
        else return monsterDamageEnemyDefense(damage, enemy);
    }

    public static double monsterElement1DamageAbsorb(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy){
        double damage = monsterElement1DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy);
        return 0;
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
