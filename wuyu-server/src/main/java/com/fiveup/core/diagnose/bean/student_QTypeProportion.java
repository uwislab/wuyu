package com.fiveup.core.diagnose.bean;

public class student_QTypeProportion {
    private int qtEasy;
    private int qtMedium;
    private int qtDifficuit;

    public student_QTypeProportion(int qtEasy, int qtMedium, int qtDifficuit) {
        this.qtEasy = qtEasy;
        this.qtMedium = qtMedium;
        this.qtDifficuit = qtDifficuit;
    }

    public student_QTypeProportion() {
    }

    public int getQtEasy() {
        return qtEasy;
    }

    public void setQtEasy(int qtEasy) {
        this.qtEasy = qtEasy;
    }

    public int getQtMedium() {
        return qtMedium;
    }

    public void setQtMedium(int qtMedium) {
        this.qtMedium = qtMedium;
    }

    public int getQtDifficuit() {
        return qtDifficuit;
    }

    public void setQtDifficuit(int qtDifficuit) {
        this.qtDifficuit = qtDifficuit;
    }

    @Override
    public String toString() {
        return "student_QTypeProportion{" +
                "qtEasy=" + qtEasy +
                ", qtMedium=" + qtMedium +
                ", qtDifficuit=" + qtDifficuit +
                '}';
    }
}
