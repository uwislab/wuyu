package com.fiveup.core.teacher.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiveup.core.management.pojo.PageDto;
import com.fiveup.core.teacher.pojo.PageDto1;
import com.fiveup.core.teacher.entity.TeacherList;
import com.fiveup.core.teacher.entity.teacher;
import com.fiveup.core.teacher.mapper.teacherFiveupMapper;
import com.fiveup.core.teacher.mapper.JumpingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Service
public class teacherFiveupService extends ServiceImpl<teacherFiveupMapper, teacher> {
    @Autowired
    private JumpingMapper mapper;
    @Autowired
    private teacherFiveupMapper teacherMapper;

    public boolean saveUser(teacher teacher){
        return saveOrUpdate(teacher);
    }

    public teacher searchTeacherById(String id) {
        return teacherMapper.selectById(id);
    }

    public TeacherList getTeacherByPage(PageDto1 dto, long schoolId) {
        //如果页码异常
        if (dto.getPageNum()<=0) {
            dto.setPageNum(1);
        }
        if (dto.getPageSize()<=0) {
            dto.setPageSize(1);
        }

        List<Long> classList = new ArrayList<>();
//        //判断是否有年级模糊查询
//        if (dto.getGrade()!=null&&!dto.getGrade().trim().equals("")){
//            classList = mapper.getClassByGrade(dto.getGrade());//如果有,获取该年级的所有的班级信息
//            System.out.println("输出list:"+classList);
//        }

        TeacherList vo = new TeacherList();
        int pageNum = dto.getPageNum();//当前请求页码

        int pageSize = dto.getPageSize();
        //查询所有数据
        //如果有年级查询,给定该年级的所有班级,判断该年级的老师有哪些,同时只会查询出老师该年级所教的班级,而不会查出其它年级的班级
        List<List<?>> list = new ArrayList<>();
        List<teacher>  teacherVos = mapper.getTeacherByPage(schoolId,dto,classList);
        list.add(teacherVos);
        List<Integer> teacherCount = mapper.getTotalTeacherCount();
        list.add(teacherCount);
        List<teacher> temp = (List<teacher>) list.get(0);//所有符合条件的记录

        Map<Integer,teacher> map = new TreeMap<>();
        for (teacher item:temp){
            map.put(item.getId(),item);
        }
        temp = new ArrayList<>(map.values());
        //去重,因为班级存在,可能存在多个相同教师但不同班级的记录
//        temp=temp.stream().collect(Collectors.collectingAndThen(
//                Collectors.toCollection(() -> new TreeSet<>(//先收集起来然后比较id,最后组成一个新的数组
//                        Comparator.comparing(o -> o.getId())
//                )), ArrayList::new));

        List<teacher> result = new ArrayList<>();
        int total = temp.size();//总记录条数,从过滤后的数组中获得,否则分页显示异常
        vo.setTotal(total);
        //判断可以分多少页
        int sum = total/pageSize;
        //设置总页数
        if (sum>=vo.getPages())
            vo.setPages(vo.getTotal()%pageSize != 0 ? sum+1 : sum);

        //获取该页显示的数据
        if (pageNum<=vo.getPages()&&total!=0){
            for (int i = (pageNum-1)*pageSize; i < temp.size() && i<pageNum*pageSize; i++) {

                result.add(temp.get(i));
            }
            //设置当前页码
            vo.setCurPage(pageNum);
        }
        //如果页码超出总页数,可能进行了模糊查询
        if (pageNum>vo.getPages()&&total!=0){
            pageNum = 1;//设置为第一页
            vo.setCurPage(pageNum);//并且需要重新进行查询
            for (int i = 0; i < temp.size() && i<pageNum*pageSize; i++) {
                result.add(temp.get(i));
            }

        }
        //如果没有数据
        if (total==0){
            System.out.println(result);
            vo.setCurPage(1);
            vo.setPages(1);
        }
        //设置搜索结果

        vo.setList(result);

        //设置页面大小
        vo.setPageSize(pageSize);

        //判断是否为最后一页
        if (vo.getPages() == vo.getCurPage()){
            vo.setLast(true);
        }else {
            vo.setLast(false);
        }
        return vo;
    }
}
