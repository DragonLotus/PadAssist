package com.padassist.Util;

import android.util.Log;

import com.padassist.Data.Element;
import com.padassist.Data.Enemy;
import com.padassist.Data.LeaderSkillType;
import com.padassist.Data.Monster;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.Team;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Thomas on 7/11/2015.
 */
public class DamageCalculationUtil {

    private static Realm realm = Realm.getDefaultInstance();

    public static double monsterElement1Damage(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Team team) {
        double totalOrbDamage = 0;
        for (int i = 0; i < orbMatches.size(); i++) {
            if (orbMatches.get(i).getElement().equals(monster.getElement1())) {
                totalOrbDamage += orbMatch(monster.getTotalAtk(), orbMatches.get(i), orbAwakenings, monster.getTPA());
            }
        }
        Log.d("Damage Util Log", "Monster: " + monster.getName() + " Combo Multiplier Damage: " + comboMultiplier(totalOrbDamage, combos) + " Lead Skill Multiplier: " + team.getLeadSkill().atkElement1Multiplier(monster, team, combos) * team.getHelperSkill().atkElement1Multiplier(monster, team, combos));
        return Math.ceil(leadOtherMultiplier1(comboMultiplier(totalOrbDamage, combos), monster, team, combos));
    }

    public static double monsterElement2Damage(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Team team) {
        double totalOrbDamage = 0;
        if (monster.getElement1().equals(monster.getElement2())) {
            for (int i = 0; i < orbMatches.size(); i++) {
                if (orbMatches.get(i).getElement().equals(monster.getElement2())) {
                    totalOrbDamage += orbMatch((int) Math.ceil(monster.getTotalAtk() * .1), orbMatches.get(i), orbAwakenings, monster.getTPA());
                }
            }
            Log.d("Damage Util Log", "Monster: " + monster.getName() + " Combo Multiplier Damage: " + comboMultiplier(totalOrbDamage, combos) + " Lead Skill Multiplier: " + team.getLeadSkill().atkElement2Multiplier(monster, team, combos) * team.getHelperSkill().atkElement2Multiplier(monster, team, combos));
            return Math.ceil(leadOtherMultiplier2(comboMultiplier(totalOrbDamage, combos), monster, team, combos));
        }
        for (int i = 0; i < orbMatches.size(); i++) {
            if (orbMatches.get(i).getElement().equals(monster.getElement2())) {
                totalOrbDamage += orbMatch((int) Math.ceil(monster.getTotalAtk() / 3), orbMatches.get(i), orbAwakenings, monster.getTPA());
            }
        }
        Log.d("Damage Util Log", "Monster: " + monster.getName() + " Combo Multiplier Damage: " + comboMultiplier(totalOrbDamage, combos) + " Lead Skill Multiplier: " + team.getLeadSkill().atkElement2Multiplier(monster, team, combos) * team.getHelperSkill().atkElement2Multiplier(monster, team, combos));
        return Math.ceil(leadOtherMultiplier2(comboMultiplier(totalOrbDamage, combos), monster, team, combos));
    }

    public static double comboMultiplier(double damage, int combos) {
        return Math.ceil(damage * ((combos - 1) * .25 + 1.0));
    }

    public static double leadOtherMultiplier1(double damage, Monster monster, Team team, int totalCombos) {
        //No active skill multiplier
        double returnDamage = damage;
        int counter = 0;
        for (int i = 0; i < realm.where(OrbMatch.class).findAll().size(); i++) {
            if (realm.where(OrbMatch.class).findAll().get(i).getElement().equals(monster.getElement1())) {
                if (realm.where(OrbMatch.class).findAll().get(i).isRow()) {
                    counter++;
                }
            }
        }
        returnDamage = returnDamage * team.getLeadSkill().atkElement1Multiplier(monster, team, totalCombos) * team.getHelperSkill().atkElement1Multiplier(monster, team, totalCombos) * (team.getRowAwakenings(monster.getElement1()) * 0.1 * counter + 1);

        if (Singleton.getInstance().isEnableMultiplier()) {
            Boolean affected = false;
            if (Singleton.getInstance().getExtraElementMultiplier().contains(monster.getElement1())) {
                returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                affected = true;
            }
            if(Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType1()) || Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType2()) ||Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType3()) && !affected){
                if(!affected){
                    returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                }
            }
        }
        return returnDamage;
    }

    public static double leadOtherMultiplier2(double damage, Monster monster, Team team, int totalCombos) {
        //No active skill multiplier
        double returnDamage = damage;
        int counter = 0;
        for (int i = 0; i < realm.where(OrbMatch.class).findAll().size(); i++) {
            if (realm.where(OrbMatch.class).findAll().get(i).getElement().equals(monster.getElement2())) {
                if (realm.where(OrbMatch.class).findAll().get(i).isRow()) {
                    counter++;
                }
            }
        }
        returnDamage = returnDamage * team.getLeadSkill().atkElement2Multiplier(monster, team, totalCombos) * team.getHelperSkill().atkElement2Multiplier(monster, team, totalCombos) * (team.getRowAwakenings(monster.getElement2()) * 0.1 * counter + 1);

        if (Singleton.getInstance().isEnableMultiplier()) {
            Boolean affected = false;
            if (Singleton.getInstance().getExtraElementMultiplier().contains(monster.getElement2())) {
                returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                affected = true;
            }
            if(Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType1()) || Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType2()) ||Singleton.getInstance().getExtraTypeMultiplier().contains(monster.getType3())){
                if(!affected){
                    returnDamage *= Singleton.getInstance().getExtraDamageMultiplier();
                }
            }
        }
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
        if(monster.getKillerAwakenings().size() != 0){
            for(int i = 0; i < monster.getKillerAwakenings().size(); i++){
                switch(monster.getKillerAwakenings().get(i).getValue()){
                    case 31:
                        if(enemy.getTypes().contains(4)){
                            damage *= 3;
                        }
                        break;
                    case 32:
                        if(enemy.getTypes().contains(5)){
                            damage *= 3;
                        }
                        break;
                    case 33:
                        if(enemy.getTypes().contains(7)){
                            damage *= 3;
                        }
                        break;
                    case 34:
                        if(enemy.getTypes().contains(8)){
                            damage *= 3;
                        }
                        break;
                    case 35:
                        if(enemy.getTypes().contains(6)){
                            damage *= 3;
                        }
                        break;
                    case 36:
                        if(enemy.getTypes().contains(2)){
                            damage *= 3;
                        }
                        break;
                    case 37:
                        if(enemy.getTypes().contains(3)){
                            damage *= 3;
                        }
                        break;
                    case 38:
                        if(enemy.getTypes().contains(1)){
                            damage *= 3;
                        }
                        break;
                    case 39:
                        if(enemy.getTypes().contains(12)){
                            damage *= 3;
                        }
                        break;
                    case 40:
                        if(enemy.getTypes().contains(14)){
                            damage *= 3;
                        }
                        break;
                    case 41:
                        if(enemy.getTypes().contains(15)){
                            damage *= 3;
                        }
                        break;
                    case 42:
                        if(enemy.getTypes().contains(0)){
                            damage *= 3;
                        }
                        break;
                }
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
        if(monster.getKillerAwakenings().size() != 0){
            Log.d("DamageCalcTag", "Monster has killer awakenings.");
            for(int i = 0; i < monster.getKillerAwakenings().size(); i++){
                switch(monster.getKillerAwakenings().get(i).getValue()){
                    case 31:
                        if(enemy.getTypes().contains(4)){
                            damage *= 3;
                        }
                        break;
                    case 32:
                        if(enemy.getTypes().contains(5)){
                            damage *= 3;
                        }
                        break;
                    case 33:
                        if(enemy.getTypes().contains(7)){
                            damage *= 3;
                        }
                        break;
                    case 34:
                        if(enemy.getTypes().contains(8)){
                            damage *= 3;
                        }
                        break;
                    case 35:
                        if(enemy.getTypes().contains(6)){
                            damage *= 3;
                        }
                        break;
                    case 36:
                        if(enemy.getTypes().contains(2)){
                            damage *= 3;
                        }
                        break;
                    case 37:
                        if(enemy.getTypes().contains(3)){
                            damage *= 3;
                        }
                        break;
                    case 38:
                        if(enemy.getTypes().contains(1)){
                            damage *= 3;
                        }
                        break;
                    case 39:
                        if(enemy.getTypes().contains(12)){
                            damage *= 3;
                        }
                        break;
                    case 40:
                        if(enemy.getTypes().contains(14)){
                            damage *= 3;
                        }
                        break;
                    case 41:
                        if(enemy.getTypes().contains(15)){
                            damage *= 3;
                        }
                        break;
                    case 42:
                        if(enemy.getTypes().contains(0)){
                            damage *= 3;
                        }
                        break;
                }
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
            return monsterDamageEnemyDefense(damage * (100 - enemy.getReductionValue()) / 100, enemy);
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
            return monsterDamageEnemyDefense(damage * (100 - enemy.getReductionValue()) / 100, enemy);
        } else return monsterDamageEnemyDefense(damage, enemy);
    }

    public static double monsterElement1DamageAbsorb(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement1DamageReduction(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (enemy.getAbsorb().contains(monster.getElement1())) {
            return damage * -1;
        }
        return damage;
    }

    public static double monsterElement2DamageAbsorb(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement2DamageReduction(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (enemy.getAbsorb().contains(monster.getElement2())) {
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

    public static double monsterElement1DamageImmunity(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement1DamageAbsorb(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage >= enemy.getDamageImmunity()) {
            return 0;
        }
        return damage;
    }

    public static double monsterElement2DamageImmunity(Monster monster, ArrayList<OrbMatch> orbMatches, int orbAwakenings, int combos, Enemy enemy, Team team) {
        double damage = monsterElement2DamageAbsorb(monster, orbMatches, orbAwakenings, combos, enemy, team);
        if (damage >= enemy.getDamageImmunity()) {
            return 0;
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

    public static double hpRecovered(Team team, int combos) {
        double totalRcv = 0;
        ArrayList<OrbMatch> heartOrbMatches = new ArrayList<>();
        ArrayList<OrbMatch> poisonOrbMatches = new ArrayList<>();
        for (int i = 0; i < realm.where(OrbMatch.class).findAll().size(); i++) {
            if (realm.where(OrbMatch.class).findAll().get(i).getElement().equals(Element.HEART)) {
               // totalOrbDamage += orbMatch(rcv, realm.where(OrbMatch.class).findAll().get(i));
                heartOrbMatches.add(realm.where(OrbMatch.class).findAll().get(i));
            } else if (realm.where(OrbMatch.class).findAll().get(i).getElement().equals(Element.POISON) || realm.where(OrbMatch.class).findAll().get(i).getElement().equals(Element.MORTAL_POISON)){
                poisonOrbMatches.add(realm.where(OrbMatch.class).findAll().get(i));
            }
        }
        for(int i = 0; i < team.getMonsters().size(); i++){
            double totalOrbDamage = 0;
            double rcv = team.getMonsters().get(i).getTotalRcv() * team.getLeadSkill().rcvMultiplier(team.getMonsters().get(i), team) * team.getHelperSkill().rcvMultiplier(team.getMonsters().get(i), team);
            for(int j = 0; j < heartOrbMatches.size(); j++){
                totalOrbDamage += orbMatch((int)Math.floor(rcv + 0.5d), heartOrbMatches.get(j));
            }
            totalRcv += comboMultiplier(totalOrbDamage, combos);
        }
        return totalRcv - poisonDamage(team, poisonOrbMatches);
    }

    public static double orbMatch(int rcv, OrbMatch orbMatch) {
        // Write in combos
        // Attack, Orb Awakenings, TPA from API, draw from Monster Database
        //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
        if (orbMatch.getOrbsLinked() < 3) {
            throw new IllegalArgumentException();
        }
        double heal = 0;
        if (orbMatch.getNumOrbPlus() == 0) {
            heal = rcv * (((orbMatch.getOrbsLinked() - 3) * .25) + 1);
        } else {
            heal = rcv * (((orbMatch.getOrbsLinked() - 3) * .25) + 1)
                    * ((orbMatch.getNumOrbPlus() * .06) + 1);
        }
        return Math.ceil(heal);
    }

    public static double poisonDamage(Team team, ArrayList<OrbMatch> poisonOrbMatches){
        if(poisonOrbMatches.size() == 0){
            return 0;
        }
        double damage = 0;
        double teamHealth = team.getTeamHealth() * team.getTeamHp()/100;
        for(int i = 0; i < poisonOrbMatches.size(); i++){
            if(poisonOrbMatches.get(i).getElement().equals(Element.POISON)){
                damage += ((poisonOrbMatches.get(i).getOrbsLinked() - 3)*.05 + .20)*teamHealth;
            } else if(poisonOrbMatches.get(i).getElement().equals(Element.MORTAL_POISON)){
                damage += ((poisonOrbMatches.get(i).getOrbsLinked() - 3)*.125 + .50)*teamHealth;
            }
        }
        return damage;
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
        Log.d("Damage Calc Util", "monster 0 is: " + team.getMonsters().get(0) + team.getMonsters().get(0).getName());
        Log.d("Damage Calc Util", "Leadskill is: " + team.getLeadSkill() + " hpData is: " + team.getLeadSkill().getHpData() + " multiplier is: " + team.getLeadSkill().hpMultiplier(monster, team));
        monsterHp = monsterHp * team.getLeadSkill().hpMultiplier(monster, team) * team.getHelperSkill().hpMultiplier(monster, team);
        return (int) Math.ceil(monsterHp);
    }

    public static double monsterRcvCalc(Monster monster, Team team){
        double monsterRcv;
        monsterRcv = monster.getTotalRcv();
        Log.d("DamageCalcUtil", "LeadSkill is: " + team.getLeadSkill().getName() + " rcvSkillType is: " + team.getLeadSkill().getRcvSkillType());
        if(team.getLeadSkill().getRcvSkillType() != null){
            if(team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.FLAT) || team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.MONSTER_CONDITIONAL) || team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.HP_FLAT) || team.getLeadSkill().getRcvSkillType().equals(LeaderSkillType.CO_OP)){
                monsterRcv *= team.getLeadSkill().rcvMultiplier(monster, team);
            }
        }
        if(team.getHelperSkill().getRcvSkillType() != null){
            if(team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.FLAT) || team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.MONSTER_CONDITIONAL) || team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.HP_FLAT) || team.getHelperSkill().getRcvSkillType().equals(LeaderSkillType.CO_OP)){
                monsterRcv *= team.getHelperSkill().rcvMultiplier(monster, team);
            }
        }
        Log.d("Damage Calc Util", "Leadskill is: " + team.getLeadSkill() + " RcvData is: " + team.getLeadSkill().getRcvData() + " multiplier is: " + team.getLeadSkill().rcvMultiplier(monster, team));
       // monsterRcv = monsterRcv * team.getLeadSkill().rcvMultiplier(monster, team) * team.getHelperSkill().rcvMultiplier(monster, team);
        return monsterRcv;
    }
}
