package com.padassist.Threads;

import android.util.Log;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.padassist.Data.ActiveSkill;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.Monster;
import com.padassist.Data.RealmDouble;
import com.padassist.Data.RealmElement;
import com.padassist.Data.RealmInt;
import com.padassist.Data.RealmLeaderSkillType;
import com.padassist.Data.RealmLong;
import com.padassist.Util.Singleton;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Thomas on 11/22/2015.
 */
public class ParseMonsterDatabaseThread extends Thread {

    private UpdateProgress update;
    private int counter = 0;
    private Realm realm;
    private ArrayList<String> usedLeaderSkills;
    private ArrayList<String> usedActiveSkills;

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
        realm = Realm.getDefaultInstance();
        usedLeaderSkills = new ArrayList<>();
        usedActiveSkills = new ArrayList<>();
        parseLeaderSkillDatabase();
        parseActiveSkillDatabase();
        parseMonsterDatabase();
        updateSavedMonsters();
        Log.d("ParseMonster", "image results size is: " + realm.where(BaseMonster.class).findAll().size());
        realm.close();
    }

    private void parseLeaderSkillDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            File leaderSkillsFile = new File(Singleton.getInstance().getContext().getFilesDir(), "leader_skills.json");
            if (leaderSkillsFile.exists()) {
                JsonNode rootNode = m.readTree(leaderSkillsFile);
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
                    } else {
                        leaderSkill.setMinimumMatch(3);
                    }
                    if(leaderSkillNode.hasNonNull("boardSize")){
                        leaderSkill.setBoardSize(leaderSkillNode.get("boardSize").asInt());
                    } else {
                        leaderSkill.setBoardSize(1);
                    }
                    if(leaderSkillNode.hasNonNull("noSkyfall")){
                        leaderSkill.setNoSkyfall(leaderSkillNode.get("noSkyfall").asBoolean());
                    } else {
                        leaderSkill.setNoSkyfall(false);
                    }
                    realm.copyToRealmOrUpdate(leaderSkill);
                    update.updateValues(counter);
                }
            }
            Log.d("ParseMonsterDatabase", "leaderSkill counter is: " + counter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void parseActiveSkillDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            File activeSkillsFile = new File(Singleton.getInstance().getContext().getFilesDir(), "active_skills.json");
            if (activeSkillsFile.exists()) {
                JsonNode rootNode = m.readTree(activeSkillsFile);
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
                        activeSkill.setMaximumCooldown(activeSkillNode.get("max_cooldown").asInt());
                    }
                    realm.copyToRealmOrUpdate(activeSkill);
                    update.updateValues(counter);
                }
            }
            Log.d("ParseMonsterDatabase", "activeSkill counter is: " + counter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void parseMonsterDatabase() {
        try {
            ObjectMapper m = new ObjectMapper();
            File monstersFile = new File(Singleton.getInstance().getContext().getFilesDir(), "monsters.json");
            if (monstersFile.exists()) {
                JsonNode rootNode = m.readTree(monstersFile);
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
                        monster.setElement1Int(monsterNode.get("element").asInt());
                    } else {
                        monster.setElement1Int(-1);
                    }
                    if (monsterNode.hasNonNull("element2")) {
                        monster.setElement2Int(monsterNode.get("element2").asInt());
                    } else {
                        monster.setElement2Int(-1);
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
                        monster.setLeaderSkillString(monsterNode.get("leader_skill").asText());
                    }
                    if (monsterNode.hasNonNull("team_cost")) {
                        monster.setTeamCost(monsterNode.get("team_cost").asInt());
                    }
                    if (monsterNode.hasNonNull("type")) {
                        monster.setType1(monsterNode.get("type").asInt());
                    } else {
                        monster.setType1(-1);
                    }
                    if (monsterNode.hasNonNull("type2")) {
                        monster.setType2(monsterNode.get("type2").asInt());
                    } else {
                        monster.setType2(-1);
                    }
                    if (monsterNode.hasNonNull("type3")) {
                        monster.setType3(monsterNode.get("type3").asInt());
                    } else {
                        monster.setType3(-1);
                    }
                    if (monsterNode.hasNonNull("hp_min")) {
                        monster.setHpMin(monsterNode.get("hp_min").asInt());
                    }
                    if (monsterNode.hasNonNull("active_skill")) {
                        monster.setActiveSkillString(monsterNode.get("active_skill").asText());
                    }
                    if (monsterNode.hasNonNull("atk_min")) {
                        monster.setAtkMin(monsterNode.get("atk_min").asInt());
                    }
                    if (monsterNode.hasNonNull("atk_max")) {
                        monster.setAtkMax(monsterNode.get("atk_max").asInt());
                    }
                    if (monsterNode.hasNonNull("evolutions")) {
                        monster.setEvolutions(parseLongArrayList(monsterNode.get("evolutions")));
                    } else {
                        monster.getEvolutions().clear();
                    }
                    if (monsterNode.hasNonNull("inheritable")) {
                        monster.setInheritable(monsterNode.get("inheritable").asBoolean());
                    } else {
                        monster.setInheritable(false);
                    }


                    monster.setLeaderSkill(realm.where(LeaderSkill.class).equalTo("name", monster.getLeaderSkillString()).findFirst());
                    monster.setActiveSkill(realm.where(ActiveSkill.class).equalTo("name", monster.getActiveSkillString()).findFirst());
                    monster.setType1String(getTypeString(monster.getType1()));
                    monster.setType2String(getTypeString(monster.getType2()));
                    monster.setType3String(getTypeString(monster.getType3()));
                    monster.setMonsterIdString(String.valueOf(monster.getMonsterId()));
                    usedLeaderSkills.add(monster.getLeaderSkillString());
                    usedActiveSkills.add(monster.getActiveSkillString());
                    realm.copyToRealmOrUpdate(monster);
                    update.updateValues(counter);
                }

            }

            Log.d("ParseMonsterDatabase", "Monster counter is: " + counter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void updateSavedMonsters() {
        RealmResults<Monster> savedMonstersResults = realm.where(Monster.class).findAll();
        Log.d("ParseMonsterDatabase", "realm schema is: " + realm.getConfiguration().getSchemaVersion());
        if (savedMonstersResults.size() != 0) {
            for (Monster monster : savedMonstersResults) {
                counter++;

                if (monster.getActiveSkillStringMonster() == null) {
                    monster.setActiveSkillString(monster.getBaseMonster().getActiveSkillString());
                } else if (!monster.getActiveSkillStringMonster().equals(monster.getActiveSkillString())) {
                    monster.setActiveSkillString(monster.getBaseMonster().getActiveSkillString());
                }
                if (monster.getLeaderSkillStringMonster() == null) {
                    monster.setLeaderSkillString(monster.getBaseMonster().getLeaderSkillString());
                } else if (!monster.getLeaderSkillStringMonster().equals(monster.getLeaderSkillString())) {
                    monster.setLeaderSkillString(monster.getBaseMonster().getLeaderSkillString());
                }

                if (monster.getActiveSkill2String() == null) {
                    monster.setActiveSkill2String("Blank");
                } else if (!monster.getActiveSkill2String().equals("Blank")) {
                    monster.setActiveSkill2(realm.where(ActiveSkill.class).equalTo("name", monster.getActiveSkill2String()).findFirst());
                }

                if(monster.getName() != null){
                    if(!monster.getName().equals(monster.getBaseMonster().getName())){
                        monster.setName(monster.getBaseMonster().getName());
                    }
                } else {
                    monster.setName(monster.getBaseMonster().getName());
                }

                if (monster.getType1String() == null) {
                    monster.setType1String(getTypeString(monster.getType1()));
                } else {
                    if (monster.getType1String().isEmpty() || !monster.getType1String().equals(monster.getBaseMonster().getType1String())) {
                        monster.setType1String(getTypeString(monster.getType1()));
                    }
                }
                if (monster.getType2String() == null) {
                    monster.setType2String(getTypeString(monster.getType2()));
                } else {
                    if (monster.getType2String().isEmpty() || !monster.getType1String().equals(monster.getBaseMonster().getType2String())) {
                        monster.setType2String(getTypeString(monster.getType2()));
                    }
                }
                if (monster.getType3String() == null) {
                    monster.setType3String(getTypeString(monster.getType3()));
                } else {
                    if (monster.getType3String().isEmpty() || !monster.getType1String().equals(monster.getBaseMonster().getType3String())) {
                        monster.setType3String(getTypeString(monster.getType3()));
                    }
                }
                if (monster.getBaseMonsterIdString() == null) {
                    monster.setBaseMonsterIdString(monster.getBaseMonster().getMonsterIdString());
                } else {
                    if (monster.getBaseMonsterIdString().isEmpty()) {
                        monster.setBaseMonsterIdString(monster.getBaseMonster().getMonsterIdString());
                    }
                }
                if (monster.getName() == null) {
                    monster.setName(monster.getBaseMonster().getName());
                } else {
                    if (monster.getName().isEmpty()) {
                        monster.setName(monster.getBaseMonster().getName());
                    }
                }

                if (monster.getActiveSkillLevel() == 0) {
                    monster.setActiveSkillLevel(1);
                }
                if (monster.getActiveSkill2Level() == 0) {
                    monster.setActiveSkill2Level(1);
                }

                if (monster.getLatents().size() == 5) {
                    monster.getLatents().add(new RealmInt(0));
                }

                monster.setCurrentLevel(monster.getCurrentLevel());

                update.updateValues(counter);
            }
        }
        RealmResults<LeaderSkill> savedLeaderSkillResults = realm.where(LeaderSkill.class).findAll();
        for(LeaderSkill leaderSkill : savedLeaderSkillResults){
            if(!usedLeaderSkills.contains(leaderSkill.getName())){
                leaderSkill.deleteFromRealm();
            }
        }
        counter++;
        update.updateValues(counter);
        RealmResults<ActiveSkill> savedActiveSkillResults = realm.where(ActiveSkill.class).findAll();
        for(ActiveSkill activeSkill : savedActiveSkillResults){
            if(!usedActiveSkills.contains(activeSkill.getName())){
                activeSkill.deleteFromRealm();
            }
        }
        counter++;
        update.updateValues(counter);
        Log.d("ParseMonsterDatabase", "LinkMonsters counter is: " + counter);
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

    public static String getTypeString(int type) {
        switch (type) {
            case 0:
                return "Evo Material";
            case 1:
                return "Balanced";
            case 2:
                return "Physical";
            case 3:
                return "Healer";
            case 4:
                return "Dragon";
            case 5:
                return "God";
            case 6:
                return "Attacker";
            case 7:
                return "Devil";
            case 8:
                return "Machine";
            case 12:
                return "Awoken Skill Material";
            case 13:
                return "Protected";
            case 14:
                return "Enhance Material";
            default:
                return "";
        }
    }
}
