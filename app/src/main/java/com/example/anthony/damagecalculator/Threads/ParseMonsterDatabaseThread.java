package com.example.anthony.damagecalculator.Threads;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.LeaderSkill;
import com.example.anthony.damagecalculator.Data.LeaderSkillType;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.Singleton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Thomas on 11/22/2015.
 */
public class ParseMonsterDatabaseThread extends Thread {

    public void run() {
        super.run();
        parseMonsterDatabase();
        parseLeaderSkillDatabase();
    }

    private void parseMonsterDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(Singleton.getInstance().getContext().getResources().openRawResource(R.raw.monsters));
            BaseMonster monster;
            ActiveAndroid.beginTransaction();
            for(JsonNode monsterNode: rootNode) {
               monster = new BaseMonster();
                if(monsterNode.hasNonNull("element")) {
                    monster.setElement1(monsterNode.get("element").asInt());
                }
                if (monsterNode.hasNonNull("element2")) {
                    monster.setElement2(monsterNode.get("element2").asInt());
                }
                if(monsterNode.hasNonNull("name")) {
                    monster.setName(monsterNode.get("name").asText());
                }
                if(monsterNode.hasNonNull("max_level")) {
                    monster.setMaxLevel(monsterNode.get("max_level").asInt());
                }
                if(monsterNode.hasNonNull("awoken_skills")) {
                    monster.setAwokenSkills(monsterNode.get("awoken_skills"));
                }
                if(monsterNode.hasNonNull("rarity")) {
                    monster.setRarity(monsterNode.get("rarity").asInt());
                }
                if(monsterNode.hasNonNull("hp_max")) {
                    monster.setHpMax(monsterNode.get("hp_max").asInt());
                }
                if(monsterNode.hasNonNull("rcv_min")) {
                    monster.setRcvMin(monsterNode.get("rcv_min").asInt());
                }
                if(monsterNode.hasNonNull("rcv_max")) {
                    monster.setRcvMax(monsterNode.get("rcv_max").asInt());
                }
                if(monsterNode.hasNonNull("rcv_scale")) {
                    monster.setRcvScale(monsterNode.get("rcv_scale").asDouble());
                }
                if(monsterNode.hasNonNull("atk_scale")) {
                    monster.setAtkScale(monsterNode.get("atk_scale").asDouble());
                }
                if(monsterNode.hasNonNull("type2")) {
                    monster.setType2(monsterNode.get("type2").asInt());
                }
                if(monsterNode.hasNonNull("hp_scale")) {
                    monster.setHpScale(monsterNode.get("hp_scale").asDouble());
                }
                if(monsterNode.hasNonNull("xp_curve")) {
                    monster.setXpCurve(monsterNode.get("xp_curve").asInt());
                }
                if(monsterNode.hasNonNull("leader_skill")) {
                    monster.setLeaderSkill(monsterNode.get("leader_skill").asText());
                }
                if(monsterNode.hasNonNull("team_cost")) {
                    monster.setTeamCost(monsterNode.get("team_cost").asInt());
                }
                if(monsterNode.hasNonNull("type")) {
                    monster.setType1(monsterNode.get("type").asInt());
                }
                if(monsterNode.hasNonNull("hp_min")) {
                    monster.setHpMin(monsterNode.get("hp_min").asInt());
                }
                if(monsterNode.hasNonNull("active_skill")) {
                    monster.setActiveSkill(monsterNode.get("active_skill").asText());
                }
                if(monsterNode.hasNonNull("atk_min")) {
                    monster.setAtkMin(monsterNode.get("atk_min").asInt());
                }
                if(monsterNode.hasNonNull("atk_max")) {
                    monster.setAtkMax(monsterNode.get("atk_max").asInt());
                }
//                if(monsterNode.hasNonNull("image40_href")) {
//                    monster.setMonsterPicture(monsterNode.get("image40_href").asText());
//                }
                monster.save();

            }
            ActiveAndroid.setTransactionSuccessful();
        } catch(Exception e) {
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
            for(JsonNode leaderSkillNode: rootNode) {
                leaderSkill = new LeaderSkill();
                if(leaderSkillNode.hasNonNull("hpSkillType")) {
                    leaderSkill.setHpSkillType(parseLeadSkillType(leaderSkillNode.get("hpSkillType").asInt()));
                }
                if (leaderSkillNode.hasNonNull("atkSkillType")) {
                    leaderSkill.setAtkSkillType(parseLeadSkillType(leaderSkillNode.get("atkSkillType").asInt()));
                }
                if(leaderSkillNode.hasNonNull("rcvSkillType")) {
                    leaderSkill.setRcvSkillType(parseLeadSkillType(leaderSkillNode.get("rcvSkillType").asInt()));
                }
                if(leaderSkillNode.hasNonNull("hpData")) {
                    leaderSkill.setHpData(parseDoubleArrayList(leaderSkillNode.get("hpData")));
                }
                if(leaderSkillNode.hasNonNull("atkData")) {
                    leaderSkill.setAtkData(parseDoubleArrayList(leaderSkillNode.get("atkData")));
                }
                if(leaderSkillNode.hasNonNull("rcvData")) {
                    leaderSkill.setRcvData(parseDoubleArrayList(leaderSkillNode.get("rcvData")));
                }
                if(leaderSkillNode.hasNonNull("hpType")) {
                    leaderSkill.setHpType(parseIntArrayList(leaderSkillNode.get("hpType")));
                }
                if(leaderSkillNode.hasNonNull("hpElement")) {
                    leaderSkill.setHpElement(parseIntArrayList(leaderSkillNode.get("hpElement")));
                }
                if(leaderSkillNode.hasNonNull("atkType")) {
                    leaderSkill.setAtkType(parseIntArrayList(leaderSkillNode.get("atkType")));
                }
                if(leaderSkillNode.hasNonNull("atkElement")) {
                    leaderSkill.setAtkElement(parseIntArrayList(leaderSkillNode.get("atkElement")));
                }
                if(leaderSkillNode.hasNonNull("rcvType")) {
                    leaderSkill.setRcvType(parseIntArrayList(leaderSkillNode.get("rcvType")));
                }
                if(leaderSkillNode.hasNonNull("rcvElement")) {
                    leaderSkill.setRcvElement(parseIntArrayList(leaderSkillNode.get("rcvElement")));
                }
                if(leaderSkillNode.hasNonNull("comboMin")) {
                    leaderSkill.setComboMin(leaderSkillNode.get("comboMin").asInt());
                }
                if(leaderSkillNode.hasNonNull("comboMax")) {
                    leaderSkill.setComboMax(leaderSkillNode.get("comboMax").asInt());
                }
                if(leaderSkillNode.hasNonNull("comboMin2")) {
                    leaderSkill.setComboMin2(leaderSkillNode.get("comboMin2").asInt());
                }
                if(leaderSkillNode.hasNonNull("comboMax2")) {
                    leaderSkill.setComboMax2(leaderSkillNode.get("comboMax2").asInt());
                }
                if(leaderSkillNode.hasNonNull("matchElements")) {
                    leaderSkill.setMatchElements(parseElementArrayList(leaderSkillNode.get("matchElements")));
                }
                if(leaderSkillNode.hasNonNull("matchMonsters")) {
                    leaderSkill.setMatchMonsters(parseLongArrayList(leaderSkillNode.get("matchMonsters")));
                }
                if(leaderSkillNode.hasNonNull("hpPercent")) {
                    leaderSkill.setHpPercent(parseIntArrayList(leaderSkillNode.get("hpPercent")));
                }
//                if(leaderSkillNode.hasNonNull("effect")) {
//                    leaderSkill.set(leaderSkillNode.get("effect").asInt());
//                }
                if(leaderSkillNode.hasNonNull("name")) {
                    leaderSkill.setName(leaderSkillNode.get("name").asText());
                }
//                if(leaderSkillNode.hasNonNull("image40_href")) {
//                    monster.setMonsterPicture(leaderSkillNode.get("image40_href").asText());
//                }

leaderSkill.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static ArrayList<String> parseStringArrayList(JsonNode stringNode) {
        ArrayList<String> returnArrayList = new ArrayList<>();
        for(JsonNode string: stringNode) {
            returnArrayList.add(string.asText());
        }
        return returnArrayList;
    }

    public static ArrayList<Integer> parseIntArrayList(JsonNode stringNode) {
        ArrayList<Integer> returnArrayList = new ArrayList<>();
        for(JsonNode string: stringNode) {
            returnArrayList.add(string.asInt());
        }
        return returnArrayList;
    }

    public static ArrayList<Long> parseLongArrayList(JsonNode stringNode) {
        ArrayList<Long> returnArrayList = new ArrayList<>();
        for(JsonNode string: stringNode) {
            returnArrayList.add(string.asLong());
        }
        return returnArrayList;
    }

    public static ArrayList<Double> parseDoubleArrayList(JsonNode stringNode) {
        ArrayList<Double> returnArrayList = new ArrayList<>();
        for(JsonNode string: stringNode) {
            returnArrayList.add(string.asDouble());
        }
        return returnArrayList;
    }

    public static LeaderSkillType parseLeadSkillType(int leadSkillInt) {

        //Fill out if statement or switch for this following format from parseElement
        return LeaderSkillType.BLANK;
    }

    public static ArrayList<Element> parseElementArrayList(JsonNode stringNode) {
        ArrayList<Element> returnArrayList = new ArrayList<>();
        for(JsonNode string: stringNode) {
            returnArrayList.add(parseElement(string.asInt()));
        }
        return returnArrayList;
    }

    public static Element parseElement(int elementType) {
        if(elementType == 0) {
            return Element.RED;
        }
        if(elementType == 1) {
            return Element.BLUE;
        }
        if(elementType == 2) {
            return Element.GREEN;
        }
        if(elementType == 3) {
            return Element.LIGHT;
        }
        if(elementType == 4) {
            return Element.DARK;
        }
        return Element.BLANK;
    }
}
