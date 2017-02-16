package com.padassist.Util;

import com.padassist.Constants;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DragonLotus on 2/15/2017.
 */

public class ScoreMonsterUtil {

    private static double score = 0;
    private static ArrayList<Double> awakeningBaseScores = new ArrayList<>(Arrays.asList(
            5d,5d,5d,                   //HP, ATK, RCV
            5d,5d,5d,5d,5d,             //Resists
            5d,10d,10d,10d,10d,         //Auto-heal, bind, blind, jammer, poison resists
            10d,10d,10d,10d,10d,        //Orb enhance
            10d,10d,10d,                //Time Extend, Bind recovery, skill boost
            20d,20d,20d,20d,20d,        //Rows
            20d,10d,10d,10d,            //Prong, Skill bind resist, Heart orb +, Co-op Boost
            20d,20d,20d,20d,20d,        //Dragon, God, Devil, Machine, Attacker killers
            20d,20d,20d,20d,20d,        //Physical, Healer, Balanced, Awoken, Enhance killers
            20d,20d,20d,10d));           //Vendor, Evo killers, 7 Combo, Def break


    public static double scoreMonster(Team team, ArrayList<Integer> teamAwakeningsSansReplace, Monster monster) {

        initializeBaseScore(team, monster);

        return score;
    }

    private static void initializeBaseScore(Team team, Monster monster) {
        score = monster.getTotalWeighted() / 10;
    }
}
