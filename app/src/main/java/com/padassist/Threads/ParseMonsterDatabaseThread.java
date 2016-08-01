package com.padassist.Threads;

import com.activeandroid.ActiveAndroid;
import com.padassist.BuildConfig;
import com.padassist.Constants;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Element;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.LeaderSkillType;
import com.padassist.R;
import com.padassist.Util.SharedPreferencesUtil;
import com.padassist.Util.Singleton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * Created by Thomas on 11/22/2015.
 */
public class ParseMonsterDatabaseThread extends Thread {

    private UpdateProgress update;
    int counter = 0;

    public interface UpdateProgress {
        public void updateValues(int counter);

    }

    public ParseMonsterDatabaseThread() {
    }

    public ParseMonsterDatabaseThread(UpdateProgress update) {
        this.update = update;
    }

    public void run() {
        super.run();

        parseMonsterDatabase();
        parseLeaderSkillDatabase();

        SharedPreferencesUtil.savePreferences(Constants.VERSION, BuildConfig.VERSION_CODE);
    }

    private void parseMonsterDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(Singleton.getInstance().getContext().getResources().openRawResource(R.raw.monsters));
            BaseMonster monster;
            ActiveAndroid.beginTransaction();
            for (JsonNode monsterNode : rootNode) {
                counter++;
                monster = new BaseMonster();
                if (monsterNode.hasNonNull("us_id")) {
                    monster.setMonsterId(monsterNode.get("us_id").asLong());
                } else if (monsterNode.hasNonNull("id")) {
                    monster.setMonsterId(monsterNode.get("id").asLong());
                }
                if (monsterNode.hasNonNull("element")) {
                    monster.setElement1(monsterNode.get("element").asInt());
                }
                if (monsterNode.hasNonNull("element2")) {
                    monster.setElement2(monsterNode.get("element2").asInt());
                }
                if (monsterNode.hasNonNull("name")) {
                    monster.setName(monsterNode.get("name").asText());
                }
                if (monsterNode.hasNonNull("max_level")) {
                    monster.setMaxLevel(monsterNode.get("max_level").asInt());
                }
                if (monsterNode.hasNonNull("awoken_skills")) {
                    monster.setAwokenSkills(parseIntArrayList(monsterNode.get("awoken_skills")));
                }
                if (monsterNode.hasNonNull("rarity")) {
                    monster.setRarity(monsterNode.get("rarity").asInt());
                }
                if (monsterNode.hasNonNull("hp_max")) {
                    monster.setHpMax(monsterNode.get("hp_max").asInt());
                }
                if (monsterNode.hasNonNull("rcv_min")) {
                    monster.setRcvMin(monsterNode.get("rcv_min").asInt());
                }
                if (monsterNode.hasNonNull("rcv_max")) {
                    monster.setRcvMax(monsterNode.get("rcv_max").asInt());
                }
                if (monsterNode.hasNonNull("rcv_scale")) {
                    monster.setRcvScale(monsterNode.get("rcv_scale").asDouble());
                }
                if (monsterNode.hasNonNull("atk_scale")) {
                    monster.setAtkScale(monsterNode.get("atk_scale").asDouble());
                }
                if (monsterNode.hasNonNull("hp_scale")) {
                    monster.setHpScale(monsterNode.get("hp_scale").asDouble());
                }
                if (monsterNode.hasNonNull("xp_curve")) {
                    monster.setXpCurve(monsterNode.get("xp_curve").asInt());
                }
                if (monsterNode.hasNonNull("leader_skill")) {
                    monster.setLeaderSkill(monsterNode.get("leader_skill").asText());
                }
                if (monsterNode.hasNonNull("team_cost")) {
                    monster.setTeamCost(monsterNode.get("team_cost").asInt());
                }
                if (monsterNode.hasNonNull("type")) {
                    monster.setType1(monsterNode.get("type").asInt());
                }
                if (monsterNode.hasNonNull("type2")) {
                    monster.setType2(monsterNode.get("type2").asInt());
                }
                if (monsterNode.hasNonNull("type3")) {
                    monster.setType3(monsterNode.get("type3").asInt());
                }
                if (monsterNode.hasNonNull("hp_min")) {
                    monster.setHpMin(monsterNode.get("hp_min").asInt());
                }
                if (monsterNode.hasNonNull("active_skill")) {
                    monster.setActiveSkill(monsterNode.get("active_skill").asText());
                }
                if (monsterNode.hasNonNull("atk_min")) {
                    monster.setAtkMin(monsterNode.get("atk_min").asInt());
                }
                if (monsterNode.hasNonNull("atk_max")) {
                    monster.setAtkMax(monsterNode.get("atk_max").asInt());
                }
                if (monsterNode.hasNonNull("evolutions")) {
                    monster.setEvolutions(parseLongArrayList(monsterNode.get("evolutions")));
                }
                monster.save();
                update.updateValues(counter);
            }
            ActiveAndroid.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    private void parseLeaderSkillDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(Singleton.getInstance().getContext().getResources().openRawResource(R.raw.leader_skills));
            LeaderSkill leaderSkill;
            ActiveAndroid.beginTransaction();
            for (JsonNode leaderSkillNode : rootNode) {
                counter++;
                leaderSkill = new LeaderSkill();
                if (leaderSkillNode.hasNonNull("hpSkillType")) {
                    leaderSkill.setHpSkillType(parseLeadSkillType(leaderSkillNode.get("hpSkillType").asInt()));
                }
                if (leaderSkillNode.hasNonNull("atkSkillType")) {
                    leaderSkill.setAtkSkillType(parseLeadSkillType(leaderSkillNode.get("atkSkillType").asInt()));
                }
                if (leaderSkillNode.hasNonNull("rcvSkillType")) {
                    leaderSkill.setRcvSkillType(parseLeadSkillType(leaderSkillNode.get("rcvSkillType").asInt()));
                }
                if (leaderSkillNode.hasNonNull("hpData")) {
                    leaderSkill.setHpData(parseDoubleArrayList(leaderSkillNode.get("hpData")));
                }
                if (leaderSkillNode.hasNonNull("atkData")) {
                    leaderSkill.setAtkData(parseDoubleArrayList(leaderSkillNode.get("atkData")));
                }
                if (leaderSkillNode.hasNonNull("rcvData")) {
                    leaderSkill.setRcvData(parseDoubleArrayList(leaderSkillNode.get("rcvData")));
                }
                if (leaderSkillNode.hasNonNull("hpType")) {
                    leaderSkill.setHpType(parseIntArrayList(leaderSkillNode.get("hpType")));
                }
                if (leaderSkillNode.hasNonNull("hpElement")) {
                    leaderSkill.setHpElement(parseIntArrayList(leaderSkillNode.get("hpElement")));
                }
                if (leaderSkillNode.hasNonNull("atkType")) {
                    leaderSkill.setAtkType(parseIntArrayList(leaderSkillNode.get("atkType")));
                }
                if (leaderSkillNode.hasNonNull("atkElement")) {
                    leaderSkill.setAtkElement(parseIntArrayList(leaderSkillNode.get("atkElement")));
                }
                if (leaderSkillNode.hasNonNull("atkType2")) {
                    leaderSkill.setAtkType2(parseIntArrayList(leaderSkillNode.get("atkType2")));
                }
                if (leaderSkillNode.hasNonNull("atkElement2")) {
                    leaderSkill.setAtkElement2(parseIntArrayList(leaderSkillNode.get("atkElement2")));
                }
                if (leaderSkillNode.hasNonNull("rcvType")) {
                    leaderSkill.setRcvType(parseIntArrayList(leaderSkillNode.get("rcvType")));
                }
                if (leaderSkillNode.hasNonNull("rcvElement")) {
                    leaderSkill.setRcvElement(parseIntArrayList(leaderSkillNode.get("rcvElement")));
                }
                if (leaderSkillNode.hasNonNull("comboMin")) {
                    leaderSkill.setComboMin(leaderSkillNode.get("comboMin").asInt());
                }
                if (leaderSkillNode.hasNonNull("comboMax")) {
                    leaderSkill.setComboMax(leaderSkillNode.get("comboMax").asInt());
                }
                if (leaderSkillNode.hasNonNull("comboMin2")) {
                    leaderSkill.setComboMin2(leaderSkillNode.get("comboMin2").asInt());
                }
                if (leaderSkillNode.hasNonNull("comboMax2")) {
                    leaderSkill.setComboMax2(leaderSkillNode.get("comboMax2").asInt());
                }
                if (leaderSkillNode.hasNonNull("matchElements")) {
                    leaderSkill.setMatchElements(parseElementArrayList(leaderSkillNode.get("matchElements")));
                }
                if (leaderSkillNode.hasNonNull("matchElements2")) {
                    leaderSkill.setMatchElements2(parseElementArrayList(leaderSkillNode.get("matchElements2")));
                }
                if (leaderSkillNode.hasNonNull("matchMonsters")) {
                    leaderSkill.setMatchMonsters(parseLongArrayList(leaderSkillNode.get("matchMonsters")));
                }
                if (leaderSkillNode.hasNonNull("hpPercent")) {
                    leaderSkill.setHpPercent(parseIntArrayList(leaderSkillNode.get("hpPercent")));
                }
                if (leaderSkillNode.hasNonNull("effect")) {
                    leaderSkill.setDescription(leaderSkillNode.get("effect").asText());
                }
                if (leaderSkillNode.hasNonNull("name")) {
                    leaderSkill.setName(leaderSkillNode.get("name").asText());
                }

                leaderSkill.save();
                update.updateValues(counter);
            }
            ActiveAndroid.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static ArrayList<String> parseStringArrayList(JsonNode stringNode) {
        ArrayList<String> returnArrayList = new ArrayList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(string.asText());
        }
        return returnArrayList;
    }

    public static ArrayList<Integer> parseIntArrayList(JsonNode stringNode) {
        ArrayList<Integer> returnArrayList = new ArrayList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(string.asInt());
        }
        return returnArrayList;
    }

    public static ArrayList<Long> parseLongArrayList(JsonNode stringNode) {
        ArrayList<Long> returnArrayList = new ArrayList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(string.asLong());
        }
        return returnArrayList;
    }

    public static ArrayList<Double> parseDoubleArrayList(JsonNode stringNode) {
        ArrayList<Double> returnArrayList = new ArrayList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(string.asDouble());
        }
        return returnArrayList;
    }

    public static LeaderSkillType parseLeadSkillType(int leadSkillInt) {
        switch (leadSkillInt) {
            case 0:
                return LeaderSkillType.BLANK;
            case 1:
                return LeaderSkillType.FLAT;
            case 2:
                return LeaderSkillType.FLAT_ATTRIBUTE_ACTIVE_ATTRIBUTE;
            case 3:
                return LeaderSkillType.FLAT_TYPE_FLAT_ATTRIBUTE;
            case 4:
                return LeaderSkillType.COMBO;
            case 5:
                return LeaderSkillType.MATCH_ELEMENT;
            case 6:
                return LeaderSkillType.MONSTER_CONDITIONAL;
            case 7:
                return LeaderSkillType.FLAT_MONSTER_CONDITIONAL;
            case 8:
                return LeaderSkillType.ORB_LINK;
            case 9:
                return LeaderSkillType.ORB_LINK_FLAT;
            case 10:
                return LeaderSkillType.ORB_LINK_INDIAN;
            case 11:
                return LeaderSkillType.MATCH_ELEMENT_FLAT;
            case 12:
                return LeaderSkillType.COMBO_MATCH_ELEMENT;
            case 13:
                return LeaderSkillType.MATCH_ELEMENT_ACTIVE;
            case 14:
                return LeaderSkillType.FLAT_ACTIVE;
            case 15:
                return LeaderSkillType.COMBO_ACTIVE;
            case 16:
                return LeaderSkillType.COMBO_FLAT;
            case 17:
                return LeaderSkillType.COMBO_ATTRIBUTE;
            case 18:
                return LeaderSkillType.COMBO_EXACT;
            case 19:
                return LeaderSkillType.INDIAN;
            case 20:
                return LeaderSkillType.INDIAN_FLAT;
            case 21:
                return LeaderSkillType.ORB_PLUS;
            case 22:
                return LeaderSkillType.ORB_PLUS_FLAT;
            case 23:
                return LeaderSkillType.INDIAN_ORB_PLUS;
            case 24:
                return LeaderSkillType.MATCH_ELEMENT_ORB_PLUS;
            case 25:
                return LeaderSkillType.COMBO_ORB_PLUS;
            case 26:
                return LeaderSkillType.GRIMOIRE_FLAT;
            case 27:
                return LeaderSkillType.HP_FLAT;
            case 28:
                return LeaderSkillType.FLAT_HP_FLAT;
            case 29:
                return LeaderSkillType.HP_FLAT_ATTRIBUTE_FLAT_TYPE;
            case 30:
                return LeaderSkillType.ACTIVE;
            case 31:
                return LeaderSkillType.FLAT_TYPE_ACTIVE_ATTRIBUTE;
            case 32:
                return LeaderSkillType.ORB_LINK_EXACT_FLAT;
            case 33:
                return LeaderSkillType.ORB_LINK_EXACT;
            case 34:
                return LeaderSkillType.ORB_LINK_ORB_PLUS;
            case 35:
                return LeaderSkillType.MATCH_ELEMENT_ORB_LINK;
            case 36:
                return LeaderSkillType.FLAT_TYPE_FLAT_TYPE;
            case 37:
                return LeaderSkillType.HP_FLAT_ORB_PLUS;
            case 38:
                return LeaderSkillType.HP_FLAT_MATCH_ELEMENT;
            case 39:
                return LeaderSkillType.HP_FLAT_ACTIVE;
            case 40:
                return LeaderSkillType.ORB_LINK_ACTIVE;
            case 41:
                return LeaderSkillType.HP_FLAT_ATTRIBUTE_FLAT_ATTRIBUTE;
            case 42:
                return LeaderSkillType.INDIAN_ACTIVE;
            case 43:
                return LeaderSkillType.HEART_CROSS;
            case 44:
                return LeaderSkillType.COMBO_INDIAN;
            case 45:
                return LeaderSkillType.ORB_LINK_HP_FLAT;
            case 46:
                return LeaderSkillType.ORB_PLUS_HEART_CROSS;
            case 47:
                return LeaderSkillType.INDIAN_HEART_CROSS;
            case 48:
                return LeaderSkillType.FLAT_HEART_CROSS;
            case 49:
                return LeaderSkillType.MATCH_ELEMENT_HEART_CROSS;
            case 50:
                return LeaderSkillType.ACTIVE_HEART_CROSS;
            case 51:
                return LeaderSkillType.INDIAN_MONSTER_CONDITIONAL;
            case 52:
                return LeaderSkillType.ORB_PLUS_MONSTER_CONDITIONAL;
            case 53:
                return LeaderSkillType.CROSS;
            case 54:
                return LeaderSkillType.INDIAN_CROSS;
            case 55:
                return LeaderSkillType.ACTIVE_CROSS;
            case 56:
                return LeaderSkillType.CO_OP_HP_FLAT;
            case 57:
                return LeaderSkillType.CO_OP_FLAT;
            case 58:
                return LeaderSkillType.CO_OP;
            default:
                return LeaderSkillType.BLANK;
        }
        //Fill out if statement or switch for this following format from parseElement
    }

    public static ArrayList<Element> parseElementArrayList(JsonNode stringNode) {
        ArrayList<Element> returnArrayList = new ArrayList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(parseElement(string.asInt()));
        }
        return returnArrayList;
    }

    public static Element parseElement(int elementType) {
        if (elementType == 0) {
            return Element.RED;
        }
        if (elementType == 1) {
            return Element.BLUE;
        }
        if (elementType == 2) {
            return Element.GREEN;
        }
        if (elementType == 3) {
            return Element.LIGHT;
        }
        if (elementType == 4) {
            return Element.DARK;
        }
        if (elementType == 5) {
            return Element.HEART;
        }
        if (elementType == 6) {
            return Element.JAMMER;
        }
        if (elementType == 7) {
            return Element.POISON;
        }
        if (elementType == 8) {
            return Element.MORTAL_POISON;
        }
        return Element.BLANK;
    }
}
