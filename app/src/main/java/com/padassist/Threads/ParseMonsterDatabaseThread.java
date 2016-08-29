package com.padassist.Threads;

//import com.activeandroid.ActiveAndroid;
import android.util.Log;

import com.padassist.BuildConfig;
import com.padassist.Constants;
import com.padassist.Data.ActiveSkill;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Element;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.LeaderSkillType;
import com.padassist.Data.RealmDouble;
import com.padassist.Data.RealmElement;
import com.padassist.Data.RealmInt;
import com.padassist.Data.RealmLeaderSkillType;
import com.padassist.Data.RealmLong;
import com.padassist.R;
import com.padassist.Util.SharedPreferencesUtil;
import com.padassist.Util.Singleton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Thomas on 11/22/2015.
 */
public class ParseMonsterDatabaseThread extends Thread {

    private UpdateProgress update;
    int counter = 0;
    private Realm realm = Realm.getDefaultInstance();

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
        parseActiveSkillDatabase();

    }

    private void parseMonsterDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(Singleton.getInstance().getContext().getResources().openRawResource(R.raw.monsters));
            BaseMonster monster;
//            realm.beginTransaction();
            for (JsonNode monsterNode : rootNode) {
                counter++;
                monster = new BaseMonster();
                if (monsterNode.hasNonNull("us_id")) {
                    monster.setMonsterId(monsterNode.get("us_id").asLong());
                } else if (monsterNode.hasNonNull("id")) {
                    monster.setMonsterId(monsterNode.get("id").asLong());
                }
//                if (monsterNode.hasNonNull("id")) {
//                    monster.setMonsterId(monsterNode.get("id").asLong());
//                }
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
                realm.copyToRealmOrUpdate(monster);
                update.updateValues(counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.d("ParseMonsterDatabase", "Monster counter is: " + counter);
        }
    }

    private void parseLeaderSkillDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(Singleton.getInstance().getContext().getResources().openRawResource(R.raw.leader_skills));
            LeaderSkill leaderSkill;
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
                if (leaderSkillNode.hasNonNull("minimumMatch")) {
                    leaderSkill.setMinimumMatch(leaderSkillNode.get("minimumMatch").asInt());
                }
                realm.copyToRealmOrUpdate(leaderSkill);
                update.updateValues(counter);
//                Log.d("ParseMonsterDatabase", "leaderSkill counter is: " + counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void parseActiveSkillDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(Singleton.getInstance().getContext().getResources().openRawResource(R.raw.active_skills));
            ActiveSkill activeSkill;
            for (JsonNode activeSkillNode : rootNode) {
                counter++;
                activeSkill = new ActiveSkill();
                if (activeSkillNode.hasNonNull("name")) {
                    activeSkill.setName(activeSkillNode.get("name").asText());
                }
                if (activeSkillNode.hasNonNull("effect")) {
                    activeSkill.setDescription(activeSkillNode.get("effect").asText());
                }
                if (activeSkillNode.hasNonNull("min_cooldown")) {
                    activeSkill.setMinimumCooldown(activeSkillNode.get("min_cooldown").asInt());
                }
                if (activeSkillNode.hasNonNull("max_cooldown")) {
                    activeSkill.setMinimumCooldown(activeSkillNode.get("max_cooldown").asInt());
                }
                realm.copyToRealmOrUpdate(activeSkill);
                update.updateValues(counter);
//                Log.d("ParseMonsterDatabase", "activeSkill counter is: " + counter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static ArrayList<String> parseStringArrayList(JsonNode stringNode) {
        ArrayList<String> returnArrayList = new ArrayList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(string.asText());
        }
        return returnArrayList;
    }

    public static RealmList<RealmInt> parseIntArrayList(JsonNode stringNode) {
        RealmList<RealmInt> returnArrayList = new RealmList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(new RealmInt(string.asInt()));
        }
        return returnArrayList;
    }

    public static RealmList<RealmLong> parseLongArrayList(JsonNode stringNode) {
        RealmList<RealmLong> returnArrayList = new RealmList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(new RealmLong(string.asLong()));
        }
        return returnArrayList;
    }

    public static RealmList<RealmDouble> parseDoubleArrayList(JsonNode stringNode) {
        RealmList<RealmDouble> returnArrayList = new RealmList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(new RealmDouble(string.asDouble()));
        }
        return returnArrayList;
    }

    public static RealmLeaderSkillType parseLeadSkillType(int leadSkillInt) {
        return new RealmLeaderSkillType(leadSkillInt);
    }

    public static RealmList<RealmElement> parseElementArrayList(JsonNode stringNode) {
        RealmList<RealmElement> returnArrayList = new RealmList<>();
        for (JsonNode string : stringNode) {
            returnArrayList.add(parseElement(string.asInt()));
        }
        return returnArrayList;
    }

    public static RealmElement parseElement(int elementType) {
//        if (elementType == 0) {
//            return Element.RED;
//        }
//        if (elementType == 1) {
//            return Element.BLUE;
//        }
//        if (elementType == 2) {
//            return Element.GREEN;
//        }
//        if (elementType == 3) {
//            return Element.LIGHT;
//        }
//        if (elementType == 4) {
//            return Element.DARK;
//        }
//        if (elementType == 5) {
//            return Element.HEART;
//        }
//        if (elementType == 6) {
//            return Element.JAMMER;
//        }
//        if (elementType == 7) {
//            return Element.POISON;
//        }
//        if (elementType == 8) {
//            return Element.MORTAL_POISON;
//        }
//        return Element.BLANK;
        return new RealmElement(elementType);
    }
}
