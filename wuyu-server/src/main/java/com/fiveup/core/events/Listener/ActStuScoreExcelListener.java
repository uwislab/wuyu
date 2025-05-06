package com.fiveup.core.events.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fiveup.core.events.model.ext.ActivityStudentDownloadExt;
import com.fiveup.core.events.service.ActivityStudentService;
import com.fiveup.core.events.service.impl.ActivityStudentServiceImpl;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.excel.StuUploadExt;
import com.fiveup.core.management.service.StuService;
import com.fiveup.core.management.service.impl.StuServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 钟承远
 * @date 2022/4/29
 */
@Slf4j
public class ActStuScoreExcelListener extends AnalysisEventListener<ActivityStudentDownloadExt> {


    //读取数据初始化值
    private static final int BATCH_COUNT = 40;
    List<ActivityStudentDownloadExt> list = new ArrayList<>();

    private ActivityStudentService activityStudentService;


    public ActStuScoreExcelListener() {
        activityStudentService = new ActivityStudentServiceImpl();
    }

    public ActStuScoreExcelListener(ActivityStudentService activityStudentService) {
        this.activityStudentService = activityStudentService;
    }

    /**
     * 这个每一条数据解析都会来调用，数据是一条一条进行解析的
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(ActivityStudentDownloadExt data, AnalysisContext context) {
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有excel表中数据解析完成了 都会来调用这个
     * 解释为什么要保存数据？
     *初始化读取数量为50，表中信息已经加载完毕，，假设excel表中最后只剩下30行遗留数据，所以为了防止存在遗留数据 尽量判断下集合是否为空，不为空在进行存储（这是我的逻辑需要判断，如果不需要也可进行不判断）
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(list.size()==0){
            return;
        }
        saveData();
        log.info("data parsing correctly！");
    }

    /**
     * 加上存储数据库
     */
    public void saveData() {
        activityStudentService.save(list); //代码实现类层保存数据
        log.info("save data successful！");
    }
}
