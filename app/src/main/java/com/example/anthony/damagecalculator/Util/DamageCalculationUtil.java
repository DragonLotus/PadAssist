package com.example.anthony.damagecalculator.Util;

import android.util.Log;

import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.LeaderSkill;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.Data.Team;

import java.util.ArrayList;

/**
 * Created by Thomas on 7/11/2015.
 */
public class DamageCalculationUtil {

    public static double monsterElement1Damage(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Team team) {
        double totalOrbDamage = 0;
        for (int i = 0; i < orbMatches.size(); i++) {
            if (orbMatches.get(i).getElement().equals(monster.getElement1())) {
                totalOrbDamage += orbMatch(monster.getTotalAtk(), orbMatches.get(i), orbAwakenings, monster.getTPA());
            }
        }
        Log.d("Damage Util Log", "Monster: " + monster.getName() + " Combo Multiplier Damage: " + comboMultiplier(totalOrbDamage, combos) + " Lead Skill Multiplier: " + team.getLeadSkill().atkElement1Multiplier(monster, team) * team.getHelperSkill().atkElement1Multiplier(monster, team));
        return Math.ceil(leadOtherMultiplier1(comboMultiplier(totalOrbDamage, combos), monster, team));
    }

    public static double monsterElement2Damage(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Team team) {
        double totalOrbDamage = 0;
        if (monster.getElement1().equals(monster.getElement2())) {
            for (int i = 0; i < orbMatches.size(); i++) {
                if (orbMatches.get(i).getElement().equals(monster.getElement2())) {
                    totalOrbDamage += orbMatch((int) Math.ceil(monster.getTotalAtk() * .1), orbMatches.get(i), orbAwakenings, monster.getTPA());
                }
            }
            Log.d("Damage Util Log", "Monster: " + monster.getName() + " Combo Multiplier Damage: " + comboMultiplier(totalOrbDamage, combos) + " Lead Skill Multiplier: " + team.getLeadSkill().atkElement2Multiplier(monster, team) * team.getHelperSkill().atkElement2Multiplier(monster, team));
            return Math.ceil(leadOtherMultiplier2(comboMultiplier(totalOrbDamage, combos), monster, team));
        }
        for (int i = 0; i < orbMatches.size(); i++) {
            if (orbMatches.get(i).getElement().equals(monster.getElement2())) {
                totalOrbDamage += orbMatch((int) Math.ceil(monster.getTotalAtk() / 3), orbMatches.get(i), orbAwakenings, monster.getTPA());
            }
        }
        Log.d("Damage Util Log", "Monster: " + monster.getName() + " Combo Multiplier Damage: " + comboMultiplier(totalOrbDamage, combos) + " Lead Skill Multiplier: " + team.getLeadSkill().atkElement2Multiplier(monster, team) * team.getHelperSkill().atkElement2Multiplier(monster, team));
        return Math.ceil(leadOtherMultiplier2(comboMultiplier(totalOrbDamage, combos), monster, team));
    }

    public static double comboMultiplier(double damage, int combos) {
        return Math.ceil(damage * ((combos - 1) * .25 + 1.0));
    }

    public static double leadOtherMultiplier1(double damage, Monster monster, Team team) {
        //No active skill multiplier
        double returnDamage = damage;
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getElement().equals(monster.getElement1())) {
                if (team.getOrbMatches().get(i).isRow()) {
                    counter++;
                }
            }
        }
        returnDamage = returnDamage * team.getLeadSkill().atkElement1Multiplier(monster, team) * team.getHelperSkill().atkElement1Multiplier(monster, team) * (team.getRowAwakenings(monster.getElement1()) * 0.1 * counter + 1);
        return returnDamage;
    }

    public static double leadOtherMultiplier2(double damage, Monster monster, Team team) {
        //No active skill multiplier
        double returnDamage = damage;
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getElement().equals(monster.getElement2())) {
                if (team.getOrbMatches().get(i).isRow()) {
                    counter++;
                }
            }
        }
        returnDamage = returnDamage * team.getLeadSkill().atkElement2Multiplier(monster, team) * team.getHelperSkill().atkElement2Multiplier(monster, team) * (team.getRowAwakenings(monster.getElement2()) * 0.1 * counter + 1);
        return returnDamage;
    }

    public static double monsterElement1DamageEnemy(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement1DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage == 0) {
            return 0;
        }
        damage = monsterDamageEnemyDefense(damage, enemy);
        return damage;
    }

    public static double monsterElement2DamageEnemy(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement2DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage == 0) {
            return 0;
        }
        damage = monsterDamageEnemyDefense(damage, enemy);
        return damage;
    }

    public static double monsterElement1DamageEnemyElement(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement1Damage(monster, orbMatches, orbAwakenings, combos, team);
        if (monster.getElement1().equals(Element.RED)) {
            if (enemy.getTargetElement().equals(Element.BLUE)) {
                damage = damage / 2;
            } else if (enemy.getTargetElement().equals(Element.GREEN)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.BLUE)) {
            if (enemy.getTargetElement().equals(Element.GREEN)) {
                damage = damage / 2;
            } else if (enemy.getTargetElement().equals(Element.RED)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.GREEN)) {
            if (enemy.getTargetElement().equals(Element.RED)) {
                damage = damage / 2;
            } else if (enemy.getTargetElement().equals(Element.BLUE)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.LIGHT)) {
            if (enemy.getTargetElement().equals(Element.DARK)) {
                damage = damage * 2;
            }
        } else if (monster.getElement1().equals(Element.DARK)) {
            if (enemy.getTargetElement().equals(Element.LIGHT)) {
                damage = damage * 2;
            }
        }
        return damage;
    }

    public static double monsterElement2DamageEnemyElement(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement2Damage(monster, orbMatches, orbAwakenings, combos, team);
        if (damage == 0) {
            return 0;
        }
        if (monster.getElement2().equals(Element.RED)) {
            if (enemy.getTargetElement().equals(Element.BLUE)) {
                damage = damage / 2;
            } else if (enemy.getTargetElement().equals(Element.GREEN)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.BLUE)) {
            if (enemy.getTargetElement().equals(Element.GREEN)) {
                damage = damage / 2;
            } else if (enemy.getTargetElement().equals(Element.RED)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.GREEN)) {
            if (enemy.getTargetElement().equals(Element.RED)) {
                damage = damage / 2;
            } else if (enemy.getTargetElement().equals(Element.BLUE)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.LIGHT)) {
            if (enemy.getTargetElement().equals(Element.DARK)) {
                damage = damage * 2;
            }
        } else if (monster.getElement2().equals(Element.DARK)) {
            if (enemy.getTargetElement().equals(Element.LIGHT)) {
                damage = damage * 2;
            }
        }
        return damage;
    }

    public static double monsterDamageEnemyDefense(double damage, Enemy enemy) {
        if (damage - enemy.getTargetDef() < 0) {
            return 1;
        }
        return damage - enemy.getTargetDef();
    }

    public static double monsterElement1DamageReduction(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement1DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage == 0) {
            return 0;
        }
        if (enemy.getReduction().isEmpty()) {
            return monsterDamageEnemyDefense(damage, enemy);
        } else if (enemy.getReduction().contains(monster.getElement1())) {
            return monsterDamageEnemyDefense(damage / 2, enemy);
        } else return monsterDamageEnemyDefense(damage, enemy);
    }

    public static double monsterElement2DamageReduction(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement2DamageEnemyElement(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage == 0) {
            return 0;
        }
        if (enemy.getReduction().isEmpty() || enemy.getReduction() == null) {
            return monsterDamageEnemyDefense(damage, enemy);
        } else if (enemy.getReduction().contains(monster.getElement2())) {
            return monsterDamageEnemyDefense(damage / 2, enemy);
        } else return monsterDamageEnemyDefense(damage, enemy);
    }

    public static double monsterElement1DamageAbsorb(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement1DamageReduction(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (enemy.getAbsorb().equals(monster.getElement1())) {
            return damage * -1;
        }
        return damage;
    }

    public static double monsterElement2DamageAbsorb(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement2DamageReduction(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (enemy.getAbsorb().equals(monster.getElement2())) {
            return damage * -1;
        } else return damage;
    }

    public static double monsterElement1DamageThreshold(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement1DamageAbsorb(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage >= enemy.getDamageThreshold()) {
            return ((damage - enemy.getDamageThreshold()) * -1);
        }
        return damage;
    }

    public static double monsterElement2DamageThreshold(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement2DamageAbsorb(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage >= enemy.getDamageThreshold()) {
            return ((damage - enemy.getDamageThreshold()) * -1);
        }
        return damage;
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

    public static double hpRecovered(int rcv, ArrayList<OrbMatch> orbMatches, int combos) {
        double totalOrbDamage = 0;
        for (int i = 0; i < orbMatches.size(); i++) {
            if (orbMatches.get(i).getElement().equals(Element.HEART)) {
                totalOrbDamage += orbMatch(rcv, orbMatches.get(i));
            }
        }
        return comboMultiplier(totalOrbDamage, combos);
    }

    public static double orbMatch(int rcv, OrbMatch orbMatches) {
        // Write in combos
        // Attack, Orb Awakenings, TPA from API, draw from Monster Database
        //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
        if (orbMatches.getOrbsLinked() < 3) {
            throw new IllegalArgumentException();
        }
        double heal = 0;
        if (orbMatches.getNumOrbPlus() == 0) {
            heal = rcv * (((orbMatches.getOrbsLinked() - 3) * .25) + 1);
        } else {
            heal = rcv * (((orbMatches.getOrbsLinked() - 3) * .25) + 1)
                    * ((orbMatches.getNumOrbPlus() * .06) + 1);
        }
        return Math.ceil(heal);
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

    public static int monsterHpCalc(Monster monster, Team team){
        double monsterHp = 0;
        monsterHp = monster.getTotalHp();
        Log.d("Damage Calc Util", "Leadskill is: " + team.getLeadSkill() + " hpData is: " + team.getLeadSkill().getHpData() + " multiplier is: " + team.getLeadSkill().hpMultiplier(monster, team));
        monsterHp = monsterHp * team.getLeadSkill().hpMultiplier(monster, team) * team.getHelperSkill().hpMultiplier(monster, team);
        return (int) Math.ceil(monsterHp);
    }

    public static double monsterRcvCalc(Monster monster, Team team){
        double monsterRcv = 0;
        monsterRcv = monster.getTotalRcv();
        Log.d("Damage Calc Util", "Leadskill is: " + team.getLeadSkill() + " RcvData is: " + team.getLeadSkill().getRcvData() + " multiplier is: " + team.getLeadSkill().rcvMultiplier(monster, team));
        monsterRcv = monsterRcv * team.getLeadSkill().rcvMultiplier(monster, team) * team.getHelperSkill().rcvMultiplier(monster, team);
        return monsterRcv;
    }
}
