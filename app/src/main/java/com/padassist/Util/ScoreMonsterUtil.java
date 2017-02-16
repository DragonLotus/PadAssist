package com.padassist.Util;

import com.padassist.Data.Monster;
import com.padassist.Data.Team;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DragonLotus on 2/15/2017.
 */

public class ScoreMonsterUtil {

    private static double score = 0;
    private static ArrayList<Integer> awakeningBaseScores = new ArrayList<>(Arrays.asList(
            5, 5, 5,                        //HP, ATK, RCV
            5, 5, 5, 5, 5,                  //Resists
            5, 10, 10, 10, 10,              //Auto-heal, bind, blind, jammer, poison resists
            10, 10, 10, 10, 10,             //Orb enhance
            10, 10, 10,                     //Time Extend, Bind recovery, skill boost
            20, 20, 20, 20, 20,             //Rows
            20, 10, 10, 10,                 //Prong, Skill bind resist, Heart orb +, Co-op Boost
            20, 20, 20, 20, 20,             //Dragon, God, Devil, Machine, Attacker killers
            20, 20, 20, 20, 20,             //Physical, Healer, Balanced, Awoken, Enhance killers
            20, 20, 20, 10));               //Vendor, Evo killers, 7 Combo, Def break
    private static ArrayList<Double> awakeningWeights = new ArrayList<>(Arrays.asList(
            1d, 1d, 1d,                   //HP, ATK, RCV
            1d, 1d, 1d, 1d, 1d,             //Resists
            1d, 1d, 1d, 1d, 1d,         //Auto-heal, bind, blind, jammer, poison resists
            1d, 1d, 1d, 1d, 1d,        //Orb enhance
            1d, 1d, 1d,                //Time Extend, Bind recovery, skill boost
            1d, 1d, 1d, 1d, 1d,        //Rows
            1d, 1d, 1d, 1d,            //Prong, Skill bind resist, Heart orb +, Co-op Boost
            1d, 1d, 1d, 1d, 1d,        //Dragon, God, Devil, Machine, Attacker killers
            1d, 1d, 1d, 1d, 1d,        //Physical, Healer, Balanced, Awoken, Enhance killers
            1d, 1d, 1d, 1d));           //Vendor, Evo killers, 7 Combo, Def break


    public static double scoreMonster(Team team, ArrayList<Integer> awakeningCount, ArrayList<Double> elementDamage, Monster monster) {
        initializeAwakeningWeights(team, awakeningCount, elementDamage, monster);
        initializeMonsterBaseScore(monster);
        calculateMonsterAwakeningScore(awakeningCount, monster);
        return score;
    }

    private static void initializeAwakeningWeights(Team team, ArrayList<Integer> awakeningCount, ArrayList<Double> elementDamage, Monster monster) {
        for (int i = 0; i < elementDamage.size(); i++) {
            awakeningWeights.set(13 + i, elementDamage.get(i) * awakeningWeights.get(13 + i) * nextEffectiveness(awakeningCount.get(13 + i), .05));
            awakeningWeights.set(21 + i, elementDamage.get(i) * awakeningWeights.get(21 + i) * nextEffectiveness(awakeningCount.get(21 + i), .1));
            if (elementDamage.get(i) == monster.getElement1Int()) {
                awakeningWeights.set(26, elementDamage.get(i) * awakeningWeights.get(26));
            }
            if (elementDamage.get(i) == monster.getElement2Int()) {
                if (monster.getElement1Int() == monster.getElement2Int()) {
                    awakeningWeights.set(26, (elementDamage.get(i) / 10) + awakeningWeights.get(26));
                } else {
                    awakeningWeights.set(26, (elementDamage.get(i) / 3) + awakeningWeights.get(26));
                }
            }

        }
    }

    private static void setRowVsProngWeights(ArrayList<Integer> awakeningCount, Monster monster){
        int numOfProngs = awakeningCount.get(26);
        int numOfRows;
        for(int i = 0; i < 5; i++){
            numOfRows = awakeningCount.get(21 + i);
            awakeningWeights.set(21 + i, awakeningWeights.get(21 + i) * (numOfRows/(numOfProngs + numOfRows)));
            if (i == monster.getElement1Int()) {
                awakeningWeights.set(26, awakeningWeights.get(26) * (numOfRows/(numOfProngs + numOfRows)));
            }
            if (i == monster.getElement2Int()) {
                if (monster.getElement1Int() == monster.getElement2Int()) {
                    awakeningWeights.set(26, awakeningWeights.get(26) + awakeningWeights.get(26) * (numOfProngs/(numOfProngs + numOfRows)) / 10);
                } else {
                    awakeningWeights.set(26, awakeningWeights.get(26) + awakeningWeights.get(26) * (numOfProngs/(numOfProngs + numOfRows)) / 3);
                }
            }
        }
    }

    private static void initializeMonsterBaseScore(Monster monster) {
        score = monster.getTotalWeighted() / 10;
    }

    private static void calculateMonsterAwakeningScore(ArrayList<Integer> awakeningCount, Monster monster){
        ArrayList<Integer> tempAwakeningCount = new ArrayList<>();
        tempAwakeningCount.addAll(awakeningCount);
        double awakeningScore = 0;
        for(int i = 0; i < monster.getCurrentAwakenings(); i++){
            tempAwakeningCount.set(monster.getAwokenSkills(i) - 1, tempAwakeningCount.get(monster.getAwokenSkills(i) - 1) + 1);
        }
        setRowVsProngWeights(tempAwakeningCount, monster);
        for (int i = 0; i < monster.getCurrentAwakenings(); i++){
            awakeningScore += awakeningBaseScores.get(monster.getAwokenSkills(i) - 1) * awakeningWeights.get(monster.getAwokenSkills(i) - 1);
//            if(monster.getAwokenSkills(i) > 13 && monster.getAwokenSkills(i) < 19){
//                awakeningWeights.set(i - 1, awakeningWeights.get(i - 1) * nextEffectiveness())
//            }
        }
        score += awakeningScore;

    }

    private static double nextEffectiveness(int awakeningCount, double percentDamageIncrease){
        double percentDamage = 1 + ((awakeningCount + 1) * percentDamageIncrease);
        double effectiveness = percentDamageIncrease / percentDamage * 10;

        return effectiveness;
    }
}
