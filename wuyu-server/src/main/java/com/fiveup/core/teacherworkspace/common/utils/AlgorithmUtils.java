package com.fiveup.core.teacherworkspace.common.utils;

import cn.hutool.extra.pinyin.PinyinUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgorithmUtils {
    @Deprecated
    /**
     *该方法已弃用，使用isSubsequenceContainChinese代替
     */
    public static boolean isSubsequence(String str1, String str2) {
        // 如果str1为空，它总是任何字符串的子序列
        if (str1.isEmpty()) return true;

        int index1 = 0;  // 指向str1的指针
        int index2 = 0;  // 指向str2的指针

        // 遍历str2直到找到所有str1的字符或遍历完str2
        while (index2 < str2.length()) {
            // 当字符匹配时，移动str1的指针
            if (str1.charAt(index1) == str2.charAt(index2)) {
                index1++;

                // 如果已匹配str1的所有字符，返回true
                if (index1 == str1.length()) {
                    return true;
                }
            }
            index2++;  // 无论是否匹配都移动str2指针
        }

        // 遍历完str2仍未找全str1的字符
        return false;
    }

    public static List<String> splitChineseAndNonChinese(String input) {
        List<String> parts = new ArrayList<>();
        // 正则表达式匹配中文或非中文
        String regex = "([\\u4e00-\\u9fff]+|[^\\u4e00-\\u9fff]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            parts.add(matcher.group());
        }

        return parts;
    }


    public static boolean isSubsequenceContainChinese(String s1, String s2) {
        if (s1 == null || s1.isEmpty()) return true;
        if (s2 == null || s2.isEmpty()) return false;

        // 构建字符序列（支持全拼）
        List<Character> seq1 = buildFullPinyinSequence(s1);
        List<Character> seq2 = buildFullPinyinSequence(s2);

        // 检查子序列
        int index = 0;
        for (char c : seq2) {
            if (index >= seq1.size()) break;
            if (c == seq1.get(index)) {
                index++;
            }
        }
        return index == seq1.size();
    }

    private static List<Character> buildFullPinyinSequence(String str) {
        List<Character> sequence = new ArrayList<>();
        List<String> segments = AlgorithmUtils.splitChineseAndNonChinese(str);

        for (String seg : segments) {
            if (AlgorithmUtils.containsChinese(seg)) {
                // 中文段：转换为全拼的字符序列
                for (char c : seg.toCharArray()) {
                    String pinyin = PinyinUtil.getPinyin(String.valueOf(c), "");
                    for (char p : pinyin.toCharArray()) {
                        sequence.add(Character.toLowerCase(p));
                    }
                }
            } else {
                // 非中文段：直接转换为小写字符序列
                for (char c : seg.toCharArray()) {
                    sequence.add(Character.toLowerCase(c));
                }
            }
        }
        return sequence;
    }

    public static boolean containsChinese(String str) {
        // 正则表达式：匹配基本汉字范围
        return str.matches(".*[\\u4e00-\\u9fff]+.*");
    }

    /**
     * 根据日期计算学年，例如：
     * - 9月 ~ 12月 => 2024~2025
     * - 1月 ~ 2月 => 2024~2025（仍属上一学年）
     * - 3月 ~ 8月 => 2023~2024
     */
    public static String calculateAcademicYear(LocalDate date) {
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
    public static int calculateSemester(LocalDate date) {
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
