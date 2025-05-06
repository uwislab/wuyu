package com.fiveup.core.commentgeneration.utils;

import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.commentgeneration.bean.Subject;
import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.vo.Average;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评价模板
 */
public class RemarkUtil {
    // 班级平均分
    private Average average;
    // 语料库
    private List<Corpus> corpus;
    // 科目列表
    private List<Subject> subjects;

    public RemarkUtil() {
    }

    public RemarkUtil(Average average, List<Corpus> corpus, List<Subject> subjects) {
        this.average = average;
        this.corpus = corpus;
        this.subjects = subjects;
    }

    public Average getAverage() {
        return average;
    }

    public void setAverage(Average average) {
        this.average = average;
    }

    public List<Corpus> getCorpus() {
        return corpus;
    }

    public void setCorpus(List<Corpus> corpus) {
        this.corpus = corpus;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // 生成科目评语（与班级平均分为基准）
    public String generateSubjectRemark(String subjectName,BigDecimal diff, String comment) {
        String remark = subjectName + "成绩";
        int virtue = diff.compareTo(new BigDecimal(0));
        if(virtue > 0) {
            remark += "高于班级平均成绩" + String.format("%.2f", diff) + "分，该生" + comment;
        } else if(virtue == 0) {
            remark += "达到班级平均成绩，该生" + comment;
        } else {
            remark += "低于班级平均成绩" + String.format("%.2f", diff.abs()) + "分，该生" + comment;
        }
        return remark;
    }

    // 分数对应的评价
    public String getComment(Integer subjectId, BigDecimal score) {
        String comment = "";
        BigDecimal basic = new BigDecimal(0);
        List<Corpus> subjectCorpus = this.corpus.stream().filter(c -> c.getSubjectId() == subjectId).collect(Collectors.toList());
        for (Corpus c : subjectCorpus) { //依次遍历，例如86大于80  80则为基准分， 86大于85， 85则为新的基准分
            BigDecimal corpusScore = new BigDecimal(c.getScore());
            if(score.compareTo(corpusScore) >= 0 && corpusScore.compareTo(basic) > 0) {
                comment = c.getComment();
                basic = corpusScore;
            }
        }

        if("".equals(comment)) {
            // 假如低于60分
            comment = "不及格";
        }
        return comment;
    }

    // 生成班级评语
    public String generateClassRemark() {
        String classRemark = "";

        List<String> subjectsName = this.subjects.stream().map(Subject::getName).collect(Collectors.toList());
        // 德育
        String virtue = this.getComment(this.subjects.get(1).getId(), this.average.getVirtue());
        classRemark +="该班级"+ subjectsName.get(1) +"方面"+ virtue + ",";
        // 智育
        String intelligence = this.getComment(this.subjects.get(2).getId(), this.average.getIntelligence());
        classRemark += subjectsName.get(2) +"方面"+ intelligence + ",";
        // 体育
        String sports = this.getComment(this.subjects.get(3).getId(), this.average.getSports());
        classRemark += subjectsName.get(3) +"方面"+ sports + ",";
        // 美育
        String art = this.getComment(this.subjects.get(4).getId(), this.average.getArt());
        classRemark += subjectsName.get(4) +"方面"+ art + ",";
        // 劳育
        String labor = this.getComment(this.subjects.get(5).getId(), this.average.getLabor());
        classRemark += subjectsName.get(5) +"方面"+ labor + "。";
        return classRemark;
    }

    // 生成所有评语
    public String generatePersonalRemark(StudentPerformance studentPerformance) {
        String remark = "";

        //stream只能被“消费”一次，一旦遍历过就会失效，就像容器的迭代器那样，想要再次遍历必须重新生成
        //map():用于映射每个元素到对应的结果。
        //filter():filter 方法用于通过设置的条件过滤出元素。
        //Collectors.toList() 用来结束Stream流
        remark += "亲爱的"+studentPerformance.getName()+"同学:\n";
        List<String> subjectsName = this.subjects.stream().map(Subject::getName).collect(Collectors.toList());
        // 德育
        String virtue = this.getComment(this.subjects.get(1).getId(),
                new BigDecimal(studentPerformance.getVirtue()));
        remark += this.generateSubjectRemark(subjectsName.get(1),
                new BigDecimal(studentPerformance.getVirtue()).subtract(this.average.getVirtue()),virtue) + "。\n";
        // 智育
        String intelligence = this.getComment(this.subjects.get(2).getId(), new BigDecimal(studentPerformance.getIntelligence()));
        remark += this.generateSubjectRemark(subjectsName.get(2),
                new BigDecimal(studentPerformance.getIntelligence()).subtract(this.average.getIntelligence())
                ,intelligence) + "。\n";
        // 体育
        String sports = this.getComment(this.subjects.get(3).getId(), new BigDecimal(studentPerformance.getSports()));
        remark += this.generateSubjectRemark(subjectsName.get(3),
                new BigDecimal(studentPerformance.getSports()).subtract(this.average.getSports())
                ,sports) + "。\n";
        // 美育
        String art = this.getComment(this.subjects.get(4).getId(), new BigDecimal(studentPerformance.getArt()));
        remark += this.generateSubjectRemark(subjectsName.get(4),
                new BigDecimal(studentPerformance.getArt()).subtract(this.average.getArt())
                ,art) + "。\n";
        // 劳育
        String labor = this.getComment(this.subjects.get(5).getId(), new BigDecimal(studentPerformance.getLabor()));
        remark += this.generateSubjectRemark(subjectsName.get(5),
                new BigDecimal(studentPerformance.getLabor()).subtract(this.average.getLabor()),labor) + "。";
        return remark;
    }
}
