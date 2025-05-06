package com.fiveup.core.miniapp.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
//坐位体前屈
public class SARStandard {

    static HashMap<String,List<Double>> standard = new HashMap<>();

    public static HashMap<String,List<Double>> getOne() {
        standard.put("不及格", Arrays.asList(0.8, 0.8, -1.6, -4.0));
        standard.put("及格",Arrays.asList(1.1, 1.1, 2.4, 0.0));
        standard.put("良好",Arrays.asList(1.3, 1.0, 13.4, 11.0));
        standard.put("优秀",Arrays.asList(1.3, 1.6, 16.0, 13.0));
        return standard;
    }

    public static HashMap<String,List<Double>> getTwo() {
        standard.put("不及格", Arrays.asList(0.8, 0.8, -1.7, -4.4));
        standard.put("及格",Arrays.asList(1.1, 1.1, 2.3, -0.4));
        standard.put("良好",Arrays.asList(1.5, 1.3, 13.3, 10.6));
        standard.put("优秀",Arrays.asList(1.3, 1.5, 16.3, 13.2));
        return standard;
    }

    public static HashMap<String,List<Double>> getThree() {
        standard.put("不及格", Arrays.asList(0.8, 0.8, -1.8, -4.8));
        standard.put("及格",Arrays.asList(1.1, 1.1, 2.2, -0.8));
        standard.put("良好",Arrays.asList(1.7, 1.6, 13.2, 10.2));
        standard.put("优秀",Arrays.asList(1.3, 1.5, 16.6, 13.4));
        return standard;
    }

    public static HashMap<String,List<Double>> getFour() {
        standard.put("不及格", Arrays.asList(0.8, 1.0, -1.9, -7.2));
        standard.put("及格",Arrays.asList(1.1, 1.2, 2.1, -2.2));
        standard.put("良好",Arrays.asList(1.9, 1.9, 13.1, 9.8));
        standard.put("优秀",Arrays.asList(1.2, 1.4, 16.9, 13.6));
        return standard;
    }

    public static HashMap<String,List<Double>> getFive() {
        standard.put("不及格", Arrays.asList(0.8, 1.0, -2.0, -7.6));
        standard.put("及格",Arrays.asList(1.1, 1.2, 2.0, -2.6));
        standard.put("良好",Arrays.asList(2.1, 2.2, 13.0, 9.4));
        standard.put("优秀",Arrays.asList(1.3, 1.4, 17.2, 13.8));
        return standard;
    }

    public static HashMap<String,List<Double>> getSix() {
        standard.put("不及格", Arrays.asList(0.8, 1.0, -2.1, -9.0));
        standard.put("及格",Arrays.asList(1.1, 1.3, 1.9, -4.0));
        standard.put("良好",Arrays.asList(2.3, 2.5, 12.9, 9.0));
        standard.put("优秀",Arrays.asList(1.2, 1.3, 17.5, 14.0));
        return standard;
    }
}
