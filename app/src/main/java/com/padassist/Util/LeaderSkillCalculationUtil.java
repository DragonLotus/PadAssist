package com.padassist.Util;

import android.util.Log;

import com.padassist.Data.Element;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.Monster;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.Team;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class LeaderSkillCalculationUtil {

    private static double hpMultiplier;
    private static ArrayList<Double> atkMultiplier = new ArrayList<>();
    private static double rcvMultiplier;

    public static double hpMultiplier(Monster monster, Team team, RealmResults<OrbMatch> orbMatchList) {
        hpMultiplier = 1;
        if(team.getLeadSkill() != null){
            if (team.getLeadSkill().getHpSkillType() != null && Singleton.getInstance().hasLeaderSkill()) {
                switch (team.getLeadSkill().getHpSkillType().getLeaderSkillType()) {
                    case FLAT:
                        flat(monster, team.getLeadSkill(), 1);
                        break;
                    case MONSTER_CONDITIONAL:
                        monsterConditional(monster, team, team.getLeadSkill(), orbMatchList, 1);
                        break;
                }
            }
        }

        if(team.getHelperSkill() != null){
            if (team.getHelperSkill().getHpSkillType() != null && Singleton.getInstance().hasHelperSkill()) {
                switch (team.getHelperSkill().getHpSkillType().getLeaderSkillType()) {
                    case FLAT:
                        flat(monster, team.getHelperSkill(), 1);
                        break;
                    case MONSTER_CONDITIONAL:
                        monsterConditional(monster, team, team.getHelperSkill(), orbMatchList, 1);
                        break;
                }
            }
        }

        return hpMultiplier;
    }

    public static ArrayList<Double> atkMultiplier(Monster monster, Team team, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        if (atkMultiplier == null) {
            atkMultiplier = new ArrayList<>();
        }
        if (atkMultiplier.size() != 2) {
            atkMultiplier.clear();
            atkMultiplier.add(1.);
            atkMultiplier.add(1.);
        } else {
            atkMultiplier.set(0, 1.);
            atkMultiplier.set(1, 1.);
        }
        if (team.getLeadSkill().getAtkSkillType() != null && Singleton.getInstance().hasLeaderSkill()) {
            switch (team.getLeadSkill().getAtkSkillType().getLeaderSkillType()) {
                case FLAT:
                    flat(monster, team.getLeadSkill(), 2);
                    break;
                case FLAT_ATTRIBUTE_ACTIVE_ATTRIBUTE:
                    flatAttributeActiveAttribute(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case FLAT_TYPE_FLAT_ATTRIBUTE:
                    flatTypeFlatAttribute(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case COMBO:
                    combo(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case MATCH_ELEMENT:
                    matchElement(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MONSTER_CONDITIONAL:
                    monsterConditional(monster, team, team.getLeadSkill(), orbMatchList, 2);
                    break;
                case FLAT_MONSTER_CONDITIONAL:
                    flatMonsterConditional(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_LINK:
                    orbLink(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_LINK_FLAT:
                    orbLinkFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_LINK_INDIAN:
                    orbLinkIndian(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_FLAT:
                    matchElementFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case COMBO_MATCH_ELEMENT:
                    comboMatchElement(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case MATCH_ELEMENT_ACTIVE:
                    matchElementActive(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case FLAT_ACTIVE:
                    flatActive(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case COMBO_ACTIVE:
                    comboActive(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case COMBO_FLAT:
                    comboFlat(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case COMBO_ATTRIBUTE:
                    comboAttribute(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case COMBO_EXACT:
                    comboExact(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case INDIAN:
                    indian(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case INDIAN_FLAT:
                    indianFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_PLUS:
                    orbPlus(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_PLUS_FLAT:
                    orbPlusFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case INDIAN_ORB_PLUS:
                    indianOrbPlus(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_ORB_PLUS:
                    matchElementOrbPlus(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case COMBO_ORB_PLUS:
                    comboOrbPlus(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case GRIMOIRE_FLAT:
                    multiFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HP_FLAT:
                    hpFlat(monster, team, team.getLeadSkill(), orbMatchList, 2);
                    break;
                case FLAT_HP_FLAT:
                    flatHpFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HP_FLAT_ATTRIBUTE_FLAT_TYPE:
                    hpFlatAttributeFlatType(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ACTIVE:
                    active(monster, team, team.getLeadSkill(), orbMatchList, 2);
                    break;
                case FLAT_TYPE_ACTIVE_ATTRIBUTE:
                    flatTypeActiveAttribute(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
//                case ORB_LINK_EXACT_FLAT:
//                    orbLinkExactFlat(monster, team);
//                    break;
//                case ORB_LINK_EXACT:
//                    orbLinkExact(monster, team);
//                    break;
                case ORB_LINK_ORB_PLUS:
                    orbLinkOrbPlus(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_ORB_LINK:
                    matchElementOrbLink(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case FLAT_TYPE_FLAT_TYPE:
                    flatTypeFlatType(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HP_FLAT_ORB_PLUS:
                    hpFlatOrbPlus(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HP_FLAT_MATCH_ELEMENT:
                    hpFlatMatchElement(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HP_FLAT_ACTIVE:
                    hpFlatActive(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_LINK_ACTIVE:
                    orbLinkActive(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HP_FLAT_ATTRIBUTE_FLAT_ATTRIBUTE:
                    hpFlatAttributeFlatAttribute(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case INDIAN_ACTIVE:
                    indianActive(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HEART_CROSS:
                    heartCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case COMBO_INDIAN:
                    comboIndian(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case ORB_LINK_HP_FLAT:
                    orbLinkHpFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_PLUS_HEART_CROSS:
                    orbPlusHeartCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case INDIAN_HEART_CROSS:
                    indianHeartCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case FLAT_HEART_CROSS:
                    flatHeartCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_HEART_CROSS:
                    matchElementHeartCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ACTIVE_HEART_CROSS:
                    activeHeartCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case INDIAN_MONSTER_CONDITIONAL:
                    indianMonsterConditional(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_PLUS_MONSTER_CONDITIONAL:
                    orbPlusMonsterConditional(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case CROSS:
                    cross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case INDIAN_CROSS:
                    indianCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ACTIVE_CROSS:
                    activeCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case CO_OP_HP_FLAT:
                    coopHpFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case CO_OP_FLAT:
                    coopFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case CO_OP:
                    coop(monster, team, team.getLeadSkill(), orbMatchList, 2);
                    break;
                case FLAT_CROSS:
                    flatCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case HEART_CROSS_CROSS:
                    heartCrossCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_INDIAN:
                    matchElementIndian(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_INDIAN_FLAT:
                    indianFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_ORB_LINK_FLAT:
                    orbLinkFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_MATCH_ELEMENT_FLAT:
                    matchElementFlat(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_COMBO_FLAT:
                    comboFlat(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case ORB_LINK_ORB_LINK:
                    orbLinkOrbLink(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case ORB_LINK_HEART_CROSS:
                    orbLinkHeartCross(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
                case COMBO_ORB_LINK:
                    comboOrbLink(monster, team, team.getLeadSkill(), orbMatchList, totalCombos);
                    break;
                case MATCH_ELEMENT_MONSTER_CONDITIONAL:
                    matchElementMonsterConditional(monster, team, team.getLeadSkill(), orbMatchList);
                    break;
            }
        }

        if (team.getHelperSkill().getAtkSkillType() != null && Singleton.getInstance().hasHelperSkill()) {
            switch (team.getHelperSkill().getAtkSkillType().getLeaderSkillType()) {
                case FLAT:
                    flat(monster, team.getHelperSkill(), 2);
                    break;
                case FLAT_ATTRIBUTE_ACTIVE_ATTRIBUTE:
                    flatAttributeActiveAttribute(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case FLAT_TYPE_FLAT_ATTRIBUTE:
                    flatTypeFlatAttribute(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case COMBO:
                    combo(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case MATCH_ELEMENT:
                    matchElement(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MONSTER_CONDITIONAL:
                    monsterConditional(monster, team, team.getHelperSkill(), orbMatchList, 2);
                    break;
                case FLAT_MONSTER_CONDITIONAL:
                    flatMonsterConditional(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_LINK:
                    orbLink(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_LINK_FLAT:
                    orbLinkFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_LINK_INDIAN:
                    orbLinkIndian(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_FLAT:
                    matchElementFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case COMBO_MATCH_ELEMENT:
                    comboMatchElement(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case MATCH_ELEMENT_ACTIVE:
                    matchElementActive(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case FLAT_ACTIVE:
                    flatActive(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case COMBO_ACTIVE:
                    comboActive(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case COMBO_FLAT:
                    comboFlat(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case COMBO_ATTRIBUTE:
                    comboAttribute(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case COMBO_EXACT:
                    comboExact(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case INDIAN:
                    indian(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case INDIAN_FLAT:
                    indianFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_PLUS:
                    orbPlus(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_PLUS_FLAT:
                    orbPlusFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case INDIAN_ORB_PLUS:
                    indianOrbPlus(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_ORB_PLUS:
                    matchElementOrbPlus(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case COMBO_ORB_PLUS:
                    comboOrbPlus(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case GRIMOIRE_FLAT:
                    multiFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HP_FLAT:
                    hpFlat(monster, team, team.getHelperSkill(), orbMatchList, 2);
                    break;
                case FLAT_HP_FLAT:
                    flatHpFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HP_FLAT_ATTRIBUTE_FLAT_TYPE:
                    hpFlatAttributeFlatType(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ACTIVE:
                    active(monster, team, team.getHelperSkill(), orbMatchList, 2);
                    break;
                case FLAT_TYPE_ACTIVE_ATTRIBUTE:
                    flatTypeActiveAttribute(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
//                case ORB_LINK_EXACT_FLAT:
//                    orbLinkExactFlat(monster, team);
//                    break;
//                case ORB_LINK_EXACT:
//                    orbLinkExact(monster, team);
//                    break;
                case ORB_LINK_ORB_PLUS:
                    orbLinkOrbPlus(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_ORB_LINK:
                    matchElementOrbLink(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case FLAT_TYPE_FLAT_TYPE:
                    flatTypeFlatType(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HP_FLAT_ORB_PLUS:
                    hpFlatOrbPlus(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HP_FLAT_MATCH_ELEMENT:
                    hpFlatMatchElement(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HP_FLAT_ACTIVE:
                    hpFlatActive(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_LINK_ACTIVE:
                    orbLinkActive(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HP_FLAT_ATTRIBUTE_FLAT_ATTRIBUTE:
                    hpFlatAttributeFlatAttribute(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case INDIAN_ACTIVE:
                    indianActive(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HEART_CROSS:
                    heartCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case COMBO_INDIAN:
                    comboIndian(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case ORB_LINK_HP_FLAT:
                    orbLinkHpFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_PLUS_HEART_CROSS:
                    orbPlusHeartCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case INDIAN_HEART_CROSS:
                    indianHeartCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case FLAT_HEART_CROSS:
                    flatHeartCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_HEART_CROSS:
                    matchElementHeartCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ACTIVE_HEART_CROSS:
                    activeHeartCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case INDIAN_MONSTER_CONDITIONAL:
                    indianMonsterConditional(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_PLUS_MONSTER_CONDITIONAL:
                    orbPlusMonsterConditional(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case CROSS:
                    cross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case INDIAN_CROSS:
                    indianCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ACTIVE_CROSS:
                    activeCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case CO_OP_HP_FLAT:
                    coopHpFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case CO_OP_FLAT:
                    coopFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case CO_OP:
                    coop(monster, team, team.getHelperSkill(), orbMatchList, 2);
                    break;
                case FLAT_CROSS:
                    flatCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case HEART_CROSS_CROSS:
                    heartCrossCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MATCH_ELEMENT_INDIAN:
                    matchElementIndian(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_INDIAN_FLAT:
                    indianFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_ORB_LINK_FLAT:
                    orbLinkFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_MATCH_ELEMENT_FLAT:
                    matchElementFlat(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case MINIMUM_MATCH_COMBO_FLAT:
                    comboFlat(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case ORB_LINK_ORB_LINK:
                    orbLinkOrbLink(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case ORB_LINK_HEART_CROSS:
                    orbLinkHeartCross(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
                case COMBO_ORB_LINK:
                    comboOrbLink(monster, team, team.getHelperSkill(), orbMatchList, totalCombos);
                    break;
                case MATCH_ELEMENT_MONSTER_CONDITIONAL:
                    matchElementMonsterConditional(monster, team, team.getHelperSkill(), orbMatchList);
                    break;
            }
        }
        return atkMultiplier;
    }

    public static double rcvMultiplier(Monster monster, Team team, RealmResults<OrbMatch> orbMatchList) {
        rcvMultiplier = 1;
        if(team.getLeadSkill() != null){
            if (team.getLeadSkill().getRcvSkillType() != null && Singleton.getInstance().hasLeaderSkill()) {
                switch (team.getLeadSkill().getRcvSkillType().getLeaderSkillType()) {
                    case FLAT:
                        flat(monster, team.getLeadSkill(), 3);
                        break;
                    case MONSTER_CONDITIONAL:
                        monsterConditional(monster, team, team.getLeadSkill(), orbMatchList, 3);
                        break;
                    case HP_FLAT:
                        hpFlat(monster, team, team.getLeadSkill(), orbMatchList, 3);
                        break;
                    case ACTIVE:
                        active(monster, team, team.getLeadSkill(), orbMatchList, 3);
                        break;
                    case CO_OP:
                        coop(monster, team, team.getLeadSkill(), orbMatchList, 3);
                        break;
                }
            }
        }


        if(team.getHelperSkill() != null){
            if (team.getHelperSkill().getRcvSkillType() != null && Singleton.getInstance().hasHelperSkill()) {
                switch (team.getHelperSkill().getRcvSkillType().getLeaderSkillType()) {
                    case FLAT:
                        flat(monster, team.getHelperSkill(), 3);
                        break;
                    case MONSTER_CONDITIONAL:
                        monsterConditional(monster, team, team.getHelperSkill(), orbMatchList, 3);
                        break;
                    case HP_FLAT:
                        hpFlat(monster, team, team.getHelperSkill(), orbMatchList, 3);
                        break;
                    case ACTIVE:
                        active(monster, team, team.getHelperSkill(), orbMatchList, 3);
                        break;
                    case CO_OP:
                        coop(monster, team, team.getHelperSkill(), orbMatchList, 3);
                        break;
                }
            }
        }

        return rcvMultiplier;
    }

    public static double comboRcv(LeaderSkill leaderSkill, int totalCombos) {
        //3198
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            return leaderSkill.getAtkData().get(comboDiff).getValue();
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            return leaderSkill.getAtkData().get(counter).getValue();
        }
        return 1;
    }

    public static double indianRcv(Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            return leaderSkill.getRcvData().get(comboDiff).getValue();
        } else if (counter >= leaderSkill.getComboMin()) {
            return leaderSkill.getRcvData().get(counter - leaderSkill.getComboMin()).getValue();
        } else return 1;
    }

    public static double orbLinkRcv(Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }

        if (counter >= leaderSkill.getComboMax()) {
            return leaderSkill.getRcvData().get(comboDiff).getValue();
        } else if (counter >= leaderSkill.getComboMin()) {
            return leaderSkill.getRcvData().get(counter - leaderSkill.getComboMin()).getValue();
        } else return 1;
    }

    private static void flat(Monster monster, LeaderSkill leaderSkill, int stat) {
        boolean buffed = false;
        switch (stat) {
            case 1:
                if (leaderSkill.getHpType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getHpType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getHpType().get(i).getValue()) && !buffed) {
                            hpMultiplier *= leaderSkill.getHpData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getHpElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getHpElement().size(); i++) {
                        if ((leaderSkill.getHpElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getHpElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            hpMultiplier *= leaderSkill.getHpData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                break;
            case 2:
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                break;
            case 3:
                if (leaderSkill.getRcvType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getRcvType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getRcvType().get(i).getValue()) && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getRcvElement().size(); i++) {
                        if ((leaderSkill.getRcvElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                break;
        }
    }

    private static void flatAttributeActiveAttribute(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //All attributes on the active skill used so yea. Cheating.
        boolean buffed = false;
        if (leaderSkill.getAtkElement().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (Singleton.getInstance().isActiveSkillUsed()) {
            buffed = false;
            if (leaderSkill.getAtkElement2().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkElement2().size(); i++) {
                    if ((leaderSkill.getAtkElement2().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement2().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        buffed = true;
                    }
                }
            }
        }
    }

    private static void flatTypeFlatAttribute(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkElement().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    buffed = true;
                }
            }
        }
    }

    private static void combo(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        //Bastet, Anubis
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
        }
    }

    private static void matchElement(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Kirin, Horus, Ra
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void monsterConditional(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int stat) {
        //Three kingdoms farmables. Zhao Yun.
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchMonsters().size(); i++) {
            if (team.getBaseMonsterId().contains(leaderSkill.getMatchMonsters().get(i).getValue())) {
                counter++;
            }
        }
        if (counter == leaderSkill.getMatchMonsters().size()) {
            if (stat == 1) {
                hpMultiplier *= leaderSkill.getHpData().get(0).getValue();
            } else if (stat == 2) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
            } else if (stat == 3) {
                rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
            }
        }
    }


    private static void flatMonsterConditional(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Attack Data will look like {flat multiplier, monster conditional multiplier}
        //See Awoken Jord
        boolean buffed = false;
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchMonsters().size(); i++) {
            if (team.getBaseMonsterId().contains(leaderSkill.getMatchMonsters().get(i).getValue())) {
                counter++;
            }
        }
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (counter == leaderSkill.getMatchMonsters().size()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
        }

    }

    private static void orbLink(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Heroes
        //matchElements is the elements you can link, 2 for beach pandora and such.
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void orbLinkFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //{Combos, flat}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void orbLinkIndian(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        int comboDiff2 = leaderSkill.getComboMax2() - leaderSkill.getComboMin2();
        int counter2 = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements2().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements2().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter2++;
                    i = -1;
                }
            }
        }

        if (counter2 >= leaderSkill.getComboMax2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter2 >= leaderSkill.getComboMin2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }
    }

    private static void matchElementFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Sonia Gran.
        // atkdata is {match element multipliers, flat multiplier is last}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void comboMatchElement(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        //Lkali ult, Awoken Kirin
        //atkdata is {combo multipliers first, match element multipliers last}
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
        }

        int comboDiff2 = leaderSkill.getComboMax2() - leaderSkill.getComboMin2();
        int counter2 = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter2++;
            }
        }
        Log.d("LeaderSkillCalcUtil", "counter2 is: " + counter2);

        if (counter2 >= leaderSkill.getComboMax2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter2 >= leaderSkill.getComboMin2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }
    }

    private static void matchElementActive(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Need active flag in team
        //Awoken Ra, Awoken Horus, Green Kirin
        //atkdata is {match elements first, additional damage with skill last}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void flatActive(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //atkData will be {flat, active}
        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        buffed = true;
                    }
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }

    }

    private static void comboActive(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        //Awoken Anubis, Awoken Bastet
        //{Combos, active}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
        }

        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
        }
    }

    private static void comboFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        //Ronia ult
        //{combo multiplier, flat multiplier}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }

        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
        }
    }

    private static void comboAttribute(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed = false;

        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            if (leaderSkill.getAtkElement().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
                        buffed = true;
                    }
                }
            }
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            if (leaderSkill.getAtkElement().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
                        buffed = true;
                    }
                }
            }
        }
    }

    private static void comboExact(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        if (totalCombos == leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
        }
    }

    private static void indian(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void indianFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        //Ruel. atkData {match multiplier, flat multiplier}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }

        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void orbPlus(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Akechi Mitsuhide
        //atkData will be one number
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed2 = true;
                }
            }
        }
    }

    private static void orbPlusFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Not Sanada. Wot. Must've been a typo on PADx
        //atkData is {orbPlus multiplier, flat}
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed2 = true;
                }
            }
        }
        buffed1 = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    buffed1 = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed1) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    buffed1 = true;
                }
            }
        }
    }

    private static void indianOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Ult Krishna and ult Sarasvati
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed2 = true;
                }
            }
        }
        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void matchElementOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Kite
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed2 = true;
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }


    private static void comboOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        //Awoken Yomi
        //atkData will be {combo multipliers, orb plus multiplier}
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed2 = true;
                }
            }
        }

        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
        }
    }

    private static void multiFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Goetia? Not Goemon. I'll use this for Goetia and friends.
        //Damn I don't remember
        //atkdata will be {bigger multiplier, smaller multiplier}
        if (leaderSkill.getAtkType().size() != 0) {
            if (monster.getTypes().contains(leaderSkill.getAtkType().get(0).getValue())) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(1).getValue())) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue() * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue() * leaderSkill.getAtkData().get(1).getValue());
                } else {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                }
            } else if (monster.getTypes().contains(leaderSkill.getAtkType().get(1).getValue())) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
            }
        }
        if (leaderSkill.getAtkElement().size() != 0) {
            if (monster.getElement1Int() == leaderSkill.getAtkElement().get(0).getValue() || monster.getElement2Int() == leaderSkill.getAtkElement().get(0).getValue()) {
                if (monster.getElement1Int() == leaderSkill.getAtkElement().get(1).getValue() || monster.getElement2Int() == leaderSkill.getAtkElement().get(1).getValue()) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue() * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue() * leaderSkill.getAtkData().get(1).getValue());
                } else {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                }
            } else if (monster.getElement1Int() == leaderSkill.getAtkElement().get(1).getValue() || monster.getElement2Int() == leaderSkill.getAtkElement().get(1).getValue()) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
            }
        }

    }

    private static void hpFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int stat) {
        boolean buffed = false;
        if (stat == 2) {
            if (leaderSkill.getHpPercent().size() == 1) {
                if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                            if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                            if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                    if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                        if (leaderSkill.getAtkType().size() != 0) {
                            for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                                if (monster.getTypes().contains(leaderSkill.getAtkType().get(j).getValue()) && !buffed) {
                                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                    buffed = true;
                                }
                            }
                        }
                        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                            for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                                if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                    buffed = true;
                                }
                            }
                        }
                    }
                }
            }
        } else if (stat == 3) {
            if (leaderSkill.getHpPercent().size() == 1) {
                if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                    if (leaderSkill.getRcvType().size() != 0) {
                        for (int i = 0; i < leaderSkill.getRcvType().size(); i++) {
                            if (monster.getTypes().contains(leaderSkill.getRcvType().get(i).getValue()) && !buffed) {
                                rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                        for (int i = 0; i < leaderSkill.getRcvElement().size(); i++) {
                            if ((leaderSkill.getRcvElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                                rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                                buffed = true;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                    if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                        if (leaderSkill.getRcvType().size() != 0) {
                            for (int j = 0; j < leaderSkill.getRcvType().size(); j++) {
                                if (monster.getTypes().contains(leaderSkill.getRcvType().get(j).getValue()) && !buffed) {
                                    rcvMultiplier *= leaderSkill.getRcvData().get(i).getValue();
                                    buffed = true;
                                }
                            }
                        }
                        if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                            for (int j = 0; j < leaderSkill.getRcvElement().size(); j++) {
                                if ((leaderSkill.getRcvElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                    rcvMultiplier *= leaderSkill.getRcvData().get(i).getValue();
                                    buffed = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void flatHpFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //{HP flats, flat}
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                            if (monster.getTypes().contains(leaderSkill.getAtkType().get(j).getValue()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    buffed = true;
                }
            }
        }
    }

    private static void hpFlatAttributeFlatType(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Goemon Ult
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkElement().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {

                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    buffed = true;
                }
            }
        }
    }

    private static void active(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int stat) {
        if (Singleton.getInstance().isActiveSkillUsed()) {
            boolean buffed = false;
            if (stat == 2) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            } else if (stat == 3) {
                if (leaderSkill.getRcvType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getRcvType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getRcvType().get(i).getValue()) && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getRcvElement().size(); i++) {
                        if ((leaderSkill.getRcvElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
            }
        }
    }

    private static void flatTypeActiveAttribute(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkElement().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        buffed = true;
                    }
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
    }
//
//    private void orbLinkExactFlat(Monster monster, Team team) {
//        if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//            atkElement1Multiplier = atkData.get(1);
//            atkElement2Multiplier = atkData.get(1);
//        } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
//            atkElement1Multiplier = atkData.get(1);
//            atkElement2Multiplier = atkData.get(1);
//        }
//        int counter = 0;
//        for (int i = 0; i < orbMatchList.size(); i++) {
//            for (int j = 0; j < matchElements.size(); j++) {
//                if (orbMatchList.get(i).getElement().equals(matchElements.get(j))) {
//                    if (orbMatchList.get(i).getOrbsLinked() == comboMin) {
//                        counter = orbMatchList.get(i).getOrbsLinked();
//                    }
//                }
//            }
//
//        }
//        if (counter == comboMin) {
//            atkElement1Multiplier *= atkData.get(0);
//            atkElement2Multiplier *= atkData.get(0);
//        }
//    }
//
//    private void orbLinkExact(Monster monster, Team team) {
//        int counter = 0;
//        for (int i = 0; i < orbMatchList.size(); i++) {
//            for (int j = 0; j < matchElements.size(); j++) {
//                if (orbMatchList.get(i).getElement().equals(matchElements.get(j))) {
//                    if (orbMatchList.get(i).getOrbsLinked() == comboMin) {
//                        counter = orbMatchList.get(i).getOrbsLinked();
//                    }
//                }
//            }
//
//        }
//        if (counter == comboMin) {
//            atkElement1Multiplier = atkData.get(0);
//            atkElement2Multiplier = atkData.get(0);
//        }
//    }

    private static void orbLinkOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed2 = true;
                }
            }
        }
    }

    private static void matchElementOrbLink(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        int comboDiff2 = leaderSkill.getComboMax2() - leaderSkill.getComboMin2();
        int counter2 = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements2().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements2().get(j).getElement())) {
                    if (counter2 < orbMatchList.get(i).getOrbsLinked()) {
                        counter2 = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter2 >= leaderSkill.getComboMax2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter2 >= leaderSkill.getComboMin2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }
    }

    private static void flatTypeFlatType(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkType2().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType2().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType2().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    buffed = true;
                }
            }
        }
    }

    private static void hpFlatOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed1 = false, buffed2 = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed1) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed1 = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed1) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed1) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed1 = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                            if (monster.getTypes().contains(leaderSkill.getAtkType().get(j).getValue()) && !buffed1) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed1 = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed1) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed1) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed1 = true;
                            }
                        }
                    }
                }
            }
        }
        buffed1 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                    buffed2 = true;
                }
            }
        }
    }

    public static void hpFlatMatchElement(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                            if (monster.getTypes().contains(leaderSkill.getAtkType().get(j).getValue()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            }
        }
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }
        if (leaderSkill.getHpPercent().size() == 1) {
            if (counter >= leaderSkill.getComboMax()) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
            } else if (counter >= leaderSkill.getComboMin()) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin() + 1).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin() + 1).getValue());
            }
        } else {
            if (counter >= leaderSkill.getComboMax()) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + leaderSkill.getHpPercent().size() / 2).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + leaderSkill.getHpPercent().size() / 2).getValue());
            } else if (counter >= leaderSkill.getComboMin()) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin() + leaderSkill.getHpPercent().size() / 2).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin() + leaderSkill.getHpPercent().size() / 2).getValue());
            }
        }

    }

    public static void hpFlatActive(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                            if (monster.getTypes().contains(leaderSkill.getAtkType().get(j).getValue()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            }
        }
        buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        buffed = true;
                    }
                }
            }
        }
    }

    private static void orbLinkActive(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                        buffed = true;
                    }
                }
            }
        }
    }

    private static void hpFlatAttributeFlatAttribute(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Dmeta
        boolean buffed = false;
        Log.d("LeaderSkillCalc", "Team Hp is: " + team.getTeamHp());
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkElement().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                Log.d("LeaderSkillCalc", "atkMultiplier 0 and 1 are: " + atkMultiplier.get(0) + " " + atkMultiplier.get(1) + " atkData i is: " + leaderSkill.getAtkData().get(i).getValue() + " for monster: " + monster.getName());
                                buffed = true;
                            }
                        }
                    }
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkElement2().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkElement2().size(); i++) {
                if ((leaderSkill.getAtkElement2().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement2().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                    Log.d("LeaderSkillCalc", "atkData flat is: " + leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue() + " for monster: " + monster.getName());
                    buffed = true;
                }
            }
        }
    }

    private static void indianActive(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        //Awoken Sun Quan
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
        }

        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void heartCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
    }

    private static void comboIndian(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
        }

        int comboDiff2 = leaderSkill.getComboMax2() - leaderSkill.getComboMin2();
        int counter2 = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements2().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements2().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter2++;
                    i = -1;
                }
            }
        }

        if (counter2 >= leaderSkill.getComboMax2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter2 >= leaderSkill.getComboMin2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }
    }

    private static void orbLinkHpFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                            if (monster.getTypes().contains(leaderSkill.getAtkType().get(j).getValue()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1 + i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1 + i).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1 + i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1 + i).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            }
        }
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }
    }

    private static void orbPlusHeartCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed2 = true;
                }
            }
        }
    }

    private static void indianHeartCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
    }

    private static void flatHeartCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }

        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
    }

    private static void matchElementHeartCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
    }

    private static void activeHeartCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                        buffed = true;
                    }
                }
            }
        }

        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
    }

    private static void indianMonsterConditional(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        int counter2 = 0;
        for (int i = 0; i < leaderSkill.getMatchMonsters().size(); i++) {
            if (team.getBaseMonsterId().contains(leaderSkill.getMatchMonsters().get(i).getValue())) {
                counter2++;
            }
        }
        if (counter2 == leaderSkill.getMatchMonsters().size()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
        }
    }

    private static void orbPlusMonsterConditional(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchMonsters().size(); i++) {
            if (team.getBaseMonsterId().contains(leaderSkill.getMatchMonsters().get(i).getValue())) {
                counter++;
            }
        }
        if (counter == leaderSkill.getMatchMonsters().size()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
        }

        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).getNumOrbPlus() >= 1 && orbMatchList.get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(orbMatchList.get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(orbMatchList.get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed2 = true;
                }
            }
        }
    }

    private static void cross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).isCross()) {
                for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                    if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    }
                }
            }
        }
    }

    private static void indianCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter++;
                    i = -1;
                }
            }
        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).isCross()) {
                for (int j = 0; j < leaderSkill.getMatchElements2().size(); j++) {
                    if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements2().get(j).getElement())) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    }
                }
            }
        }
    }

    private static void activeCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                        buffed = true;
                    }
                }
            }
        }

        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).isCross()) {
                for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                    if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    }
                }
            }
        }
    }

    private static void coopHpFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() > leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                            if (monster.getTypes().contains(leaderSkill.getAtkType().get(j).getValue()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getAtkElement().size(); j++) {
                            if ((leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int()) && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            }
        }

        if (Singleton.getInstance().isCoopEnable()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
        }
    }

    private static void coopFlat(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        boolean buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }

        if (Singleton.getInstance().isCoopEnable()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
        }
    }

    private static void coop(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int stat) {
        if (Singleton.getInstance().isCoopEnable()) {
            if (stat == 2) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
            } else if (stat == 3) {
                rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
            }
        }
    }

    private static void flatCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).isCross()) {
                for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                    if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    }
                }
            }
        }

        boolean buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (monster.getTypes().contains(leaderSkill.getAtkType().get(i).getValue()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if ((leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int()) && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
    }

    private static void heartCrossCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        for (int i = 0; i < orbMatchList.size(); i++) {
            if (orbMatchList.get(i).isCross()) {
                for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                    if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    }
                }
            }
        }

        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
    }

    private static void matchElementIndian(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int counter = 0;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        int comboDiff2 = leaderSkill.getComboMax2() - leaderSkill.getComboMin2();
        int counter2 = 0;
        ArrayList<Element> orbMatchElements = OrbMatchUtil.getAllOrbMatchElements(orbMatchList);
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements2().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements2().get(j).getElement())) {
                    orbMatchElements.remove(i);
                    j++;
                    counter2++;
                    i = -1;
                }
            }
        }

        if (counter2 >= leaderSkill.getComboMax2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter2 >= leaderSkill.getComboMin2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }

    }private static void orbLinkOrbLink(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        int comboDiff2 = leaderSkill.getComboMax2() - leaderSkill.getComboMin2();
        int counter2 = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements2().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements2().get(j).getElement())) {
                    if (counter2 < orbMatchList.get(i).getOrbsLinked()) {
                        counter2 = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter2 >= leaderSkill.getComboMax2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter2 >= leaderSkill.getComboMin2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }
    }

    private static void orbLinkHeartCross(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getElement())) {
                    if (counter < orbMatchList.get(i).getOrbsLinked()) {
                        counter = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        if (Singleton.getInstance().isHeartCarryOver()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
        } else {
            Boolean matched = false;
            int i = 0;
            while (!matched) {
                if (orbMatchList.get(i).getElement() == Element.HEART) {
                    if (orbMatchList.get(i).isCross()) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        matched = true;
                    }
                }
                i++;
                if (i == orbMatchList.size()) {
                    break;
                }
            }
        }
    }

    private static void comboOrbLink(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList, int totalCombos) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
        }

        int comboDiff2 = leaderSkill.getComboMax2() - leaderSkill.getComboMin2();
        int counter2 = 0;
        for (int i = 0; i < orbMatchList.size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements2().size(); j++) {
                if (orbMatchList.get(i).getElement().equals(leaderSkill.getMatchElements2().get(j).getElement())) {
                    if (counter2 < orbMatchList.get(i).getOrbsLinked()) {
                        counter2 = orbMatchList.get(i).getOrbsLinked();
                    }
                }
            }

        }
        if (counter2 >= leaderSkill.getComboMax2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter2 >= leaderSkill.getComboMin2()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }
    }

    private static void matchElementMonsterConditional(Monster monster, Team team, LeaderSkill leaderSkill, RealmResults<OrbMatch> orbMatchList) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getElement())) {
                counter++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter - leaderSkill.getComboMin()).getValue());
        }

        int counter2 = 0;
        for (int i = 0; i < leaderSkill.getMatchMonsters().size(); i++) {
            if (team.getBaseMonsterId().contains(leaderSkill.getMatchMonsters().get(i).getValue())) {
                counter2++;
            }
        }
        if (counter2 == leaderSkill.getMatchMonsters().size()) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getAtkData().size() - 1).getValue());
        }
    }

//    private double atkDataMax() {
//        double max = 1;
//        for (int i = 0; i < atkData.size(); i++) {
//            if (i == 0) {
//                max = atkData.get(i);
//            } else if (atkData.get(i) > atkData.get(i - 1)) {
//                max = atkData.get(i);
//            }
//        }
//        return max;
//    }
}
