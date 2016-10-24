package com.padassist.Comparators;

import com.padassist.Data.Team;

import java.util.Comparator;

/**
 * Created by DragonLotus on 10/4/2015.
 */
public class TeamLeaderType2Comparator implements Comparator<Team> {
    @Override
    public int compare(Team lhs, Team rhs) {
        if (lhs.getLead().getType2() == -1 && rhs.getLead().getType2() == -1) {
            if (lhs.getLead().getType1() > rhs.getLead().getType1()) {
                return 1;
            } else if (lhs.getLead().getType1() == rhs.getLead().getType1()) {
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
            } else {
                return -1;
            }
        } else if (lhs.getLead().getType2() == -1) {
            return 1;
        } else if (rhs.getLead().getType2() == -1) {
            return -1;
        } else {
            if (lhs.getLead().getType2() > rhs.getLead().getType2()) {
                return 1;
            } else if (lhs.getLead().getType2() == rhs.getLead().getType2()) {
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
            } else {
                return -1;
            }
        }
    }
}
