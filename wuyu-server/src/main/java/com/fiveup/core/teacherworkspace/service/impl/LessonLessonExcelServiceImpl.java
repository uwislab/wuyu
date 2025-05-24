package com.fiveup.core.teacherworkspace.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fiveup.core.common.exception.ApiException;
import com.fiveup.core.performanceevaluation.mapper.TeacherMapper;
import com.fiveup.core.teacherworkspace.common.constant.CommonMessage;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.bo.LessonExcelBo;
import com.fiveup.core.teacherworkspace.model.bo.LessonFailExportBo;
import com.fiveup.core.teacherworkspace.model.vo.ExcelImportVo;
import com.fiveup.core.teacherworkspace.service.LessonExcelService;
import com.fiveup.core.teacherworkspace.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonLessonExcelServiceImpl implements LessonExcelService {
    private final RedisTemplate<Object, Object> redisTemplate;
    private final TeacherMapper teacherMapper;
    private final LessonService lessonService;

    @Override
    public ExcelImportVo<LessonExcelBo, LessonFailExportBo> tryImport(List<LessonExcelBo> list) {
        List<LessonExcelBo> sucessList = new ArrayList<>();
        List<LessonFailExportBo> failList = new ArrayList<>();
        //校验数据
        for (LessonExcelBo lesson : list) {
            String failMsg = null;
            //判断数据是否为空
            if (CharSequenceUtil.isBlank(lesson.getClassName())) {
                failMsg = "班级名不能为空";
            } else if (lesson.getGrade() == null) {
                failMsg = "年级不能为空";
            } else if (CharSequenceUtil.isBlank(lesson.getCourse())) {
                failMsg = "课程名不能为空";
            } else if (lesson.getClassNum() == null) {
                failMsg = "班级不能为空";
            } else if (CharSequenceUtil.isBlank(lesson.getTeacherName())) {
                failMsg = "任课老师不能为空";
            } else if (teacherMapper.getTeach(lesson.getTeacherId(), lesson.getTeacherName()) == null) {
                failMsg = "任课老师不存在";
            }
            //判断数据是否合法
            if (failMsg != null) {
                LessonFailExportBo userFailExportBo = BeanUtil.copyProperties(lesson, LessonFailExportBo.class);
                userFailExportBo.setFailReason(failMsg);
                failList.add(userFailExportBo);
            } else {
                sucessList.add(lesson);
            }
        }
        Long id = IdWorker.getId();
        ExcelImportVo<LessonExcelBo, LessonFailExportBo> excelImportVo = new ExcelImportVo<>();
        //成功数据
        if (!sucessList.isEmpty()) {
            //成功数据放入redis数据库中
            redisTemplate.opsForValue().set(CommonMessage.Cache.LESSON_EXCEL_SUCCESS.getKey(id.toString()), sucessList, 5, TimeUnit.MINUTES);
        }
        //失败数据
        if (!failList.isEmpty()) {
            //失败数据放入redis数据库中
            redisTemplate.opsForValue().set(CommonMessage.Cache.LESSON_EXCEL_FAIL.getKey(id.toString()), failList, 5, TimeUnit.MINUTES);
        }
        return excelImportVo.setId(id)
                .setSuccessList(sucessList)
                .setSuccessNumber(sucessList.size())
                .setFailList(failList)
                .setFailNumber(failList.size());
    }

    @Override
    public void confirmImport(String id) {
        //从redis中获取数据
        List<Map<String, Object>> list = (List<Map<String, Object>>) redisTemplate.opsForValue().get(CommonMessage.Cache.LESSON_EXCEL_SUCCESS.getKey(id));
        if (list == null) {
            throw new ApiException("导入的成功数据不存在或已失效");
        } else if (list.isEmpty()) {
            return;
        }
        List<Lesson> lessons = new ArrayList<>();
        for (Map<String, Object> map : list) {
            LessonExcelBo lessonExcelBo = BeanUtil.toBean(map, LessonExcelBo.class);
            Lesson newLesson = BeanUtil.copyProperties(lessonExcelBo, Lesson.class);
            newLesson.setTeacherId(lessonExcelBo.getTeacherId());
            // 查询是否已存在教师
            Lesson lesson = lessonService.getOne(
                    new LambdaQueryWrapper<Lesson>()
                            .eq(Lesson::getTeacherId, lessonExcelBo.getTeacherId())
                            .eq(Lesson::getGrade, lessonExcelBo.getGrade())
                            .eq(Lesson::getClassNum, lessonExcelBo.getClassNum())
                            .eq(Lesson::getCourse, lessonExcelBo.getCourse())
            );
            if (lesson != null) {
                // 存在更新原课程信息
                newLesson.setId(lesson.getId());
                lessonService.updateById(newLesson);
            } else {
                // 不存在存入数组批量添加
                lessons.add(newLesson);
            }
        }
        //批量插入
        lessonService.saveBatch(lessons);
        //删除缓存
        redisTemplate.delete(CommonMessage.Cache.LESSON_EXCEL_SUCCESS.getKey(id));
    }

    @Override
    public List<LessonFailExportBo> getFailExportList(String id) {
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) redisTemplate.opsForValue().get(CommonMessage.Cache.LESSON_EXCEL_FAIL.getKey(id));
        if (mapList == null) {
            throw new ApiException("导出的失败数据不存在或已失效");
        }
        //获取数据
        return mapList
                .stream().map(map -> BeanUtil.toBean(map, LessonFailExportBo.class))
                .collect(Collectors.toList());
    }
}
