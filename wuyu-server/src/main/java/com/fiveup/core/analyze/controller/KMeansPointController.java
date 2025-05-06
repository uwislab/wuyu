package com.fiveup.core.analyze.controller;


import com.fiveup.core.analyze.entity.*;
import com.fiveup.core.analyze.repository.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping(value = "/kmeanspoint")
public class KMeansPointController {

    @Autowired
    private KmeanspointRepository kmeanspointRepository;

    @Autowired
    private KmeanspointCluNumberRepository kmeanspointCluNumberRepository;

    @Autowired
    private UpdataSetRepository updataSetRepository;

    @RequestMapping("/KMeans_2")
    @ResponseBody
    public List<Map<String, Object>> KMeans2_Compute(Ana_wuyu_score_WebInput re){
        String X = re.getX_axles();
        String Y = re.getY_axles();
        String regrade = re.getRegrade();
        String X_python, Y_python;
        String Distance = re.getDistance();
        int P = re.getP();

//        System.out.println(Distance+P);

        Process proc;

        switch (X){
            case "德育":
                X_python = "de";
                break;
            case "智育":
                X_python = "zhi";
                break;
            case "体育":
                X_python = "ti";
                break;
            case "美育":
                X_python = "mei";
                break;
            case "劳育":
                X_python = "lao";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + X);
        }
        switch (Y){
            case "德育":
                Y_python = "de";
                break;
            case "智育":
                Y_python = "zhi";
                break;
            case "体育":
                Y_python = "ti";
                break;
            case "美育":
                Y_python = "mei";
                break;
            case "劳育":
                Y_python = "lao";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Y);
        }
//        System.out.println(X+X_python);
//        System.out.println(Y+Y_python);
//        System.out.println(regrade);
        try {
            String python_cmd = "python src\\main\\java\\com\\fiveup\\core\\analyze\\python\\算法代码\\K_Means算法成绩间关系_2维.py"
                    + " " + X_python + " " + Y_python + " " + regrade + " " + Distance + " " + P;
            // Python文件在cmd上执行，cmd初始路径在..\wuyu-server>
            proc = Runtime.getRuntime().exec(python_cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            // python报错显示
            BufferedReader isError = new BufferedReader(new InputStreamReader(proc.getErrorStream(),"GBK"));
            String error = null;
            while ((error = isError.readLine()) != null) {
                System.out.println(error);
            }
            String pre = null;
            while ((pre = in.readLine()) != null) {
//                System.out.println(pre);
            }

            in.close();
            isError.close();
            proc.waitFor();
            System.out.println("完成");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        List<Map<String, Object>> relist = new ArrayList<>();
        Map<String, Object> points_re = new HashMap<>();
        Map<String, Object> number_re = new HashMap<>();

        List<Ana_kmeanspoint_clunumber> list_num = kmeanspointCluNumberRepository.findAll();

        Gson gson = new Gson();
        String json = "";
        try {
            String file_name = "src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据集\\kmeans_2维.json";
            File file = new File(file_name);
            Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            int ch = 0;
            StringBuffer buffer = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                buffer.append((char) ch);
            }
            reader.close();
            json = buffer.toString();
            points_re = gson.fromJson(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (Ana_kmeanspoint_clunumber ana_kmeanspoint_clunumber : list_num){
            int cluster = ana_kmeanspoint_clunumber.getCluster();
            float number = ana_kmeanspoint_clunumber.getNumber();
            number_re.put(cluster + "", number);
        }

        relist.add(points_re);
        relist.add(number_re);
        return relist;
    }
}
