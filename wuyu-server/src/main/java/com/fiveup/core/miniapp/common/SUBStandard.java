package com.fiveup.core.miniapp.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
//仰卧起坐
public class SUBStandard {

    static HashMap<String,List<Integer>> standard = new HashMap<>();

    public static HashMap<String,List<Integer>> getThree() {
        standard.put("不及格", Arrays.asList(2, 2, 6, 6));
        standard.put("及格",Arrays.asList(2, 2, 16, 16));
        standard.put("良好",Arrays.asList(3, 3, 36, 36));
        standard.put("优秀",Arrays.asList(2, 3, 42, 42));
        return standard;
    }

    public static HashMap<String,List<Integer>> getFour() {
        standard.put("不及格", Arrays.asList(2, 2, 7, 7));
        standard.put("及格",Arrays.asList(2, 2, 17, 17));
        standard.put("良好",Arrays.asList(3, 3, 37, 37));
        standard.put("优秀",Arrays.asList(2, 3, 43, 43));
        return standard;
    }

    public static HashMap<String,List<Integer>> getFive() {
        standard.put("不及格", Arrays.asList(2, 2, 8, 8));
        standard.put("及格",Arrays.asList(2, 2, 18, 18));
        standard.put("良好",Arrays.asList(3, 3, 38, 38));
        standard.put("优秀",Arrays.asList(2, 3, 44, 44));
        return standard;
    }

    public static HashMap<String,List<Integer>> getSix() {
        standard.put("不及格", Arrays.asList(2, 2, 9, 9));
        standard.put("及格",Arrays.asList(2, 2, 19, 19));
        standard.put("良好",Arrays.asList(3, 3, 39, 39));
        standard.put("优秀",Arrays.asList(2, 3, 45, 45));
        return standard;
    }
}
