package com.fiveup.core.miniapp.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
//50m短跑
public class Dash50Standard {

    static HashMap<String,List<Double>> standard = new HashMap<>();

    public static HashMap<String,List<Double>> getOne() {
        standard.put("不及格", Arrays.asList(-0.2, -0.2, 14.8, 13.6));
        standard.put("及格",Arrays.asList(-0.2, -0.2, 13.8, 12.6));
        standard.put("良好",Arrays.asList(-0.3, -0.1, 11.8, 10.6));
        standard.put("优秀",Arrays.asList(-0.1, -0.1, 11.2, 10.4));
        return standard;
    }

    public static HashMap<String,List<Double>> getTwo() {
        standard.put("不及格", Arrays.asList(-0.2, -0.2, 13.8, 13.0));
        standard.put("及格",Arrays.asList(-0.2, -0.2, 12.8, 12.0));
        standard.put("良好",Arrays.asList(-0.3, -0.1, 10.8, 10.0));
        standard.put("优秀",Arrays.asList(-0.1, -0.1, 10.2, 9.8));
        return standard;
    }

    public static HashMap<String,List<Double>> getThree() {
        standard.put("不及格", Arrays.asList(-0.2, -0.2, 13.0, 12.5));
        standard.put("及格",Arrays.asList(-0.2, -0.2, 12.0, 11.5));
        standard.put("良好",Arrays.asList(-0.3, -0.1, 10.0, 9.5));
        standard.put("优秀",Arrays.asList(-0.1, -0.1, 9.4, 9.3));
        return standard;
    }

    public static HashMap<String,List<Double>> getFour() {
        standard.put("不及格", Arrays.asList(-0.2, -0.2, 12.5, 12.1));
        standard.put("及格",Arrays.asList(-0.2, -0.2, 11.5, 11.1));
        standard.put("良好",Arrays.asList(-0.3, -0.1, 9.5, 9.1));
        standard.put("优秀",Arrays.asList(-0.1, -0.1, 8.9, 8.9));
        return standard;
    }

    public static HashMap<String,List<Double>> getFive() {
        standard.put("不及格", Arrays.asList(-0.2, -0.2, 12.1, 11.8));
        standard.put("及格",Arrays.asList(-0.2, -0.2, 11.1, 10.8));
        standard.put("良好",Arrays.asList(-0.3, -0.1, 9.1, 8.8));
        standard.put("优秀",Arrays.asList(-0.1, -0.1, 8.5, 8.6));
        return standard;
    }

    public static HashMap<String,List<Double>> getSix() {
        standard.put("不及格", Arrays.asList(-0.2, -0.2, 12.0, 11.6));
        standard.put("及格",Arrays.asList(-0.2, -0.2, 11.0, 10.6));
        standard.put("良好",Arrays.asList(-0.3, -0.1, 9.0, 8.6));
        standard.put("优秀",Arrays.asList(-0.1, -0.1, 8.4, 8.4));
        return standard;
    }
}
