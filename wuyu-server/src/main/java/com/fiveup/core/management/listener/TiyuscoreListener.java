package com.fiveup.core.management.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.fiveup.core.management.model.excel.TiyuscoreExt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bro
 * @version v1.0.1
 * @date 2024/11/26 0:12
 * @description:
 */
public class TiyuscoreListener extends AnalysisEventListener {
    List<TiyuscoreExt> tiyuscoreExts = new ArrayList<>();

    /**
     * 每解析一行，回调该方法
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        //校验名称
        tiyuscoreExts.add((TiyuscoreExt)data);
    }

    /**
     * 出现异常回调
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (exception instanceof ExcelDataConvertException) {
            /**从0开始计算*/
            Integer columnIndex = ((ExcelDataConvertException) exception).getColumnIndex() + 1;
            Integer rowIndex = ((ExcelDataConvertException) exception).getRowIndex() + 1;
            String message = "第" + rowIndex + "行，第" + columnIndex + "列" + "数据格式有误，请核实";
            throw new RuntimeException(message);
        } else if (exception instanceof RuntimeException) {
            throw exception;
        } else {
            super.onException(exception, context);
        }
    }

    /**
     * 解析完,全部回调
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析完,全部回调逻辑实现
//        tiyuscoreExts.clear();
    }
}
