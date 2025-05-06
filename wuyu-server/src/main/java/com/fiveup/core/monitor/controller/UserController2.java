package com.fiveup.core.monitor.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveup.core.monitor.common.AddressUtils;
import com.fiveup.core.monitor.common.Result;
import com.fiveup.core.monitor.entity.User2;
import com.fiveup.core.monitor.entity.User3;
import com.fiveup.core.monitor.entity.User4;
import com.fiveup.core.monitor.mapper.UserMapper2;
import com.fiveup.core.monitor.mapper.UserMapper3;
import com.fiveup.core.monitor.mapper.UserMapper4;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/user2")
public class UserController2 {
    @Resource
    UserMapper2 userMapper2;
    @Resource
    UserMapper3 userMapper3;
    @Resource
    UserMapper4 userMapper4;
    @PostMapping
    public Result<?> save(@RequestBody User2 user2) throws ParseException {
        LinuxStateForShell s=new LinuxStateForShell();
        LinuxStateForShell2 s2=new LinuxStateForShell2();
        LambdaQueryWrapper<User3> wrapper= Wrappers.<User3>lambdaQuery();
        LambdaQueryWrapper<User4> wrapper2= Wrappers.<User4>lambdaQuery();
        LambdaQueryWrapper<User2> wrapper3= Wrappers.<User2>lambdaQuery();
        User3 user3= s.Res(user2);
        User4 user4=s2.Res(user2);
        int num=0;
        if (user3.getIp()==null){
            return Result.error("1","连接不到该ip");
        }
        int num5=userMapper2.selectCount(wrapper3.eq(User2::getIp,user2.getIp())).intValue();
        if(num5==0){
            userMapper2.insert(user2);
        }
        else {
        }
        num=userMapper4.selectCount(wrapper2.eq(User4::getCount,1)).intValue();
        List<User4> l1=new ArrayList<User4>();
        List<User4> l2=new ArrayList<User4>();
        User3 res=userMapper3.selectOne(wrapper.eq(User3::getIp,user2.getIp()));
        System.out.println(userMapper4.selectList(wrapper2.eq(User4::getIp,user2.getIp())));
        l1=userMapper4.selectList(wrapper2.eq(User4::getIp,user2.getIp()));
        System.out.println(l1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(user4.getDate());
        int id=0;
        int k=l1.size();
        for(User4 x:l1){
            Date date2 = sdf.parse(x.getDate());
            if(date1.compareTo(date2)>0){
                System.out.println(x.getId());
                id++;
            } else if(date1.compareTo(date2)<0){
                id++;
            }else {
                id=0;
                System.out.println("相等");
                break;
            }
        }
        if(id==0){
            if(k==0){
                num++;
                user4.setId(num);
                user4.setCount("1");
                System.out.println(id);
                userMapper4.insert(user4);
            }
        }else {
            num++;
            user4.setId(num);
            user4.setCount("1");
            System.out.println(id);
            userMapper4.insert(user4);
        }
        if (res==null){
            userMapper3.insert(user3);
        }else {
            return Result.error("1","已经有了该ip");
        }
        return Result.success(user3);
    }

    @PostMapping("/ip")
    public Result<?> register(@RequestBody User3 user3) throws IOException {

        String s2;
        double[] myList=new double[2];

        System.out.println("这里是ip地址"+user3.getTrueip());

        AddressUtils a=new AddressUtils();

        myList=a.getll(user3.getTrueip());

        return Result.success(myList);

    }

    @PostMapping("/ip4")
    public Result<?> rer(@RequestBody User3 user3) throws IOException {
        double[] myList=new double[2];
        System.out.println("这里是ip地址"+user3.getTrueip());
        AddressUtils a=new AddressUtils();
        myList=a.getll(user3.getTrueip());
        return Result.success(myList);

    }

    @PostMapping("/ip3")
    public Result<?> rser(@RequestBody User3 user3) throws IOException {
        User3 res=userMapper3.selectOne(Wrappers.<User3>lambdaQuery().eq(User3::getIp,user3.getIp()));
        if(res==null){
            return Result.success(user3);
        }
        return Result.success(res);
    }
    @PostMapping("/ip2")
    public Result<?> update(@RequestBody User3 user3){

        userMapper3.deleteById(user3.getIp());

        return Result.success();

    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){

        LambdaQueryWrapper<User3> wrapper= Wrappers.<User3>lambdaQuery();

        if(StrUtil.isNotBlank(search)){

            wrapper.like(User3::getIp,search);

        }

        Page<User3> userPage=userMapper3.selectPage(new Page<>(pageNum,pageSize),wrapper);


        return Result.success(userPage);

    }
}

