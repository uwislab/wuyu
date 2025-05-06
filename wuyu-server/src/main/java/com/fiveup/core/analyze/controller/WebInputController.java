package com.fiveup.core.analyze.controller;

import com.fiveup.core.analyze.entity.*;
import com.fiveup.core.analyze.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping(value = "/WebInputPrediction")
public class WebInputController {
    @Autowired
    private CenterDataRepository centerDataRepository;

    @Autowired
    private UpdataSetRepository updataSetRepository;

    @RequestMapping("/NaiveBayes")
    @ResponseBody
    public String NaiveBayes_Compute(Ana_wuyu_score_WebInput re){

        float De = re.getDe();
        float Zhi = re.getZhi();
        float Ti = re.getTi();
        float Mei = re.getMei();
        float Lao = re.getLao();
        String regrade = re.getRegrade();

        Process proc;
        String test_data;
        test_data = De + ", " +
                Zhi + ", " +
                Ti + ", " +
                Mei + ", " +
                Lao;
//        System.out.println(test_data);
//        System.out.println(regrade);
        try {
            String python_cmd = "python src\\main\\java\\com\\fiveup\\core\\analyze\\python\\算法代码\\朴素贝叶斯算法.py"
                    + " "+ "\"" + test_data + "\"" + " " + regrade;
            // Python文件在cmd上执行，cmd初始路径在..\wuyu-server>
            proc = Runtime.getRuntime().exec(python_cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line = null;
            line = in.readLine();
//            if (line != null) {
//                System.out.println(line);
//            }
            in.close();
            proc.waitFor();
            System.out.println(line);
            return line;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "错误";
        }
    }

    @RequestMapping("/KMeans")
    @ResponseBody
    public String KMeans_Compute(Ana_wuyu_score_WebInput re) {
        List<Ana_center_data> list = centerDataRepository.findAll();
        List<Ana_updata_set> list_set = updataSetRepository.findAll();
        String Distance = null;
        Integer P = 1;
        String result = null;
        for (Ana_updata_set ana_updata_set : list_set) {
            Distance = ana_updata_set.getDistance();
            P = ana_updata_set.getP();
        }
        List<Float> Cos_Endlist = new ArrayList<>();
        List<Float> M_Endlist = new ArrayList<>();
        String[] Per = {"优秀", "良好", "一般", "不及格"};
        float De_vue = re.getDe();
        float Zhi_vue = re.getZhi();
        float Ti_vue = re.getTi();
        float Mei_vue = re.getMei();
        float Lao_vue = re.getLao();
        String regrade = re.getRegrade();
        float[] test = {De_vue, Zhi_vue, Ti_vue, Mei_vue, Lao_vue};
//        System.out.println(Arrays.toString(test));

        switch (Distance) {
            case "夹角余弦相似度":
                for (Ana_center_data ana_center_data : list){
                    String grade = ana_center_data.getGrade();
                    if (regrade.equals(grade)){
                        float[] center_list = new float[5];
                        float De = ana_center_data.getDe();
                        center_list[0] = De;
                        float Zhi = ana_center_data.getZhi();
                        center_list[1] = Zhi;
                        float Ti = ana_center_data.getTi();
                        center_list[2] = Ti;
                        float Mei = ana_center_data.getMei();
                        center_list[3] = Mei;
                        float Lao = ana_center_data.getLao();
                        center_list[4] = Lao;

                        //            System.out.println(Arrays.toString(center_list));

                        float Cos_end;
                        float Cos_M = 0; // 分子
                        float Cos_D = 0;  // 分母
                        float Cos_DOR_1 = 0;  //向量的开根
                        float Cos_DOR_2 = 0;  //向量的开根
                        for (int i = 0; i < test.length; i++){
                            Cos_M += center_list[i] * test[i];
                        }
                        for (int i = 0; i < test.length; i++){
                            Cos_DOR_1 += center_list[i] * center_list[i];
                            Cos_DOR_2 += test[i] * test[i];
                        }

                        Cos_D = (float) (Math.sqrt(Cos_DOR_1) * Math.sqrt(Cos_DOR_2));

                        Cos_end = Cos_M / Cos_D;
                        //            System.out.println(Cos_end);
                        Cos_Endlist.add(Cos_end);
                    }
                }
//        System.out.println(Cos_Endlist);
                result = Per[Cos_Endlist.indexOf(Collections.max(Cos_Endlist))];
                break;
            case "闵可夫斯基距离":
                for (Ana_center_data ana_center_data : list){
                    String grade = ana_center_data.getGrade();
                    if (regrade.equals(grade)){
                        float[] center_list = new float[5];
                        float De = ana_center_data.getDe();
                        center_list[0] = De;
                        float Zhi = ana_center_data.getZhi();
                        center_list[1] = Zhi;
                        float Ti = ana_center_data.getTi();
                        center_list[2] = Ti;
                        float Mei = ana_center_data.getMei();
                        center_list[3] = Mei;
                        float Lao = ana_center_data.getLao();
                        center_list[4] = Lao;


                        float M_end;
                        float p_1 = 0; // 1 / P
                        float X_Y = 0; // 向量各项相减之和
                        for (int i = 0; i < test.length; i++){
                            X_Y += Math.abs(Math.pow((center_list[i] - test[i]), P));
                        }
                        p_1 =  1 / (float) P;
//                        System.out.println(p_1);
                        M_end = (float) Math.pow(X_Y, p_1);
                        //            System.out.println(Cos_end);
                        M_Endlist.add(M_end);
                    }
                }
                result = Per[M_Endlist.indexOf(Collections.min(M_Endlist))];
                break;
            default: break;
        }

//        System.out.println(result);
        return result;
    }
}
