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
    @Column(name = "comboMin2")
    private int comboMin2;
    @Column(name = "comboMax2")
    private int comboMax2;
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
            case ORB_LINKED:
                orbLink(monster, team);
                break;
            case MATCH_ELEMENT_FLAT:
                matchElementFlat(monster, team);
                break;
            case COMBO_MATCH_ELEMENT:
                comboMatchElement(monster, team);
                break;
            case MATCH_ELEMENT_ACTIVE:
                matchElementActive(monster, team, 2);
                break;
            case FLAT_ACTIVE:
                flatActive(monster, team, 2);
                break;
            case COMBO_ACTIVE:
                comboActive(monster, team, 2);
                break;
            case COMBO_FLAT:
                comboFlat(monster, team);
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
            case MATCH_ELEMENT_ACTIVE:
                matchElementActive(monster, team, 3);
                break;
            case FLAT_ACTIVE:
                flatActive(monster, team, 3);
                break;
            case COMBO_ACTIVE:
                comboActive(monster, team, 2);
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
        //Bastet, Anubis
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
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkMultiplier = atkData.get(counter);
        }
    }

    private void matchElement(Monster monster, Team team) {
        //Kirin, Horus, Ra
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
        //Three kingdoms farmables. Zhao Yun.
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

    private void orbPlus (Monster monster, Team team){
        //Akechi Mitsuhide
        //Need to rethink how to do just secondary or just primary attributes.
        //Incomplete.
        for(int i = 0; i < team.getOrbMatches().size(); i++){
            if(team.getOrbMatches().get(i).getNumOrbPlus() >= 1){
                if(monster.getElement1().equals(team.getOrbMatches().get(i).getElement())){
                    atkMultiplier = atkData.get(0);
                }
            }
        }
    }

    private void orbLink (Monster monster, Team team){
        //Heroes
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++){
            for (int j = 0; j < matchElements.size(); i++){
                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))){
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()){
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter >= comboDiff){
            atkMultiplier = atkData.get(comboDiff);
        }else if (counter >= comboMin){
            atkMultiplier = atkData.get(counter);
        }
    }

    private void matchElementFlat(Monster monster, Team team) {
        //Sonia Gran.
        // atkdata is {match element multipliers, flat multiplier is last}
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                    atkMultiplier = atkData.get(atkData.size() - 1);
                }
            }
        }
        if (atkElement.size() != 0) {
            for (int i = 0; i < atkElement.size(); i++) {
                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                    atkMultiplier = atkData.get(atkData.size() - 1);
                }
            }
        }
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        for (int i = 0; i < matchElements.size(); i++){
            if(team.getOrbMatches().contains(matchElements.get(i))){
                counter++;
            }
        }
        if (counter >= comboDiff){
            atkMultiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin){
            atkMultiplier *= atkData.get(counter);
        }

    }

    private void comboMatchElement (Monster monster, Team team){
        //Lkali ult, Awoken Kirin
        //atkdata is {combo multipliers first, match element multipliers last}
        int comboDiff = comboMax - comboMin;
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkMultiplier = atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkMultiplier = atkData.get(counter);
        }

        int comboDiff2 = comboMax2 - comboMin2;
        int counter2 = 0;
        for (int i = 0; i < matchElements.size(); i++){
            if(team.getOrbMatches().contains(matchElements.get(i))){
                counter++;
            }
        }
        if (counter >= comboDiff2){
            atkMultiplier *= atkData.get(comboDiff2 + comboDiff + 1);
        } else if (counter >= comboMin2){
            atkMultiplier *= atkData.get(counter2 + comboDiff + 1);
        }

    }

    private void matchElementActive (Monster monster, Team team, int stat){
        //Need active flag in team
        //Awoken Ra, Awoken Horus, Green Kirin
        //atkdata is {match elements first, additional damage with skill last}
        if(team.isActiveSkillUsed()){
            if (stat == 2){
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                            atkMultiplier = atkData.get(atkData.size() - 1);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkMultiplier = atkData.get(atkData.size() - 1);
                        }
                    }
                }
            } else if (stat == 3){
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i)) {
                            rcvMultiplier = rcvData.get(rcvData.size() - 1);
                        }
                    }
                }
                if (rcvElement.size() != 0) {
                    for (int i = 0; i < rcvElement.size(); i++) {
                        if (monster.getElement1Int() == rcvElement.get(i) || monster.getElement2Int() == rcvElement.get(i)) {
                            rcvMultiplier = rcvData.get(rcvData.size() - 1);
                        }
                    }
                }
            }
        }
        if (stat == 2){
            int comboDiff = comboMax - comboMin;
            int counter = 0;
            for (int i = 0; i < matchElements.size(); i++){
                if(team.getOrbMatches().contains(matchElements.get(i))){
                    counter++;
                }
            }
            if (counter >= comboDiff){
                atkMultiplier *= atkData.get(comboDiff);
            } else if (counter >= comboMin){
                atkMultiplier *= atkData.get(counter);
            }
        }

    }

    private void flatActive(Monster monster, Team team, int stat) {
        //Red/dark Cao cao
        //atkData will be {flat, active}
        if(team.isActiveSkillUsed()){
            if (stat == 2){
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                            atkMultiplier = atkData.get(1);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkMultiplier = atkData.get(1);
                        }
                    }
                }
            } else if (stat == 3){
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i)) {
                            rcvMultiplier = rcvData.get(1);
                        }
                    }
                }
                if (rcvElement.size() != 0) {
                    for (int i = 0; i < rcvElement.size(); i++) {
                        if (monster.getElement1Int() == rcvElement.get(i) || monster.getElement2Int() == rcvElement.get(i)) {
                            rcvMultiplier = rcvData.get(1);
                        }
                    }
                }
            }
        }
        if (stat == 2){
            if (atkType.size() != 0) {
                for (int i = 0; i < atkType.size(); i++) {
                    if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                        atkMultiplier *= atkData.get(0);
                        break;
                    }
                }
            }
            if (atkElement.size() != 0) {
                for (int i = 0; i < atkElement.size(); i++) {
                    if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                        atkMultiplier *= atkData.get(0);
                        break;
                    }
                }
            }
        } else if (stat == 3){
            if (rcvType.size() != 0) {
                for (int i = 0; i < rcvType.size(); i++) {
                    if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i)) {
                        rcvMultiplier *= rcvData.get(0);
                        break;
                    }
                }
            }
            if (rcvElement.size() != 0) {
                for (int i = 0; i < rcvElement.size(); i++) {
                    if (monster.getElement1Int() == rcvElement.get(i) || monster.getElement2Int() == rcvElement.get(i)) {
                        rcvMultiplier *= rcvData.get(0);
                        break;
                    }
                }
            }
        }
    }

    private void comboActive(Monster monster, Team team, int stat){
        //Awoken Anubis, Awoken Bastet
        int comboDiff = comboMax - comboMin;
        if(team.isActiveSkillUsed()){
            if (stat == 2){
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                            atkMultiplier = atkData.get(comboDiff + 1);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkMultiplier = atkData.get(comboDiff + 1);
                        }
                    }
                }
            } else if (stat == 3){
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i)) {
                            rcvMultiplier = rcvData.get(comboDiff + 1);
                        }
                    }
                }
                if (rcvElement.size() != 0) {
                    for (int i = 0; i < rcvElement.size(); i++) {
                        if (monster.getElement1Int() == rcvElement.get(i) || monster.getElement2Int() == rcvElement.get(i)) {
                            rcvMultiplier = rcvData.get(comboDiff + 1);
                        }
                    }
                }
            }
        }

        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkMultiplier *= atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkMultiplier *= atkData.get(counter);
        }
    }

    private void comboFlat(Monster monster, Team team){
        //Ronia ult
        int comboDiff = comboMax - comboMin;
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                    atkMultiplier = atkData.get(comboDiff + 1);
                }
            }
        }
        if (atkElement.size() != 0) {
            for (int i = 0; i < atkElement.size(); i++) {
                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                    atkMultiplier = atkData.get(comboDiff + 1);
                }
            }
        }
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkMultiplier *= atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkMultiplier *= atkData.get(counter);
        }
    }

    private void multiFlat (Monster monster, Team team){
        //Goetia?
        //Damn I don't remember
    }
}
