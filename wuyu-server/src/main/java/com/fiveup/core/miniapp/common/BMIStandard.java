package com.fiveup.core.miniapp.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
//50m短跑
public class BMIStandard {

    static HashMap<String,List<Double>> standard = new HashMap<>();

    public static HashMap<String,List<Double>> getOne() {
        standard.put("肥胖", Arrays.asList(19.3, 20.4));
        standard.put("超重",Arrays.asList(17.4, 18.2, 19.2, 20.3));
        standard.put("低体重",Arrays.asList(13.2, 13.4));
        standard.put("正常",Arrays.asList(13.3, 13.5, 17.3, 18.1));
        return standard;
    }

    public static HashMap<String,List<Double>> getTwo() {
        standard.put("肥胖", Arrays.asList(20.3, 20.5));
        standard.put("超重",Arrays.asList(17.9, 18.5, 20.2, 20.4));
        standard.put("低体重",Arrays.asList(13.4, 13.6));
        standard.put("正常",Arrays.asList(13.5, 13.7, 17.8, 18.4));
        return standard;
    }

    public static HashMap<String,List<Double>> getThree() {
        standard.put("肥胖", Arrays.asList(21.2, 22.2));
        standard.put("超重",Arrays.asList(18.7, 19.5, 21.1, 22.1));
        standard.put("低体重",Arrays.asList(13.5, 13.8));
        standard.put("正常",Arrays.asList(13.3, 13.5, 17.3, 18.1));
        return standard;
    }

    public static HashMap<String,List<Double>> getFour() {
        standard.put("肥胖", Arrays.asList(22.1, 22.7));
        standard.put("超重",Arrays.asList(19.5, 20.2, 22.0, 22.6));
        standard.put("低体重",Arrays.asList(13.6, 14.1));
        standard.put("正常",Arrays.asList(13.7, 14.2, 19.4, 20.1));
        return standard;
    }

    public static HashMap<String,List<Double>> getFive() {
        standard.put("肥胖", Arrays.asList(23.0, 24.2));
        standard.put("超重",Arrays.asList(20.6, 21.5, 22.9, 24.1));
        standard.put("低体重",Arrays.asList(13.7, 14.3));
        standard.put("正常",Arrays.asList(13.8, 14.4, 20.5, 21.4));
        return standard;
    }

    public static HashMap<String,List<Double>> getSix() {
        standard.put("肥胖", Arrays.asList(23.7, 24.6));
        standard.put("超重",Arrays.asList(20.9, 21.9, 23.6, 24.5));
        standard.put("低体重",Arrays.asList(14.1, 14.6));
        standard.put("正常",Arrays.asList(14.2, 14.7, 20.8, 21.8));
        return standard;
    }
}
