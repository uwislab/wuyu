package com.fiveup.core.monitor.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fiveup.core.monitor.common.Result;
import com.fiveup.core.monitor.entity.*;
import com.fiveup.core.monitor.mapper.UserMapper2;
import com.fiveup.core.monitor.mapper.UserMapper4;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user4")
public class UserController4 {
    @Resource
    UserMapper4 userMapper4;
    @Resource
    UserMapper2 userMapper2;
    @PostMapping("/ip")
    public Result<?> update(@RequestBody User4 user4){
        LambdaQueryWrapper<User4> wrapper= Wrappers.<User4>lambdaQuery();
        LambdaQueryWrapper<User2> wrapper2= Wrappers.<User2>lambdaQuery();
        LinuxStateForShell2 s2=new LinuxStateForShell2();
        List<User4> l1=new ArrayList<User4>();
        User2 use=userMapper2.selectOne(wrapper2.eq(User2::getIp,user4.getIp()));
        User4 Tuser4=s2.Res(use);
        List<User4> l5=new ArrayList<User4>();
        l5=userMapper4.selectList(wrapper.eq(User4::getCount,"1"));
        l1=userMapper4.selectList(wrapper.eq(User4::getIp,user4.getIp()));
        int num2=0;
        int num3=0;
        if(l5.size()==0||l1.size()==0 ||Tuser4.getIp()==null || Tuser4.getCpu()==null){
            return Result.success();
        }
        for(User4 k:l5){
            num2++;
        }
        User4 k2=l5.get(l5.size()-1);
        num2=k2.getId();
        int num=l1.size();
        int i=0;
        if(num>=20){
            for(User4 Xy:l1){
                if(i==0){
                    userMapper4.deleteById(Xy.getId());
                }
                break;
            }
            double[] ss = new double[num];
            String[] ss2 = new String[num];
            for (User4 x : l1) {
                Double d1 = Double.valueOf(x.getMemtoatal());
                Double d2 = Double.valueOf(x.getMemfree());
                Double d3 = d1 / d2;
                System.out.println(i);
                ss[i] = d3;
                System.out.println(i);
                ss2[i] = x.getDate();
                i++;
            }
            User5 user5 = new User5();
            for (double k : ss) {
                System.out.println(k);
            }
            user5.setDat(ss);
            user5.setBai(ss2);
            num2++;
            Tuser4.setId(num2);
            Tuser4.setCount("1");
            userMapper4.insert(Tuser4);
            return Result.success(user5);
        }else {
            if(num==0){
                return Result.success();
            }
        double[] ss = new double[num];
        String[] ss2 = new String[num];
        System.out.println(num);
        for (User4 x : l1) {
            Double d1 = Double.valueOf(x.getMemtoatal());
            Double d2 = Double.valueOf(x.getMemfree());
            Double d3 = d1 / d2;
            ss[i] = d3;
            ss2[i] = x.getDate();
            i++;
        }
        User5 user5 = new User5();
        for (double k : ss) {
            System.out.println(k);
        }
        user5.setDat(ss);
        user5.setBai(ss2);
        num2++;
        Tuser4.setId(num2);
        Tuser4.setCount("1");
        userMapper4.insert(Tuser4);
        return Result.success(user5);
        }
    }

    @PostMapping("/ip2")
    public Result<?> update2(@RequestBody User4 user4){

        LambdaQueryWrapper<User4> wrapper= Wrappers.<User4>lambdaQuery();

        LambdaQueryWrapper<User2> wrapper2= Wrappers.<User2>lambdaQuery();

        LinuxStateForShell2 s2=new LinuxStateForShell2();

        List<User4> l1=new ArrayList<User4>();

        User2 use=userMapper2.selectOne(wrapper2.eq(User2::getIp,user4.getIp()));

        User4 Tuser4=s2.Res(use);

        List<User4> l5=new ArrayList<User4>();

        l5=userMapper4.selectList(wrapper.eq(User4::getCount,"1"));

        l1=userMapper4.selectList(wrapper.eq(User4::getIp,user4.getIp()));

        int num2=0;
        int num3=0;

        if(l5.size()==0||l1.size()==0 ||Tuser4.getIp()==null || Tuser4.getCpu()==null){

            return Result.success();

        }
        for(User4 k:l5){

            num2++;

        }

        User4 k2=l5.get(l5.size()-1);

        num2=k2.getId();

        int num=l1.size();

        int i=0;

        if(num>=20){

            for(User4 Xy:l1){

                if(i==0){

                    userMapper4.deleteById(Xy.getId());

                }

                break;

            }
            double[] ss = new double[num];
            String[] ss2 = new String[num];
            System.out.println(num);
            for (User4 x : l1) {
                Double d1 = Double.valueOf(x.getMemtoatal());
                Double d2 = Double.valueOf(x.getMemfree());
                Double d3 = d1 / d2;
                System.out.println(i);
                ss[i] = d3;
                System.out.println(i);
                ss2[i] = x.getDate();
                i++;
            }
            User5 user5 = new User5();

            for (double k : ss) {

                System.out.println(k);

            }
            user5.setDat(ss);
            user5.setBai(ss2);
            num2++;
            Tuser4.setId(num2);
            Tuser4.setCount("1");
            userMapper4.insert(Tuser4);
            return Result.success(user5);

        }else {

            if(num==0){

                return Result.success();

            }

            double[] ss = new double[num];

            String[] ss2 = new String[num];

            for (User4 x : l1) {

                Double d1 = Double.valueOf(x.getSwaptotal());
                Double d2 = Double.valueOf(x.getSwapfree());
                Double d3 = d1 / d2;
                System.out.println(i);
                ss[i] = d3;
                System.out.println(i);
                ss2[i] = x.getDate();
                i++;

            }
            User5 user5 = new User5();

            for (double k : ss) {

                System.out.println(k);

            }
            user5.setDat(ss);

            user5.setBai(ss2);

            num2++;

            Tuser4.setId(num2);

            Tuser4.setCount("1");

            userMapper4.insert(Tuser4);

            return Result.success(user5);

        }

    }

}