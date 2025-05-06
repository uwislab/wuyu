package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.controller.FiveupManagement;
import com.fiveup.core.diagnose.mapper.FiveupManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class FiveupManagementService {
    @Autowired
    FiveupManagementMapper fiveupManagementMapper;
    public boolean insertstudentscore(int id, int deyu, int zhiyu, int tiyu, int meiyu, int laoyu, Long sdate) throws ParseException {

        int i=fiveupManagementMapper.getCount(id);
        if(i==0)
            return false;

        fiveupManagementMapper.insertstudentscore(id,deyu,zhiyu,tiyu,meiyu,laoyu,sdate);
        return true;
    };

    public void updatestudentscore(int id, int deyu, int zhiyu, int tiyu, int meiyu, int laoyu){

        fiveupManagementMapper.updatestudentscore(id,deyu,zhiyu,tiyu,meiyu,laoyu);

    };
    public void deletestudentscore(int id){
        fiveupManagementMapper.deletestudentscore(id);
    };
}
