package com.fiveup.core.analyze.controller;

import com.fiveup.core.analyze.entity.Ana_updata_set;
import com.fiveup.core.analyze.entity.Ana_updata_time;
import com.fiveup.core.analyze.repository.UpdataSetRepository;
import com.fiveup.core.analyze.repository.UpdatatimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DataAutoUpdate {

    @Autowired
    private UpdatatimeRepository updatatimeRepository;

    @Autowired
    private UpdataSetRepository updataSetRepository;

    // 每周星期天凌晨2点实行一次 S Min H DayofMonth Month DayofWeek
    @Scheduled(cron = "0 0 2 ? * 1")
    public void Autoupdate(){
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

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("完成？？？？");
    }
}
