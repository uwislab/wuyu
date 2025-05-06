package com.fiveup.core.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelExportUtil {
    /**
     * 将EXCEL文件转化为java可操作的Workbook类型
     *
     * @param file Excel文件的流对象
     * @return Workbook workbook java可以操纵的文件类型
     */
    private String pname;
    private String cname;
    private double score = 0;

    public Workbook getWorkbook(MultipartFile file) {
        //获取文件类型，即文件名后缀，通过获取文件名并用.分割文件名，并取用返回值的第二个下标1
        String fileType = file.getOriginalFilename().split("\\.")[1];
        Workbook workbook = new XSSFWorkbook();
        try {
            if (fileType.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (fileType.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return workbook;
    }

    /**
     * @param SheetNum 需要使用的是Excel的第几张表
     * @return
     * @Param workbook 需要操作的Excel文件
     * @Param colNames 列名数组，需要该Excel表的每一列所对应的信息（将作为map的key值）
     */
    public List<ExcelExportUtil> getExcelValues(Workbook workbook, String[] colNames, int SheetNum) {
        //根据Excel修改，目前只读前三列
        int length = 3;
        //初始化基础数据
        String cellData = null, childData = null;
        double summary = 0;
        List<ExcelExportUtil> list = new ArrayList<>();    //用于存储最终结果
        //用于逐一存储表格的每一行数据
        Sheet sheet = workbook.getSheetAt(SheetNum - 1);    //获取Excel文件的表格
        int rowNum = sheet.getPhysicalNumberOfRows();    //获取当前表格最大行数
        //循环遍历表格的每一行，获取每一行的值(i的初始值决定从表格的第几行开始),第一行是表头
        for (int i = 1; i < rowNum; i++) {
            Row row = sheet.getRow(i);    //获取表格中第i行的数据
            //循环遍历表格第i行的每一个单元格的值(j的初始值决定从表格的第几列开始）
            for (int j = 0; j < length; j++) {
                //边界检验
                if (j == length - 2) {
                    try {
                        //处理空值
                        if(row.getCell(length-1)!=null ){
                            if(row.getCell(length-1).getCellType() == CellType.NUMERIC){
                                summary = row.getCell(length - 1).getNumericCellValue();
                            }
                            else
                                summary=0;
                        }else
                            summary=0;
                    } catch (Exception e) {
                        summary = 0;
                        e.printStackTrace();
                    }
                    break;
                }
                try {
                    cellData = row.getCell(j).getStringCellValue();
                    childData = row.getCell(j + 1).getStringCellValue();
                } catch (Exception e) {
                    try {    //如果某一个单元格为纯数字的字符串时会报错，需特殊处理
                        String[] a = new DecimalFormat().format(row.getCell(j).getNumericCellValue()).split(",");
                        String[] b = new DecimalFormat().format(row.getCell(j + 1).getNumericCellValue()).split(",");
                        cellData = a[0];
                        childData = b[0];
                        for (int k = 1; k < a.length; k++) {
                            cellData += a[k];
                            childData += b[k];
                        }
                    } catch (Exception error) {
                        cellData = null;
                        childData = null;
                    }
                }
                //空值处理
                if (cellData == null || cellData.equals(" ")) {
                    cellData = "空";
                }
                if (childData == null || childData.equals(" ")) {
                    childData = "空";
                }
            }
            //避免错误的输入
            if (!childData.equals("空")) {
                list.add(new ExcelExportUtil(cellData, childData, summary));//将第i行数据存入list中
            }
        }
        return list;
    }

}
