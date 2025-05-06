package com.fiveup.core.diagnose.bean;


public class student_score {
    private int sDeyu;
    private int sZhiyu;
    private int sTiyu;
    private int sMeiyu;
    private int sLaoyu;

    public student_score(int sDeyu, int sZhiyu, int sTiyu, int sMeiyu, int sLaoyu) {
        this.sDeyu = sDeyu;
        this.sZhiyu = sZhiyu;
        this.sTiyu = sTiyu;
        this.sMeiyu = sMeiyu;
        this.sLaoyu = sLaoyu;
    }

    public student_score() {
    }

    public int getsDeyu() {
        return sDeyu;
    }

    public void setsDeyu(int sDeyu) {
        this.sDeyu = sDeyu;
    }

    public int getsZhiyu() {
        return sZhiyu;
    }

    public void setsZhiyu(int sZhiyu) {
        this.sZhiyu = sZhiyu;
    }

    public int getsTiyu() {
        return sTiyu;
    }

    public void setsTiyu(int sTiyu) {
        this.sTiyu = sTiyu;
    }

    public int getsMeiyu() {
        return sMeiyu;
    }

    public void setsMeiyu(int sMeiyu) {
        this.sMeiyu = sMeiyu;
    }

    public int getsLaoyu() {
        return sLaoyu;
    }

    public void setsLaoyu(int sLaoyu) {
        this.sLaoyu = sLaoyu;
    }

    @Override
    public String toString() {
        return "student_score{" +
                "sDeyu=" + sDeyu +
                ", sZhiyu=" + sZhiyu +
                ", sTiyu=" + sTiyu +
                ", sMeiyu=" + sMeiyu +
                ", sLaoyu=" + sLaoyu +
                '}';
    }
}
