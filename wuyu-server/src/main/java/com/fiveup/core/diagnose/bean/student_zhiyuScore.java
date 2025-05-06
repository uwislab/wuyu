package com.fiveup.core.diagnose.bean;

public class student_zhiyuScore {
    private int chinese;
    private int math;
    private int english;

    public student_zhiyuScore(int chinese, int math, int english) {
        this.chinese = chinese;
        this.math = math;
        this.english = english;
    }

    public student_zhiyuScore() {
    }

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return "student_zhiyuScore{" +
                "chinese=" + chinese +
                ", math=" + math +
                ", english=" + english +
                '}';
    }
}
