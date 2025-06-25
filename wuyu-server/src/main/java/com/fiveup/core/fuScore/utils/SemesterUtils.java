package com.fiveup.core.fuScore.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SemesterUtils {
    /**
     * 将中文格式的学期名（如"三年级上学期"）转换为学期编码（如"202312"）
     *
     * 注意：
     * - "上学期" 的数据采集时间为当年12月
     * - "下学期" 的数据采集时间为次年7月
     *
     * @param studentId 学号（用于提取入学年份）
     * @param semesterName 中文学期名（如："三年级上学期"）
     * @return 学期编码（如："202312" 表示 2023 年上学期）
     */
    public static String convertToSemesterCode(Integer studentId, String semesterName) {
        if (studentId == null || semesterName == null) {
            return null;
        }

        String studentIdStr = String.valueOf(studentId);
        if (studentIdStr.length() < 6) {
            throw new IllegalArgumentException("学号长度不足，无法提取入学年份");
        }

        String enrollYearMonth = studentIdStr.substring(0, 6); // 如 202108
        int entranceYear = Integer.parseInt(enrollYearMonth.substring(0, 4)); // 入学年份 2021

        Pattern pattern = Pattern.compile("(\\d+)年级([上下])学期");
        Matcher matcher = pattern.matcher(semesterName);

        if (!matcher.find()) {
            throw new IllegalArgumentException("学期格式不正确: " + semesterName);
        }

        int gradeLevel = Integer.parseInt(matcher.group(1)); // 年级，如 3
        String semesterPart = matcher.group(2); // 上 or 下

        int year;
        if ("上".equals(semesterPart)) {
            year = entranceYear + (gradeLevel - 1);
        } else {
            year = entranceYear + gradeLevel;
        }

        return String.format("%d%s", year, semesterPart.equals("上") ? "12" : "07");
    }

    /**
     * 从学期名中提取年级级别
     *
     * @param semesterName 中文学期名（如："三年级上学期"）
     * @return 年级级别（如：3）
     */
    public static int extractGradeLevel(String semesterName) {
        Pattern pattern = Pattern.compile("(\\d+)年级([上下])学期");
        Matcher matcher = pattern.matcher(semesterName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1)); // 年级数字，如 1、2、3...
        }
        throw new IllegalArgumentException("学期格式不正确: " + semesterName);
    }
}