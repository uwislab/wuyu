package com.fiveup.core.diagnose.bean;



public class student_kpointsScore {

    private String kName;
    private int kProportion;
    private int ksScore;

    public student_kpointsScore(String kName, int kProportion, int ksScore) {
        this.kName = kName;
        this.kProportion = kProportion;
        this.ksScore = ksScore;
    }

    public student_kpointsScore() {
    }

    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }

    public int getkProportion() {
        return kProportion;
    }

    public void setkProportion(int kProportion) {
        this.kProportion = kProportion;
    }

    public int getKsScore() {
        return ksScore;
    }

    public void setKsScore(int ksScore) {
        this.ksScore = ksScore;
    }

    @Override
    public String toString() {
        return "student_kpointsScore{" +
                "kName='" + kName + '\'' +
                ", kProportion=" + kProportion +
                ", ksScore=" + ksScore +
                '}';
    }
}
