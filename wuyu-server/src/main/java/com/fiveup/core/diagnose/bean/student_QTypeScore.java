package com.fiveup.core.diagnose.bean;


public class student_QTypeScore {

    private int easyScore;
    private int mediumScore;
    private int difficuitScore;

    public student_QTypeScore(int easyScore, int mediumScore, int difficuitScore) {
        this.easyScore = easyScore;
        this.mediumScore = mediumScore;
        this.difficuitScore = difficuitScore;
    }

    public student_QTypeScore() {
    }

    public int getEasyScore() {
        return easyScore;
    }

    public void setEasyScore(int easyScore) {
        this.easyScore = easyScore;
    }

    public int getMediumScore() {
        return mediumScore;
    }

    public void setMediumScore(int mediumScore) {
        this.mediumScore = mediumScore;
    }

    public int getDifficuitScore() {
        return difficuitScore;
    }

    @Override
    public String toString() {
        return "student_QTypeScore{" +
                "easyScore=" + easyScore +
                ", mediumScore=" + mediumScore +
                ", difficuitScore=" + difficuitScore +
                '}';
    }

    public void setDifficuitScore(int difficuitScore) {
        this.difficuitScore = difficuitScore;
    }
}
