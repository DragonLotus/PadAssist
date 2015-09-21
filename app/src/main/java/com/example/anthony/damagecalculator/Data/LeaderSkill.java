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
    private double atkElement1Multiplier;
    private double atkElement2Multiplier;
    private double rcvMultiplier;

    public LeaderSkill() {
        hpMultiplier = 1;
        atkElement1Multiplier = 1;
        atkElement2Multiplier = 1;
        rcvMultiplier = 1;
    }

    public ArrayList<Double> getAtkData() {
        return atkData;
    }

    public void setAtkData(ArrayList<Double> atkData) {
        this.atkData = atkData;
    }

    public ArrayList<Integer> getAtkElement() {
        return atkElement;
    }

    public void setAtkElement(ArrayList<Integer> atkElement) {
        this.atkElement = atkElement;
    }

    public LeaderSkillType getAtkSkillType() {
        return atkSkillType;
    }

    public void setAtkSkillType(LeaderSkillType atkSkillType) {
        this.atkSkillType = atkSkillType;
    }

    public ArrayList<Integer> getAtkType() {
        return atkType;
    }

    public void setAtkType(ArrayList<Integer> atkType) {
        this.atkType = atkType;
    }

    public int getComboMax2() {
        return comboMax2;
    }

    public void setComboMax2(int comboMax2) {
        this.comboMax2 = comboMax2;
    }

    public int getComboMax() {
        return comboMax;
    }

    public void setComboMax(int comboMax) {
        this.comboMax = comboMax;
    }

    public int getComboMin2() {
        return comboMin2;
    }

    public void setComboMin2(int comboMin2) {
        this.comboMin2 = comboMin2;
    }

    public int getComboMin() {
        return comboMin;
    }

    public void setComboMin(int comboMin) {
        this.comboMin = comboMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Double> getHpData() {
        return hpData;
    }

    public void setHpData(ArrayList<Double> hpData) {
        this.hpData = hpData;
    }

    public ArrayList<Integer> getHpElement() {
        return hpElement;
    }

    public void setHpElement(ArrayList<Integer> hpElement) {
        this.hpElement = hpElement;
    }

    public LeaderSkillType getHpSkillType() {
        return hpSkillType;
    }

    public void setHpSkillType(LeaderSkillType hpSkillType) {
        this.hpSkillType = hpSkillType;
    }

    public ArrayList<Integer> getHpType() {
        return hpType;
    }

    public void setHpType(ArrayList<Integer> hpType) {
        this.hpType = hpType;
    }

    public ArrayList<Element> getMatchElements() {
        return matchElements;
    }

    public void setMatchElements(ArrayList<Element> matchElements) {
        this.matchElements = matchElements;
    }

    public ArrayList<Monster> getMatchMonsters() {
        return matchMonsters;
    }

    public void setMatchMonsters(ArrayList<Monster> matchMonsters) {
        this.matchMonsters = matchMonsters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getRcvData() {
        return rcvData;
    }

    public void setRcvData(ArrayList<Double> rcvData) {
        this.rcvData = rcvData;
    }

    public ArrayList<Integer> getRcvElement() {
        return rcvElement;
    }

    public void setRcvElement(ArrayList<Integer> rcvElement) {
        this.rcvElement = rcvElement;
    }

    public LeaderSkillType getRcvSkillType() {
        return rcvSkillType;
    }

    public void setRcvSkillType(LeaderSkillType rcvSkillType) {
        this.rcvSkillType = rcvSkillType;
    }

    public ArrayList<Integer> getRcvType() {
        return rcvType;
    }

    public void setRcvType(ArrayList<Integer> rcvType) {
        this.rcvType = rcvType;
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

    private double atkElement1Multiplier(Monster monster, Team team) {
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
            case INDIAN:
                indian(monster, team);
                break;
            case INDIAN_FLAT:
                indianFlat(monster, team);
                break;
            case ORB_PLUS:
                orbPlus(monster, team);
                break;
            case INDIAN_PLUS_ORB:
                indianOrbPlus(monster, team);
                break;
            case MATCH_ELEMENT_ORB_PLUS:
                matchElementOrbPlus(monster, team);
                break;
            case COMBO_ORB_PLUS:
                comboOrbPlus(monster, team);
                break;
            case ORB_PLUS_FLAT:
                orbPlusFlat(monster, team);
                break;
            case MULTI_FLAT:
                multiFlat(monster, team);
                break;
        }
        return atkElement1Multiplier;
    }

    private double atkElement2Multiplier(Monster monster, Team team) {
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
            case INDIAN:
                indian(monster, team);
                break;
            case INDIAN_FLAT:
                indianFlat(monster, team);
                break;
            case ORB_PLUS:
                orbPlus(monster, team);
                break;
            case INDIAN_PLUS_ORB:
                indianOrbPlus(monster, team);
                break;
            case MATCH_ELEMENT_ORB_PLUS:
                matchElementOrbPlus(monster, team);
                break;
            case COMBO_ORB_PLUS:
                comboOrbPlus(monster, team);
                break;
            case ORB_PLUS_FLAT:
                orbPlusFlat(monster, team);
                break;
            case MULTI_FLAT:
                multiFlat(monster, team);
                break;
        }
        return atkElement2Multiplier;
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
                            atkElement1Multiplier = atkData.get(0);
                            atkElement2Multiplier = atkData.get(0);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkElement1Multiplier = atkData.get(0);
                            atkElement2Multiplier = atkData.get(0);
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
//            atkElement1Multiplier = atkData.get(comboDiff);
//        }else if(team.getOrbMatches().size() > comboMin){
//            for(int i = 0; i <= comboDiff; i++){
//                if(team.getOrbMatches().size() >= (comboMin + i)){
//                    atkElement1Multiplier = atkData.get(i);
//                }
//            }
//        }
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier = atkData.get(counter);
            atkElement2Multiplier = atkData.get(counter);
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
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        } else if (counter >= comboMin){
            atkElement1Multiplier = atkData.get(counter - comboMin);
            atkElement2Multiplier = atkData.get(counter - comboMin);
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
                atkElement1Multiplier = atkData.get(0);
                atkElement2Multiplier = atkData.get(0);
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
                            atkElement1Multiplier = atkData.get(0);
                            atkElement2Multiplier = atkData.get(0);
                        }
                    }
                }
                if (atkElement.size() != 0){
                    for (int i = 0; i < atkElement.size(); i++){
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getType2() == atkElement.get(i)){
                            atkElement1Multiplier = atkData.get(0);
                            atkElement2Multiplier = atkData.get(0);
                        }
                    }
                }
                atkElement1Multiplier *= atkData.get(1);
                atkElement2Multiplier *= atkData.get(1);
            } else if (stat == 3) {
                rcvMultiplier = rcvData.get(0);
            }
        }

    }

    private void orbPlus (Monster monster, Team team){
        //Akechi Mitsuhide
        //atkData will be one number
        for(int i = 0; i < team.getOrbMatches().size(); i++){
            if(team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5){
                if(monster.getElement1().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement1Multiplier = atkData.get(0);
                }
                if(monster.getElement2().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement2Multiplier = atkData.get(0);
                }
            }
        }
    }

    private void orbLink (Monster monster, Team team){
        //Heroes
        //matchElements is the elements you can link, 2 for beach pandora and such.
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
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        }else if (counter >= comboMin){
            atkElement1Multiplier = atkData.get(counter - comboMin);
            atkElement2Multiplier = atkData.get(counter - comboMin);
        }
    }

    private void matchElementFlat(Monster monster, Team team) {
        //Sonia Gran.
        // atkdata is {match element multipliers, flat multiplier is last}
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                    atkElement1Multiplier = atkData.get(atkData.size() - 1);
                    atkElement2Multiplier = atkData.get(atkData.size() - 1);
                }
            }
        }
        if (atkElement.size() != 0) {
            for (int i = 0; i < atkElement.size(); i++) {
                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                    atkElement1Multiplier = atkData.get(atkData.size() - 1);
                    atkElement2Multiplier = atkData.get(atkData.size() - 1);
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
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin){
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }

    }

    private void comboMatchElement (Monster monster, Team team){
        //Lkali ult, Awoken Kirin
        //atkdata is {combo multipliers first, match element multipliers last}
        int comboDiff = comboMax - comboMin;
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier = atkData.get(counter);
            atkElement2Multiplier = atkData.get(counter);
        }

        int comboDiff2 = comboMax2 - comboMin2;
        int counter2 = 0;
        for (int i = 0; i < matchElements.size(); i++){
            if(team.getOrbMatches().contains(matchElements.get(i))){
                counter++;
            }
        }
        if (counter >= comboDiff2){
            atkElement1Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
            atkElement2Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
        } else if (counter >= comboMin2){
            atkElement1Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
            atkElement2Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
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
                            atkElement1Multiplier = atkData.get(atkData.size() - 1);
                            atkElement2Multiplier = atkData.get(atkData.size() - 1);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkElement1Multiplier = atkData.get(atkData.size() - 1);
                            atkElement2Multiplier = atkData.get(atkData.size() - 1);
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
                atkElement1Multiplier *= atkData.get(comboDiff);
                atkElement2Multiplier *= atkData.get(comboDiff);
            } else if (counter >= comboMin){
                atkElement1Multiplier *= atkData.get(counter - comboMin);
                atkElement2Multiplier *= atkData.get(counter - comboMin);
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
                            atkElement1Multiplier = atkData.get(1);
                            atkElement2Multiplier = atkData.get(1);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkElement1Multiplier = atkData.get(1);
                            atkElement2Multiplier = atkData.get(1);
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
                        atkElement1Multiplier *= atkData.get(0);
                        atkElement2Multiplier *= atkData.get(0);
                        break;
                    }
                }
            }
            if (atkElement.size() != 0) {
                for (int i = 0; i < atkElement.size(); i++) {
                    if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                        atkElement1Multiplier *= atkData.get(0);
                        atkElement2Multiplier *= atkData.get(0);
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
                            atkElement1Multiplier = atkData.get(comboDiff + 1);
                            atkElement2Multiplier = atkData.get(comboDiff + 1);
                        }
                    }
                }
                if (atkElement.size() != 0) {
                    for (int i = 0; i < atkElement.size(); i++) {
                        if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                            atkElement1Multiplier = atkData.get(comboDiff + 1);
                            atkElement2Multiplier = atkData.get(comboDiff + 1);
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
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter);
            atkElement2Multiplier *= atkData.get(counter);
        }
    }

    private void comboFlat(Monster monster, Team team){
        //Ronia ult
        int comboDiff = comboMax - comboMin;
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }
        if (atkElement.size() != 0) {
            for (int i = 0; i < atkElement.size(); i++) {
                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter);
            atkElement2Multiplier *= atkData.get(counter);
        }
    }

    private void multiFlat (Monster monster, Team team){
        //Goetia?
        //Damn I don't remember
        //atkdata will be {bigger multiplier, smaller multipler}
        int counter = 0;
        if (atkType.size() != 0){
            if(monster.getType1() == atkType.get(0) || monster.getType2() == atkType.get(0)){
                if(monster.getType1() == atkType.get(1) || monster.getType2() == atkType.get(1)){
                    atkElement1Multiplier = atkData.get(0)*atkData.get(1);
                    atkElement2Multiplier = atkData.get(0)*atkData.get(1);
                }
                atkElement1Multiplier = atkData.get(0);
                atkElement2Multiplier = atkData.get(0);
            } else if (monster.getType1() == atkType.get(1) || monster.getType2() == atkType.get(1)){
                atkElement1Multiplier = atkData.get(1);
                atkElement2Multiplier = atkData.get(1);
            }
        }
        if (atkElement.size() != 0){
            if(monster.getElement1Int() == atkElement.get(0) || monster.getElement2Int() == atkElement.get(0)){
                if(monster.getElement1Int() == atkElement.get(1) || monster.getElement2Int() == atkElement.get(1)){
                    atkElement1Multiplier = atkData.get(0)*atkData.get(1);
                    atkElement2Multiplier = atkData.get(0)*atkData.get(1);
                }
                atkElement1Multiplier = atkData.get(0);
                atkElement2Multiplier = atkData.get(0);
            } else if (monster.getElement1Int() == atkElement.get(1) || monster.getElement2Int() == atkElement.get(1)){
                atkElement1Multiplier = atkData.get(1);
                atkElement2Multiplier = atkData.get(1);
            }
        }

    }

    private void indian (Monster monster, Team team){
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        int i = 0;
        ArrayList<OrbMatch> orbMatches = team.getOrbMatches();
        while(counter < matchElements.size()){
            if(orbMatches.get(i).getElement().equals(matchElements.get(counter))){
                orbMatches.remove(i);
                counter++;
                i = 0;
            }
            i++;
        }
        if (counter >= comboMax){
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        }else if (counter >= comboMin){
            atkElement1Multiplier = atkData.get(counter - comboMin);
            atkElement2Multiplier = atkData.get(counter - comboMin);
        }
    }

    private void indianFlat (Monster monster, Team team){
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        //Ruel. atkData {match multiplier, flat multiplier}
        int comboDiff = comboMax - comboMin;
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i)) {
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }
        if (atkElement.size() != 0) {
            for (int i = 0; i < atkElement.size(); i++) {
                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }
        int counter = 0;
        int i = 0;
        ArrayList<OrbMatch> orbMatches = team.getOrbMatches();
        while(counter < matchElements.size()){
            if(orbMatches.get(i).getElement().equals(matchElements.get(counter))){
                orbMatches.remove(i);
                counter++;
                i = 0;
            }
            i++;
        }
        if (counter >= comboMax){
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        }else if (counter >= comboMin){
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }
    }

    private void indianOrbPlus(Monster monster, Team team){
        //Ult Krishna and ult Sarasvati
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = comboMax - comboMin;
        for(int i = 0; i < team.getOrbMatches().size(); i++){
            if(team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5){
                if(monster.getElement1().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                }
                if(monster.getElement2().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }

        int counter = 0;
        int i = 0;
        ArrayList<OrbMatch> orbMatches = team.getOrbMatches();
        while(counter < matchElements.size()){
            if(orbMatches.get(i).getElement().equals(matchElements.get(counter))){
                orbMatches.remove(i);
                counter++;
                i = 0;
            }
            i++;
        }
        if (counter >= comboMax){
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        }else if (counter >= comboMin){
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }
    }

    private void matchElementOrbPlus(Monster monster, Team team){
        //Kite
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = comboMax - comboMin;
        for(int i = 0; i < team.getOrbMatches().size(); i++){
            if(team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5){
                if(monster.getElement1().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                }
                if(monster.getElement2().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < matchElements.size(); i++){
            if(team.getOrbMatches().contains(matchElements.get(i))){
                counter++;
            }
        }
        if (counter >= comboDiff){
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin){
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }
    }

    private void comboOrbPlus(Monster monster, Team team){
        //Awoken Yomi
        //atkData will be {combo multipliers, orb plus multiplier}
        int comboDiff = comboMax - comboMin;
        for(int i = 0; i < team.getOrbMatches().size(); i++){
            if(team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5){
                if(monster.getElement1().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                }
                if(monster.getElement2().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }

        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff){
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        }else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier = atkData.get(counter);
            atkElement2Multiplier = atkData.get(counter);
        }
    }

    private void orbPlusFlat(Monster monster, Team team){
        //Sanada Yukimura
        //atkData is {flat, orb plus multiplier
        for(int i = 0; i < team.getOrbMatches().size(); i++){
            if(team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5){
                if(monster.getElement1().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement1Multiplier = atkData.get(1);
                }
                if(monster.getElement2().equals(team.getOrbMatches().get(i).getElement())){
                    atkElement2Multiplier = atkData.get(1);
                }
            }
        }
        if (atkElement.size() != 0){
            int i = 0;
            while(i < atkElement.size()){
                if (monster.getElement1().equals(atkElement.get(i)) || monster.getElement2().equals(atkElement.get(i))){
                    atkElement1Multiplier *= atkData.get(0);
                    atkElement2Multiplier *= atkData.get(0);
                    i = atkElement.size() + 1;
                } else {
                    i++;
                }
            }
        }
        if (atkType.size() != 0){
            int i = 0;
            while(i < atkType.size()){
                if (monster.getElement1().equals(atkType.get(i)) || monster.getElement2().equals(atkType.get(i))){
                    atkElement1Multiplier = atkData.get(0);
                    atkElement2Multiplier *= atkData.get(0);
                    i = atkType.size() + 1;
                } else {
                    i++;
                }
            }
        }
    }
}
