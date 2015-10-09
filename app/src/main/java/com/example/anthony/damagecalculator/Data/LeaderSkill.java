package com.example.anthony.damagecalculator.Data;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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
    @Column(name = "name", unique = true, index = true, onUniqueConflict = Column.ConflictAction.REPLACE)
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
    @Column(name = "matchElements2")
    private ArrayList<Element> matchElements2;
    @Column(name = "matchMonsters")
    private ArrayList<Long> matchMonsters;
    @Column(name = "hpSkillType")
    private LeaderSkillType hpSkillType;
    @Column(name = "atkSkillType")
    private LeaderSkillType atkSkillType;
    @Column(name = "rcvSkillType")
    private LeaderSkillType rcvSkillType;
    @Column(name = "hpPercent")
    private ArrayList<Integer> hpPercent;
    private double hpMultiplier;
    private double atkElement1Multiplier;
    private double atkElement2Multiplier;
    private double rcvMultiplier;

    public LeaderSkill() {
        hpMultiplier = 1;
        atkElement1Multiplier = 1;
        atkElement2Multiplier = 1;
        rcvMultiplier = 1;
        hpData = new ArrayList<>();
        atkData = new ArrayList<>();
        rcvData = new ArrayList<>();
        hpType = new ArrayList<>();
        hpElement = new ArrayList<>();
        atkType = new ArrayList<>();
        atkElement = new ArrayList<>();
        rcvType = new ArrayList<>();
        rcvElement = new ArrayList<>();
        matchElements = new ArrayList<>();
        matchElements = new ArrayList<>();
        matchMonsters = new ArrayList<>();
        hpPercent = new ArrayList<>();

    }

    public ArrayList<Double> getAtkData() {
        return atkData;
    }

    public void setAtkData(ArrayList<Double> atkData) {
        this.atkData = atkData;
    }

    public void addAtkData(Double data) {
        atkData.add(data);
    }

    public ArrayList<Integer> getAtkElement() {
        return atkElement;
    }

    public void setAtkElement(ArrayList<Integer> atkElement) {
        this.atkElement = atkElement;
    }

    public void addAtkElement(int element) {
        atkElement.add(element);
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

    public void addAtkType(int type) {
        atkType.add(type);
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

    public void addHpData(Double data) {
        hpData.add(data);
    }

    public ArrayList<Integer> getHpElement() {
        return hpElement;
    }

    public void setHpElement(ArrayList<Integer> hpElement) {
        this.hpElement = hpElement;
    }

    public void addHpElement(int element) {
        hpElement.add(element);
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

    public void addHpType(int type) {
        hpType.add(type);
    }

    public ArrayList<Element> getMatchElements() {
        return matchElements;
    }

    public void setMatchElements(ArrayList<Element> matchElements) {
        this.matchElements = matchElements;
    }

    public void addMatchElements(Element element) {
        matchElements.add(element);
    }

    public ArrayList<Element> getMatchElements2() {
        return matchElements2;
    }

    public void setMatchElements2(ArrayList<Element> matchElements2) {
        this.matchElements2 = matchElements2;
    }

    public void addMatchElements2(Element element) {
        matchElements2.add(element);
    }

    public ArrayList<Long> getMatchMonsters() {
        return matchMonsters;
    }

    public void setMatchMonsters(ArrayList<Long> matchMonsters) {
        this.matchMonsters = matchMonsters;
    }

    public void addMatchmonsters(Long monsterId) {
        matchMonsters.add(monsterId);
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

    public void addRcvData(Double data) {
        rcvData.add(data);
    }

    public ArrayList<Integer> getRcvElement() {
        return rcvElement;
    }

    public void setRcvElement(ArrayList<Integer> rcvElement) {
        this.rcvElement = rcvElement;
    }

    public void addRcvElement(int element) {
        rcvElement.add(element);
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

    public void addRcvType(int type) {
        rcvType.add(type);
    }

    public double getAtkElement1Multiplier() {
        return atkElement1Multiplier;
    }

    public void setAtkElement1Multiplier(double atkElement1Multiplier) {
        this.atkElement1Multiplier = atkElement1Multiplier;
    }

    public double getAtkElement2Multiplier() {
        return atkElement2Multiplier;
    }

    public void setAtkElement2Multiplier(double atkElement2Multiplier) {
        this.atkElement2Multiplier = atkElement2Multiplier;
    }

    public double getHpMultiplier() {
        return hpMultiplier;
    }

    public void setHpMultiplier(double hpMultiplier) {
        this.hpMultiplier = hpMultiplier;
    }

    public ArrayList<Integer> getHpPercent() {
        return hpPercent;
    }

    public void setHpPercent(ArrayList<Integer> hpPercent) {
        this.hpPercent = hpPercent;
    }

    public double getRcvMultiplier() {
        return rcvMultiplier;
    }

    public void setRcvMultiplier(double rcvMultiplier) {
        this.rcvMultiplier = rcvMultiplier;
    }

    public double hpMultiplier(Monster monster, Team team) {
        hpMultiplier = 1;
        switch (hpSkillType) {
            case BLANK:
                break;
            case FLAT:
                flat(monster, 1);
                break;
            case MONSTER_CONDITIONAL:
                monsterConditional(monster, team, 1);
                break;
        }
        return hpMultiplier;
    }

    public double atkElement1Multiplier(Monster monster, Team team) {
        atkElement1Multiplier = 1;
        switch (atkSkillType) {
            case BLANK:
                break;
            case FLAT:
                flat(monster, 2);
                break;
            case FLAT_ATTRIBUTE_ACTIVE_ATTRIBUTE:
                flatAttributeActiveAttribute(monster, team);
                break;
            case FLAT_TYPE_FLAT_ATTRIBUTE:
                flatTypeFlatAttribute(monster, team);
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
            case ORB_LINK:
                orbLink(monster, team);
                break;
            case ORB_LINK_FLAT:
                orbLinkFlat(monster, team);
                break;
            case ORB_LINK_INDIAN:
                orbLinkIndian(monster, team);
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
            case COMBO_ATTRIBUTE:
                comboAttribute(monster, team);
                break;
            case COMBO_EXACT:
                comboExact(monster, team);
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
            case ORB_PLUS_FLAT:
                orbPlusFlat(monster, team);
                break;
            case INDIAN_ORB_PLUS:
                indianOrbPlus(monster, team);
                break;
            case MATCH_ELEMENT_ORB_PLUS:
                matchElementOrbPlus(monster, team);
                break;
            case COMBO_ORB_PLUS:
                comboOrbPlus(monster, team);
                break;
            case GRIMOIRE_FLAT:
                multiFlat(monster, team);
                break;
            case HP_FLAT:
                hpFlat(monster, team, 2);
                break;
            case FLAT_HP_FLAT:
                flatHpFlat(monster, team);
                break;
            case HP_FLAT_ATTRIBUTE_FLAT_TYPE:
                hpFlatAttributeFlatType(monster, team);
                break;
            case ACTIVE:
                active(monster, team, 2);
                break;
            case FLAT_TYPE_ACTIVE_ATTRIBUTE:
                flatTypeActiveAttribute(monster, team);
                break;
            case ORB_LINK_EXACT_FLAT:
                orbLinkExactFlat(monster, team);
                break;
            case ORB_LINK_EXACT:
                orbLinkExact(monster, team);
                break;
        }
        return atkElement1Multiplier;
    }

    public double atkElement2Multiplier(Monster monster, Team team) {
        atkElement2Multiplier = 1;
        switch (atkSkillType) {
            case BLANK:
                break;
            case FLAT:
                flat(monster, 2);
                break;
            case FLAT_ATTRIBUTE_ACTIVE_ATTRIBUTE:
                flatAttributeActiveAttribute(monster, team);
                break;
            case FLAT_TYPE_FLAT_ATTRIBUTE:
                flatTypeFlatAttribute(monster, team);
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
            case ORB_LINK:
                orbLink(monster, team);
                break;
            case ORB_LINK_FLAT:
                orbLinkFlat(monster, team);
                break;
            case ORB_LINK_INDIAN:
                orbLinkIndian(monster, team);
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
            case COMBO_ATTRIBUTE:
                comboAttribute(monster, team);
                break;
            case COMBO_EXACT:
                comboExact(monster, team);
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
            case ORB_PLUS_FLAT:
                orbPlusFlat(monster, team);
                break;
            case INDIAN_ORB_PLUS:
                indianOrbPlus(monster, team);
                break;
            case MATCH_ELEMENT_ORB_PLUS:
                matchElementOrbPlus(monster, team);
                break;
            case COMBO_ORB_PLUS:
                comboOrbPlus(monster, team);
                break;
            case GRIMOIRE_FLAT:
                multiFlat(monster, team);
                break;
            case HP_FLAT:
                hpFlat(monster, team, 2);
                break;
            case FLAT_HP_FLAT:
                flatHpFlat(monster, team);
                break;
            case HP_FLAT_ATTRIBUTE_FLAT_TYPE:
                hpFlatAttributeFlatType(monster, team);
                break;
            case ACTIVE:
                active(monster, team, 2);
                break;
            case FLAT_TYPE_ACTIVE_ATTRIBUTE:
                flatTypeActiveAttribute(monster, team);
                break;
            case ORB_LINK_EXACT_FLAT:
                orbLinkExactFlat(monster, team);
                break;
            case ORB_LINK_EXACT:
                orbLinkExact(monster, team);
                break;
        }
        return atkElement2Multiplier;
    }

    public double rcvMultiplier(Monster monster, Team team) {
        rcvMultiplier = 1;
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
                comboActive(monster, team, 3);
                break;
            case HP_FLAT:
                hpFlat(monster, team, 3);
                break;
        }
        return rcvMultiplier;
    }

    private void flat(Monster monster, int stat) {
        switch (stat) {
            case 1:
                if (hpType.size() != 0) {
                    for (int i = 0; i < hpType.size(); i++) {
                        if (monster.getType1() == hpType.get(i) || monster.getType2() == hpType.get(i) || monster.getType3() == hpType.get(i)) {
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
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i) || monster.getType3() == rcvType.get(i)) {
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
        if (counter >= comboDiff) {
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        } else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier = atkData.get(counter);
            atkElement2Multiplier = atkData.get(counter);
        }
    }

    private void matchElement(Monster monster, Team team) {
        //Kirin, Horus, Ra
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        for (int i = 0; i < matchElements.size(); i++) {
            if (team.getOrbMatchElements().contains(matchElements.get(i))) {
                counter++;
            }
        }
        Log.d("Leader Skill Log", "Match Element counter: " + counter + " Attack 1 Multiplier: " + atkElement1Multiplier + " Attack 2 Multiplier: " + atkElement2Multiplier);
        if (counter >= comboMax) {
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier = atkData.get(counter - comboMin);
            atkElement2Multiplier = atkData.get(counter - comboMin);
        }
    }

    private void monsterConditional(Monster monster, Team team, int stat) {
        //Three kingdoms farmables. Zhao Yun.
        int counter = 0;
        for (int i = 0; i < matchMonsters.size(); i++) {
            if (team.getBaseMonsterId().contains(matchMonsters.get(i))) {
                counter++;
            }
        }
        if (counter == matchMonsters.size()) {
            if (stat == 1) {
                hpMultiplier = hpData.get(0);
            } else if (stat == 2) {
                atkElement1Multiplier = atkData.get(0);
                atkElement2Multiplier = atkData.get(0);
            } else if (stat == 3) {
                rcvMultiplier = rcvData.get(0);
            }
        }
    }

    private void flatMonsterConditional(Monster monster, Team team, int stat) {
        //Attack Data will look like {flat multiplier, monster conditional multiplier}
        //See Awoken Jord and Zhou Yu
        int counter = 0;
        for (int i = 0; i < matchMonsters.size(); i++) {
            if (team.getBaseMonsterId().contains(matchMonsters.get(i))) {
                counter++;
            }
        }
        Log.d("Leader Skill Log", "Flat Monster Conditional Counter: " + counter);
        if (stat == 1) {
            if (hpType.size() != 0) {
                for (int i = 0; i < hpType.size(); i++) {
                    if (monster.getType1() == hpType.get(i) || monster.getType2() == hpType.get(i) || monster.getType3() == hpType.get(i)) {
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
        } else if (stat == 2) {
            if (atkType.size() != 0) {
                for (int i = 0; i < atkType.size(); i++) {
                    if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
                        atkElement1Multiplier = atkData.get(0);
                        atkElement2Multiplier = atkData.get(0);
                    }
                }
            }
            if (atkElement.size() != 0) {
                for (int i = 0; i < atkElement.size(); i++) {
                    if (monster.getElement1Int() == atkElement.get(i) || monster.getType2() == atkElement.get(i) || monster.getType3() == atkElement.get(i)) {
                        atkElement1Multiplier = atkData.get(0);
                        atkElement2Multiplier = atkData.get(0);
                    }
                }
            }
        } else if (stat == 3) {
            if (rcvType.size() != 0) {
                for (int i = 0; i < rcvType.size(); i++) {
                    if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i) || monster.getType3() == rcvType.get(i)) {
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
        }
        if (counter == matchMonsters.size()) {
            if (stat == 1) {
                hpMultiplier *= hpData.get(1);
            } else if (stat == 2) {
                atkElement1Multiplier *= atkData.get(1);
                atkElement2Multiplier *= atkData.get(1);
            } else if (stat == 3) {
                rcvMultiplier *= rcvData.get(1);
            }
        }

    }

    private void orbPlus(Monster monster, Team team) {
        //Akechi Mitsuhide
        //atkData will be one number
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement1Multiplier = atkData.get(0);
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement2Multiplier = atkData.get(0);
                }
            }
        }
    }

    private void orbLink(Monster monster, Team team) {
        //Heroes
        //matchElements is the elements you can link, 2 for beach pandora and such.
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < matchElements.size(); j++) {
                Log.d("Leader Skill Log", "i is; " + i + " j is: " + j);
                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter >= comboMax) {
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier = atkData.get(counter - comboMin);
            atkElement2Multiplier = atkData.get(counter - comboMin);
        }
    }

    private void matchElementFlat(Monster monster, Team team) {
        //Sonia Gran.
        // atkdata is {match element multipliers, flat multiplier is last}
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
        for (int i = 0; i < matchElements.size(); i++) {
            if (team.getOrbMatchElements().contains(matchElements.get(i))) {
                counter++;
            }
        }
        if (counter >= comboMax) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }

    }

    private void comboMatchElement(Monster monster, Team team) {
        //Lkali ult, Awoken Kirin
        //atkdata is {combo multipliers first, match element multipliers last}
        int comboDiff = comboMax - comboMin;
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff) {
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        } else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier = atkData.get(counter);
            atkElement2Multiplier = atkData.get(counter);
        }

        int comboDiff2 = comboMax2 - comboMin2;
        int counter2 = 0;
        for (int i = 0; i < matchElements.size(); i++) {
            if (team.getOrbMatchElements().contains(matchElements.get(i))) {
                counter2++;
            }
        }
        if (counter2 >= comboMax2) {
            atkElement1Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
            atkElement2Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
        } else if (counter2 >= comboMin2) {
            atkElement1Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
            atkElement2Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
        }

    }

    private void matchElementActive(Monster monster, Team team, int stat) {
        //Need active flag in team
        //Awoken Ra, Awoken Horus, Green Kirin
        //atkdata is {match elements first, additional damage with skill last}
        Log.d("Leader Skill Log", "Active Skill Used: " + team.isActiveSkillUsed());
        if (team.isActiveSkillUsed()) {
            if (stat == 2) {
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
            } else if (stat == 3) {
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i) || monster.getType3() == rcvType.get(i)) {
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
        if (stat == 2) {
            int comboDiff = comboMax - comboMin;
            int counter = 0;
            for (int i = 0; i < matchElements.size(); i++) {
                if (team.getOrbMatchElements().contains(matchElements.get(i))) {
                    counter++;
                }
            }
            if (counter >= comboMax) {
                atkElement1Multiplier *= atkData.get(comboDiff);
                atkElement2Multiplier *= atkData.get(comboDiff);
            } else if (counter >= comboMin) {
                atkElement1Multiplier *= atkData.get(counter - comboMin);
                atkElement2Multiplier *= atkData.get(counter - comboMin);
            }
        }

    }

    private void flatActive(Monster monster, Team team, int stat) {
        //Nope. Not used
        //atkData will be {flat, active}
        if (team.isActiveSkillUsed()) {
            if (stat == 2) {
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
            } else if (stat == 3) {
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i) || monster.getType3() == rcvType.get(i)) {
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
        if (stat == 2) {
            if (atkType.size() != 0) {
                for (int i = 0; i < atkType.size(); i++) {
                    if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
        } else if (stat == 3) {
            if (rcvType.size() != 0) {
                for (int i = 0; i < rcvType.size(); i++) {
                    if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i) || monster.getType3() == rcvType.get(i)) {
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

    private void comboActive(Monster monster, Team team, int stat) {
        //Awoken Anubis, Awoken Bastet
        //{Combos, active}
        int comboDiff = comboMax - comboMin;
        if (team.isActiveSkillUsed()) {
            if (stat == 2) {
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
            } else if (stat == 3) {
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i) || monster.getType3() == rcvType.get(i)) {
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
        if (counter >= comboDiff) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter);
            atkElement2Multiplier *= atkData.get(counter);
        }
    }

    private void comboFlat(Monster monster, Team team) {
        //Ronia ult
        //{combo multiplier, flat multiplier}
        int comboDiff = comboMax - comboMin;
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
        if (counter >= comboDiff) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter);
            atkElement2Multiplier *= atkData.get(counter);
        }
    }

    private void multiFlat(Monster monster, Team team) {
        //Goetia? Not Goemon. I'll use this for Goetia and friends.
        //Damn I don't remember
        //atkdata will be {bigger multiplier, smaller multipler}
        int counter = 0;
        if (atkType.size() != 0) {
            if (monster.getType1() == atkType.get(0) || monster.getType2() == atkType.get(0) || monster.getType3() == atkType.get(0)) {
                if (monster.getType1() == atkType.get(1) || monster.getType2() == atkType.get(1) || monster.getType3() == atkType.get(1)) {
                    atkElement1Multiplier = atkData.get(0) * atkData.get(1);
                    atkElement2Multiplier = atkData.get(0) * atkData.get(1);
                } else {
                    atkElement1Multiplier = atkData.get(0);
                    atkElement2Multiplier = atkData.get(0);
                }
            } else if (monster.getType1() == atkType.get(1) || monster.getType2() == atkType.get(1) || monster.getType3() == atkType.get(1)) {
                atkElement1Multiplier = atkData.get(1);
                atkElement2Multiplier = atkData.get(1);
            }
        }
        if (atkElement.size() != 0) {
            if (monster.getElement1Int() == atkElement.get(0) || monster.getElement2Int() == atkElement.get(0)) {
                if (monster.getElement1Int() == atkElement.get(1) || monster.getElement2Int() == atkElement.get(1)) {
                    atkElement1Multiplier = atkData.get(0) * atkData.get(1);
                    atkElement2Multiplier = atkData.get(0) * atkData.get(1);
                } else {
                    atkElement1Multiplier = atkData.get(0);
                    atkElement2Multiplier = atkData.get(0);
                }
            } else if (monster.getElement1Int() == atkElement.get(1) || monster.getElement2Int() == atkElement.get(1)) {
                atkElement1Multiplier = atkData.get(1);
                atkElement2Multiplier = atkData.get(1);
            }
        }

    }

    private void indian(Monster monster, Team team) {
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < matchElements.size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= comboMax) {
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier = atkData.get(counter - comboMin);
            atkElement2Multiplier = atkData.get(counter - comboMin);
        }
    }

    private void indianFlat(Monster monster, Team team) {
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        //Ruel. atkData {match multiplier, flat multiplier}
        int comboDiff = comboMax - comboMin;
        if (atkType.size() != 0) {
            for (int i = 0; i < atkType.size(); i++) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < matchElements.size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= comboMax) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }
    }

    private void indianOrbPlus(Monster monster, Team team) {
        //Ult Krishna and ult Sarasvati
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = comboMax - comboMin;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }

        int counter = 0;
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < matchElements.size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= comboMax) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }
    }

    private void matchElementOrbPlus(Monster monster, Team team) {
        //Kite
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = comboMax - comboMin;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < matchElements.size(); i++) {
            if (team.getOrbMatchElements().contains(matchElements.get(i))) {
                counter++;
            }
        }
        if (counter >= comboMax) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }
    }

    private void comboOrbPlus(Monster monster, Team team) {
        //Awoken Yomi
        //atkData will be {combo multipliers, orb plus multiplier}
        int comboDiff = comboMax - comboMin;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement1Multiplier = atkData.get(comboDiff + 1);
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement2Multiplier = atkData.get(comboDiff + 1);
                }
            }
        }
        Log.d("Leader Skill Log", "Atk Multiplier 1: " + atkElement1Multiplier);
        Log.d("Leader Skill Log", "Atk Multiplier 2: " + atkElement2Multiplier);

        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (team.getOrbMatches().size() >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter);
            atkElement2Multiplier *= atkData.get(counter);
        }
        Log.d("Leader Skill Log", "Atk Multiplier 1: " + atkElement1Multiplier);
        Log.d("Leader Skill Log", "Atk Multiplier 2: " + atkElement2Multiplier);
    }

    private void orbPlusFlat(Monster monster, Team team) {
        //Not Sanada. Wot. Must've been a typo on PADx
        //atkData is {flat, orb plus multiplier
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement1Multiplier = atkData.get(1);
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement())) {
                    atkElement2Multiplier = atkData.get(1);
                }
            }
        }
        if (atkElement.size() != 0) {
            int i = 0;
            while (i < atkElement.size()) {
                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
                    atkElement1Multiplier *= atkData.get(0);
                    atkElement2Multiplier *= atkData.get(0);
                    i = atkElement.size() + 1;
                } else {
                    i++;
                }
            }
        }
        if (atkType.size() != 0 && atkElement1Multiplier != atkData.get(0) * atkData.get(1)) {
            int i = 0;
            while (i < atkType.size()) {
                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
                    atkElement1Multiplier *= atkData.get(0);
                    atkElement2Multiplier *= atkData.get(0);
                    i = atkType.size() + 1;
                } else {
                    i++;
                }
            }
        }
    }

    private void active(Monster monster, Team team, int stat) {
        if (team.isActiveSkillUsed()) {
            if (stat == 2) {
                if (atkType.size() != 0) {
                    for (int i = 0; i < atkType.size(); i++) {
                        if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
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
            } else if (stat == 3) {
                if (rcvType.size() != 0) {
                    for (int i = 0; i < rcvType.size(); i++) {
                        if (monster.getType1() == rcvType.get(i) || monster.getType2() == rcvType.get(i) || monster.getType3() == rcvType.get(i)) {
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
            }
        }
    }

    private void comboAttribute(Monster monster, Team team) {
        int comboDiff = comboMax - comboMin;
        int counter = team.getOrbMatches().size() - comboMin;
        if (counter >= comboDiff) {
            if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                atkElement1Multiplier = atkData.get(comboDiff);
                atkElement2Multiplier = atkData.get(comboDiff);
            }
        } else if (team.getOrbMatches().size() >= comboMin) {
            if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
                atkElement1Multiplier = atkData.get(counter);
                atkElement2Multiplier = atkData.get(counter);
            }
        }

    }

    private void hpFlat(Monster monster, Team team, int stat) {
        Log.d("Leader Skill Log", "teamHp is: " + team.getTeamHp());
        Log.d("Leader Skill Log", "HpPercent is: " + hpPercent);
        if (stat == 2) {
            Log.d("Leader Skill Log", "Stat is 2");
            if (hpPercent.size() == 1) {
                Log.d("Leader Skill Log", "I am enter HpPercent size 1");
                Log.d("Leader Skill Log", "HpPercent * 100 is: " + hpPercent.get(0));
                Log.d("Leader Skill Log", "AtkType is: " + atkType + " AtkElement is: " + atkElement);
                Log.d("Leader Skill Log", "Monster Type 1 is: " + monster.getType1() + " Monster Type 2 is: " + monster.getType2());
                if (team.getTeamHp() == hpPercent.get(0)) {
                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                        atkElement1Multiplier = atkData.get(0);
                        atkElement2Multiplier = atkData.get(0);
                    } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
                        atkElement1Multiplier = atkData.get(0);
                        atkElement2Multiplier = atkData.get(0);
                    }
                }
            } else {
                for (int i = 0; i < hpPercent.size()/2; i++) {
                    if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
                        if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                            atkElement1Multiplier = atkData.get(i);
                            atkElement2Multiplier = atkData.get(i);
                        } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
                            atkElement1Multiplier = atkData.get(i);
                            atkElement2Multiplier = atkData.get(i);
                        }
                    }
                }
            }
            Log.d("Leader Skill Log", "AtkMultiplier 1 is: " + atkElement1Multiplier);
            Log.d("Leader Skill Log", "AtkMultiplier 2 is: " + atkElement2Multiplier);
        } else if (stat == 3) {
            if (team.getTeamHp() == hpPercent.get(0)) {
                if (rcvElement.contains(monster.getElement1Int()) || rcvElement.contains(monster.getElement2Int())) {
                    rcvMultiplier = rcvData.get(0);
                } else if (rcvType.contains(monster.getType1()) || rcvType.contains(monster.getType2())) {
                    rcvMultiplier = atkData.get(0);
                }
            }
        } else {
            for (int i = 0; i < hpPercent.size()/2; i++) {
                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
                    if (rcvElement.contains(monster.getElement1Int()) || rcvElement.contains(monster.getElement2Int()) || atkType.contains(monster.getType3())) {
                        if (rcvMultiplier < rcvData.get(i)) {
                            rcvMultiplier = rcvData.get(i);
                        }
                    } else if (rcvType.contains(monster.getType1()) || rcvType.contains(monster.getType2())) {
                        if (rcvMultiplier < rcvData.get(i)) {
                            rcvMultiplier = rcvData.get(i);
                        }
                    }
                }
            }
        }
    }

    private void orbLinkFlat(Monster monster, Team team) {
        int comboDiff = comboMax - comboMin;
        if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
            atkElement1Multiplier = atkData.get(comboDiff + 1);
            atkElement2Multiplier = atkData.get(comboDiff + 1);
        } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
            atkElement1Multiplier = atkData.get(comboDiff + 1);
            atkElement2Multiplier = atkData.get(comboDiff + 1);
        }

        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < matchElements.size(); j++) {
                Log.d("Leader Skill Log", "i is; " + i + " j is: " + j);
                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter >= comboMax) {
            atkElement1Multiplier *= atkData.get(comboDiff);
            atkElement2Multiplier *= atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier *= atkData.get(counter - comboMin);
            atkElement2Multiplier *= atkData.get(counter - comboMin);
        }
    }

    private void comboExact(Monster monster, Team team) {
        if (team.getOrbMatches().size() == comboMin){
            atkElement1Multiplier = atkData.get(0);
            atkElement2Multiplier = atkData.get(0);
        }
    }

    private void orbLinkIndian(Monster monster, Team team){
        int comboDiff = comboMax - comboMin;
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < matchElements.size(); j++) {
                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter >= comboMax) {
            atkElement1Multiplier = atkData.get(comboDiff);
            atkElement2Multiplier = atkData.get(comboDiff);
        } else if (counter >= comboMin) {
            atkElement1Multiplier = atkData.get(counter - comboMin);
            atkElement2Multiplier = atkData.get(counter - comboMin);
        }

        int comboDiff2 = comboMax2 - comboMin2;
        int counter2 = 0;
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < matchElements.size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
                    orbMatchElements.remove(i);
                    j++;
                    counter2++;
                    i = -1;
                }
            }
        }
        if (counter2 >= comboMax2) {
            atkElement1Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
            atkElement2Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
        } else if (counter2 >= comboMin2) {
            atkElement1Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
            atkElement2Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
        }
    }

    private void flatHpFlat(Monster monster, Team team){
        if (hpPercent.size() == 1) {
            if (team.getTeamHp() == hpPercent.get(0)) {
                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                    atkElement1Multiplier = atkData.get(0);
                    atkElement2Multiplier = atkData.get(0);
                } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
                    atkElement1Multiplier = atkData.get(0);
                    atkElement2Multiplier = atkData.get(0);
                }
            }
        } else {
            for (int i = 0; i < hpPercent.size()/2; i++) {
                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                        atkElement1Multiplier = atkData.get(i);
                        atkElement2Multiplier = atkData.get(i);
                    } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
                        atkElement1Multiplier = atkData.get(i);
                        atkElement2Multiplier = atkData.get(i);
                    }
                }
            }
        }
        if(atkElement1Multiplier < atkDataMax()*atkData.get(hpPercent.size()/2)){
            if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                atkElement1Multiplier *= atkData.get(hpPercent.size()/2);
            } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
                atkElement1Multiplier *= atkData.get(hpPercent.size()/2);
            }
        }
        if(atkElement2Multiplier < atkDataMax()*atkData.get(hpPercent.size()/2)){
            if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                atkElement2Multiplier *= atkData.get(hpPercent.size()/2);
            } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
                atkElement2Multiplier *= atkData.get(hpPercent.size()/2);
            }
        }

    }

    private void flatAttributeActiveAttribute(Monster monster, Team team){
        //All attributes on the active skill used so yea. Cheating.
        if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
            atkElement1Multiplier = atkData.get(0);
            atkElement2Multiplier = atkData.get(0);
        }
        if(team.isActiveSkillUsed()){
            atkElement1Multiplier *= atkData.get(1);
            atkElement2Multiplier *= atkData.get(1);
        }
    }

    private void flatTypeFlatAttribute(Monster monster, Team team){
        if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
            atkElement1Multiplier = atkData.get(1);
            atkElement2Multiplier = atkData.get(1);
        }
        if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
            atkElement1Multiplier *= atkData.get(0);
            atkElement2Multiplier *= atkData.get(0);
        }
    }

    private void hpFlatAttributeFlatType(Monster monster, Team team){
        //Goemon Ult
        if (hpPercent.size() == 1) {
            if (team.getTeamHp() == hpPercent.get(0)) {
                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                    atkElement1Multiplier = atkData.get(0);
                    atkElement2Multiplier = atkData.get(0);
                }
            }
        } else {
            for (int i = 0; i < hpPercent.size()/2; i++) {
                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                        atkElement1Multiplier = atkData.get(i);
                        atkElement2Multiplier = atkData.get(i);
                    }
                }
            }
        }
        if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
            atkElement1Multiplier *= atkData.get(0);
            atkElement2Multiplier *= atkData.get(0);
        }
    }

    private void flatTypeActiveAttribute(Monster monster, Team team){
        if (team.isActiveSkillUsed()){
            if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
                atkElement1Multiplier = atkData.get(1);
                atkElement2Multiplier = atkData.get(1);
            }
        }
        if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
            atkElement1Multiplier *= atkData.get(0);
            atkElement2Multiplier *= atkData.get(0);
        }
    }

    private void orbLinkExactFlat(Monster monster, Team team){
        if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
            atkElement1Multiplier = atkData.get(1);
            atkElement2Multiplier = atkData.get(1);
        } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
            atkElement1Multiplier = atkData.get(1);
            atkElement2Multiplier = atkData.get(1);
        }
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < matchElements.size(); j++) {
                Log.d("Leader Skill Log", "i is; " + i + " j is: " + j);
                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
                    if (team.getOrbMatches().get(i).getOrbsLinked() == comboMin) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter == comboMin) {
            atkElement1Multiplier *= atkData.get(0);
            atkElement2Multiplier *= atkData.get(0);
        }
    }

    private void orbLinkExact(Monster monster, Team team){
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < matchElements.size(); j++) {
                Log.d("Leader Skill Log", "i is; " + i + " j is: " + j);
                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
                    if (team.getOrbMatches().get(i).getOrbsLinked() == comboMin) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter == comboMin) {
            atkElement1Multiplier = atkData.get(0);
            atkElement2Multiplier = atkData.get(0);
        }
    }

    private double atkDataMax(){
        double max = 1;
        for(int i = 0; i < atkData.size(); i++){
            if(i == 0){
                max = atkData.get(i);
            }else if (atkData.get(i) > atkData.get(i-1)){
                max = atkData.get(i);
            }
        }
        return max;
    }

    public static LeaderSkill getLeaderSkill(String name) {
        return new Select().from(LeaderSkill.class).where("name = ?", name).executeSingle();
    }
}
