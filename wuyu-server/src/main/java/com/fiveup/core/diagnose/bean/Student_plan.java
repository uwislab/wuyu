package com.fiveup.core.diagnose.bean;

public class Student_plan {

    private Long studentId;
    private String studentName;
    private String studentClassNumber;
    private String studentGrade;
    private Integer pDeyu;
    private Integer pZhiyu;
    private  Integer pTiyu;
    private  Integer pMeiyu;
    private  Integer pLaoyu;
    private  String pPlan;

    public Student_plan(Long studentId, String studentName, String studentClassNumber, String studentGrade, Integer pDeyu, Integer pZhiyu, Integer pTiyu, Integer pMeiyu, Integer pLaoyu, String pPlan) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentClassNumber = studentClassNumber;
        this.studentGrade = studentGrade;
        this.pDeyu = pDeyu;
        this.pZhiyu = pZhiyu;
        this.pTiyu = pTiyu;
        this.pMeiyu = pMeiyu;
        this.pLaoyu = pLaoyu;
        this.pPlan = pPlan;
    }

    public Student_plan() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClassNumber() {
        return studentClassNumber;
    }

    public void setStudentClassNumber(String studentClassNumber) {
        this.studentClassNumber = studentClassNumber;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public Integer getpDeyu() {
        return pDeyu;
    }

    public void setpDeyu(Integer pDeyu) {
        this.pDeyu = pDeyu;
    }

    public Integer getpZhiyu() {
        return pZhiyu;
    }

    public void setpZhiyu(Integer pZhiyu) {
        this.pZhiyu = pZhiyu;
    }

    public Integer getpTiyu() {
        return pTiyu;
    }

    public void setpTiyu(Integer pTiyu) {
        this.pTiyu = pTiyu;
    }

    public Integer getpMeiyu() {
        return pMeiyu;
    }

    public void setpMeiyu(Integer pMeiyu) {
        this.pMeiyu = pMeiyu;
    }

    public Integer getpLaoyu() {
        return pLaoyu;
    }

    public void setpLaoyu(Integer pLaoyu) {
        this.pLaoyu = pLaoyu;
    }

    public String getpPlan() {
        return pPlan;
    }

    public void setpPlan(String pPlan) {
        this.pPlan = pPlan;
    }

    @Override
    public String toString() {
        return "Student_plan{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", studentClassNumber='" + studentClassNumber + '\'' +
                ", studentGrade='" + studentGrade + '\'' +
                ", pDeyu=" + pDeyu +
                ", pZhiyu=" + pZhiyu +
                ", pTiyu=" + pTiyu +
                ", pMeiyu=" + pMeiyu +
                ", pLaoyu=" + pLaoyu +
                ", pPlan='" + pPlan + '\'' +
                '}';
    }
}
