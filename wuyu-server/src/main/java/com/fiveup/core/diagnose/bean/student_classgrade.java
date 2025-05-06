package com.fiveup.core.diagnose.bean;

public class student_classgrade {

    private int studentGrade;
    private int studentClassNumber;

    public student_classgrade(int studentGrade, int studentClassNumber) {
        this.studentGrade = studentGrade;
        this.studentClassNumber = studentClassNumber;
    }

    public student_classgrade() {
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(int studentGrade) {
        this.studentGrade = studentGrade;
    }

    public int getStudentClassNumber() {
        return studentClassNumber;
    }

    public void setStudentClassNumber(int studentClassNumber) {
        this.studentClassNumber = studentClassNumber;
    }

    @Override
    public String toString() {
        return "student_classgrade{" +
                "studentGrade=" + studentGrade +
                ", studentClassNumber=" + studentClassNumber +
                '}';
    }
}
