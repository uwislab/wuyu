package com.fiveup.core.teacherworkspace.common.utils;

/**
 * 正则格式验证器
 */
public class RegexVerifyUtils {
    public static boolean validAcademicYear(String academicYear) {
        String regex = "\\d{4}-\\d{4}";
        return academicYear.matches(regex);
    }
}
