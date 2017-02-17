package com.padassist.Util;

import android.util.Log;

import com.padassist.Data.Monster;
import com.padassist.Data.Team;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DragonLotus on 2/15/2017.
 */

public class ScoreMonsterUtil {

    private static double score;
    private static ArrayList<Integer> awakeningBaseScores;
    private static ArrayList<Double> awakeningWeights;


    public static double scoreMonster(Team team, ArrayList<Integer> awakeningCount, ArrayList<Double> elementDamage, Monster monster) {
        score = 0;
        awakeningBaseScores = new ArrayList<>(Arrays.asList(
            2, 2, 2,                        //HP, ATK, RCV
            2, 2, 2, 2, 2,                  //Resists
            2, 5, 10, 10, 10,              //Auto-heal, bind, blind, jammer, poison resists
            15, 15, 15, 15, 15,             //Orb enhance
            5, 5, 5,                     //Time Extend, Bind recovery, skill boost
            40, 40, 40, 40, 40,             //Rows
            20, 10, 5, 5,                 //Prong, Skill bind resist, Heart orb +, Co-op Boost
            10, 10, 10, 10, 10,             //Dragon, God, Devil, Machine, Attacker killers
            10, 10, 10, 10, 10,             //Physical, Healer, Balanced, Awoken, Enhance killers
            10, 10, 10, 5));               //Vendor, Evo killers, 7 Combo, Def break
        awakeningWeights = new ArrayList<>(Arrays.asList(
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
        initializeAwakeningWeights(team, awakeningCount, elementDamage, monster);
        initializeMonsterBaseScore(monster, elementDamage);
        calculateMonsterAwakeningScore(awakeningCount, monster);
        return score;
    }

    private static void initializeAwakeningWeights(Team team, ArrayList<Integer> awakeningCount, ArrayList<Double> elementDamage, Monster monster) {
        for (int i = 0; i < elementDamage.size(); i++) {
            awakeningWeights.set(13 + i, elementDamage.get(i) * awakeningWeights.get(13 + i) * nextEffectiveness(awakeningCount.get(13 + i), .05));
            awakeningWeights.set(21 + i, elementDamage.get(i) * awakeningWeights.get(21 + i) * nextEffectiveness(awakeningCount.get(21 + i), .1));
            if (i == monster.getElement1Int()) {
                awakeningWeights.set(26, elementDamage.get(i) * awakeningWeights.get(26));
            }
            if (i == monster.getElement2Int()) {
                if (monster.getElement1Int() == monster.getElement2Int()) {
                    awakeningWeights.set(26, (elementDamage.get(i) / 10) + awakeningWeights.get(26));
                } else {
                    awakeningWeights.set(26, (elementDamage.get(i) / 3) + awakeningWeights.get(26));
                }
            }
        }
        awakeningWeights.set(28, awakeningWeights.get(28) * nextEffectiveness(awakeningCount.get(28), .05));
        if(awakeningCount.get(27) >= 5){
            awakeningWeights.set(27, 0d);
        } else {
            switch (5 - awakeningCount.get(27)){
                case 0:
                    awakeningWeights.set(27, 0d);
                    break;
                case 1:
                    awakeningWeights.set(27, awakeningWeights.get(27) * 3);
                    break;
                case 2:
                    awakeningWeights.set(27, awakeningWeights.get(27) * 3);
                    break;
                case 3:
                    awakeningWeights.set(27, awakeningWeights.get(27) * 3);
                    break;
                case 4:
                    awakeningWeights.set(27, awakeningWeights.get(27) * 3);
                    break;
                case 5:
                    awakeningWeights.set(27, awakeningWeights.get(27) * 3);
                    break;
            }
        }
        if(awakeningCount.get(10) >= 5){
            awakeningWeights.set(10, 0d);
        }
        if(awakeningCount.get(11) >= 5){
            awakeningWeights.set(11, 0d);
        }
        if(awakeningCount.get(12) >= 5){
            awakeningWeights.set(12, 0d);
        }
        Log.d("ScoreMonsterUtil","awakeningWeights after initialized are: " + awakeningWeights);
    }

    private static void setRowVsProngWeights(ArrayList<Integer> awakeningCount, Monster monster){
        int numOfProngs = awakeningCount.get(26);
        int numOfRows;
        for(int i = 0; i < 5; i++){
            numOfRows = awakeningCount.get(21 + i);
            awakeningWeights.set(21 + i, awakeningWeights.get(21 + i) * ((double)numOfRows/(numOfProngs + numOfRows)));
            if (i == monster.getElement1Int()) {
                awakeningWeights.set(26, awakeningWeights.get(26) * ((double)numOfProngs/(numOfProngs + numOfRows)));
            }
            if (i == monster.getElement2Int()) {
                if (monster.getElement1Int() == monster.getElement2Int()) {
                    awakeningWeights.set(26, awakeningWeights.get(26) + awakeningWeights.get(26) * ((double)numOfProngs/(numOfProngs + numOfRows)) / 10);
                } else {
                    awakeningWeights.set(26, awakeningWeights.get(26) + awakeningWeights.get(26) * ((double)numOfProngs/(numOfProngs + numOfRows)) / 3);
                }
            }
        }
        Log.d("ScoreMonsterUtil","awakeningWeights rowVsProng are: " + awakeningWeights);
    }

    private static void initializeMonsterBaseScore(Monster monster, ArrayList<Double> elementDamage) {
        score = monster.getTotalHp() / 10 + (monster.getTotalAtk() / 5 * elementDamage.get(monster.getElement1Int())) + monster.getTotalRcv() / 3;
        if(monster.getElement2Int() > -1){
                if (monster.getElement1Int() == monster.getElement2Int()) {
                    score += monster.getTotalAtk() / 50 * elementDamage.get(monster.getElement2Int());
                } else {
                    score += monster.getTotalAtk() / 15 * elementDamage.get(monster.getElement2Int());
                }
        }
        score /= 20;
    }

    private static void calculateMonsterAwakeningScore(ArrayList<Integer> awakeningCount, Monster monster){
        ArrayList<Integer> tempAwakeningCount = new ArrayList<>();
        tempAwakeningCount.addAll(awakeningCount);
        double awakeningScore = 0;
        for(int i = 0; i < monster.getCurrentAwakenings(); i++){
            tempAwakeningCount.set(monster.getAwokenSkills(i) - 1, tempAwakeningCount.get(monster.getAwokenSkills(i) - 1) + 1);
        }
        setRowVsProngWeights(tempAwakeningCount, monster);
        tempAwakeningCount.clear();
        tempAwakeningCount.addAll(awakeningCount);
        Log.d("ScoreMonsterUtil","tempAwakeningCount before is: " + tempAwakeningCount);
        int awakeningIndex;
        int bindResistCounter = 0;
        for (int i = 0; i < monster.getCurrentAwakenings(); i++){
            if(monster.getAwokenSkills(i) == 10){
                bindResistCounter++;
            }
            awakeningIndex = monster.getAwokenSkills(i) - 1;
            awakeningScore += awakeningBaseScores.get(awakeningIndex) * awakeningWeights.get(awakeningIndex);
            tempAwakeningCount.set(awakeningIndex, tempAwakeningCount.get(awakeningIndex) + 1);
            if(awakeningIndex == 27){
                switch (5 - tempAwakeningCount.get(27)){
                case 0:
                    awakeningWeights.set(27, 0d);
                    break;
                case 1:
                    awakeningWeights.set(27, 3d);
                    break;
                case 2:
                    awakeningWeights.set(27, 3d);
                    break;
                case 3:
                    awakeningWeights.set(27, 3d);
                    break;
                case 4:
                    awakeningWeights.set(27, 3d);
                    break;
                case 5:
                    awakeningWeights.set(27, 3d);
                    break;
            }
            } else if((awakeningIndex == 10 || awakeningIndex == 11 || awakeningIndex == 12) && tempAwakeningCount.get(awakeningIndex) >= 5){
                awakeningWeights.set(awakeningIndex, 0d);
            }
            Log.d("ScoreMonsterUtil","tempAwakeningCount is: " + tempAwakeningCount);
            if((awakeningIndex >= 13 && awakeningIndex <= 17) || awakeningIndex == 28){
                awakeningWeights.set(awakeningIndex, awakeningWeights.get(awakeningIndex) * nextEffectiveness(awakeningCount.get(awakeningIndex),.05));
            } else if(awakeningIndex >= 21 && awakeningIndex <= 25) {
                awakeningWeights.set(awakeningIndex, awakeningWeights.get(awakeningIndex) * nextEffectiveness(awakeningCount.get(awakeningIndex),.1));
            }
        }
        score += awakeningScore;
        if(bindResistCounter == 2){
            score *= 1.1;
        }
    }

    private static double nextEffectiveness(int awakeningCount, double percentDamageIncrease){
        double percentDamage = 1 + ((awakeningCount) * percentDamageIncrease);

        return 1 / percentDamage;
    }
}
