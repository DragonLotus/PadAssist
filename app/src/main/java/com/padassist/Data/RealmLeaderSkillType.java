package com.padassist.Data;

import io.realm.RealmObject;

/**
 * Created by DragonLotus on 8/4/2016.
 */

public class RealmLeaderSkillType extends RealmObject {
    private int value;

    public RealmLeaderSkillType() {
    }

    public RealmLeaderSkillType(int value) {
        this.value = value;
    }

    public LeaderSkillType getValue() {
        switch (value) {
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
            case 59:
                return LeaderSkillType.FLAT_CROSS;
            case 60:
                return LeaderSkillType.HEART_CROSS_CROSS;
            case 61:
                return LeaderSkillType.MATCH_ELEMENT_INDIAN;
            case 62:
                return LeaderSkillType.MINIMUM_MATCH_INDIAN_FLAT;
            case 63:
                return LeaderSkillType.MINIMUM_MATCH_ORB_LINK_FLAT;
            case 64:
                return LeaderSkillType.MINIMUM_MATCH_MATCH_ELEMENT_FLAT;
            case 65:
                return LeaderSkillType.MINIMUM_MATCH_COMBO_FLAT;
            case 66:
                return LeaderSkillType.COMBO_NO_DROP;
            case 67:
                return LeaderSkillType.MATCH_ELEMENTS_FLAT_NO_DROP;
            case 68:
                return LeaderSkillType.ORB_LINK_ORB_LINK;
            case 69:
                return LeaderSkillType.COMBO_FLAT_NO_DROP;
            case 70:
                return LeaderSkillType.FLAT_CROSS_NO_DROP;
            case 71:
                return LeaderSkillType.ORB_LINK_HEART_CROSS;
            case 72:
                return LeaderSkillType.COMBO_EXACT_NO_DROP;
            case 73:
                return LeaderSkillType.COMBO_ORB_LINK;
            case 74:
                return LeaderSkillType.MATCH_ELEMENT_MONSTER_CONDITIONAL;
            case 75:
                return LeaderSkillType.BIG_BOARD_SIZE_MATCH_ELEMENT;
            default:
                return LeaderSkillType.BLANK;
        }
    }

    public void setValue(int value) {
        this.value = value;
    }
}
