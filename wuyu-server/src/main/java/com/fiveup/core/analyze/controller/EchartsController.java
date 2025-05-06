package com.fiveup.core.analyze.controller;


import com.fiveup.core.analyze.entity.*;
import com.fiveup.core.analyze.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping(value = "/echarts")
public class EchartsController {

    @Autowired
    private CenterDataRepository centerDataRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClusterNumberRepository clusterNumberRepository;

    @Autowired
    private WuyuFlunkRepository wuyuFlunkRepository;

    // 通过五育成绩划分学生表现
    @RequestMapping("/scores")
    @ResponseBody
    public List<Map<String, Object>> Scores(Ana_wuyu_score_WebInput gr){
        List<Ana_cluster_number> list = clusterNumberRepository.findAll();
        List<Map<String, Object>> relist = new ArrayList<>();
        String regrade = gr.getRegrade();
        int[] a = {0, 0, 0, 0};
        String[] n = {"优秀", "良好", "一般", "不及格"};

        int t = 0;
//        System.out.println("re" + regrade);
        for (Ana_cluster_number ana_cluster_number : list){
            String grade = ana_cluster_number.getGrade();
//            System.out.println(grade);
            if (regrade.equals(grade)){
                int number = ana_cluster_number.getNumber();
                a[t] = number;
                t++;
            }

        }
        for (int i = 0; i < 4; i++){
            Map<String, Object> map_score = new HashMap<>();
            map_score.put("value", a[i]);
            map_score.put("name", n[i]);
            relist.add(map_score);
        }
        return relist;
    }

    @GetMapping("/Fai_rate")
    public List<Map<String, Object>> Fai_rate(){
        List<Ana_wuyu_flunk> list = wuyuFlunkRepository.findAll();
        List<Map<String, Object>> relist = new ArrayList<>();
        String[] n = {"一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "总体"};

        int i = 0;
        for (Ana_wuyu_flunk Ana_wuyu_flunk : list){
            float De = Ana_wuyu_flunk.getDe_rate();
            float Zhi = Ana_wuyu_flunk.getZhi_rate();
            float Ti = Ana_wuyu_flunk.getTi_rate();
            float Mei = Ana_wuyu_flunk.getMei_rate();
            float Lao = Ana_wuyu_flunk.getLao_rate();
            float[] rate_list = {De, Zhi, Ti, Mei, Lao};

            Map<String, Object> map_score = new HashMap<>();
            map_score.put("value", rate_list);
            map_score.put("name", n[i]);
            relist.add(map_score);
            i++;
        }
        return relist;
    }

    @GetMapping("/Top4")
    public Map<String, Object> Top4(){
        List<Ana_cluster_number> list_clu = clusterNumberRepository.findAll();
        List<Ana_wuyu_flunk> list_flu = wuyuFlunkRepository.findAll();
        Map<String, Object> remap = new HashMap<>();
        String[] Wuyu = {"德育", "智育", "体育", "美育", "劳育"};

        int stu_num = 0;  // 学生总数
        int fai_num = 0;  // 不及格人数
        String Best_sub = null, Worst_sub = null;
        for (Ana_cluster_number ana_cluster_number : list_clu){
            String grade = ana_cluster_number.getGrade();
            int cluster = ana_cluster_number.getCluster();
            int number = ana_cluster_number.getNumber();
            if (grade.equals("Sum")){
                stu_num += number;
                if (cluster == 4){
                    fai_num = number;
                }
            }
        }
        for (Ana_wuyu_flunk ana_wuyu_flunk : list_flu){
            String grade = ana_wuyu_flunk.getGrade();
            if (grade.equals("Sum")){
                float De = ana_wuyu_flunk.getDe_rate();
                float Zhi = ana_wuyu_flunk.getZhi_rate();
                float Ti = ana_wuyu_flunk.getTi_rate();
                float Mei = ana_wuyu_flunk.getMei_rate();
                float Lao = ana_wuyu_flunk.getLao_rate();
                List<Float> rate_list = new ArrayList<>();
                rate_list.add(De);
                rate_list.add(Zhi);
                rate_list.add(Ti);
                rate_list.add(Mei);
                rate_list.add(Lao);
                float max = Collections.max(rate_list);
                float min = Collections.min(rate_list);
                Best_sub = Wuyu[rate_list.indexOf(min)];
                Worst_sub = Wuyu[rate_list.indexOf(max)];
            }
        }
        remap.put("stu_num", stu_num - 4);
        remap.put("fai_num", fai_num);
        remap.put("Best_sub", Best_sub);
        remap.put("Worst_sub", Worst_sub);
        return remap;
    }

    @GetMapping("/Performscore")
    public List<Map<String, Object>> PerformanceScore(Ana_wuyu_score_WebInput gr){
        List<Ana_center_data> list_center = centerDataRepository.findAll();
        List<Map<String, Object>> relist = new ArrayList<>();

        String regrade = gr.getRegrade();
        String[] n = {"优秀", "良好", "一般", "不及格"};

        // 0\1\2\3为 学生表现
        float[] a = {0, 0, 0, 0};
        for (Ana_center_data ana_center_data : list_center){
            if (regrade.equals(ana_center_data.getGrade())){
                float De = ana_center_data.getDe();
                float Zhi = ana_center_data.getZhi();
                float Ti = ana_center_data.getTi();
                float Mei = ana_center_data.getMei();
                float Lao = ana_center_data.getLao();
                switch (ana_center_data.getCluster()){
                    case 1:
                        a[0] = (De + Zhi + Ti + Mei + Lao) / 5;
                        break;
                    case 2:
                        a[1] = (De + Zhi + Ti + Mei + Lao) / 5;
                        break;
                    case 3:
                        a[2] = (De + Zhi + Ti + Mei + Lao) / 5;
                        break;
                    case 4:
                        a[3] = (De + Zhi + Ti + Mei + Lao) / 5;
                        break;
                    default:break;
                }
            }

        }
        for (int i = 0; i < 4; i++){
            Map<String, Object> map_score = new HashMap<>();
            map_score.put("value", a[i]);
            map_score.put("name", n[i]);
            relist.add(map_score);
        }
        return relist;
    }

    @RequestMapping("/relationchange")
    @ResponseBody
    public List<String> relation_change(Ana_wuyu_score_WebInput re){
        List<String> relist = new ArrayList<>();
        String rePer = re.getRePer();
        String regrade = re.getRegrade();
        Process proc;
//        System.out.println(regrade + rePer);

        try {
            // Python文件在cmd上执行，cmd初始路径在..\wuyu-server>
            String python_cmd = "python src\\main\\java\\com\\fiveup\\core\\analyze\\python\\算法代码\\五育关联.py" + " " + regrade + " " + rePer;
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

        List<Ana_subject_relation> list = subjectRepository.findAll();
        for (Ana_subject_relation ana_subject_relation : list){
            String subject = ana_subject_relation.getSubject();
            switch (subject) {
                case "de":
                    relist.add("德");
                    break;
                case "zhi":
                    relist.add("智");
                    break;
                case "ti":
                    relist.add("体");
                    break;
                case "mei":
                    relist.add("美");
                    break;
                case "lao":
                    relist.add("劳");
                    break;
                default: break;
            }
        }
        return relist;
    }
}
