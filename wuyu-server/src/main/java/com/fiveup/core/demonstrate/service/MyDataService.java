package com.fiveup.core.demonstrate.service;


import java.util.Map;

public interface MyDataService  {

    Map<String, Object> test();

    Map<String, Object> getLineTj();

    Map<String, Object> getLineTj2();

    Map<String, Object> getWanchengdu();

    Map<String, Object> getTj(String type);

    Map<String, Object> getGrade2(String type);

    Map<String, Object> getGrade3(String type);

    Map<String, Object> getQxwycj(String grade);

    Map<String, Object> getWydc();

    Map<String, Object> getNjcz();

    Map<String, Object> getNjqk();

    Map<String, Object> getXYStudent();

    Map<String,Object> getXYClass();

    Map<String, Object> getSubLineTj();

    Map<String,Object> getTable(String queryParm,String range);
}
