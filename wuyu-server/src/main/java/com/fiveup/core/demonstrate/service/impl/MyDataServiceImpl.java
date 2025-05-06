package com.fiveup.core.demonstrate.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.fiveup.core.demonstrate.entity.Score;
import com.fiveup.core.demonstrate.entity.Student;
import com.fiveup.core.demonstrate.mapper.MyDataMapper;
import com.fiveup.core.demonstrate.service.MyDataService;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("myDataService")
public class MyDataServiceImpl implements MyDataService {
    @Autowired
    private MyDataMapper myDataMapper;
    @Override
    public Map<String, Object> test() {
        String[] riqi={"2020","2021","2022"};
        for(int i=1;i<13;i++){
            for(int j=0;j<4;j++){
                Student student=new Student();
                student.setStudentNum("2020"+i+""+j);
                student.setClassId(i);
                student.setStudentName("李"+i+""+j);
                myDataMapper.addStudent(student);
                for(String s:riqi){
                    Score score=new Score();
                    score.setName(student.getStudentName());
                    score.setRiqi(s);
                    score.setDeyu(RandomUtil.randomInt(50,100));
                    score.setZhiyu(RandomUtil.randomInt(50,100));
                    score.setTiyu(RandomUtil.randomInt(50,100));
                    score.setMeiyu(RandomUtil.randomInt(50,100));
                    score.setLaoyu(RandomUtil.randomInt(50,100));
                    myDataMapper.addScore(score);
                }
            }
        }


        return myDataMapper.test();
    }

    @Override
    public Map<String, Object> getLineTj() {
        List<Map<String, Object>> maps = myDataMapper.lineTj();
        Map<String,Object> result=new HashMap<>();

        List<String> gradeList=new ArrayList<>();
        Map<String,List<String>> clazzMap=new HashMap<>();
        Map<String,List<String>> riqiMap=new HashMap<>();
        Map<String,List<Map<String,Object>>> fenshuMap=new HashMap<>();
        for(Map<String,Object> m:maps){
            //年级
            String grade = m.get("grade").toString();
            if(!gradeList.contains(grade)){
                gradeList.add(grade);
            }
            //班级
            List<String> clazzList = clazzMap.get(grade);
            if(CollectionUtil.isEmpty(clazzList)){
                clazzList=new ArrayList<>();
            }
            String class_name = m.get("class_name").toString();
            if(!clazzList.contains(class_name)){
                clazzList.add(class_name);
                clazzMap.put(grade,clazzList);
            }

            //分数
            String fenshuKey=grade+"_"+"_fenshu";
            List<Map<String, Object>> fenshuList = fenshuMap.get(fenshuKey);
            if(CollectionUtil.isEmpty(fenshuList)){
                fenshuList=new ArrayList<>();
                Map<String,Object> fenMap=new HashMap<>();
                fenMap.put("name",class_name);
                fenMap.put("type","line");
                List<Integer> list=new ArrayList<>();
                list.add(Integer.parseInt(m.get("count").toString()));
                fenMap.put("data",list);
                fenshuList.add(fenMap);
            }else{
                Map<String, Object> stringObjectMap = fenshuList.get(fenshuList.size() - 1);
                String name = MapUtil.getStr(stringObjectMap, "name");
                if(name.equals(class_name)){
                    List<Integer> data = (List<Integer>)stringObjectMap.get("data");
                    data.add(Integer.parseInt(m.get("count").toString()));
                    stringObjectMap.put("data",data);
                }else{
                    Map<String,Object> fenMap=new HashMap<>();
                    fenMap.put("name",class_name);
                    fenMap.put("type","line");
                    List<Integer> list=new ArrayList<>();
                    list.add(Integer.parseInt(m.get("count").toString()));
                    fenMap.put("data",list);
                    fenshuList.add(fenMap);
                }
            }
            fenshuMap.put(fenshuKey,fenshuList);
            //日期
            String riqiKey=grade+"_riqi";
            List<String> riqiList = riqiMap.get(riqiKey);
            if(CollectionUtil.isEmpty(riqiList)){
                riqiList=new ArrayList<>();
            }
            if(!riqiList.contains(m.get("riqi").toString())){
                riqiList.add(m.get("riqi").toString());
                riqiMap.put(riqiKey,riqiList);
            }



        }
        result.put("gradeList",gradeList);
        result.putAll(clazzMap);
        result.putAll(riqiMap);
        result.putAll(fenshuMap);
        return result;
    }

    @Override
    public Map<String, Object> getLineTj2() {
        List<String> typeList=new ArrayList<>();
        typeList.add("deyu");
        typeList.add("zhiyu");
        typeList.add("tiyu");
        typeList.add("meiyu");
        typeList.add("laoyu");

        List<Map<String, Object>> maps = myDataMapper.lineTj2();
        Map<String,Object> result=new HashMap<>();

        List<String> gradeList=new ArrayList<>();
        Map<String,List<String>> clazzMap=new HashMap<>();
        Map<String,List<String>> riqiMap=new HashMap<>();
        Map<String,List<Map<String,Object>>> fenshuMap=new HashMap<>();
        for(Map<String,Object> m:maps){
            //年级
            String grade = m.get("grade").toString();
            if(!gradeList.contains(grade)){
                gradeList.add(grade);
            }
            //日期
            String riqiKey=grade+"riqi";
            List<String> riqiList = riqiMap.get(riqiKey);
            if(CollectionUtil.isEmpty(riqiList)){
                riqiList=new ArrayList<>();
            }
            if(!riqiList.contains(m.get("riqi").toString())){
                riqiList.add(m.get("riqi").toString());
                riqiMap.put(riqiKey,riqiList);
            }


            //分数
            for(String yu:typeList){

            }
            init("deyu",0,fenshuMap,grade,m);
            init("zhiyu",1,fenshuMap,grade,m);
            init("tiyu",2,fenshuMap,grade,m);
            init("meiyu",3,fenshuMap,grade,m);
            init("laoyu",4,fenshuMap,grade,m);
        }

        result.put("gradeList",gradeList);
        result.putAll(clazzMap);
        result.putAll(riqiMap);
        result.putAll(fenshuMap);
        return result;
    }

    @Override
    public Map<String, Object> getWanchengdu() {
        List<Map<String, Object>> maps = myDataMapper.getWanchengdu();
        List<String> gradeList=new ArrayList<>();
        List<Integer> failList=new ArrayList<>();
        List<Integer> successList=new ArrayList<>();
        for(Map<String,Object> map:maps){
            gradeList.add(map.get("grade").toString());
            failList.add(MapUtil.getInt(map,"fail"));
            successList.add(MapUtil.getInt(map,"success"));
        }
        Map<String,Object> result=new HashMap<>();
        result.put("gradeList",gradeList);
        result.put("successList",successList);
        result.put("failList",failList);
        Map<String, Object> tj = myDataMapper.getTj();
        result.putAll(tj);
        return result;
    }

    //处理分数数组
    public void init(String key,int i,Map<String,List<Map<String,Object>>> fenshuMap,String grade,Map<String,Object> m){
        String deyuKey=key;
        List<Map<String, Object>> fenshuList = fenshuMap.get(deyuKey);
        if(CollectionUtil.isEmpty(fenshuList)){
            fenshuList=new ArrayList<>();
            Map<String,Object> fenMap=new HashMap<>();
            fenMap.put("name",grade);
            fenMap.put("type","line");
            List<Integer> list=new ArrayList<>();
            list.add(Integer.parseInt(m.get(key).toString()));
            fenMap.put("data",list);
            fenshuList.add(fenMap);
        }else{
            Map<String, Object> stringObjectMap = fenshuList.get(fenshuList.size() - 1);
            String name = MapUtil.getStr(stringObjectMap, "name");
            if(name.equals(grade)){
                List<Integer> data = (List<Integer>)stringObjectMap.get("data");
                data.add(Integer.parseInt(m.get(key).toString()));
                stringObjectMap.put("data",data);
            }else{
                Map<String,Object> fenMap=new HashMap<>();
                fenMap.put("name",grade);
                fenMap.put("type","line");
                List<Integer> list=new ArrayList<>();
                list.add(Integer.parseInt(m.get(key).toString()));
                fenMap.put("data",list);
                fenshuList.add(fenMap);
            }
        }
        fenshuMap.put(deyuKey,fenshuList);
    }


    //各年级+各班 随学年变化的五育数据
    //对应年级成长（一组数组包括年级总和各班）
    @Override
    public Map<String, Object> getTj(String type) {
        Map<String,Object> paraMap=new HashMap<>();
        String[] typeStr={"s.deyu","s.zhiyu","s.tiyu","s.meiyu","s.laoyu"};
        paraMap.put("type",typeStr[Integer.parseInt(type)]);
        List<Map<String, Object>> maps = myDataMapper.tj(paraMap);
        List<Map<String, Object>> maps2 = myDataMapper.tj2(paraMap);
        Map<String,List<Map<String,Object>>> fenshuMap=new HashMap<>();
        for(Map<String,Object> m:maps2){
            String grade = m.get("grade").toString();
            String fenshuKey=grade+"_"+"_fenshu";
            List<Map<String, Object>> fenshuList = fenshuMap.get(fenshuKey);
            if(CollectionUtil.isEmpty(fenshuList)){
                fenshuList=new ArrayList<>();
                Map<String,Object> fenMap=new HashMap<>();
                fenMap.put("name",grade);
                fenMap.put("type","line");
                List<Integer> list=new ArrayList<>();
                list.add(Integer.parseInt(m.get("count").toString()));
                fenMap.put("data",list);
                fenshuList.add(fenMap);
            }else{
                Map<String, Object> stringObjectMap = fenshuList.get(fenshuList.size() - 1);
                String name = MapUtil.getStr(stringObjectMap, "name");
                if(name.equals(grade)){
                    List<Integer> data = (List<Integer>)stringObjectMap.get("data");
                    data.add(Integer.parseInt(m.get("count").toString()));
                    stringObjectMap.put("data",data);
                }else{
                    Map<String,Object> fenMap=new HashMap<>();
                    fenMap.put("name",grade);
                    fenMap.put("type","line");
                    List<Integer> list=new ArrayList<>();
                    list.add(Integer.parseInt(m.get("count").toString()));
                    fenMap.put("data",list);
                    fenshuList.add(fenMap);
                }
            }
            fenshuMap.put(fenshuKey,fenshuList);
        }

        Map<String,Object> result=new HashMap<>();
        List<String> gradeList=new ArrayList<>();
        Map<String,List<String>> clazzMap=new HashMap<>();
        Map<String,List<String>> riqiMap=new HashMap<>();
        for(Map<String,Object> m:maps){
            //年级
            String grade = m.get("grade").toString();
            if(!gradeList.contains(grade)){
                gradeList.add(grade);
            }
            //班级
            List<String> clazzList = clazzMap.get(grade);
            if(CollectionUtil.isEmpty(clazzList)){
                clazzList=new ArrayList<>();
                clazzList.add(grade);
            }
            String class_name = m.get("class_name").toString();
            if(!clazzList.contains(class_name)){
                clazzList.add(class_name);
                clazzMap.put(grade,clazzList);
            }

            //分数
            String fenshuKey=grade+"_"+"_fenshu";
            List<Map<String, Object>> fenshuList = fenshuMap.get(fenshuKey);
            if(CollectionUtil.isEmpty(fenshuList)){
                fenshuList=new ArrayList<>();
                Map<String,Object> fenMap=new HashMap<>();
                fenMap.put("name",class_name);
                fenMap.put("type","line");
                List<Integer> list=new ArrayList<>();
                list.add(Integer.parseInt(m.get("count").toString()));
                fenMap.put("data",list);
                fenshuList.add(fenMap);
            }else{
                Map<String, Object> stringObjectMap = fenshuList.get(fenshuList.size() - 1);
                String name = MapUtil.getStr(stringObjectMap, "name");
                if(name.equals(class_name)){
                    List<Integer> data = (List<Integer>)stringObjectMap.get("data");
                    data.add(Integer.parseInt(m.get("count").toString()));
                    stringObjectMap.put("data",data);
                }else{
                    Map<String,Object> fenMap=new HashMap<>();
                    fenMap.put("name",class_name);
                    fenMap.put("type","line");
                    List<Integer> list=new ArrayList<>();
                    list.add(Integer.parseInt(m.get("count").toString()));
                    fenMap.put("data",list);
                    fenshuList.add(fenMap);
                }
            }
            fenshuMap.put(fenshuKey,fenshuList);
            //日期
            String riqiKey=grade+"_riqi";
            List<String> riqiList = riqiMap.get(riqiKey);
            if(CollectionUtil.isEmpty(riqiList)){
                riqiList=new ArrayList<>();
            }
            if(!riqiList.contains(m.get("riqi").toString())){

                riqiList.add(m.get("riqi").toString());
                riqiMap.put(riqiKey,riqiList);
            }
        }
        result.put("gradeList",gradeList);
        result.putAll(clazzMap);
        result.putAll(riqiMap);
        result.putAll(fenshuMap);
        return result;
    }

    @Override
    public Map<String, Object> getGrade2(String type) {
        List<String> gradeParaList=new ArrayList<>();
        if(StrUtil.isNotBlank(type)){
            gradeParaList=Arrays.asList(type.split(","));
        }
        List<Map<String, Object>> maps = myDataMapper.barTj2(gradeParaList);
        List<Map<String, Object>> maps2 = myDataMapper.Qxwycj(gradeParaList);
        Map<String,Object> result=new HashMap<>();
        List<String> gradeList=new ArrayList<>();
        List<Integer> deyuList=new ArrayList<>();
        List<Integer> zhiyuList=new ArrayList<>();
        List<Integer> tiyuList=new ArrayList<>();
        List<Integer> meiyuList=new ArrayList<>();
        List<Integer> laoyuList=new ArrayList<>();

        List<Integer> deyuList2=new ArrayList<>();
        List<Integer> zhiyuList2=new ArrayList<>();
        List<Integer> tiyuList2=new ArrayList<>();
        List<Integer> meiyuList2=new ArrayList<>();
        List<Integer> laoyuList2=new ArrayList<>();

        List<Integer> deyuList3=new ArrayList<>();
        List<Integer> zhiyuList3=new ArrayList<>();
        List<Integer> tiyuList3=new ArrayList<>();
        List<Integer> meiyuList3=new ArrayList<>();
        List<Integer> laoyuList3=new ArrayList<>();

        for(Map<String,Object> m:maps2){
            deyuList3.add(MapUtil.getInt(m,"deyu"));
            zhiyuList3.add(MapUtil.getInt(m,"zhiyu"));
            tiyuList3.add(MapUtil.getInt(m,"tiyu"));
            meiyuList3.add(MapUtil.getInt(m,"meiyu"));
            laoyuList3.add(MapUtil.getInt(m,"laoyu"));
        }

        for(Map<String,Object> m:maps){
            if(!gradeList.contains(m.get("grade").toString())){
                gradeList.add(m.get("grade").toString());
            }
            int gender = Integer.parseInt(m.get("gender").toString());
            if(gender==1){
                //男
                deyuList.add(MapUtil.getInt(m,"deyu"));
                zhiyuList.add(MapUtil.getInt(m,"zhiyu"));
                tiyuList.add(MapUtil.getInt(m,"tiyu"));
                meiyuList.add(MapUtil.getInt(m,"meiyu"));
                laoyuList.add(MapUtil.getInt(m,"laoyu"));
            }else{
                //女
                deyuList2.add(MapUtil.getInt(m,"deyu"));
                zhiyuList2.add(MapUtil.getInt(m,"zhiyu"));
                tiyuList2.add(MapUtil.getInt(m,"tiyu"));
                meiyuList2.add(MapUtil.getInt(m,"meiyu"));
                laoyuList2.add(MapUtil.getInt(m,"laoyu"));
            }
        }
        result.put("xAxisData",gradeList);
        result.put("deyuData1",deyuList);
        result.put("zhiyuData1",zhiyuList);
        result.put("tiyuData1",tiyuList);
        result.put("meiyuData1",meiyuList);
        result.put("laoyuData1",laoyuList);
        result.put("deyuData2",deyuList2);
        result.put("zhiyuData2",zhiyuList2);
        result.put("tiyuData2",tiyuList2);
        result.put("meiyuData2",meiyuList2);
        result.put("laoyuData2",laoyuList2);
        result.put("deyuData3",deyuList3);
        result.put("zhiyuData3",zhiyuList3);
        result.put("tiyuData3",tiyuList3);
        result.put("meiyuData3",meiyuList3);
        result.put("laoyuData3",laoyuList3);
        return result;
    }

    @Override
    public Map<String, Object> getGrade3(String type) {
        List<Map<String, Object>> maps = myDataMapper.Qxwycj(null);
        Map<String,Object> result=new HashMap<>();
        List<String> gradeList=new ArrayList<>();
        gradeList.add("全校");
        for(Map<String,Object> m:maps){
            gradeList.add(m.get("grade").toString());
            List<Integer> dataList=new ArrayList<>();
            dataList.add(MapUtil.getInt(m,"deyu"));
            dataList.add(MapUtil.getInt(m,"zhiyu"));
            dataList.add(MapUtil.getInt(m,"tiyu"));
            dataList.add(MapUtil.getInt(m,"meiyu"));
            dataList.add(MapUtil.getInt(m,"laoyu"));
            result.put(m.get("grade").toString(),dataList);
        }
        result.put("gradeList",gradeList);
        //全校
        List<Map<String, Object>> maps2 = myDataMapper.barTj3();
        for(Map<String,Object> m:maps2){
            List<Integer> dataList=new ArrayList<>();
            dataList.add(MapUtil.getInt(m,"deyu"));
            dataList.add(MapUtil.getInt(m,"zhiyu"));
            dataList.add(MapUtil.getInt(m,"tiyu"));
            dataList.add(MapUtil.getInt(m,"meiyu"));
            dataList.add(MapUtil.getInt(m,"laoyu"));
            result.put("全校",dataList);
        }
        return result;
    }

    // 获取全校按年级的整体五育成绩
    @Override
    public Map<String, Object> getQxwycj(String grade) {
        List<String> gradeParaList=new ArrayList<>();
        if(StrUtil.isNotBlank(grade)){
            gradeParaList= Arrays.asList(grade.split(","));
        }
        List<Map<String, Object>> maps = myDataMapper.Qxwycj(gradeParaList);
        Map<String,Object> result=new HashMap<>();
        List<String> gradeList=new ArrayList<>();
        List<Integer> deyuList=new ArrayList<>();
        List<Integer> zhiyuList=new ArrayList<>();
        List<Integer> tiyuList=new ArrayList<>();
        List<Integer> meiyuList=new ArrayList<>();
        List<Integer> laoyuList=new ArrayList<>();

        for(Map<String,Object> m:maps){
            if(!gradeList.contains(m.get("grade").toString())){
                gradeList.add(m.get("grade").toString());
            }
            deyuList.add(MapUtil.getInt(m,"deyu"));
            zhiyuList.add(MapUtil.getInt(m,"zhiyu"));
            tiyuList.add(MapUtil.getInt(m,"tiyu"));
            meiyuList.add(MapUtil.getInt(m,"meiyu"));
            laoyuList.add(MapUtil.getInt(m,"laoyu"));
        }
        result.put("xAxisData",gradeList);
        result.put("deyuData",deyuList);
        result.put("zhiyuData",zhiyuList);
        result.put("tiyuData",tiyuList);
        result.put("meiyuData",meiyuList);
        result.put("laoyuData",laoyuList);
        return result;
    }


    // 五育达成雷达图
    @Override
    public Map<String, Object> getWydc() {
        List<Map<String, Object>> maps = myDataMapper.Qxwycj(null);
        Map<String,Object> result=new HashMap<>();
        for(Map<String,Object> m:maps){
            List<Integer> dataList=new ArrayList<>();
            dataList.add(MapUtil.getInt(m,"deyu"));
            dataList.add(MapUtil.getInt(m,"zhiyu"));
            dataList.add(MapUtil.getInt(m,"tiyu"));
            dataList.add(MapUtil.getInt(m,"meiyu"));
            dataList.add(MapUtil.getInt(m,"laoyu"));
            result.put(m.get("grade").toString(),dataList);
        }
        return result;
    }

    // 年级成长（单班级无年级总）
    @Override
    public Map<String, Object> getNjcz() {
        Map<String,Object> result=new HashMap<>();
        List<String> typeList=new ArrayList<>();
        typeList.add("deyu");
        typeList.add("zhiyu");
        typeList.add("tiyu");
        typeList.add("meiyu");
        typeList.add("laoyu");

        List<Map<String, Object>> njczMaps = myDataMapper.getNjcz();

        List<String> classList=new ArrayList<>();
        for(Map<String,Object> m:njczMaps){
            //班级名
            String className = MapUtil.getStr(m,"grade")+MapUtil.getStr(m,"class_name");
            m.put("class",className);
            if(!classList.contains(className)){
                classList.add(className);
            }
        }
        List<Map<String,Object>> handleClass = new ArrayList<>();
        handelMap(handleClass,classList,njczMaps);
//        System.out.println(handleClass);
        result.put("classList",handleClass);
        return result;
    }


    public void handelMap(List<Map<String,Object>> handleClass,List<String> classList,List<Map<String, Object>> njczMaps){
        for (String className:classList){
            Map<String,Object> classMap = new HashMap<>();
            List <String> xuenianList = new ArrayList<>();
            List<String> deyuList = new ArrayList<>();
            List<String> zhiyuList = new ArrayList<>();
            List<String> tiyuList = new ArrayList<>();
            List<String> meiyuList = new ArrayList<>();
            List<String> laoyuList = new ArrayList<>();
            List<String> avgScoreList = new ArrayList<>();
            for (Map<String,Object> m:njczMaps){
                if (MapUtil.getStr(m,"class").equals(className)){
                    deyuList.add(MapUtil.getStr(m,"deyu"));
                    zhiyuList.add(MapUtil.getStr(m,"zhiyu"));
                    tiyuList.add(MapUtil.getStr(m,"tiyu"));
                    meiyuList.add(MapUtil.getStr(m,"meiyu"));
                    laoyuList.add(MapUtil.getStr(m,"laoyu"));
                    avgScoreList.add(MapUtil.getStr(m,"avgScore"));
                    String xuenian = Integer.parseInt(MapUtil.getStr(m,"riqi"))-1+"-"+MapUtil.getStr(m,"riqi")+"学年";
                    xuenianList.add(xuenian);
                }
            }
            classMap.put("class_name",MapUtil.getStr(njczMaps.get(0),"class_name"));
            classMap.put("grade",MapUtil.getInt(njczMaps.get(0),"grade"));
            classMap.put("xuenian",xuenianList);
            classMap.put("deyu",deyuList);
            classMap.put("zhiyu",zhiyuList);
            classMap.put("tiyu",tiyuList);
            classMap.put("meiyu",meiyuList);
            classMap.put("laoyu",laoyuList);
            classMap.put("avgScore",avgScoreList);
//            Map<String,Object> map = new HashMap<>();
//            map.put(className,classMap);
            handleClass.add(classMap);
        }
    }

    //年级情况
    @Override
    public  Map<String,Object> getNjqk(){
        Map<String,Object> result=new HashMap<>();
        List<Map<String, Object>> njqkMaps = myDataMapper.getNjqk();
        result.put("class",njqkMaps);
        return result;
    }

    //优秀学生
    @Override
    public Map<String,Object> getXYStudent(){
        Map<String,Object> result=new HashMap<>();
        List<Map<String, Object>> xyStuByAllMaps = myDataMapper.getXYStuByAll();
        List<String> typeList=new ArrayList<>();
        typeList.add("deyu");
        typeList.add("zhiyu");
        typeList.add("tiyu");
        typeList.add("meiyu");
        typeList.add("laoyu");
        for (String one :typeList) {
            List<Map<String, Object>> xyStuByOneMaps = myDataMapper.getXYStuByOne(one);
            List<String> nameList = new ArrayList<>();
            List<Double> scoreOneList = new ArrayList<>();
            for(Map<String,Object> m:xyStuByOneMaps){
                nameList.add(MapUtil.getStr(m,"name"));
                scoreOneList.add(MapUtil.getDouble(m,"score"));
                Map<String,Object> mapOne = new HashMap<>();
                mapOne.put("name",nameList);
                mapOne.put("score",scoreOneList);
                result.put(one,mapOne);
            }
        }
        List<String> nameList1 = new ArrayList<>();
        List<Double> gradeAllList = new ArrayList<>();
        for(Map<String,Object> m:xyStuByAllMaps){
            nameList1.add(MapUtil.getStr(m,"name"));
            gradeAllList.add(MapUtil.getDouble(m,"avg_score"));
        }
        Map<String,Object> map=new HashMap<>();
        map.put("name",nameList1);
        map.put("score",gradeAllList);
        result.put("zong",map);
        return result;
    }


    //优秀班级
    @Override
    public Map<String,Object> getXYClass(){
        Map<String,Object> result=new HashMap<>();
        List<Map<String, Object>> xyClaByAllMaps = myDataMapper.getXYClaByAll();
        for(Map<String,Object> m:xyClaByAllMaps){
            String className = MapUtil.getStr(m,"grade")+MapUtil.getStr(m,"class_name");
            m.remove("grade");
            m.remove("class_name");
            m.put("class",className);

        }
        result.put("xyClass",xyClaByAllMaps);
        return result;
    }


    @Override
    public Map<String, Object> getSubLineTj() {
        List<String> typeList=new ArrayList<>();
        typeList.add("deyu");
        typeList.add("zhiyu");
        typeList.add("tiyu");
        typeList.add("meiyu");
        typeList.add("laoyu");


        List<Map<String, Object>> maps = myDataMapper.getSubLineTj();
        Map<String,Object> result=new HashMap<>();

        List<String> gradeList=new ArrayList<>();
        Map<String,List<String>> clazzMap=new HashMap<>();
        Map<String,List<String>> riqiMap=new HashMap<>();
        Map<String,List<Map<String,Object>>> fenshuMap=new HashMap<>();
        for(Map<String,Object> m:maps){
            //年级
            String grade = m.get("grade").toString();
            if(!gradeList.contains(grade)){
                gradeList.add(grade);
            }
            //日期
            String riqiKey=grade+"riqi";
            List<String> riqiList = riqiMap.get(riqiKey);
            if(CollectionUtil.isEmpty(riqiList)){
                riqiList=new ArrayList<>();
            }
            if(!riqiList.contains(m.get("riqi").toString())){
                riqiList.add(m.get("riqi").toString());
                riqiMap.put(riqiKey,riqiList);
            }
            //分数
            for(String yu:typeList){

            }
            init("deyu",0,fenshuMap,grade,m);
            init("zhiyu",1,fenshuMap,grade,m);
            init("tiyu",2,fenshuMap,grade,m);
            init("meiyu",3,fenshuMap,grade,m);
            init("laoyu",4,fenshuMap,grade,m);
        }

        result.put("gradeList",gradeList);
        result.putAll(clazzMap);
        result.putAll(riqiMap);
        result.putAll(fenshuMap);
        return result;
    }


    //详情页面
    @Override
    public Map<String,Object> getTable(String queryParm,String range){
        Map<String,Object> result=new HashMap<>();
        List<Map<String, Object>> tableMaps = myDataMapper.getTable(queryParm,range);
        List <String> xuenianList = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for(Map<String,Object> m:tableMaps){
            String xuenian = Integer.parseInt(MapUtil.getStr(m,"riqi"))-1+"-"+MapUtil.getStr(m,"riqi")+"学年";
            m.put("xuenian",xuenian);
            m.remove("riqi");
            set.add(xuenian);
        }
        xuenianList.addAll(set);
        result.put("table",tableMaps);
        result.put("xuenianList",xuenianList);
        return result;
    }
}
