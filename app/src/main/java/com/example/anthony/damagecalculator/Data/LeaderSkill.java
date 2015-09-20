package com.example.anthony.damagecalculator.Data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 9/18/2015.
 */
@Table(name = "LeaderSkill")
public class LeaderSkill extends Model {

    @Column(name = "hpData")
    private ArrayList<Double> hpData;
    @Column(name = "atkData")
    private ArrayList<Double> atkData;
    @Column(name = "rcvData")
    private ArrayList<Double> rcvData;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "hpType")
    private ArrayList<Integer> hpType;
    @Column(name = "hpElement")
    private ArrayList<Integer> hpElement;
    @Column(name = "atkType")
    private ArrayList<Integer> atkType;
    @Column(name = "atkElement")
    private ArrayList<Integer> atkElement;
    @Column(name = "rcvType")
    private ArrayList<Integer> rcvType;
    @Column(name = "rcvElement")
    private ArrayList<Integer> rcvElement;
    @Column(name = "comboMin")
    private int comboMin;
    @Column(name = "comboMax")
    private int comboMax;
    //Can use for orbs linked, indian 2 skills, etc. need to think about it.
    @Column(name = "matchElements")
    private ArrayList<Element> matchElements;
    @Column(name = "matchMonsters")
    private ArrayList<Monster> matchMonsters;
    @Column(name = "hpSkillType")
    private LeaderSkillType hpSkillType;
    @Column(name = "atkSkillType")
    private LeaderSkillType atkSkillType;
    @Column(name = "rcvSkillType")
    private LeaderSkillType rcvSkillType;
    private double hpMultiplier;
    private double atkMultiplier;
    private double rcvMultiplier;

    public LeaderSkill() {
        hpMultiplier = 1;
        atkMultiplier = 1;
        rcvMultiplier = 1;
    }

    private double hpMultiplier(Monster monster, Team team) {
        switch (hpSkillType) {
            case FLAT:
                flat(monster, 1);
                break;
            case MONSTER_CONDITIONAL:
                monsterConditional(monster, team, 1);
                break;
        }
        return hpMultiplier;
    }

    private double atkMultiplier(Monster monster, Team team) {
        switch (atkSkillType) {
            case FLAT:
                flat(monster, 2);
                break;
            case COMBO:
                combo(monster, team);
                break;
            case MATCH_ELEMENT:
                matchElement(monster, team);
                break;
            case MONSTER_CONDITIONAL:
                monsterConditional(monster, team, 2);
                break;
            case FLAT_MONSTER_CONDITIONAL:
                flatMonsterConditional(monster, team, 2);
                break;
        }
        return atkMultiplier;
    }

    private double getRcvMultiplier(Monster monster, Team team) {
        switch (rcvSkillType) {
            case FLAT:
                flat(monster, 3);
                break;
            case MONSTER_CONDITIONAL:
                monsterConditional(monster, team, 3);
                break;
        }
        return rcvMultiplier;
    }

    private void flat(Monster monster, int stat) {
        switch (stat) {
            case 1:
                if (hpType.size() != 0) {
                    for (int i = 0; i < hpType.size(); i++) {
                        if (monster.getType1() == hpType.get(i) || monster.getType2() == hpType.get(i)) {
                            hpMultiplier = hpData.get(0);
                        }
                    }
                }
                if (hpElement.size() != 0) {
                    for (int i = 0; i < hpElement.size(); i++) {
                        if (monster.getElement1Int() == hpElement.get(i) || monster.getElement2Int() == hpElement.get(i)) {
                            hpMultiplier = hpData.get(0);
                        }
                    }
                }
                break;
            case 2:
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                            atkMultiplier = atkData.get(0);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkMultiplier = atkData.get(0);
                        }
                    }
                }
                break;
            case 3:
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i)) {
                            rcvMultiplier = rcvData.get(0);
                        }
                    }
                }
                if (rcvElement.size() != 0) {
                    for (int i = 0; i < rcvElement.size(); i++) {
                        if (monster.getElement1Int() == rcvElement.get(i) || monster.getElement2Int() == rcvElement.get(i)) {
                            rcvMultiplier = rcvData.get(0);
                        }
                    }
                }
                break;
        }

    }

    private void combo(Monster monster, Team team) {
        int comboDiff = comboMax - comboMin;
//        if (team.getOrbMatches().size() >= comboMax){
//            atkMultiplier = atkData.get(comboDiff);
//        }else if(team.getOrbMatches().size() > comboMin){
//            for(int i = 0; i <= comboDiff; i++){
//                if(team.getOrbMatches().size() >= (comboMin + i)){
//                    atkMultiplier = atkData.get(i);
//                }
//            }
//        }
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkMultiplier = atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() > comboMin) {
            atkMultiplier = atkData.get(counter);
        }
    }

    private void matchElement(Monster monster, Team team) {
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        for (int i = 0; i < matchElements.size(); i++){
            if(team.getOrbMatches().contains(matchElements.get(i))){
                counter++;
            }
        }
        if (counter >= comboDiff){
            atkMultiplier = atkData.get(comboDiff);
        } else if (counter >= comboMin){
            atkMultiplier = atkData.get(counter);
        }
    }

    private void monsterConditional(Monster monster, Team team, int stat){
        int counter = 0;
        for (int i = 0; i < matchMonsters.size(); i++){
            if (team.getBaseMonsterId().contains(matchMonsters.get(i).getBaseMonsterId())){
                counter++;
            }
        }
        if (counter == matchMonsters.size()){
            if(stat == 1){
                hpMultiplier = hpData.get(0);
            } else if (stat == 2) {
                atkMultiplier = atkData.get(0);
            } else if (stat == 3) {
                rcvMultiplier = rcvData.get(0);
            }
        }
    }

    private void flatMonsterConditional (Monster monster, Team team, int stat){
        //Attack Data will look like {flat multiplier, monster conditional multiplier}
        //See Awoken Jord and Zhou Yu
        int counter = 0;
        for (int i = 0; i < matchMonsters.size(); i++){
            if (team.getBaseMonsterId().contains(matchMonsters.get(i).getBaseMonsterId())){
                counter++;
            }
        }
        if (counter == matchMonsters.size()){
            if(stat == 1){
                hpMultiplier = hpData.get(0);
            } else if (stat == 2) {
                if (atkType.size() != 0){
                    for (int i = 0; i < atkType.size(); i++){
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)){
                            atkMultiplier = atkData.get(0);
                        }
                    }
                }
                if (atkElement.size() != 0){
                    for (int i = 0; i < atkElement.size(); i++){
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getType2() == atkElement.get(i)){
                            atkMultiplier = atkData.get(0);
                        }
                    }
                }
                atkMultiplier *= atkData.get(1);
            } else if (stat == 3) {
                rcvMultiplier = rcvData.get(0);
            }
        }

    }

}
