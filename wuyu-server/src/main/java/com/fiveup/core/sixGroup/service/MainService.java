package com.fiveup.core.sixGroup.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.sixGroup.entity.*;
import com.fiveup.core.sixGroup.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MainService {
    @Autowired
    private ClassMapper1 classMapper1;
    @Autowired
    private ItemContentMapper itemContentMapper;
    @Autowired
    private SportItemMapper1 sportItemMapper1;
    @Autowired
    private StudentScoreMapper1 studentScoreMapper1;
    @Autowired
    private StuPhyScoreMapper stuPhyScoreMapper;
    @Autowired
    private StudentMapper1 studentMapper1;
    @Autowired
    private StuIcScoreMapper icScoreMapper;
    public Object 获取三级评价指标(){
        QueryWrapper<FuItemContent> q = new QueryWrapper<>();
        q.eq("item_level", 3);
        List<FuItemContent> items = itemContentMapper.selectList(q);
        return items;
    }
    public void 添加编辑指标成绩(SixStuIcScore data) {
        if(data.getId() != null){
            // update
            icScoreMapper.updateById(data);
        } else{
            icScoreMapper.insert(data);
        }
    }
    public void mockData(){
        List<BasicStudent> basicStudents = studentMapper1.selectList(null);
        List<BasicSportItem> sportItems = 获取体育项目();
        List<FuItemContent> itemContents = 获取非一级评价指标();

        // mock 1
        ArrayList<SixStuPhyScore> mockData_1 = new ArrayList<>();
        basicStudents.forEach(stu->{
            sportItems.forEach(s->{
                SixStuPhyScore i = new SixStuPhyScore();

                Double score = Math.random() * 50 + 50;
                i.setScore(score.intValue());
                i.setStudentNum(stu.getStudentNum());
                i.setPhyItemId(s.getItemId());

                mockData_1.add(i);
            });
        });

        // mock 2
//        ArrayList<SixStuIcScore> mockData_2 = new ArrayList<>();
//        basicStudents.forEach(i->{
//            itemContents.forEach(j->{
//                SixStuIcScore i2 = new SixStuIcScore();
//
//                // mock 出的成绩不应该超过这条指标的总分
//                Integer itemScore = j.getItemScore();
//                // 最低分阈值
//
//                Double v = Math.random() * itemScore;
//                i2.setScore(v.intValue());
//                i2.setStuId(i.getId());
//                i2.setIcId(j.getId());
//
//                mockData_2.add(i2);
//            });
//        });

        // 入库
        mockData_1.forEach(i->{
            stuPhyScoreMapper.insert(i);
        });
//        mockData_2.forEach(i->{
//            icScoreMapper.insert(i);
//        });

    }

    public Object 获取一级评价指标(){
        QueryWrapper<FuItemContent> q = new QueryWrapper<>();
        q.eq("item_level", 1);
        List<FuItemContent> items = itemContentMapper.selectList(q);
        return items;
    }
    public Object 获取年级班级信息(){
        QueryWrapper<BasicClass> q = new QueryWrapper<>();
        q.orderByAsc("grade", "class");
        List<BasicClass> items = classMapper1.selectList(q);
        return items;
    }
    public List<FuItemContent> 获取非一级评价指标(){
        QueryWrapper<FuItemContent> q = new QueryWrapper<>();
        q.ne("item_level", 1);
        List<FuItemContent> items = itemContentMapper.selectList(q);
        return items;
    }

    public HashMap<String, List<DataRet>> 计算生成图表数据(String 一级指标){
        // 及格率
        List<DataRet> 及格率Arr = itemContentMapper.一级指标下所有班级的指标(一级指标, 0.6f);
        // 优秀率
        List<DataRet> 优秀率Arr = itemContentMapper.一级指标下所有班级的指标(一级指标, 0.8f);
        HashMap<String, List<DataRet>> ret = new HashMap<>();
        ret.put("及格率", 及格率Arr);
        ret.put("优秀率", 优秀率Arr);
        return ret;
    }

    public Object 所有学生(){
        return  studentMapper1.selectList(null);
    }
    public List<BasicSportItem> 获取体育项目(){
        return sportItemMapper1.selectList(null);
    }

    public Object 获取体育成绩列表(){
        return stuPhyScoreMapper.selectList(null);
    }

    public Object 分页获取表格数据(StudentScoreQuery q){
        PageHelper.startPage(q.get当前页(), q.get每页数量());
        List<FuStudentScore> studentScore = studentScoreMapper1.getStudentScore(q);
        PageInfo<FuStudentScore> ret = new PageInfo<>(studentScore);
        return ret;
    }
    public Object 获取指标成绩列表(IndexScoreQuery q){
        PageHelper.startPage(q.get当前页(), q.get每页数量());
        List<Ret01> studentScore = icScoreMapper.listData(q);
        PageInfo<Ret01> ret = new PageInfo<>(studentScore);
        return ret;
    }

}
