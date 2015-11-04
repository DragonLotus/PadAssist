package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamFavoriteComparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (!lhs.getLead().isFavorite() && rhs.getLead().isFavorite()) {
            return 1;
        } else if (lhs.getLead().isFavorite() && rhs.getLead().isFavorite()) {
            if (lhs.getLead().getElement1Int() > rhs.getLead().getElement1Int()) {
                return 1;
            } else if (lhs.getLead().getElement1Int() == rhs.getLead().getElement1Int()) {
                if (lhs.getLead().getRarity() < rhs.getLead().getRarity()) {
                    return 1;
                } else if (lhs.getLead().getRarity() == rhs.getLead().getRarity()) {
                    if (lhs.getLead().getElement2Int() == -1 && rhs.getLead().getElement2Int() == -1) {
                        if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else if (lhs.getLead().getElement2Int() == -1) {
                        return 1;
                    } else if (rhs.getLead().getElement2Int() == -1) {
                        return -1;
                    } else {
                        if (lhs.getLead().getElement2Int() > rhs.getLead().getElement2Int()) {
                            return 1;
                        } else if (lhs.getLead().getElement2Int() == rhs.getLead().getElement2Int()) {
                            if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else if(!lhs.getLead().isFavorite() && !rhs.getLead().isFavorite()){
            if (lhs.getLead().getElement1Int() > rhs.getLead().getElement1Int()) {
                return 1;
            } else if (lhs.getLead().getElement1Int() == rhs.getLead().getElement1Int()) {
                if (lhs.getLead().getRarity() < rhs.getLead().getRarity()) {
                    return 1;
                } else if (lhs.getLead().getRarity() == rhs.getLead().getRarity()) {
                    if (lhs.getLead().getElement2Int() == -1 && rhs.getLead().getElement2Int() == -1) {
                        if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else if (lhs.getLead().getElement2Int() == -1) {
                        return 1;
                    } else if (rhs.getLead().getElement2Int() == -1) {
                        return -1;
                    } else {
                        if (lhs.getLead().getElement2Int() > rhs.getLead().getElement2Int()) {
                            return 1;
                        } else if (lhs.getLead().getElement2Int() == rhs.getLead().getElement2Int()) {
                            if (lhs.getLead().getBaseMonsterId() > rhs.getLead().getBaseMonsterId()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else {
                            return -1;
                        }
                    }
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }else {
            return -1;
        }
    }
}
