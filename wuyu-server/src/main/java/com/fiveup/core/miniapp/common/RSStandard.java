package com.fiveup.core.miniapp.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
//一分钟跳绳
public class RSStandard {

    static HashMap<String,List<Integer>> standard = new HashMap<>();

    public static HashMap<String,List<Integer>> getOne() {
        standard.put("不及格", Arrays.asList(3, 3, 2, 2));
        standard.put("及格",Arrays.asList(7, 7, 17, 17));
        standard.put("良好",Arrays.asList(8, 6, 87, 87));
        standard.put("优秀",Arrays.asList(7, 5, 103, 99));
        return standard;
    }

    public static HashMap<String,List<Integer>> getTwo() {
        standard.put("不及格", Arrays.asList(3, 3, 12, 10));
        standard.put("及格",Arrays.asList(7, 7, 27, 25));
        standard.put("良好",Arrays.asList(8, 6, 97, 97));
        standard.put("优秀",Arrays.asList(7, 5, 113, 107));
        return standard;
    }

    public static HashMap<String,List<Integer>> getThree() {
        standard.put("不及格", Arrays.asList(3, 3, 24, 19));
        standard.put("及格",Arrays.asList(7, 7, 39, 34));
        standard.put("良好",Arrays.asList(8, 6, 109, 104));
        standard.put("优秀",Arrays.asList(7, 5, 125, 116));
        return standard;
    }

    public static HashMap<String,List<Integer>> getFour() {
        standard.put("不及格", Arrays.asList(3, 3, 34, 30));
        standard.put("及格",Arrays.asList(7, 7, 49, 45));
        standard.put("良好",Arrays.asList(8, 6, 119, 115));
        standard.put("优秀",Arrays.asList(7, 5, 135, 127));
        return standard;
    }

    public static HashMap<String,List<Integer>> getFive() {
        standard.put("不及格", Arrays.asList(3, 3, 43, 41));
        standard.put("及格",Arrays.asList(7, 7, 58, 56));
        standard.put("良好",Arrays.asList(8, 6, 128, 126));
        standard.put("优秀",Arrays.asList(7, 5, 144, 138));
        return standard;
    }

    public static HashMap<String,List<Integer>> getSix() {
        standard.put("不及格", Arrays.asList(3, 3, 51, 50));
        standard.put("及格",Arrays.asList(7, 7, 66, 65));
        standard.put("良好",Arrays.asList(8, 6, 136, 135));
        standard.put("优秀",Arrays.asList(7, 5, 152, 147));
        return standard;
    }
}
