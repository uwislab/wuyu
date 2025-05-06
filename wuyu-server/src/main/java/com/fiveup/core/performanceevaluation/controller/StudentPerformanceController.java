package com.fiveup.core.performanceevaluation.controller;

import com.alibaba.fastjson.JSON;
import com.fiveup.core.commentgeneration.bean.Corpus;
import com.fiveup.core.overallOperation.domain.StudentScore;
import com.fiveup.core.performanceevaluation.bean.StudentPerformance;
import com.fiveup.core.performanceevaluation.dto.PageDto;
import com.fiveup.core.performanceevaluation.service.StudentPerformanceService;
import com.fiveup.core.performanceevaluation.service.SubjectScoreWeightService;
import com.fiveup.core.performanceevaluation.utils.Result;
import com.fiveup.core.performanceevaluation.utils.StudentPerformanceUtil;
import com.fiveup.core.performanceevaluation.vo.AdvantageSubjects;
import com.fiveup.core.performanceevaluation.vo.Average;
import com.fiveup.core.performanceevaluation.vo.StudentPerformanceVO;
import com.fiveup.core.performanceevaluation.vo.SubjectScoreWeightVO;
import com.fiveup.core.remark.entity.studentScore;
import com.fiveup.core.remark.mapper.StudentScoreALLMapper;
import org.python.antlr.op.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理与学生表现相关的请求
 */
@Controller
@CrossOrigin
@RequestMapping("/performance")
public class StudentPerformanceController {

    @Autowired
    private StudentPerformanceService studentPerformanceService;

    @Autowired
    private SubjectScoreWeightService subjectScoreWeightService;

    @Autowired
    StudentScoreALLMapper studentScoreMapper;

    /**
     * 获取所有记录
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/all")
    //tid:教师id，用来判断当前是什么老师
    public String getAllList(Integer tid) {
        Result result = new Result();
        List<StudentPerformanceVO> all = null;
        int count = 0;
        //如果为空，则当前为校长，否则为老师
        if (tid == null) {
            all = studentPerformanceService.getAll();
        } else {
            all = studentPerformanceService.getAllByTidChange(tid);
        }
        count = all.size();
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(all);
        result.setCount(count);
        return JSON.toJSON(result).toString();
    }

    /**
     * 获取某学期某老师的所有学生记录
     */
    @ResponseBody
    @GetMapping("/allByItem")
    //通过学期和老师查询
    public String getAllByItem(Integer tid,String item) {
        Result result = new Result();
        //教师和校长公用一个方法，在service中再进行修改，-1为校长
        if(tid==null) tid=-1;
        List<StudentPerformanceVO> all = null;
        int count = 0;
        int year = Integer.parseInt(item.substring(0,4));
        //将上下学期区分成1和7
        int month = item.charAt(4) == '上' ?1:7;
        //年月连接用于数据库查询
        int year_month = year*100+month;
        //调用方法开始查询
        all = studentPerformanceService.getAllByItem(tid,year_month);
//        System.out.println(year_month);//测试用
//        count = all.size();
        count = 1;
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(all);
        result.setCount(count);
        return JSON.toJSON(result).toString();
    }

    //自动生成评语
    @ResponseBody
    @GetMapping("/autoRemark")
    public String autoRemark(Integer tid,String item) {
        System.out.println(tid+item);
        Result result = new Result();
        List<StudentPerformanceVO> stuNoRemark = null;
        //测试用，可以删除
        ArrayList<String> allRemark = new ArrayList<>(10);

        //还是item转换，因为不希望增加文件数目，因此这个没有改成方法放在工具类里
        int year = Integer.parseInt(item.substring(0,4));
        //将上下学期区分成1和7
        int month = item.charAt(4) == '上' ?1:7;
        //年月连接用于数据库查询
        int year_month = year*100+month;

        //拿到了没有评语的学生信息
        if (tid == null) {
            stuNoRemark = studentPerformanceService.AutoRemark(-1, year_month);
        } else {
            stuNoRemark = studentPerformanceService.AutoRemark(tid, year_month);
        }
        //没有相应数据，尝试节约时间
        if(stuNoRemark.isEmpty()){
            result.setCode(600);
            result.setMsg("生成评语失败：没有需要生成评语的学生");
            result.setData(stuNoRemark);
            result.setCount(0);
            System.out.println(allRemark);
            return JSON.toJSON(result).toString();
        }
        //准备获取语料库
        List<Corpus> corpusList = new ArrayList<>();

        Average averageByTid = studentPerformanceService.getAverageByTid(tid);
        System.out.println("班级平均分查看"+averageByTid);
        //导出教师的学生们的平均分
        double virtue = averageByTid.getVirtue().doubleValue();
        double art= averageByTid.getArt().doubleValue();
        double intelligence= averageByTid.getIntelligence().doubleValue();
        double labor= averageByTid.getLabor().doubleValue();
        double sports= averageByTid.getSports().doubleValue();
        double avg = (virtue+art+intelligence+labor+sports)/5.0;
        //综合分

        //根据语料库生成对应的评语
        for (StudentPerformanceVO stu : stuNoRemark) {
            StringBuilder remarkTemp = new StringBuilder("学生" + stu.getName() + "你好：");//临时评论
            //生成有关平均分的评语：
            remarkTemp.append("你的成绩情况如下：");
            remarkTemp.append(StudentPerformanceUtil.compareAvg(stu.getTotalScore().doubleValue(), avg, "综合分")).append("。");
            remarkTemp.append(StudentPerformanceUtil.compareAvg(stu.getVirtue().doubleValue(), virtue, "德育分")).append("。");
            remarkTemp.append(StudentPerformanceUtil.compareAvg(stu.getIntelligence().doubleValue(), intelligence, "平均分")).append("。");
            remarkTemp.append(StudentPerformanceUtil.compareAvg(stu.getSports().doubleValue(), sports, "体育分")).append("。");
            remarkTemp.append(StudentPerformanceUtil.compareAvg(stu.getArt().doubleValue(), art, "美育分")).append("。");
            remarkTemp.append(StudentPerformanceUtil.compareAvg(stu.getLabor().doubleValue(), labor, "劳育分")).append("。");
            remarkTemp.append("\n你");
            //通过学科代码查询当前学科的语料库
            corpusList = studentPerformanceService.getCorpusBySubject(5);
            for (Corpus corpus : corpusList) {
                if (stu.getTotalScore().doubleValue() > corpus.getScore().doubleValue() && stu.getTotalScore().doubleValue() <= corpus.getScore().doubleValue() + 9.0) {
                    remarkTemp.append(corpus.getComment());
                    remarkTemp.append("、");
                }
            }
            //德
            corpusList = studentPerformanceService.getCorpusBySubject(1);
            for (Corpus corpus : corpusList) {
                if (stu.getVirtue() > corpus.getScore() && stu.getVirtue() <= corpus.getScore() + 9) {
                    remarkTemp.append(corpus.getComment());
                    remarkTemp.append("、");
                }
            }
            //智
            corpusList = studentPerformanceService.getCorpusBySubject(2);
            for (Corpus corpus : corpusList) {
                if (stu.getIntelligence() > corpus.getScore() && stu.getIntelligence() <= corpus.getScore() + 9) {
                    remarkTemp.append(corpus.getComment());
                    remarkTemp.append("、");
                }
            }
            //体
            corpusList = studentPerformanceService.getCorpusBySubject(3);
            for (Corpus corpus : corpusList) {
                if (stu.getSports() > corpus.getScore() && stu.getSports() <= corpus.getScore() + 9) {
                    remarkTemp.append(corpus.getComment());
                    remarkTemp.append("、");
                }
            }
            //美
            corpusList = studentPerformanceService.getCorpusBySubject(4);
            for (Corpus corpus : corpusList) {
                if (stu.getArt() > corpus.getScore() && stu.getArt() <= corpus.getScore() + 9) {
                    remarkTemp.append(corpus.getComment());
                    remarkTemp.append("、");
                }
            }
            //劳
            corpusList = studentPerformanceService.getCorpusBySubject(5);
            for (Corpus corpus : corpusList) {
                if (stu.getLabor() > corpus.getScore() && stu.getLabor() <= corpus.getScore() + 9) {
                    remarkTemp.append(corpus.getComment());
                    remarkTemp.append("、");
                }
            }
            remarkTemp.append("希望你再接再厉，继续努力！\n");
            //将生成的评语插入到该学生的评价数据中
            studentScore studentScore = new studentScore();
            studentScore.setId(stu.getId());
            studentScore.setRemark(remarkTemp.toString());
            studentScoreMapper.updateById(studentScore);
            //以下是用于测试用，泡在后端控制的内容，可以不使用
            allRemark.add(remarkTemp.toString());
//            System.out.println("生成的评语是:"+remarkTemp);
        }
        int count = 0;
        count = stuNoRemark.size();
        result.setCode(600);
        result.setMsg("生成成功，请刷新界面进行查看！");
        result.setData(stuNoRemark);
        result.setCount(count);
        System.out.println(allRemark);
        return JSON.toJSON(result).toString();
    }

    /**
     * 学期获取,将数据库中202412类似的数据提取出来，稍加修改作为前端下拉菜单的选项
     *
     * @return 学期
     */
    @ResponseBody
    @GetMapping("/getItem")
    public String getItem() {
        Result result = new Result();
        List<String> item;
        int count = 0;
        //获取评语生成学期
        item = studentPerformanceService.getItem();
        // 自定义排序规则
        item.sort((s1, s2) -> {
            int year1 = Integer.parseInt(s1.substring(0, 4));
            int year2 = Integer.parseInt(s2.substring(0, 4));

            // 年份不同，按年份排序
            if (year1 != year2) {
                return year1 - year2;
            }
            // 年份相同，按学期排序：上学期排在下学期前
            String semester1 = s1.substring(4);
            String semester2 = s2.substring(4);

            return semester1.equals("上学期") ? -1 : 1; // 上学期排在前面
        });

            count = item.size();
            result.setCode(600);
            result.setMsg("查询成功！");
            result.setData(item);
            result.setCount(count);
            return JSON.toJSON(result).toString();
}
    /**
     * 根据分页获取
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public String getAllList(@RequestBody PageDto pageDto) {
        Result result = new Result();
        // 获取记录数
        String sql = "select count(id) from student_performance";
        Integer count = studentPerformanceService.getCount(sql);

        // 根据分页数据查询值
        List<StudentPerformanceVO> list = studentPerformanceService.getPagination(
                (pageDto.getCurrentPage() - 1) * pageDto.getPageSize(),
                        pageDto.getPageSize());
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(list);
        result.setCount(count);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @GetMapping("/averageGrade/{tid}")
    public String getAverageGrade(@PathVariable("tid")Integer teacherId) {
        Result result = new Result();
        // 根据分页数据查询值
        Integer[] grade = studentPerformanceService.getAverageGrade(teacherId);
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(grade);
        return JSON.toJSON(result).toString();
    }

    /**
     * 根据分页获取
     * @return
     */
    @ResponseBody
    @PostMapping("/list/teacher/{tid}")
    public String getAllList(@RequestBody PageDto pageDto,@PathVariable("tid")Integer teacherId) {
        Result result = new Result();
        // 获取记录数
        Integer count = studentPerformanceService.getCountByTeacher(teacherId);

        // 根据分页数据查询值
        List<StudentPerformanceVO> list = studentPerformanceService.getPaginationByTeacherId(
                teacherId,
                (pageDto.getCurrentPage() - 1) * pageDto.getPageSize(),
                pageDto.getPageSize());
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(list);
        result.setCount(count);
        return JSON.toJSON(result).toString();
    }

    /**
     * 验证学号是否存在
     * @return
     */
    @ResponseBody
    @GetMapping("/exist")
    public String exist(Integer sid) {
        Result result = new Result();
        // 查询是否存在
        StudentPerformanceVO bySid = studentPerformanceService.getBySid(sid);
        result.setCode(600);
        result.setMsg("查询成功！");
        result.setData(bySid);
        return JSON.toJSON(result).toString();
    }


    /**
     * 增加数据
     * @param studentPerformance
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public String add(@RequestBody StudentPerformance studentPerformance){
        Result result = new Result();

        // 增加
        studentPerformanceService.add(studentPerformance);

        // 查询封装后的数据
        StudentPerformanceVO bySid = studentPerformanceService.getById(studentPerformance.getId());
        result.setCode(600);
        result.setData(bySid);
        result.setMsg("增加成功");
        return JSON.toJSON(result).toString();
    }

    /**
     * 更新数据
     * @param studentPerformance
     * @return
     */
    @ResponseBody
    @PutMapping("/update")
    public String update(@RequestBody StudentPerformance studentPerformance){
        Result result = new Result();

        if(studentPerformance.getTotalScore() != null) {
            // 已经生成过总评成绩了，需要重新计算
            // 查询该学生成绩对应的老师权重
            SubjectScoreWeightVO byTId = subjectScoreWeightService.getByTId(studentPerformance.getTid());

            // 生成总评成绩
            BigDecimal bigDecimal = StudentPerformanceUtil.computeTotalScore(studentPerformance, byTId);

            // 设置总评成绩
            studentPerformance.setTotalScore(bigDecimal);
        }

        // 更新
        studentPerformanceService.updateById(studentPerformance);

        // 查询更新后的数据
        StudentPerformanceVO byId = studentPerformanceService.getById(studentPerformance.getId());
        result.setCode(600);
        result.setData(byId);
        result.setMsg("更新成功！");
        return JSON.toJSON(result).toString();
    }


    /**
     * 删除数据
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/delete")
    public String delete(Integer id) {
        Result result =  new Result();
        // 删除
        // studentPerformanceService.deleteById(id);
        studentScore stu = new studentScore();
        stu.setId(id);
        stu.setRemark("");
        System.out.println(stu);
        studentScoreMapper.updateById(stu);
        result.setCode(600);
        result.setMsg("删除成功！");
        return JSON.toJSON(result).toString();
    }


    /**
     * 生成总评成绩
     * @param studentPerformance
     * @return
     */
    @ResponseBody
    @PutMapping("/generate")
    public String generateTotalScore(@RequestBody StudentPerformance studentPerformance){
        Result result = new Result();

        // 查询该学生成绩对应的老师权重
        SubjectScoreWeightVO byTId = subjectScoreWeightService.getByTId(studentPerformance.getTid());

        // 生成总评成绩
        BigDecimal bigDecimal = StudentPerformanceUtil.computeTotalScore(studentPerformance, byTId);

        // 设置总评成绩
        studentPerformance.setTotalScore(bigDecimal);
        // 更新
        studentPerformanceService.updateById(studentPerformance);

        // 查询更新后的数据
        StudentPerformanceVO byId = studentPerformanceService.getById(studentPerformance.getId());
        result.setCode(600);
        result.setData(byId);
        result.setMsg("更新成功！");
        return JSON.toJSON(result).toString();
    }


    /**
     * 根据教师ID查询对应的5个方面平均分对象
     * @param tid
     * @return
     */
    @ResponseBody
    @GetMapping("/avg")
    public String getAverage(Integer tid) {
        Result result = new Result();

        // 查询
        Average averageByTid = studentPerformanceService.getAverageByTid(tid);
        averageByTid.setTid(tid);

        result.setCode(600);
        result.setData(averageByTid);
        result.setMsg("查询成功！");

        return JSON.toJSON(result).toString();
    }

    /**
     * 验证学号对应的数据信息
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public String get(Integer sid) {
        Result result = new Result();
        // 查询数据
        StudentPerformanceVO bySid = studentPerformanceService.getBySid(sid);
        if(bySid != null) {
            result.setCode(600);
            result.setMsg("查询成功！");
            result.setData(bySid);
        } else {
            result.setCode(602);
            result.setMsg("该学号对应的数据信息不存在！");
        }
        return JSON.toJSON(result).toString();
    }


    /**
     * 根据老师ID查询所在班级的所有同学信息
     * @param tid
     * @return
     */
    @ResponseBody
    @GetMapping("/class")
    public String getList(Integer tid) {
        Result result = new Result();

        // 查询数据
        List<StudentPerformanceVO> byTId = studentPerformanceService.getByTId(tid);
        result.setCode(600);
        result.setCount(byTId.size());
        result.setData(byTId);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 查询教师ID对应的除查询的学生之外的所有学生的总数
     * @param tid
     * @param sid
     * @return
     */
    @ResponseBody
    @GetMapping("/sum")
    public String sum(Integer tid, Integer sid) {
        Result result = new Result();

        // 查询5个方面和数据
        List<Integer> subjectScoreSum = studentPerformanceService.getSubjectScoreSum(tid, sid);
        result.setCode(600);
        result.setData(subjectScoreSum);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }

    /**
     * 查询教师ID对应的班级优势科目人数
     * @param tid
     * @return
     */
    @ResponseBody
    @GetMapping("/as")
    public String getAdvantageSubject(Integer tid,Integer score) {
        Result result = new Result();

        // 所需字段
        String statisticalFields[] = new String[]
                {"virtue", "intelligence", "sports", "art", "labor"};
        Integer nums[] = new Integer[6];

        // 创建一个优势科目对象
        for (int i = 0; i < statisticalFields.length; i++) {
            String sql = "select count(" + statisticalFields[i] + ") from student_performance "
                    + "where " + statisticalFields[i] + " >= "+ score +" and tid = " + tid;
            nums[i] = studentPerformanceService.getCount(sql);
        }

        String sql = "select count(id) from student_performance where tid = " + tid;
        nums[5] = studentPerformanceService.getCount(sql);

        AdvantageSubjects advantageSubjects = new AdvantageSubjects();
        // 设置值
        advantageSubjects.setVirtue(nums[0]);
        advantageSubjects.setIntelligence(nums[1]);
        advantageSubjects.setSports(nums[2]);
        advantageSubjects.setArt(nums[3]);
        advantageSubjects.setLabor(nums[4]);
        advantageSubjects.setTotalNum(nums[5]);

        result.setCode(600);
        result.setData(advantageSubjects);
        result.setMsg("查询成功！");
        return JSON.toJSON(result).toString();
    }
}
