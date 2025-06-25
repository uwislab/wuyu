package com.fiveup.core;


import com.fiveup.core.management.mapper.SportScoreMapper;
import com.fiveup.core.management.pojo.SportScore;
import com.fiveup.core.management.service.SportScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class SportTest {
    @Autowired
    private SportScoreMapper dao;

    @Autowired
    private SportScoreService service;
    @Test
    public void test(){
        System.out.println(dao.findAllSportScore());
    }
    @Test
    public void test2(){
        System.out.println(dao.findSportScoreForSearch(new com.fiveup.core.management.pojo.SportSearchVO("","0","1","1",null)));
    }
    @Test
    public void test3(){
        SportScore score = new SportScore("李想","2024081199",1,1,1,99,99,99,99,99,99,99);
        List<String> flag = service.addSportScore(score);
        if(!flag.isEmpty()){
            System.out.println(flag);
        }else System.out.println("ok");
    }
    @Test
    public void test4(){
        SportScore score = new SportScore("李想","2024081199",1,1,1,100,100,100,100,100,100,100);
        List<String> flag = service.editBmiSportScore(score);
        if(!flag.isEmpty()){
            System.out.println(flag);
        }else System.out.println("ok");
    }
    @Test
    public void test5(){
        SportScore score = new SportScore("李想","2024081199",1,1,1,100,100,100,100,100,100,100);
        List<String> flag = service.removeSportScore(score);
        if(!flag.isEmpty()){
            System.out.println(flag);
        }else System.out.println("ok");
    }
}
