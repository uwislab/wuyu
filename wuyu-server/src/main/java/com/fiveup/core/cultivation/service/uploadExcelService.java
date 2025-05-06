package com.fiveup.core.cultivation.service;

import com.fiveup.core.common.util.ExcelExportUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface uploadExcelService {
    List<ExcelExportUtil> uploadExcel(MultipartFile file);
}
