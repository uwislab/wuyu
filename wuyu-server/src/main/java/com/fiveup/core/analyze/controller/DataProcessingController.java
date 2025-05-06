package com.fiveup.core.analyze.controller;


import com.fiveup.core.analyze.entity.*;
import com.fiveup.core.analyze.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping(value = "/dataprocessing")
public class DataProcessingController {

    @Autowired
    private UpdatatimeRepository updatatimeRepository;

    @Autowired
    private UpdataSetRepository updataSetRepository;

    @RequestMapping("/deal")
    @ResponseBody
    public Map<String, Object> DataProcessing(){
        List<Ana_updata_set> setlist = updataSetRepository.findAll();
        Map<String, Object> remap = new HashMap<>();
        String Distance = null;
        int P = 0;
        for (Ana_updata_set ana_updata_set : setlist) {
            Distance = ana_updata_set.getDistance();
            P = ana_updata_set.getP();
        }

        Process proc_pretreatment;  // 数据预处理代码执行
        Process proc_Algorithm;  // 算法代码执行
//        System.out.println("开始！");
        try {
            // Python文件在cmd上执行，cmd初始路径在..\wuyu-server>
            String python_cmd1 = "python src\\main\\java\\com\\fiveup\\core\\analyze\\python\\数据预处理\\数据处理整合.py";
            proc_pretreatment = Runtime.getRuntime().exec(python_cmd1);
            BufferedReader in_pretreatment = new BufferedReader(new InputStreamReader(proc_pretreatment.getInputStream(),"GBK"));
            // python报错显示
            BufferedReader isError_pretreatment = new BufferedReader(new InputStreamReader(proc_pretreatment.getErrorStream(),"GBK"));
            String pre_error = null;
            while ((pre_error = isError_pretreatment.readLine()) != null) {
                System.out.println(pre_error);
            }
            String pre = null;
            while ((pre = in_pretreatment.readLine()) != null) {
//                System.out.println(pre);
            }
            in_pretreatment.close();
            isError_pretreatment.close();
            proc_pretreatment.waitFor();

            String python_cmd2 = "python src\\main\\java\\com\\fiveup\\core\\analyze\\python\\算法代码\\算法数据处理整合.py" + " " + Distance + " " + P;
            proc_Algorithm = Runtime.getRuntime().exec(python_cmd2);
            BufferedReader in_Algorithm = new BufferedReader(new InputStreamReader(proc_Algorithm.getInputStream(),"GBK"));
            // python报错显示
            BufferedReader isError_Algorithm = new BufferedReader(new InputStreamReader(proc_Algorithm.getErrorStream(),"GBK"));
            String A_error = null;
            while ((A_error = isError_Algorithm.readLine()) != null) {
                System.out.println(A_error);
            }
            String Alg = null;
            while ((Alg = in_Algorithm.readLine()) != null) {
//                System.out.println(Alg);
            }
            in_Algorithm.close();
            isError_Algorithm.close();
            proc_Algorithm.waitFor();

            Ana_updata_time re_time = new Ana_updata_time();
            Date time = new Date(System.currentTimeMillis());
            SimpleDateFormat time_vue = new SimpleDateFormat("yyyy-MM-dd | hh:mm:ss");
            String str_time = time_vue.format(time);
            re_time.setId(1);
            re_time.setUpdatatime(time);
            Ana_updata_time re_time1 = updatatimeRepository.save(re_time);

            remap.put("result", "完成");
            remap.put("updatatime", str_time);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            remap.put("result", "错误");

        }
//        System.out.println("完成？？？？");
        return remap;
    }

    @RequestMapping("/uptime")
    @ResponseBody
    public Map<String, Object> Updatatime(){
        List<Ana_updata_time> list = updatatimeRepository.findAll();
        Map<String, Object> remap = new HashMap<>();
        Date time = new Date();
        for (Ana_updata_time ana_updata_time : list){
            time = ana_updata_time.getUpdatatime();
        }
        SimpleDateFormat time_vue = new SimpleDateFormat("yyyy-MM-dd | hh:mm:ss");
        String str_time = time_vue.format(time);

        remap.put("result", "点击更新");
        remap.put("updatatime", str_time);
        return remap;
    }

    @RequestMapping("/UpSet")
    @ResponseBody
    public String UpdataSet(Ana_wuyu_score_WebInput re){
        Ana_updata_set re_set = new Ana_updata_set();
        String Kfill = re.getKfill();
        String Knormal = re.getKnormal();
        String Distance = re.getDistance();
        int P = re.getP();
        String Afill = re.getAfill();
        String Anormal = re.getAnormal();
        String Bfill = re.getBfill();
        String Bnormal = re.getBnormal();

        re_set.setId(1);
        re_set.setFill_kmeans(Kfill);
        re_set.setNormal_kmeans(Knormal);
        re_set.setDistance(Distance);
        re_set.setP(P);
        re_set.setFill_apriori(Afill);
        re_set.setNormal_apriori(Anormal);
        re_set.setFill_bayes(Bfill);
        re_set.setNormal_bayes(Bnormal);

        updataSetRepository.save(re_set);

        return "完成";
    }

    @RequestMapping("/setview")
    @ResponseBody
    public Map<String, Object> Setview(){

        List<Ana_updata_set> re_set = updataSetRepository.findAll();
        Map<String, Object> remap = new HashMap<>();

        for (Ana_updata_set ana_updata_set : re_set){
            String Fill_kmeans = ana_updata_set.getFill_kmeans();
            String Normal_kmeans = ana_updata_set.getNormal_kmeans();
            String Distance = ana_updata_set.getDistance();
            int P = ana_updata_set.getP();
            String Fill_apriori = ana_updata_set.getFill_apriori();
            String Normal_apriori = ana_updata_set.getNormal_apriori();
            String Fill_bayes = ana_updata_set.getFill_bayes();
            String Normal_bayes = ana_updata_set.getNormal_bayes();

            remap.put("Kfill", Fill_kmeans);
            remap.put("Knormal", Normal_kmeans);
            remap.put("Distance", Distance);
            remap.put("P", P);
            remap.put("Afill", Fill_apriori);
            remap.put("Anormal", Normal_apriori);
            remap.put("Bfill", Fill_bayes);
            remap.put("Bnormal", Normal_bayes);
        }
        return remap;
    }
}
