package com.fiveup.core.miniapp.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
//肺活量
public class FVCStandard {

    static HashMap<String,List<Integer>> standard = new HashMap<>();

    public static HashMap<String,List<Integer>> getOne() {
        standard.put("不及格", Arrays.asList(20, 40, 500, 500));
        standard.put("及格",Arrays.asList(40, 60, 600, 700));
        standard.put("良好",Arrays.asList(100, 100, 1000, 1300));
        standard.put("优秀",Arrays.asList(100, 100, 1200, 1500));
        return standard;
    }

    public static HashMap<String,List<Integer>> getTwo() {
        standard.put("不及格", Arrays.asList(20, 50, 600, 550));
        standard.put("及格",Arrays.asList(50, 70, 700, 800));
        standard.put("良好",Arrays.asList(100, 150, 1200, 1500));
        standard.put("优秀",Arrays.asList(100, 100, 1400, 1800));
        return standard;
    }

    public static HashMap<String,List<Integer>> getThree() {
        standard.put("不及格", Arrays.asList(20, 60, 700, 600));
        standard.put("及格",Arrays.asList(60, 80, 800, 900));
        standard.put("良好",Arrays.asList(100, 200, 1400, 1700));
        standard.put("优秀",Arrays.asList(100, 100, 1600, 2100));
        return standard;
    }

    public static HashMap<String,List<Integer>> getFour() {
        standard.put("不及格", Arrays.asList(20, 70, 800, 750));
        standard.put("及格",Arrays.asList(70, 80, 900, 1100));
        standard.put("良好",Arrays.asList(100, 250, 1600, 1900));
        standard.put("优秀",Arrays.asList(100, 100, 1800, 2400));
        return standard;
    }

    public static HashMap<String,List<Integer>> getFive() {
        standard.put("不及格", Arrays.asList(30, 80, 900, 900));
        standard.put("及格",Arrays.asList(80, 90, 1050, 1300));
        standard.put("良好",Arrays.asList(100, 150, 1850, 2200));
        standard.put("优秀",Arrays.asList(100, 100, 2050, 2700));
        return standard;
    }

    public static HashMap<String,List<Integer>> getSix() {
        standard.put("不及格", Arrays.asList(30, 90, 1050, 1050));
        standard.put("及格",Arrays.asList(90, 100, 1200, 1500));
        standard.put("良好",Arrays.asList(100, 250, 2100, 2500));
        standard.put("优秀",Arrays.asList(100, 100, 1300, 3000));
        return standard;
    }
}
