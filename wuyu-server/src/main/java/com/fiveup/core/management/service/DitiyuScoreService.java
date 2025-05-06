package com.fiveup.core.management.service;

import com.fiveup.core.management.common.PageResult;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.Req.TiyuScoreReq;
import com.fiveup.core.management.model.Resp.DiTiyuscoreResp;
import com.fiveup.core.questionnaire.entity.Class;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author
 * @version v1.0.1
 * @date 2024/11/25 23:06
 * @description:
 */
public interface DitiyuScoreService {

    PageResult<DiTiyuscoreResp> pages(TiyuScoreReq tiyuScoreReq);

    void importData(MultipartFile file);

    void export(HttpServletResponse response) throws IOException;

    void exportTemplate(HttpServletResponse response);

    List<ClassDTO>  classList(long gradeId);

    List<Integer> gradeList(HttpServletResponse response);
}
