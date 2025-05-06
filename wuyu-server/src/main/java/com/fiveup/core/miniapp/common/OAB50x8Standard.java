package com.fiveup.core.miniapp.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author 史林
 * @date 2022/9/18
 */
//50m*8往返跑
public class OAB50x8Standard {

    static HashMap<String,List<Integer>> standard = new HashMap<>();

    public static HashMap<String,List<Integer>> getFive() {
        standard.put("不及格", Arrays.asList(-4, -4, 163, 158));
        standard.put("及格",Arrays.asList(-3, -3, 143, 138));
        standard.put("良好",Arrays.asList(-3, -3, 113, 108));
        standard.put("优秀",Arrays.asList(-3, -3, 107, 102));
        return standard;
    }

    public static HashMap<String,List<Integer>> getSix() {
        standard.put("不及格", Arrays.asList(-4, -4, 159, 152));
        standard.put("及格",Arrays.asList(-3, -3, 139, 132));
        standard.put("良好",Arrays.asList(-3, -3, 109, 102));
        standard.put("优秀",Arrays.asList(-3, -3, 103, 96));
        return standard;
    }
}
