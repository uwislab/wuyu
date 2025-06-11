package com.fiveup.core.teacherworkspace.scheduled;

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
        this.academicYear = calculateAcademicYear(date);
        this.semester = calculateSemester(date);
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


    /**
     * 根据日期计算学年，例如：
     * - 9月 ~ 12月 => 2024~2025
     * - 1月 ~ 2月 => 2024~2025（仍属上一学年）
     * - 3月 ~ 8月 => 2023~2024
     */
    private String calculateAcademicYear(LocalDate date) {
        int year = date.getYear();
        int monthValue = date.getMonthValue();

        if (monthValue >= 9 || monthValue <= 2) {
            // 9月到次年2月属于下一年的上半年
            return year + "-" + (year + 1);
        } else {
            // 3月到8月属于本学年
            return (year - 1) + "-" + year;
        }
    }

    /**
     * 根据日期计算当前学期：
     * - 9月 ~ 12月 => 第一学期（1）
     * - 3月 ~ 6月 => 第二学期（2）
     * - 其他月份（如 1-2, 7-8）返回 0 表示非教学学期
     */
    private int calculateSemester(LocalDate date) {
        int monthValue = date.getMonthValue();
        if (monthValue >= 9 || monthValue <= 2) {
            return 1; // 秋季学期
        } else if (monthValue <= 6) {
            return 2; // 春季学期
        } else {
            return 0; // 非教学学期（暑假/寒假）
        }
    }
}
