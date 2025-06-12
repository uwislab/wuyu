package com.fiveup.core.teacherworkspace.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fiveup.core.common.exception.ApiException;
import com.fiveup.core.performanceevaluation.mapper.TeacherMapper;
import com.fiveup.core.teacherworkspace.common.constant.CommonMessage;
import com.fiveup.core.teacherworkspace.common.utils.RegexVerifyUtils;
import com.fiveup.core.teacherworkspace.model.Lesson;
import com.fiveup.core.teacherworkspace.model.bo.LessonExcelBo;
import com.fiveup.core.teacherworkspace.model.bo.LessonFailExportBo;
import com.fiveup.core.teacherworkspace.model.vo.ExcelImportVo;
import com.fiveup.core.teacherworkspace.service.LessonExcelService;
import com.fiveup.core.teacherworkspace.service.LessonService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonLessonExcelServiceImpl implements LessonExcelService {
    private final TeacherMapper teacherMapper;
    private final LessonService lessonService;
    private final Cache<String, String> cache;

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
                failMsg = "年级不能为空，格式为年级数字";
            } else if (CharSequenceUtil.isBlank(lesson.getCourse())) {
                failMsg = "课程名不能为空";
            } else if (lesson.getClassNum() == null) {
                failMsg = "班级不能为空，格式为班级数字";
            } else if (CharSequenceUtil.isBlank(lesson.getTeacherName())) {
                failMsg = "任课老师不能为空";
            } else if (CharSequenceUtil.isBlank(lesson.getAcademicYear())) {
                failMsg = "学年不能为空，格式为xxxx-xxxx";
            } else if (lesson.getSemester() == null) {
                failMsg = "学期不能为空，格式为学期数字，1、第一学期，2、第二学期";
            } else if (!RegexVerifyUtils.validAcademicYear(lesson.getAcademicYear())) {
                failMsg = "学年格式错误，格式为xxxx-xxxx，例如2022-2023";
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
            cache.put(CommonMessage.Cache.LESSON_EXCEL_SUCCESS.getKey(id.toString()), JSON.toJSONString(sucessList));
        }
        //失败数据
        if (!failList.isEmpty()) {
            //失败数据放入redis数据库中
            cache.put(CommonMessage.Cache.LESSON_EXCEL_FAIL.getKey(id.toString()), JSON.toJSONString(failList));
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
        String listString = cache.getIfPresent(CommonMessage.Cache.LESSON_EXCEL_SUCCESS.getKey(id));
        List<LessonExcelBo> list = JSON.parseArray(listString, LessonExcelBo.class);
        if (list == null) {
            throw new ApiException("导入的成功数据不存在或已失效");
        } else if (list.isEmpty()) {
            return;
        }
        List<Lesson> lessons = new ArrayList<>();
        for (LessonExcelBo lessonExcelBo : list) {
            Lesson newLesson = BeanUtil.copyProperties(lessonExcelBo, Lesson.class);
            newLesson.setIsCurrent(false);
            // 查询是否已存在教师
            Lesson lesson = lessonService.getOne(
                    new LambdaQueryWrapper<Lesson>()
                            .eq(Lesson::getTeacherId, lessonExcelBo.getTeacherId())
                            .eq(Lesson::getGrade, lessonExcelBo.getGrade())
                            .eq(Lesson::getClassNum, lessonExcelBo.getClassNum())
                            .eq(Lesson::getCourse, lessonExcelBo.getCourse())
                            .eq(Lesson::getAcademicYear, lessonExcelBo.getAcademicYear())
                            .eq(Lesson::getSemester, lessonExcelBo.getSemester())
            );
            if (lesson != null) {
                // 存在更新原课程信息
                newLesson.setId(lesson.getId());
                newLesson.setIsCurrent(lesson.getIsCurrent());
                lessonService.updateById(newLesson);
            } else {
                // 不存在存入数组批量添加
                lessons.add(newLesson);
            }
        }
        //批量插入
        lessonService.saveBatch(lessons);
        //删除缓存
        cache.asMap().keySet().removeIf(key -> key.equals(CommonMessage.Cache.LESSON_EXCEL_SUCCESS.getKey(id)));
    }

    @Override
    public List<LessonFailExportBo> getFailExportList(String id) {
        String listString = cache.getIfPresent(CommonMessage.Cache.LESSON_EXCEL_FAIL.getKey(id));
        if (listString == null) {
            throw new ApiException("导出的失败数据不存在或已失效");
        }
        //获取数据
        return JSON.parseArray(listString, LessonFailExportBo.class);
    }
}
