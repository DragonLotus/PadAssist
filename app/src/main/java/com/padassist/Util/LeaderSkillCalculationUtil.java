package com.padassist.Util;

import android.util.Log;

import com.padassist.Data.Element;
import com.padassist.Data.Enemy;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.LeaderSkillType;
import com.padassist.Data.Monster;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.RealmInt;
import com.padassist.Data.Team;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;

public class LeaderSkillCalculationUtil {

    private static Realm realm = Realm.getDefaultInstance();
    private static double hpMultiplier;
    private static ArrayList<Double> atkMultiplier = new ArrayList<>();
    private static double rcvMultiplier;

    public static double hpMultiplier(Monster monster, Team team) {
        hpMultiplier = 1;
        Log.d("LeaderSkillCalcUtil", "Is leadSkill valid: " + team.getLeadSkill().isValid());
        if (team.getLeadSkill().getHpSkillType() != null) {
            switch (team.getLeadSkill().getHpSkillType().getValue()) {
                case FLAT:
                    flat(monster, team.getLeadSkill(), 1);
                    break;
                case MONSTER_CONDITIONAL:
                    monsterConditional(monster, team, team.getLeadSkill(), 1);
                    break;
            }
        }

        if (team.getHelperSkill().getHpSkillType() != null) {
            switch (team.getHelperSkill().getHpSkillType().getValue()) {
                case FLAT:
                    flat(monster, team.getHelperSkill(), 1);
                    break;
                case MONSTER_CONDITIONAL:
                    monsterConditional(monster, team, team.getHelperSkill(), 1);
                    break;
            }
        }

        return hpMultiplier;
    }

    public static ArrayList<Double> atkMultiplier(Monster monster, Team team, int totalCombos) {
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
        if (team.getLeadSkill().getAtkSkillType() != null) {
            switch (team.getLeadSkill().getAtkSkillType().getValue()) {
                case FLAT:
                    flat(monster, team.getLeadSkill(), 2);
                    break;
                case FLAT_ATTRIBUTE_ACTIVE_ATTRIBUTE:
                    flatAttributeActiveAttribute(monster, team, team.getLeadSkill());
                    break;
                case FLAT_TYPE_FLAT_ATTRIBUTE:
                    flatTypeFlatAttribute(monster, team, team.getLeadSkill());
                    break;
                case COMBO:
                    combo(monster, team, team.getLeadSkill(), totalCombos);
                    break;
                case MATCH_ELEMENT:
                    matchElement(monster, team, team.getLeadSkill());
                    break;
                case MONSTER_CONDITIONAL:
                    monsterConditional(monster, team, team.getLeadSkill(), 2);
                    break;
                case FLAT_MONSTER_CONDITIONAL:
                    flatMonsterConditional(monster, team, team.getLeadSkill());
                    break;
                case ORB_LINK:
                    orbLink(monster, team, team.getLeadSkill());
                    break;
                case ORB_LINK_FLAT:
                    orbLinkFlat(monster, team, team.getLeadSkill());
                    break;
                case ORB_LINK_INDIAN:
                    orbLinkIndian(monster, team, team.getLeadSkill());
                    break;
                case MATCH_ELEMENT_FLAT:
                    matchElementFlat(monster, team, team.getLeadSkill());
                    break;
                case COMBO_MATCH_ELEMENT:
                    comboMatchElement(monster, team, team.getLeadSkill(), totalCombos);
                    break;
                case MATCH_ELEMENT_ACTIVE:
                    matchElementActive(monster, team, team.getLeadSkill());
                    break;
                case FLAT_ACTIVE:
                    flatActive(monster, team, team.getLeadSkill());
                    break;
                case COMBO_ACTIVE:
                    comboActive(monster, team, team.getLeadSkill(), totalCombos);
                    break;
                case COMBO_FLAT:
                    comboFlat(monster, team, team.getLeadSkill(), totalCombos);
                    break;
                case COMBO_ATTRIBUTE:
                    comboAttribute(monster, team, team.getLeadSkill(), totalCombos);
                    break;
                case COMBO_EXACT:
                    comboExact(monster, team, team.getLeadSkill(), totalCombos);
                    break;
                case INDIAN:
                    indian(monster, team, team.getLeadSkill());
                    break;
                case INDIAN_FLAT:
                    indianFlat(monster, team, team.getLeadSkill());
                    break;
                case ORB_PLUS:
                    orbPlus(monster, team, team.getLeadSkill());
                    break;
                case ORB_PLUS_FLAT:
                    orbPlusFlat(monster, team, team.getLeadSkill());
                    break;
                case INDIAN_ORB_PLUS:
                    indianOrbPlus(monster, team, team.getLeadSkill());
                    break;
                case MATCH_ELEMENT_ORB_PLUS:
                    matchElementOrbPlus(monster, team, team.getLeadSkill());
                    break;
                case COMBO_ORB_PLUS:
                    comboOrbPlus(monster, team, team.getLeadSkill(), totalCombos);
                    break;
                case GRIMOIRE_FLAT:
                    multiFlat(monster, team, team.getLeadSkill());
                    break;
                case HP_FLAT:
                    hpFlat(monster, team, team.getLeadSkill(), 2);
                    break;
                case FLAT_HP_FLAT:
                    flatHpFlat(monster, team, team.getLeadSkill());
                    break;
                case HP_FLAT_ATTRIBUTE_FLAT_TYPE:
                    hpFlatAttributeFlatType(monster, team, team.getLeadSkill());
                    break;
                case ACTIVE:
                    active(monster, team, team.getLeadSkill(), 2);
                    break;
                case FLAT_TYPE_ACTIVE_ATTRIBUTE:
                    flatTypeActiveAttribute(monster, team, team.getLeadSkill());
                    break;
//                case ORB_LINK_EXACT_FLAT:
//                    orbLinkExactFlat(monster, team);
//                    break;
//                case ORB_LINK_EXACT:
//                    orbLinkExact(monster, team);
//                    break;
                case ORB_LINK_ORB_PLUS:
                    orbLinkOrbPlus(monster, team, team.getLeadSkill());
                    break;
                case MATCH_ELEMENT_ORB_LINK:
                    matchElementOrbLink(monster, team, team.getLeadSkill());
                    break;
//                case FLAT_TYPE_FLAT_TYPE:
//                    flatTypeFlatType(monster, team);
//                    break;
//                case HP_FLAT_ORB_PLUS:
//                    hpFlatOrbPlus(monster, team);
//                    break;
//                case HP_FLAT_MATCH_ELEMENT:
//                    hpFlatMatchElement(monster, team);
//                    break;
//                case HP_FLAT_ACTIVE:
//                    hpFlatActive(monster, team);
//                    break;
//                case ORB_LINK_ACTIVE:
//                    orbLinkActive(monster, team);
//                    break;
//                case HP_FLAT_ATTRIBUTE_FLAT_ATTRIBUTE:
//                    hpFlatAttributeFlatAttribute(monster, team);
//                    break;
//                case INDIAN_ACTIVE:
//                    indianActive(monster, team);
//                    break;
//                case HEART_CROSS:
//                    heartCross(monster, team);
//                    break;
//                case COMBO_INDIAN:
//                    comboIndian(monster, team, totalCombos);
//                    break;
//                case ORB_LINK_HP_FLAT:
//                    orbLinkHpFlat(monster, team);
//                    break;
//                case ORB_PLUS_HEART_CROSS:
//                    orbPlusHeartCross(monster, team);
//                    break;
//                case INDIAN_HEART_CROSS:
//                    indianHeartCross(monster, team);
//                    break;
//                case FLAT_HEART_CROSS:
//                    flatHeartCross(monster, team);
//                    break;
//                case MATCH_ELEMENT_HEART_CROSS:
//                    matchElementHeartCross(monster, team);
//                    break;
//                case ACTIVE_HEART_CROSS:
//                    activeHeartCross(monster, team);
//                    break;
//                case INDIAN_MONSTER_CONDITIONAL:
//                    indianMonsterConditional(monster, team);
//                    break;
//                case ORB_PLUS_MONSTER_CONDITIONAL:
//                    orbPlusMonsterConditional(monster, team);
//                    break;
//                case CROSS:
//                    cross(monster, team);
//                    break;
//                case INDIAN_CROSS:
//                    indianCross(monster, team);
//                    break;
//                case ACTIVE_CROSS:
//                    activeCross(monster, team);
//                    break;
//                case CO_OP_HP_FLAT:
//                    coopHpFlat(monster, team);
//                    break;
//                case CO_OP_FLAT:
//                    coopFlat(monster, team);
//                    break;
                case CO_OP:
                    coop(monster, team, team.getLeadSkill(), 2);
                    break;
            }
        }
        return atkMultiplier;
    }

    public static double rcvMultiplier(Monster monster, Team team) {
        rcvMultiplier = 1;

        if (team.getLeadSkill().getRcvSkillType() != null) {
            switch (team.getLeadSkill().getRcvSkillType().getValue()) {
                case FLAT:
                    flat(monster, team.getLeadSkill(), 3);
                    break;
                case MONSTER_CONDITIONAL:
                    monsterConditional(monster, team, team.getLeadSkill(), 3);
                    break;
                case HP_FLAT:
                    hpFlat(monster, team, team.getLeadSkill(), 3);
                    break;
                case ACTIVE:
                    active(monster, team, team.getLeadSkill(), 3);
                    break;
                case CO_OP:
                    coop(monster, team, team.getLeadSkill(), 3);
                    break;
            }
        }
        if (team.getHelperSkill().getRcvSkillType() != null) {
            switch (team.getHelperSkill().getRcvSkillType().getValue()) {
                case FLAT:
                    flat(monster, team.getHelperSkill(), 3);
                    break;
                case MONSTER_CONDITIONAL:
                    monsterConditional(monster, team, team.getHelperSkill(), 3);
                    break;
                case HP_FLAT:
                    hpFlat(monster, team, team.getHelperSkill(), 3);
                    break;
                case ACTIVE:
                    active(monster, team, team.getHelperSkill(), 3);
                    break;
                case CO_OP:
                    coop(monster, team, team.getHelperSkill(), 3);
                    break;
            }
        }
        return rcvMultiplier;
    }

    private static void flat(Monster monster, LeaderSkill leaderSkill, int stat) {
        boolean buffed = false;
        switch (stat) {
            case 1:
                if (leaderSkill.getHpType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getHpType().size(); i++) {
                        if (leaderSkill.getHpType().get(i).getValue() == monster.getType1() || leaderSkill.getHpType().get(i).getValue() == monster.getType2() || leaderSkill.getHpType().get(i).getValue() == monster.getType3() && !buffed) {
                            hpMultiplier *= leaderSkill.getHpData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getHpElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getHpElement().size(); i++) {
                        if (leaderSkill.getHpElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getHpElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                            hpMultiplier *= leaderSkill.getHpData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                break;
            case 2:
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
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
                        if (leaderSkill.getRcvType().get(i).getValue() == monster.getType1() || leaderSkill.getRcvType().get(i).getValue() == monster.getType2() || leaderSkill.getRcvType().get(i).getValue() == monster.getType3() && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getRcvElement().size(); i++) {
                        if (leaderSkill.getRcvElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                break;
        }
    }

    private static void flatAttributeActiveAttribute(Monster monster, Team team, LeaderSkill leaderSkill) {
        //All attributes on the active skill used so yea. Cheating.
        boolean buffed = false;
        if (leaderSkill.getAtkElement().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
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
                    if (leaderSkill.getAtkElement2().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement2().get(i).getValue() == monster.getElement2Int() && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        buffed = true;
                    }
                }
            }
        }
    }

    private static void flatTypeFlatAttribute(Monster monster, Team team, LeaderSkill leaderSkill) {
        boolean buffed = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        buffed = false;
        if (leaderSkill.getAtkElement().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    buffed = true;
                }
            }
        }
    }

    private static void combo(Monster monster, Team team, LeaderSkill leaderSkill, int totalCombos) {
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

    private static void matchElement(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Kirin, Horus, Ra
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getValue())) {
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

    private static void monsterConditional(Monster monster, Team team, LeaderSkill leaderSkill, int stat) {
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


    private static void flatMonsterConditional(Monster monster, Team team, LeaderSkill leaderSkill) {
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
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
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

    private static void orbLink(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Heroes
        //matchElements is the elements you can link, 2 for beach pandora and such.
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (team.getOrbMatches().get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getValue())) {
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
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

    private static void orbLinkFlat(Monster monster, Team team, LeaderSkill leaderSkill) {
        //{Combos, flat}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (team.getOrbMatches().get(i).getElement().equals(leaderSkill.getMatchElements().get(j))) {
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
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

    private static void orbLinkIndian(Monster monster, Team team, LeaderSkill leaderSkill) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (team.getOrbMatches().get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getValue())) {
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
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
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements2().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements2().get(j).getValue())) {
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

    private static void matchElementFlat(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Sonia Gran.
        // atkdata is {match element multipliers, flat multiplier is last}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getValue())) {
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

    private static void comboMatchElement(Monster monster, Team team, LeaderSkill leaderSkill, int totalCombos) {
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
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getValue())) {
                counter2++;
            }
        }

        if (counter >= leaderSkill.getComboMax()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff2 + comboDiff + 1).getValue());
        } else if (counter >= leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter2 - leaderSkill.getComboMin2() + comboDiff + 1).getValue());
        }
    }

    private static void matchElementActive(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Need active flag in team
        //Awoken Ra, Awoken Horus, Green Kirin
        //atkdata is {match elements first, additional damage with skill last}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getValue())) {
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

    private static void flatActive(Monster monster, Team team, LeaderSkill leaderSkill) {
        //atkData will be {flat, active}
        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
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
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed = true;
                }
            }
        }

    }

    private static void comboActive(Monster monster, Team team, LeaderSkill leaderSkill, int totalCombos) {
        //Awoken Anubis, Awoken Bastet
        //{Combos, active}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkType().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                    if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                        buffed = true;
                    }
                }
            }
            if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
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

    private static void comboFlat(Monster monster, Team team, LeaderSkill leaderSkill, int totalCombos) {
        //Ronia ult
        //{combo multiplier, flat multiplier}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
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

    private static void comboAttribute(Monster monster, Team team, LeaderSkill leaderSkill, int totalCombos) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed = false;

        int counter = totalCombos - leaderSkill.getComboMin();
        if (counter >= comboDiff) {
            if (leaderSkill.getAtkElement().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff).getValue());
                        buffed = true;
                    }
                }
            }
        } else if (totalCombos >= leaderSkill.getComboMin()) {
            if (leaderSkill.getAtkElement().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                        atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(counter).getValue());
                        atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(counter).getValue());
                        buffed = true;
                    }
                }
            }
        }
    }

    private static void comboExact(Monster monster, Team team, LeaderSkill leaderSkill, int totalCombos) {
        if (totalCombos == leaderSkill.getComboMin()) {
            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
        }
    }

    private static void indian(Monster monster, Team team, LeaderSkill leaderSkill) {
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getValue())) {
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

    private static void indianFlat(Monster monster, Team team, LeaderSkill leaderSkill) {
        //matchElement will be {fire, fire, fire} or something for Krishna
        //comboMin = 2, comboMax = 3 for Krishna. 3/3 for Sarasvati. 3/4 for Zuoh.
        //Ruel. atkData {match multiplier, flat multiplier}
        boolean buffed = false;
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed = true;
                }
            }
        }

        int counter = 0;
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getValue())) {
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

    private static void orbPlus(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Akechi Mitsuhide
        //atkData will be one number
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed2 = true;
                }
            }
        }
    }

    private static void orbPlusFlat(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Not Sanada. Wot. Must've been a typo on PADx
        //atkData is {orbPlus multiplier, flat}
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                    buffed2 = true;
                }
            }
        }
        buffed1 = false;
        if (leaderSkill.getAtkType().size() != 0) {
            for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    buffed1 = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed1) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(1).getValue());
                    buffed1 = true;
                }
            }
        }
    }

    private static void indianOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Ult Krishna and ult Sarasvati
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed2 = true;
                }
            }
        }
        int counter = 0;
        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
        for (int i = 0, j = 0; j < leaderSkill.getMatchElements().size(); i++) {
            if (i == orbMatchElements.size()) {
                j++;
                i = -1;
            } else {
                if (orbMatchElements.get(i).equals(leaderSkill.getMatchElements().get(j).getValue())) {
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

    private static void matchElementOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Kite
        //atkData will be {match elements, orb plus multiplier}
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed2 = true;
                }
            }
        }

        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getValue())) {
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


    private static void comboOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill, int totalCombos) {
        //Awoken Yomi
        //atkData will be {combo multipliers, orb plus multiplier}
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        boolean buffed1 = false, buffed2 = false;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement()) && !buffed2) {
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

        private static void multiFlat(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Goetia? Not Goemon. I'll use this for Goetia and friends.
        //Damn I don't remember
        //atkdata will be {bigger multiplier, smaller multiplier}
        if (leaderSkill.getAtkType().size() != 0) {
            if (monster.getType1() == leaderSkill.getAtkType().get(0).getValue() || monster.getType2() == leaderSkill.getAtkType().get(0).getValue() || monster.getType3() == leaderSkill.getAtkType().get(0).getValue()) {
                if (monster.getType1() == leaderSkill.getAtkType().get(1).getValue() || monster.getType2() == leaderSkill.getAtkType().get(1).getValue() || monster.getType3() == leaderSkill.getAtkType().get(1).getValue()) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue() * leaderSkill.getAtkData().get(1).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue() * leaderSkill.getAtkData().get(1).getValue());
                } else {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                }
            } else if (monster.getType1() == leaderSkill.getAtkType().get(1).getValue() || monster.getType2() == leaderSkill.getAtkType().get(1).getValue() || monster.getType3() == leaderSkill.getAtkType().get(1).getValue()) {
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

    private static void hpFlat(Monster monster, Team team, LeaderSkill leaderSkill, int stat) {
        boolean buffed = false;
        if (stat == 2) {
            if (leaderSkill.getHpPercent().size() == 1) {
                if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                            if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                            if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                                buffed = true;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                    if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() >= leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                        if (leaderSkill.getAtkType().size() != 0) {
                            for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                                if (leaderSkill.getAtkType().get(j).getValue() == monster.getType1() || leaderSkill.getAtkType().get(j).getValue() == monster.getType2() || leaderSkill.getAtkType().get(j).getValue() == monster.getType3() && !buffed) {
                                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                    buffed = true;
                                }
                            }
                        }
                        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                            for (int j = 0; j < leaderSkill.getHpType().size(); j++) {
                                if (leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int() && !buffed) {
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
                            if (leaderSkill.getRcvType().get(i).getValue() == monster.getType1() || leaderSkill.getRcvType().get(i).getValue() == monster.getType2() || leaderSkill.getRcvType().get(i).getValue() == monster.getType3() && !buffed) {
                                rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                        for (int i = 0; i < leaderSkill.getRcvElement().size(); i++) {
                            if (leaderSkill.getRcvElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                                rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                                buffed = true;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                    if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() >= leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                        if (leaderSkill.getRcvType().size() != 0) {
                            for (int j = 0; j < leaderSkill.getRcvType().size(); j++) {
                                if (leaderSkill.getRcvType().get(j).getValue() == monster.getType1() || leaderSkill.getRcvType().get(j).getValue() == monster.getType2() || leaderSkill.getRcvType().get(j).getValue() == monster.getType3() && !buffed) {
                                    rcvMultiplier *= leaderSkill.getRcvData().get(i).getValue();
                                    buffed = true;
                                }
                            }
                        }
                        if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                            for (int j = 0; j < leaderSkill.getRcvElement().size(); j++) {
                                if (leaderSkill.getRcvElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(j).getValue() == monster.getElement2Int() && !buffed) {
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

    private static void flatHpFlat(Monster monster, Team team, LeaderSkill leaderSkill) {
        //{HP flats, flat}
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() >= leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {
                    if (leaderSkill.getAtkType().size() != 0) {
                        for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                            if (leaderSkill.getAtkType().get(j).getValue() == monster.getType1() || leaderSkill.getAtkType().get(j).getValue() == monster.getType2() || leaderSkill.getAtkType().get(j).getValue() == monster.getType3() && !buffed) {
                                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(i).getValue());
                                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(i).getValue());
                                buffed = true;
                            }
                        }
                    }
                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getHpType().size(); j++) {
                            if (leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int() && !buffed) {
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
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    buffed = true;
                }
            }
        }
        if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
            for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    buffed = true;
                }
            }
        }
    }
    private static void hpFlatAttributeFlatType(Monster monster, Team team, LeaderSkill leaderSkill) {
        //Goemon Ult
        boolean buffed = false;
        if (leaderSkill.getHpPercent().size() == 1) {
            if (team.getTeamHp() == leaderSkill.getHpPercent().get(0).getValue()) {
                if (leaderSkill.getAtkElement().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < leaderSkill.getHpPercent().size() / 2; i++) {
                if (team.getTeamHp() <= leaderSkill.getHpPercent().get(2 * i).getValue() && team.getTeamHp() >= leaderSkill.getHpPercent().get(1 + 2 * i).getValue()) {

                    if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                        for (int j = 0; j < leaderSkill.getHpType().size(); j++) {
                            if (leaderSkill.getAtkElement().get(j).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(j).getValue() == monster.getElement2Int() && !buffed) {
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
            for (int j = 0; j < leaderSkill.getAtkType().size(); j++) {
                if (leaderSkill.getAtkType().get(j).getValue() == monster.getType1() || leaderSkill.getAtkType().get(j).getValue() == monster.getType2() || leaderSkill.getAtkType().get(j).getValue() == monster.getType3() && !buffed) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(leaderSkill.getHpPercent().size() / 2).getValue());
                    buffed = true;
                }
            }
        }
    }

    private static void active(Monster monster, Team team, LeaderSkill leaderSkill, int stat) {
        if (Singleton.getInstance().isActiveSkillUsed()) {
            boolean buffed = false;
            if (stat == 2) {
                if (leaderSkill.getAtkType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getAtkType().size(); i++) {
                        if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getAtkElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                        if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                            atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                            atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
                            buffed = true;
                        }
                    }
                }
            } else if (stat == 3) {
                if (leaderSkill.getRcvType().size() != 0) {
                    for (int i = 0; i < leaderSkill.getRcvType().size(); i++) {
                        if (leaderSkill.getRcvType().get(i).getValue() == monster.getType1() || leaderSkill.getRcvType().get(i).getValue() == monster.getType2() || leaderSkill.getRcvType().get(i).getValue() == monster.getType3() && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
                if (leaderSkill.getRcvElement().size() != 0 && !buffed) {
                    for (int i = 0; i < leaderSkill.getRcvElement().size(); i++) {
                        if (leaderSkill.getRcvElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getRcvElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
                            rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
                            buffed = true;
                        }
                    }
                }
            }
        }
    }

    private static void flatTypeActiveAttribute(Monster monster, Team team, LeaderSkill leaderSkill) {
        boolean buffed = false;
        if (Singleton.getInstance().isActiveSkillUsed()) {
            if (leaderSkill.getAtkElement().size() != 0) {
                for (int i = 0; i < leaderSkill.getAtkElement().size(); i++) {
                    if (leaderSkill.getAtkElement().get(i).getValue() == monster.getElement1Int() || leaderSkill.getAtkElement().get(i).getValue() == monster.getElement2Int() && !buffed) {
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
                if (leaderSkill.getAtkType().get(i).getValue() == monster.getType1() || leaderSkill.getAtkType().get(i).getValue() == monster.getType2() || leaderSkill.getAtkType().get(i).getValue() == monster.getType3() && !buffed) {
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
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            for (int j = 0; j < matchElements.size(); j++) {
//                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
//                    if (team.getOrbMatches().get(i).getOrbsLinked() == comboMin) {
//                        counter = team.getOrbMatches().get(i).getOrbsLinked();
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
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            for (int j = 0; j < matchElements.size(); j++) {
//                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
//                    if (team.getOrbMatches().get(i).getOrbsLinked() == comboMin) {
//                        counter = team.getOrbMatches().get(i).getOrbsLinked();
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

    private static void orbLinkOrbPlus(Monster monster, Team team, LeaderSkill leaderSkill) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements().size(); j++) {
                if (team.getOrbMatches().get(i).getElement().equals(leaderSkill.getMatchElements().get(j).getValue())) {
                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter = team.getOrbMatches().get(i).getOrbsLinked();
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
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement()) && !buffed1) {
                    atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed1 = true;
                }
                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement()) && !buffed2) {
                    atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(comboDiff + 1).getValue());
                    buffed2 = true;
                }
            }
        }
    }

    private static void matchElementOrbLink(Monster monster, Team team, LeaderSkill leaderSkill) {
        int comboDiff = leaderSkill.getComboMax() - leaderSkill.getComboMin();
        int counter = 0;
        for (int i = 0; i < leaderSkill.getMatchElements().size(); i++) {
            if (team.getOrbMatchElements().contains(leaderSkill.getMatchElements().get(i).getValue())) {
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
        for (int i = 0; i < team.getOrbMatches().size(); i++) {
            for (int j = 0; j < leaderSkill.getMatchElements2().size(); j++) {
                if (team.getOrbMatches().get(i).getElement().equals(leaderSkill.getMatchElements2().get(j).getValue())) {
                    if (counter2 < team.getOrbMatches().get(i).getOrbsLinked()) {
                        counter2 = team.getOrbMatches().get(i).getOrbsLinked();
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

//    private void flatTypeFlatType(Monster monster, Team team) {
//        if (atkType.size() != 0) {
//            for (int i = 0; i < atkType.size(); i++) {
//                if (monster.getTypes().contains(atkType.get(i))) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        }
//        if (atkType2.size() != 0) {
//            for (int i = 0; i < atkType2.size(); i++) {
//                if (monster.getTypes().contains(atkType.get(i))) {
//                    atkElement1Multiplier *= atkData.get(1);
//                    atkElement2Multiplier *= atkData.get(1);
//                }
//            }
//        }
//    }
//
//    private void hpFlatOrbPlus(Monster monster, Team team) {
//        if (hpPercent.size() == 1) {
//            if (team.getTeamHp() == hpPercent.get(0)) {
//                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        } else {
//            for (int i = 0; i < hpPercent.size() / 2; i++) {
//                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
//                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            if (team.getOrbMatches().get(i).getNumOrbPlus() >= 1 && team.getOrbMatches().get(i).getOrbsLinked() == 5) {
//                if (monster.getElement1().equals(team.getOrbMatches().get(i).getElement())) {
//                    atkElement1Multiplier *= atkData.get(atkData.size() - 1);
//                }
//                if (monster.getElement2().equals(team.getOrbMatches().get(i).getElement())) {
//                    atkElement2Multiplier *= atkData.get(atkData.size() - 1);
//                }
//            }
//        }
//    }
//
//    public void hpFlatMatchElement(Monster monster, Team team) {
//        if (hpPercent.size() == 1) {
//            if (team.getTeamHp() == hpPercent.get(0)) {
//                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        } else {
//            for (int i = 0; i < hpPercent.size() / 2; i++) {
//                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
//                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    }
//                }
//            }
//        }
//
//        int comboDiff = comboMax - comboMin;
//        int counter = 0;
//        for (int i = 0; i < matchElements.size(); i++) {
//            if (team.getOrbMatchElements().contains(matchElements.get(i))) {
//                counter++;
//            }
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier *= atkData.get(comboDiff + hpPercent.size() / 2);
//            atkElement2Multiplier *= atkData.get(comboDiff + hpPercent.size() / 2);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier *= atkData.get(counter - comboMin + hpPercent.size() / 2);
//            atkElement2Multiplier *= atkData.get(counter - comboMin + hpPercent.size() / 2);
//        }
//
//    }
//
//    public void hpFlatActive(Monster monster, Team team) {
//        if (hpPercent.size() == 1) {
//            if (team.getTeamHp() == hpPercent.get(0)) {
//                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        } else {
//            for (int i = 0; i < hpPercent.size() / 2; i++) {
//                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
//                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    }
//                }
//            }
//        }
//
//        if (team.isActiveSkillUsed()) {
//            if (atkType.size() != 0) {
//                for (int i = 0; i < atkType.size(); i++) {
//                    if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
//                        atkElement1Multiplier *= atkData.get(atkData.size() - 1);
//                        atkElement2Multiplier *= atkData.get(atkData.size() - 1);
//                    }
//                }
//            }
//            if (atkElement.size() != 0) {
//                for (int i = 0; i < atkElement.size(); i++) {
//                    if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
//                        atkElement1Multiplier *= atkData.get(atkData.size() - 1);
//                        atkElement2Multiplier *= atkData.get(atkData.size() - 1);
//                    }
//                }
//            }
//        }
//    }
//
//    private void orbLinkActive(Monster monster, Team team) {
//        int comboDiff = comboMax - comboMin;
//        int counter = 0;
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            for (int j = 0; j < matchElements.size(); j++) {
//                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
//                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
//                        counter = team.getOrbMatches().get(i).getOrbsLinked();
//                    }
//                }
//            }
//
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier = atkData.get(comboDiff);
//            atkElement2Multiplier = atkData.get(comboDiff);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier = atkData.get(counter - comboMin);
//            atkElement2Multiplier = atkData.get(counter - comboMin);
//        }
//        if (team.isActiveSkillUsed()) {
//            if (atkElement.size() != 0) {
//                for (int i = 0; i < atkElement.size(); i++) {
//                    if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
//                        atkElement1Multiplier *= atkData.get(atkData.size() - 1);
//                        atkElement2Multiplier *= atkData.get(atkData.size() - 1);
//                    }
//                }
//            }
//        }
//    }
//
//    private void hpFlatAttributeFlatAttribute(Monster monster, Team team) {
//        //Dmeta
//        if (hpPercent.size() == 1) {
//            if (team.getTeamHp() == hpPercent.get(0)) {
//                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        } else {
//            for (int i = 0; i < hpPercent.size() / 2; i++) {
//                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
//                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                        if (atkElement1Multiplier < atkData.get(i)) {
//                            atkElement1Multiplier = atkData.get(i);
//                        }
//                        if (atkElement2Multiplier < atkData.get(i)) {
//                            atkElement2Multiplier = atkData.get(i);
//                        }
//                    }
//                }
//            }
//        }
//        if (atkElement2.contains(monster.getElement1Int()) || atkElement2.contains(monster.getElement2Int())) {
//            atkElement1Multiplier *= atkData.get(hpPercent.size() / 2);
//            atkElement2Multiplier *= atkData.get(hpPercent.size() / 2);
//        }
//    }
//
//    private void indianActive(Monster monster, Team team) {
//        //Awoken Sun Quan
//        int comboDiff = comboMax - comboMin;
//        int counter = 0;
//        if (team.isActiveSkillUsed()) {
//            if (atkType.size() != 0) {
//                for (int i = 0; i < atkType.size(); i++) {
//                    if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
//                        atkElement1Multiplier = atkData.get(comboDiff + 1);
//                        atkElement2Multiplier = atkData.get(comboDiff + 1);
//                    }
//                }
//            }
//            if (atkElement.size() != 0) {
//                for (int i = 0; i < atkElement.size(); i++) {
//                    if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
//                        atkElement1Multiplier = atkData.get(comboDiff + 1);
//                        atkElement2Multiplier = atkData.get(comboDiff + 1);
//                    }
//                }
//            }
//        }
//        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
//        for (int i = 0, j = 0; j < matchElements.size(); i++) {
//            if (i == orbMatchElements.size()) {
//                j++;
//                i = -1;
//            } else {
//                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
//                    orbMatchElements.remove(i);
//                    j++;
//                    counter++;
//                    i = -1;
//                }
//            }
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier *= atkData.get(comboDiff);
//            atkElement2Multiplier *= atkData.get(comboDiff);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier *= atkData.get(counter - comboMin);
//            atkElement2Multiplier *= atkData.get(counter - comboMin);
//        }
//    }
//
//    private void heartCross(Monster monster, Team team) {
//        Boolean matched = false;
//        int i = 0;
//        while (!matched) {
//            if (team.getOrbMatches().get(i).getElement() == Element.HEART) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                    matched = true;
//                }
//            }
//            i++;
//            if (i == team.getOrbMatches().size()) {
//                break;
//            }
//        }
//    }
//
//    private void comboIndian(Monster monster, Team team, int totalCombos) {
//        int comboDiff = comboMax - comboMin;
//        int counter = totalCombos - comboMin;
//        if (counter >= comboDiff) {
//            atkElement1Multiplier = atkData.get(comboDiff);
//            atkElement2Multiplier = atkData.get(comboDiff);
//        } else if (totalCombos >= comboMin) {
//            atkElement1Multiplier = atkData.get(counter);
//            atkElement2Multiplier = atkData.get(counter);
//        }
//
//        int comboDiff2 = comboMax2 - comboMin2;
//        int counter2 = 0;
//        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
//        for (int i = 0, j = 0; j < matchElements2.size(); i++) {
//            if (i == orbMatchElements.size()) {
//                j++;
//                i = -1;
//            } else {
//                if (orbMatchElements.get(i).equals(matchElements2.get(j))) {
//                    orbMatchElements.remove(i);
//                    j++;
//                    counter2++;
//                    i = -1;
//                }
//            }
//        }
//
//        if (counter2 >= comboMax2) {
//            atkElement1Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
//            atkElement2Multiplier *= atkData.get(comboDiff2 + comboDiff + 1);
//        } else if (counter2 >= comboMin2) {
//            atkElement1Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
//            atkElement2Multiplier *= atkData.get(counter2 - comboMin2 + comboDiff + 1);
//        }
//
//    }
//
//    private void orbLinkHpFlat(Monster monster, Team team) {
//        int comboDiff = comboMax - comboMin;
//        if (hpPercent.size() == 1) {
//            if (team.getTeamHp() == hpPercent.get(0)) {
//                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                    atkElement1Multiplier = atkData.get(comboDiff + 1);
//                    atkElement2Multiplier = atkData.get(comboDiff + 1);
//                } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
//                    atkElement1Multiplier = atkData.get(comboDiff + 1);
//                    atkElement2Multiplier = atkData.get(comboDiff + 1);
//                }
//            }
//        } else {
//            for (int i = 0; i < hpPercent.size() / 2; i++) {
//                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
//                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                        atkElement1Multiplier = atkData.get(comboDiff + 1 + i);
//                        atkElement2Multiplier = atkData.get(comboDiff + 1 + i);
//                    } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
//                        atkElement1Multiplier = atkData.get(comboDiff + 1 + i);
//                        atkElement2Multiplier = atkData.get(comboDiff + 1 + i);
//                    }
//                }
//            }
//        }
//        int counter = 0;
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            for (int j = 0; j < matchElements.size(); j++) {
//                if (team.getOrbMatches().get(i).getElement().equals(matchElements.get(j))) {
//                    if (counter < team.getOrbMatches().get(i).getOrbsLinked()) {
//                        counter = team.getOrbMatches().get(i).getOrbsLinked();
//                    }
//                }
//            }
//
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier *= atkData.get(comboDiff);
//            atkElement2Multiplier *= atkData.get(comboDiff);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier *= atkData.get(counter - comboMin);
//            atkElement2Multiplier *= atkData.get(counter - comboMin);
//        }
//    }
//
//    private void orbPlusHeartCross(Monster monster, Team team) {
//        Boolean matched = false;
//        int i = 0;
//        while (!matched) {
//            if (team.getOrbMatches().get(i).getElement() == Element.HEART) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier = atkData.get(1);
//                    atkElement2Multiplier = atkData.get(1);
//                    matched = true;
//                }
//            }
//            i++;
//            if (i == team.getOrbMatches().size()) {
//                break;
//            }
//        }
//        for (int j = 0; j < team.getOrbMatches().size(); j++) {
//            if (team.getOrbMatches().get(j).getNumOrbPlus() >= 1 && team.getOrbMatches().get(j).getOrbsLinked() == 5) {
//                if (monster.getElement1().equals(team.getOrbMatches().get(j).getElement())) {
//                    atkElement1Multiplier *= atkData.get(0);
//                }
//                if (monster.getElement2().equals(team.getOrbMatches().get(j).getElement())) {
//                    atkElement2Multiplier *= atkData.get(0);
//                }
//            }
//        }
//    }
//
//    private void indianHeartCross(Monster monster, Team team) {
//        int comboDiff = comboMax - comboMin;
//        int counter = 0;
//        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
//        for (int i = 0, j = 0; j < matchElements.size(); i++) {
//            if (i == orbMatchElements.size()) {
//                j++;
//                i = -1;
//            } else {
//                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
//                    orbMatchElements.remove(i);
//                    j++;
//                    counter++;
//                    i = -1;
//                }
//            }
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier = atkData.get(comboDiff);
//            atkElement2Multiplier = atkData.get(comboDiff);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier = atkData.get(counter - comboMin);
//            atkElement2Multiplier = atkData.get(counter - comboMin);
//        }
//
//        Boolean matched = false;
//        int i = 0;
//        while (!matched) {
//            if (team.getOrbMatches().get(i).getElement() == Element.HEART) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier *= atkData.get(comboDiff + 1);
//                    atkElement2Multiplier *= atkData.get(comboDiff + 1);
//                    matched = true;
//                }
//            }
//            i++;
//            if (i == team.getOrbMatches().size()) {
//                break;
//            }
//        }
//    }
//
//    private void flatHeartCross(Monster monster, Team team) {
//        if (atkType.size() != 0) {
//            for (int i = 0; i < atkType.size(); i++) {
//                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        }
//        if (atkElement.size() != 0) {
//            for (int i = 0; i < atkElement.size(); i++) {
//                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        }
//
//        Boolean matched = false;
//        int i = 0;
//        while (!matched) {
//            if (team.getOrbMatches().get(i).getElement() == Element.HEART) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier *= atkData.get(1);
//                    atkElement2Multiplier *= atkData.get(1);
//                    matched = true;
//                }
//            }
//            i++;
//            if (i == team.getOrbMatches().size()) {
//                break;
//            }
//        }
//    }
//
//    private void matchElementHeartCross(Monster monster, Team team) {
//        int comboDiff = comboMax - comboMin;
//        int counter = 0;
//        for (int i = 0; i < matchElements.size(); i++) {
//            if (team.getOrbMatchElements().contains(matchElements.get(i))) {
//                counter++;
//            }
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier *= atkData.get(comboDiff);
//            atkElement2Multiplier *= atkData.get(comboDiff);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier *= atkData.get(counter - comboMin);
//            atkElement2Multiplier *= atkData.get(counter - comboMin);
//        }
//
//        Boolean matched = false;
//        int i = 0;
//        while (!matched) {
//            if (team.getOrbMatches().get(i).getElement() == Element.HEART) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier *= atkData.get(comboDiff + 1);
//                    atkElement2Multiplier *= atkData.get(comboDiff + 1);
//                    matched = true;
//                }
//            }
//            i++;
//            if (i == team.getOrbMatches().size()) {
//                break;
//            }
//        }
//    }
//
//    private void activeHeartCross(Monster monster, Team team) {
//        if (team.isActiveSkillUsed()) {
//            if (atkType.size() != 0) {
//                for (int i = 0; i < atkType.size(); i++) {
//                    if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
//                        atkElement1Multiplier = atkData.get(0);
//                        atkElement2Multiplier = atkData.get(0);
//                    }
//                }
//            }
//            if (atkElement.size() != 0) {
//                for (int i = 0; i < atkElement.size(); i++) {
//                    if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
//                        atkElement1Multiplier = atkData.get(0);
//                        atkElement2Multiplier = atkData.get(0);
//                    }
//                }
//            }
//        }
//
//        Boolean matched = false;
//        int i = 0;
//        while (!matched) {
//            if (team.getOrbMatches().get(i).getElement() == Element.HEART) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier *= atkData.get(1);
//                    atkElement2Multiplier *= atkData.get(1);
//                    matched = true;
//                }
//            }
//            i++;
//            if (i == team.getOrbMatches().size()) {
//                break;
//            }
//        }
//    }
//
//    private void indianMonsterConditional(Monster monster, Team team) {
//        int comboDiff = comboMax - comboMin;
//        int counter = 0;
//        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
//        for (int i = 0, j = 0; j < matchElements.size(); i++) {
//            if (i == orbMatchElements.size()) {
//                j++;
//                i = -1;
//            } else {
//                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
//                    orbMatchElements.remove(i);
//                    j++;
//                    counter++;
//                    i = -1;
//                }
//            }
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier = atkData.get(comboDiff);
//            atkElement2Multiplier = atkData.get(comboDiff);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier = atkData.get(counter - comboMin);
//            atkElement2Multiplier = atkData.get(counter - comboMin);
//        }
//
//        int counter2 = 0;
//        for (int i = 0; i < matchMonsters.size(); i++) {
//            if (team.getBaseMonsterId().contains(matchMonsters.get(i))) {
//                counter2++;
//            }
//        }
//        if (counter2 == matchMonsters.size()) {
//            atkElement1Multiplier *= atkData.get(comboDiff + 1);
//            atkElement2Multiplier *= atkData.get(comboDiff + 1);
//        }
//    }
//
//    private void orbPlusMonsterConditional(Monster monster, Team team) {
//        int counter = 0;
//        for (int i = 0; i < matchMonsters.size(); i++) {
//            if (team.getBaseMonsterId().contains(matchMonsters.get(i))) {
//                counter++;
//            }
//        }
//        if (counter == matchMonsters.size()) {
//            atkElement1Multiplier = atkData.get(1);
//            atkElement2Multiplier = atkData.get(1);
//        }
//        for (int j = 0; j < team.getOrbMatches().size(); j++) {
//            if (team.getOrbMatches().get(j).getNumOrbPlus() >= 1 && team.getOrbMatches().get(j).getOrbsLinked() == 5) {
//                if (monster.getElement1().equals(team.getOrbMatches().get(j).getElement())) {
//                    atkElement1Multiplier *= atkData.get(0);
//                }
//                if (monster.getElement2().equals(team.getOrbMatches().get(j).getElement())) {
//                    atkElement2Multiplier *= atkData.get(0);
//                }
//            }
//        }
//    }
//
//    private void cross(Monster monster, Team team) {
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            if (matchElements.contains(team.getOrbMatches().get(i).getElement())) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier *= atkData.get(0);
//                    atkElement2Multiplier *= atkData.get(0);
//                }
//            }
//        }
//    }
//
//    private void indianCross(Monster monster, Team team) {
//        int comboDiff = comboMax - comboMin;
//        int counter = 0;
//        ArrayList<Element> orbMatchElements = team.getAllOrbMatchElements();
//        for (int i = 0, j = 0; j < matchElements.size(); i++) {
//            if (i == orbMatchElements.size()) {
//                j++;
//                i = -1;
//            } else {
//                if (orbMatchElements.get(i).equals(matchElements.get(j))) {
//                    orbMatchElements.remove(i);
//                    j++;
//                    counter++;
//                    i = -1;
//                }
//            }
//        }
//        if (counter >= comboMax) {
//            atkElement1Multiplier = atkData.get(comboDiff);
//            atkElement2Multiplier = atkData.get(comboDiff);
//        } else if (counter >= comboMin) {
//            atkElement1Multiplier = atkData.get(counter - comboMin);
//            atkElement2Multiplier = atkData.get(counter - comboMin);
//        }
//
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            if (matchElements.contains(team.getOrbMatches().get(i).getElement())) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier *= atkData.get(comboDiff + 1);
//                    atkElement2Multiplier *= atkData.get(comboDiff + 1);
//                }
//            }
//        }
//    }
//
//    private void activeCross(Monster monster, Team team) {
//        if (team.isActiveSkillUsed()) {
//            if (atkType.size() != 0) {
//                for (int i = 0; i < atkType.size(); i++) {
//                    if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
//                        atkElement1Multiplier *= atkData.get(0);
//                        atkElement2Multiplier *= atkData.get(0);
//                    }
//                }
//            }
//            if (atkElement.size() != 0) {
//                for (int i = 0; i < atkElement.size(); i++) {
//                    if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
//                        atkElement1Multiplier *= atkData.get(0);
//                        atkElement2Multiplier *= atkData.get(0);
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < team.getOrbMatches().size(); i++) {
//            if (matchElements.contains(team.getOrbMatches().get(i).getElement())) {
//                if (team.getOrbMatches().get(i).isCross()) {
//                    atkElement1Multiplier *= atkData.get(1);
//                    atkElement2Multiplier *= atkData.get(1);
//                }
//            }
//        }
//    }
//
//    private void coopHpFlat(Monster monster, Team team) {
//
//        if (hpPercent.size() == 1) {
//            if (team.getTeamHp() == hpPercent.get(0)) {
//                if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2())) {
//                    atkElement1Multiplier = atkData.get(0);
//                    atkElement2Multiplier = atkData.get(0);
//                }
//            }
//        } else {
//            for (int i = 0; i < hpPercent.size() / 2; i++) {
//                if (team.getTeamHp() <= hpPercent.get(0 + 2 * i) && team.getTeamHp() >= hpPercent.get(1 + 2 * i)) {
//                    if (atkElement.contains(monster.getElement1Int()) || atkElement.contains(monster.getElement2Int())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    } else if (atkType.contains(monster.getType1()) || atkType.contains(monster.getType2()) || atkType.contains(monster.getType3())) {
//                        atkElement1Multiplier = atkData.get(i);
//                        atkElement2Multiplier = atkData.get(i);
//                    }
//                }
//            }
//        }
//
//        if (Singleton.getInstance().isCoopEnable()) {
//            atkElement1Multiplier *= atkData.get(atkData.size() - 1);
//            atkElement2Multiplier *= atkData.get(atkData.size() - 1);
//        }
//    }
//
//    private void coopFlat(Monster monster, Team team) {
//        if (atkType.size() != 0) {
//            for (int i = 0; i < atkType.size(); i++) {
//                if (monster.getType1() == atkType.get(i) || monster.getType2() == atkType.get(i) || monster.getType3() == atkType.get(i)) {
//                    atkElement1Multiplier *= atkData.get(0);
//                    atkElement2Multiplier *= atkData.get(0);
//                    break;
//                }
//            }
//        }
//        if (atkElement.size() != 0) {
//            for (int i = 0; i < atkElement.size(); i++) {
//                if (monster.getElement1Int() == atkElement.get(i) || monster.getElement2Int() == atkElement.get(i)) {
//                    atkElement1Multiplier *= atkData.get(0);
//                    atkElement2Multiplier *= atkData.get(0);
//                    break;
//                }
//            }
//        }
//
//        if (Singleton.getInstance().isCoopEnable()) {
//            atkElement1Multiplier *= atkData.get(atkData.size() - 1);
//            atkElement2Multiplier *= atkData.get(atkData.size() - 1);
//        }
//    }

    private static void coop(Monster monster, Team team, LeaderSkill leaderSkill, int stat) {
        if (Singleton.getInstance().isCoopEnable()) {
            if (stat == 2) {
                atkMultiplier.set(0, atkMultiplier.get(0) * leaderSkill.getAtkData().get(0).getValue());
                atkMultiplier.set(1, atkMultiplier.get(1) * leaderSkill.getAtkData().get(0).getValue());
            } else if (stat == 3) {
                rcvMultiplier *= leaderSkill.getRcvData().get(0).getValue();
            }
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
