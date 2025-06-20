package com.fiveup.core.teacherworkspace.scheduled;

import com.fiveup.core.teacherworkspace.common.utils.AlgorithmUtils;
import com.fiveup.core.teacherworkspace.config.ScheduleConfig;
import com.fiveup.core.teacherworkspace.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class AutoCopyLessonScheduled {
    private final String academicYear;
    private final int semester;
    private final LessonService lessonService;
    private final ScheduleConfig scheduleConfig;

    public AutoCopyLessonScheduled(LessonService lessonService, ScheduleConfig scheduleConfig) {
        // AutoWired
        this.lessonService = lessonService;
        this.scheduleConfig = scheduleConfig;
        // 获取当前学年
        LocalDate date = LocalDate.now();
        this.academicYear = AlgorithmUtils.calculateAcademicYear(date);
        this.semester = AlgorithmUtils.calculateSemester(date);
    }

    /**
     * 每年2月1日和8月1日0点执行
     * 用于学期切换时自动复制课程
     */
    @Scheduled(cron = "0 0 0 1 2,8 ?")
    public void autoCopyLesson() {
        try {
            if (!scheduleConfig.isAutoCopyEnabled()) {
                log.info("自动复制上一学期排课信息已关闭");
                return;
            }
            lessonService.copyLessonByLastSemester(academicYear, semester, true);
            lessonService.setCurrentByAcademicAndSemester(academicYear, semester);
        } catch (Exception e) {
            log.error("自动复制上一学期排课信息失败", e);
        }
    }
}
