package com.fiveup.core.cultivation.enums;

/**
 * @author Harvi
 */
public enum GoalStates {
    /**
     * 未发起
     */
    UN_LAUNCHED(1),
    /**
     * 发起中
     */
    LAUNCHING(2),
    /**
     * 待审核
     */
    REVIEWING(3),
    /**
     * 审核通过
     */
    PASSED(4),
    /**
     * 审核驳回
     */
    REJECTED(5),
    /**
     * 未发布
     */
    UN_PUBLISHED(6),
    /**
     * 发布中
     */
    PUBLISHING(7);

    private final int goalState;

    GoalStates(int goalState) {
        this.goalState = goalState;
    }

    public int getGoalState() {
        return goalState;
    }
}
