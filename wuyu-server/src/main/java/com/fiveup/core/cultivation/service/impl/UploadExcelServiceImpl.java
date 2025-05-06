package com.fiveup.core.cultivation.service.impl;
import com.fiveup.core.common.util.ExcelExportUtil;
import com.fiveup.core.cultivation.service.uploadExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UploadExcelServiceImpl implements uploadExcelService {
    public List<ExcelExportUtil> uploadExcel(MultipartFile file){
        ExcelExportUtil tools = new ExcelExportUtil();
        Workbook workbook = tools.getWorkbook(file);
        String[] colNames = {"","","","","","",};   //表格从第一列开始的每一列列名，这个参数会作为map的key值
        return tools.getExcelValues(workbook,colNames,1);
    }
}
